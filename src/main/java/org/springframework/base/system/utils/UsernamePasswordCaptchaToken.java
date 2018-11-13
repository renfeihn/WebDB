/*    */ package org.springframework.base.system.utils;
/*    */ 
/*    */ public class UsernamePasswordCaptchaToken
/*    */ {
/*    */   private static final long serialVersionUID = 1L;
/*    */   private String captcha;
/*    */ 
/*    */   public String getCaptcha()
/*    */   {
/* 17 */     return this.captcha;
/*    */   }
/*    */ 
/*    */   public void setCaptcha(String captcha) {
/* 21 */     this.captcha = captcha;
/*    */   }
/*    */ 
/*    */   public UsernamePasswordCaptchaToken()
/*    */   {
/*    */   }
/*    */ 
/*    */   public UsernamePasswordCaptchaToken(String username, char[] password, boolean rememberMe, String host, String captcha)
/*    */   {
/* 30 */     this.captcha = captcha;
/*    */   }
/*    */ }

/* Location:           C:\Users\renfei\Desktop\treeDMS-2.3.1\treeDMS-2.3.1\webapps\treesoft\WEB-INF\classes\
 * Qualified Name:     org.springframework.base.system.utils.UsernamePasswordCaptchaToken
 * JD-Core Version:    0.6.0
 */