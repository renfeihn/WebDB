/*    */ package org.springframework.base.system.utils;
/*    */ 
/*    */ import java.io.IOException;
/*    */ import java.io.PrintWriter;
/*    */ import javax.servlet.Filter;
/*    */ import javax.servlet.FilterChain;
/*    */ import javax.servlet.FilterConfig;
/*    */ import javax.servlet.ServletException;
/*    */ import javax.servlet.ServletRequest;
/*    */ import javax.servlet.ServletResponse;
/*    */ import javax.servlet.http.HttpServletRequest;
/*    */ import javax.servlet.http.HttpServletResponse;
/*    */ 
/*    */ public class AjaxFilter
/*    */   implements Filter
/*    */ {
/*    */   public void init(FilterConfig filterConfig)
/*    */     throws ServletException
/*    */   {
/*    */   }
/*    */ 
/*    */   public void doFilter(ServletRequest servletRequestt, ServletResponse servletResponse, FilterChain chain)
/*    */     throws IOException, ServletException
/*    */   {
/* 22 */     HttpServletRequest request = (HttpServletRequest)servletRequestt;
/* 23 */     HttpServletResponse response = (HttpServletResponse)servletResponse;
/*    */ 
/* 28 */     String ajaxSubmit = request.getHeader("X-Requested-With");
/* 29 */     if ((ajaxSubmit != null) && (ajaxSubmit.equals("XMLHttpRequest")) && 
/* 30 */       (request.getSession(false) == null)) {
/* 31 */       response.setHeader("sessionstatus", "timeout");
/* 32 */       response.getWriter().print("sessionstatus");
/* 33 */       return;
/*    */     }
/*    */ 
/* 36 */     chain.doFilter(servletRequestt, servletResponse);
/*    */   }
/*    */ 
/*    */   public void destroy()
/*    */   {
/*    */   }
/*    */ }

/* Location:           C:\Users\renfei\Desktop\treeDMS-2.3.1\treeDMS-2.3.1\webapps\treesoft\WEB-INF\classes\
 * Qualified Name:     org.springframework.base.system.utils.AjaxFilter
 * JD-Core Version:    0.6.0
 */