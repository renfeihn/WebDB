/*    */ package org.springframework.base.system.utils;
/*    */ 
/*    */ import java.util.HashSet;
/*    */ import javax.servlet.ServletContext;
/*    */ import javax.servlet.http.HttpSession;
/*    */ import javax.servlet.http.HttpSessionEvent;
/*    */ import javax.servlet.http.HttpSessionListener;
/*    */ import org.slf4j.Logger;
/*    */ import org.slf4j.LoggerFactory;
/*    */ 
/*    */ public class SessionListener
/*    */   implements HttpSessionListener
/*    */ {
/* 21 */   private Logger log = LoggerFactory.getLogger(SessionListener.class);
/*    */ 
/*    */   public void sessionCreated(HttpSessionEvent event)
/*    */   {
/* 25 */     HttpSession session = event.getSession();
/* 26 */     ServletContext application = session.getServletContext();
/*    */ 
/* 29 */     HashSet sessions = (HashSet)application.getAttribute("sessions");
/* 30 */     if (sessions == null) {
/* 31 */       sessions = new HashSet();
/* 32 */       application.setAttribute("sessions", sessions);
/*    */     }
/*    */ 
/* 36 */     sessions.add(session);
/*    */   }
/*    */ 
/*    */   public void sessionDestroyed(HttpSessionEvent event)
/*    */   {
/* 44 */     HttpSession session = event.getSession();
/* 45 */     ServletContext application = session.getServletContext();
/* 46 */     HashSet sessions = (HashSet)application.getAttribute("sessions");
/*    */ 
/* 49 */     sessions.remove(session);
/*    */   }
/*    */ }

/* Location:           C:\Users\renfei\Desktop\treeDMS-2.3.1\treeDMS-2.3.1\webapps\treesoft\WEB-INF\classes\
 * Qualified Name:     org.springframework.base.system.utils.SessionListener
 * JD-Core Version:    0.6.0
 */