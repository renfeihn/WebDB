/*     */ package org.springframework.base.common.utils;
/*     */ 
/*     */ import java.sql.Timestamp;
/*     */ import java.text.ParseException;
/*     */ import java.text.SimpleDateFormat;
/*     */ import java.util.Date;
/*     */ import org.apache.commons.lang3.time.DateFormatUtils;
/*     */ 
/*     */ public class DateUtils extends org.apache.commons.lang3.time.DateUtils
/*     */ {
/*  17 */   private static String[] parsePatterns = { "yyyy-MM-dd", "yyyy-MM-dd HH:mm:ss", "yyyy-MM-dd HH:mm", 
/*  18 */     "yyyy/MM/dd", "yyyy/MM/dd HH:mm:ss", "yyyy/MM/dd HH:mm" };
/*     */ 
/*     */   public static String getDate()
/*     */   {
/*  24 */     return getDate("yyyy-MM-dd");
/*     */   }
/*     */ 
/*     */   public static String getDate(String pattern)
/*     */   {
/*  31 */     return DateFormatUtils.format(new Date(), pattern);
/*     */   }
/*     */ 
/*     */   public static String formatDate(Date date, Object[] pattern)
/*     */   {
/*  38 */     String formatDate = null;
/*  39 */     if ((pattern != null) && (pattern.length > 0))
/*  40 */       formatDate = DateFormatUtils.format(date, pattern[0].toString());
/*     */     else {
/*  42 */       formatDate = DateFormatUtils.format(date, "yyyy-MM-dd");
/*     */     }
/*  44 */     return formatDate;
/*     */   }
/*     */ 
/*     */   public static String formatDateTime(Date date)
/*     */   {
/*  51 */     return formatDate(date, new Object[] { "yyyy-MM-dd HH:mm:ss" });
/*     */   }
/*     */ 
/*     */   public static String getTime()
/*     */   {
/*  58 */     return formatDate(new Date(), new Object[] { "HH:mm:ss" });
/*     */   }
/*     */ 
/*     */   public static String getDateTime()
/*     */   {
/*  65 */     return formatDate(new Date(), new Object[] { "yyyy-MM-dd HH:mm:ss" });
/*     */   }
/*     */ 
/*     */   public static String getDateTimeStringNotTime(Date date)
/*     */   {
/*  72 */     return formatDate(new Date(), new Object[] { "yyyyMMdd" });
/*     */   }
/*     */ 
/*     */   public static String getDateTimeString(Date date)
/*     */   {
/*  79 */     return formatDate(new Date(), new Object[] { "yyyyMMddHHmmss" });
/*     */   }
/*     */ 
/*     */   public static String getYear()
/*     */   {
/*  86 */     return formatDate(new Date(), new Object[] { "yyyy" });
/*     */   }
/*     */ 
/*     */   public static String getMonth()
/*     */   {
/*  93 */     return formatDate(new Date(), new Object[] { "MM" });
/*     */   }
/*     */ 
/*     */   public static String getDay()
/*     */   {
/* 100 */     return formatDate(new Date(), new Object[] { "dd" });
/*     */   }
/*     */ 
/*     */   public static String getWeek()
/*     */   {
/* 107 */     return formatDate(new Date(), new Object[] { "E" });
/*     */   }
/*     */ 
/*     */   public static Date parseDate(Object str)
/*     */   {
/* 116 */     if (str == null)
/* 117 */       return null;
/*     */     try
/*     */     {
/* 120 */       return parseDate(str.toString(), parsePatterns); } catch (ParseException e) {
/*     */     }
/* 122 */     return null;
/*     */   }
/*     */ 
/*     */   public static long pastDays(Date date)
/*     */   {
/* 132 */     long t = new Date().getTime() - date.getTime();
/* 133 */     return t / 86400000L;
/*     */   }
/*     */ 
/*     */   public static Date getDateStart(Date date)
/*     */   {
/* 138 */     if (date == null) {
/* 139 */       return null;
/*     */     }
/* 141 */     SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
/*     */     try {
/* 143 */       date = sdf.parse(formatDate(date, new Object[] { "yyyy-MM-dd" }) + " 00:00:00");
/*     */     } catch (ParseException e) {
/* 145 */       e.printStackTrace();
/*     */     }
/* 147 */     return date;
/*     */   }
/*     */ 
/*     */   public static Date getDateEnd(Date date) {
/* 151 */     if (date == null) {
/* 152 */       return null;
/*     */     }
/* 154 */     SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
/*     */     try {
/* 156 */       date = sdf.parse(formatDate(date, new Object[] { "yyyy-MM-dd" }) + " 23:59:59");
/*     */     } catch (ParseException e) {
/* 158 */       e.printStackTrace();
/*     */     }
/* 160 */     return date;
/*     */   }
/*     */ 
/*     */   public static boolean isDate(String timeString)
/*     */   {
/* 169 */     SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
/* 170 */     format.setLenient(false);
/*     */     try {
/* 172 */       format.parse(timeString);
/*     */     } catch (Exception e) {
/* 174 */       return false;
/*     */     }
/* 176 */     return true;
/*     */   }
/*     */ 
/*     */   public static String dateFormat(Date timestamp)
/*     */   {
/* 185 */     SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
/* 186 */     return format.format(timestamp);
/*     */   }
/*     */ 
/*     */   public static Timestamp getSysTimestamp()
/*     */   {
/* 194 */     return new Timestamp(new Date().getTime());
/*     */   }
/*     */ 
/*     */   public static Date getSysDate()
/*     */   {
/* 202 */     return new Date();
/*     */   }
/*     */ 
/*     */   public static String getDateRandom()
/*     */   {
/* 210 */     String s = new SimpleDateFormat("yyyyMMddHHmmssSSS").format(new Date());
/* 211 */     return s;
/*     */   }
/*     */ 
/*     */   public static void main(String[] args)
/*     */     throws ParseException
/*     */   {
/*     */   }
/*     */ }

/* Location:           C:\Users\renfei\Desktop\treeDMS-2.3.1\treeDMS-2.3.1\webapps\treesoft\WEB-INF\classes\
 * Qualified Name:     org.springframework.base.common.utils.DateUtils
 * JD-Core Version:    0.6.0
 */