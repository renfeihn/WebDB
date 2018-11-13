/*    */ package org.springframework.base.system.utils;
/*    */ 
/*    */ import java.net.InetAddress;
/*    */ import java.net.NetworkInterface;
/*    */ import java.util.Enumeration;
/*    */ import org.springframework.base.common.utils.StringUtils;
/*    */ 
/*    */ public class MacAddress
/*    */ {
/*    */   public static String getMac2()
/*    */   {
/* 16 */     String mac = "";
/*    */     try {
/* 18 */       Enumeration enumeration = NetworkInterface.getNetworkInterfaces();
/* 19 */       while (enumeration.hasMoreElements()) {
/* 20 */         StringBuffer stringBuffer = new StringBuffer();
/* 21 */         NetworkInterface networkInterface = (NetworkInterface)enumeration.nextElement();
/* 22 */         if (networkInterface != null) {
/* 23 */           byte[] bytes = networkInterface.getHardwareAddress();
/* 24 */           if (bytes != null) {
/* 25 */             for (int i = 0; i < bytes.length; i++) {
/* 26 */               if (i != 0)
/*    */               {
/* 28 */                 stringBuffer.append("T");
/*    */               }
/* 30 */               int tmp = bytes[i] & 0xFF;
/* 31 */               String str = Integer.toHexString(tmp);
/* 32 */               if (str.length() == 1)
/* 33 */                 stringBuffer.append("0" + str);
/*    */               else {
/* 35 */                 stringBuffer.append(str);
/*    */               }
/*    */             }
/* 38 */             mac = stringBuffer.toString().toUpperCase();
/*    */           }
/*    */ 
/*    */         }
/*    */ 
/* 43 */         mac = mac.trim();
/* 44 */         if (!mac.equals(""))
/*    */           break;
/*    */       }
/*    */     }
/*    */     catch (Exception e)
/*    */     {
/* 50 */       e.printStackTrace();
/*    */     }
/*    */ 
/* 53 */     return mac;
/*    */   }
/*    */ 
/*    */   public static void main(String[] args)
/*    */     throws Exception
/*    */   {
/*    */   }
/*    */ 
/*    */   public static String getLocalMac()
/*    */   {
/* 68 */     StringBuffer sb = new StringBuffer("");
/*    */     try {
/* 70 */       InetAddress ia = InetAddress.getLocalHost();
/*    */ 
/* 72 */       byte[] mac = NetworkInterface.getByInetAddress(ia).getHardwareAddress();
/*    */ 
/* 74 */       for (int i = 0; i < mac.length; i++) {
/* 75 */         if (i != 0) {
/* 76 */           sb.append("-");
/*    */         }
/*    */ 
/* 79 */         int temp = mac[i] & 0xFF;
/* 80 */         String str = Integer.toHexString(temp);
/*    */ 
/* 82 */         if (str.length() == 1)
/* 83 */           sb.append("0" + str);
/*    */         else {
/* 85 */           sb.append(str);
/*    */         }
/*    */       }
/*    */     }
/*    */     catch (Exception localException)
/*    */     {
/*    */     }
/* 92 */     String str = StringUtils.MD5(sb.toString().toUpperCase());
/* 93 */     return str;
/*    */   }
/*    */ }

/* Location:           C:\Users\renfei\Desktop\treeDMS-2.3.1\treeDMS-2.3.1\webapps\treesoft\WEB-INF\classes\
 * Qualified Name:     org.springframework.base.system.utils.MacAddress
 * JD-Core Version:    0.6.0
 */