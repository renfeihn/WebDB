/*    */ package org.springframework.base.system.utils;
/*    */ 
/*    */ import javax.servlet.http.HttpServletRequest;
/*    */ 
/*    */ public class NetworkUtil
/*    */ {
/*    */   public static final String getIpAddress(HttpServletRequest request)
/*    */   {
/* 19 */     String ip = request.getHeader("X-Forwarded-For");
/*    */ 
/* 21 */     if ((ip == null) || (ip.length() == 0) || ("unknown".equalsIgnoreCase(ip))) {
/* 22 */       if ((ip == null) || (ip.length() == 0) || ("unknown".equalsIgnoreCase(ip))) {
/* 23 */         ip = request.getHeader("Proxy-Client-IP");
/*    */       }
/*    */ 
/* 26 */       if ((ip == null) || (ip.length() == 0) || ("unknown".equalsIgnoreCase(ip))) {
/* 27 */         ip = request.getHeader("WL-Proxy-Client-IP");
/*    */       }
/*    */ 
/* 30 */       if ((ip == null) || (ip.length() == 0) || ("unknown".equalsIgnoreCase(ip))) {
/* 31 */         ip = request.getHeader("HTTP_CLIENT_IP");
/*    */       }
/*    */ 
/* 34 */       if ((ip == null) || (ip.length() == 0) || ("unknown".equalsIgnoreCase(ip))) {
/* 35 */         ip = request.getHeader("HTTP_X_FORWARDED_FOR");
/*    */       }
/* 37 */       if ((ip == null) || (ip.length() == 0) || ("unknown".equalsIgnoreCase(ip)))
/* 38 */         ip = request.getRemoteAddr();
/*    */     }
/* 40 */     else if (ip.length() > 15) {
/* 41 */       String[] ips = ip.split(",");
/* 42 */       for (int index = 0; index < ips.length; index++) {
/* 43 */         String strIp = ips[index];
/* 44 */         if (!"unknown".equalsIgnoreCase(strIp)) {
/* 45 */           ip = strIp;
/* 46 */           break;
/*    */         }
/*    */       }
/*    */     }
/* 50 */     return ip;
/*    */   }
/*    */ }

/* Location:           C:\Users\renfei\Desktop\treeDMS-2.3.1\treeDMS-2.3.1\webapps\treesoft\WEB-INF\classes\
 * Qualified Name:     org.springframework.base.system.utils.NetworkUtil
 * JD-Core Version:    0.6.0
 */