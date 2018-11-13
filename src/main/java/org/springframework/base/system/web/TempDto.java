/*    */ package org.springframework.base.system.web;
/*    */ 
/*    */ public class TempDto
/*    */ {
/*    */   private String id;
/*    */   private String sql;
/*    */   private String dbName;
/*    */   private String tableName;
/*    */   private String oldPass;
/*    */   private String newPass;
/*    */   private String name;
/*    */   private String personNumber;
/*    */   private String company;
/*    */   private String token;
/*    */ 
/*    */   public String getId()
/*    */   {
/* 17 */     return this.id;
/*    */   }
/*    */   public String getTableName() {
/* 20 */     return this.tableName;
/*    */   }
/*    */   public void setTableName(String tableName) {
/* 23 */     this.tableName = tableName;
/*    */   }
/*    */   public void setId(String id) {
/* 26 */     this.id = id;
/*    */   }
/*    */   public String getSql() {
/* 29 */     return this.sql;
/*    */   }
/*    */   public void setSql(String sql) {
/* 32 */     this.sql = sql;
/*    */   }
/*    */   public String getDbName() {
/* 35 */     return this.dbName;
/*    */   }
/*    */   public void setDbName(String dbName) {
/* 38 */     this.dbName = dbName;
/*    */   }
/*    */   public String getOldPass() {
/* 41 */     return this.oldPass;
/*    */   }
/*    */   public void setOldPass(String oldPass) {
/* 44 */     this.oldPass = oldPass;
/*    */   }
/*    */   public String getNewPass() {
/* 47 */     return this.newPass;
/*    */   }
/*    */   public void setNewPass(String newPass) {
/* 50 */     this.newPass = newPass;
/*    */   }
/*    */   public String getName() {
/* 53 */     return this.name;
/*    */   }
/*    */   public void setName(String name) {
/* 56 */     this.name = name;
/*    */   }
/*    */   public String getPersonNumber() {
/* 59 */     return this.personNumber;
/*    */   }
/*    */   public void setPersonNumber(String personNumber) {
/* 62 */     this.personNumber = personNumber;
/*    */   }
/*    */   public String getCompany() {
/* 65 */     return this.company;
/*    */   }
/*    */   public void setCompany(String company) {
/* 68 */     this.company = company;
/*    */   }
/*    */   public String getToken() {
/* 71 */     return this.token;
/*    */   }
/*    */   public void setToken(String token) {
/* 74 */     this.token = token;
/*    */   }
/*    */ }

/* Location:           C:\Users\renfei\Desktop\treeDMS-2.3.1\treeDMS-2.3.1\webapps\treesoft\WEB-INF\classes\
 * Qualified Name:     org.springframework.base.system.web.TempDto
 * JD-Core Version:    0.6.0
 */