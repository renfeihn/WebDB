/*     */ package org.springframework.base.system.utils;
/*     */ 
/*     */ import java.io.PrintWriter;
/*     */ import java.util.Map;
/*     */ import javax.servlet.http.HttpServletRequest;
/*     */ import javax.servlet.http.HttpServletResponse;
/*     */ import javax.servlet.http.HttpSession;
/*     */ import org.springframework.base.system.web.LoginController;
/*     */ import org.springframework.web.servlet.ModelAndView;
/*     */ import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;
/*     */ 
/*     */ public class CommonInterceptor extends HandlerInterceptorAdapter
/*     */ {
/*     */   public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView)
/*     */     throws Exception
/*     */   {
/*     */   }
/*     */ 
/*     */   public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
/*     */     throws Exception
/*     */   {
/*  39 */     request.setCharacterEncoding("UTF-8");
/*  40 */     response.setCharacterEncoding("UTF-8");
/*  41 */     response.setContentType("text/html;charset=UTF-8");
/*     */ 
/*  43 */     HttpSession session = request.getSession(true);
/*  44 */     String userName = (String)session.getAttribute("LOGIN_USER_NAME");
/*     */ 
/*  47 */     String url = request.getServletPath();
/*     */ 
/*  50 */     if (url.indexOf("treesoft/login") != -1) {
/*  51 */       return true;
/*     */     }
/*     */ 
/*  54 */     if (url.indexOf("treesoft/treesoft") != -1) {
/*  55 */       return true;
/*     */     }
/*     */ 
/*  58 */     if (url.indexOf("static/css") != -1) {
/*  59 */       return true;
/*     */     }
/*     */ 
/*  62 */     if (url.indexOf("static/images") != -1) {
/*  63 */       return true;
/*     */     }
/*     */ 
/*  66 */     if (url.indexOf("static/plugins") != -1) {
/*  67 */       return true;
/*     */     }
/*     */ 
/*  70 */     if (url.indexOf("treesoft/logout") != -1) {
/*  71 */       return true;
/*     */     }
/*     */ 
/*  74 */     if (url.indexOf("logout") != -1) {
/*  75 */       return true;
/*     */     }
/*     */ 
/*  78 */     if (url.indexOf("system/login") != -1) {
/*  79 */       return true;
/*     */     }
/*  81 */     if (url.indexOf("treesoft/loginVaildate") != -1) {
/*  82 */       return true;
/*     */     }
/*     */ 
/*  85 */     Map map = LoginController.loginUserMap;
/*     */ 
/*  88 */     if (userName != null) {
/*  89 */       String tempID = (String)map.get(userName);
/*  90 */       String sessionId = session.getId();
/*  91 */       if (!sessionId.equals(tempID)) {
/*  92 */         PrintWriter out = response.getWriter();
/*  93 */         StringBuilder builder = new StringBuilder();
/*  94 */         builder.append("<script type=\"text/javascript\" charset=\"UTF-8\">");
/*  95 */         builder.append("parent.$.messager.alert(\"操作提示\", \"您好,该帐号已在其他地方登录！\",\"error\");");
/*  96 */         builder.append(" </script>");
/*  97 */         out.print(builder.toString());
/*  98 */         out.close();
/*  99 */         return false;
/*     */       }
/*     */     }
/*     */ 
/* 103 */     if (userName == null)
/*     */     {
/* 105 */       PrintWriter out = response.getWriter();
/* 106 */       StringBuilder builder = new StringBuilder();
/* 107 */       builder.append("<script type=\"text/javascript\" charset=\"UTF-8\">");
/*     */ 
/* 109 */       builder.append("window.top.location.href=\"");
/* 110 */       builder.append(request.getContextPath());
/* 111 */       builder.append("/index.jsp\";</script>");
/* 112 */       out.print(builder.toString());
/* 113 */       out.close();
/* 114 */       return false;
/*     */     }
/*     */ 
/* 122 */     return true;
/*     */   }
/*     */ }

/* Location:           C:\Users\renfei\Desktop\treeDMS-2.3.1\treeDMS-2.3.1\webapps\treesoft\WEB-INF\classes\
 * Qualified Name:     org.springframework.base.system.utils.CommonInterceptor
 * JD-Core Version:    0.6.0
 */