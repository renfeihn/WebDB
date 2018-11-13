/*    */ package org.springframework.base.common.utils.security;
/*    */ 
/*    */ import java.beans.PropertyEditorSupport;
/*    */ import org.springframework.web.util.HtmlUtils;
/*    */ import org.springframework.web.util.JavaScriptUtils;
/*    */ 
/*    */ public class StringEscapeEditor extends PropertyEditorSupport
/*    */ {
/*    */   private boolean escapeHTML;
/*    */   private boolean escapeJavaScript;
/*    */ 
/*    */   public StringEscapeEditor()
/*    */   {
/*    */   }
/*    */ 
/*    */   public StringEscapeEditor(boolean escapeHTML, boolean escapeJavaScript)
/*    */   {
/* 25 */     this.escapeHTML = escapeHTML;
/* 26 */     this.escapeJavaScript = escapeJavaScript;
/*    */   }
/*    */ 
/*    */   public String getAsText()
/*    */   {
/* 31 */     Object value = getValue();
/* 32 */     return value != null ? value.toString() : "";
/*    */   }
/*    */ 
/*    */   public void setAsText(String text) throws IllegalArgumentException
/*    */   {
/* 37 */     if (text == null) {
/* 38 */       setValue(null);
/*    */     } else {
/* 40 */       String value = text;
/* 41 */       if (this.escapeHTML) {
/* 42 */         value = HtmlUtils.htmlEscape(value);
/*    */       }
/* 44 */       if (this.escapeJavaScript) {
/* 45 */         value = JavaScriptUtils.javaScriptEscape(value);
/*    */       }
/* 47 */       setValue(value);
/*    */     }
/*    */   }
/*    */ }

/* Location:           C:\Users\renfei\Desktop\treeDMS-2.3.1\treeDMS-2.3.1\webapps\treesoft\WEB-INF\classes\
 * Qualified Name:     org.springframework.base.common.utils.security.StringEscapeEditor
 * JD-Core Version:    0.6.0
 */