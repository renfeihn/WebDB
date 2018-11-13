/*     */ package org.springframework.base.common.utils;
/*     */ 
/*     */ import java.io.UnsupportedEncodingException;
/*     */ import java.security.MessageDigest;
/*     */ import java.util.regex.Matcher;
/*     */ import java.util.regex.Pattern;
/*     */ import org.apache.commons.lang3.StringEscapeUtils;
/*     */ 
/*     */ public class StringUtils extends org.apache.commons.lang3.StringUtils
/*     */ {
/*     */   public static String lowerFirst(String str)
/*     */   {
/*  18 */     if (isBlank(str)) {
/*  19 */       return "";
/*     */     }
/*  21 */     return str.substring(0, 1).toLowerCase() + str.substring(1);
/*     */   }
/*     */ 
/*     */   public static String upperFirst(String str)
/*     */   {
/*  26 */     if (isBlank(str)) {
/*  27 */       return "";
/*     */     }
/*  29 */     return str.substring(0, 1).toUpperCase() + str.substring(1);
/*     */   }
/*     */ 
/*     */   public static String replaceHtml(String html)
/*     */   {
/*  37 */     if (isBlank(html)) {
/*  38 */       return "";
/*     */     }
/*  40 */     String regEx = "<.+?>";
/*  41 */     Pattern p = Pattern.compile(regEx);
/*  42 */     Matcher m = p.matcher(html);
/*  43 */     String s = m.replaceAll("");
/*  44 */     return s;
/*     */   }
/*     */ 
/*     */   public static String abbr(String str, int length)
/*     */   {
/*  54 */     if (str == null)
/*  55 */       return "";
/*     */     try
/*     */     {
/*  58 */       StringBuilder sb = new StringBuilder();
/*  59 */       int currentLength = 0;
/*  60 */       for (char c : replaceHtml(StringEscapeUtils.unescapeHtml4(str)).toCharArray()) {
/*  61 */         currentLength += String.valueOf(c).getBytes("GBK").length;
/*  62 */         if (currentLength <= length - 3) {
/*  63 */           sb.append(c);
/*     */         } else {
/*  65 */           sb.append("...");
/*  66 */           break;
/*     */         }
/*     */       }
/*  69 */       return sb.toString();
/*     */     } catch (UnsupportedEncodingException e) {
/*  71 */       e.printStackTrace();
/*     */     }
/*  73 */     return "";
/*     */   }
/*     */ 
/*     */   public static String rabbr(String str, int length)
/*     */   {
/*  83 */     return abbr(replaceHtml(str), length);
/*     */   }
/*     */ 
/*     */   public static Double toDouble(Object val)
/*     */   {
/*  91 */     if (val == null)
/*  92 */       return Double.valueOf(0.0D);
/*     */     try
/*     */     {
/*  95 */       return Double.valueOf(trim(val.toString())); } catch (Exception e) {
/*     */     }
/*  97 */     return Double.valueOf(0.0D);
/*     */   }
/*     */ 
/*     */   public static Float toFloat(Object val)
/*     */   {
/* 105 */     return Float.valueOf(toDouble(val).floatValue());
/*     */   }
/*     */ 
/*     */   public static Long toLong(Object val)
/*     */   {
/* 112 */     return Long.valueOf(toDouble(val).longValue());
/*     */   }
/*     */ 
/*     */   public static Integer toInteger(Object val)
/*     */   {
/* 119 */     return Integer.valueOf(toLong(val).intValue());
/*     */   }
/*     */ 
/*     */   public static final String MD5(String s)
/*     */   {
/* 124 */     char[] hexDigits = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f' };
/*     */     try {
/* 126 */       byte[] strTemp = s.getBytes();
/*     */ 
/* 128 */       MessageDigest mdTemp = MessageDigest.getInstance("MD5");
/* 129 */       mdTemp.update(strTemp);
/* 130 */       byte[] md = mdTemp.digest();
/* 131 */       int j = md.length;
/* 132 */       char[] str = new char[j * 2];
/* 133 */       int k = 0;
/* 134 */       for (int i = 0; i < j; i++) {
/* 135 */         byte b = md[i];
/*     */ 
/* 138 */         str[(k++)] = hexDigits[(b >> 4 & 0xF)];
/* 139 */         str[(k++)] = hexDigits[(b & 0xF)];
/*     */       }
/* 141 */       return new String(str); } catch (Exception e) {
/*     */     }
/* 143 */     return "";
/*     */   }
/*     */ }

/* Location:           C:\Users\renfei\Desktop\treeDMS-2.3.1\treeDMS-2.3.1\webapps\treesoft\WEB-INF\classes\
 * Qualified Name:     org.springframework.base.common.utils.StringUtils
 * JD-Core Version:    0.6.0
 */