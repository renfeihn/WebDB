/*    */ package org.springframework.base.common.utils;
/*    */ 
/*    */ import java.io.PrintWriter;
/*    */ import java.io.StringWriter;
/*    */ 
/*    */ public class Exceptions
/*    */ {
/*    */   public static RuntimeException unchecked(Exception e)
/*    */   {
/* 17 */     if ((e instanceof RuntimeException)) {
/* 18 */       return (RuntimeException)e;
/*    */     }
/* 20 */     return new RuntimeException(e);
/*    */   }
/*    */ 
/*    */   public static String getStackTraceAsString(Exception e)
/*    */   {
/* 28 */     StringWriter stringWriter = new StringWriter();
/* 29 */     e.printStackTrace(new PrintWriter(stringWriter));
/* 30 */     return stringWriter.toString();
/*    */   }
/*    */ 
/*    */   public static boolean isCausedBy(Exception ex, Class<? extends Exception>[] causeExceptionClasses)
/*    */   {
/* 37 */     Throwable cause = ex;
/* 38 */     while (cause != null) {
/* 39 */       for (Class causeClass : causeExceptionClasses) {
/* 40 */         if (causeClass.isInstance(cause)) {
/* 41 */           return true;
/*    */         }
/*    */       }
/* 44 */       cause = cause.getCause();
/*    */     }
/* 46 */     return false;
/*    */   }
/*    */ }

/* Location:           C:\Users\renfei\Desktop\treeDMS-2.3.1\treeDMS-2.3.1\webapps\treesoft\WEB-INF\classes\
 * Qualified Name:     org.springframework.base.common.utils.Exceptions
 * JD-Core Version:    0.6.0
 */