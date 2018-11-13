/*    */ package org.springframework.base.system.web;
/*    */ 
/*    */ public class IdsDto
/*    */ {
/*    */   private String[] ids;
/*    */   private String tableName;
/*    */   private String databaseName;
/*    */   private String primary_key;
/*    */   private String column_name;
/*    */   private String is_nullable;
/*    */   private String column_key;
/*    */   private String column_name2;
/*    */   private String checkedItems;
/*    */   private String newTableName;
/*    */ 
/*    */   public String getNewTableName()
/*    */   {
/* 17 */     return this.newTableName;
/*    */   }
/*    */   public void setNewTableName(String newTableName) {
/* 20 */     this.newTableName = newTableName;
/*    */   }
/*    */   public String getCheckedItems() {
/* 23 */     return this.checkedItems;
/*    */   }
/*    */   public void setCheckedItems(String checkedItems) {
/* 26 */     this.checkedItems = checkedItems;
/*    */   }
/*    */   public String getColumn_name2() {
/* 29 */     return this.column_name2;
/*    */   }
/*    */   public void setColumn_name2(String columnName2) {
/* 32 */     this.column_name2 = columnName2;
/*    */   }
/*    */   public String getColumn_name() {
/* 35 */     return this.column_name;
/*    */   }
/*    */   public void setColumn_name(String columnName) {
/* 38 */     this.column_name = columnName;
/*    */   }
/*    */   public String getIs_nullable() {
/* 41 */     return this.is_nullable;
/*    */   }
/*    */   public void setIs_nullable(String isNullable) {
/* 44 */     this.is_nullable = isNullable;
/*    */   }
/*    */   public String getColumn_key() {
/* 47 */     return this.column_key;
/*    */   }
/*    */   public void setColumn_key(String columnKey) {
/* 50 */     this.column_key = columnKey;
/*    */   }
/*    */   public String getPrimary_key() {
/* 53 */     return this.primary_key;
/*    */   }
/*    */   public void setPrimary_key(String primaryKey) {
/* 56 */     this.primary_key = primaryKey;
/*    */   }
/*    */   public String[] getIds() {
/* 59 */     return this.ids;
/*    */   }
/*    */   public void setIds(String[] ids) {
/* 62 */     this.ids = ids;
/*    */   }
/*    */   public String getTableName() {
/* 65 */     return this.tableName;
/*    */   }
/*    */   public void setTableName(String tableName) {
/* 68 */     this.tableName = tableName;
/*    */   }
/*    */   public String getDatabaseName() {
/* 71 */     return this.databaseName;
/*    */   }
/*    */   public void setDatabaseName(String databaseName) {
/* 74 */     this.databaseName = databaseName;
/*    */   }
/*    */ }

/* Location:           C:\Users\renfei\Desktop\treeDMS-2.3.1\treeDMS-2.3.1\webapps\treesoft\WEB-INF\classes\
 * Qualified Name:     org.springframework.base.system.web.IdsDto
 * JD-Core Version:    0.6.0
 */