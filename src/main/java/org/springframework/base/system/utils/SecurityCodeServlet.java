/*     */ package org.springframework.base.system.utils;
/*     */ 
/*     */ import java.awt.Color;
/*     */ import java.awt.Font;
/*     */ import java.awt.Graphics;
/*     */ import java.awt.image.BufferedImage;
/*     */ import java.io.File;
/*     */ import java.io.IOException;
/*     */ import java.util.Random;
/*     */ import javax.imageio.ImageIO;
/*     */ import javax.servlet.ServletContext;
/*     */ import javax.servlet.ServletException;
/*     */ import javax.servlet.http.HttpServlet;
/*     */ import javax.servlet.http.HttpServletRequest;
/*     */ import javax.servlet.http.HttpServletResponse;
/*     */ import javax.servlet.http.HttpSession;
/*     */ import org.apache.log4j.Logger;
/*     */ 
/*     */ public class SecurityCodeServlet extends HttpServlet
/*     */ {
/*     */   private static final long serialVersionUID = -770474964506858977L;
/*     */   public static final String KEY_SECURITY_CODE = "securityCode";
/*     */   public static final String CODE = "23456789abcdefghijkmnpqrstuvwxyzABCDEFGHIJKLMNPQRSTUVWXYZ";
/*  35 */   public static final int CODE_LENGTH = "23456789abcdefghijkmnpqrstuvwxyzABCDEFGHIJKLMNPQRSTUVWXYZ".length();
/*  36 */   private Logger log = null;
/*     */ 
/*     */   public SecurityCodeServlet()
/*     */   {
/*  40 */     this.log = Logger.getRootLogger();
/*     */   }
/*     */ 
/*     */   public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
/*     */   {
/*  45 */     Constants.DATABASEPATH = request.getSession().getServletContext().getRealPath("/") + "WEB-INF" + File.separator + "classes" + File.separator;
/*     */ 
/*  47 */     response.setContentType("image/jpeg");
/*  48 */     createImage(request, response);
/*     */   }
/*     */ 
/*     */   private void createImage(HttpServletRequest request, HttpServletResponse response)
/*     */   {
/*  53 */     response.setHeader("Pragma", "No-cache");
/*  54 */     response.setHeader("Cache-Control", "no-cache");
/*  55 */     response.setDateHeader("Expires", 0L);
/*     */ 
/*  57 */     int width = 60; int height = 30;
/*  58 */     BufferedImage image = new BufferedImage(width, height, 1);
/*     */ 
/*  60 */     Graphics g = image.getGraphics();
/*     */ 
/*  62 */     Random random = new Random();
/*     */ 
/*  66 */     g.setColor(new Color(255, 255, 255));
/*  67 */     g.fillRect(0, 0, width, height);
/*     */ 
/*  72 */     g.setFont(new Font("Arial", 0, 18));
/*     */ 
/*  74 */     g.setColor(getRandColor(160, 200));
/*     */ 
/*  76 */     for (int i = 0; i < 155; i++) {
/*  77 */       int x = random.nextInt(width);
/*  78 */       int y = random.nextInt(height);
/*  79 */       int xl = random.nextInt(12);
/*  80 */       int yl = random.nextInt(12);
/*  81 */       g.drawLine(x, y, x + xl, y + yl);
/*     */     }
/*     */ 
/*  84 */     String sRand = "";
/*  85 */     for (int i = 0; i < 4; i++) {
/*  86 */       int code = random.nextInt(CODE_LENGTH);
/*     */ 
/*  88 */       String rand = String.valueOf("23456789abcdefghijkmnpqrstuvwxyzABCDEFGHIJKLMNPQRSTUVWXYZ".charAt(code));
/*  89 */       sRand = sRand.concat(rand);
/*     */ 
/*  91 */       g.setColor(
/*  92 */         new Color(20 + random.nextInt(110), 20 + 
/*  93 */         random.nextInt(110), 20 + random.nextInt(110)));
/*  94 */       g.drawString(rand, 13 * i + 6, 16);
/*     */     }
/*     */ 
/*  97 */     request.getSession().setAttribute("KAPTCHA_SESSION_KEY", sRand.toLowerCase());
/*     */ 
/*  99 */     g.dispose();
/*     */     try
/*     */     {
/* 102 */       ImageIO.write(image, "JPEG", response.getOutputStream());
/*     */     } catch (IOException e) {
/* 104 */       this.log.error("SecurityCodeSevlet.createImage() : Failed : " + e.getMessage());
/*     */     }
/*     */   }
/*     */ 
/*     */   private Color getRandColor(int fc, int bc)
/*     */   {
/* 110 */     Random random = new Random();
/* 111 */     if (fc > 255)
/* 112 */       fc = 255;
/* 113 */     if (bc > 255)
/* 114 */       bc = 255;
/* 115 */     int r = fc + random.nextInt(bc - fc);
/* 116 */     int g = fc + random.nextInt(bc - fc);
/* 117 */     int b = fc + random.nextInt(bc - fc);
/* 118 */     return new Color(r, g, b);
/*     */   }
/*     */ }

/* Location:           C:\Users\renfei\Desktop\treeDMS-2.3.1\treeDMS-2.3.1\webapps\treesoft\WEB-INF\classes\
 * Qualified Name:     org.springframework.base.system.utils.SecurityCodeServlet
 * JD-Core Version:    0.6.0
 */