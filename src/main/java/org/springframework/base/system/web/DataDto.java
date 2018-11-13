/*    */ package org.springframework.base.system.web;
/*    */ 
/*    */ public class DataDto
/*    */ {
/*    */   private String tableName;
/*    */   private String databaseName;
/*    */   private String inserted;
/*    */   private String updated;
/*    */ 
/*    */   public String getTableName()
/*    */   {
/* 14 */     return this.tableName;
/*    */   }
/*    */   public void setTableName(String tableName) {
/* 17 */     this.tableName = tableName;
/*    */   }
/*    */   public String getDatabaseName() {
/* 20 */     return this.databaseName;
/*    */   }
/*    */   public void setDatabaseName(String databaseName) {
/* 23 */     this.databaseName = databaseName;
/*    */   }
/*    */   public String getInserted() {
/* 26 */     return this.inserted;
/*    */   }
/*    */   public void setInserted(String inserted) {
/* 29 */     this.inserted = inserted;
/*    */   }
/*    */   public String getUpdated() {
/* 32 */     return this.updated;
/*    */   }
/*    */   public void setUpdated(String updated) {
/* 35 */     this.updated = updated;
/*    */   }
/*    */ }

/* Location:           C:\Users\renfei\Desktop\treeDMS-2.3.1\treeDMS-2.3.1\webapps\treesoft\WEB-INF\classes\
 * Qualified Name:     org.springframework.base.system.web.DataDto
 * JD-Core Version:    0.6.0
 */