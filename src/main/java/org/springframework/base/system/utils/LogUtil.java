/*    */ package org.springframework.base.system.utils;
/*    */ 
/*    */ import org.apache.commons.logging.Log;
/*    */ import org.apache.commons.logging.LogFactory;
/*    */ 
/*    */ public class LogUtil
/*    */ {
/* 12 */   private static Log log = LogFactory.getLog(LogUtil.class);
/*    */ 
/*    */   public static void i(String msg) {
/* 15 */     log.info(msg);
/*    */   }
/*    */ 
/*    */   public static void i(Throwable t) {
/* 19 */     i(t.getMessage(), t);
/*    */   }
/*    */ 
/*    */   public static void i(String msg, Throwable t) {
/* 23 */     log.info(msg, t);
/*    */   }
/*    */ 
/*    */   public static void d(String msg) {
/* 27 */     log.debug(msg);
/*    */   }
/*    */ 
/*    */   public static void d(Throwable t) {
/* 31 */     d(t.getMessage(), t);
/*    */   }
/*    */ 
/*    */   public static void d(String msg, Throwable t) {
/* 35 */     log.debug(msg, t);
/*    */   }
/*    */ 
/*    */   public static void e(String msg) {
/* 39 */     log.error(msg);
/*    */   }
/*    */ 
/*    */   public static void e(Throwable t) {
/* 43 */     e(t.getMessage(), t);
/*    */   }
/*    */ 
/*    */   public static void e(String msg, Throwable t) {
/* 47 */     log.error(msg, t);
/*    */   }
/*    */ 
/*    */   public static void f(String msg) {
/* 51 */     log.fatal(msg);
/*    */   }
/*    */ 
/*    */   public static void f(Throwable t) {
/* 55 */     f(t.getMessage(), t);
/*    */   }
/*    */ 
/*    */   public static void f(String msg, Throwable t) {
/* 59 */     log.fatal(msg, t);
/*    */   }
/*    */ 
/*    */   public static void t(String msg) {
/* 63 */     log.trace(msg);
/*    */   }
/*    */ 
/*    */   public static void t(Throwable t) {
/* 67 */     t(t.getMessage(), t);
/*    */   }
/*    */ 
/*    */   public static void t(String msg, Throwable t) {
/* 71 */     log.trace(msg, t);
/*    */   }
/*    */ }

/* Location:           C:\Users\renfei\Desktop\treeDMS-2.3.1\treeDMS-2.3.1\webapps\treesoft\WEB-INF\classes\
 * Qualified Name:     org.springframework.base.system.utils.LogUtil
 * JD-Core Version:    0.6.0
 */