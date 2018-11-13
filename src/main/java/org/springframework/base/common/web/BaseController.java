/*    */ package org.springframework.base.common.web;
/*    */ 
/*    */ import java.beans.PropertyEditorSupport;
/*    */ import java.sql.Timestamp;
/*    */ import java.util.Date;
/*    */ import java.util.HashMap;
/*    */ import java.util.Map;
/*    */ import javax.servlet.http.HttpServletRequest;
/*    */ import org.apache.commons.lang3.StringEscapeUtils;
/*    */ import org.springframework.base.common.persistence.Page;
/*    */ import org.springframework.base.common.utils.DateUtils;
/*    */ import org.springframework.base.common.utils.StringUtils;
/*    */ import org.springframework.web.bind.WebDataBinder;
/*    */ import org.springframework.web.bind.annotation.InitBinder;
/*    */ 
/*    */ public class BaseController
/*    */ {
/*    */   @InitBinder
/*    */   public void initBinder(WebDataBinder binder)
/*    */   {
/* 30 */     binder.registerCustomEditor(String.class, new PropertyEditorSupport()
/*    */     {
/*    */       public void setAsText(String text) {
/* 33 */         setValue(text == null ? null : StringEscapeUtils.escapeHtml4(text.trim()));
/*    */       }
/*    */ 
/*    */       public String getAsText() {
/* 37 */         Object value = getValue();
/* 38 */         return value != null ? value.toString() : "";
/*    */       }
/*    */     });
/* 43 */     binder.registerCustomEditor(Date.class, new PropertyEditorSupport()
/*    */     {
/*    */       public void setAsText(String text) {
/* 46 */         setValue(DateUtils.parseDate(text));
/*    */       }
/*    */     });
/* 51 */     binder.registerCustomEditor(Timestamp.class, new PropertyEditorSupport()
/*    */     {
/*    */       public void setAsText(String text) {
/* 54 */         Date date = DateUtils.parseDate(text);
/* 55 */         setValue(date == null ? null : new Timestamp(date.getTime()));
/*    */       }
/*    */     });
/*    */   }
/*    */ 
/*    */   public <T> Page<T> getPage(HttpServletRequest request)
/*    */   {
/* 66 */     int pageNo = 1;
/* 67 */     int pageSize = 20;
/* 68 */     String orderBy = "";
/* 69 */     String order = "asc";
/* 70 */     if (StringUtils.isNotEmpty(request.getParameter("page")))
/* 71 */       pageNo = Integer.valueOf(request.getParameter("page")).intValue();
/* 72 */     if (StringUtils.isNotEmpty(request.getParameter("rows")))
/* 73 */       pageSize = Integer.valueOf(request.getParameter("rows")).intValue();
/* 74 */     if (StringUtils.isNotEmpty(request.getParameter("sort")))
/* 75 */       orderBy = request.getParameter("sort").toString();
/* 76 */     if (StringUtils.isNotEmpty(request.getParameter("order")))
/* 77 */       order = request.getParameter("order").toString();
/* 78 */     return new Page(pageNo, pageSize, orderBy, order);
/*    */   }
/*    */ 
/*    */   public <T> Map<String, Object> getEasyUIData(Page<T> page)
/*    */   {
/* 87 */     Map map = new HashMap();
/* 88 */     map.put("rows", page.getResult());
/* 89 */     map.put("total", Long.valueOf(page.getTotalCount()));
/* 90 */     map.put("columns", page.getColumns());
/* 91 */     map.put("primaryKey", page.getPrimaryKey());
/*    */ 
/* 93 */     return map;
/*    */   }
/*    */ }

/* Location:           C:\Users\renfei\Desktop\treeDMS-2.3.1\treeDMS-2.3.1\webapps\treesoft\WEB-INF\classes\
 * Qualified Name:     org.springframework.base.common.web.BaseController
 * JD-Core Version:    0.6.0
 */