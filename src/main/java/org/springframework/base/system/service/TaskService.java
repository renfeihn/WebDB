/*    */ package org.springframework.base.system.service;
/*    */ 
/*    */ import java.util.List;
/*    */ import java.util.Map;
/*    */ import org.springframework.base.common.persistence.Page;
/*    */ import org.springframework.base.system.dao.TaskDao;
/*    */ import org.springframework.base.system.entity.Task;
/*    */ import org.springframework.beans.factory.annotation.Autowired;
/*    */ import org.springframework.stereotype.Service;
/*    */ 
/*    */ @Service
/*    */ public class TaskService
/*    */ {
/*    */ 
/*    */   @Autowired
/*    */   private TaskDao taskDao;
/*    */ 
/*    */   public Page<Map<String, Object>> taskList(Page<Map<String, Object>> page)
/*    */     throws Exception
/*    */   {
/* 28 */     return this.taskDao.taskList(page);
/*    */   }
/*    */ 
/*    */   public boolean deleteTask(String[] ids) throws Exception {
/* 32 */     return this.taskDao.deleteTask(ids);
/*    */   }
/*    */ 
/*    */   public Map<String, Object> getTask(String id) throws Exception {
/* 36 */     return this.taskDao.getTask(id);
/*    */   }
/*    */ 
/*    */   public Map<String, Object> getTaskById2(String id) throws Exception {
/* 40 */     return this.taskDao.getTaskById2(id);
/*    */   }
/*    */ 
/*    */   public boolean taskUpdate(Task task) throws Exception {
/* 44 */     return this.taskDao.taskUpdate(task);
/*    */   }
/*    */ 
/*    */   public boolean taskUpdateStatus(String taskId, String status)
/*    */   {
/* 49 */     return this.taskDao.taskUpdateStatus(taskId, status);
/*    */   }
/*    */ 
/*    */   public List<Map<String, Object>> getTaskListById(String[] ids) throws Exception {
/* 53 */     return this.taskDao.getTaskListById(ids);
/*    */   }
/*    */ 
/*    */   public Page<Map<String, Object>> taskLogList(Page<Map<String, Object>> page, String taskId)
/*    */     throws Exception
/*    */   {
/* 63 */     return this.taskDao.taskLogList(page, taskId);
/*    */   }
/*    */ 
/*    */   public List<Map<String, Object>> getTaskList2(String state)
/*    */   {
/* 68 */     return this.taskDao.getTaskList2(state);
/*    */   }
/*    */ 
/*    */   public boolean deleteTaskLog(String[] ids) throws Exception {
/* 72 */     return this.taskDao.deleteTaskLog(ids);
/*    */   }
/*    */ 
/*    */   public boolean taskLogSave(String status, String comments, String taskId)
/*    */   {
/* 77 */     return this.taskDao.taskLogSave(status, comments, taskId);
/*    */   }
/*    */ 
/*    */   public boolean deleteTaskLogByDS(String id) throws Exception
/*    */   {
/* 82 */     return this.taskDao.deleteTaskLogByDS(id);
/*    */   }
/*    */ }

/* Location:           C:\Users\renfei\Desktop\treeDMS-2.3.1\treeDMS-2.3.1\webapps\treesoft\WEB-INF\classes\
 * Qualified Name:     org.springframework.base.system.service.TaskService
 * JD-Core Version:    0.6.0
 */