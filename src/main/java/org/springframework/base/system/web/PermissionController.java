//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package org.springframework.base.system.web;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Map.Entry;
import javax.annotation.Resource;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.apache.log4j.Logger;
import org.apache.tools.ant.Project;
import org.apache.tools.ant.taskdefs.Expand;
import org.apache.tools.ant.taskdefs.Zip;
import org.apache.tools.ant.types.FileSet;
import org.springframework.base.common.persistence.Page;
import org.springframework.base.common.utils.StringUtils;
import org.springframework.base.common.web.BaseController;
import org.springframework.base.system.entity.Config;
import org.springframework.base.system.entity.DataSynchronize;
import org.springframework.base.system.scheduler.QuartzJobFactory;
import org.springframework.base.system.scheduler.QuartzManager;
import org.springframework.base.system.service.PermissionService;
import org.springframework.base.system.service.PersonService;
import org.springframework.base.system.utils.ComputerMonitorUtil;
import org.springframework.base.system.utils.ExportExcelUtils;
import org.springframework.base.system.utils.FileUtil;
import org.springframework.base.system.utils.LogUtil;
import org.springframework.base.system.utils.NetworkUtil;
import org.springframework.base.system.web.IdsDto;
import org.springframework.base.system.web.TempDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.util.HtmlUtils;

@Controller
@RequestMapping({"system/permission"})
public class PermissionController extends BaseController {
    @Autowired
    private PermissionService permissionService;
    @Autowired
    private PersonService personService;
    @Resource
    private QuartzManager quartzManager;
    Logger logger = Logger.getLogger(PermissionController.class);
    List<String> tempFileList = new ArrayList();

    public PermissionController() {
    }

    @RequestMapping({"i/allDatabaseList"})
    @ResponseBody
    public List<Map<String, Object>> allDatabaseList(HttpServletRequest request) throws Exception {
        new ArrayList();
        HttpSession session = request.getSession(true);
        String username = (String)session.getAttribute("LOGIN_USER_NAME");
        List userList = this.permissionService.selectUserByName(username);
        String datascope = "" + ((Map)userList.get(0)).get("datascope");

        try {
            List listDb = this.permissionService.getAllDataBaseById(datascope);
            return listDb;
        } catch (Exception var8) {
            var8.printStackTrace();
            System.out.println("取得配置的全部数据库 服务器信息出错，" + var8.getMessage());
            LogUtil.e("取得配置的全部数据库 服务器信息出错，" + var8.getMessage());
            return null;
        }
    }

    @RequestMapping(
            value = {"i/allTableAndColumn/{databaseName}/{databaseConfigId}"},
            method = {RequestMethod.GET}
    )
    @ResponseBody
    public List<Map<String, Object>> allTableAndColumn(@PathVariable("databaseName") String databaseName, @PathVariable("databaseConfigId") String databaseConfigId) throws Exception {
        String column_name = "";
        String dbName = databaseName;
        ArrayList resultListObject = new ArrayList();
        Object listTable = new ArrayList();
        new HashMap();
        Object listTableColumn = new ArrayList();
        new HashMap();
        Object listView = new ArrayList();
        Map map = this.permissionService.getConfig(databaseConfigId);
        String databaseType = (String)map.get("databaseType");

        try {
            if(databaseType.equals("MySql")) {
                listTable = this.permissionService.getAllTables(dbName, databaseConfigId);
            }

            if(databaseType.equals("MariaDB")) {
                listTable = this.permissionService.getAllTables(dbName, databaseConfigId);
            }

            if(databaseType.equals("Oracle")) {
                listTable = this.permissionService.getAllTablesForOracle(dbName, databaseConfigId);
            }

            if(databaseType.equals("PostgreSQL")) {
                listTable = this.permissionService.getAllTablesForPostgreSQL(dbName, databaseConfigId);
            }

            if(databaseType.equals("MSSQL")) {
                listTable = this.permissionService.getAllTablesForMSSQL(dbName, databaseConfigId);
            }

            if(databaseType.equals("MongoDB")) {
                listTable = this.permissionService.getAllTablesForMongoDB(dbName, databaseConfigId);
            }
        } catch (Exception var19) {
            var19.printStackTrace();
            this.logger.debug(var19.getMessage());
            return null;
        }

        Map tempObject;
        int y;
        for(y = 0; y < ((List)listTable).size(); ++y) {
            tempObject = (Map)((List)listTable).get(y);
            String tempViewMap = (String)tempObject.get("TABLE_NAME");
            HashMap viewName = new HashMap();
            if(databaseType.equals("MySql")) {
                listTableColumn = this.permissionService.getTableColumns3(databaseName, tempViewMap, databaseConfigId);
            }

            if(databaseType.equals("MariaDB")) {
                listTableColumn = this.permissionService.getTableColumns3(databaseName, tempViewMap, databaseConfigId);
            }

            if(databaseType.equals("Oracle")) {
                listTableColumn = this.permissionService.getTableColumns3ForOracle(databaseName, tempViewMap, databaseConfigId);
            }

            if(databaseType.equals("PostgreSQL")) {
                listTableColumn = this.permissionService.getTableColumns3ForPostgreSQL(databaseName, tempViewMap, databaseConfigId);
            }

            if(databaseType.equals("MSSQL")) {
                listTableColumn = this.permissionService.getTableColumns3ForMSSQL(databaseName, tempViewMap, databaseConfigId);
            }

            ArrayList tempColumnsListObject = new ArrayList();

            for(int z = 0; z < ((List)listTableColumn).size(); ++z) {
                Map tempMap = (Map)((List)listTableColumn).get(z);
                column_name = (String)tempMap.get("COLUMN_NAME");
                HashMap tempColumnsMap = new HashMap();
                tempColumnsMap.put("text", column_name);
                tempColumnsMap.put("displayText", column_name);
                tempColumnsListObject.add(tempColumnsMap);
            }

            viewName.put("text", tempViewMap);
            viewName.put("columns", tempColumnsListObject);
            resultListObject.add(viewName);
        }

        if(databaseType.equals("MySql")) {
            listView = this.permissionService.getAllViews(databaseName, databaseConfigId);
        }

        if(databaseType.equals("MariaDB")) {
            listView = this.permissionService.getAllViews(databaseName, databaseConfigId);
        }

        if(databaseType.equals("Oracle")) {
            listView = this.permissionService.getAllViewsForOracle(databaseName, databaseConfigId);
        }

        if(databaseType.equals("PostgreSQL")) {
            listView = this.permissionService.getAllViewsForPostgreSQL(databaseName, databaseConfigId);
        }

        if(databaseType.equals("MSSQL")) {
            listView = this.permissionService.getAllViewsForMSSQL(databaseName, databaseConfigId);
        }

        for(y = 0; y < ((List)listView).size(); ++y) {
            HashMap var20 = new HashMap();
            tempObject = (Map)((List)listView).get(y);
            String var21 = (String)tempObject.get("TABLE_NAME");
            var20.put("text", var21);
            var20.put("columns", new ArrayList());
            resultListObject.add(var20);
        }

        return resultListObject;
    }

    @RequestMapping(
            value = {"i/databaseList/{databaseConfigId}"},
            method = {RequestMethod.GET}
    )
    @ResponseBody
    public List<Map<String, Object>> databaseList(@PathVariable("databaseConfigId") String databaseConfigId, HttpServletRequest request) throws Exception {
        ArrayList listAll = new ArrayList();
        Object listTable = new ArrayList();
        Object listView = new ArrayList();
        Object listFunction = new ArrayList();
        new ArrayList();
        Map map = this.permissionService.getConfig(databaseConfigId);
        String databaseType = (String)map.get("databaseType");
        String databaseName = (String)map.get("databaseName");
        String userName = (String)map.get("userName");
        Object listDb = new ArrayList();

        try {
            if(databaseType.equals("MySql")) {
                listDb = this.permissionService.getAllDataBase(databaseConfigId);
            }

            if(databaseType.equals("MariaDB")) {
                listDb = this.permissionService.getAllDataBase(databaseConfigId);
            }

            if(databaseType.equals("Oracle")) {
                listDb = this.permissionService.getAllDataBaseForOracle(databaseConfigId);
            }

            if(databaseType.equals("PostgreSQL")) {
                listDb = this.permissionService.getAllDataBaseForPostgreSQL(databaseConfigId);
            }

            if(databaseType.equals("MSSQL")) {
                if(userName.equals("sa")) {
                    listDb = this.permissionService.getAllDataBaseForMSSQL(databaseConfigId);
                } else {
                    HashMap id = new HashMap();
                    id.put("SCHEMA_NAME", databaseName);
                    ((List)listDb).add(id);
                }
            }

            if(databaseType.equals("MongoDB")) {
                listDb = this.permissionService.getAllDataBaseForMongoDB(databaseConfigId);
            }

            if(databaseType.equals("Hive2")) {
                listDb = this.permissionService.getAllDataBaseForHive2(databaseConfigId);
            }
        } catch (Exception var32) {
            var32.printStackTrace();
            return null;
        }

        int var33 = 0;
        boolean pid = false;
        boolean cpid = false;

        for(int i = 0; i < ((List)listDb).size(); ++i) {
            HashMap tempObject = new HashMap();
            Map map3 = (Map)((List)listDb).get(i);
            String dbName = "" + map3.get("SCHEMA_NAME");
            new HashMap();
            String column_name = "";
            String data_type = "";
            String precision = "";
            new HashMap();
            ++var33;
            tempObject.put("id", Integer.valueOf(var33));
            tempObject.put("name", dbName);
            tempObject.put("type", "db");
            tempObject.put("icon", "icon-hamburg-database");
            if(!dbName.equals(databaseName)) {
                tempObject.put("state", "closed");
            }

            listAll.add(tempObject);
            int var34 = var33;
            HashMap tempObject2 = new HashMap();
            ++var33;
            tempObject2.put("id", Integer.valueOf(var33));
            tempObject2.put("pid", Integer.valueOf(var34));
            tempObject2.put("name", "表");
            tempObject2.put("icon", "icon-berlin-billing");
            tempObject2.put("type", "direct");
            listAll.add(tempObject2);
            HashMap tempObject3 = new HashMap();
            ++var33;
            tempObject3.put("id", Integer.valueOf(var33));
            tempObject3.put("pid", Integer.valueOf(var34));
            tempObject3.put("name", "视图");
            tempObject3.put("icon", "icon-berlin-address");
            tempObject3.put("type", "direct");
            listAll.add(tempObject3);
            HashMap tempObject4 = new HashMap();
            ++var33;
            tempObject4.put("id", Integer.valueOf(var33));
            tempObject4.put("pid", Integer.valueOf(var34));
            tempObject4.put("name", "函数");
            tempObject4.put("icon", "icon-berlin-address");
            tempObject4.put("type", "direct");
            listAll.add(tempObject4);

            try {
                if(databaseType.equals("MySql")) {
                    listTable = this.permissionService.getAllTables(dbName, databaseConfigId);
                }

                if(databaseType.equals("MariaDB")) {
                    listTable = this.permissionService.getAllTables(dbName, databaseConfigId);
                }

                if(databaseType.equals("Oracle")) {
                    listTable = this.permissionService.getAllTablesForOracle(dbName, databaseConfigId);
                }

                if(databaseType.equals("PostgreSQL")) {
                    listTable = this.permissionService.getAllTablesForPostgreSQL(dbName, databaseConfigId);
                }

                if(databaseType.equals("MSSQL")) {
                    listTable = this.permissionService.getAllTablesForMSSQL(dbName, databaseConfigId);
                }

                if(databaseType.equals("MongoDB")) {
                    listTable = this.permissionService.getAllTablesForMongoDB(dbName, databaseConfigId);
                }

                if(databaseType.equals("Hive2")) {
                    listTable = this.permissionService.getAllTablesForHive2(dbName, databaseConfigId);
                }
            } catch (Exception var31) {
                var31.printStackTrace();
                return null;
            }

            int y;
            Map var35;
            for(y = 0; y < ((List)listTable).size(); ++y) {
                var35 = (Map)((List)listTable).get(y);
                String tempObjectFunction = (String)var35.get("TABLE_NAME");
                HashMap tempObjectTable = new HashMap();
                ++var33;
                tempObjectTable.put("id", Integer.valueOf(var33));
                tempObjectTable.put("pid", Integer.valueOf(var34 + 1));
                tempObjectTable.put("name", tempObjectFunction);
                tempObjectTable.put("icon", "icon-berlin-calendar");
                tempObjectTable.put("type", "table");
                listAll.add(tempObjectTable);
            }

            if(databaseType.equals("MySql")) {
                listView = this.permissionService.getAllViews(dbName, databaseConfigId);
            }

            if(databaseType.equals("MariaDB")) {
                listView = this.permissionService.getAllViews(dbName, databaseConfigId);
            }

            if(databaseType.equals("Oracle")) {
                listView = this.permissionService.getAllViewsForOracle(dbName, databaseConfigId);
            }

            if(databaseType.equals("PostgreSQL")) {
                listView = this.permissionService.getAllViewsForPostgreSQL(dbName, databaseConfigId);
            }

            if(databaseType.equals("MSSQL")) {
                listView = this.permissionService.getAllViewsForMSSQL(dbName, databaseConfigId);
            }

            if(databaseType.equals("Hive2")) {
                listView = this.permissionService.getAllViewsForHive2(dbName, databaseConfigId);
            }

            HashMap var36;
            for(y = 0; y < ((List)listView).size(); ++y) {
                var35 = (Map)((List)listView).get(y);
                var36 = new HashMap();
                ++var33;
                var36.put("id", Integer.valueOf(var33));
                var36.put("pid", Integer.valueOf(var34 + 2));
                var36.put("name", var35.get("TABLE_NAME"));
                var36.put("icon", "icon-berlin-library");
                var36.put("type", "view");
                listAll.add(var36);
            }

            if(databaseType.equals("MySql")) {
                listFunction = this.permissionService.getAllFuntion(dbName, databaseConfigId);
            }

            if(databaseType.equals("MariaDB")) {
                listFunction = this.permissionService.getAllFuntion(dbName, databaseConfigId);
            }

            if(databaseType.equals("Oracle")) {
                listFunction = this.permissionService.getAllFuntionForOracle(dbName, databaseConfigId);
            }

            if(databaseType.equals("PostgreSQL")) {
                listFunction = this.permissionService.getAllFuntionForPostgreSQL(dbName, databaseConfigId);
            }

            if(databaseType.equals("MSSQL")) {
                listFunction = this.permissionService.getAllFuntionForMSSQL(dbName, databaseConfigId);
            }

            for(y = 0; y < ((List)listFunction).size(); ++y) {
                var35 = (Map)((List)listFunction).get(y);
                var36 = new HashMap();
                ++var33;
                var36.put("id", Integer.valueOf(var33));
                var36.put("pid", Integer.valueOf(var34 + 3));
                var36.put("name", var35.get("ROUTINE_NAME"));
                var36.put("icon", "icon-berlin-settings");
                var36.put("type", "function");
                listAll.add(var36);
            }

            var33 += 100;
        }

        request.setAttribute("databaseName", databaseName);
        return listAll;
    }

    @RequestMapping(
            value = {"i/tableColumns/{tableName}/{dbName}/{databaseConfigId}"},
            method = {RequestMethod.GET}
    )
    @ResponseBody
    public List<Map<String, Object>> tableColumns(@PathVariable("tableName") String tableName, @PathVariable("dbName") String dbName, @PathVariable("databaseConfigId") String databaseConfigId) throws Exception {
        List listAllColumn = this.permissionService.getTableColumns(dbName, tableName, databaseConfigId);
        ArrayList list2 = new ArrayList();

        for(int i = 0; i < listAllColumn.size(); ++i) {
            Map map1 = (Map)listAllColumn.get(i);
            HashMap map2 = new HashMap();
            map2.put("column_name", (String)map1.get("column_name"));
            map2.put("column_key", map1.get("column_key"));
            list2.add(map2);
        }

        return list2;
    }

    @RequestMapping(
            value = {"i/table/{tableName}/{dbName}/{databaseConfigId}"},
            method = {RequestMethod.POST}
    )
    @ResponseBody
    public Map<String, Object> getData(@PathVariable("tableName") String tableName, @PathVariable("dbName") String dbName, @PathVariable("databaseConfigId") String databaseConfigId, HttpServletRequest request) throws Exception {
        Page page = this.getPage(request);
        Map map = this.permissionService.getConfig(databaseConfigId);
        String databaseType = "" + map.get("databaseType");
        HashMap map2 = new HashMap();
        String mess = "";
        String status = "";

        try {
            if(databaseType.equals("MySql")) {
                page = this.permissionService.getData(page, tableName, dbName, databaseConfigId);
                map2.put("operator", "edit");
            }

            if(databaseType.equals("MariaDB")) {
                page = this.permissionService.getData(page, tableName, dbName, databaseConfigId);
                map2.put("operator", "edit");
            }

            if(databaseType.equals("Oracle")) {
                page = this.permissionService.getDataForOracle(page, tableName, dbName, databaseConfigId);
                map2.put("operator", "edit");
            }

            if(databaseType.equals("PostgreSQL")) {
                page = this.permissionService.getDataForPostgreSQL(page, tableName, dbName, databaseConfigId);
                map2.put("operator", "edit");
            }

            if(databaseType.equals("MSSQL")) {
                page = this.permissionService.getDataForMSSQL(page, tableName, dbName, databaseConfigId);
                map2.put("operator", "edit");
            }

            if(databaseType.equals("MongoDB")) {
                page = this.permissionService.getDataForMongoDB(page, tableName, dbName, databaseConfigId);
                map2.put("operator", "edit");
            }

            if(databaseType.equals("Hive2")) {
                page = this.permissionService.getDataForHive2(page, tableName, dbName, databaseConfigId);
                map2.put("operator", "read");
            }

            mess = "执行完成！";
            status = "success";
            map2.put("rows", page.getResult());
            map2.put("total", Long.valueOf(page.getTotalCount()));
            map2.put("columns", page.getColumns());
            map2.put("primaryKey", page.getPrimaryKey());
            map2.put("totalCount", Long.valueOf(page.getTotalCount()));
            map2.put("mess", mess);
            map2.put("status", status);
            return map2;
        } catch (Exception var12) {
            var12.printStackTrace();
            LogUtil.e(var12.getMessage());
            mess = var12.getMessage();
            status = "fail";
            map2.put("mess", mess);
            map2.put("status", status);
            return map2;
        }
    }

    @RequestMapping(
            value = {"i/executeSqlTest/{databaseConfigId}"},
            method = {RequestMethod.POST}
    )
    @ResponseBody
    public Map<String, Object> executeSqlTest(HttpServletRequest request, @PathVariable("databaseConfigId") String databaseConfigId) throws Exception {
        Object map = new HashMap();
        String sql = request.getParameter("sql");
        String databaseName = request.getParameter("databaseName");
        HttpSession session = request.getSession(true);
        String username = (String)session.getAttribute("LOGIN_USER_NAME");
        String ip = NetworkUtil.getIpAddress(request);
        Map map0 = this.permissionService.getConfig(databaseConfigId);
        String databaseType = (String)map0.get("databaseType");
        if(databaseType.equals("MySql")) {
            if(sql.indexOf("select") != 0 && sql.indexOf("SELECT") != 0 && sql.indexOf("show") != 0 && sql.indexOf("SHOW") != 0) {
                map = this.executeSqlNotRes(sql, databaseName, databaseConfigId);
                this.permissionService.saveLog(sql, username, ip);
            } else {
                map = this.executeSqlHaveRes(sql, databaseName, request, databaseConfigId);
            }
        }

        if(databaseType.equals("MariaDB")) {
            if(sql.indexOf("select") != 0 && sql.indexOf("SELECT") != 0 && sql.indexOf("show") != 0 && sql.indexOf("SHOW") != 0) {
                map = this.executeSqlNotRes(sql, databaseName, databaseConfigId);
                this.permissionService.saveLog(sql, username, ip);
            } else {
                map = this.executeSqlHaveRes(sql, databaseName, request, databaseConfigId);
            }
        }

        if(databaseType.equals("Oracle")) {
            if(sql.indexOf("select") != 0 && sql.indexOf("SELECT") != 0 && sql.indexOf("show") != 0 && sql.indexOf("SHOW") != 0) {
                map = this.executeSqlNotRes(sql, databaseName, databaseConfigId);
                this.permissionService.saveLog(sql, username, ip);
            } else {
                map = this.executeSqlHaveResForOracle(sql, databaseName, databaseConfigId, request);
            }
        }

        if(databaseType.equals("PostgreSQL")) {
            if(sql.indexOf("select") != 0 && sql.indexOf("SELECT") != 0 && sql.indexOf("show") != 0 && sql.indexOf("SHOW") != 0) {
                map = this.executeSqlNotRes(sql, databaseName, databaseConfigId);
                this.permissionService.saveLog(sql, username, ip);
            } else {
                map = this.executeSqlHaveResForPostgreSQL(sql, databaseName, databaseConfigId, request);
            }
        }

        if(databaseType.equals("MSSQL")) {
            if(sql.indexOf("select") != 0 && sql.indexOf("SELECT") != 0 && sql.indexOf("show") != 0 && sql.indexOf("SHOW") != 0) {
                map = this.executeSqlNotRes(sql, databaseName, databaseConfigId);
                this.permissionService.saveLog(sql, username, ip);
            } else {
                map = this.executeSqlHaveResForMSSQL(sql, databaseName, databaseConfigId, request);
            }
        }

        if(databaseType.equals("Hive2")) {
            if(sql.indexOf("select") != 0 && sql.indexOf("SELECT") != 0 && sql.indexOf("show") != 0 && sql.indexOf("SHOW") != 0) {
                map = this.executeSqlNotRes(sql, databaseName, databaseConfigId);
                this.permissionService.saveLog(sql, username, ip);
            } else {
                map = this.executeSqlHaveResForHive2(sql, databaseName, databaseConfigId, request);
            }
        }

        if(databaseType.equals("MongoDB")) {
            if(sql.indexOf("db.") != 0 && sql.indexOf("DB.") != 0) {
                ((Map)map).put("mess", "暂未完成MongoDB该shell命令的解析执行!");
                ((Map)map).put("status", "fail");
            } else if(sql.indexOf("find") > 0) {
                map = this.executeSqlHaveResForMongoDB(sql, databaseName, databaseConfigId, request);
            } else if(sql.indexOf("insert") <= 0 && sql.indexOf("remove") <= 0) {
                ((Map)map).put("mess", "暂未完成MongoDB该shell命令的解析执行!");
                ((Map)map).put("status", "fail");
            } else {
                map = this.executeSqlNotResForMongoDB(sql, databaseName, databaseConfigId);
            }
        }

        return (Map)map;
    }

    public Map<String, Object> executeSqlHaveRes(String sql, String dbName, HttpServletRequest request, String databaseConfigId) {
        HashMap map = new HashMap();
        Page page = this.getPage(request);
        String mess = "";
        String status = "";
        Date b1 = new Date();

        try {
            page = this.permissionService.executeSql(page, sql, dbName, databaseConfigId);
            mess = "执行成功！";
            status = "success";
        } catch (Exception var13) {
            var13.printStackTrace();
            mess = var13.getMessage();
            status = "fail";
        }

        Date b2 = new Date();
        long y = b2.getTime() - b1.getTime();
        map.put("rows", page.getResult());
        map.put("total", Long.valueOf(page.getTotalCount()));
        map.put("columns", page.getColumns());
        map.put("primaryKey", page.getPrimaryKey());
        map.put("tableName", page.getTableName());
        map.put("totalCount", Long.valueOf(page.getTotalCount()));
        map.put("time", Long.valueOf(y));
        map.put("mess", mess);
        map.put("status", status);
        return map;
    }

    public Map<String, Object> executeSqlNotRes(String sql, String dbName, String databaseConfigId) {
        String mess = "";
        String status = "";
        Date b1 = new Date();
        int i = 0;

        try {
            i = this.permissionService.executeSqlNotRes(sql, dbName, databaseConfigId);
            mess = "执行成功！";
            status = "success";
        } catch (Exception var12) {
            var12.printStackTrace();
            mess = var12.getMessage();
            status = "fail";
        }

        Date b2 = new Date();
        long y = b2.getTime() - b1.getTime();
        HashMap map = new HashMap();
        map.put("totalCount", Integer.valueOf(i));
        map.put("time", Long.valueOf(y));
        map.put("mess", mess);
        map.put("status", status);
        return map;
    }

    @RequestMapping(
            value = {"i/saveSearchHistory"},
            method = {RequestMethod.POST}
    )
    @ResponseBody
    public Map<String, Object> saveSearchHistory(@RequestBody TempDto tem, HttpServletRequest request) {
        HttpSession session = request.getSession(true);
        String username = (String)session.getAttribute("LOGIN_USER_NAME");
        List userList = this.permissionService.selectUserByName(username);
        String userId = "" + ((Map)userList.get(0)).get("id");
        String id = tem.getId();
        String sql3 = tem.getSql();
        String dbName = tem.getDbName();
        String name = tem.getName();
        sql3 = sql3.replaceAll("\'", "\'\'");
        boolean bool = true;
        String mess = "";
        String status = "";
        if(id != null && !"".equals(id)) {
            bool = this.permissionService.updateSearchHistory(id, name, sql3, dbName);
        } else {
            bool = this.permissionService.saveSearchHistory(name, sql3, dbName, userId);
        }

        if(bool) {
            mess = "修改成功";
            status = "success";
        } else {
            LogUtil.e("SQL保存失败. ");
            mess = "保存失败";
            status = "fail";
        }

        HashMap map = new HashMap();
        map.put("mess", mess);
        map.put("status", status);
        return map;
    }

    @RequestMapping(
            value = {"i/deleteSearchHistory"},
            method = {RequestMethod.POST}
    )
    @ResponseBody
    public Map<String, Object> deleteSearchHistory(@RequestBody TempDto tem, HttpServletRequest request) {
        String id = tem.getId();
        HashMap map = new HashMap();
        String mess = "";
        String status = "";
        boolean bool = true;
        if(id != null || !"".equals(id)) {
            bool = this.permissionService.deleteSearchHistory(id);
        }

        if(bool) {
            mess = "操作成功";
            status = "success";
        } else {
            mess = "操作失败";
            status = "fail";
        }

        map.put("mess", mess);
        map.put("status", status);
        return map;
    }

    @RequestMapping(
            value = {"i/selectSearchHistory"},
            method = {RequestMethod.GET}
    )
    @ResponseBody
    public List<Map<String, Object>> selectSearchHistory() {
        List list = this.permissionService.selectSearchHistory();
        ArrayList list2 = new ArrayList();
        Iterator var4 = list.iterator();

        while(var4.hasNext()) {
            Map map = (Map)var4.next();
            String tempName = (String)map.get("name");
            map.put("name", tempName);
            map.put("pid", "0");
            map.put("icon", "icon-hamburg-zoom");
            list2.add(map);
        }

        return list2;
    }

    @RequestMapping(
            value = {"i/config"},
            method = {RequestMethod.GET}
    )
    public String config(Model model) {
        return "system/configList";
    }

    @RequestMapping(
            value = {"i/configList/{random}"},
            method = {RequestMethod.GET}
    )
    @ResponseBody
    public Map<String, Object> configList(HttpServletRequest request) throws Exception {
        Page page = this.getPage(request);

        try {
            page = this.permissionService.configList(page);
        } catch (Exception var4) {
            var4.printStackTrace();
            return this.getEasyUIData(page);
        }

        return this.getEasyUIData(page);
    }

    @RequestMapping(
            value = {"i/addConfigForm"},
            method = {RequestMethod.GET}
    )
    public String addConfigForm(Model model) {
        return "system/configForm";
    }

    @RequestMapping(
            value = {"i/editConfigForm/{id}"},
            method = {RequestMethod.GET}
    )
    public String editConfigForm(@PathVariable("id") String id, Model model) {
        Object map = new HashMap();

        try {
            map = this.permissionService.getConfig(id);
        } catch (Exception var5) {
            ;
        }

        model.addAttribute("config", map);
        return "system/configForm";
    }

    @RequestMapping(
            value = {"i/configUpdate"},
            method = {RequestMethod.POST}
    )
    @ResponseBody
    public Map<String, Object> configUpdate(@RequestBody Config config, Model model) {
        HashMap map = new HashMap();
        String databaseType = config.getDatabaseType();
        String ip = config.getIp();
        String port = config.getPort();
        String dbName = config.getDatabaseName();
        String url = "";
        if(databaseType.equals("MySql")) {
            url = "jdbc:mysql://" + ip + ":" + port + "/" + dbName;
        }

        if(databaseType.equals("MariaDB")) {
            url = "jdbc:mysql://" + ip + ":" + port + "/" + dbName;
        }

        if(databaseType.equals("Oracle")) {
            url = "jdbc:oracle:thin:@" + ip + ":" + port + ":" + dbName;
        }

        if(databaseType.equals("PostgreSQL")) {
            url = "jdbc:postgresql://" + ip + ":" + port + "/" + dbName;
        }

        if(databaseType.equals("MSSQL")) {
            url = "jdbc:sqlserver://" + ip + ":" + port + ";database=" + dbName;
        }

        config.setUrl(url);
        String mess = "";
        String status = "";

        try {
            this.permissionService.configUpdate(config);
            mess = "修改成功";
            status = "success";
        } catch (Exception var12) {
            var12.printStackTrace();
            mess = "error:" + var12.getMessage();
            status = "fail";
        }

        map.put("mess", mess);
        map.put("status", status);
        return map;
    }

    @RequestMapping(
            value = {"i/changePass"},
            method = {RequestMethod.GET}
    )
    public String changePass(Model model) {
        return "system/changePass";
    }

    @RequestMapping(
            value = {"i/searchHistory"},
            method = {RequestMethod.GET}
    )
    public String searchHistory(Model model) {
        return "system/searchHistory";
    }

    @RequestMapping(
            value = {"i/changePassUpdate"},
            method = {RequestMethod.POST}
    )
    @ResponseBody
    public Map<String, Object> changePassUpdate(@RequestBody TempDto tem, HttpServletRequest request) {
        String mess = "";
        String status = "";
        HttpSession session = request.getSession(true);
        String userByName = (String)session.getAttribute("LOGIN_USER_NAME");
        String oldPass = tem.getOldPass();
        String newPass = tem.getNewPass();
        List list = this.permissionService.selectUserByName(userByName);
        String oldPass2 = "";
        String userId = "";
        if(list.size() > 0) {
            Map map = (Map)list.get(0);
            oldPass2 = (String)map.get("password");
            userId = "" + map.get("id");
        } else {
            mess = "error";
            status = "fail";
        }

        oldPass = StringUtils.MD5(oldPass + "treesoft" + userByName);
        if(!oldPass.equals(oldPass2)) {
            mess = "旧密码不符！";
            status = "fail";
        } else {
            try {
                this.permissionService.changePassUpdate(userId, StringUtils.MD5(newPass + "treesoft" + userByName));
                mess = "修改密码成功";
                status = "success";
            } catch (Exception var13) {
                var13.printStackTrace();
                mess = var13.getMessage();
                status = "fail";
            }
        }

        HashMap map1 = new HashMap();
        map1.put("mess", mess);
        map1.put("status", status);
        return map1;
    }

    @RequestMapping(
            value = {"i/deleteRows/{databaseConfigId}"},
            method = {RequestMethod.POST}
    )
    @ResponseBody
    public Map<String, Object> deleteRows(@RequestBody IdsDto tem, @PathVariable("databaseConfigId") String databaseConfigId, HttpServletRequest request) throws Exception {
        String databaseName = tem.getDatabaseName();
        String tableName = tem.getTableName();
        String primary_key = tem.getPrimary_key();
        String checkedItems = tem.getCheckedItems();
        HttpSession session = request.getSession(true);
        String username = (String)session.getAttribute("LOGIN_USER_NAME");
        String ip = NetworkUtil.getIpAddress(request);
        Map map0 = this.permissionService.getConfig(databaseConfigId);
        String databaseType = (String)map0.get("databaseType");
        ArrayList condition = new ArrayList();
        if(checkedItems != null) {
            JSONArray i = JSONArray.parseArray(checkedItems);

            for(int mess = 0; mess < i.size(); ++mess) {
                Map status = (Map)i.get(mess);
                String map = "";
                if(primary_key != null && !primary_key.equals("")) {
                    String[] var28 = primary_key.split(",");
                    String[] var22 = var28;
                    int var21 = var28.length;

                    for(int var20 = 0; var20 < var21; ++var20) {
                        String var29 = var22[var20];
                        map = map + " and " + var29 + " = \'" + status.get(var29) + "\' ";
                    }
                } else {
                    Iterator primaryKey = status.keySet().iterator();

                    while(primaryKey.hasNext()) {
                        String primaryKeys = (String)primaryKey.next();
                        if(status.get(primaryKeys) != null && !primaryKeys.equals("RN")) {
                            map = map + " and " + primaryKeys + " = \'" + status.get(primaryKeys) + "\' ";
                        }
                    }
                }

                condition.add(map);
            }
        }

        byte var24 = 0;
        String var25 = "";
        String var26 = "";

        try {
            if(databaseType.equals("MySql")) {
                this.permissionService.deleteRowsNew(databaseName, tableName, primary_key, condition, databaseConfigId);
            }

            if(databaseType.equals("MariaDB")) {
                this.permissionService.deleteRowsNew(databaseName, tableName, primary_key, condition, databaseConfigId);
            }

            if(databaseType.equals("Oracle")) {
                this.permissionService.deleteRowsNewForOracle(databaseName, tableName, primary_key, condition, databaseConfigId);
            }

            if(databaseType.equals("PostgreSQL")) {
                this.permissionService.deleteRowsNewForPostgreSQL(databaseName, tableName, primary_key, condition, databaseConfigId);
            }

            if(databaseType.equals("MSSQL")) {
                this.permissionService.deleteRowsNewForMSSQL(databaseName, tableName, primary_key, condition, databaseConfigId);
            }

            if(databaseType.equals("MongoDB")) {
                this.permissionService.deleteRowsNewForMongoDB(databaseName, tableName, primary_key, condition, databaseConfigId);
            }

            this.permissionService.saveLog("delete from " + databaseName + "." + tableName + " where 1=1 " + condition.toString(), username, ip);
            var25 = "删除成功";
            var26 = "success";
        } catch (Exception var23) {
            var25 = var23.getMessage();
            var26 = "fail";
        }

        HashMap var27 = new HashMap();
        var27.put("totalCount", Integer.valueOf(var24));
        var27.put("mess", var25);
        var27.put("status", var26);
        return var27;
    }

    @RequestMapping(
            value = {"i/saveRows"},
            method = {RequestMethod.POST}
    )
    @ResponseBody
    public Map<String, Object> saveRows(HttpServletRequest request) {
        String databaseName = request.getParameter("databaseName");
        String tableName = request.getParameter("tableName");
        String databaseConfigId = request.getParameter("databaseConfigId");
        HashMap mapResult = new HashMap();
        HashMap maps = new HashMap();
        Map map = request.getParameterMap();
        Set set = map.entrySet();
        Iterator it = set.iterator();
        String column = "";
        String value = "";
        String mess = "";
        String status = "";

        while(it.hasNext()) {
            Entry e = (Entry)it.next();
            column = (String)e.getKey();
            String[] var18;
            int var17 = (var18 = (String[])e.getValue()).length;

            for(int var16 = 0; var16 < var17; ++var16) {
                String i = var18[var16];
                value = i.replaceAll("\'", "\'\'");
            }

            maps.put(column, value);
        }

        maps.remove("databaseName");
        maps.remove("tableName");

        try {
            this.permissionService.saveRows(maps, databaseName, tableName, databaseConfigId);
            mess = "新增成功！";
            status = "success";
        } catch (Exception var19) {
            mess = var19.getMessage();
            status = "fail";
        }

        mapResult.put("mess", mess);
        mapResult.put("status", status);
        return mapResult;
    }

    @RequestMapping(
            value = {"i/editRows/{tableName}/{databaseName}/{id}/{idValues}/{databaseConfigId}"},
            method = {RequestMethod.GET}
    )
    public String editRows(@PathVariable("tableName") String tableName, @PathVariable("databaseName") String databaseName, @PathVariable("id") String id, @PathVariable("idValues") String idValues, HttpServletRequest request, @PathVariable("databaseConfigId") String databaseConfigId) {
        List listAllColumn = this.permissionService.getOneRowById(databaseName, tableName, id, idValues, databaseConfigId);
        ArrayList newList = new ArrayList();

        for(int i = 0; i < listAllColumn.size(); ++i) {
            Map map3 = (Map)listAllColumn.get(i);
            String data_type = (String)map3.get("data_type");
            if(data_type.equals("VARCHAR")) {
                String column_value = (String)map3.get("column_value");
                column_value = htmlEscape(column_value);
                map3.put("column_value", column_value);
            }

            newList.add(map3);
        }

        request.setAttribute("databaseName", databaseName);
        request.setAttribute("tableName", tableName);
        request.setAttribute("listAllColumn", newList);
        request.setAttribute("id", id);
        request.setAttribute("idValues", idValues);
        return "system/editRowOne";
    }

    @RequestMapping(
            value = {"i/updateRows"},
            method = {RequestMethod.POST}
    )
    @ResponseBody
    public Map<String, Object> updateRows(HttpServletRequest request) {
        String mess = "";
        String status = "";
        HashMap mapResult = new HashMap();
        String databaseName = request.getParameter("databaseName");
        String tableName = request.getParameter("tableName");
        String id = request.getParameter("id");
        String idValues = request.getParameter("idValues");
        String databaseConfigId = request.getParameter("databaseConfigId");
        HashMap maps = new HashMap();
        Map map = request.getParameterMap();
        Set set = map.entrySet();
        Iterator it = set.iterator();
        String column = "";
        String value = "";

        while(it.hasNext()) {
            Entry e = (Entry)it.next();
            column = (String)e.getKey();
            String[] var20;
            int var19 = (var20 = (String[])e.getValue()).length;

            for(int var18 = 0; var18 < var19; ++var18) {
                String i = var20[var18];
                value = i;
            }

            value = value.replaceAll("\'", "\'\'");
            maps.put(column, value);
        }

        maps.remove("databaseName");
        maps.remove("tableName");
        maps.remove("id");
        maps.remove("idValues");

        try {
            this.permissionService.updateRows(maps, databaseName, tableName, id, idValues, databaseConfigId);
            mess = " 更新成功！";
            status = "success";
        } catch (Exception var21) {
            mess = var21.getMessage();
            status = "fail";
        }

        mapResult.put("mess", mess);
        mapResult.put("status", status);
        return mapResult;
    }

    @RequestMapping(
            value = {"i/exportExcel"},
            method = {RequestMethod.POST}
    )
    public void exportExcel(@RequestBody String sContent, HttpServletRequest request, HttpServletResponse response) {
        System.out.println(sContent);
        String filePath = "";
        StringBuffer sb = new StringBuffer();
        sb.append("<html> <body> <table  border=\"1px\"> ");
        sb.append(" <tr> <td>8888888888888  </td> </tr>  ");
        sb.append(" </table> </body> </html>  ");
        String ss = sb.toString();

        try {
            ServletOutputStream e = response.getOutputStream();
            response.setContentType("application/msexcel; charset=UTF-8");
            response.setHeader("Content-disposition", "attachment; filename=data.xls");
            byte[] dataByteArr = ss.getBytes("UTF-8");
            e.write(dataByteArr);
            e.flush();
            e.close();
        } catch (Exception var9) {
            System.out.println(var9.getMessage());
        }

    }

    @RequestMapping(
            value = {"i/download"},
            method = {RequestMethod.GET}
    )
    public void download(HttpServletResponse response) {
        try {
            String ex = "E://a.xls";
            File file = new File(ex);
            String filename = file.getName();
            BufferedInputStream fis = new BufferedInputStream(new FileInputStream(ex));
            byte[] buffer = new byte[fis.available()];
            fis.read(buffer);
            fis.close();
            response.reset();
            response.addHeader("Content-Disposition", "attachment;filename=" + new String(filename.getBytes()));
            response.addHeader("Content-Length", "" + file.length());
            BufferedOutputStream toClient = new BufferedOutputStream(response.getOutputStream());
            response.setContentType("application/vnd.ms-excel;charset=gb2312");
            toClient.write(buffer);
            toClient.flush();
            toClient.close();
        } catch (IOException var8) {
            var8.printStackTrace();
        }

    }

    @RequestMapping(
            value = {"i/getViewSql/{databaseConfigId}"},
            method = {RequestMethod.POST}
    )
    @ResponseBody
    public Map<String, Object> getViewSql(@RequestBody IdsDto tem, @PathVariable("databaseConfigId") String databaseConfigId) throws Exception {
        Map map = this.permissionService.getConfig(databaseConfigId);
        String databaseType = (String)map.get("databaseType");
        HashMap mapResult = new HashMap();
        String tableName = tem.getTableName();
        String databaseName = tem.getDatabaseName();
        String viewSql = "";
        String mess = "";
        String status = "";

        try {
            if(databaseType.equals("MySql")) {
                viewSql = this.permissionService.getViewSql(databaseName, tableName, databaseConfigId);
            }

            if(databaseType.equals("MariaDB")) {
                viewSql = this.permissionService.getViewSql(databaseName, tableName, databaseConfigId);
            }

            if(databaseType.equals("Oracle")) {
                viewSql = this.permissionService.getViewSqlForOracle(databaseName, tableName, databaseConfigId);
            }

            if(databaseType.equals("PostgreSQL")) {
                viewSql = this.permissionService.getViewSqlForPostgreSQL(databaseName, tableName, databaseConfigId);
            }

            if(databaseType.equals("MSSQL")) {
                viewSql = this.permissionService.getViewSqlForMSSQL(databaseName, tableName, databaseConfigId);
            }

            viewSql = viewSql.replaceAll("`", "");
            viewSql = viewSql.replaceAll(databaseName + ".", "");
            mess = "查询成功！";
            status = "success";
        } catch (Exception var12) {
            mess = var12.getMessage();
            status = "fail";
        }

        mapResult.put("mess", mess);
        mapResult.put("status", status);
        mapResult.put("viewSql", viewSql);
        return mapResult;
    }

    public static String htmlEscape(String strData) {
        if(strData == null) {
            return "";
        } else {
            strData = replaceString(strData, "&", "&amp;");
            strData = replaceString(strData, "<", "&lt;");
            strData = replaceString(strData, ">", "&gt;");
            strData = replaceString(strData, "\'", "&apos;");
            strData = replaceString(strData, "\"", "&quot;");
            return strData;
        }
    }

    public static String replaceString(String strData, String regex, String replacement) {
        if(strData == null) {
            return null;
        } else {
            int index = strData.indexOf(regex);
            String strNew = "";
            if(index < 0) {
                return strData;
            } else {
                while(index >= 0) {
                    strNew = strNew + strData.substring(0, index) + replacement;
                    strData = strData.substring(index + regex.length());
                    index = strData.indexOf(regex);
                }

                strNew = strNew + strData;
                return strNew;
            }
        }
    }

    @RequestMapping(
            value = {"i/contribute"},
            method = {RequestMethod.GET}
    )
    public String contribute(HttpServletRequest request) {
        return "system/contribute";
    }

    @RequestMapping(
            value = {"i/help"},
            method = {RequestMethod.GET}
    )
    public String help(HttpServletRequest request) {
        return "system/help";
    }

    @RequestMapping(
            value = {"i/testConn"},
            method = {RequestMethod.POST}
    )
    @ResponseBody
    public Map<String, Object> testConn(@RequestBody Config config) {
        HashMap mapResult = new HashMap();
        String databaseType = config.getDatabaseType();
        String databaseName = config.getDatabaseName();
        String ip = config.getIp();
        String port = config.getPort();
        String user = config.getUserName();
        String pass = config.getPassword();
        String mess = "";
        String status = "";
        boolean bl = this.permissionService.testConn(databaseType, databaseName, ip, port, user, pass);
        if(bl) {
            mess = "连接成功！";
            status = "success";
        } else {
            status = "fail";
        }

        mapResult.put("mess", mess);
        mapResult.put("status", status);
        return mapResult;
    }

    @RequestMapping(
            value = {"i/showTableData/{tableName}/{databaseName}/{databaseConfigId}/{objectType}"},
            method = {RequestMethod.GET}
    )
    public String showTableData(@PathVariable("tableName") String tableName, @PathVariable("databaseName") String databaseName, @PathVariable("databaseConfigId") String databaseConfigId, @PathVariable("objectType") String objectType, HttpServletRequest request) {
        Object map0 = new HashMap();

        try {
            map0 = this.permissionService.getConfig(databaseConfigId);
        } catch (Exception var8) {
            ;
        }

        String databaseType = (String)((Map)map0).get("databaseType");
        request.setAttribute("databaseName", databaseName);
        request.setAttribute("tableName", tableName);
        request.setAttribute("databaseConfigId", databaseConfigId);
        request.setAttribute("objectType", objectType);
        return databaseType.equals("MongoDB")?"system/showTableDataForMongo":"system/showTableData";
    }

    @RequestMapping(
            value = {"i/showResult/{sqlIndex}"},
            method = {RequestMethod.GET}
    )
    public String showResult(@PathVariable("sqlIndex") String sqlIndex, HttpServletRequest request) {
        request.setAttribute("sqlIndex", sqlIndex);
        return "system/showResult";
    }

    @RequestMapping(
            value = {"i/selectSqlStudy"},
            method = {RequestMethod.GET}
    )
    @ResponseBody
    public List<Map<String, Object>> selectSqlStudy(HttpServletRequest request) throws Exception {
        new ArrayList();
        List list = this.permissionService.selectSqlStudy();
        return list;
    }

    @RequestMapping(
            value = {"i/updateRow/{tableName}/{databaseName}"},
            method = {RequestMethod.GET}
    )
    public Map<String, Object> updateRow(@PathVariable("tableName") String tableName, @PathVariable("databaseName") String databaseName, HttpServletRequest request) {
        HashMap mapResult = new HashMap();
        String mess = "";
        String status = "";
        mess = "update成功！";
        status = "success";
        mapResult.put("mess", mess);
        mapResult.put("status", status);
        return mapResult;
    }

    @RequestMapping(
            value = {"i/designTable/{tableName}/{databaseName}/{databaseConfigId}"},
            method = {RequestMethod.GET}
    )
    public String designTable(@PathVariable("tableName") String tableName, @PathVariable("databaseName") String databaseName, @PathVariable("databaseConfigId") String databaseConfigId, HttpServletRequest request) throws Exception {
        request.setAttribute("databaseName", databaseName);
        request.setAttribute("tableName", tableName);
        request.setAttribute("databaseConfigId", databaseConfigId);
        return "system/designTable";
    }

    @RequestMapping(
            value = {"i/treeShow/{tableName}/{databaseName}/{databaseConfigId}"},
            method = {RequestMethod.GET}
    )
    public String treeShow(@PathVariable("tableName") String tableName, @PathVariable("databaseName") String databaseName, @PathVariable("databaseConfigId") String databaseConfigId, HttpServletRequest request) throws Exception {
        request.setAttribute("databaseName", databaseName);
        request.setAttribute("tableName", tableName);
        request.setAttribute("databaseConfigId", databaseConfigId);
        return "system/treeShow";
    }

    @RequestMapping(
            value = {"i/addNewTable/{databaseName}/{databaseConfigId}"},
            method = {RequestMethod.GET}
    )
    public String addNewTable(@PathVariable("databaseName") String databaseName, @PathVariable("databaseConfigId") String databaseConfigId, HttpServletRequest request) throws Exception {
        request.setAttribute("databaseName", databaseName);
        request.setAttribute("databaseConfigId", databaseConfigId);
        return "system/addNewTable";
    }

    @RequestMapping(
            value = {"i/jsonFormat"},
            method = {RequestMethod.GET}
    )
    public String jsonFormat(HttpServletRequest request) throws Exception {
        return "system/jsonFormat";
    }

    @RequestMapping(
            value = {"i/designTableData/{tableName}/{databaseName}/{databaseConfigId}"},
            method = {RequestMethod.GET}
    )
    @ResponseBody
    public Map<String, Object> designTableData(@PathVariable("tableName") String tableName, @PathVariable("databaseName") String databaseName, @PathVariable("databaseConfigId") String databaseConfigId, HttpServletRequest request) throws Exception {
        HashMap map = new HashMap();
        Map map0 = this.permissionService.getConfig(databaseConfigId);
        String databaseType = (String)map0.get("databaseType");
        Object listAllColumn = new ArrayList();
        if(databaseType.equals("MySql")) {
            listAllColumn = this.permissionService.getTableColumns3(databaseName, tableName, databaseConfigId);
            map.put("status", "success");
        }

        if(databaseType.equals("MariaDB")) {
            listAllColumn = this.permissionService.getTableColumns3(databaseName, tableName, databaseConfigId);
            map.put("status", "success");
        }

        if(databaseType.equals("Oracle")) {
            listAllColumn = this.permissionService.getTableColumns3ForOracle(databaseName, tableName, databaseConfigId);
            map.put("status", "success");
        }

        if(databaseType.equals("PostgreSQL")) {
            listAllColumn = this.permissionService.getTableColumns3ForPostgreSQL(databaseName, tableName, databaseConfigId);
            map.put("status", "success");
        }

        if(databaseType.equals("MSSQL")) {
            listAllColumn = this.permissionService.getTableColumns3ForMSSQL(databaseName, tableName, databaseConfigId);
            map.put("status", "success");
        }

        if(databaseType.equals("Hive2")) {
            map.put("mess", "暂不支持Hive");
            map.put("status", "fail");
        }

        if(databaseType.equals("MongoDB")) {
            map.put("mess", "暂不支持MongoDB");
            map.put("status", "fail");
        }

        map.put("rows", listAllColumn);
        map.put("total", Integer.valueOf(((List)listAllColumn).size()));
        return map;
    }

    @RequestMapping(
            value = {"i/saveData/{databaseConfigId}"},
            method = {RequestMethod.POST}
    )
    @ResponseBody
    public Map<String, Object> saveData(HttpServletRequest request, @PathVariable("databaseConfigId") String databaseConfigId) throws Exception {
        HashMap mapResult = new HashMap();
        Map map = this.permissionService.getConfig(databaseConfigId);
        String databaseType = (String)map.get("databaseType");
        String databaseName = request.getParameter("databaseName");
        String tableName = request.getParameter("tableName");
        String inserted = request.getParameter("inserted");
        String updated = request.getParameter("updated");
        String primary_key = request.getParameter("primary_key");
        HttpSession session = request.getSession(true);
        String username = (String)session.getAttribute("LOGIN_USER_NAME");
        String ip = NetworkUtil.getIpAddress(request);
        String mess = "";
        String status = "";
        String columnType = "";

        try {
            if(inserted != null) {
                JSONArray e = JSONArray.parseArray(inserted);

                for(int updateArray = 0; updateArray < e.size(); ++updateArray) {
                    Map i = (Map)e.get(updateArray);
                    HashMap map1 = new HashMap();
                    Iterator map3 = i.keySet().iterator();

                    while(map3.hasNext()) {
                        String map2 = (String)map3.next();
                        map1.put(map2, i.get(map2));
                    }

                    map1.remove("treeSoftPrimaryKey");
                    if(databaseType.equals("MySql")) {
                        this.permissionService.saveRows(map1, databaseName, tableName, databaseConfigId);
                    }

                    if(databaseType.equals("MariaDB")) {
                        this.permissionService.saveRows(map1, databaseName, tableName, databaseConfigId);
                    }

                    if(databaseType.equals("Oracle")) {
                        this.permissionService.saveRowsForOracle(map1, databaseName, tableName, databaseConfigId);
                    }

                    if(databaseType.equals("PostgreSQL")) {
                        this.permissionService.saveRowsForPostgreSQL(map1, databaseName, tableName, databaseConfigId);
                    }

                    if(databaseType.equals("MSSQL")) {
                        this.permissionService.saveRowsForMSSQL(map1, databaseName, tableName, databaseConfigId);
                    }

                    if(databaseType.equals("MongoDB")) {
                        this.permissionService.saveRowsForMongoDB(map1, databaseName, tableName, databaseConfigId);
                    }
                }
            }

            ArrayList var31 = new ArrayList();
            if(updated != null) {
                JSONArray var32 = JSONArray.parseArray(updated);

                for(int var33 = 0; var33 < var32.size(); ++var33) {
                    Map var34 = (Map)var32.get(var33);
                    Map var35 = (Map)var34.get("oldData");
                    Map var36 = (Map)var34.get("changesData");
                    String setStr = " set ";
                    String whereStr = " where 1=1 ";
                    if(var35.size() <= 0 || var36.size() <= 0) {
                        break;
                    }

                    if(primary_key != null && !primary_key.equals("")) {
                        String[] var37 = primary_key.split(",");
                        Iterator var27 = var36.keySet().iterator();

                        String var38;
                        while(var27.hasNext()) {
                            var38 = (String)var27.next();
                            if(var36.get(var38) == null) {
                                setStr = setStr + var38 + " = null , ";
                            } else {
                                if(databaseType.equals("MySql")) {
                                    if(var36.get(var38).equals("")) {
                                        setStr = setStr + var38 + " = null ,";
                                    } else {
                                        setStr = setStr + var38 + " = \'" + var36.get(var38) + "\',";
                                    }
                                }

                                if(databaseType.equals("MariaDB")) {
                                    setStr = setStr + var38 + " = \'" + var36.get(var38) + "\',";
                                }

                                if(databaseType.equals("Oracle")) {
                                    columnType = this.permissionService.selectColumnTypeForOracle(databaseName, tableName, var38, databaseConfigId);
                                    if(columnType.equals("DATE")) {
                                        setStr = setStr + var38 + " = to_date(\'" + var36.get(var38) + "\' ,\'yyyy-mm-dd hh24:mi:ss\'),";
                                    } else if(columnType.indexOf("TIMESTAMP") >= 0) {
                                        setStr = setStr + var38 + " = to_date(\'" + var36.get(var38) + "\' ,\'yyyy-mm-dd hh24:mi:ss\'),";
                                    } else {
                                        setStr = setStr + var38 + " = \'" + var36.get(var38) + "\',";
                                    }
                                }

                                if(databaseType.equals("PostgreSQL")) {
                                    setStr = setStr + "\"" + var38 + "\" = \'" + var36.get(var38) + "\',";
                                }

                                if(databaseType.equals("MSSQL")) {
                                    setStr = setStr + var38 + " = \'" + var36.get(var38) + "\',";
                                }

                                if(databaseType.equals("MongoDB")) {
                                    setStr = var36.toString() + "#@@#,";
                                }
                            }
                        }

                        int var28;
                        String[] var29;
                        int var39;
                        if(databaseType.equals("MongoDB")) {
                            var29 = var37;
                            var28 = var37.length;

                            for(var39 = 0; var39 < var28; ++var39) {
                                var38 = var29[var39];
                                whereStr = "" + var35.get(var38);
                            }
                        } else if(databaseType.equals("PostgreSQL")) {
                            var29 = var37;
                            var28 = var37.length;

                            for(var39 = 0; var39 < var28; ++var39) {
                                var38 = var29[var39];
                                whereStr = whereStr + " and \"" + var38 + "\" = \'" + var35.get(var38) + "\' ";
                            }
                        } else {
                            var29 = var37;
                            var28 = var37.length;

                            for(var39 = 0; var39 < var28; ++var39) {
                                var38 = var29[var39];
                                whereStr = whereStr + " and " + var38 + " = \'" + var35.get(var38) + "\' ";
                            }
                        }
                    } else {
                        Iterator primaryKey = var36.keySet().iterator();

                        String primaryKeys;
                        while(primaryKey.hasNext()) {
                            primaryKeys = (String)primaryKey.next();
                            if(var36.get(primaryKeys) == null) {
                                setStr = setStr + primaryKeys + " = null , ";
                            } else {
                                if(databaseType.equals("MySql")) {
                                    if(var36.get(primaryKeys).equals("")) {
                                        setStr = setStr + primaryKeys + " = null ,";
                                    } else {
                                        setStr = setStr + primaryKeys + " = \'" + var36.get(primaryKeys) + "\',";
                                    }
                                }

                                if(databaseType.equals("MariaDB")) {
                                    setStr = setStr + primaryKeys + " = \'" + var36.get(primaryKeys) + "\',";
                                }

                                if(databaseType.equals("Oracle")) {
                                    columnType = this.permissionService.selectColumnTypeForOracle(databaseName, tableName, primaryKeys, databaseConfigId);
                                    if(columnType.equals("DATE")) {
                                        setStr = setStr + primaryKeys + " = to_date(\'" + var36.get(primaryKeys) + "\' ,\'yyyy-mm-dd hh24:mi:ss\'),";
                                    } else if(columnType.indexOf("TIMESTAMP") >= 0) {
                                        setStr = setStr + primaryKeys + " = to_date(\'" + var36.get(primaryKeys) + "\' ,\'yyyy-mm-dd hh24:mi:ss\'),";
                                    } else {
                                        setStr = setStr + primaryKeys + " = \'" + var36.get(primaryKeys) + "\',";
                                    }
                                }

                                if(databaseType.equals("PostgreSQL")) {
                                    setStr = setStr + "\"" + primaryKeys + "\" = \'" + var36.get(primaryKeys) + "\',";
                                }

                                if(databaseType.equals("MSSQL")) {
                                    setStr = setStr + primaryKeys + " = \'" + var36.get(primaryKeys) + "\',";
                                }
                            }
                        }

                        primaryKey = var35.keySet().iterator();

                        while(primaryKey.hasNext()) {
                            primaryKeys = (String)primaryKey.next();
                            if(var35.get(primaryKeys) != null && !primaryKeys.equals("RN")) {
                                if(databaseType.equals("MySql")) {
                                    columnType = this.permissionService.selectColumnTypeForMySql(databaseName, tableName, primaryKeys, databaseConfigId);
                                }

                                if(databaseType.equals("MariaDB")) {
                                    columnType = this.permissionService.selectColumnTypeForMySql(databaseName, tableName, primaryKeys, databaseConfigId);
                                }

                                if(databaseType.equals("Oracle")) {
                                    columnType = this.permissionService.selectColumnTypeForOracle(databaseName, tableName, primaryKeys, databaseConfigId);
                                }

                                if(databaseType.equals("PostgreSQL")) {
                                    columnType = this.permissionService.selectColumnTypeForPostgreSQL(databaseName, tableName, primaryKeys, databaseConfigId);
                                }

                                if(databaseType.equals("MSSQL")) {
                                    columnType = this.permissionService.selectColumnTypeForMSSQL(databaseName, tableName, primaryKeys, databaseConfigId);
                                }

                                if(columnType.equals("DATE")) {
                                    whereStr = whereStr + " and " + primaryKeys + " = to_date(\' " + var35.get(primaryKeys) + "\',\'yyyy-mm-dd hh24:mi:ss\') ";
                                } else if(columnType.indexOf("TIMESTAMP") >= 0) {
                                    whereStr = whereStr + " and " + primaryKeys + " = to_date(\' " + var35.get(primaryKeys) + "\',\'yyyy-mm-dd hh24:mi:ss\') ";
                                } else if(databaseType.equals("PostgreSQL")) {
                                    whereStr = whereStr + " and \"" + primaryKeys + "\" = \'" + var35.get(primaryKeys) + "\' ";
                                } else {
                                    whereStr = whereStr + " and " + primaryKeys + " = \'" + var35.get(primaryKeys) + "\' ";
                                }
                            }
                        }
                    }

                    setStr = setStr.substring(0, setStr.length() - 1);
                    var31.add(setStr + whereStr);
                }

                if(databaseType.equals("MySql")) {
                    this.permissionService.updateRowsNew(databaseName, tableName, var31, databaseConfigId);
                    this.permissionService.saveLog("update " + databaseName + "." + tableName + " " + var31.toString(), username, ip);
                }

                if(databaseType.equals("MariaDB")) {
                    this.permissionService.updateRowsNew(databaseName, tableName, var31, databaseConfigId);
                    this.permissionService.saveLog("update " + databaseName + "." + tableName + " " + var31.toString(), username, ip);
                }

                if(databaseType.equals("Oracle")) {
                    this.permissionService.updateRowsNewForOracle(databaseName, tableName, var31, databaseConfigId);
                    this.permissionService.saveLog("update " + databaseName + "." + tableName + " " + var31.toString(), username, ip);
                }

                if(databaseType.equals("PostgreSQL")) {
                    this.permissionService.updateRowsNewForPostgreSQL(databaseName, tableName, var31, databaseConfigId);
                    this.permissionService.saveLog("update " + databaseName + "." + tableName + " " + var31.toString(), username, ip);
                }

                if(databaseType.equals("MSSQL")) {
                    this.permissionService.updateRowsNewForMSSQL(databaseName, tableName, var31, databaseConfigId);
                    this.permissionService.saveLog("update " + databaseName + "." + tableName + " " + var31.toString(), username, ip);
                }

                if(databaseType.equals("MongoDB")) {
                    this.permissionService.updateRowsNewForMongoDB(databaseName, tableName, var31, databaseConfigId);
                    this.permissionService.saveLog("update " + databaseName + "." + tableName + " " + var31.toString(), username, ip);
                }
            }

            mess = "保存成功！";
            status = "success";
        } catch (Exception var30) {
            mess = "保存出错！" + var30.getMessage();
            status = "fail";
        }

        mapResult.put("mess", mess);
        mapResult.put("status", status);
        return mapResult;
    }

    @RequestMapping(
            value = {"i/designTableUpdate/{databaseConfigId}"},
            method = {RequestMethod.POST}
    )
    @ResponseBody
    public Map<String, Object> designTableUpdate(@PathVariable("databaseConfigId") String databaseConfigId, HttpServletRequest request) throws Exception {
        HashMap mapResult = new HashMap();
        String mess = "";
        String status = "";
        Map map = this.permissionService.getConfig(databaseConfigId);
        String databaseType = (String)map.get("databaseType");
        String databaseName = request.getParameter("databaseName");
        String tableName = request.getParameter("tableName");
        String inserted = request.getParameter("inserted");
        String updated = request.getParameter("updated");

        try {
            if(inserted != null) {
                JSONArray e = JSONArray.parseArray(inserted);

                for(int i = 0; i < e.size(); ++i) {
                    Map map1 = (Map)e.get(i);
                    HashMap maps = new HashMap();
                    Iterator var17 = map1.keySet().iterator();

                    while(var17.hasNext()) {
                        String key = (String)var17.next();
                        maps.put(key, map1.get(key));
                    }

                    maps.remove("TREESOFTPRIMARYKEY");
                    if(databaseType.equals("MySql")) {
                        this.permissionService.saveDesginColumn(maps, databaseName, tableName, databaseConfigId);
                    }

                    if(databaseType.equals("MariaDB")) {
                        this.permissionService.saveDesginColumn(maps, databaseName, tableName, databaseConfigId);
                    }

                    if(databaseType.equals("Oracle")) {
                        this.permissionService.saveDesginColumnForOracle(maps, databaseName, tableName, databaseConfigId);
                    }

                    if(databaseType.equals("PostgreSQL")) {
                        this.permissionService.saveDesginColumnForPostgreSQL(maps, databaseName, tableName, databaseConfigId);
                    }

                    if(databaseType.equals("MSSQL")) {
                        this.permissionService.saveDesginColumnForMSSQL(maps, databaseName, tableName, databaseConfigId);
                    }

                    if(databaseType.equals("Hive2")) {
                        throw new Exception("暂不支持Hive");
                    }

                    if(databaseType.equals("MongoDB")) {
                        throw new Exception("暂不支持MongoDB");
                    }
                }
            }

            if(updated != null) {
                if(databaseType.equals("MySql")) {
                    this.permissionService.updateTableColumn(updated, databaseName, tableName, databaseConfigId);
                }

                if(databaseType.equals("MariaDB")) {
                    this.permissionService.updateTableColumn(updated, databaseName, tableName, databaseConfigId);
                }

                if(databaseType.equals("Oracle")) {
                    this.permissionService.updateTableColumnForOracle(updated, databaseName, tableName, databaseConfigId);
                }

                if(databaseType.equals("PostgreSQL")) {
                    this.permissionService.updateTableColumnForPostgreSQL(updated, databaseName, tableName, databaseConfigId);
                }

                if(databaseType.equals("MSSQL")) {
                    this.permissionService.updateTableColumnForMSSQL(updated, databaseName, tableName, databaseConfigId);
                }

                if(databaseType.equals("Hive2")) {
                    throw new Exception("暂不支持Hive");
                }

                if(databaseType.equals("MongoDB")) {
                    throw new Exception("暂不支持MongoDB");
                }
            }

            mess = "保存成功！";
            status = "success";
        } catch (Exception var18) {
            mess = "保存出错！" + var18.getMessage();
            status = "fail";
        }

        mapResult.put("mess", mess);
        mapResult.put("status", status);
        return mapResult;
    }

    @RequestMapping(
            value = {"i/deleteTableColumn/{databaseConfigId}"},
            method = {RequestMethod.POST}
    )
    @ResponseBody
    public Map<String, Object> deleteTableColumn(@PathVariable("databaseConfigId") String databaseConfigId, @RequestBody IdsDto tem) throws Exception {
        String databaseName = tem.getDatabaseName();
        String tableName = tem.getTableName();
        String[] ids = tem.getIds();
        Map map0 = this.permissionService.getConfig(databaseConfigId);
        String databaseType = (String)map0.get("databaseType");
        byte i = 0;
        String mess = "";
        String status = "";

        try {
            if(databaseType.equals("MySql")) {
                this.permissionService.deleteTableColumn(databaseName, tableName, ids, databaseConfigId);
            }

            if(databaseType.equals("MariaDB")) {
                this.permissionService.deleteTableColumn(databaseName, tableName, ids, databaseConfigId);
            }

            if(databaseType.equals("Oracle")) {
                this.permissionService.deleteTableColumnForOracle(databaseName, tableName, ids, databaseConfigId);
            }

            if(databaseType.equals("PostgreSQL")) {
                this.permissionService.deleteTableColumnForPostgreSQL(databaseName, tableName, ids, databaseConfigId);
            }

            if(databaseType.equals("MSSQL")) {
                this.permissionService.deleteTableColumnForMSSQL(databaseName, tableName, ids, databaseConfigId);
            }

            mess = "删除成功";
            status = "success";
        } catch (Exception var12) {
            mess = var12.getMessage();
            status = "fail";
        }

        HashMap map = new HashMap();
        map.put("totalCount", Integer.valueOf(i));
        map.put("mess", mess);
        map.put("status", status);
        return map;
    }

    @RequestMapping(
            value = {"i/designTableSetNull/{databaseConfigId}"},
            method = {RequestMethod.POST}
    )
    @ResponseBody
    public Map<String, Object> designTableSetNull(@PathVariable("databaseConfigId") String databaseConfigId, @RequestBody IdsDto tem) throws Exception {
        String mess = "";
        String status = "";
        Map map0 = this.permissionService.getConfig(databaseConfigId);
        String databaseType = (String)map0.get("databaseType");
        String databaseName = tem.getDatabaseName();
        String tableName = tem.getTableName();
        String column_name = tem.getColumn_name();
        String is_nullable = tem.getIs_nullable();

        try {
            if(databaseType.equals("MySql")) {
                this.permissionService.updateTableNullAble(databaseName, tableName, column_name, is_nullable, databaseConfigId);
            }

            if(databaseType.equals("MariaDB")) {
                this.permissionService.updateTableNullAble(databaseName, tableName, column_name, is_nullable, databaseConfigId);
            }

            if(databaseType.equals("Oracle")) {
                this.permissionService.updateTableNullAbleForOracle(databaseName, tableName, column_name, is_nullable, databaseConfigId);
            }

            if(databaseType.equals("PostgreSQL")) {
                this.permissionService.updateTableNullAbleForPostgreSQL(databaseName, tableName, column_name, is_nullable, databaseConfigId);
            }

            if(databaseType.equals("MSSQL")) {
                this.permissionService.updateTableNullAbleForMSSQL(databaseName, tableName, column_name, is_nullable, databaseConfigId);
            }

            mess = "保存成功";
            status = "success";
        } catch (Exception var12) {
            mess = var12.getMessage();
            status = "fail";
        }

        HashMap map = new HashMap();
        map.put("mess", mess);
        map.put("status", status);
        return map;
    }

    @RequestMapping(
            value = {"i/designTableSetPimary/{databaseConfigId}"},
            method = {RequestMethod.POST}
    )
    @ResponseBody
    public Map<String, Object> designTableSetPimary(@PathVariable("databaseConfigId") String databaseConfigId, @RequestBody IdsDto tem) throws Exception {
        String mess = "";
        String status = "";
        Map map0 = this.permissionService.getConfig(databaseConfigId);
        String databaseType = (String)map0.get("databaseType");
        String databaseName = tem.getDatabaseName();
        String tableName = tem.getTableName();
        String column_name = tem.getColumn_name();
        String column_key = tem.getColumn_key();

        try {
            if(databaseType.equals("MySql")) {
                this.permissionService.savePrimaryKey(databaseName, tableName, column_name, column_key, databaseConfigId);
            }

            if(databaseType.equals("MariaDB")) {
                this.permissionService.savePrimaryKey(databaseName, tableName, column_name, column_key, databaseConfigId);
            }

            if(databaseType.equals("Oracle")) {
                this.permissionService.savePrimaryKeyForOracle(databaseName, tableName, column_name, column_key, databaseConfigId);
            }

            if(databaseType.equals("PostgreSQL")) {
                this.permissionService.savePrimaryKeyForPostgreSQL(databaseName, tableName, column_name, column_key, databaseConfigId);
            }

            if(databaseType.equals("MSSQL")) {
                this.permissionService.savePrimaryKeyForMSSQL(databaseName, tableName, column_name, column_key, databaseConfigId);
            }

            mess = "保存成功";
            status = "success";
        } catch (Exception var12) {
            mess = var12.getMessage();
            status = "fail";
        }

        HashMap map = new HashMap();
        map.put("mess", mess);
        map.put("status", status);
        return map;
    }

    @RequestMapping(
            value = {"i/upDownColumn/{databaseConfigId}"},
            method = {RequestMethod.POST}
    )
    @ResponseBody
    public Map<String, Object> upDownColumn(@PathVariable("databaseConfigId") String databaseConfigId, @RequestBody IdsDto tem) throws Exception {
        String mess = "";
        String status = "";
        Map map0 = this.permissionService.getConfig(databaseConfigId);
        String databaseType = (String)map0.get("databaseType");
        String databaseName = tem.getDatabaseName();
        String tableName = tem.getTableName();
        String column_name = tem.getColumn_name();
        String column_name2 = tem.getColumn_name2();

        try {
            if(databaseType.equals("MySql")) {
                this.permissionService.upDownColumn(databaseName, tableName, column_name, column_name2, databaseConfigId);
                mess = "保存成功";
                status = "success";
            }

            if(databaseType.equals("MariaDB")) {
                this.permissionService.upDownColumn(databaseName, tableName, column_name, column_name2, databaseConfigId);
                mess = "保存成功";
                status = "success";
            }

            if(databaseType.equals("Oracle")) {
                mess = "Oracle不支持该操作！";
                status = "success";
            }

            if(databaseType.equals("PostgreSQL")) {
                mess = "PostgreSQL不支持该操作！";
                status = "success";
            }

            if(databaseType.equals("MSSQL")) {
                mess = "SQL Server不支持该操作！";
                status = "success";
            }
        } catch (Exception var12) {
            mess = var12.getMessage();
            status = "fail";
        }

        HashMap map = new HashMap();
        map.put("mess", mess);
        map.put("status", status);
        return map;
    }

    public Map<String, Object> executeSqlHaveResForOracle(String sql, String dbName, String databaseConfigId, HttpServletRequest request) {
        HashMap map = new HashMap();
        Page page = this.getPage(request);
        String mess = "";
        String status = "";
        Date b1 = new Date();

        try {
            page = this.permissionService.executeSqlHaveResForOracle(page, sql, dbName, databaseConfigId);
            mess = "执行成功！";
            status = "success";
        } catch (Exception var13) {
            System.out.println("执行出错了  " + var13.getMessage());
            mess = var13.getMessage();
            status = "fail";
        }

        Date b2 = new Date();
        long y = b2.getTime() - b1.getTime();
        map.put("rows", page.getResult());
        map.put("total", Long.valueOf(page.getTotalCount()));
        map.put("columns", page.getColumns());
        map.put("primaryKey", page.getPrimaryKey());
        map.put("totalCount", Long.valueOf(page.getTotalCount()));
        map.put("time", Long.valueOf(y));
        map.put("mess", mess);
        map.put("status", status);
        return map;
    }

    public Map<String, Object> executeSqlHaveResForPostgreSQL(String sql, String dbName, String databaseConfigId, HttpServletRequest request) {
        HashMap map = new HashMap();
        Page page = this.getPage(request);
        String mess = "";
        String status = "";
        Date b1 = new Date();

        try {
            page = this.permissionService.executeSqlHaveResForPostgreSQL(page, sql, dbName, databaseConfigId);
            mess = "执行成功！";
            status = "success";
        } catch (Exception var13) {
            System.out.println("SQL执行出错, " + var13.getMessage());
            mess = var13.getMessage();
            status = "fail";
        }

        Date b2 = new Date();
        long y = b2.getTime() - b1.getTime();
        map.put("rows", page.getResult());
        map.put("total", Long.valueOf(page.getTotalCount()));
        map.put("columns", page.getColumns());
        map.put("primaryKey", page.getPrimaryKey());
        map.put("totalCount", Long.valueOf(page.getTotalCount()));
        map.put("time", Long.valueOf(y));
        map.put("mess", mess);
        map.put("status", status);
        return map;
    }

    public Map<String, Object> executeSqlHaveResForMSSQL(String sql, String dbName, String databaseConfigId, HttpServletRequest request) {
        HashMap map = new HashMap();
        Page page = this.getPage(request);
        String mess = "";
        String status = "";
        Date b1 = new Date();

        try {
            page = this.permissionService.executeSqlHaveResForMSSQL(page, sql, dbName, databaseConfigId);
            mess = "执行成功！";
            status = "success";
        } catch (Exception var13) {
            System.out.println("ERROR " + var13.getMessage());
            mess = var13.getMessage();
            status = "fail";
        }

        Date b2 = new Date();
        long y = b2.getTime() - b1.getTime();
        map.put("rows", page.getResult());
        map.put("total", Long.valueOf(page.getTotalCount()));
        map.put("columns", page.getColumns());
        map.put("primaryKey", page.getPrimaryKey());
        map.put("totalCount", Long.valueOf(page.getTotalCount()));
        map.put("time", Long.valueOf(y));
        map.put("mess", mess);
        map.put("status", status);
        return map;
    }

    public Map<String, Object> executeSqlHaveResForHive2(String sql, String dbName, String databaseConfigId, HttpServletRequest request) {
        HashMap map = new HashMap();
        Page page = this.getPage(request);
        String mess = "";
        String status = "";
        Date b1 = new Date();

        try {
            page = this.permissionService.executeSqlHaveResForHive2(page, sql, dbName, databaseConfigId);
            mess = "执行成功！";
            status = "success";
        } catch (Exception var13) {
            System.out.println("ERROR " + var13.getMessage());
            mess = var13.getMessage();
            status = "fail";
        }

        Date b2 = new Date();
        long y = b2.getTime() - b1.getTime();
        map.put("rows", page.getResult());
        map.put("total", Long.valueOf(page.getTotalCount()));
        map.put("columns", page.getColumns());
        map.put("tableName", "");
        map.put("totalCount", Long.valueOf(page.getTotalCount()));
        map.put("time", Long.valueOf(y));
        map.put("mess", mess);
        map.put("status", status);
        return map;
    }

    public Map<String, Object> executeSqlHaveResForMongoDB(String sql, String dbName, String databaseConfigId, HttpServletRequest request) {
        HashMap map = new HashMap();
        Page page = this.getPage(request);
        String mess = "";
        String status = "";
        Date b1 = new Date();

        try {
            page = this.permissionService.executeSqlHaveResForMongoDB(page, sql, dbName, databaseConfigId);
            mess = "执行成功！";
            status = "success";
        } catch (Exception var13) {
            System.out.println("MongoDB不支持该shell命令或执行shell命令出错, " + var13.getMessage());
            mess = "MongoDB不支持该shell命令或执行shell命令出错," + var13.getMessage();
            status = "fail";
        }

        Date b2 = new Date();
        long y = b2.getTime() - b1.getTime();
        map.put("rows", page.getResult());
        map.put("total", Long.valueOf(page.getTotalCount()));
        map.put("columns", page.getColumns());
        map.put("primaryKey", page.getPrimaryKey());
        map.put("tableName", page.getTableName());
        map.put("totalCount", Long.valueOf(page.getTotalCount()));
        map.put("time", Long.valueOf(y));
        map.put("mess", mess);
        map.put("status", status);
        return map;
    }

    public Map<String, Object> executeSqlNotResForMongoDB(String sql, String dbName, String databaseConfigId) {
        String mess = "";
        String status = "";
        Date b1 = new Date();
        int i = 0;

        try {
            i = this.permissionService.executeSqlNotResForMongoDB(sql, dbName, databaseConfigId);
            mess = "执行成功！";
            status = "success";
        } catch (Exception var12) {
            mess = var12.getMessage();
            status = "fail";
        }

        Date b2 = new Date();
        long y = b2.getTime() - b1.getTime();
        HashMap map = new HashMap();
        map.put("totalCount", Integer.valueOf(i));
        map.put("time", Long.valueOf(y));
        map.put("mess", mess);
        map.put("status", status);
        return map;
    }

    @RequestMapping(
            value = {"i/backupDatabase/{databaseName}/{databaseConfigId}"},
            method = {RequestMethod.GET}
    )
    public String backupDatabase(@PathVariable("databaseName") String databaseName, @PathVariable("databaseConfigId") String databaseConfigId, HttpServletRequest request) throws Exception {
        request.setAttribute("databaseName", databaseName);
        request.setAttribute("databaseConfigId", databaseConfigId);
        return "system/backupDatabase";
    }

    @RequestMapping(
            value = {"i/monitor/{databaseName}/{databaseConfigId}"},
            method = {RequestMethod.GET}
    )
    public String monitor(@PathVariable("databaseName") String databaseName, @PathVariable("databaseConfigId") String databaseConfigId, HttpServletRequest request) throws Exception {
        Map map0 = this.permissionService.getConfig(databaseConfigId);
        String ip = "" + map0.get("ip");
        String port = "" + map0.get("port");
        String name = "" + map0.get("name");
        String databaseType = "" + map0.get("databaseType");
        request.setAttribute("ip", ip);
        request.setAttribute("port", port);
        request.setAttribute("name", name);
        request.setAttribute("databaseType", databaseType);
        request.setAttribute("databaseConfigId", databaseConfigId);
        if(databaseType.equals("MongoDB")) {
            return "system/monitorMongoDB";
        } else if(!databaseType.equals("Oracle")) {
            return databaseType.equals("PostgreSQL")?"system/monitorPostgreSQL":(databaseType.equals("MySql")?"system/monitorMySql":(databaseType.equals("MariaDB")?"system/monitorMySql":"system/monitor"));
        } else {
            List list = this.permissionService.queryTableSpaceForOracle(databaseName, databaseConfigId);
            String tableSpaceName = "";
            String TABLESPACE_SIZE_FREE = "";
            String TABLESPACE_SIZE_USED = "";

            for(int i = 0; i < list.size(); ++i) {
                Map map = (Map)list.get(i);
                tableSpaceName = tableSpaceName + "\'" + map.get("TABLESPACE_NAME") + "\',";
                TABLESPACE_SIZE_FREE = TABLESPACE_SIZE_FREE + map.get("TABLESPACE_SIZE_FREE") + ",";
                TABLESPACE_SIZE_USED = TABLESPACE_SIZE_USED + map.get("TABLESPACE_SIZE_USED") + ",";
            }

            tableSpaceName = tableSpaceName.substring(0, tableSpaceName.length() - 1);
            TABLESPACE_SIZE_FREE = TABLESPACE_SIZE_FREE.substring(0, TABLESPACE_SIZE_FREE.length() - 1);
            TABLESPACE_SIZE_USED = TABLESPACE_SIZE_USED.substring(0, TABLESPACE_SIZE_USED.length() - 1);
            request.setAttribute("tableSpaceName", tableSpaceName);
            request.setAttribute("tableSpaceSizeFree", TABLESPACE_SIZE_FREE);
            request.setAttribute("tableSpaceSizeUsed", TABLESPACE_SIZE_USED);
            return "system/monitorOracle";
        }
    }

    @RequestMapping(
            value = {"i/monitorItem/{databaseName}/{databaseConfigId}"},
            method = {RequestMethod.GET}
    )
    public String monitorItem(@PathVariable("databaseName") String databaseName, @PathVariable("databaseConfigId") String databaseConfigId, HttpServletRequest request) throws Exception {
        request.setAttribute("databaseName", databaseName);
        request.setAttribute("databaseConfigId", databaseConfigId);
        return "system/monitorItem";
    }

    @RequestMapping({"i/queryDatabaseStatus/{databaseName}/{databaseConfigId}"})
    @ResponseBody
    public Map<String, Object> queryDatabaseStatus(@PathVariable("databaseName") String databaseName, @PathVariable("databaseConfigId") String databaseConfigId) {
        Object map = new HashMap();
        String mess = "";
        String status = "";

        try {
            Map e = this.permissionService.getConfig(databaseConfigId);
            String databaseType = (String)e.get("databaseType");
            if(databaseType.equals("MySql")) {
                map = this.permissionService.queryDatabaseStatus(databaseName, databaseConfigId);
                mess = "查询成功";
                status = "success";
            }

            if(databaseType.equals("MariaDB")) {
                map = this.permissionService.queryDatabaseStatus(databaseName, databaseConfigId);
                mess = "查询成功";
                status = "success";
            }

            if(databaseType.equals("Oracle")) {
                map = this.permissionService.queryDatabaseStatusForOracle(databaseName, databaseConfigId);
                mess = "查询成功";
                status = "success";
            }

            if(databaseType.equals("PostgreSQL")) {
                map = this.permissionService.queryDatabaseStatusForPostgreSQL(databaseName, databaseConfigId);
                mess = "查询成功";
                status = "success";
            }

            if(databaseType.equals("MSSQL")) {
                mess = "暂不支持SQL Server状态监控!";
                status = "fail";
            }

            if(databaseType.equals("Hive2")) {
                mess = "暂不支持Hive状态监控!";
                status = "fail";
            }

            if(databaseType.equals("MongoDB")) {
                map = this.permissionService.queryDatabaseStatusForMongoDB(databaseName, databaseConfigId);
                mess = "查询成功";
                status = "success";
            }

            double memUsage = ComputerMonitorUtil.getMemUsage();
            double diskUsage = ComputerMonitorUtil.getDiskUsage();
            ((Map)map).put("memUsage", Double.valueOf(memUsage));
            ((Map)map).put("diskUsage", Double.valueOf(diskUsage));
        } catch (Exception var12) {
            mess = var12.getMessage();
            status = "fail";
        }

        ((Map)map).put("mess", mess);
        ((Map)map).put("status", status);
        return (Map)map;
    }

    @RequestMapping({"i/monitorItemValue/{databaseName}/{databaseConfigId}"})
    @ResponseBody
    public Map<String, Object> monitorItemValue(@PathVariable("databaseName") String databaseName, @PathVariable("databaseConfigId") String databaseConfigId) throws Exception {
        Object list = new ArrayList();
        HashMap map = new HashMap();
        Map map0 = this.permissionService.getConfig(databaseConfigId);
        String databaseType = (String)map0.get("databaseType");
        String mess = "";
        String status = "";

        try {
            if(databaseType.equals("MySql")) {
                list = this.permissionService.monitorItemValue(databaseName, databaseConfigId);
                mess = "查询成功";
                status = "success";
            }

            if(databaseType.equals("MariaDB")) {
                list = this.permissionService.monitorItemValue(databaseName, databaseConfigId);
                mess = "查询成功";
                status = "success";
            }

            if(databaseType.equals("Oracle")) {
                list = this.permissionService.monitorItemValueForOracle(databaseName, databaseConfigId);
                mess = "查询成功";
                status = "success";
            }

            if(databaseType.equals("PostgreSQL")) {
                Thread.sleep(3000L);
                mess = "暂不支持";
                status = "fail";
            }

            if(databaseType.equals("MSSQL")) {
                Thread.sleep(3000L);
                mess = "暂不支持";
                status = "fail";
            }

            if(databaseType.equals("MongoDB")) {
                mess = "暂不支持";
                status = "fail";
            }

            mess = "查询成功";
            status = "success";
        } catch (Exception var10) {
            mess = var10.getMessage();
            status = "fail";
            return null;
        }

        map.put("mess", mess);
        map.put("status", status);
        map.put("rows", list);
        map.put("total", Integer.valueOf(((List)list).size()));
        return map;
    }

    @RequestMapping(
            value = {"i/backupDatabaseData/{random}"},
            method = {RequestMethod.GET}
    )
    @ResponseBody
    public Map<String, Object> backupDatabaseData(HttpServletRequest request) throws Exception {
        HashMap map = new HashMap();
        new ArrayList();
        String path = request.getSession().getServletContext().getRealPath("/") + "backup";
        List list = this.permissionService.selectBackupList(path);
        map.put("rows", list);
        map.put("total", Integer.valueOf(list.size()));
        return map;
    }

    @RequestMapping(
            value = {"i/backupDatabaseExecute/{databaseConfigId}"},
            method = {RequestMethod.POST}
    )
    @ResponseBody
    public Map<String, Object> backupDatabaseExecute(@RequestBody IdsDto tem, @PathVariable("databaseConfigId") String databaseConfigId, HttpServletRequest request) throws Exception {
        String databaseName = tem.getDatabaseName();
        HashMap mapResult = new HashMap();
        String mess = "";
        String status = "";
        Map map = this.permissionService.getConfig(databaseConfigId);
        String databaseType = (String)map.get("databaseType");
        String path = request.getSession().getServletContext().getRealPath("/");

        try {
            if(databaseType.equals("MySql")) {
                this.permissionService.backupDatabaseExecute(databaseName, "", path, databaseConfigId);
                Thread.sleep(3000L);
                mess = "备份完成！";
                status = "success";
            }

            if(databaseType.equals("MariaDB")) {
                this.permissionService.backupDatabaseExecute(databaseName, "", path, databaseConfigId);
                Thread.sleep(3000L);
                mess = "备份完成！";
                status = "success";
            }

            if(databaseType.equals("Oracle")) {
                this.permissionService.backupDatabaseExecuteForOracle(databaseName, "", path, databaseConfigId);
                Thread.sleep(3000L);
                mess = "备份完成！";
                status = "success";
            }

            if(databaseType.equals("PostgreSQL")) {
                this.permissionService.backupDatabaseExecuteForPostgreSQL(databaseName, "", path, databaseConfigId);
                Thread.sleep(3000L);
                mess = "备份完成！";
                status = "success";
            }

            if(databaseType.equals("MSSQL")) {
                this.permissionService.backupDatabaseExecuteForMSSQL(databaseName, "", path, databaseConfigId);
                Thread.sleep(3000L);
                mess = "备份完成！";
                status = "success";
            }

            if(databaseType.equals("Hive2")) {
                mess = "暂不支持Hive备份！";
                status = "fail";
            }

            if(databaseType.equals("MongoDB")) {
                mess = "暂不支持MongoDB备份！";
                status = "fail";
            }
        } catch (Exception var12) {
            mess = "备份数据库失败！";
            status = "fail";
        }

        mapResult.put("mess", mess);
        mapResult.put("status", status);
        return mapResult;
    }

    @RequestMapping(
            value = {"i/exportTable/{databaseConfigId}"},
            method = {RequestMethod.POST}
    )
    @ResponseBody
    public Map<String, Object> exportTable(@RequestBody IdsDto tem, @PathVariable("databaseConfigId") String databaseConfigId, HttpServletRequest request) throws Exception {
        String databaseName = tem.getDatabaseName();
        String tableName = tem.getTableName();
        Map map = this.permissionService.getConfig(databaseConfigId);
        String databaseType = (String)map.get("databaseType");
        HashMap mapResult = new HashMap();
        String mess = "";
        String status = "";
        String path = request.getSession().getServletContext().getRealPath("/");

        try {
            if(databaseType.equals("MySql")) {
                this.permissionService.backupDatabaseExecute(databaseName, tableName, path, databaseConfigId);
                Thread.sleep(3000L);
                mess = "导出表完成，请刷新并下载！";
                status = "success";
            }

            if(databaseType.equals("MariaDB")) {
                this.permissionService.backupDatabaseExecute(databaseName, tableName, path, databaseConfigId);
                Thread.sleep(3000L);
                mess = "导出表完成，请刷新并下载！";
                status = "success";
            }

            if(databaseType.equals("Oracle")) {
                this.permissionService.backupDatabaseExecuteForOracle(databaseName, tableName, path, databaseConfigId);
                Thread.sleep(3000L);
                mess = "导出表完成，请刷新并下载！";
                status = "success";
            }

            if(databaseType.equals("PostgreSQL")) {
                this.permissionService.backupDatabaseExecuteForPostgreSQL(databaseName, tableName, path, databaseConfigId);
                Thread.sleep(3000L);
                mess = "导出表完成，请刷新并下载！";
                status = "success";
            }

            if(databaseType.equals("MSSQL")) {
                this.permissionService.backupDatabaseExecuteForMSSQL(databaseName, tableName, path, databaseConfigId);
                Thread.sleep(3000L);
                mess = "导出表完成，请刷新并下载！";
                status = "success";
            }

            if(databaseType.equals("Hive2")) {
                mess = "暂不支持Hive导出表！";
                status = "fail";
            }

            if(databaseType.equals("MongoDB")) {
                mess = "暂不支持MongoDB导出表！";
                status = "fail";
            }
        } catch (Exception var13) {
            mess = "导出表失败！";
            status = "fail";
        }

        mapResult.put("mess", mess);
        mapResult.put("status", status);
        return mapResult;
    }

    @RequestMapping(
            value = {"i/exportDataToSQL/{databaseConfigId}"},
            method = {RequestMethod.POST}
    )
    @ResponseBody
    public void exportDataToSQL(IdsDto tem, @PathVariable("databaseConfigId") String databaseConfigId, HttpServletRequest request, HttpServletResponse response) throws Exception {
        String databaseName = tem.getDatabaseName();
        String tableName = tem.getTableName();
        Map map = this.permissionService.getConfig(databaseConfigId);
        String databaseType = (String)map.get("databaseType");
        Object listTableColumn = new ArrayList();
        String primary_key = tem.getPrimary_key();
        String checkedItems = tem.getCheckedItems();
        checkedItems = HtmlUtils.htmlUnescape(checkedItems);
        ArrayList condition = new ArrayList();
        int len;
        String var27;
        if(checkedItems != null) {
            JSONArray TableColumnType = JSONArray.parseArray(checkedItems);

            for(int dataList = 0; dataList < TableColumnType.size(); ++dataList) {
                if(databaseType.equals("MongoDB")) {
                    var27 = "";
                    JSONObject var30 = (JSONObject)TableColumnType.get(dataList);
                    var27 = var30.getString("_id");
                    condition.add(var27);
                } else {
                    Map e = (Map)TableColumnType.get(dataList);
                    String out = "";
                    if(primary_key != null && !primary_key.equals("")) {
                        String[] var29 = primary_key.split(",");
                        String[] var21 = var29;
                        len = var29.length;

                        for(int buffer = 0; buffer < len; ++buffer) {
                            String var33 = var21[buffer];
                            if(databaseType.equals("PostgreSQL")) {
                                out = out + " and \"" + var33 + "\" = \'" + e.get(var33) + "\' ";
                            } else {
                                out = out + " and " + var33 + " = \'" + e.get(var33) + "\' ";
                            }
                        }
                    } else {
                        Iterator b = e.keySet().iterator();

                        label154:
                        while(true) {
                            String e1;
                            do {
                                if(!b.hasNext()) {
                                    break label154;
                                }

                                e1 = (String)b.next();
                            } while(databaseType.equals("Oracle") && e1.equals("RN"));

                            if(e.get(e1) != null) {
                                if(databaseType.equals("PostgreSQL")) {
                                    out = out + " and \"" + e1 + "\" = \'" + e.get(e1) + "\' ";
                                } else {
                                    out = out + " and " + e1 + " = \'" + e.get(e1) + "\' ";
                                }
                            }
                        }
                    }

                    condition.add(out);
                }
            }
        }

        if(databaseType.equals("MySql")) {
            listTableColumn = this.permissionService.getTableColumns3(databaseName, tableName, databaseConfigId);
        }

        if(databaseType.equals("MariaDB")) {
            listTableColumn = this.permissionService.getTableColumns3(databaseName, tableName, databaseConfigId);
        }

        if(databaseType.equals("Oracle")) {
            listTableColumn = this.permissionService.getTableColumns3ForOracle(databaseName, tableName, databaseConfigId);
        }

        if(databaseType.equals("PostgreSQL")) {
            listTableColumn = this.permissionService.getTableColumns3ForPostgreSQL(databaseName, tableName, databaseConfigId);
        }

        if(databaseType.equals("MSSQL")) {
            listTableColumn = this.permissionService.getTableColumns3ForMSSQL(databaseName, tableName, databaseConfigId);
        }

        databaseType.equals("MongoDB");
        HashMap var24 = new HashMap();
        Iterator var28 = ((List)listTableColumn).iterator();

        while(var28.hasNext()) {
            Map var25 = (Map)var28.next();
            var24.put((String)var25.get("COLUMN_NAME"), (String)var25.get("DATA_TYPE"));
        }

        Object var26 = new ArrayList();

        try {
            if(databaseType.equals("MySql")) {
                var26 = this.permissionService.exportDataToSQLForMySQL(databaseName, tableName, condition, databaseConfigId);
            }

            if(databaseType.equals("MariaDB")) {
                var26 = this.permissionService.exportDataToSQLForMySQL(databaseName, tableName, condition, databaseConfigId);
            }

            if(databaseType.equals("Oracle")) {
                var26 = this.permissionService.exportDataToSQLForOracle(databaseName, tableName, condition, databaseConfigId);
            }

            if(databaseType.equals("PostgreSQL")) {
                var26 = this.permissionService.exportDataToSQLForPostgreSQL(databaseName, tableName, condition, databaseConfigId);
            }

            if(databaseType.equals("MSSQL")) {
                var26 = this.permissionService.exportDataToSQLForMSSQL(databaseName, tableName, condition, databaseConfigId);
            }

            if(databaseType.equals("MongoDB")) {
                var26 = this.permissionService.exportDataToSQLForMongoDB(databaseName, tableName, condition, databaseConfigId);
            }

            var27 = "";
            if(databaseType.equals("MySql")) {
                var27 = this.dataListToStringForMySQL(tableName, var24, (List)var26);
            }

            if(databaseType.equals("MariaDB")) {
                var27 = this.dataListToStringForMySQL(tableName, var24, (List)var26);
            }

            if(databaseType.equals("Oracle")) {
                var27 = this.dataListToStringForOracle(tableName, var24, (List)var26);
            }

            if(databaseType.equals("PostgreSQL")) {
                var27 = this.dataListToStringForPostgreSQL(tableName, var24, (List)var26);
            }

            if(databaseType.equals("MSSQL")) {
                var27 = this.dataListToStringForMSSQL(tableName, var24, (List)var26);
            }

            if(databaseType.equals("MongoDB")) {
                var27 = this.dataListToStringForMongoDB((List)var26);
            }

            response.setContentType("multipart/form-data");
            response.addHeader("Content-Disposition", "attachment;fileName=" + tableName + ".sql ");

            try {
                ByteArrayInputStream var31 = new ByteArrayInputStream(var27.getBytes());
                ServletOutputStream var32 = response.getOutputStream();
                boolean var34 = false;
                byte[] var35 = new byte[1024];
                boolean var36 = false;

                while((len = var31.read(var35)) > 0) {
                    var32.write(var35, 0, len);
                }

                var31.close();
                var32.close();
                var32.flush();
            } catch (Exception var22) {
                var22.printStackTrace();
            }
        } catch (Exception var23) {
            System.out.println("导出数据失败 " + var23.getMessage());
        }

    }

    public String dataListToStringForMSSQL(String tableName, Map<String, String> TableColumnType, List<Map<String, Object>> dataList) {
        String key = "";
        String values = "";
        String tempValues = "";
        String tempColumnName = "";
        StringBuffer sb = new StringBuffer();

        label75:
        for(Iterator var10 = dataList.iterator(); var10.hasNext(); sb.append(tempColumnName.substring(0, tempColumnName.length() - 1) + " ) VALUES ( " + values.substring(0, values.length() - 1) + " ); \r\n")) {
            Map map4 = (Map)var10.next();
            tempColumnName = "";
            tempValues = "";
            values = "";
            sb.append("INSERT INTO " + tableName + " ( ");
            values = "";
            Iterator var12 = map4.entrySet().iterator();

            while(true) {
                while(true) {
                    if(!var12.hasNext()) {
                        continue label75;
                    }

                    Entry entry = (Entry)var12.next();
                    key = (String)entry.getKey();
                    tempColumnName = tempColumnName + key + ",";
                    if(entry.getValue() == null) {
                        values = values + "null,";
                    } else if(!((String)TableColumnType.get(key)).equals("date") && !((String)TableColumnType.get(key)).equals("datetime")) {
                        if(!((String)TableColumnType.get(key)).equals("int") && !((String)TableColumnType.get(key)).equals("smallint") && !((String)TableColumnType.get(key)).equals("tinyint") && !((String)TableColumnType.get(key)).equals("integer") && !((String)TableColumnType.get(key)).equals("bit") && !((String)TableColumnType.get(key)).equals("NUMBER") && !((String)TableColumnType.get(key)).equals("bigint") && !((String)TableColumnType.get(key)).equals("long") && !((String)TableColumnType.get(key)).equals("float") && !((String)TableColumnType.get(key)).equals("decimal") && !((String)TableColumnType.get(key)).equals("numeric") && !((String)TableColumnType.get(key)).equals("mediumint")) {
                            if(!((String)TableColumnType.get(key)).equals("binary") && !((String)TableColumnType.get(key)).equals("varbinary") && !((String)TableColumnType.get(key)).equals("blob") && !((String)TableColumnType.get(key)).equals("tinyblob") && !((String)TableColumnType.get(key)).equals("mediumblob") && !((String)TableColumnType.get(key)).equals("longblob")) {
                                tempValues = (String)entry.getValue();
                                tempValues = tempValues.replace("\'", "\\\'");
                                tempValues = tempValues.replace("\r\n", "\\r\\n");
                                tempValues = tempValues.replace("\n\r", "\\n\\r");
                                tempValues = tempValues.replace("\r", "\\r");
                                tempValues = tempValues.replace("\n", "\\n");
                                values = values + "\'" + tempValues + "\',";
                            } else {
                                byte[] ss = (byte[])entry.getValue();
                                if(ss.length == 0) {
                                    values = values + "null,";
                                } else {
                                    values = values + "0x" + this.bytesToHexString(ss) + ",";
                                }
                            }
                        } else {
                            values = values + entry.getValue() + ",";
                        }
                    } else {
                        values = values + "\'" + entry.getValue() + "\',";
                    }
                }
            }
        }

        return sb.toString();
    }

    public String dataListToStringForPostgreSQL(String tableName, Map<String, String> TableColumnType, List<Map<String, Object>> dataList) {
        String key = "";
        String values = "";
        String tempValues = "";
        String tempColumnName = "";
        StringBuffer sb = new StringBuffer();

        label77:
        for(Iterator var10 = dataList.iterator(); var10.hasNext(); sb.append(tempColumnName.substring(0, tempColumnName.length() - 1) + " ) VALUES ( " + values.substring(0, values.length() - 1) + " ); \r\n")) {
            Map map4 = (Map)var10.next();
            tempColumnName = "";
            tempValues = "";
            values = "";
            sb.append("INSERT INTO \"" + tableName + "\" ( ");
            values = "";
            Iterator var12 = map4.entrySet().iterator();

            while(true) {
                while(true) {
                    if(!var12.hasNext()) {
                        continue label77;
                    }

                    Entry entry = (Entry)var12.next();
                    key = (String)entry.getKey();
                    tempColumnName = tempColumnName + "\"" + key + "\",";
                    if(entry.getValue() == null) {
                        values = values + "null,";
                    } else if(!((String)TableColumnType.get(key)).equals("date") && !((String)TableColumnType.get(key)).equals("datetime") && !((String)TableColumnType.get(key)).equals("timestamp")) {
                        if(!((String)TableColumnType.get(key)).equals("int") && !((String)TableColumnType.get(key)).equals("smallint") && !((String)TableColumnType.get(key)).equals("tinyint") && !((String)TableColumnType.get(key)).equals("integer") && !((String)TableColumnType.get(key)).equals("bit") && !((String)TableColumnType.get(key)).equals("real") && !((String)TableColumnType.get(key)).equals("bigint") && !((String)TableColumnType.get(key)).equals("long") && !((String)TableColumnType.get(key)).equals("float") && !((String)TableColumnType.get(key)).equals("decimal") && !((String)TableColumnType.get(key)).equals("numeric") && !((String)TableColumnType.get(key)).equals("mediumint")) {
                            if(!((String)TableColumnType.get(key)).equals("binary") && !((String)TableColumnType.get(key)).equals("varbinary") && !((String)TableColumnType.get(key)).equals("blob") && !((String)TableColumnType.get(key)).equals("tinyblob") && !((String)TableColumnType.get(key)).equals("mediumblob") && !((String)TableColumnType.get(key)).equals("longblob")) {
                                tempValues = (String)entry.getValue();
                                tempValues = tempValues.replace("\'", "\\\'");
                                tempValues = tempValues.replace("\r\n", "\\r\\n");
                                tempValues = tempValues.replace("\n\r", "\\n\\r");
                                tempValues = tempValues.replace("\r", "\\r");
                                tempValues = tempValues.replace("\n", "\\n");
                                values = values + "\'" + tempValues + "\',";
                            } else {
                                byte[] ss = (byte[])entry.getValue();
                                if(ss.length == 0) {
                                    values = values + "null,";
                                } else {
                                    values = values + "0x" + this.bytesToHexString(ss) + ",";
                                }
                            }
                        } else {
                            values = values + entry.getValue() + ",";
                        }
                    } else {
                        values = values + "to_date( \'" + entry.getValue() + "\',\'YYYY-MM-DD HH24:MI:SS\'),";
                    }
                }
            }
        }

        return sb.toString();
    }

    public String dataListToStringForOracle(String tableName, Map<String, String> TableColumnType, List<Map<String, Object>> dataList) {
        String key = "";
        String values = "";
        String tempValues = "";
        String tempColumnName = "";
        StringBuffer sb = new StringBuffer();

        label81:
        for(Iterator var10 = dataList.iterator(); var10.hasNext(); sb.append(tempColumnName.substring(0, tempColumnName.length() - 1) + " ) VALUES ( " + values.substring(0, values.length() - 1) + " ); \r\n")) {
            Map map4 = (Map)var10.next();
            tempColumnName = "";
            tempValues = "";
            values = "";
            sb.append(" INSERT INTO " + tableName + " (");
            values = "";
            Iterator var12 = map4.entrySet().iterator();

            while(true) {
                while(true) {
                    Entry entry;
                    do {
                        if(!var12.hasNext()) {
                            continue label81;
                        }

                        entry = (Entry)var12.next();
                        key = (String)entry.getKey();
                        tempColumnName = tempColumnName + key + ",";
                    } while(key.equals("RN"));

                    if(entry.getValue() == null) {
                        values = values + "null,";
                    } else if(!((String)TableColumnType.get(key)).equals("DATE") && !((String)TableColumnType.get(key)).equals("DATETIME")) {
                        if(!((String)TableColumnType.get(key)).equals("int") && !((String)TableColumnType.get(key)).equals("smallint") && !((String)TableColumnType.get(key)).equals("tinyint") && !((String)TableColumnType.get(key)).equals("integer") && !((String)TableColumnType.get(key)).equals("bit") && !((String)TableColumnType.get(key)).equals("NUMBER") && !((String)TableColumnType.get(key)).equals("bigint") && !((String)TableColumnType.get(key)).equals("long") && !((String)TableColumnType.get(key)).equals("float") && !((String)TableColumnType.get(key)).equals("decimal") && !((String)TableColumnType.get(key)).equals("numeric") && !((String)TableColumnType.get(key)).equals("mediumint")) {
                            if(!((String)TableColumnType.get(key)).equals("binary") && !((String)TableColumnType.get(key)).equals("varbinary") && !((String)TableColumnType.get(key)).equals("blob") && !((String)TableColumnType.get(key)).equals("tinyblob") && !((String)TableColumnType.get(key)).equals("mediumblob") && !((String)TableColumnType.get(key)).equals("longblob")) {
                                tempValues = (String)entry.getValue();
                                tempValues = tempValues.replace("\'", "\\\'");
                                tempValues = tempValues.replace("\r\n", "\\r\\n");
                                tempValues = tempValues.replace("\n\r", "\\n\\r");
                                tempValues = tempValues.replace("\r", "\\r");
                                tempValues = tempValues.replace("\n", "\\n");
                                values = values + "\'" + tempValues + "\',";
                            } else {
                                byte[] ss = (byte[])entry.getValue();
                                if(ss.length == 0) {
                                    values = values + "null,";
                                } else {
                                    values = values + "0x" + this.bytesToHexString(ss) + ",";
                                }
                            }
                        } else {
                            values = values + entry.getValue() + ",";
                        }
                    } else {
                        values = values + "to_date( \'" + entry.getValue() + "\',\'YYYY-MM-DD HH24:MI:SS\'),";
                    }
                }
            }
        }

        return sb.toString();
    }

    public String dataListToStringForMySQL(String tableName, Map<String, String> TableColumnType, List<Map<String, Object>> dataList) {
        String key = "";
        String values = "";
        String tempValues = "";
        String tempColumnName = "";
        StringBuffer sb = new StringBuffer();

        label67:
        for(Iterator var10 = dataList.iterator(); var10.hasNext(); sb.append(tempColumnName.substring(0, tempColumnName.length() - 1) + " ) VALUES ( " + values.substring(0, values.length() - 1) + " ); \r\n")) {
            Map map2 = (Map)var10.next();
            tempColumnName = "";
            tempValues = "";
            values = "";
            sb.append(" INSERT INTO " + tableName + " (");
            Iterator var12 = map2.entrySet().iterator();

            while(true) {
                while(true) {
                    if(!var12.hasNext()) {
                        continue label67;
                    }

                    Entry entry = (Entry)var12.next();
                    key = (String)entry.getKey();
                    tempColumnName = tempColumnName + key + ",";
                    if(entry.getValue() == null) {
                        values = values + "null,";
                    } else if(!((String)TableColumnType.get(key)).equals("int") && !((String)TableColumnType.get(key)).equals("smallint") && !((String)TableColumnType.get(key)).equals("tinyint") && !((String)TableColumnType.get(key)).equals("integer") && !((String)TableColumnType.get(key)).equals("bit") && !((String)TableColumnType.get(key)).equals("bigint") && !((String)TableColumnType.get(key)).equals("double") && !((String)TableColumnType.get(key)).equals("float") && !((String)TableColumnType.get(key)).equals("decimal") && !((String)TableColumnType.get(key)).equals("numeric") && !((String)TableColumnType.get(key)).equals("mediumint")) {
                        if(!((String)TableColumnType.get(key)).equals("binary") && !((String)TableColumnType.get(key)).equals("varbinary") && !((String)TableColumnType.get(key)).equals("blob") && !((String)TableColumnType.get(key)).equals("tinyblob") && !((String)TableColumnType.get(key)).equals("mediumblob") && !((String)TableColumnType.get(key)).equals("longblob")) {
                            tempValues = (String)entry.getValue();
                            tempValues = tempValues.replace("\'", "\\\'");
                            tempValues = tempValues.replace("\r\n", "\\r\\n");
                            tempValues = tempValues.replace("\n\r", "\\n\\r");
                            tempValues = tempValues.replace("\r", "\\r");
                            tempValues = tempValues.replace("\n", "\\n");
                            values = values + "\'" + tempValues + "\',";
                        } else {
                            byte[] ss = (byte[])entry.getValue();
                            if(ss.length == 0) {
                                values = values + "null,";
                            } else {
                                values = values + "0x" + this.bytesToHexString(ss) + ",";
                            }
                        }
                    } else {
                        values = values + entry.getValue() + ",";
                    }
                }
            }
        }

        return sb.toString();
    }

    public String dataListToStringForMongoDB(List<Map<String, Object>> dataList) {
        String str = "";

        try {
            JSONArray e = JSONArray.parseArray(JSON.toJSONString(dataList));
            str = e.toString();
        } catch (Exception var4) {
            System.out.println(var4.getMessage());
        }

        return str;
    }

    @RequestMapping(
            value = {"i/copyTable/{databaseConfigId}"},
            method = {RequestMethod.POST}
    )
    @ResponseBody
    public Map<String, Object> copyTable(@RequestBody IdsDto tem, @PathVariable("databaseConfigId") String databaseConfigId, HttpServletRequest request) throws Exception {
        String databaseName = tem.getDatabaseName();
        String tableName = tem.getTableName();
        Map map = this.permissionService.getConfig(databaseConfigId);
        String databaseType = (String)map.get("databaseType");
        HashMap mapResult = new HashMap();
        String mess = "";
        String status = "";

        try {
            if(databaseType.equals("MySql")) {
                this.permissionService.copyTableForMySql(databaseName, tableName, databaseConfigId);
                Thread.sleep(3000L);
                mess = "复制表完成！";
                status = "success";
            }

            if(databaseType.equals("MariaDB")) {
                this.permissionService.copyTableForMySql(databaseName, tableName, databaseConfigId);
                Thread.sleep(3000L);
                mess = "复制表完成！";
                status = "success";
            }

            if(databaseType.equals("Oracle")) {
                this.permissionService.copyTableForOracle(databaseName, tableName, databaseConfigId);
                Thread.sleep(3000L);
                mess = "复制表完成！";
                status = "success";
            }

            if(databaseType.equals("PostgreSQL")) {
                this.permissionService.copyTableForPostgreSQL(databaseName, tableName, databaseConfigId);
                Thread.sleep(3000L);
                mess = "复制表完成！";
                status = "success";
            }

            if(databaseType.equals("MSSQL")) {
                mess = "暂不支持MSSQL复制表！";
                status = "fail";
            }

            if(databaseType.equals("Hive2")) {
                mess = "暂不支持Hive复制表！";
                status = "fail";
            }

            if(databaseType.equals("MongoDB")) {
                mess = "暂不支持MongoDB复制表！";
                status = "fail";
            }
        } catch (Exception var12) {
            mess = "复制表失败！";
            status = "fail";
        }

        mapResult.put("mess", mess);
        mapResult.put("status", status);
        return mapResult;
    }

    @RequestMapping(
            value = {"i/renameTable/{databaseConfigId}"},
            method = {RequestMethod.POST}
    )
    @ResponseBody
    public Map<String, Object> renameTable(@RequestBody IdsDto tem, @PathVariable("databaseConfigId") String databaseConfigId, HttpServletRequest request) throws Exception {
        String databaseName = tem.getDatabaseName();
        String tableName = tem.getTableName();
        String newTableName = tem.getNewTableName();
        Map map = this.permissionService.getConfig(databaseConfigId);
        String databaseType = (String)map.get("databaseType");
        HashMap mapResult = new HashMap();
        String mess = "";
        String status = "";

        try {
            if(databaseType.equals("MySql")) {
                this.permissionService.renameTableForMySql(databaseName, tableName, databaseConfigId, newTableName);
                Thread.sleep(3000L);
                mess = "操作完成！";
                status = "success";
            }

            if(databaseType.equals("MariaDB")) {
                this.permissionService.renameTableForMySql(databaseName, tableName, databaseConfigId, newTableName);
                Thread.sleep(3000L);
                mess = "操作完成！";
                status = "success";
            }

            if(databaseType.equals("Oracle")) {
                this.permissionService.renameTableForOracle(databaseName, tableName, databaseConfigId, newTableName);
                Thread.sleep(3000L);
                mess = "操作完成！";
                status = "success";
            }

            if(databaseType.equals("PostgreSQL")) {
                this.permissionService.renameTableForPostgreSQL(databaseName, tableName, databaseConfigId, newTableName);
                Thread.sleep(3000L);
                mess = "操作完成！";
                status = "success";
            }

            if(databaseType.equals("Hive2")) {
                this.permissionService.renameTableForHive2(databaseName, tableName, databaseConfigId, newTableName);
                Thread.sleep(3000L);
                mess = "操作完成！";
                status = "success";
            }

            if(databaseType.equals("MSSQL")) {
                mess = "暂不支持MSSQL重命名表！";
                status = "fail";
            }

            if(databaseType.equals("MongoDB")) {
                mess = "暂不支持MongoDB重命名表！";
                status = "fail";
            }
        } catch (Exception var13) {
            mess = "重命名表失败！";
            status = "fail";
        }

        mapResult.put("mess", mess);
        mapResult.put("status", status);
        return mapResult;
    }

    @RequestMapping(
            value = {"i/deleteBackupFile"},
            method = {RequestMethod.POST}
    )
    @ResponseBody
    public Map<String, Object> deleteBackupFile(@RequestBody IdsDto tem, HttpServletRequest request) {
        String[] ids = tem.getIds();
        String mess = "";
        String status = "";
        String path = request.getSession().getServletContext().getRealPath("/") + "backup" + File.separator;

        try {
            this.permissionService.deleteBackupFile(ids, path);
            mess = "删除成功";
            status = "success";
        } catch (Exception var8) {
            mess = var8.getMessage();
            status = "fail";
        }

        HashMap map = new HashMap();
        map.put("mess", mess);
        map.put("status", status);
        return map;
    }

    @RequestMapping(
            value = {"i/backupFileDownload/{fileName:.+}"},
            method = {RequestMethod.GET}
    )
    @ResponseBody
    public void backupFileDownload(@PathVariable("fileName") String fileName, HttpServletRequest request, HttpServletResponse response) {
        String path = request.getSession().getServletContext().getRealPath("/") + "backup" + File.separator;
        File file = new File(path + fileName);
        response.setContentType("multipart/form-data");
        response.addHeader("Content-Disposition", "attachment;fileName=" + fileName);
        response.addHeader("Content-Length", "" + file.length());

        try {
            FileInputStream e = new FileInputStream(file);
            ServletOutputStream out = response.getOutputStream();
            boolean b = false;
            byte[] buffer = new byte[1024];
            boolean len = false;

            int len1;
            while((len1 = e.read(buffer)) > 0) {
                out.write(buffer, 0, len1);
            }

            e.close();
            out.close();
            out.flush();
        } catch (Exception var11) {
            var11.printStackTrace();
        }

    }

    @RequestMapping(
            value = {"i/zipFile"},
            method = {RequestMethod.POST}
    )
    @ResponseBody
    public Map<String, Object> zipFile(@RequestBody IdsDto tem, HttpServletRequest request) {
        String path = request.getSession().getServletContext().getRealPath("/") + "backup" + File.separator;
        String mess = "";
        String status = "";
        String fileName = "";
        String[] ids = tem.getIds();

        try {
            for(int map = 0; map < ids.length; ++map) {
                fileName = ids[map];
                File srcFile = new File(path + fileName);
                File zipFile = new File(path + fileName.replaceAll(".sql", "") + ".zip");
                Project prj = new Project();
                Zip zip = new Zip();
                zip.setProject(prj);
                zip.setDestFile(zipFile);
                FileSet fileSet = new FileSet();
                fileSet.setProject(prj);
                fileSet.setFile(srcFile);
                zip.addFileset(fileSet);
                zip.execute();
            }

            mess = "压缩成功";
            status = "success";
        } catch (Exception var14) {
            this.logger.error("压缩文件时出错!" + var14.toString());
            mess = "压缩文件出错";
            status = "fail";
        }

        HashMap var15 = new HashMap();
        var15.put("mess", mess);
        var15.put("status", status);
        return var15;
    }

    @RequestMapping(
            value = {"i/unzipFile"},
            method = {RequestMethod.POST}
    )
    @ResponseBody
    public Map<String, Object> unzip(String zipFilepath, String destDir) {
        String mess = "";
        String status = "";

        try {
            if(!(new File(zipFilepath)).exists()) {
                throw new RuntimeException("zip file " + zipFilepath + " does not exist.");
            }

            Project map = new Project();
            Expand expand = new Expand();
            expand.setProject(map);
            expand.setTaskType("unzip");
            expand.setTaskName("unzip");
            expand.setSrc(new File(zipFilepath));
            expand.setDest(new File(destDir));
            expand.execute();
            mess = "解压缩成功";
            status = "success";
        } catch (Exception var7) {
            this.logger.error(":^^^^^^^^^^^^解压文件时出错^^^^^^^^^^^^^");
            this.logger.error(var7.toString());
            mess = "解压缩文件出错";
            status = "fail";
        }

        HashMap map1 = new HashMap();
        map1.put("mess", mess);
        map1.put("status", status);
        return map1;
    }

    @RequestMapping(
            value = {"i/dropTable/{databaseConfigId}"},
            method = {RequestMethod.POST}
    )
    @ResponseBody
    public Map<String, Object> dropTable(@RequestBody IdsDto tem, @PathVariable("databaseConfigId") String databaseConfigId, HttpServletRequest request) throws Exception {
        String mess = "";
        String status = "";
        HashMap map = new HashMap();
        String tableName = tem.getTableName();
        String databaseName = tem.getDatabaseName();
        HttpSession session = request.getSession(true);
        String username = (String)session.getAttribute("LOGIN_USER_NAME");
        String ip = NetworkUtil.getIpAddress(request);
        Map map0 = this.permissionService.getConfig(databaseConfigId);
        String databaseType = (String)map0.get("databaseType");
        boolean bool = true;

        try {
            if(tableName != null || !"".equals(tableName)) {
                if(databaseType.equals("MongoDB")) {
                    this.permissionService.dropTableForMongoDB(databaseName, tableName, databaseConfigId);
                } else if(databaseType.equals("Oracle")) {
                    this.permissionService.dropTableForOracle(databaseName, tableName, databaseConfigId);
                } else if(databaseType.equals("PostgreSQL")) {
                    this.permissionService.dropTableForPostgreSQL(databaseName, tableName, databaseConfigId);
                } else {
                    this.permissionService.dropTable(databaseName, tableName, databaseConfigId);
                }

                this.permissionService.saveLog("drop table " + databaseName + "." + tableName, username, ip);
            }

            mess = "删除成功";
            status = "success";
        } catch (Exception var16) {
            mess = "删除失败," + var16.getMessage();
            status = "fail";
        }

        map.put("mess", mess);
        map.put("status", status);
        return map;
    }

    @RequestMapping(
            value = {"i/dropDatabase/{databaseConfigId}"},
            method = {RequestMethod.POST}
    )
    @ResponseBody
    public Map<String, Object> dropDatabase(@RequestBody IdsDto tem, @PathVariable("databaseConfigId") String databaseConfigId, HttpServletRequest request) throws Exception {
        String mess = "";
        String status = "";
        HashMap map = new HashMap();
        String databaseName = tem.getDatabaseName();
        HttpSession session = request.getSession(true);
        String username = (String)session.getAttribute("LOGIN_USER_NAME");
        String ip = NetworkUtil.getIpAddress(request);
        Map map0 = this.permissionService.getConfig(databaseConfigId);
        String databaseType = (String)map0.get("databaseType");
        boolean bool = true;

        try {
            if(databaseType.equals("MongoDB")) {
                this.permissionService.dropDatabaseForMongoDB(databaseName, databaseConfigId);
            } else {
                this.permissionService.dropDatabase(databaseName, databaseConfigId);
            }

            mess = "删除成功";
            status = "success";
            this.permissionService.saveLog("drop database  " + databaseName, username, ip);
        } catch (Exception var15) {
            mess = "删除失败";
            status = "fail";
        }

        map.put("mess", mess);
        map.put("status", status);
        return map;
    }

    @RequestMapping(
            value = {"i/clearTable/{databaseConfigId}"},
            method = {RequestMethod.POST}
    )
    @ResponseBody
    public Map<String, Object> clearTable(@PathVariable("databaseConfigId") String databaseConfigId, @RequestBody IdsDto tem, HttpServletRequest request) throws Exception {
        String mess = "";
        String status = "";
        HashMap map = new HashMap();
        String tableName = tem.getTableName();
        String databaseName = tem.getDatabaseName();
        HttpSession session = request.getSession(true);
        String username = (String)session.getAttribute("LOGIN_USER_NAME");
        String ip = NetworkUtil.getIpAddress(request);
        Map map0 = this.permissionService.getConfig(databaseConfigId);
        String databaseType = (String)map0.get("databaseType");
        boolean bool = true;

        try {
            if(tableName != null || !"".equals(tableName)) {
                if(databaseType.equals("MongoDB")) {
                    this.permissionService.clearTableForMongoDB(databaseName, tableName, databaseConfigId);
                } else {
                    this.permissionService.clearTable(databaseName, tableName, databaseConfigId);
                }
            }

            this.permissionService.saveLog("delete from " + databaseName + "." + tableName, username, ip);
            mess = "删除成功";
            status = "success";
        } catch (Exception var16) {
            mess = "删除失败";
            status = "fail";
        }

        map.put("mess", mess);
        map.put("status", status);
        return map;
    }

    @RequestMapping(
            value = {"i/showTableMess/{tableName}/{databaseName}/{databaseConfigId}"},
            method = {RequestMethod.GET}
    )
    public String showTableMess(@PathVariable("tableName") String tableName, @PathVariable("databaseName") String databaseName, @PathVariable("databaseConfigId") String databaseConfigId, HttpServletRequest request) {
        request.setAttribute("databaseName", databaseName);
        request.setAttribute("tableName", tableName);
        request.setAttribute("databaseConfigId", databaseConfigId);
        return "system/showTableMess";
    }

    @RequestMapping(
            value = {"i/viewTableMess/{tableName}/{databaseName}/{databaseConfigId}"},
            method = {RequestMethod.GET}
    )
    @ResponseBody
    public Map<String, Object> viewTableMess(@PathVariable("tableName") String tableName, @PathVariable("databaseName") String databaseName, @PathVariable("databaseConfigId") String databaseConfigId, HttpServletRequest request) throws Exception {
        HashMap map = new HashMap();
        Map map0 = this.permissionService.getConfig(databaseConfigId);
        String databaseType = (String)map0.get("databaseType");
        Object listAllColumn = new ArrayList();
        if(databaseType.equals("MySql")) {
            listAllColumn = this.permissionService.viewTableMessForMySql(databaseName, tableName, databaseConfigId);
        }

        if(databaseType.equals("MariaDB")) {
            listAllColumn = this.permissionService.viewTableMessForMySql(databaseName, tableName, databaseConfigId);
        }

        if(databaseType.equals("Oracle")) {
            listAllColumn = this.permissionService.viewTableMessForOracle(databaseName, tableName, databaseConfigId);
        }

        if(databaseType.equals("PostgreSQL")) {
            listAllColumn = this.permissionService.viewTableMessForPostgreSQL(databaseName, tableName, databaseConfigId);
        }

        if(databaseType.equals("MSSQL")) {
            listAllColumn = this.permissionService.viewTableMessForMSSQL(databaseName, tableName, databaseConfigId);
        }

        if(databaseType.equals("Hive2")) {
            listAllColumn = this.permissionService.viewTableMessForHive2(databaseName, tableName, databaseConfigId);
        }

        if(databaseType.equals("MongoDB")) {
            listAllColumn = this.permissionService.viewTableMessForMongoDB(databaseName, tableName, databaseConfigId);
        }

        map.put("rows", listAllColumn);
        map.put("total", Integer.valueOf(((List)listAllColumn).size()));
        return map;
    }

    @RequestMapping(
            value = {"i/saveNewTable/{databaseConfigId}"},
            method = {RequestMethod.POST}
    )
    @ResponseBody
    public Map<String, Object> saveNewTable(@PathVariable("databaseConfigId") String databaseConfigId, HttpServletRequest request) throws Exception {
        HashMap mapResult = new HashMap();
        String mess = "";
        String status = "";
        Map map = this.permissionService.getConfig(databaseConfigId);
        String databaseType = (String)map.get("databaseType");
        String databaseName = request.getParameter("databaseName");
        String tableName = request.getParameter("tableName");
        String inserted = request.getParameter("inserted");

        try {
            JSONObject e = JSONObject.parseObject(inserted);
            JSONArray insertArray = e.getJSONArray("rows");
            if(databaseType.equals("MySql")) {
                this.permissionService.saveNewTable(insertArray, databaseName, tableName, databaseConfigId);
            }

            if(databaseType.equals("MariaDB")) {
                this.permissionService.saveNewTable(insertArray, databaseName, tableName, databaseConfigId);
            }

            if(databaseType.equals("Oracle")) {
                this.permissionService.saveNewTableForOracle(insertArray, databaseName, tableName, databaseConfigId);
            }

            if(databaseType.equals("PostgreSQL")) {
                this.permissionService.saveNewTableForPostgreSQL(insertArray, databaseName, tableName, databaseConfigId);
            }

            if(databaseType.equals("MSSQL")) {
                this.permissionService.saveNewTableForMSSQL(insertArray, databaseName, tableName, databaseConfigId);
            }

            if(databaseType.equals("MongoDB")) {
                this.permissionService.saveNewTableForMongoDB(insertArray, databaseName, tableName, databaseConfigId);
            }

            mess = "保存成功！";
            status = "success";
        } catch (Exception var13) {
            mess = "保存出错！" + var13.getMessage();
            status = "fail";
        }

        mapResult.put("mess", mess);
        mapResult.put("status", status);
        return mapResult;
    }

    @RequestMapping(
            value = {"i/restoreDB/{databaseConfigId}"},
            method = {RequestMethod.POST}
    )
    @ResponseBody
    public Map<String, Object> restoreDB(@RequestBody IdsDto tem, @PathVariable("databaseConfigId") String databaseConfigId, HttpServletRequest request) {
        String path = request.getSession().getServletContext().getRealPath("/") + "backup" + File.separator;
        String mess = "";
        String status = "";
        String fileName = "";
        String[] ids = tem.getIds();

        try {
            for(int map = 0; map < ids.length; ++map) {
                fileName = ids[map];
                File file = new File(path + fileName);
                int a;
                if(fileName.indexOf(".rar") > 0) {
                    FileUtil.unRarFile(path + fileName, path + "temp" + File.separator);
                    this.doImportDB(path + "temp" + File.separator);

                    for(a = 0; a < this.tempFileList.size(); ++a) {
                        this.permissionService.restoreDBFromFile(tem.getDatabaseName(), (String)this.tempFileList.get(a), databaseConfigId);
                    }
                }

                if(fileName.indexOf(".zip") > 0) {
                    FileUtil.unZipFiles(file, path + "temp" + File.separator);
                    this.doImportDB(path + "temp" + File.separator);

                    for(a = 0; a < this.tempFileList.size(); ++a) {
                        this.permissionService.restoreDBFromFile(tem.getDatabaseName(), (String)this.tempFileList.get(a), databaseConfigId);
                    }
                }

                if(fileName.indexOf(".sql") > 0 || fileName.indexOf(".SQL") > 0) {
                    this.permissionService.restoreDBFromFile(tem.getDatabaseName(), path + fileName, databaseConfigId);
                }
            }

            mess = "数据库还原成功";
            status = "success";
        } catch (Exception var12) {
            System.out.println(var12.getMessage());
            mess = "数据库还原出错!";
            status = "fail";
        }

        HashMap var13 = new HashMap();
        var13.put("mess", mess);
        var13.put("status", status);
        return var13;
    }

    public void doImportDB(String path) {
        File file = new File(path);
        this.getSqlFile(file);
    }

    public void getSqlFile(File f) {
        if(f != null) {
            String fileName = f.getName();
            if(f.isDirectory()) {
                File[] fileArray = f.listFiles();
                if(fileArray != null) {
                    File[] var7 = fileArray;
                    int var6 = fileArray.length;

                    for(int var5 = 0; var5 < var6; ++var5) {
                        File file = var7[var5];
                        this.getSqlFile(file);
                    }
                }
            } else if(fileName.indexOf(".sql") > 0 || fileName.indexOf(".SQL") > 0) {
                System.out.println(f.getAbsolutePath());
                this.tempFileList.add(f.getAbsolutePath());
            }
        }

    }

    @RequestMapping(
            value = {"i/deleteConfig"},
            method = {RequestMethod.POST}
    )
    @ResponseBody
    public Map<String, Object> deleteConfig(@RequestBody IdsDto tem, HttpServletRequest request) {
        String[] ids = tem.getIds();
        String mess = "";
        String status = "";
        new StringBuffer();

        try {
            boolean map = this.permissionService.isTheConfigUsed(ids);
            if(map) {
                throw new Exception("数据库配置被引用,不允许删除!");
            }

            this.permissionService.deleteConfig(ids);
            mess = "删除成功";
            status = "success";
        } catch (Exception var8) {
            mess = var8.getMessage();
            status = "fail";
        }

        HashMap map1 = new HashMap();
        map1.put("mess", mess);
        map1.put("status", status);
        return map1;
    }

    @RequestMapping(
            value = {"i/getDataBaseConfig/{databaseConfigId}"},
            method = {RequestMethod.GET}
    )
    @ResponseBody
    public Map<String, Object> getDataBaseConfig(@PathVariable("databaseConfigId") String databaseConfigId, HttpServletRequest request) throws Exception {
        Map map = this.permissionService.getConfig(databaseConfigId);
        return map;
    }

    @RequestMapping(
            value = {"i/exportDataToSQLFromSQL"},
            method = {RequestMethod.POST}
    )
    @ResponseBody
    public void exportDataToSQLFromSQL(HttpServletRequest request, HttpServletResponse response) throws Exception {
        String sql = request.getParameter("sql");
        String exportType = request.getParameter("exportType");
        String databaseName = request.getParameter("databaseName");
        String databaseConfigId = request.getParameter("databaseConfigId");
        Map map = this.permissionService.getConfig(databaseConfigId);
        String databaseType = (String)map.get("databaseType");
        Object dataList = new ArrayList();

        try {
            if(databaseType.equals("MySql")) {
                dataList = this.permissionService.selectAllDataFromSQLForMysql(databaseName, databaseConfigId, sql);
            }

            if(databaseType.equals("MariaDB")) {
                dataList = this.permissionService.selectAllDataFromSQLForMysql(databaseName, databaseConfigId, sql);
            }

            if(databaseType.equals("Oracle")) {
                dataList = this.permissionService.selectAllDataFromSQLForOracle(databaseName, databaseConfigId, sql);
            }

            if(databaseType.equals("PostgreSQL")) {
                dataList = this.permissionService.selectAllDataFromSQLForPostgreSQL(databaseName, databaseConfigId, sql);
            }

            if(databaseType.equals("MSSQL")) {
                dataList = this.permissionService.selectAllDataFromSQLForMSSQL(databaseName, databaseConfigId, sql);
            }

            if(databaseType.equals("Hive2")) {
                dataList = this.permissionService.selectAllDataFromSQLForHive2(databaseName, databaseConfigId, sql);
            }

            if(databaseType.equals("MongoDB")) {
                dataList = this.permissionService.selectAllDataFromSQLForMongoDBForExport(databaseName, databaseConfigId, sql);
            }

            if(exportType.equals("json")) {
                String e = "";
                if(databaseType.equals("MySql")) {
                    e = this.dataListToTxt((List)dataList);
                }

                if(databaseType.equals("MariaDB")) {
                    e = this.dataListToTxt((List)dataList);
                }

                if(databaseType.equals("Oracle")) {
                    e = this.dataListToTxt((List)dataList);
                }

                if(databaseType.equals("PostgreSQL")) {
                    e = this.dataListToTxt((List)dataList);
                }

                if(databaseType.equals("MSSQL")) {
                    e = this.dataListToTxt((List)dataList);
                }

                if(databaseType.equals("Hive2")) {
                    e = this.dataListToTxt((List)dataList);
                }

                if(databaseType.equals("MongoDB")) {
                    e = this.dataListToTxt((List)dataList);
                }

                response.setContentType("multipart/form-data");
                response.addHeader("Content-Disposition", "attachment;fileName=data.txt ");
                ByteArrayInputStream inputStream = new ByteArrayInputStream(e.getBytes());
                ServletOutputStream out = response.getOutputStream();
                boolean b = false;
                byte[] buffer = new byte[1024];
                boolean len = false;

                int len1;
                while((len1 = inputStream.read(buffer)) > 0) {
                    out.write(buffer, 0, len1);
                }

                inputStream.close();
                out.close();
                out.flush();
            }

            if(exportType.equals("excel")) {
                ExportExcelUtils.downloadExcel(response, (List)dataList);
            }
        } catch (Exception var16) {
            System.out.println("导出数据失败 " + var16.getMessage());
        }

    }

    public String bytesToHexString(byte[] src) {
        StringBuilder stringBuilder = new StringBuilder("");
        if(src != null && src.length > 0) {
            for(int i = 0; i < src.length; ++i) {
                int v = src[i] & 255;
                String hv = Integer.toHexString(v);
                if(hv.length() < 2) {
                    stringBuilder.append(0);
                }

                stringBuilder.append(hv);
            }

            return stringBuilder.toString();
        } else {
            return null;
        }
    }

    public String dataListToTxt(List<Map<String, Object>> dataList) {
        StringBuffer sb = new StringBuffer();
        Iterator var4 = dataList.iterator();

        while(var4.hasNext()) {
            Map map4 = (Map)var4.next();
            sb.append(JSONArray.toJSONString(map4) + " \r\n");
        }

        return sb.toString();
    }

    public String dataListToTable(List<Map<String, Object>> dataList) {
        StringBuffer sb = new StringBuffer();
        sb.append("<html> <head> <title> Excel </title> </head>  <body>  <table border=\"1\" cellspacing=\"0\" > ");
        Iterator var4 = dataList.iterator();

        while(var4.hasNext()) {
            Map map4 = (Map)var4.next();
            sb.append(" <tr> ");
            Iterator var6 = map4.entrySet().iterator();

            while(var6.hasNext()) {
                Entry vo = (Entry)var6.next();
                sb.append(" <tb> " + vo.getValue() + " </td> ");
            }

            sb.append("</tr>");
        }

        sb.append(" </table></body> </html>");
        return sb.toString();
    }

    @RequestMapping(
            value = {"i/dataSynchronize"},
            method = {RequestMethod.GET}
    )
    public String dataSynchronize(Model model) {
        return "system/dataSynchronizeList";
    }

    @RequestMapping(
            value = {"i/dataSynchronizeList"},
            method = {RequestMethod.GET}
    )
    @ResponseBody
    public Map<String, Object> dataSynchronizeList(HttpServletRequest request) throws Exception {
        Page page = this.getPage(request);

        try {
            page = this.permissionService.dataSynchronizeList(page);
        } catch (Exception var4) {
            return this.getEasyUIData(page);
        }

        return this.getEasyUIData(page);
    }

    @RequestMapping(
            value = {"i/addDataSynchronizeForm"},
            method = {RequestMethod.GET}
    )
    public String addDataSynchronizeForm(Model model) throws Exception {
        List configList = this.permissionService.getAllConfigList();
        model.addAttribute("configList", configList);
        return "system/dataSynchronizeForm";
    }

    @RequestMapping(
            value = {"i/editDataSynchronizeForm/{id}"},
            method = {RequestMethod.GET}
    )
    public String editDataSynchronizeForm(@PathVariable("id") String id, Model model) throws Exception {
        Object map = new HashMap();
        List configList = this.permissionService.getAllConfigList();

        try {
            map = this.permissionService.getDataSynchronize(id);
        } catch (Exception var6) {
            ;
        }

        model.addAttribute("configList", configList);
        model.addAttribute("dataSynchronize", map);
        return "system/dataSynchronizeForm";
    }

    @RequestMapping(
            value = {"i/dataSynchronizeUpdate"},
            method = {RequestMethod.POST}
    )
    @ResponseBody
    public Map<String, Object> dataSynchronizeUpdate(@ModelAttribute @RequestBody DataSynchronize dataSynchronize, Model model) {
        String mess = "";
        String status = "";

        try {
            String map = dataSynchronize.getId();
            String status2 = dataSynchronize.getStatus();
            String cron = dataSynchronize.getCron();
            String state = dataSynchronize.getState();
            if(state.equals("1")) {
                dataSynchronize.setStatus("0");
            }

            this.permissionService.dataSynchronizeUpdate(dataSynchronize);
            if(!map.equals("")) {
                if(status2.equals("1")) {
                    Map job = this.permissionService.getDataSynchronizeById2(map);
                    QuartzManager.removeJob(map);
                    this.quartzManager.addJob(map, QuartzJobFactory.class, cron, job);
                }

                if(state.equals("1")) {
                    QuartzManager.removeJob(map);
                }
            }

            mess = "修改成功";
            status = "success";
        } catch (Exception var10) {
            mess = "error:" + var10.getMessage();
            status = "fail";
        }

        HashMap map1 = new HashMap();
        map1.put("mess", mess);
        map1.put("status", status);
        return map1;
    }

    @RequestMapping(
            value = {"i/deleteDataSynchronize"},
            method = {RequestMethod.POST}
    )
    @ResponseBody
    public Map<String, Object> deleteDataSynchronize(@RequestBody IdsDto tem, HttpServletRequest request) {
        String[] ids = tem.getIds();
        String mess = "";
        String status = "";

        try {
            this.permissionService.deleteDataSynchronize(ids);
            String[] var9 = ids;
            int var8 = ids.length;

            for(int var7 = 0; var7 < var8; ++var7) {
                String map = var9[var7];
                QuartzManager.removeJob(map);
                this.permissionService.deleteDataSynchronizeLogByDS(map);
            }

            mess = "删除成功";
            status = "success";
        } catch (Exception var10) {
            mess = var10.getMessage();
            status = "fail";
        }

        HashMap var11 = new HashMap();
        var11.put("mess", mess);
        var11.put("status", status);
        return var11;
    }

    @RequestMapping(
            value = {"i/startDataTask"},
            method = {RequestMethod.POST}
    )
    @ResponseBody
    public Map<String, Object> startDataTask(@RequestBody IdsDto tem, HttpServletRequest request) {
        String[] ids = tem.getIds();
        String mess = "";
        String status = "";
        String state = "";
        String dataSynchronizeId = "";

        try {
            List map = this.permissionService.getDataSynchronizeListById(ids);
            Iterator var10 = map.iterator();

            while(var10.hasNext()) {
                Map job = (Map)var10.next();
                dataSynchronizeId = "" + job.get("id");
                state = "" + job.get("state");
                if(state.equals("1")) {
                    throw new Exception("启用状态的任务才能运行！");
                }

                QuartzManager.removeJob(dataSynchronizeId);
                this.quartzManager.addJob(dataSynchronizeId, QuartzJobFactory.class, "" + job.get("cron"), job);
                this.permissionService.dataSynchronizeUpdateStatus(dataSynchronizeId, "1");
            }

            mess = "任务已提交!";
            status = "success";
        } catch (Exception var11) {
            System.out.println(var11.getMessage());
            this.permissionService.dataSynchronizeUpdateStatus(dataSynchronizeId, "0");
            mess = var11.getMessage();
            status = "fail";
        }

        HashMap map1 = new HashMap();
        map1.put("mess", mess);
        map1.put("status", status);
        return map1;
    }

    @RequestMapping(
            value = {"i/startDataTaskOne"},
            method = {RequestMethod.POST}
    )
    @ResponseBody
    public Map<String, Object> startDataTaskOne(@RequestBody IdsDto tem, HttpServletRequest request) {
        String[] ids = tem.getIds();
        String mess = "";
        String status = "";
        String state = "";
        String dataSynchronizeId = "";

        try {
            List map = this.permissionService.getDataSynchronizeListById(ids);
            Iterator var10 = map.iterator();

            while(var10.hasNext()) {
                Map job = (Map)var10.next();
                dataSynchronizeId = "" + job.get("id");
                this.quartzManager.addJobOne(dataSynchronizeId, QuartzJobFactory.class, job);
            }

            mess = "任务已提交!";
            status = "success";
        } catch (Exception var11) {
            System.out.println(var11.getMessage());
            this.permissionService.dataSynchronizeUpdateStatus(dataSynchronizeId, "0");
            mess = var11.getMessage();
            status = "fail";
        }

        HashMap map1 = new HashMap();
        map1.put("mess", mess);
        map1.put("status", status);
        return map1;
    }

    @RequestMapping(
            value = {"i/stopDataTask"},
            method = {RequestMethod.POST}
    )
    @ResponseBody
    public Map<String, Object> stopDataTask(@RequestBody IdsDto tem, HttpServletRequest request) {
        String[] ids = tem.getIds();
        String mess = "";
        String status = "";
        String dataSynchronizeId = "";

        try {
            List map = this.permissionService.getDataSynchronizeListById(ids);
            Iterator var9 = map.iterator();

            while(var9.hasNext()) {
                Map job = (Map)var9.next();
                dataSynchronizeId = "" + job.get("id");
                QuartzManager.removeJob(dataSynchronizeId);
                this.permissionService.dataSynchronizeUpdateStatus(dataSynchronizeId, "0");
            }

            mess = "操作成功!";
            status = "success";
        } catch (Exception var10) {
            System.out.println(var10.getMessage());
            this.permissionService.dataSynchronizeUpdateStatus(dataSynchronizeId, "0");
            mess = var10.getMessage();
            status = "fail";
        }

        HashMap map1 = new HashMap();
        map1.put("mess", mess);
        map1.put("status", status);
        return map1;
    }

    @RequestMapping(
            value = {"i/dataSynchronizeLogForm/{dataSynchronizeId}"},
            method = {RequestMethod.GET}
    )
    public String dataSynchronizeLogForm(@PathVariable("dataSynchronizeId") String dataSynchronizeId, HttpServletRequest request) throws Exception {
        request.setAttribute("dataSynchronizeId", dataSynchronizeId);
        return "system/dataSynchronizeLogForm";
    }

    @RequestMapping(
            value = {"i/dataSynchronizeLogList/{dataSynchronizeId}"},
            method = {RequestMethod.GET}
    )
    @ResponseBody
    public Map<String, Object> dataSynchronizeLogList(@PathVariable("dataSynchronizeId") String dataSynchronizeId, HttpServletRequest request) throws Exception {
        Page page = this.getPage(request);

        try {
            page = this.permissionService.dataSynchronizeLogList(page, dataSynchronizeId);
        } catch (Exception var5) {
            return this.getEasyUIData(page);
        }

        return this.getEasyUIData(page);
    }

    @RequestMapping(
            value = {"i/deleteDataSynchronizeLog"},
            method = {RequestMethod.POST}
    )
    @ResponseBody
    public Map<String, Object> deleteDataSynchronizeLog(@RequestBody IdsDto tem, HttpServletRequest request) {
        String[] ids = tem.getIds();
        String mess = "";
        String status = "";

        try {
            this.permissionService.deleteDataSynchronizeLog(ids);
            mess = "删除成功";
            status = "success";
        } catch (Exception var7) {
            mess = var7.getMessage();
            status = "fail";
        }

        HashMap map = new HashMap();
        map.put("mess", mess);
        map.put("status", status);
        return map;
    }

    @RequestMapping(
            value = {"i/treeShowData/{tableName}/{dbName}/{databaseConfigId}"},
            method = {RequestMethod.POST}
    )
    @ResponseBody
    public Map<String, Object> treeShowData(@PathVariable("tableName") String tableName, @PathVariable("dbName") String dbName, @PathVariable("databaseConfigId") String databaseConfigId, @RequestBody Page<Map<String, Object>> page, HttpServletRequest request) throws Exception {
        System.out.println("page.getPageSize()=" + page.getPageSize());
        System.out.println("page.getPageNo()=" + page.getPageNo());
        HashMap map2 = new HashMap();
        String mess = "";
        String status = "";

        try {
            page = this.permissionService.getDataForMongoJson(page, tableName, dbName, databaseConfigId);
            map2.put("operator", "read");
            mess = "执行完成！";
            status = "success";
            map2.put("rows", page.getResult());
            map2.put("total", Long.valueOf(page.getTotalCount()));
            map2.put("columns", page.getColumns());
            map2.put("primaryKey", page.getPrimaryKey());
            map2.put("totalCount", Long.valueOf(page.getTotalCount()));
            map2.put("mess", mess);
            map2.put("status", status);
            return map2;
        } catch (Exception var10) {
            mess = var10.getMessage();
            status = "fail";
            map2.put("mess", mess);
            map2.put("status", status);
            return map2;
        }
    }
}
