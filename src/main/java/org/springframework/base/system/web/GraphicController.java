/*     */ package org.springframework.base.system.web;
/*     */ 
/*     */ import java.io.PrintStream;
/*     */ import java.util.ArrayList;
/*     */ import java.util.HashMap;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ import javax.servlet.http.HttpServletRequest;
/*     */ import org.springframework.base.common.web.BaseController;
/*     */ import org.springframework.base.system.service.GraphicService;
/*     */ import org.springframework.base.system.service.PermissionService;
/*     */ import org.springframework.beans.factory.annotation.Autowired;
/*     */ import org.springframework.stereotype.Controller;
/*     */ import org.springframework.web.bind.annotation.PathVariable;
/*     */ import org.springframework.web.bind.annotation.RequestMapping;
/*     */ import org.springframework.web.bind.annotation.ResponseBody;
/*     */ 
/*     */ @Controller
/*     */ @RequestMapping({"system/graphic"})
/*     */ public class GraphicController extends BaseController
/*     */ {
/*     */ 
/*     */   @Autowired
/*     */   private PermissionService permissionService;
/*     */ 
/*     */   @Autowired
/*     */   private GraphicService graphicService;
/*     */ 
/*     */   @RequestMapping(value={"graphicPage/{databaseName}/{databaseConfigId}/{temp}"}, method={org.springframework.web.bind.annotation.RequestMethod.GET})
/*     */   public String config(@PathVariable("databaseName") String databaseName, @PathVariable("databaseConfigId") String databaseConfigId, @PathVariable("temp") String temp, HttpServletRequest request)
/*     */     throws Exception
/*     */   {
/*  56 */     request.setAttribute("databaseName", databaseName);
/*  57 */     request.setAttribute("databaseConfigId", databaseConfigId);
/*  58 */     request.setAttribute("temp", temp);
/*  59 */     return "system/graphicPage";
/*     */   }
/*     */ 
/*     */   @RequestMapping(value={"getViewData/{databaseConfigId}"}, method={org.springframework.web.bind.annotation.RequestMethod.POST})
/*     */   @ResponseBody
/*     */   public Map<String, Object> getViewData(HttpServletRequest request, @PathVariable("databaseConfigId") String databaseConfigId) throws Exception {
/*  67 */     String sql = request.getParameter("sql");
/*  68 */     String databaseName = request.getParameter("databaseName");
/*  69 */     String limitForm = request.getParameter("limitForm");
/*  70 */     String pageSize = request.getParameter("pageSize");
/*     */ 
/*  73 */     Map map0 = this.permissionService.getConfig(databaseConfigId);
/*     */ 
/*  76 */     String databaseType = (String)map0.get("databaseType");
/*  77 */     String mess = "";
/*  78 */     String status = "";
/*     */ 
/*  81 */     List dataList = new ArrayList();
/*     */     try {
/*  83 */       if (databaseType.equals("MySql")) {
/*  84 */         sql = "select * from (" + sql + ") tab limit " + limitForm + "," + pageSize;
/*  85 */         dataList = this.permissionService.selectAllDataFromSQLForMysql(databaseName, databaseConfigId, sql);
/*     */       }
/*     */ 
/*  88 */       if (databaseType.equals("MariaDB")) {
/*  89 */         sql = "select * from (" + sql + ") tab limit " + limitForm + "," + pageSize;
/*  90 */         dataList = this.permissionService.selectAllDataFromSQLForMysql(databaseName, databaseConfigId, sql);
/*     */       }
/*     */ 
/*  93 */       if (databaseType.equals("Oracle")) {
/*  94 */         dataList = this.permissionService.selectAllDataFromSQLForOracle(databaseName, databaseConfigId, sql);
/*     */       }
/*     */ 
/*  97 */       if (databaseType.equals("PostgreSQL")) {
/*  98 */         dataList = this.permissionService.selectAllDataFromSQLForPostgreSQL(databaseName, databaseConfigId, sql);
/*     */       }
/*     */ 
/* 101 */       if (databaseType.equals("MSSQL")) {
/* 102 */         dataList = this.permissionService.selectAllDataFromSQLForMSSQL(databaseName, databaseConfigId, sql);
/*     */       }
/*     */ 
/* 105 */       if (databaseType.equals("Hive2")) {
/* 106 */         dataList = this.permissionService.selectAllDataFromSQLForHive2(databaseName, databaseConfigId, sql);
/*     */       }
/*     */ 
/* 109 */       if (databaseType.equals("MongoDB")) {
/* 110 */         dataList = this.permissionService.selectAllDataFromSQLForMongoDB(databaseName, databaseConfigId, sql);
/*     */       }
/*     */ 
/* 116 */       mess = "查询数据成功";
/* 117 */       status = "success";
/*     */     } catch (Exception e) {
/* 119 */       mess = "查询数据出错!" + e.getMessage();
/* 120 */       status = "fail";
/* 121 */       System.out.println("查询数据失败 " + e.getMessage());
/*     */     }
/*     */ 
/* 124 */     Map map = new HashMap();
/* 125 */     map.put("mess", mess);
/* 126 */     map.put("status", status);
/* 127 */     map.put("rows", dataList);
/* 128 */     return map;
/*     */   }
/*     */ }

/* Location:           C:\Users\renfei\Desktop\treeDMS-2.3.1\treeDMS-2.3.1\webapps\treesoft\WEB-INF\classes\
 * Qualified Name:     org.springframework.base.system.web.GraphicController
 * JD-Core Version:    0.6.0
 */