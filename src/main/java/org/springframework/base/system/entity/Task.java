/*     */ package org.springframework.base.system.entity;
/*     */ 
/*     */ public class Task
/*     */ {
/*     */   private String id;
/*     */   private String name;
/*     */   private String createDate;
/*     */   private String updateDate;
/*     */   private String souceConfigId;
/*     */   private String souceDataBase;
/*     */   private String doSql;
/*     */   private String targetConfigId;
/*     */   private String targetDataBase;
/*     */   private String targetTable;
/*     */   private String cron;
/*     */   private String operation;
/*     */   private String comments;
/*     */   private String status;
/*     */   private String state;
/*     */   private String updateUser;
/*     */   private String qualification;
/*     */ 
/*     */   public String getId()
/*     */   {
/*  29 */     return this.id;
/*     */   }
/*     */   public void setId(String id) {
/*  32 */     this.id = id;
/*     */   }
/*     */   public String getName() {
/*  35 */     return this.name;
/*     */   }
/*     */   public void setName(String name) {
/*  38 */     this.name = name;
/*     */   }
/*     */ 
/*     */   public String getState() {
/*  42 */     return this.state;
/*     */   }
/*     */   public void setState(String state) {
/*  45 */     this.state = state;
/*     */   }
/*     */   public String getCreateDate() {
/*  48 */     return this.createDate;
/*     */   }
/*     */   public void setCreateDate(String createDate) {
/*  51 */     this.createDate = createDate;
/*     */   }
/*     */   public String getUpdateDate() {
/*  54 */     return this.updateDate;
/*     */   }
/*     */   public void setUpdateDate(String updateDate) {
/*  57 */     this.updateDate = updateDate;
/*     */   }
/*     */ 
/*     */   public String getSouceDataBase() {
/*  61 */     return this.souceDataBase;
/*     */   }
/*     */   public void setSouceDataBase(String souceDataBase) {
/*  64 */     this.souceDataBase = souceDataBase;
/*     */   }
/*     */   public String getDoSql() {
/*  67 */     return this.doSql;
/*     */   }
/*     */   public void setDoSql(String doSql) {
/*  70 */     this.doSql = doSql;
/*     */   }
/*     */ 
/*     */   public String getSouceConfigId() {
/*  74 */     return this.souceConfigId;
/*     */   }
/*     */   public void setSouceConfigId(String souceConfigId) {
/*  77 */     this.souceConfigId = souceConfigId;
/*     */   }
/*     */   public String getTargetConfigId() {
/*  80 */     return this.targetConfigId;
/*     */   }
/*     */   public void setTargetConfigId(String targetConfigId) {
/*  83 */     this.targetConfigId = targetConfigId;
/*     */   }
/*     */   public String getTargetDataBase() {
/*  86 */     return this.targetDataBase;
/*     */   }
/*     */   public void setTargetDataBase(String targetDataBase) {
/*  89 */     this.targetDataBase = targetDataBase;
/*     */   }
/*     */   public String getTargetTable() {
/*  92 */     return this.targetTable;
/*     */   }
/*     */   public void setTargetTable(String targetTable) {
/*  95 */     this.targetTable = targetTable;
/*     */   }
/*     */   public String getCron() {
/*  98 */     return this.cron;
/*     */   }
/*     */   public void setCron(String cron) {
/* 101 */     this.cron = cron;
/*     */   }
/*     */   public String getOperation() {
/* 104 */     return this.operation;
/*     */   }
/*     */   public void setOperation(String operation) {
/* 107 */     this.operation = operation;
/*     */   }
/*     */   public String getComments() {
/* 110 */     return this.comments;
/*     */   }
/*     */   public void setComments(String comments) {
/* 113 */     this.comments = comments;
/*     */   }
/*     */   public String getStatus() {
/* 116 */     return this.status;
/*     */   }
/*     */   public void setStatus(String status) {
/* 119 */     this.status = status;
/*     */   }
/*     */   public String getUpdateUser() {
/* 122 */     return this.updateUser;
/*     */   }
/*     */   public void setUpdateUser(String updateUser) {
/* 125 */     this.updateUser = updateUser;
/*     */   }
/*     */   public String getQualification() {
/* 128 */     return this.qualification;
/*     */   }
/*     */   public void setQualification(String qualification) {
/* 131 */     this.qualification = qualification;
/*     */   }
/*     */ }

/* Location:           C:\Users\renfei\Desktop\treeDMS-2.3.1\treeDMS-2.3.1\webapps\treesoft\WEB-INF\classes\
 * Qualified Name:     org.springframework.base.system.entity.Task
 * JD-Core Version:    0.6.0
 */