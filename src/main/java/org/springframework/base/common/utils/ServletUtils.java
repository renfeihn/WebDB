/*     */ package org.springframework.base.common.utils;
/*     */ 
/*     */ import java.io.UnsupportedEncodingException;
/*     */ import java.util.Enumeration;
/*     */ import java.util.Map;
/*     */ import java.util.StringTokenizer;
/*     */ import java.util.TreeMap;
/*     */ import javax.servlet.ServletRequest;
/*     */ import javax.servlet.http.HttpServletRequest;
/*     */ import javax.servlet.http.HttpServletResponse;
/*     */ import org.springframework.util.Assert;
/*     */ 
/*     */ public class ServletUtils
/*     */ {
/*     */   public static final String TEXT_TYPE = "text/plain";
/*     */   public static final String JSON_TYPE = "application/json";
/*     */   public static final String XML_TYPE = "text/xml";
/*     */   public static final String HTML_TYPE = "text/html";
/*     */   public static final String JS_TYPE = "text/javascript";
/*     */   public static final String EXCEL_TYPE = "application/vnd.ms-excel";
/*     */   public static final String AUTHENTICATION_HEADER = "Authorization";
/*     */   public static final long ONE_YEAR_SECONDS = 31536000L;
/*     */ 
/*     */   public static void setExpiresHeader(HttpServletResponse response, long expiresSeconds)
/*     */   {
/*  41 */     response.setDateHeader("Expires", System.currentTimeMillis() + expiresSeconds * 1000L);
/*     */ 
/*  43 */     response.setHeader("Cache-Control", "private, max-age=" + expiresSeconds);
/*     */   }
/*     */ 
/*     */   public static void setDisableCacheHeader(HttpServletResponse response)
/*     */   {
/*  51 */     response.setDateHeader("Expires", 1L);
/*  52 */     response.addHeader("Pragma", "no-cache");
/*     */ 
/*  54 */     response.setHeader("Cache-Control", "no-cache, no-store, max-age=0");
/*     */   }
/*     */ 
/*     */   public static void setLastModifiedHeader(HttpServletResponse response, long lastModifiedDate)
/*     */   {
/*  61 */     response.setDateHeader("Last-Modified", lastModifiedDate);
/*     */   }
/*     */ 
/*     */   public static void setEtag(HttpServletResponse response, String etag)
/*     */   {
/*  68 */     response.setHeader("ETag", etag);
/*     */   }
/*     */ 
/*     */   public static boolean checkIfModifiedSince(HttpServletRequest request, HttpServletResponse response, long lastModified)
/*     */   {
/*  80 */     long ifModifiedSince = request.getDateHeader("If-Modified-Since");
/*  81 */     if ((ifModifiedSince != -1L) && (lastModified < ifModifiedSince + 1000L)) {
/*  82 */       response.setStatus(304);
/*  83 */       return false;
/*     */     }
/*  85 */     return true;
/*     */   }
/*     */ 
/*     */   public static boolean checkIfNoneMatchEtag(HttpServletRequest request, HttpServletResponse response, String etag)
/*     */   {
/*  96 */     String headerValue = request.getHeader("If-None-Match");
/*  97 */     if (headerValue != null) {
/*  98 */       boolean conditionSatisfied = false;
/*  99 */       if (!"*".equals(headerValue)) {
/* 100 */         StringTokenizer commaTokenizer = new StringTokenizer(headerValue, ",");
/*     */         do
/*     */         {
/* 103 */           String currentToken = commaTokenizer.nextToken();
/* 104 */           if (currentToken.trim().equals(etag))
/* 105 */             conditionSatisfied = true;
/* 102 */           if (conditionSatisfied) break; 
/* 102 */         }while (commaTokenizer.hasMoreTokens());
/*     */       }
/*     */       else
/*     */       {
/* 109 */         conditionSatisfied = true;
/*     */       }
/*     */ 
/* 112 */       if (conditionSatisfied) {
/* 113 */         response.setStatus(304);
/* 114 */         response.setHeader("ETag", etag);
/* 115 */         return false;
/*     */       }
/*     */     }
/* 118 */     return true;
/*     */   }
/*     */ 
/*     */   public static void setFileDownloadHeader(HttpServletResponse response, String fileName)
/*     */   {
/*     */     try
/*     */     {
/* 129 */       String encodedfileName = new String(fileName.getBytes(), "ISO8859-1");
/* 130 */       response.setHeader("Content-Disposition", "attachment; filename=\"" + encodedfileName + "\"");
/*     */     }
/*     */     catch (UnsupportedEncodingException localUnsupportedEncodingException)
/*     */     {
/*     */     }
/*     */   }
/*     */ 
/*     */   public static Map<String, Object> getParametersStartingWith(ServletRequest request, String prefix)
/*     */   {
/* 142 */     Assert.notNull(request, "Request must not be null");
/* 143 */     Enumeration paramNames = request.getParameterNames();
/* 144 */     Map params = new TreeMap();
/* 145 */     if (prefix == null) {
/* 146 */       prefix = "";
/*     */     }
/* 148 */     while ((paramNames != null) && (paramNames.hasMoreElements())) {
/* 149 */       String paramName = (String)paramNames.nextElement();
/* 150 */       if (("".equals(prefix)) || (paramName.startsWith(prefix))) {
/* 151 */         String unprefixed = paramName.substring(prefix.length());
/* 152 */         String[] values = request.getParameterValues(paramName);
/* 153 */         if ((values == null) || (values.length == 0))
/*     */           continue;
/* 155 */         if (values.length > 1)
/* 156 */           params.put(unprefixed, values);
/*     */         else {
/* 158 */           params.put(unprefixed, values[0]);
/*     */         }
/*     */       }
/*     */     }
/* 162 */     return params;
/*     */   }
/*     */ }

/* Location:           C:\Users\renfei\Desktop\treeDMS-2.3.1\treeDMS-2.3.1\webapps\treesoft\WEB-INF\classes\
 * Qualified Name:     org.springframework.base.common.utils.ServletUtils
 * JD-Core Version:    0.6.0
 */