/*     */ package org.springframework.base.system.entity;
/*     */ 
/*     */ import java.io.Serializable;
/*     */ 
/*     */ public class ScheduleJob
/*     */   implements Serializable
/*     */ {
/*     */   private static final long serialVersionUID = 1L;
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
/*     */ 
/*     */   public String getStatus()
/*     */   {
/*  46 */     return this.status;
/*     */   }
/*     */ 
/*     */   public String getId() {
/*  50 */     return this.id;
/*     */   }
/*     */ 
/*     */   public void setId(String id) {
/*  54 */     this.id = id;
/*     */   }
/*     */ 
/*     */   public String getName() {
/*  58 */     return this.name;
/*     */   }
/*     */ 
/*     */   public void setName(String name) {
/*  62 */     this.name = name;
/*     */   }
/*     */ 
/*     */   public String getCreateDate() {
/*  66 */     return this.createDate;
/*     */   }
/*     */ 
/*     */   public void setCreateDate(String createDate) {
/*  70 */     this.createDate = createDate;
/*     */   }
/*     */ 
/*     */   public String getUpdateDate() {
/*  74 */     return this.updateDate;
/*     */   }
/*     */ 
/*     */   public void setUpdateDate(String updateDate) {
/*  78 */     this.updateDate = updateDate;
/*     */   }
/*     */ 
/*     */   public String getSouceConfigId() {
/*  82 */     return this.souceConfigId;
/*     */   }
/*     */ 
/*     */   public void setSouceConfigId(String souceConfigId) {
/*  86 */     this.souceConfigId = souceConfigId;
/*     */   }
/*     */ 
/*     */   public String getSouceDataBase() {
/*  90 */     return this.souceDataBase;
/*     */   }
/*     */ 
/*     */   public void setSouceDataBase(String souceDataBase) {
/*  94 */     this.souceDataBase = souceDataBase;
/*     */   }
/*     */ 
/*     */   public String getDoSql() {
/*  98 */     return this.doSql;
/*     */   }
/*     */ 
/*     */   public void setDoSql(String doSql) {
/* 102 */     this.doSql = doSql;
/*     */   }
/*     */ 
/*     */   public String getTargetConfigId() {
/* 106 */     return this.targetConfigId;
/*     */   }
/*     */ 
/*     */   public void setTargetConfigId(String targetConfigId) {
/* 110 */     this.targetConfigId = targetConfigId;
/*     */   }
/*     */ 
/*     */   public String getTargetDataBase() {
/* 114 */     return this.targetDataBase;
/*     */   }
/*     */ 
/*     */   public void setTargetDataBase(String targetDataBase) {
/* 118 */     this.targetDataBase = targetDataBase;
/*     */   }
/*     */ 
/*     */   public String getTargetTable() {
/* 122 */     return this.targetTable;
/*     */   }
/*     */ 
/*     */   public void setTargetTable(String targetTable) {
/* 126 */     this.targetTable = targetTable;
/*     */   }
/*     */ 
/*     */   public String getCron() {
/* 130 */     return this.cron;
/*     */   }
/*     */ 
/*     */   public void setCron(String cron) {
/* 134 */     this.cron = cron;
/*     */   }
/*     */ 
/*     */   public String getOperation() {
/* 138 */     return this.operation;
/*     */   }
/*     */ 
/*     */   public void setOperation(String operation) {
/* 142 */     this.operation = operation;
/*     */   }
/*     */ 
/*     */   public String getComments() {
/* 146 */     return this.comments;
/*     */   }
/*     */ 
/*     */   public void setComments(String comments) {
/* 150 */     this.comments = comments;
/*     */   }
/*     */ 
/*     */   public String getState() {
/* 154 */     return this.state;
/*     */   }
/*     */ 
/*     */   public void setState(String state) {
/* 158 */     this.state = state;
/*     */   }
/*     */ 
/*     */   public String getUpdateUser() {
/* 162 */     return this.updateUser;
/*     */   }
/*     */ 
/*     */   public void setUpdateUser(String updateUser) {
/* 166 */     this.updateUser = updateUser;
/*     */   }
/*     */ 
/*     */   public void setStatus(String status) {
/* 170 */     this.status = status;
/*     */   }
/*     */ }

/* Location:           C:\Users\renfei\Desktop\treeDMS-2.3.1\treeDMS-2.3.1\webapps\treesoft\WEB-INF\classes\
 * Qualified Name:     org.springframework.base.system.entity.ScheduleJob
 * JD-Core Version:    0.6.0
 */