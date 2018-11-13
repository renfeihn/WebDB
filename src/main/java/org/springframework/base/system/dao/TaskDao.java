/*     */ package org.springframework.base.system.dao;
/*     */ 
/*     */ import java.io.PrintStream;
/*     */ import java.util.ArrayList;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ import org.apache.commons.lang3.StringEscapeUtils;
/*     */ import org.springframework.base.common.persistence.Page;
/*     */ import org.springframework.base.common.utils.DateUtils;
/*     */ import org.springframework.base.system.entity.Task;
/*     */ import org.springframework.base.system.utils.DBUtil;
/*     */ import org.springframework.stereotype.Repository;
/*     */ 
/*     */ @Repository
/*     */ public class TaskDao
/*     */ {
/*     */   public Page<Map<String, Object>> taskList(Page<Map<String, Object>> page)
/*     */     throws Exception
/*     */   {
/*  32 */     int pageNo = page.getPageNo();
/*  33 */     int pageSize = page.getPageSize();
/*  34 */     int limitFrom = (pageNo - 1) * pageSize;
/*     */ 
/*  37 */     DBUtil db1 = new DBUtil();
/*     */ 
/*  39 */     String sql2 = " select t1.id, t1.state , t1.name, t1.createDate,t1.updateDate ,t1.souceConfig_id as souceConfigId , t1.souceDataBase, t1.doSql ,t1.targetConfig_id as targetConfigId, t1.targetDataBase,t1.targetTable, t1.cron, t1.operation, t1.comments,t1. status , t2.name||','||t2.ip||':'||t2.port as souceConfig , t3.ip||':'||t3.port as targetConfig from  treesoft_task t1 left join treesoft_config t2 on t1.souceConfig_id = t2.id LEFT JOIN treesoft_config t3 on t1.targetConfig_id = t3.id ";
/*  40 */     int rowCount = db1.executeQueryForCount(sql2);
/*  41 */     sql2 = sql2 + "  limit " + limitFrom + "," + pageSize;
/*  42 */     List list = db1.executeQuery(sql2);
/*  43 */     page.setTotalCount(rowCount);
/*  44 */     page.setResult(list);
/*  45 */     return page;
/*     */   }
/*     */ 
/*     */   public List<Map<String, Object>> getTaskListById(String[] ids) throws Exception {
/*  49 */     DBUtil du = new DBUtil();
/*  50 */     List list = du.getTaskListById(ids);
/*  51 */     return list;
/*     */   }
/*     */ 
/*     */   public boolean deleteTask(String[] ids) throws Exception {
/*  55 */     DBUtil db = new DBUtil();
/*  56 */     StringBuffer sb = new StringBuffer();
/*     */ 
/*  58 */     for (int i = 0; i < ids.length; i++) {
/*  59 */       sb = sb.append("'" + ids[i] + "',");
/*     */     }
/*  61 */     String newStr = sb.toString();
/*  62 */     String str3 = newStr.substring(0, newStr.length() - 1);
/*  63 */     String sql = "  delete  from  treesoft_task  where id in (" + str3 + ")";
/*  64 */     boolean bl = db.do_update(sql);
/*  65 */     return bl;
/*     */   }
/*     */ 
/*     */   public boolean taskUpdate(Task task)
/*     */     throws Exception
/*     */   {
/*  71 */     DBUtil db = new DBUtil();
/*     */ 
/*  73 */     String id = task.getId();
/*  74 */     String sql = "";
/*     */ 
/*  76 */     String status = task.getStatus();
/*  77 */     if ((status == null) || (status.equals(""))) {
/*  78 */       status = "0";
/*     */     }
/*     */ 
/*  82 */     String doSql = task.getDoSql();
/*  83 */     doSql = doSql.replaceAll("'", "''");
/*     */ 
/*  85 */     doSql = doSql.replaceAll(";", "");
/*  86 */     doSql = StringEscapeUtils.unescapeHtml4(doSql);
/*     */ 
/*  88 */     task.setDoSql(doSql);
/*     */ 
/*  90 */     if (!id.equals(""))
/*  91 */       sql = " update treesoft_task  set name='" + 
/*  92 */         task.getName() + "' ," + 
/*  93 */         "souceConfig_id='" + task.getSouceConfigId() + "' ," + 
/*  94 */         "souceDataBase='" + task.getSouceDataBase() + "', " + 
/*  95 */         "doSql='" + task.getDoSql() + "', " + 
/*  96 */         "targetConfig_id='" + task.getTargetConfigId() + "', " + 
/*  97 */         "targetDataBase ='" + task.getTargetDataBase() + "', " + 
/*  98 */         "targetTable='" + task.getTargetTable() + "', " + 
/*  99 */         "cron='" + task.getCron() + "', " + 
/* 100 */         "status='" + status + "', " + 
/* 101 */         "state='" + task.getState() + "', " + 
/* 102 */         "qualification='" + task.getQualification() + "', " + 
/* 103 */         "comments='" + task.getComments() + "', " + 
/* 104 */         "operation='" + task.getOperation() + 
/* 105 */         "'  where id='" + id + "'";
/*     */     else {
/* 107 */       sql = " insert into treesoft_task ( name, createDate,updateDate ,souceConfig_id,souceDataBase, doSql ,targetConfig_id ,targetDataBase,targetTable, cron,operation,comments,status ,qualification ,state ) values ( '" + 
/* 109 */         task.getName() + "','" + 
/* 110 */         DateUtils.getDateTime() + "','" + 
/* 111 */         DateUtils.getDateTime() + "','" + 
/* 112 */         task.getSouceConfigId() + "','" + 
/* 113 */         task.getSouceDataBase() + "','" + 
/* 114 */         task.getDoSql() + "','" + 
/* 115 */         task.getTargetConfigId() + "','" + 
/* 116 */         task.getTargetDataBase() + "','" + 
/* 118 */         task.getTargetTable() + "','" + 
/* 119 */         task.getCron() + "','" + 
/* 120 */         task.getOperation() + "','" + 
/* 121 */         task.getComments() + "','" + 
/* 122 */         status + "','" + 
/* 123 */         task.getQualification() + "','" + 
/* 124 */         task.getState() + "' ) ";
/*     */     }
/*     */ 
/* 128 */     boolean bl = db.do_update(sql);
/*     */ 
/* 130 */     return bl;
/*     */   }
/*     */ 
/*     */   public boolean taskUpdateStatus(String taskId, String status) {
/* 134 */     DBUtil db = new DBUtil();
/* 135 */     boolean bl = true;
/*     */     try {
/* 137 */       String sql = "update treesoft_task set status='" + 
/* 138 */         status + "' " + 
/* 139 */         " where id='" + taskId + "'";
/*     */ 
/* 142 */       bl = db.do_update2(sql);
/*     */     }
/*     */     catch (Exception e) {
/* 145 */       e.printStackTrace();
/*     */     }
/* 147 */     return bl;
/*     */   }
/*     */ 
/*     */   public Map<String, Object> getTask(String id) {
/* 151 */     DBUtil db = new DBUtil();
/* 152 */     String sql = " select id, name, souceConfig_id as souceConfigId ,souceDataBase, doSql,targetConfig_id as targetConfigId, targetDataBase, targetTable,cron, operation,comments ,status ,state,qualification from  treesoft_task where id='" + id + "'";
/* 153 */     List list = db.executeQuery(sql);
/* 154 */     Map map = (Map)list.get(0);
/* 155 */     return map;
/*     */   }
/*     */ 
/*     */   public Map<String, Object> getTaskById2(String id) {
/* 159 */     DBUtil db = new DBUtil();
/* 160 */     String sql = " select id, name, souceConfig_id as souceConfigId,souceDataBase, doSql ,targetConfig_id as targetConfigId, targetDataBase,targetTable, cron,operation,comments,status,state ,qualification from  treesoft_task where id='" + id + "'";
/* 161 */     List list = db.executeQuery(sql);
/* 162 */     Map map = (Map)list.get(0);
/* 163 */     return map;
/*     */   }
/*     */ 
/*     */   public Page<Map<String, Object>> taskLogList(Page<Map<String, Object>> page, String taskId)
/*     */     throws Exception
/*     */   {
/* 173 */     int pageNo = page.getPageNo();
/* 174 */     int pageSize = page.getPageSize();
/* 175 */     int limitFrom = (pageNo - 1) * pageSize;
/*     */ 
/* 179 */     DBUtil db1 = new DBUtil();
/*     */ 
/* 181 */     String sql = " select id, createDate, status ,comments  from  treesoft_task_log where task_id ='" + taskId + "' order by createdate desc ";
/*     */ 
/* 183 */     int rowCount = db1.executeQueryForCount(sql);
/*     */ 
/* 185 */     sql = sql + "  limit " + limitFrom + "," + pageSize;
/* 186 */     List list = db1.executeQuery(sql);
/* 187 */     page.setTotalCount(rowCount);
/* 188 */     page.setResult(list);
/* 189 */     return page;
/*     */   }
/*     */ 
/*     */   public List<Map<String, Object>> getTaskList2(String state)
/*     */   {
/* 194 */     List list = new ArrayList();
/*     */     try {
/* 196 */       DBUtil du = new DBUtil();
/* 197 */       list = du.getTaskList2(state);
/* 198 */       return list;
/*     */     } catch (Exception e) {
/* 200 */       System.out.println("error= " + e.getMessage());
/*     */ 
/* 202 */       e.printStackTrace();
/*     */     }
/* 204 */     return list;
/*     */   }
/*     */ 
/*     */   public boolean deleteTaskLog(String[] ids) throws Exception
/*     */   {
/* 209 */     DBUtil db = new DBUtil();
/* 210 */     StringBuffer sb = new StringBuffer();
/*     */ 
/* 212 */     for (int i = 0; i < ids.length; i++) {
/* 213 */       sb = sb.append("'" + ids[i] + "',");
/*     */     }
/* 215 */     String newStr = sb.toString();
/* 216 */     String str3 = newStr.substring(0, newStr.length() - 1);
/* 217 */     String sql = "  delete  from  treesoft_task_log  where id in (" + str3 + ")";
/* 218 */     boolean bl = db.do_update(sql);
/* 219 */     return bl;
/*     */   }
/*     */ 
/*     */   public boolean deleteTaskLogByDS(String id) throws Exception {
/* 223 */     DBUtil db = new DBUtil();
/* 224 */     String sql = "  delete  from  treesoft_task_log  where task_id ='" + id + "'";
/* 225 */     boolean bl = db.do_update(sql);
/* 226 */     return bl;
/*     */   }
/*     */ 
/*     */   public boolean taskLogSave(String status, String comments, String taskId)
/*     */   {
/* 231 */     DBUtil db = new DBUtil();
/* 232 */     boolean bl = true;
/*     */ 
/* 234 */     String comments2 = comments.replaceAll("'", "''");
/*     */     try {
/* 236 */       String sql = " insert into treesoft_task_log ( createDate, status ,comments, task_id ) values ( '" + 
/* 237 */         DateUtils.getDateTime() + "','" + 
/* 238 */         status + "','" + 
/* 239 */         comments2 + "','" + 
/* 240 */         taskId + "')";
/*     */ 
/* 242 */       bl = db.do_update2(sql);
/*     */     } catch (Exception e) {
/* 244 */       System.out.println(e.getMessage());
/* 245 */       e.printStackTrace();
/*     */     }
/* 247 */     return bl;
/*     */   }
/*     */ }

/* Location:           C:\Users\renfei\Desktop\treeDMS-2.3.1\treeDMS-2.3.1\webapps\treesoft\WEB-INF\classes\
 * Qualified Name:     org.springframework.base.system.dao.TaskDao
 * JD-Core Version:    0.6.0
 */