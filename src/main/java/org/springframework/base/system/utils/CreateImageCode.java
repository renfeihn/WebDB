/*     */ package org.springframework.base.system.utils;
/*     */ 
/*     */ import java.awt.Color;
/*     */ import java.awt.Font;
/*     */ import java.awt.Graphics;
/*     */ import java.awt.image.BufferedImage;
/*     */ import java.io.IOException;
/*     */ import java.io.OutputStream;
/*     */ import java.util.Random;
/*     */ import javax.imageio.ImageIO;
/*     */ import javax.servlet.ServletException;
/*     */ import javax.servlet.http.HttpServlet;
/*     */ import javax.servlet.http.HttpServletRequest;
/*     */ import javax.servlet.http.HttpServletResponse;
/*     */ import javax.servlet.http.HttpSession;
/*     */ 
/*     */ public class CreateImageCode extends HttpServlet
/*     */ {
/*     */   private static final long serialVersionUID = 8484577524665768986L;
/*  23 */   private int width = 160;
/*     */ 
/*  25 */   private int height = 40;
/*     */ 
/*  27 */   private int codeCount = 1;
/*     */ 
/*  29 */   private int lineCount = 20;
/*     */ 
/*  31 */   private String code = null;
/*     */ 
/*  33 */   private BufferedImage buffImg = null;
/*  34 */   Random random = new Random();
/*     */ 
/*     */   public void doGet(HttpServletRequest request, HttpServletResponse response)
/*     */     throws ServletException, IOException
/*     */   {
/*  40 */     response.setContentType("image/jpeg");
/*  41 */     response.setHeader("Pragma", "no-cache");
/*  42 */     response.setHeader("Cache-Control", "no-cache");
/*  43 */     response.setDateHeader("Expires", 0L);
/*     */ 
/*  45 */     CreateImageCode vCode = new CreateImageCode(100, 30, 5, 10);
/*  46 */     request.getSession().setAttribute("KAPTCHA_SESSION_KEY", vCode.getCode().toLowerCase());
/*  47 */     vCode.write(response.getOutputStream());
/*     */   }
/*     */ 
/*     */   public CreateImageCode()
/*     */   {
/*  52 */     creatImage();
/*     */   }
/*     */ 
/*     */   public CreateImageCode(int width, int height) {
/*  56 */     this.width = width;
/*  57 */     this.height = height;
/*  58 */     creatImage();
/*     */   }
/*     */ 
/*     */   public CreateImageCode(int width, int height, int codeCount) {
/*  62 */     this.width = width;
/*  63 */     this.height = height;
/*  64 */     this.codeCount = codeCount;
/*  65 */     creatImage();
/*     */   }
/*     */ 
/*     */   public CreateImageCode(int width, int height, int codeCount, int lineCount) {
/*  69 */     this.width = width;
/*  70 */     this.height = height;
/*  71 */     this.codeCount = codeCount;
/*  72 */     this.lineCount = lineCount;
/*  73 */     creatImage();
/*     */   }
/*     */ 
/*     */   private void creatImage()
/*     */   {
/*  78 */     int fontWidth = this.width / this.codeCount;
/*  79 */     int fontHeight = this.height - 5;
/*  80 */     int codeY = this.height - 8;
/*     */ 
/*  83 */     this.buffImg = new BufferedImage(this.width, this.height, 1);
/*  84 */     Graphics g = this.buffImg.getGraphics();
/*     */ 
/*  87 */     g.setColor(getRandColor(200, 250));
/*  88 */     g.fillRect(0, 0, this.width, this.height);
/*     */ 
/*  92 */     Font font = new Font("Fixedsys", 1, fontHeight);
/*  93 */     g.setFont(font);
/*     */ 
/*  96 */     for (int i = 0; i < this.lineCount; i++) {
/*  97 */       int xs = this.random.nextInt(this.width);
/*  98 */       int ys = this.random.nextInt(this.height);
/*  99 */       int xe = xs + this.random.nextInt(this.width);
/* 100 */       int ye = ys + this.random.nextInt(this.height);
/* 101 */       g.setColor(getRandColor(1, 255));
/* 102 */       g.drawLine(xs, ys, xe, ye);
/*     */     }
/*     */ 
/* 106 */     float yawpRate = 0.01F;
/* 107 */     int area = (int)(yawpRate * this.width * this.height);
/* 108 */     for (int i = 0; i < area; i++) {
/* 109 */       int x = this.random.nextInt(this.width);
/* 110 */       int y = this.random.nextInt(this.height);
/*     */ 
/* 112 */       this.buffImg.setRGB(x, y, this.random.nextInt(255));
/*     */     }
/*     */ 
/* 115 */     String str1 = randomStr(this.codeCount);
/* 116 */     this.code = str1;
/* 117 */     for (int i = 0; i < this.codeCount; i++) {
/* 118 */       String strRand = str1.substring(i, i + 1);
/* 119 */       g.setColor(getRandColor(1, 255));
/*     */ 
/* 123 */       g.drawString(strRand, i * fontWidth + 3, codeY);
/*     */     }
/*     */   }
/*     */ 
/*     */   private String randomStr(int n)
/*     */   {
/* 130 */     String str1 = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz1234567890";
/* 131 */     String str2 = "";
/* 132 */     int len = str1.length() - 1;
/*     */ 
/* 134 */     for (int i = 0; i < n; i++) {
/* 135 */       double r = Math.random() * len;
/* 136 */       str2 = str2 + str1.charAt((int)r);
/*     */     }
/* 138 */     return str2;
/*     */   }
/*     */ 
/*     */   private Color getRandColor(int fc, int bc)
/*     */   {
/* 143 */     if (fc > 255)
/* 144 */       fc = 255;
/* 145 */     if (bc > 255)
/* 146 */       bc = 255;
/* 147 */     int r = fc + this.random.nextInt(bc - fc);
/* 148 */     int g = fc + this.random.nextInt(bc - fc);
/* 149 */     int b = fc + this.random.nextInt(bc - fc);
/* 150 */     return new Color(r, g, b);
/*     */   }
/*     */ 
/*     */   private Font getFont(int size)
/*     */   {
/* 157 */     Random random = new Random();
/* 158 */     Font[] font = new Font[5];
/* 159 */     font[0] = new Font("Ravie", 0, size);
/* 160 */     font[1] = new Font("Antique Olive Compact", 0, size);
/* 161 */     font[2] = new Font("Fixedsys", 0, size);
/* 162 */     font[3] = new Font("Wide Latin", 0, size);
/* 163 */     font[4] = new Font("Gill Sans Ultra Bold", 0, size);
/* 164 */     return font[random.nextInt(5)];
/*     */   }
/*     */ 
/*     */   private void shear(Graphics g, int w1, int h1, Color color)
/*     */   {
/* 169 */     shearX(g, w1, h1, color);
/* 170 */     shearY(g, w1, h1, color);
/*     */   }
/*     */ 
/*     */   private void shearX(Graphics g, int w1, int h1, Color color)
/*     */   {
/* 175 */     int period = this.random.nextInt(2);
/*     */ 
/* 177 */     boolean borderGap = true;
/* 178 */     int frames = 1;
/* 179 */     int phase = this.random.nextInt(2);
/*     */ 
/* 181 */     for (int i = 0; i < h1; i++) {
/* 182 */       double d = (period >> 1) * 
/* 183 */         Math.sin(i / period + 
/* 184 */         6.283185307179586D * phase / 
/* 185 */         frames);
/* 186 */       g.copyArea(0, i, w1, 1, (int)d, 0);
/* 187 */       if (borderGap) {
/* 188 */         g.setColor(color);
/* 189 */         g.drawLine((int)d, i, 0, i);
/* 190 */         g.drawLine((int)d + w1, i, w1, i);
/*     */       }
/*     */     }
/*     */   }
/*     */ 
/*     */   private void shearY(Graphics g, int w1, int h1, Color color)
/*     */   {
/* 198 */     int period = this.random.nextInt(40) + 10;
/*     */ 
/* 200 */     boolean borderGap = true;
/* 201 */     int frames = 20;
/* 202 */     int phase = 7;
/* 203 */     for (int i = 0; i < w1; i++) {
/* 204 */       double d = (period >> 1) * 
/* 205 */         Math.sin(i / period + 
/* 206 */         6.283185307179586D * phase / 
/* 207 */         frames);
/* 208 */       g.copyArea(i, 0, 1, h1, 0, (int)d);
/* 209 */       if (borderGap) {
/* 210 */         g.setColor(color);
/* 211 */         g.drawLine(i, (int)d, i, 0);
/* 212 */         g.drawLine(i, (int)d + h1, i, h1);
/*     */       }
/*     */     }
/*     */   }
/*     */ 
/*     */   public void write(OutputStream sos)
/*     */     throws IOException
/*     */   {
/* 220 */     ImageIO.write(this.buffImg, "png", sos);
/* 221 */     sos.close();
/*     */   }
/*     */ 
/*     */   public BufferedImage getBuffImg() {
/* 225 */     return this.buffImg;
/*     */   }
/*     */ 
/*     */   public String getCode() {
/* 229 */     return this.code.toLowerCase();
/*     */   }
/*     */ 
/*     */   public void getCode3(HttpServletRequest req, HttpServletResponse response, HttpSession session)
/*     */     throws IOException
/*     */   {
/* 235 */     response.setContentType("image/jpeg");
/* 236 */     response.setHeader("Pragma", "no-cache");
/* 237 */     response.setHeader("Cache-Control", "no-cache");
/* 238 */     response.setDateHeader("Expires", 0L);
/*     */ 
/* 240 */     CreateImageCode vCode = new CreateImageCode(100, 30, 5, 10);
/*     */ 
/* 242 */     session.setAttribute("KAPTCHA_SESSION_KEY", vCode.getCode().toLowerCase());
/* 243 */     vCode.write(response.getOutputStream());
/*     */   }
/*     */ }

/* Location:           C:\Users\renfei\Desktop\treeDMS-2.3.1\treeDMS-2.3.1\webapps\treesoft\WEB-INF\classes\
 * Qualified Name:     org.springframework.base.system.utils.CreateImageCode
 * JD-Core Version:    0.6.0
 */