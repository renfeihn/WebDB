//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package org.springframework.base.system.dao;

import com.alibaba.fastjson.JSONArray;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mongodb.BasicDBObject;
import com.mongodb.CommandResult;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.result.DeleteResult;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.sql.Time;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.TreeSet;
import java.util.UUID;
import java.util.Map.Entry;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import net.sf.json.JSONObject;
import org.apache.commons.lang3.StringEscapeUtils;
import org.apache.log4j.Logger;
import org.bson.Document;
import org.bson.types.ObjectId;
import org.springframework.base.common.persistence.Page;
import org.springframework.base.common.utils.DateUtils;
import org.springframework.base.system.dao.BackDbForMSSQL;
import org.springframework.base.system.dao.BackDbForMySql;
import org.springframework.base.system.dao.BackDbForOracle;
import org.springframework.base.system.dao.BackDbForPostgreSQL;
import org.springframework.base.system.entity.Config;
import org.springframework.base.system.entity.DataSynchronize;
import org.springframework.base.system.utils.CryptoUtil;
import org.springframework.base.system.utils.DBUtil;
import org.springframework.base.system.utils.DBUtil2;
import org.springframework.base.system.utils.MongoDBUtil;
import org.springframework.stereotype.Repository;

@Repository
public class PermissionDao {
    Logger logger = Logger.getLogger(PermissionDao.class);

    public PermissionDao() {
    }

    public List<Map<String, Object>> getAllDataBase(String databaseConfigId) throws Exception {
        Map map0 = this.getConfig(databaseConfigId);
        String databaseName = (String)map0.get("databaseName");
        String sql = " select * from  information_schema.schemata  ";
        DBUtil2 db = new DBUtil2(databaseName, databaseConfigId);
        List list = db.queryForList(sql);
        ArrayList list2 = new ArrayList();
        HashMap map = new HashMap();
        map.put("SCHEMA_NAME", databaseName);
        list2.add(map);

        for(int i = 0; i < list.size(); ++i) {
            Map map2 = (Map)list.get(i);
            String schema_name = (String)map2.get("SCHEMA_NAME");
            if(!schema_name.equals("mysql") && !schema_name.equals("information_schema") && !schema_name.equals("performance_schema") && !schema_name.equals(databaseName)) {
                if(schema_name.equals("ensemble")){
                    map2.put("SCHEMA_NAME","dcits_gn_core");
                }
                list2.add(map2);
            }

        }

        return list2;
    }

    public List<Map<String, Object>> getConfigAllDataBase() throws Exception {
        DBUtil db = new DBUtil();
        String sql = " select id, name, databaseType ,databaseName, port, ip  from  treesoft_config order by isdefault desc ";
        List list = db.executeQuery(sql);
        return list;
    }

    public List<Map<String, Object>> getAllDataBaseById(String datascope) throws Exception {
        DBUtil db = new DBUtil();
        String sql = " select id, name, databaseType ,databaseName, port, ip  from  treesoft_config where id in (" + datascope + ") order by isdefault desc ";
        List list = db.executeQuery(sql);
        return list;
    }

    public List<Map<String, Object>> getAllTables(String dbName, String databaseConfigId) throws Exception {
        DBUtil2 db = new DBUtil2(dbName, databaseConfigId);
        String sql = " select TABLE_NAME from information_schema.TABLES where table_schema=\'" + dbName + "\' and table_type=\'BASE TABLE\' ";
        List list = db.queryForList(sql);
        return list;
    }

    public List<Map<String, Object>> getAllTables2(String dbName, String tableName, String databaseConfigId) throws Exception {
        DBUtil2 db = new DBUtil2(dbName, databaseConfigId);
        String sql = " select * from information_schema.TABLES where table_schema=\'" + dbName + "\' and table_type=\'BASE TABLE\' ";
        if(tableName.equals("")) {
            sql = " select TABLE_NAME,ENGINE, VERSION,ROW_FORMAT,TABLE_ROWS,AUTO_INCREMENT,TABLE_COLLATION,TABLE_COMMENT  from information_schema.TABLES where table_schema=\'" + dbName + "\' and table_type=\'BASE TABLE\' ";
        } else {
            sql = " select TABLE_NAME,ENGINE, VERSION,ROW_FORMAT,TABLE_ROWS,AUTO_INCREMENT,TABLE_COLLATION,TABLE_COMMENT  from information_schema.TABLES where table_schema=\'" + dbName + "\' and  table_name=\'" + tableName + "\'  and table_type=\'BASE TABLE\' ";
        }

        List list = db.queryForList(sql);
        return list;
    }

    public List<Map<String, Object>> getAllViews(String dbName, String databaseConfigId) throws Exception {
        String sql = " select TABLE_NAME   from information_schema.TABLES where table_schema=\'" + dbName + "\' and table_type=\'VIEW\' ";
        DBUtil2 db = new DBUtil2(dbName, databaseConfigId);
        List list = db.queryForList(sql);
        return list;
    }

    public List<Map<String, Object>> getAllFuntion(String dbName, String databaseConfigId) throws Exception {
        String sql = " select ROUTINE_NAME   from information_schema.ROUTINES where routine_schema=\'" + dbName + "\' ";
        DBUtil2 db = new DBUtil2(dbName, databaseConfigId);
        List list = db.queryForList(sql);
        return list;
    }

    public List<Map<String, Object>> getTableColumns(String dbName, String tableName, String databaseConfigId) throws Exception {
        String sql = "select * from  `" + dbName + "`.`" + tableName + "` limit 1 ";
        new DBUtil2(dbName, databaseConfigId);
        List list = DBUtil2.queryForColumnOnly(sql);
        return list;
    }

    public List<Map<String, Object>> getTableColumns3(String dbName, String tableName, String databaseConfigId) throws Exception {
        String sql = " select column_name as TREESOFTPRIMARYKEY, COLUMN_NAME,COLUMN_TYPE , DATA_TYPE ,COLUMN_DEFAULT ,CHARACTER_MAXIMUM_LENGTH, NUMERIC_PRECISION ,NUMERIC_SCALE , IS_NULLABLE, COLUMN_KEY,extra, COLUMN_COMMENT  from information_schema.columns where   table_name=\'" + tableName + "\' and table_schema=\'" + dbName + "\'  ";
        DBUtil2 db = new DBUtil2(dbName, databaseConfigId);
        List list = db.queryForList(sql);
        return list;
    }

    public List<Map<String, Object>> getTableIndexForMySQL(String dbName, String tableName, String databaseConfigId) throws Exception {
        String sql = " SELECT non_unique,index_name, column_name  from  information_schema.statistics where table_schema = \'" + dbName + "\' and  table_name =\'" + tableName + "\'  and  index_name <>\'PRIMARY\'  order by index_name,seq_in_index;  ";
        DBUtil2 db = new DBUtil2(dbName, databaseConfigId);
        List list = db.queryForList(sql);
        return list;
    }

    public Page<Map<String, Object>> getData(Page<Map<String, Object>> page, String tableName, String dbName, String databaseConfigId) throws Exception {
        int pageNo = page.getPageNo();
        int pageSize = page.getPageSize();
        int limitFrom = (pageNo - 1) * pageSize;
        String orderBy = page.getOrderBy();
        String order = page.getOrder();
        DBUtil2 db = new DBUtil2(dbName, databaseConfigId);
        List list3 = this.getPrimaryKeyss(dbName, tableName, databaseConfigId);
        String tem = "";

        Map primaryKey;
        for(Iterator sql = list3.iterator(); sql.hasNext(); tem = tem + primaryKey.get("column_name") + ",") {
            primaryKey = (Map)sql.next();
        }

        String primaryKey1 = "";
        if(!tem.equals("")) {
            primaryKey1 = tem.substring(0, tem.length() - 1);
        }

        String sql1 = "select count(*) from  `" + dbName + "`.`" + tableName + "`";
        String sql2 = "";
        if(orderBy != null && !orderBy.equals("")) {
            sql2 = "select  *  from  `" + dbName + "`.`" + tableName + "` order by " + orderBy + " " + order + "  LIMIT " + limitFrom + "," + pageSize;
        } else {
            sql2 = "select  *  from  `" + dbName + "`.`" + tableName + "`  LIMIT " + limitFrom + "," + pageSize;
        }

        List list = db.queryForList(sql2);
        int rowCount = DBUtil2.executeQueryForCount(sql1);
        List columns = this.getTableColumns(dbName, tableName, databaseConfigId);
        ArrayList tempList = new ArrayList();
        HashMap map1 = new HashMap();
        map1.put("field", "treeSoftPrimaryKey");
        map1.put("checkbox", Boolean.valueOf(true));
        tempList.add(map1);

        HashMap map2;
        for(Iterator jsonfromList = columns.iterator(); jsonfromList.hasNext(); tempList.add(map2)) {
            Map mapper = (Map)jsonfromList.next();
            map2 = new HashMap();
            map2.put("field", mapper.get("column_name"));
            map2.put("title", mapper.get("column_name"));
            map2.put("sortable", Boolean.valueOf(true));
            if(mapper.get("data_type").equals("DATETIME")) {
                map2.put("editor", "datetimebox");
            } else if(!mapper.get("data_type").equals("INT") && !mapper.get("data_type").equals("SMALLINT") && !mapper.get("data_type").equals("TINYINT")) {
                if(mapper.get("data_type").equals("DOUBLE")) {
                    map2.put("editor", "numberbox");
                } else if(!mapper.get("data_type").equals("BLOB") && !mapper.get("data_type").equals("CLOB") && !mapper.get("data_type").equals("VARBINARY") && !mapper.get("data_type").equals("BINARY")) {
                    map2.put("editor", "text");
                }
            } else {
                map2.put("editor", "numberbox");
            }
        }

        ObjectMapper mapper1 = new ObjectMapper();
        String jsonfromList1 = "[" + mapper1.writeValueAsString(tempList) + "]";
        page.setTotalCount((long)rowCount);
        page.setResult(list);
        page.setColumns(jsonfromList1);
        page.setPrimaryKey(primaryKey1);
        return page;
    }

    public Page<Map<String, Object>> executeSql(Page<Map<String, Object>> page, String sql, String dbName, String databaseConfigId) throws Exception {
        int pageNo = page.getPageNo();
        int pageSize = page.getPageSize();
        int limitFrom = (pageNo - 1) * pageSize;
        sql = sql.trim();
        String sql2 = " select * from ( " + sql + " ) tab  LIMIT " + limitFrom + "," + pageSize;
        if(sql.indexOf("show") == 0 || sql.indexOf("SHOW") == 0) {
            sql2 = sql;
        }

        DBUtil2 db = new DBUtil2(dbName, databaseConfigId);
        List list = db.queryForList(sql2);
        int rowCount = DBUtil2.executeQueryForCount2(sql);
        List columns = this.executeSqlForColumns(sql, dbName, databaseConfigId);
        ArrayList tempList = new ArrayList();

        HashMap temp;
        for(Iterator tableName = columns.iterator(); tableName.hasNext(); tempList.add(temp)) {
            Map primaryKey = (Map)tableName.next();
            temp = new HashMap();
            temp.put("field", primaryKey.get("column_name"));
            temp.put("title", primaryKey.get("column_name"));
            temp.put("sortable", Boolean.valueOf(true));
            if(primaryKey.get("data_type").equals("DATETIME")) {
                temp.put("editor", "datetimebox");
            } else if(!primaryKey.get("data_type").equals("INT") && !primaryKey.get("data_type").equals("SMALLINT") && !primaryKey.get("data_type").equals("TINYINT")) {
                if(primaryKey.get("data_type").equals("DOUBLE")) {
                    temp.put("editor", "numberbox");
                } else if(!primaryKey.get("data_type").equals("BLOB") && !primaryKey.get("data_type").equals("CLOB") && !primaryKey.get("data_type").equals("VARBINARY") && !primaryKey.get("data_type").equals("BINARY")) {
                    temp.put("editor", "text");
                }
            } else {
                temp.put("editor", "numberbox");
            }
        }

        String var23 = "";
        String var24 = "";
        String var25 = "";
        if(this.checkSqlIsOneTableForMySql(dbName, sql, databaseConfigId)) {
            Pattern mapper = Pattern.compile("\\s+");
            Matcher jsonfromList = mapper.matcher(sql);
            var25 = jsonfromList.replaceAll(" ");
            var25 = var25.toLowerCase();

            String tem;
            for(int list3 = 14; list3 < var25.length(); ++list3) {
                tem = String.valueOf(var25.charAt(list3));
                if(tem.equals(" ")) {
                    break;
                }

                var24 = var24 + tem;
            }

            List var28 = this.getPrimaryKeyss(dbName, var24, databaseConfigId);
            tem = "";

            Map map3;
            for(Iterator var22 = var28.iterator(); var22.hasNext(); tem = tem + map3.get("column_name") + ",") {
                map3 = (Map)var22.next();
            }

            if(!tem.equals("")) {
                var23 = tem.substring(0, tem.length() - 1);
            }
        }

        ObjectMapper var26 = new ObjectMapper();
        String var27 = "[" + var26.writeValueAsString(tempList) + "]";
        page.setTotalCount((long)rowCount);
        page.setResult(list);
        page.setColumns(var27);
        page.setPrimaryKey(var23);
        page.setTableName(var24);
        return page;
    }

    public List<Map<String, Object>> selectAllDataFromSQLForMysql(String databaseName, String databaseConfigId, String sql) throws Exception {
        DBUtil2 db = new DBUtil2(databaseName, databaseConfigId);
        List list = db.queryForList(sql);
        return list;
    }

    public boolean checkSqlIsOneTableForMySql(String dbName, String sql, String databaseConfigId) {
        String temp = "";
        String tableName = "";

        try {
            DBUtil2 e = new DBUtil2(dbName, databaseConfigId);
            Pattern p = Pattern.compile("\\s+");
            Matcher m = p.matcher(sql);
            temp = m.replaceAll(" ");
            temp = temp.toLowerCase();
            if(temp.indexOf("select * from") >= 0) {
                for(int sql2 = 14; sql2 < temp.length(); ++sql2) {
                    String list = String.valueOf(temp.charAt(sql2));
                    if(list.equals(" ")) {
                        break;
                    }

                    tableName = tableName + list;
                }

                String var13 = " select TABLE_NAME from information_schema.TABLES where table_schema=\'" + dbName + "\' and table_type=\'BASE TABLE\' and  TABLE_NAME =\'" + tableName + "\'";
                List var12 = e.queryForList(sql);
                if(var12.size() > 0) {
                    return true;
                }
            }

            return false;
        } catch (Exception var11) {
            return false;
        }
    }

    public List<Map<String, Object>> executeSqlForColumns(String sql, String dbName, String databaseConfigId) throws Exception {
        String sql2 = " select * from  ( " + sql + " ) tab  limit 1 ";
        if(sql.indexOf("show") == 0 || sql.indexOf("SHOW") == 0) {
            sql2 = sql;
        }

        new DBUtil2(dbName, databaseConfigId);
        List list = DBUtil2.executeSqlForColumns(sql2);
        return list;
    }

    public boolean saveSearchHistory(String name, String sql, String dbName, String userId) {
        DBUtil db = new DBUtil();
        String insertSQL = "insert into  treesoft_searchHistory ( createdate, sqls, name, database,user_id) values (  datetime(\'now\') ,\'" + sql + "\',\'" + name + "\',\'" + dbName + "\',\'" + userId + "\')";

        try {
            db.do_update(insertSQL);
            return true;
        } catch (Exception var8) {
            System.out.println(var8.getMessage());
            return false;
        }
    }

    public boolean updateSearchHistory(String id, String name, String sql, String dbName) {
        DBUtil db = new DBUtil();
        String insertSQL = "update  treesoft_searchHistory set createdate= datetime(\'now\') , sqls=\'" + sql + "\', name = \'" + name + "\', database=\'" + dbName + "\' where id=\'" + id + "\' ";

        try {
            db.do_update(insertSQL);
            return true;
        } catch (Exception var8) {
            System.out.println(var8.getMessage());
            return false;
        }
    }

    public boolean deleteSearchHistory(String id) {
        DBUtil db = new DBUtil();
        String insertSQL = "delete  from  treesoft_searchHistory  where id=\'" + id + "\' ";

        try {
            db.do_update(insertSQL);
            return true;
        } catch (Exception var5) {
            System.out.println(var5.getMessage());
            return false;
        }
    }

    public List<Map<String, Object>> selectSearchHistory() {
        DBUtil db = new DBUtil();
        String sql = " select * from  treesoft_searchHistory ";
        List list = db.executeQuery(sql);
        return list;
    }

    public boolean configUpdate(Config config) throws Exception {
        DBUtil db = new DBUtil();
        String id = config.getId();
        String sql = "";
        String isdefault = config.getIsdefault();
        if(isdefault == null || isdefault.equals("")) {
            isdefault = "0";
        }

        if(!id.equals("")) {
            sql = " update treesoft_config  set databaseType=\'" + config.getDatabaseType() + "\' ," + "databaseName=\'" + config.getDatabaseName() + "\' ," + "userName=\'" + config.getUserName() + "\', " + "password=\'" + CryptoUtil.encode(config.getUserName() + "`" + config.getPassword()) + "\', " + "isdefault=\'" + isdefault + "\', " + "name =\'" + config.getName() + "\', " + "port=\'" + config.getPort() + "\', " + "ip=\'" + config.getIp() + "\', " + "url=\'" + config.getUrl() + "\'  where id=\'" + id + "\'";
        } else {
            sql = " insert into treesoft_config ( name,createDate, databaseType ,databaseName, userName ,password , port, ip , isdefault , url ) values ( \'" + config.getName() + "\',\'" + DateUtils.getDateTime() + "\',\'" + config.getDatabaseType() + "\',\'" + config.getDatabaseName() + "\',\'" + config.getUserName() + "\',\'" + CryptoUtil.encode(config.getUserName() + "`" + config.getPassword()) + "\',\'" + config.getPort() + "\',\'" + config.getIp() + "\',\'" + isdefault + "\',\'" + config.getUrl() + "\' ) ";
        }

        boolean bl = db.do_update(sql);
        String sql3 = " select id, name, databaseType ,databaseName, port, ip  from  treesoft_config order by isdefault desc ";
        List list3 = db.executeQuery(sql3);
        String ids = "";

        Map updateSQL;
        for(Iterator var11 = list3.iterator(); var11.hasNext(); ids = ids + updateSQL.get("id") + ",") {
            updateSQL = (Map)var11.next();
        }

        ids = ids.substring(0, ids.length() - 1);
        String updateSQL1 = " update treesoft_users set datascope =\'" + ids + "\'  where username in (\'admin\',\'treesoft\')";
        db.do_update2(updateSQL1);
        return bl;
    }

    public List<Map<String, Object>> selectUserByName(String userName) {
        DBUtil db = new DBUtil();
        String sql = " select * from  treesoft_users where username=\'" + userName + "\' ";
        List list = db.executeQuery(sql);
        return list;
    }

    public boolean updateUserPass(String userId, String newPass) throws Exception {
        DBUtil db = new DBUtil();
        String sql = " update treesoft_users  set password=\'" + newPass + "\'  where id=\'" + userId + "\'";
        boolean bl = db.do_update(sql);
        return bl;
    }

    public int executeSqlNotRes(String sql, String dbName, String databaseConfigId) throws Exception {
        new HashMap();
        new DBUtil2(dbName, databaseConfigId);
        int i = DBUtil2.setupdateData(sql);
        return i;
    }

    public int deleteRows(String databaseName, String tableName, String primary_key, String[] ids, String databaseConfigId) throws Exception {
        new DBUtil2(databaseName, databaseConfigId);
        int y = 0;

        for(int i = 0; i < ids.length; ++i) {
            String sql = " delete from  " + databaseName + "." + tableName + " where " + primary_key + " =\'" + ids[i] + "\'";
            y += DBUtil2.setupdateData(sql);
        }

        return y;
    }

    public int deleteRowsNew(String databaseName, String tableName, String primary_key, List<String> condition, String databaseConfigId) throws Exception {
        new DBUtil2(databaseName, databaseConfigId);
        int y = 0;

        for(int i = 0; i < condition.size(); ++i) {
            String whereStr = (String)condition.get(i);
            String sql = " delete from  " + databaseName + "." + tableName + " where  1=1 " + whereStr;
            System.out.println("删除数据行= " + DateUtils.getDateTime());
            System.out.println("SQL = " + sql);
            y += DBUtil2.setupdateData(sql);
        }

        return y;
    }

    public int saveRows(Map<String, Object> map, String databaseName, String tableName, String databaseConfigId) throws Exception {
        new DBUtil2(databaseName, databaseConfigId);
        String sql = " insert into " + databaseName + "." + tableName;
        boolean y = false;
        String colums = " ";
        String values = " ";
        String columnType = "";
        Iterator var12 = map.entrySet().iterator();

        while(true) {
            while(var12.hasNext()) {
                Entry entry = (Entry)var12.next();
                colums = colums + (String)entry.getKey() + ",";
                columnType = this.selectOneColumnType(databaseName, tableName, (String)entry.getKey(), databaseConfigId);
                String str = "" + entry.getValue();
                if(str.equals("")) {
                    values = values + " null ,";
                } else if(columnType.indexOf("integer") < 0 && columnType.indexOf("bit") < 0 && columnType.indexOf("int") < 0 && columnType.indexOf("float") < 0) {
                    values = values + "\'" + entry.getValue() + "\',";
                } else {
                    values = values + entry.getValue() + ",";
                }
            }

            colums = colums.substring(0, colums.length() - 1);
            values = values.substring(0, values.length() - 1);
            sql = sql + " (" + colums + ") values (" + values + ")";
            int y1 = DBUtil2.setupdateData(sql);
            return y1;
        }
    }

    public List<Map<String, Object>> getOneRowById(String databaseName, String tableName, String id, String idValues, String databaseConfigId) {
        new DBUtil2(databaseName, databaseConfigId);
        String sql2 = " select * from   " + databaseName + "." + tableName + " where " + id + "=\'" + idValues + "\' ";
        List list = DBUtil2.queryForListWithType(sql2);
        return list;
    }

    public int updateRows(Map<String, Object> map, String databaseName, String tableName, String id, String idValues, String databaseConfigId) throws Exception {
        if(id != null && !"".equals(id)) {
            if(idValues != null && !"".equals(idValues)) {
                new DBUtil2(databaseName, databaseConfigId);
                String sql = " update  " + databaseName + "." + tableName;
                boolean y = false;
                String ss = " set  ";
                Iterator var12 = map.entrySet().iterator();

                while(var12.hasNext()) {
                    Entry d = (Entry)var12.next();
                    String str = "" + d.getValue();
                    if(str.equals("")) {
                        ss = ss + (String)d.getKey() + "= null ,";
                    } else if(d.getValue() instanceof String) {
                        ss = ss + (String)d.getKey() + "= \'" + d.getValue() + "\',";
                    } else {
                        ss = ss + (String)d.getKey() + "= " + d.getValue() + ",";
                    }
                }

                ss = ss.substring(0, ss.length() - 1);
                sql = sql + ss + " where " + id + "=\'" + idValues + "\'";
                new Date();
                this.logger.debug(DateUtils.getDateTime() + ",SQL= " + sql);
                int y1 = DBUtil2.setupdateData(sql);
                return y1;
            } else {
                throw new Exception("数据不完整,保存失败!");
            }
        } else {
            throw new Exception("数据不完整,保存失败!");
        }
    }

    public String getViewSql(String databaseName, String tableName, String databaseConfigId) throws Exception {
        String sql = " select  view_definition  from  information_schema.VIEWS  where  table_name=\'" + tableName + "\' and table_schema=\'" + databaseName + "\'  ";
        String str = "";
        DBUtil2 db = new DBUtil2(databaseName, databaseConfigId);
        List list = db.queryForList(sql);
        if(list.size() == 1) {
            Map map = (Map)list.get(0);
            str = (String)map.get("view_definition");
        }

        return str;
    }

    public String getProcSqlForSQL(String databaseName, String proc_name, String databaseConfigId) {
        String sql = " show create procedure  " + proc_name;
        String str = "";
        DBUtil2 db = new DBUtil2(databaseName, databaseConfigId);

        try {
            List e = db.queryForList(sql);
            if(e.size() == 1) {
                Map map = (Map)e.get(0);
                str = (String)map.get("Create Procedure");
            }
        } catch (Exception var9) {
            System.out.println(var9.getMessage());
            str = "";
        }

        return str;
    }

    public List<Map<String, Object>> getTableColumns2(String databaseName, String tableName, String databaseConfigId) throws Exception {
        String sql = "select * from  `" + databaseName + "`.`" + tableName + "` limit 1";
        new DBUtil2(databaseName, databaseConfigId);
        List list = DBUtil2.queryForColumnOnly(sql);
        return list;
    }

    public String getPrimaryKeys(String databaseName, String tableName, String databaseConfigId) {
        DBUtil2 db = new DBUtil2(databaseName, databaseConfigId);
        return db.getPrimaryKeys(databaseName, tableName);
    }

    public List<Map<String, Object>> getPrimaryKeyss(String databaseName, String tableName, String databaseConfigId) throws Exception {
        String sql = " select   column_name  from information_schema.columns where   table_name=\'" + tableName + "\' and table_schema=\'" + databaseName + "\' and column_key=\'PRI\' ";
        DBUtil2 db = new DBUtil2(databaseName, databaseConfigId);
        List list = db.queryForList(sql);
        return list;
    }

    public boolean testConn(String databaseType, String databaseName, String ip, String port, String user, String pass) {
        return DBUtil2.testConnection(databaseType, databaseName, ip, port, user, pass);
    }

    public List<Map<String, Object>> selectSqlStudy() {
        DBUtil db = new DBUtil();
        String sql = " select id, title, content, pid,icon  from  treesoft_study   ";
        List list = db.executeQuery(sql);
        return list;
    }

    public int saveDesginColumn(Map<String, String> map, String databaseName, String tableName, String databaseConfigId) throws Exception {
        new DBUtil2(databaseName, databaseConfigId);
        String sql = " alter table " + databaseName + "." + tableName + " add column ";
        sql = sql + (String)map.get("COLUMN_NAME") + "  ";
        sql = sql + (String)map.get("DATA_TYPE");
        if(map.get("CHARACTER_MAXIMUM_LENGTH") != null && !((String)map.get("CHARACTER_MAXIMUM_LENGTH")).equals("")) {
            sql = sql + " (" + (String)map.get("CHARACTER_MAXIMUM_LENGTH") + ") ";
        }

        if(map.get("COLUMN_COMMENT") != null && !((String)map.get("COLUMN_COMMENT")).equals("")) {
            sql = sql + " comment \'" + (String)map.get("COLUMN_COMMENT") + "\'";
        }

        boolean y = false;
        int y1 = DBUtil2.setupdateData(sql);
        return y1;
    }

    public int deleteTableColumn(String databaseName, String tableName, String[] ids, String databaseConfigId) throws Exception {
        new DBUtil2(databaseName, databaseConfigId);
        int y = 0;

        for(int i = 0; i < ids.length; ++i) {
            String sql = " alter table   " + databaseName + "." + tableName + " drop column  " + ids[i];
            y += DBUtil2.setupdateData(sql);
        }

        return y;
    }

    public int updateTableColumn(Map<String, Object> map, String databaseName, String tableName, String columnName, String idValues, String databaseConfigId) throws Exception {
        if(columnName != null && !"".equals(columnName)) {
            if(idValues != null && !"".equals(idValues)) {
                new DBUtil2(databaseName, databaseConfigId);
                String old_field_name = (String)map.get("TREESOFTPRIMARYKEY");
                String column_name = (String)map.get("COLUMN_NAME");
                String data_type = (String)map.get("DATA_TYPE");
                String character_maximum_length = "" + map.get("CHARACTER_MAXIMUM_LENGTH");
                String column_comment = (String)map.get("COLUMN_COMMENT");
                String sql2;
                int y;
                if(!old_field_name.endsWith(column_name)) {
                    sql2 = " alter table  " + databaseName + "." + tableName + " change ";
                    sql2 = sql2 + old_field_name + " " + column_name + " " + data_type;
                    if(character_maximum_length != null && !character_maximum_length.equals("")) {
                        sql2 = sql2 + " (" + character_maximum_length + ")";
                    }

                    y = DBUtil2.setupdateData(sql2);
                }

                sql2 = " alter table  " + databaseName + "." + tableName + " modify column " + column_name + " " + data_type;
                if(character_maximum_length != null && !character_maximum_length.equals("")) {
                    sql2 = sql2 + " (" + character_maximum_length + ")";
                }

                if(column_comment != null && !column_comment.equals("")) {
                    sql2 = sql2 + " comment \'" + column_comment + "\'";
                }

                y = DBUtil2.setupdateData(sql2);
                return y;
            } else {
                throw new Exception("数据不完整,保存失败!");
            }
        } else {
            throw new Exception("数据不完整,保存失败!");
        }
    }

    public int dropPrimaryKey(String databaseName, String tableName, String databaseConfigId) throws Exception {
        new DBUtil2(databaseName, databaseConfigId);
        String sql4 = " alter table  " + databaseName + "." + tableName + " drop primary key ";
        DBUtil2.setupdateData(sql4);
        return 0;
    }

    public int savePrimaryKey2(String databaseName, String tableName, String primaryKeys, String databaseConfigId) throws Exception {
        String sql4 = "";
        if(primaryKeys != null && !primaryKeys.equals("")) {
            new DBUtil2(databaseName, databaseConfigId);
            sql4 = " alter table  " + databaseName + "." + tableName + " add primary key (" + primaryKeys + ")";
            DBUtil2.setupdateData(sql4);
        }

        return 0;
    }

    public int savePrimaryKey(String databaseName, String tableName, String column_name, String isSetting, String databaseConfigId) throws Exception {
        String sql4 = "";
        if(column_name != null && !column_name.equals("")) {
            new DBUtil2(databaseName, databaseConfigId);
            List list2 = this.selectTablePrimaryKey(databaseName, tableName, databaseConfigId);
            if(isSetting.equals("true")) {
                list2.add(column_name);
            } else {
                list2.remove(column_name);
            }

            String tem = list2.toString();
            String primaryKey = tem.substring(1, tem.length() - 1);
            if(primaryKey.equals("")) {
                sql4 = " alter table  " + databaseName + "." + tableName + " drop primary key ";
            } else if(list2.size() == 1 && isSetting.equals("true")) {
                sql4 = " alter table  " + databaseName + "." + tableName + " add primary key (" + primaryKey + ")";
            } else {
                sql4 = " alter table  " + databaseName + "." + tableName + " drop primary key, add primary key (" + primaryKey + ")";
            }

            DBUtil2.setupdateData(sql4);
        }

        return 0;
    }

    public List<String> selectTablePrimaryKey(String databaseName, String tableName, String databaseConfigId) throws Exception {
        String sql = " select column_name   from information_schema.columns where   table_name=\'" + tableName + "\' and table_schema=\'" + databaseName + "\'  and column_key=\'PRI\' ";
        DBUtil2 db = new DBUtil2(databaseName, databaseConfigId);
        List list = db.queryForList(sql);
        ArrayList list2 = new ArrayList();
        Iterator var9 = list.iterator();

        while(var9.hasNext()) {
            Map map = (Map)var9.next();
            list2.add((String)map.get("column_name"));
        }

        return list2;
    }

    public String selectOneColumnType(String databaseName, String tableName, String column_name, String databaseConfigId) throws Exception {
        String sql = " select   column_type  from information_schema.columns where   table_name=\'" + tableName + "\' and table_schema=\'" + databaseName + "\' and column_name=\'" + column_name + "\'";
        DBUtil2 db = new DBUtil2(databaseName, databaseConfigId);
        List list = db.queryForList(sql);
        return (String)((Map)list.get(0)).get("column_type");
    }

    public int updateTableNullAble(String databaseName, String tableName, String column_name, String is_nullable, String databaseConfigId) throws Exception {
        String sql4 = "";
        if(column_name != null && !column_name.equals("")) {
            new DBUtil2(databaseName, databaseConfigId);
            String column_type = this.selectOneColumnType(databaseName, tableName, column_name, databaseConfigId);
            if(is_nullable.equals("true")) {
                sql4 = " alter table  " + databaseName + "." + tableName + " modify column " + column_name + " " + column_type + "  null ";
            } else {
                sql4 = " alter table  " + databaseName + "." + tableName + " modify column " + column_name + " " + column_type + " not null ";
            }

            DBUtil2.setupdateData(sql4);
        }

        return 0;
    }

    public int upDownColumn(String databaseName, String tableName, String column_name, String column_name2, String databaseConfigId) throws Exception {
        String sql4 = "";
        if(column_name != null && !column_name.equals("")) {
            new DBUtil2(databaseName, databaseConfigId);
            String column_type = this.selectOneColumnType(databaseName, tableName, column_name, databaseConfigId);
            if(column_name2 != null && !column_name2.equals("")) {
                sql4 = " alter table  " + databaseName + "." + tableName + " modify column " + column_name + " " + column_type + " after " + column_name2;
            } else {
                sql4 = " alter table  " + databaseName + "." + tableName + " modify column " + column_name + " " + column_type + " first ";
            }

            DBUtil2.setupdateData(sql4);
        }

        return 0;
    }

    public List<Map<String, Object>> getAllDataBaseForOracle(String databaseConfigId) throws Exception {
        ArrayList list = new ArrayList();
        Map map0 = this.getConfig(databaseConfigId);
        String databaseName = (String)map0.get("databaseName");
        HashMap map = new HashMap();
        map.put("SCHEMA_NAME", databaseName);
        list.add(map);
        return list;
    }

    public List<Map<String, Object>> getAllTablesForOracle(String dbName, String databaseConfigId) throws Exception {
        DBUtil2 db = new DBUtil2(dbName, databaseConfigId);
        String sql = " select TABLE_NAME  from  user_tables  ";
        List list = db.queryForList(sql);
        return list;
    }

    public List<Map<String, Object>> getTableColumns3ForOracle(String dbName, String tableName, String databaseConfigId) throws Exception {
        String sql = "select t1.column_name as TREESOFTPRIMARYKEY, t1.COLUMN_NAME,  nvl2( t1.CHAR_COL_DECL_LENGTH,  t1.DATA_TYPE||\'(\' ||CHAR_COL_DECL_LENGTH||\')\',t1.DATA_TYPE ) as COLUMN_TYPE ,t1.data_type,   t1.data_length as CHARACTER_MAXIMUM_LENGTH ,CASE t1.nullable when \'Y\' then \'YES\' END as IS_NULLABLE  ,  nvl2(t3.column_name ,\'PRI\' ,\'\')  as COLUMN_KEY,  t2.comments as COLUMN_COMMENT  from user_tab_columns  t1   left join user_col_comments t2  on  t1.table_name = t2.table_name and t1.COLUMN_NAME = t2.COLUMN_NAME   left join   (select a.table_name, a.column_name   from user_cons_columns a, user_constraints b    where a.constraint_name = b.constraint_name    and b.constraint_type = \'P\' ) t3    on t1.TABLE_NAME = t3.table_name  and t1.COLUMN_NAME = t3.COLUMN_NAME    where   t1.table_name= \'" + tableName + "\'  ";
        DBUtil2 db = new DBUtil2(dbName, databaseConfigId);
        List list = db.queryForList(sql);
        return list;
    }

    public List<Map<String, Object>> getAllViewsForOracle(String dbName, String databaseConfigId) throws Exception {
        String sql = " select view_name as TABLE_NAME from  user_views  ";
        DBUtil2 db = new DBUtil2(dbName, databaseConfigId);
        List list = db.queryForList(sql);
        return list;
    }

    public String getViewSqlForOracle(String databaseName, String tableName, String databaseConfigId) throws Exception {
        String sql = " select TEXT from all_views where view_name = \'" + tableName + "\'";
        String str = "";
        DBUtil2 db = new DBUtil2(databaseName, databaseConfigId);
        List list = db.queryForList(sql);
        if(list.size() == 1) {
            Map map = (Map)list.get(0);
            str = "create view " + tableName + " as " + (String)map.get("TEXT") + ";";
        }

        return str;
    }

    public List<Map<String, Object>> getAllFuntionForOracle(String dbName, String databaseConfigId) throws Exception {
        String sql = " select object_name as ROUTINE_NAME from  user_procedures ";
        DBUtil2 db = new DBUtil2(dbName, databaseConfigId);
        List list = db.queryForList(sql);
        return list;
    }

    public Page<Map<String, Object>> getDataForOracle(Page<Map<String, Object>> page, String tableName, String dbName, String databaseConfigId) throws Exception {
        int pageNo = page.getPageNo();
        int pageSize = page.getPageSize();
        int limitFrom = (pageNo - 1) * pageSize;
        String orderBy = page.getOrderBy();
        String order = page.getOrder();
        DBUtil2 db = new DBUtil2(dbName, databaseConfigId);
        List list3 = this.getPrimaryKeyssForOracle(dbName, tableName, databaseConfigId);
        String tem = "";

        Map primaryKey;
        for(Iterator sql = list3.iterator(); sql.hasNext(); tem = tem + primaryKey.get("COLUMN_NAME") + ",") {
            primaryKey = (Map)sql.next();
        }

        String primaryKey1 = "";
        if(!tem.equals("")) {
            primaryKey1 = tem.substring(0, tem.length() - 1);
        }

        String sql1 = "select * from  " + tableName;
        String sql2 = "";
        if(orderBy != null && !orderBy.equals("")) {
            sql2 = "select * from (select rownum rn, t1.* from " + tableName + " t1) where rn between " + limitFrom + " and  " + (limitFrom + pageSize) + " order by " + orderBy + " " + order;
        } else {
            sql2 = "select * from (select rownum rn, t1.* from " + tableName + " t1) where rn between " + limitFrom + " and  " + (limitFrom + pageSize);
        }

        List list = db.queryForList(sql2);
        int rowCount = DBUtil2.executeQueryForCountForOracle(sql1);
        List columns = this.getTableColumnsForOracle(dbName, tableName, databaseConfigId);
        ArrayList tempList = new ArrayList();
        HashMap map1 = new HashMap();
        map1.put("field", "treeSoftPrimaryKey");
        map1.put("checkbox", Boolean.valueOf(true));
        tempList.add(map1);

        HashMap map2;
        for(Iterator jsonfromList = columns.iterator(); jsonfromList.hasNext(); tempList.add(map2)) {
            Map mapper = (Map)jsonfromList.next();
            map2 = new HashMap();
            map2.put("field", mapper.get("column_name"));
            map2.put("title", mapper.get("column_name"));
            map2.put("sortable", Boolean.valueOf(true));
            if(!mapper.get("data_type").equals("DATETIME") && !mapper.get("data_type").equals("DATE") && !mapper.get("data_type").equals("TIMESTAMP")) {
                if(!mapper.get("data_type").equals("INT") && !mapper.get("data_type").equals("SMALLINT") && !mapper.get("data_type").equals("TINYINT")) {
                    if(mapper.get("data_type").equals("DOUBLE")) {
                        map2.put("editor", "numberbox");
                    } else {
                        map2.put("editor", "text");
                    }
                } else {
                    map2.put("editor", "numberbox");
                }
            } else {
                map2.put("editor", "datetimebox");
            }
        }

        ObjectMapper mapper1 = new ObjectMapper();
        String jsonfromList1 = "[" + mapper1.writeValueAsString(tempList) + "]";
        page.setTotalCount((long)rowCount);
        page.setResult(list);
        page.setColumns(jsonfromList1);
        page.setPrimaryKey(primaryKey1);
        return page;
    }

    public List<Map<String, Object>> getPrimaryKeyssForOracle(String databaseName, String tableName, String databaseConfigId) throws Exception {
        String sql = "  select  COLUMN_NAME   from   user_cons_columns  where   constraint_name= (select  constraint_name  from user_constraints  where table_name = \'" + tableName + "\' and constraint_type =\'P\') ";
        DBUtil2 db = new DBUtil2(databaseName, databaseConfigId);
        List list = db.queryForList(sql);
        return list;
    }

    public List<Map<String, Object>> getTableColumnsForOracle(String dbName, String tableName, String databaseConfigId) throws Exception {
        String sql = "select  * from   " + tableName + " where rownum =1 ";
        new DBUtil2(dbName, databaseConfigId);
        List list = DBUtil2.queryForColumnOnly(sql);
        return list;
    }

    public Page<Map<String, Object>> executeSqlHaveResForOracle(Page<Map<String, Object>> page, String sql, String dbName, String databaseConfigId) throws Exception {
        int pageNo = page.getPageNo();
        int pageSize = page.getPageSize();
        int limitFrom = (pageNo - 1) * pageSize;
        String sql2 = "SELECT * FROM (SELECT A.*, ROWNUM RN  FROM (  " + sql + " ) A ) WHERE RN BETWEEN " + limitFrom + " AND " + (limitFrom + pageSize);
        DBUtil2 db = new DBUtil2(dbName, databaseConfigId);
        List list = db.queryForList(sql2);
        int rowCount = DBUtil2.executeQueryForCountForOracle(sql);
        List columns = this.executeSqlForColumnsForOracle(sql, dbName, databaseConfigId);
        ArrayList tempList = new ArrayList();
        Iterator jsonfromList = columns.iterator();

        while(jsonfromList.hasNext()) {
            Map mapper = (Map)jsonfromList.next();
            HashMap map2 = new HashMap();
            map2.put("field", mapper.get("column_name"));
            map2.put("title", mapper.get("column_name"));
            map2.put("sortable", Boolean.valueOf(true));
            tempList.add(map2);
        }

        ObjectMapper mapper1 = new ObjectMapper();
        String jsonfromList1 = "[" + mapper1.writeValueAsString(tempList) + "]";
        page.setTotalCount((long)rowCount);
        page.setResult(list);
        page.setColumns(jsonfromList1);
        return page;
    }

    public List<Map<String, Object>> executeSqlForColumnsForOracle(String sql, String dbName, String databaseConfigId) throws Exception {
        String sql2 = " select * from (" + sql + ") where  rownum = 1 ";
        new DBUtil2(dbName, databaseConfigId);
        List list = DBUtil2.executeSqlForColumns(sql2);
        return list;
    }

    public int updateTableNullAbleForOracle(String databaseName, String tableName, String column_name, String is_nullable, String databaseConfigId) throws Exception {
        String sql4 = "";
        if(column_name != null && !column_name.equals("")) {
            new DBUtil2(databaseName, databaseConfigId);
            if(is_nullable.equals("true")) {
                sql4 = " alter table  " + tableName + " modify   " + column_name + "  null ";
            } else {
                sql4 = " alter table  " + tableName + " modify   " + column_name + "  not null ";
            }

            DBUtil2.setupdateData(sql4);
        }

        return 0;
    }

    public int savePrimaryKeyForOracle(String databaseName, String tableName, String column_name, String isSetting, String databaseConfigId) throws Exception {
        String sql4 = "";
        if(column_name != null && !column_name.equals("")) {
            new DBUtil2(databaseName, databaseConfigId);
            List list2 = this.selectTablePrimaryKeyForOracle(databaseName, tableName, databaseConfigId);
            ArrayList list3 = new ArrayList();
            Iterator primaryKey = list2.iterator();

            while(primaryKey.hasNext()) {
                Map tem = (Map)primaryKey.next();
                list3.add((String)tem.get("COLUMN_NAME"));
            }

            if(isSetting.equals("true")) {
                list3.add(column_name);
            } else {
                list3.remove(column_name);
            }

            String tem1 = list3.toString();
            String primaryKey1 = tem1.substring(1, tem1.length() - 1);
            if(list2.size() > 0) {
                String temp = (String)((Map)list2.get(0)).get("CONSTRAINT_NAME");
                sql4 = " alter table   " + tableName + " drop constraint  " + temp;
                DBUtil2.setupdateData(sql4);
            }

            if(!primaryKey1.equals("")) {
                sql4 = " alter table " + tableName + " add   primary key (" + primaryKey1 + ") ";
                DBUtil2.setupdateData(sql4);
            }
        }

        return 0;
    }

    public List<Map<String, Object>> selectTablePrimaryKeyForOracle(String databaseName, String tableName, String databaseConfigId) throws Exception {
        String sql = " select a.CONSTRAINT_NAME,  a.COLUMN_NAME  from user_cons_columns a, user_constraints b  where a.constraint_name = b.constraint_name   and b.constraint_type = \'P\'  and a.table_name = \'" + tableName + "\' ";
        DBUtil2 db = new DBUtil2(databaseName, databaseConfigId);
        List list = db.queryForList(sql);
        new ArrayList();
        return list;
    }

    public int saveDesginColumnForOracle(Map<String, String> map, String databaseName, String tableName, String databaseConfigId) throws Exception {
        new DBUtil2(databaseName, databaseConfigId);
        String sql = " alter table " + tableName + " add  ";
        sql = sql + (String)map.get("COLUMN_NAME") + "  ";
        sql = sql + (String)map.get("DATA_TYPE");
        if(map.get("CHARACTER_MAXIMUM_LENGTH") != null && !((String)map.get("CHARACTER_MAXIMUM_LENGTH")).equals("")) {
            sql = sql + " (" + (String)map.get("CHARACTER_MAXIMUM_LENGTH") + ") ";
        }

        if(map.get("COLUMN_COMMENT") != null && !((String)map.get("COLUMN_COMMENT")).equals("")) {
            sql = sql + " comment \'" + (String)map.get("COLUMN_COMMENT") + "\'";
        }

        boolean y = false;
        int y1 = DBUtil2.setupdateData(sql);
        return y1;
    }

    public int updateTableColumnForOracle(Map<String, Object> map, String databaseName, String tableName, String columnName, String idValues, String databaseConfigId) throws Exception {
        if(columnName != null && !"".equals(columnName)) {
            if(idValues != null && !"".equals(idValues)) {
                new DBUtil2(databaseName, databaseConfigId);
                String old_field_name = (String)map.get("TREESOFTPRIMARYKEY");
                String column_name = (String)map.get("COLUMN_NAME");
                String data_type = (String)map.get("DATA_TYPE");
                String character_maximum_length = "" + map.get("CHARACTER_MAXIMUM_LENGTH");
                String column_comment = (String)map.get("COLUMN_COMMENT");
                String sql2;
                int y;
                if(!old_field_name.endsWith(column_name)) {
                    sql2 = " ALTER TABLE " + tableName + " RENAME COLUMN " + old_field_name + " to  " + column_name;
                    y = DBUtil2.setupdateData(sql2);
                }

                sql2 = " alter table  " + tableName + " modify  " + column_name + " " + data_type;
                if(character_maximum_length != null && !character_maximum_length.equals("")) {
                    sql2 = sql2 + " (" + character_maximum_length + ")";
                }

                y = DBUtil2.setupdateData(sql2);
                if(column_comment != null && !column_comment.equals("")) {
                    String sql4 = "  comment on column " + tableName + "." + column_name + " is \'" + column_comment + "\' ";
                    DBUtil2.setupdateData(sql4);
                }

                return y;
            } else {
                throw new Exception("数据不完整,保存失败!");
            }
        } else {
            throw new Exception("数据不完整,保存失败!");
        }
    }

    public int deleteTableColumnForOracle(String databaseName, String tableName, String[] ids, String databaseConfigId) throws Exception {
        new DBUtil2(databaseName, databaseConfigId);
        int y = 0;

        for(int i = 0; i < ids.length; ++i) {
            String sql = " alter table   " + tableName + " drop (" + ids[i] + ")";
            y += DBUtil2.setupdateData(sql);
        }

        return y;
    }

    public int saveRowsForOracle(Map<String, String> map, String databaseName, String tableName, String databaseConfigId) throws Exception {
        new DBUtil2(databaseName, databaseConfigId);
        String sql = " insert into  " + tableName;
        boolean y = false;
        String colums = " ";
        String values = " ";
        String columnType = "";
        Iterator var12 = map.entrySet().iterator();

        while(var12.hasNext()) {
            Entry entry = (Entry)var12.next();
            if(!((String)entry.getKey()).equals("RN")) {
                colums = colums + (String)entry.getKey() + ",";
                columnType = this.selectColumnTypeForOracle(databaseName, tableName, (String)entry.getKey(), databaseConfigId);
                String str = (String)entry.getValue();
                if(str.equals("")) {
                    values = values + " null ,";
                } else if(columnType.equals("DATE")) {
                    values = values + " to_date(\'" + (String)entry.getValue() + "\' ,\'yyyy-mm-dd hh24:mi:ss\') ,";
                } else if(columnType.indexOf("TIMESTAMP") >= 0) {
                    values = values + " to_date(\'" + (String)entry.getValue() + "\' ,\'yyyy-mm-dd hh24:mi:ss\') ,";
                } else if(columnType.equals("NUMBER")) {
                    values = values + (String)entry.getValue() + ",";
                } else if(columnType.equals("INTEGER")) {
                    values = values + (String)entry.getValue() + ",";
                } else if(columnType.equals("FLOAT")) {
                    values = values + (String)entry.getValue() + ",";
                } else if(columnType.equals("BINARY_FLOAT")) {
                    values = values + (String)entry.getValue() + ",";
                } else if(columnType.equals("BINARY_DOUBLE")) {
                    values = values + (String)entry.getValue() + ",";
                } else {
                    values = values + "\'" + (String)entry.getValue() + "\',";
                }
            }
        }

        colums = colums.substring(0, colums.length() - 1);
        values = values.substring(0, values.length() - 1);
        sql = sql + " (" + colums + ") values (" + values + ")";
        System.out.println("sql=" + sql);
        int y1 = DBUtil2.setupdateData(sql);
        return y1;
    }

    public String selectColumnTypeForOracle(String databaseName, String tableName, String column, String databaseConfigId) throws Exception {
        String sql = " select DATA_TYPE from user_tab_columns where table_name =\'" + tableName + "\' AND COLUMN_NAME =\'" + column + "\' ";
        DBUtil2 db = new DBUtil2(databaseName, databaseConfigId);
        List list = db.queryForList(sql);
        return (String)((Map)list.get(0)).get("DATA_TYPE");
    }

    public int deleteRowsNewForOracle(String databaseName, String tableName, String primary_key, List<String> condition, String databaseConfigId) throws Exception {
        new DBUtil2(databaseName, databaseConfigId);
        int y = 0;

        for(int i = 0; i < condition.size(); ++i) {
            String whereStr = (String)condition.get(i);
            String sql = " delete from  " + tableName + " where  1=1 " + whereStr;
            new Date();
            this.logger.debug(DateUtils.getDateTime() + ",SQL= " + sql);
            System.out.println("删除数据行= " + DateUtils.getDateTime());
            System.out.println("SQL = " + sql);
            y += DBUtil2.setupdateData(sql);
        }

        return y;
    }

    public List<Map<String, Object>> getAllDataBaseForPostgreSQL(String databaseConfigId) throws Exception {
        Map map0 = this.getConfig(databaseConfigId);
        String databaseName = (String)map0.get("databaseName");
        DBUtil2 db = new DBUtil2(databaseName, databaseConfigId);
        String sql = " select  datname as  \"SCHEMA_NAME\"   from pg_database  where  datname not like \'template%\'  ";
        List list2 = db.queryForList(sql);
        return list2;
    }

    public List<Map<String, Object>> getAllTablesForPostgreSQL(String dbName, String databaseConfigId) throws Exception {
        DBUtil2 db = new DBUtil2(dbName, databaseConfigId);
        String sql = " select  tablename as \"TABLE_NAME\" from pg_tables  where schemaname=\'public\'  ";
        List list = db.queryForList(sql);
        return list;
    }

    public List<Map<String, Object>> getTableColumns3ForPostgreSQL(String dbName, String tableName, String databaseConfigId) throws Exception {
        String sql = "   select t1.column_name as \"TREESOFTPRIMARYKEY\", t1.COLUMN_NAME as \"COLUMN_NAME\", t1.DATA_TYPE   as \"COLUMN_TYPE\" , t1.DATA_TYPE as \"DATA_TYPE\" , character_maximum_length as \"CHARACTER_MAXIMUM_LENGTH\" ,   t1.IS_NULLABLE as \"IS_NULLABLE\" ,  \'\' as \"COLUMN_COMMENT\" , CASE  WHEN t2.COLUMN_NAME IS NULL THEN \'\'  ELSE \'PRI\'  END AS \"COLUMN_KEY\"   from information_schema.columns t1    left join information_schema.constraint_column_usage t2    on t1.table_name = t2.table_name  and t1.COLUMN_NAME = t2.COLUMN_NAME where  t1.table_name=\'" + tableName + "\'    ";
        DBUtil2 db = new DBUtil2(dbName, databaseConfigId);
        List list = db.queryForList(sql);
        return list;
    }

    public List<Map<String, Object>> getAllViewsForPostgreSQL(String dbName, String databaseConfigId) throws Exception {
        String sql = " select   viewname as \"TABLE_NAME\"  from pg_views  where schemaname=\'public\'  ";
        DBUtil2 db = new DBUtil2(dbName, databaseConfigId);
        List list = db.queryForList(sql);
        return list;
    }

    public List<Map<String, Object>> getAllFuntionForPostgreSQL(String dbName, String databaseConfigId) throws Exception {
        String sql = "  select prosrc as \"ROUTINE_NAME\" from pg_proc where 1=2  ";
        DBUtil2 db = new DBUtil2(dbName, databaseConfigId);
        List list = db.queryForList(sql);
        return list;
    }

    public Page<Map<String, Object>> getDataForPostgreSQL(Page<Map<String, Object>> page, String tableName, String dbName, String databaseConfigId) throws Exception {
        int pageNo = page.getPageNo();
        int pageSize = page.getPageSize();
        int limitFrom = (pageNo - 1) * pageSize;
        String orderBy = page.getOrderBy();
        String order = page.getOrder();
        DBUtil2 db = new DBUtil2(dbName, databaseConfigId);
        tableName = "\"" + tableName + "\"";
        List list3 = this.getPrimaryKeyssForPostgreSQL(dbName, tableName, databaseConfigId);
        String tem = "";

        Map primaryKey;
        for(Iterator sql = list3.iterator(); sql.hasNext(); tem = tem + primaryKey.get("COLUMN_NAME") + ",") {
            primaryKey = (Map)sql.next();
        }

        String primaryKey1 = "";
        if(!tem.equals("")) {
            primaryKey1 = tem.substring(0, tem.length() - 1);
        }

        String sql1 = "select * from  " + tableName;
        String sql2 = "";
        if(orderBy != null && !orderBy.equals("")) {
            sql2 = "select  *  from  " + tableName + " order by " + orderBy + " " + order + "  LIMIT " + pageSize + "  OFFSET " + limitFrom;
        } else {
            sql2 = "select  *  from  " + tableName + "  LIMIT " + pageSize + " OFFSET  " + limitFrom;
        }

        List list = db.queryForListForPostgreSQL(sql2);
        int rowCount = DBUtil2.executeQueryForCountForPostgreSQL(sql1);
        List columns = this.getTableColumnsForPostgreSQL(dbName, tableName, databaseConfigId);
        ArrayList tempList = new ArrayList();
        HashMap map1 = new HashMap();
        map1.put("field", "treeSoftPrimaryKey");
        map1.put("checkbox", Boolean.valueOf(true));
        tempList.add(map1);

        HashMap map2;
        for(Iterator jsonfromList = columns.iterator(); jsonfromList.hasNext(); tempList.add(map2)) {
            Map mapper = (Map)jsonfromList.next();
            map2 = new HashMap();
            map2.put("field", mapper.get("column_name"));
            map2.put("title", mapper.get("column_name"));
            map2.put("sortable", Boolean.valueOf(true));
            System.out.println(mapper.get("data_type"));
            if(!mapper.get("data_type").equals("DATETIME") && !mapper.get("data_type").equals("DATE") && !mapper.get("data_type").equals("date") && !mapper.get("data_type").equals("timestamp")) {
                if(!mapper.get("data_type").equals("integer") && !mapper.get("data_type").equals("float4") && !mapper.get("data_type").equals("numeric") && !mapper.get("data_type").equals("int4")) {
                    if(mapper.get("data_type").equals("DOUBLE")) {
                        map2.put("editor", "numberbox");
                    } else if(mapper.get("data_type").equals("bpchar") || mapper.get("data_type").equals("varchar")) {
                        map2.put("editor", "text");
                    }
                } else {
                    map2.put("editor", "numberbox");
                }
            } else {
                map2.put("editor", "datetimebox");
            }
        }

        ObjectMapper mapper1 = new ObjectMapper();
        String jsonfromList1 = "[" + mapper1.writeValueAsString(tempList) + "]";
        page.setTotalCount((long)rowCount);
        page.setResult(list);
        page.setColumns(jsonfromList1);
        page.setPrimaryKey(primaryKey1);
        return page;
    }

    public Page<Map<String, Object>> executeSqlHaveResForPostgreSQL(Page<Map<String, Object>> page, String sql, String dbName, String databaseConfigId) throws Exception {
        int pageNo = page.getPageNo();
        int pageSize = page.getPageSize();
        int limitFrom = (pageNo - 1) * pageSize;
        String sql2 = "select  *  from  (" + sql + ") t  LIMIT " + pageSize + " OFFSET  " + limitFrom;
        DBUtil2 db = new DBUtil2(dbName, databaseConfigId);
        List list = db.queryForListForPostgreSQL(sql2);
        int rowCount = DBUtil2.executeQueryForCountForPostgreSQL(sql);
        List columns = this.executeSqlForColumnsForPostgreSQL(sql, dbName, databaseConfigId);
        ArrayList tempList = new ArrayList();
        Iterator jsonfromList = columns.iterator();

        while(jsonfromList.hasNext()) {
            Map mapper = (Map)jsonfromList.next();
            HashMap map2 = new HashMap();
            map2.put("field", mapper.get("column_name"));
            map2.put("title", mapper.get("column_name"));
            map2.put("sortable", Boolean.valueOf(true));
            tempList.add(map2);
        }

        ObjectMapper mapper1 = new ObjectMapper();
        String jsonfromList1 = "[" + mapper1.writeValueAsString(tempList) + "]";
        page.setTotalCount((long)rowCount);
        page.setResult(list);
        page.setColumns(jsonfromList1);
        return page;
    }

    public int deleteRowsNewForPostgreSQL(String databaseName, String tableName, String primary_key, List<String> condition, String databaseConfigId) throws Exception {
        new DBUtil2(databaseName, databaseConfigId);
        int y = 0;

        for(int i = 0; i < condition.size(); ++i) {
            String whereStr = (String)condition.get(i);
            String sql = " delete from  " + tableName + " where  1=1 " + whereStr;
            System.out.println("删除数据行= " + DateUtils.getDateTime());
            System.out.println("SQL = " + sql);
            y += DBUtil2.setupdateData(sql);
        }

        return y;
    }

    public int saveRowsForPostgreSQL(Map<String, String> map, String databaseName, String tableName, String databaseConfigId) throws Exception {
        new DBUtil2(databaseName, databaseConfigId);
        String sql = " insert into  \"" + tableName + "\"";
        boolean y = false;
        String colums = " ";
        String values = " ";
        String columnType = "";
        Iterator var12 = map.entrySet().iterator();

        while(var12.hasNext()) {
            Entry entry = (Entry)var12.next();
            colums = colums + "\"" + (String)entry.getKey() + "\",";
            columnType = this.selectColumnTypeForPostgreSQL(databaseName, tableName, (String)entry.getKey(), databaseConfigId);
            String str = (String)entry.getValue();
            if(str.equals("")) {
                values = values + " null ,";
            } else if(columnType.equals("integer")) {
                values = values + (String)entry.getValue() + " ,";
            } else if(columnType.equals("numeric")) {
                values = values + (String)entry.getValue() + " ,";
            } else if(columnType.equals("real")) {
                values = values + (String)entry.getValue() + " ,";
            } else {
                values = values + "\'" + (String)entry.getValue() + "\',";
            }
        }

        colums = colums.substring(0, colums.length() - 1);
        values = values.substring(0, values.length() - 1);
        sql = sql + " (" + colums + ") values (" + values + ")";
        int y1 = DBUtil2.setupdateData(sql);
        return y1;
    }

    public int saveDesginColumnForPostgreSQL(Map<String, String> map, String databaseName, String tableName, String databaseConfigId) throws Exception {
        new DBUtil2(databaseName, databaseConfigId);
        tableName = "\"" + tableName + "\"";
        String sql = " alter table " + tableName + " add  ";
        sql = sql + "\"" + (String)map.get("COLUMN_NAME") + "\"  ";
        sql = sql + (String)map.get("DATA_TYPE");
        if(map.get("CHARACTER_MAXIMUM_LENGTH") != null && !((String)map.get("CHARACTER_MAXIMUM_LENGTH")).equals("")) {
            sql = sql + " (" + (String)map.get("CHARACTER_MAXIMUM_LENGTH") + ") ";
        }

        if(map.get("COLUMN_COMMENT") != null && !((String)map.get("COLUMN_COMMENT")).equals("")) {
            sql = sql + " comment \'" + (String)map.get("COLUMN_COMMENT") + "\'";
        }

        boolean y = false;
        int y1 = DBUtil2.setupdateData(sql);
        return y1;
    }

    public int updateTableColumnForPostgreSQL(Map<String, Object> map, String databaseName, String tableName, String columnName, String idValues, String databaseConfigId) throws Exception {
        if(columnName != null && !"".equals(columnName)) {
            if(idValues != null && !"".equals(idValues)) {
                new DBUtil2(databaseName, databaseConfigId);
                tableName = "\"" + tableName + "\"";
                String old_field_name = "\"" + map.get("TREESOFTPRIMARYKEY") + "\"";
                String column_name = "\"" + map.get("COLUMN_NAME") + "\"";
                String data_type = (String)map.get("DATA_TYPE");
                String character_maximum_length = "" + map.get("CHARACTER_MAXIMUM_LENGTH");
                String column_comment = (String)map.get("COLUMN_COMMENT");
                String sql2;
                int y;
                if(!old_field_name.endsWith(column_name)) {
                    sql2 = " ALTER TABLE " + tableName + " RENAME COLUMN " + old_field_name + " to  " + column_name;
                    y = DBUtil2.setupdateData(sql2);
                }

                sql2 = " alter table  " + tableName + " alter column  " + column_name + " type " + data_type;
                if(character_maximum_length != null && !character_maximum_length.equals("")) {
                    sql2 = sql2 + " (" + character_maximum_length + ")";
                }

                y = DBUtil2.setupdateData(sql2);
                if(column_comment != null && !column_comment.equals("")) {
                    String sql4 = "  comment on column " + tableName + "." + column_name + " is \'" + column_comment + "\' ";
                    DBUtil2.setupdateData(sql4);
                }

                return y;
            } else {
                throw new Exception("数据不完整,保存失败!");
            }
        } else {
            throw new Exception("数据不完整,保存失败!");
        }
    }

    public int deleteTableColumnForPostgreSQL(String databaseName, String tableName, String[] ids, String databaseConfigId) throws Exception {
        new DBUtil2(databaseName, databaseConfigId);
        int y = 0;

        for(int i = 0; i < ids.length; ++i) {
            String sql = " alter table   " + tableName + " drop (" + ids[i] + ")";
            y += DBUtil2.setupdateData(sql);
        }

        return y;
    }

    public int updateTableNullAbleForPostgreSQL(String databaseName, String tableName, String column_name, String is_nullable, String databaseConfigId) throws Exception {
        String sql4 = "";
        if(column_name != null && !column_name.equals("")) {
            new DBUtil2(databaseName, databaseConfigId);
            if(is_nullable.equals("true")) {
                sql4 = " alter table  " + tableName + " alter column   " + column_name + " drop not null ";
            } else {
                sql4 = " alter table  " + tableName + " alter column   " + column_name + " set not null ";
            }

            DBUtil2.setupdateData(sql4);
        }

        return 0;
    }

    public int savePrimaryKeyForPostgreSQL(String databaseName, String tableName, String column_name, String isSetting, String databaseConfigId) throws Exception {
        String sql4 = "";
        if(column_name != null && !column_name.equals("")) {
            new DBUtil2(databaseName, databaseConfigId);
            List list2 = this.selectTablePrimaryKeyForPostgreSQL(databaseName, tableName, databaseConfigId);
            ArrayList list3 = new ArrayList();
            Iterator primaryKey = list2.iterator();

            while(primaryKey.hasNext()) {
                Map tem = (Map)primaryKey.next();
                list3.add((String)tem.get("COLUMN_NAME"));
            }

            if(isSetting.equals("true")) {
                list3.add(column_name);
            } else {
                list3.remove(column_name);
            }

            String tem1 = list3.toString();
            String primaryKey1 = tem1.substring(1, tem1.length() - 1);
            if(list2.size() > 0) {
                String temp = (String)((Map)list2.get(0)).get("CONSTRAINT_NAME");
                sql4 = " alter table   " + tableName + " drop constraint  " + temp;
                DBUtil2.setupdateData(sql4);
            }

            if(!primaryKey1.equals("")) {
                sql4 = " alter table " + tableName + " add   primary key (" + primaryKey1 + ") ";
                DBUtil2.setupdateData(sql4);
            }
        }

        return 0;
    }

    public List<Map<String, Object>> getPrimaryKeyssForPostgreSQL(String databaseName, String tableName, String databaseConfigId) throws Exception {
        String sql = " select  pg_attribute.attname as \"COLUMN_NAME\" from   pg_constraint  inner join pg_class    on pg_constraint.conrelid = pg_class.oid    inner join pg_attribute on pg_attribute.attrelid = pg_class.oid    and  pg_attribute.attnum = pg_constraint.conkey[1]     inner join pg_type on pg_type.oid = pg_attribute.atttypid  where pg_class.relname = \'" + tableName + "\'  " + " and pg_constraint.contype=\'p\' ";
        DBUtil2 db = new DBUtil2(databaseName, databaseConfigId);
        List list = db.queryForList(sql);
        return list;
    }

    public List<Map<String, Object>> getTableColumnsForPostgreSQL(String dbName, String tableName, String databaseConfigId) throws Exception {
        String sql = "select  * from   " + tableName + " limit 1 ";
        new DBUtil2(dbName, databaseConfigId);
        List list = DBUtil2.queryForColumnOnly(sql);
        return list;
    }

    public String selectColumnTypeForPostgreSQL(String databaseName, String tableName, String column, String databaseConfigId) throws Exception {
        String sql = " select data_type as \"DATA_TYPE\"  from  information_schema.columns  where    table_name =\'" + tableName + "\' AND COLUMN_NAME =\'" + column + "\' ";
        DBUtil2 db = new DBUtil2(databaseName, databaseConfigId);
        List list = db.queryForList(sql);
        return (String)((Map)list.get(0)).get("DATA_TYPE");
    }

    public List<Map<String, Object>> selectTablePrimaryKeyForPostgreSQL(String databaseName, String tableName, String databaseConfigId) throws Exception {
        String sql = " select pg_constraint.conname as \"CONSTRAINT_NAME\" ,pg_attribute.attname as \"COLUMN_NAME\" ,pg_type.typname as typename from   pg_constraint  inner join pg_class   on pg_constraint.conrelid = pg_class.oid    inner join pg_attribute on pg_attribute.attrelid = pg_class.oid    and  pg_attribute.attnum = pg_constraint.conkey[1]   inner join pg_type on pg_type.oid = pg_attribute.atttypid    where pg_class.relname = \'" + tableName + "\'  " + "  and pg_constraint.contype=\'p\' ";
        DBUtil2 db = new DBUtil2(databaseName, databaseConfigId);
        List list = db.queryForList(sql);
        new ArrayList();
        return list;
    }

    public List<Map<String, Object>> executeSqlForColumnsForPostgreSQL(String sql, String dbName, String databaseConfigId) throws Exception {
        String sql2 = " select * from (" + sql + ") t   limit 1; ";
        new DBUtil2(dbName, databaseConfigId);
        List list = DBUtil2.executeSqlForColumns(sql2);
        return list;
    }

    public String getViewSqlForPostgreSQL(String databaseName, String tableName, String databaseConfigId) throws Exception {
        String sql = " select  view_definition  from  information_schema.views  where  table_name=\'" + tableName + "\' and table_catalog=\'" + databaseName + "\'  ";
        String str = " ";
        DBUtil2 db = new DBUtil2(databaseName, databaseConfigId);
        List list = db.queryForList(sql);
        if(list.size() == 1) {
            Map map = (Map)list.get(0);
            str = (String)map.get("view_definition");
        }

        return str;
    }

    public List<Map<String, Object>> getAllDataBaseForMSSQL(String databaseConfigId) throws Exception {
        String sql = " SELECT name as SCHEMA_NAME FROM sys.databases where state=\'0\' ORDER BY name  ";
        Map map12 = this.getConfig(databaseConfigId);
        String databaseName = "" + map12.get("databaseName");
        Object list = new ArrayList();

        try {
            DBUtil2 list2 = new DBUtil2(databaseName, databaseConfigId);
            list = list2.queryForList(sql);
        } catch (Exception var11) {
            System.out.println(var11.getMessage());
            var11.printStackTrace();
        }

        ArrayList var12 = new ArrayList();
        HashMap map = new HashMap();
        map.put("SCHEMA_NAME", databaseName);
        var12.add(map);

        for(int i = 0; i < ((List)list).size(); ++i) {
            Map map2 = (Map)((List)list).get(i);
            String schema_name = (String)map2.get("SCHEMA_NAME");
            if(!schema_name.equals(databaseName)) {
                var12.add(map2);
            }
        }

        return var12;
    }

    public List<Map<String, Object>> getAllDataBaseForHive2(String databaseConfigId) throws Exception {
        String sql = " show databases ";
        Map map12 = this.getConfig(databaseConfigId);
        String databaseName = "" + map12.get("databaseName");
        Object list = new ArrayList();

        try {
            DBUtil2 list2 = new DBUtil2(databaseName, databaseConfigId);
            list = list2.queryForList(sql);
        } catch (Exception var11) {
            System.out.println(var11.getMessage());
            var11.printStackTrace();
        }

        ArrayList var12 = new ArrayList();
        HashMap map = new HashMap();
        map.put("SCHEMA_NAME", databaseName);
        var12.add(map);

        for(int i = 0; i < ((List)list).size(); ++i) {
            Map map2 = (Map)((List)list).get(i);
            String schema_name = (String)map2.get("database_name");
            if(!schema_name.equals(databaseName)) {
                map2.put("SCHEMA_NAME", schema_name);
                var12.add(map2);
            }
        }

        return var12;
    }

    public List<Map<String, Object>> getAllTablesForMSSQL(String dbName, String databaseConfigId) {
        ArrayList list = new ArrayList();

        try {
            DBUtil2 e = new DBUtil2(dbName, databaseConfigId);
            String sql = " SELECT Name as TABLE_NAME FROM " + dbName + "..SysObjects Where XType=\'U\' ORDER BY Name ";
            List list1 = e.queryForList(sql);
            return list1;
        } catch (Exception var6) {
            return list;
        }
    }

    public List<Map<String, Object>> getTableColumns3ForMSSQL(String dbName, String tableName, String databaseConfigId) throws Exception {
        String sql = " select b.name   TREESOFTPRIMARYKEY, b.name COLUMN_NAME, ISNULL( c.name +\'(\'+  cast(b.length as varchar(10)) +\')\' , c.name ) as  COLUMN_TYPE, c.name DATA_TYPE, b.length CHARACTER_MAXIMUM_LENGTH ,  case when b.isnullable=1  then \'YES\' else \'NO\' end as IS_NULLABLE , \'\' as COLUMN_COMMENT , (SELECT \'PRI\' FROM sysobjects where xtype=\'PK\' and  parent_obj=b.id and name in (    SELECT name  FROM sysindexes   WHERE indid in(    SELECT indid FROM sysindexkeys WHERE id = b.id AND colid=b.colid  ))) as COLUMN_KEY  from sysobjects a,syscolumns b,systypes c  where a.id=b.id  and a.name=\'" + tableName + "\' and a.xtype=\'U\'  and b.xtype=c.xtype and c.name<>\'sysname\' ";
        new DBUtil2(dbName, databaseConfigId);
        List list = DBUtil2.queryForList2(sql);
        ArrayList tempList = new ArrayList();

        Map mmap;
        for(Iterator var9 = list.iterator(); var9.hasNext(); tempList.add(mmap)) {
            mmap = (Map)var9.next();
            String data_type = (String)mmap.get("DATA_TYPE");
            if(data_type.equals("nvarchar")) {
                short leng = ((Short)mmap.get("CHARACTER_MAXIMUM_LENGTH")).shortValue();
                mmap.put("CHARACTER_MAXIMUM_LENGTH", Integer.valueOf(leng / 2));
            }
        }

        return tempList;
    }

    public List<Map<String, Object>> getAllViewsForMSSQL(String dbName, String databaseConfigId) throws Exception {
        Object list = new ArrayList();

        try {
            String sql = "  SELECT  NAME AS TABLE_NAME FROM  sysobjects where XTYPE =\'V\'  ";
            DBUtil2 db = new DBUtil2(dbName, databaseConfigId);
            list = db.queryForList(sql);
        } catch (Exception var6) {
            ;
        }

        return (List)list;
    }

    public List<Map<String, Object>> getAllViewsForHive2(String databaseName, String databaseConfigId) throws Exception {
        DBUtil2 db = new DBUtil2(databaseName, databaseConfigId);
        String sql = " use " + databaseName;
        DBUtil2.setupdateData(sql);
        String sql2 = "show views ";
        List list = db.queryForList(sql2);
        ArrayList list2 = new ArrayList();

        for(int i = 0; i < list.size(); ++i) {
            HashMap map = new HashMap();
            Map map2 = (Map)list.get(i);
            map.put("TABLE_NAME", map2.get("tab_name"));
            list2.add(map);
        }

        return list2;
    }

    public List<Map<String, Object>> getAllFuntionForMSSQL(String dbName, String databaseConfigId) throws Exception {
        Object list = new ArrayList();

        try {
            String sql = " SELECT  NAME AS ROUTINE_NAME FROM  sysobjects where XTYPE =\'FN\' ";
            DBUtil2 db = new DBUtil2(dbName, databaseConfigId);
            list = db.queryForList(sql);
        } catch (Exception var6) {
            ;
        }

        return (List)list;
    }

    public Page<Map<String, Object>> getDataForMSSQL(Page<Map<String, Object>> page, String tableName, String dbName, String databaseConfigId) throws Exception {
        int pageNo = page.getPageNo();
        int pageSize = page.getPageSize();
        int limitFrom = (pageNo - 1) * pageSize;
        if(limitFrom > 0) {
            --limitFrom;
        }

        String orderBy = page.getOrderBy();
        String order = page.getOrder();
        new DBUtil2(dbName, databaseConfigId);
        List list3 = this.getPrimaryKeyssForMSSQL(dbName, tableName, databaseConfigId);
        String tem = "";

        Map primaryKey;
        for(Iterator sql = list3.iterator(); sql.hasNext(); tem = tem + primaryKey.get("COLUMN_NAME") + ",") {
            primaryKey = (Map)sql.next();
        }

        String var27 = "";
        if(!tem.equals("")) {
            var27 = tem.substring(0, tem.length() - 1);
        }

        String var28 = "select * from  " + tableName;
        String sql2 = "";
        if(orderBy != null && !orderBy.equals("")) {
            sql2 = "select * from  " + tableName + " order by " + orderBy + " " + order;
        } else {
            sql2 = "select * from  " + tableName;
        }

        List list = DBUtil2.queryForListPageForMSSQL(sql2, pageNo * pageSize, (pageNo - 1) * pageSize);
        int rowCount = DBUtil2.executeQueryForCountForPostgreSQL(var28);
        List columns = this.getTableColumnsForMSSQL(dbName, tableName, databaseConfigId);
        ArrayList tempList = new ArrayList();
        HashMap map1 = new HashMap();
        map1.put("field", "treeSoftPrimaryKey");
        map1.put("checkbox", Boolean.valueOf(true));
        tempList.add(map1);
        String data_type = "";

        HashMap map2;
        for(Iterator jsonfromList = columns.iterator(); jsonfromList.hasNext(); tempList.add(map2)) {
            Map mapper = (Map)jsonfromList.next();
            map2 = new HashMap();
            map2.put("field", mapper.get("column_name"));
            map2.put("title", mapper.get("column_name"));
            map2.put("sortable", Boolean.valueOf(true));
            data_type = ("" + mapper.get("data_type")).toUpperCase();
            if(!data_type.equals("DATETIME") && !data_type.equals("DATE")) {
                if(!data_type.equals("INT") && !data_type.equals("SMALLINT") && !data_type.equals("TINYINT")) {
                    if(data_type.equals("DOUBLE")) {
                        map2.put("editor", "numberbox");
                    } else {
                        map2.put("editor", "text");
                    }
                } else {
                    map2.put("editor", "numberbox");
                }
            } else {
                map2.put("editor", "datetimebox");
            }
        }

        ObjectMapper var25 = new ObjectMapper();
        String var26 = "[" + var25.writeValueAsString(tempList) + "]";
        page.setTotalCount((long)rowCount);
        page.setResult(list);
        page.setColumns(var26);
        page.setPrimaryKey(var27);
        return page;
    }

    public Page<Map<String, Object>> getDataForHive2(Page<Map<String, Object>> page, String tableName, String dbName, String databaseConfigId) throws Exception {
        int pageNo = page.getPageNo();
        int pageSize = page.getPageSize();
        int limitFrom = (pageNo - 1) * pageSize;
        String orderBy = page.getOrderBy();
        String order = page.getOrder();
        DBUtil2 db = new DBUtil2(dbName, databaseConfigId);
        String sql_use = " use  " + dbName;
        DBUtil2.setupdateData(sql_use);
        String sql2 = "";
        if(orderBy != null && !orderBy.equals("")) {
            sql2 = "select * from   " + tableName + " limit 20 ";
        } else {
            sql2 = "select * from   " + tableName + " limit 20 ";
        }

        List list = db.queryForList(sql2);
        byte rowCount = 20;
        ArrayList tempList = new ArrayList();
        HashMap map1 = new HashMap();
        map1.put("field", "treeSoftPrimaryKey");
        map1.put("checkbox", Boolean.valueOf(true));
        tempList.add(map1);
        if(list.size() > 0) {
            Map mapper = (Map)list.get(0);
            Iterator var19 = mapper.entrySet().iterator();

            while(var19.hasNext()) {
                Entry jsonfromList = (Entry)var19.next();
                HashMap map3 = new HashMap();
                map3.put("field", jsonfromList.getKey());
                map3.put("title", jsonfromList.getKey());
                map3.put("sortable", Boolean.valueOf(true));
                tempList.add(map3);
            }
        }

        ObjectMapper mapper1 = new ObjectMapper();
        String jsonfromList1 = "[" + mapper1.writeValueAsString(tempList) + "]";
        page.setTotalCount((long)rowCount);
        page.setResult(list);
        page.setColumns(jsonfromList1);
        return page;
    }

    public Page<Map<String, Object>> executeSqlHaveResForMSSQL(Page<Map<String, Object>> page, String sql, String dbName, String databaseConfigId) throws Exception {
        int pageNo = page.getPageNo();
        int pageSize = page.getPageSize();
        int limitFrom = (pageNo - 1) * pageSize;
        if(limitFrom > 0) {
            --limitFrom;
        }

        String sql2 = " select  * from (" + sql + ")  t1  ";
        new DBUtil2(dbName, databaseConfigId);
        List list = DBUtil2.queryForListPageForMSSQL(sql2, pageNo * pageSize, (pageNo - 1) * pageSize);
        int rowCount = DBUtil2.executeQueryForCountForPostgreSQL(sql);
        List columns = this.executeSqlForColumnsForMSSQL(sql, dbName, databaseConfigId);
        ArrayList tempList = new ArrayList();
        Iterator jsonfromList = columns.iterator();

        while(jsonfromList.hasNext()) {
            Map mapper = (Map)jsonfromList.next();
            HashMap map2 = new HashMap();
            map2.put("field", mapper.get("column_name"));
            map2.put("title", mapper.get("column_name"));
            map2.put("sortable", Boolean.valueOf(true));
            tempList.add(map2);
        }

        ObjectMapper var17 = new ObjectMapper();
        String var18 = "[" + var17.writeValueAsString(tempList) + "]";
        page.setTotalCount((long)rowCount);
        page.setResult(list);
        page.setColumns(var18);
        return page;
    }

    public Page<Map<String, Object>> executeSqlHaveResForHive2(Page<Map<String, Object>> page, String sql, String dbName, String databaseConfigId) throws Exception {
        int pageNo = page.getPageNo();
        int pageSize = page.getPageSize();
        int limitFrom = (pageNo - 1) * pageSize;
        if(limitFrom > 0) {
            --limitFrom;
        }

        String sql2 = " select  * from (" + sql + ")  t1  ";
        new DBUtil2(dbName, databaseConfigId);
        List list = DBUtil2.queryForListPageForHive2(sql2, pageNo * pageSize, (pageNo - 1) * pageSize);
        byte rowCount = 20;
        ArrayList tempList = new ArrayList();
        if(list.size() > 0) {
            Map mapper = (Map)list.get(0);
            Iterator var15 = mapper.entrySet().iterator();

            while(var15.hasNext()) {
                Entry jsonfromList = (Entry)var15.next();
                HashMap map3 = new HashMap();
                map3.put("field", jsonfromList.getKey());
                map3.put("title", jsonfromList.getKey());
                map3.put("sortable", Boolean.valueOf(true));
                tempList.add(map3);
            }
        }

        ObjectMapper var17 = new ObjectMapper();
        String var18 = "[" + var17.writeValueAsString(tempList) + "]";
        page.setTotalCount((long)rowCount);
        page.setResult(list);
        page.setColumns(var18);
        return page;
    }

    public int deleteRowsNewForMSSQL(String databaseName, String tableName, String primary_key, List<String> condition, String databaseConfigId) throws Exception {
        new DBUtil2(databaseName, databaseConfigId);
        int y = 0;

        for(int i = 0; i < condition.size(); ++i) {
            String whereStr = (String)condition.get(i);
            String sql = " delete from  " + tableName + " where  1=1 " + whereStr;
            System.out.println("删除数据行= " + DateUtils.getDateTime());
            System.out.println("SQL = " + sql);
            y += DBUtil2.setupdateData(sql);
        }

        return y;
    }

    public String getViewSqlForMSSQL(String databaseName, String tableName, String databaseConfigId) throws Exception {
        String sql = " select  view_definition  from  information_schema.views  where  table_name=\'" + tableName + "\' and table_catalog=\'" + databaseName + "\'  ";
        String str = " ";
        DBUtil2 db = new DBUtil2(databaseName, databaseConfigId);
        List list = db.queryForList(sql);
        if(list.size() == 1) {
            Map map = (Map)list.get(0);
            str = (String)map.get("view_definition");
        }

        return str;
    }

    public int saveRowsForMSSQL(Map<String, String> map, String databaseName, String tableName, String databaseConfigId) throws Exception {
        new DBUtil2(databaseName, databaseConfigId);
        String sql = " insert into  " + tableName;
        boolean y = false;
        String colums = " ";
        String values = " ";
        String columnType = "";
        Iterator var12 = map.entrySet().iterator();

        while(var12.hasNext()) {
            Entry entry = (Entry)var12.next();
            colums = colums + (String)entry.getKey() + ",";
            columnType = this.selectOneColumnTypeForMSSQL(databaseName, tableName, (String)entry.getKey(), databaseConfigId);
            String str = (String)entry.getValue();
            if(str.equals("")) {
                values = values + " null ,";
            } else if(columnType.equals("DATE")) {
                values = values + " to_date(\'" + (String)entry.getValue() + "\' ,\'yyyy-mm-dd hh24:mi:ss\') ,";
            } else {
                values = values + "\'" + (String)entry.getValue() + "\',";
            }
        }

        colums = colums.substring(0, colums.length() - 1);
        values = values.substring(0, values.length() - 1);
        sql = sql + " (" + colums + ") values (" + values + ")";
        int y1 = DBUtil2.setupdateData(sql);
        return y1;
    }

    public int saveDesginColumnForMSSQL(Map<String, String> map, String databaseName, String tableName, String databaseConfigId) throws Exception {
        new DBUtil2(databaseName, databaseConfigId);
        String sql = " alter table " + tableName + " add  ";
        sql = sql + (String)map.get("COLUMN_NAME") + "  ";
        sql = sql + (String)map.get("DATA_TYPE");
        if(map.get("CHARACTER_MAXIMUM_LENGTH") != null && !((String)map.get("CHARACTER_MAXIMUM_LENGTH")).equals("")) {
            sql = sql + " (" + (String)map.get("CHARACTER_MAXIMUM_LENGTH") + ") ";
        }

        if(map.get("COLUMN_COMMENT") != null && !((String)map.get("COLUMN_COMMENT")).equals("")) {
            sql = sql + " comment \'" + (String)map.get("COLUMN_COMMENT") + "\'";
        }

        boolean y = false;
        int y1 = DBUtil2.setupdateData(sql);
        return y1;
    }

    public int updateTableColumnForMSSQL(Map<String, Object> map, String databaseName, String tableName, String columnName, String idValues, String databaseConfigId) throws Exception {
        if(columnName != null && !"".equals(columnName)) {
            if(idValues != null && !"".equals(idValues)) {
                new DBUtil2(databaseName, databaseConfigId);
                String old_field_name = (String)map.get("TREESOFTPRIMARYKEY");
                String column_name = (String)map.get("COLUMN_NAME");
                String data_type = (String)map.get("DATA_TYPE");
                String character_maximum_length = "" + map.get("CHARACTER_MAXIMUM_LENGTH");
                String column_comment = (String)map.get("COLUMN_COMMENT");
                String sql2;
                int y;
                if(!old_field_name.endsWith(column_name)) {
                    sql2 = " exec sp_rename \'" + tableName + "." + old_field_name + "\',\'" + column_name + "\',\'COLUMN\'";
                    y = DBUtil2.setupdateData(sql2);
                }

                sql2 = " alter table  " + tableName + " alter column " + column_name + " " + data_type;
                if(!data_type.equals("int") && !data_type.equals("date") && character_maximum_length != null && !character_maximum_length.equals("")) {
                    sql2 = sql2 + " (" + character_maximum_length + ")";
                }

                y = DBUtil2.setupdateData(sql2);
                if(column_comment != null && !column_comment.equals("")) {
                    String sql4 = "exec sp_addextendedproperty \'MS_Description\',\'" + column_comment + "\',\'user\',\'dbo\',\'TABLE\',\'" + tableName + "\',\'column\',\'" + column_name + "\' ";
                    System.out.println(sql4);
                    DBUtil2.setupdateData(sql4);
                }

                return y;
            } else {
                throw new Exception("数据不完整,保存失败!");
            }
        } else {
            throw new Exception("数据不完整,保存失败!");
        }
    }

    public int deleteTableColumnForMSSQL(String databaseName, String tableName, String[] ids, String databaseConfigId) throws Exception {
        new DBUtil2(databaseName, databaseConfigId);
        int y = 0;

        for(int i = 0; i < ids.length; ++i) {
            String sql = " alter table   " + tableName + " drop (" + ids[i] + ")";
            y += DBUtil2.setupdateData(sql);
        }

        return y;
    }

    public int updateTableNullAbleForMSSQL(String databaseName, String tableName, String column_name, String is_nullable, String databaseConfigId) throws Exception {
        String sql4 = "";
        if(column_name != null && !column_name.equals("")) {
            new DBUtil2(databaseName, databaseConfigId);
            String column_type = this.selectOneColumnTypeForMSSQL(databaseName, tableName, column_name, databaseConfigId);
            if(is_nullable.equals("true")) {
                sql4 = " alter table  " + tableName + " alter column   " + column_name + " " + column_type + " " + "  null ";
            } else {
                sql4 = " alter table  " + tableName + " alter column   " + column_name + " " + column_type + " " + "  not null ";
            }

            DBUtil2.setupdateData(sql4);
        }

        return 0;
    }

    public int savePrimaryKeyForMSSQL(String databaseName, String tableName, String column_name, String isSetting, String databaseConfigId) throws Exception {
        String sql4 = "";
        if(column_name != null && !column_name.equals("")) {
            new DBUtil2(databaseName, databaseConfigId);
            List list2 = this.selectTablePrimaryKeyForMSSQL(databaseName, tableName, databaseConfigId);
            ArrayList list3 = new ArrayList();
            Iterator primaryKey = list2.iterator();

            while(primaryKey.hasNext()) {
                Map tem = (Map)primaryKey.next();
                list3.add((String)tem.get("COLUMN_NAME"));
            }

            if(isSetting.equals("true")) {
                list3.add(column_name);
            } else {
                list3.remove(column_name);
            }

            String tem1 = list3.toString();
            String primaryKey1 = tem1.substring(1, tem1.length() - 1);
            if(list2.size() > 0) {
                String temp = (String)((Map)list2.get(0)).get("CONSTRAINT_NAME");
                sql4 = " alter table   " + tableName + " drop constraint  " + temp;
                System.out.println("1=" + sql4);
                DBUtil2.setupdateData(sql4);
            }

            if(!primaryKey1.equals("")) {
                sql4 = " alter table " + tableName + " add   primary key (" + primaryKey1 + ") ";
                System.out.println("2=" + sql4);
                DBUtil2.setupdateData(sql4);
            }
        }

        return 0;
    }

    public List<Map<String, Object>> selectTablePrimaryKeyForMSSQL(String databaseName, String tableName, String databaseConfigId) throws Exception {
        String sql = "  select  b.CONSTRAINT_NAME, b.COLUMN_NAME  from information_schema.table_constraints a  inner join information_schema.constraint_column_usage b  on a.constraint_name = b.constraint_name  where a.constraint_type = \'PRIMARY KEY\' and a.table_name = \'" + tableName + "\'";
        DBUtil2 db = new DBUtil2(databaseName, databaseConfigId);
        List list = db.queryForList(sql);
        new ArrayList();
        return list;
    }

    public List<Map<String, Object>> getPrimaryKeyssForMSSQL(String databaseName, String tableName, String databaseConfigId) throws Exception {
        String sql = " select  c.name as COLUMN_NAME from sysindexes i   join sysindexkeys k on i.id = k.id and i.indid = k.indid    join sysobjects o on i.id = o.id    join syscolumns c on i.id=c.id and k.colid = c.colid    where o.xtype = \'U\'   and exists(select 1 from sysobjects where  xtype = \'PK\'  and name = i.name)     and o.name=\'" + tableName + "\' ";
        DBUtil2 db = new DBUtil2(databaseName, databaseConfigId);
        List list = db.queryForList(sql);
        return list;
    }

    public List<Map<String, Object>> getTableColumnsForMSSQL(String dbName, String tableName, String databaseConfigId) throws Exception {
        String sql = "select top 1 * from   " + tableName;
        new DBUtil2(dbName, databaseConfigId);
        List list = DBUtil2.queryForColumnOnly(sql);
        return list;
    }

    public List<Map<String, Object>> executeSqlForColumnsForMSSQL(String sql, String dbName, String databaseConfigId) throws Exception {
        String sql2 = " select top 1 * from (" + sql + ") t  ";
        new DBUtil2(dbName, databaseConfigId);
        List list = DBUtil2.executeSqlForColumns(sql2);
        return list;
    }

    public String selectOneColumnTypeForMSSQL(String databaseName, String tableName, String column_name, String databaseConfigId) throws Exception {
        String sql = " select  ISNULL( c.name +\'(\'+  cast(b.length as varchar(10)) +\')\' , c.name ) as  column_type  from sysobjects a,syscolumns b,systypes c  where a.id=b.id  and a.name=\'" + tableName + "\'  and  b.name=\'" + column_name + "\' and a.xtype=\'U\'  and b.xtype=c.xtype ";
        DBUtil2 db = new DBUtil2(databaseName, databaseConfigId);
        List list = db.queryForList(sql);
        return (String)((Map)list.get(0)).get("column_type");
    }

    public boolean backupDatabaseExecute(String databaseName, String tableName, String path, String databaseConfigId) throws Exception {
        BackDbForMySql ff = new BackDbForMySql();
        ff.readDataToFile(databaseName, tableName, path, databaseConfigId);
        return true;
    }

    public boolean backupDatabaseExecuteForOracle(String databaseName, String tableName, String path, String databaseConfigId) throws Exception {
        BackDbForOracle bdo = new BackDbForOracle();
        bdo.readDataToFile(databaseName, tableName, path, databaseConfigId);
        return true;
    }

    public boolean backupDatabaseExecuteForPostgreSQL(String databaseName, String tableName, String path, String databaseConfigId) throws Exception {
        BackDbForPostgreSQL bdo = new BackDbForPostgreSQL();
        bdo.readDataToFile(databaseName, tableName, path, databaseConfigId);
        return true;
    }

    public boolean backupDatabaseExecuteForMSSQL(String databaseName, String tableName, String path, String databaseConfigId) throws Exception {
        BackDbForMSSQL bdo = new BackDbForMSSQL();
        bdo.readDataToFile(databaseName, tableName, path, databaseConfigId);
        return true;
    }

    public boolean copyTableForMySql(String databaseName, String tableName, String databaseConfigId) throws Exception {
        String sql4 = "create table " + databaseName + "." + tableName + "_" + DateUtils.getDateTimeString(new Date()) + "  select * from " + databaseName + "." + tableName;
        new DBUtil2(databaseName, databaseConfigId);
        DBUtil2.setupdateData(sql4);
        return true;
    }

    public boolean copyTableForOracle(String databaseName, String tableName, String databaseConfigId) throws Exception {
        String sql4 = "create table " + tableName + "_" + DateUtils.getDateTimeString(new Date()) + " as select * from " + tableName;
        new DBUtil2(databaseName, databaseConfigId);
        DBUtil2.setupdateData(sql4);
        return true;
    }

    public boolean copyTableForPostgreSQL(String databaseName, String tableName, String databaseConfigId) throws Exception {
        String sql4 = "create table \"" + tableName + "_" + DateUtils.getDateTimeString(new Date()) + "\" as select * from \"" + tableName + "\"";
        new DBUtil2(databaseName, databaseConfigId);
        DBUtil2.setupdateData(sql4);
        return true;
    }

    public boolean renameTableForMySql(String databaseName, String tableName, String databaseConfigId, String newTableName) throws Exception {
        String sql4 = " rename table " + tableName + " TO  " + newTableName;
        new DBUtil2(databaseName, databaseConfigId);
        DBUtil2.setupdateData(sql4);
        return true;
    }

    public boolean renameTableForOracle(String databaseName, String tableName, String databaseConfigId, String newTableName) throws Exception {
        String sql4 = " alter table " + tableName + " rename to  " + newTableName;
        new DBUtil2(databaseName, databaseConfigId);
        DBUtil2.setupdateData(sql4);
        return true;
    }

    public boolean renameTableForPostgreSQL(String databaseName, String tableName, String databaseConfigId, String newTableName) throws Exception {
        tableName = "\"" + tableName + "\"";
        newTableName = "\"" + newTableName + "\"";
        String sql4 = " alter table " + tableName + " rename to  " + newTableName;
        new DBUtil2(databaseName, databaseConfigId);
        DBUtil2.setupdateData(sql4);
        return true;
    }

    public boolean renameTableForHive2(String databaseName, String tableName, String databaseConfigId, String newTableName) throws Exception {
        tableName = "\"" + tableName + "\"";
        newTableName = "\"" + newTableName + "\"";
        String sql4 = " alter table " + tableName + " rename to  " + newTableName;
        new DBUtil2(databaseName, databaseConfigId);
        DBUtil2.setupdateData(sql4);
        return true;
    }

    public List<Map<String, Object>> exportDataToSQLForMySQL(String databaseName, String tableName, List<String> condition, String databaseConfigId) throws Exception {
        String sql = " select * from  `" + tableName + "` where   1=1  ";
        DBUtil2 db = new DBUtil2(databaseName, databaseConfigId);
        ArrayList list = new ArrayList();

        for(int i = 0; i < condition.size(); ++i) {
            List list2 = db.queryForList(sql + (String)condition.get(i));
            list.add((Map)list2.get(0));
        }

        return list;
    }

    public List<Map<String, Object>> exportDataToSQLForOracle(String databaseName, String tableName, List<String> condition, String databaseConfigId) throws Exception {
        String sql = " select * from  " + tableName + " where   1=1  ";
        DBUtil2 db = new DBUtil2(databaseName, databaseConfigId);
        ArrayList list = new ArrayList();

        for(int i = 0; i < condition.size(); ++i) {
            List list2 = db.queryForList(sql + (String)condition.get(i));
            list.add((Map)list2.get(0));
        }

        return list;
    }

    public List<Map<String, Object>> exportDataToSQLForPostgreSQL(String databaseName, String tableName, List<String> condition, String databaseConfigId) throws Exception {
        String sql = " select * from  \"" + tableName + "\" where   1=1  ";
        DBUtil2 db = new DBUtil2(databaseName, databaseConfigId);
        ArrayList list = new ArrayList();

        for(int i = 0; i < condition.size(); ++i) {
            List list2 = db.queryForList(sql + (String)condition.get(i));
            list.add((Map)list2.get(0));
        }

        return list;
    }

    public List<Map<String, Object>> exportDataToSQLForMSSQL(String databaseName, String tableName, List<String> condition, String databaseConfigId) throws Exception {
        String sql = " select * from  " + tableName + " where   1=1  ";
        DBUtil2 db = new DBUtil2(databaseName, databaseConfigId);
        ArrayList list = new ArrayList();

        for(int i = 0; i < condition.size(); ++i) {
            List list2 = db.queryForList(sql + (String)condition.get(i));
            list.add((Map)list2.get(0));
        }

        return list;
    }

    public List<Map<String, Object>> exportDataToSQLForMongoDB(String databaseName, String tableName, List<String> condition, String databaseConfigId) throws Exception {
        MongoDBUtil db = new MongoDBUtil(databaseConfigId);
        SimpleDateFormat sdf2 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        MongoCollection dbCollection = db.getCollection(databaseName, tableName);
        ArrayList list = new ArrayList();

        for(int mapper = 0; mapper < condition.size(); ++mapper) {
            String id = (String)condition.get(mapper);
            Document doc = db.findById(dbCollection, id);
            HashMap map3 = new HashMap();
            Iterator var14 = doc.entrySet().iterator();

            while(var14.hasNext()) {
                Entry entry = (Entry)var14.next();
                String key = (String)entry.getKey();
                Object param = entry.getValue();
                if(param instanceof String) {
                    map3.put(key, param);
                } else if(param instanceof Date) {
                    map3.put(key, sdf2.format(param));
                } else if(param instanceof ObjectId) {
                    map3.put(key, "ObjectId(\"" + param.toString() + "\")");
                } else if(param instanceof Document) {
                    map3.put(key, (Document)param);
                } else if(param instanceof Object) {
                    map3.put(key, param);
                } else {
                    map3.put(key, param);
                }
            }

            list.add(map3);
        }

        new ObjectMapper();
        db.close();
        return list;
    }

    public List<Map<String, Object>> getAllDataBaseForMongoDB(String databaseConfigId) throws Exception {
        Map map12 = this.getConfig(databaseConfigId);
        String databaseName = "" + map12.get("databaseName");
        MongoDBUtil db = new MongoDBUtil(databaseConfigId);
        Object list = new ArrayList();

        try {
            list = db.getAllDBNames();
        } catch (Exception var10) {
            ((List)list).add(databaseName);
        }

        ArrayList list2 = new ArrayList();
        Iterator var8 = ((List)list).iterator();

        while(var8.hasNext()) {
            String str = (String)var8.next();
            HashMap map = new HashMap();
            map.put("SCHEMA_NAME", str);
            list2.add(map);
        }

        db.close();
        return list2;
    }

    public List<Map<String, Object>> getAllTablesForMongoDB(String databaseName, String databaseConfigId) throws Exception {
        MongoDBUtil db = new MongoDBUtil(databaseConfigId);
        List list = db.getAllCollections(databaseName);
        ArrayList list2 = new ArrayList();
        Iterator var7 = list.iterator();

        while(var7.hasNext()) {
            String s = (String)var7.next();
            HashMap map = new HashMap();
            map.put("TABLE_NAME", s);
            list2.add(map);
        }

        db.close();
        return list2;
    }

    public List<Map<String, Object>> getAllTablesForHive2(String databaseName, String databaseConfigId) throws Exception {
        DBUtil2 db = new DBUtil2(databaseName, databaseConfigId);
        String sql = " use " + databaseName;
        DBUtil2.setupdateData(sql);
        String sql2 = "show tables ";
        new ArrayList();
        List list = db.queryForList(sql2);
        ArrayList list2 = new ArrayList();

        for(int i = 0; i < list.size(); ++i) {
            HashMap map = new HashMap();
            Map map2 = (Map)list.get(i);
            map.put("TABLE_NAME", map2.get("tab_name"));
            list2.add(map);
        }

        return list2;
    }

    public Page<Map<String, Object>> getDataForMongoDB(Page<Map<String, Object>> page, String tableName, String dbName, String databaseConfigId) throws Exception {
        int pageNo = page.getPageNo();
        int pageSize = page.getPageSize();
        int limitFrom = (pageNo - 1) * pageSize;
        if(limitFrom > 0) {
            --limitFrom;
        }

        String orderBy = page.getOrderBy();
        String order = page.getOrder();
        MongoDBUtil db = new MongoDBUtil(databaseConfigId);
        SimpleDateFormat sdf2 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        MongoCollection dbCollection = db.getCollection(dbName, tableName);
        BasicDBObject filter = new BasicDBObject();
        MongoCursor mongoCursor = db.findByPageForMongoDB(dbCollection, filter, pageNo, pageSize);
        int rowCount = (int)dbCollection.count();
        ArrayList list = new ArrayList();
        ArrayList tempList = new ArrayList();
        HashMap map1 = new HashMap();
        map1.put("field", "treeSoftPrimaryKey");
        map1.put("checkbox", Boolean.valueOf(true));
        tempList.add(map1);
        TreeSet ts = new TreeSet();

        while(mongoCursor.hasNext()) {
            Document mapper = (Document)mongoCursor.next();
            HashMap jsonfromList = new HashMap();
            Iterator var23 = mapper.keySet().iterator();

            Object param;
            while(var23.hasNext()) {
                String entry = (String)var23.next();
                if(!ts.contains(entry)) {
                    ts.add(entry);
                    HashMap key = new HashMap();
                    key.put("column_name", entry);
                    param = mapper.get(entry);
                    String data_type = "";
                    if(entry.equals("_id")) {
                        data_type = "String";
                    } else if(param instanceof String) {
                        data_type = "String";
                        key.put("editor", "text");
                    } else if(param instanceof Date) {
                        data_type = "Date";
                        key.put("editor", "datetimebox");
                    } else if(param instanceof Double) {
                        data_type = "Double";
                        key.put("editor", "numberbox");
                    } else if(param instanceof Boolean) {
                        data_type = "Boolean";
                        key.put("editor", "text");
                    } else if(param instanceof Integer) {
                        data_type = "Integer";
                        key.put("editor", "numberbox");
                    } else if(param instanceof Float) {
                        data_type = "Float";
                        key.put("editor", "numberbox");
                    } else if(param instanceof Long) {
                        data_type = "Long";
                        key.put("editor", "numberbox");
                    } else if(param instanceof Document) {
                        data_type = "Document";
                    } else if(param instanceof ObjectId) {
                        data_type = "ObjectId";
                    } else {
                        data_type = "Object";
                    }

                    key.put("data_type", data_type);
                    key.put("field", entry);
                    key.put("title", entry);
                    key.put("sortable", Boolean.valueOf(true));
                    tempList.add(key);
                }
            }

            var23 = mapper.entrySet().iterator();

            while(var23.hasNext()) {
                Entry var30 = (Entry)var23.next();
                String var32 = (String)var30.getKey();
                param = var30.getValue();
                if(param instanceof String) {
                    jsonfromList.put(var32, param);
                } else if(param instanceof Date) {
                    jsonfromList.put(var32, sdf2.format(param));
                } else if(param instanceof ObjectId) {
                    jsonfromList.put(var32, "ObjectId(\"" + param.toString() + "\")");
                } else if(param instanceof Document) {
                    jsonfromList.put(var32, "{ " + ((Document)param).size() + " field }");
                } else if(param instanceof Object) {
                    jsonfromList.put(var32, param);
                } else {
                    jsonfromList.put(var32, param);
                }
            }

            list.add(jsonfromList);
        }

        String var29;
        if(list.size() > 0) {
            new HashMap();
            Map var27 = (Map)list.get(0);
            Iterator var31 = ts.iterator();

            while(var31.hasNext()) {
                var29 = (String)var31.next();
                if(var27.get(var29) == null) {
                    var27.put(var29, "");
                }
            }

            list.set(0, var27);
        }

        ObjectMapper var28 = new ObjectMapper();
        var29 = "[" + var28.writeValueAsString(tempList) + "]";
        page.setTotalCount((long)rowCount);
        page.setResult(list);
        page.setColumns(var29);
        page.setPrimaryKey("_id");
        page.setTableName(tableName);
        db.close();
        return page;
    }

    public Page<Map<String, Object>> getDataForMongoJson(Page<Map<String, Object>> page, String tableName, String dbName, String databaseConfigId) throws Exception {
        int pageNo = page.getPageNo();
        int pageSize = page.getPageSize();
        int limitFrom = (pageNo - 1) * pageSize;
        if(limitFrom > 0) {
            --limitFrom;
        }

        String orderBy = page.getOrderBy();
        String order = page.getOrder();
        MongoDBUtil db = new MongoDBUtil(databaseConfigId);
        SimpleDateFormat sdf2 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        MongoCollection dbCollection = db.getCollection(dbName, tableName);
        BasicDBObject filter = new BasicDBObject();
        MongoCursor mongoCursor = db.findByPageForMongoDB(dbCollection, filter, pageNo, pageSize);
        int rowCount = (int)dbCollection.count();
        ArrayList list = new ArrayList();

        while(mongoCursor.hasNext()) {
            Document mapper = (Document)mongoCursor.next();
            HashMap map3 = new HashMap();
            Iterator var20 = mapper.entrySet().iterator();

            while(var20.hasNext()) {
                Entry entry = (Entry)var20.next();
                String key = (String)entry.getKey();
                Object param = entry.getValue();
                if(param instanceof String) {
                    map3.put(key, param);
                } else if(param instanceof Date) {
                    map3.put(key, sdf2.format(param));
                } else if(param instanceof ObjectId) {
                    map3.put(key, "ObjectId(\"" + param.toString() + "\")");
                } else if(param instanceof Document) {
                    map3.put(key, (Document)param);
                } else if(param instanceof Object) {
                    map3.put(key, param);
                } else {
                    map3.put(key, param);
                }
            }

            list.add(map3);
        }

        new ObjectMapper();
        page.setTotalCount((long)rowCount);
        page.setResult(list);
        page.setTableName(tableName);
        db.close();
        return page;
    }

    public Page<Map<String, Object>> executeSqlHaveResForMongoDB(Page<Map<String, Object>> page, String sql, String dbName, String databaseConfigId) throws Exception {
        int pageNo = page.getPageNo();
        int pageSize = page.getPageSize();
        String tableName = "";
        String queryStr = "";
        String orderLimit = "";
        String[] str2 = sql.split("\\.");
        tableName = str2[1];
        queryStr = str2[2];
        queryStr = queryStr.replace("find(", "");
        queryStr = queryStr.substring(0, queryStr.lastIndexOf(")"));
        if(queryStr.equals("")) {
            queryStr = "{}";
        }

        JSONObject obj = JSONObject.fromObject(queryStr);
        MongoDBUtil db = new MongoDBUtil(databaseConfigId);
        MongoCollection dbCollection = db.getCollection(dbName, tableName);
        SimpleDateFormat sdf2 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Document filter = new Document(obj);
        MongoCursor mongoCursor = db.findByPageForMongoDB(dbCollection, filter, pageNo, pageSize);
        int rowCount = 0;
        ArrayList list = new ArrayList();
        ArrayList tempList = new ArrayList();

        while(mongoCursor.hasNext()) {
            Document mapper = (Document)mongoCursor.next();
            HashMap jsonfromList = new HashMap();
            ++rowCount;
            if(tempList.size() == 0) {
                HashMap entry = new HashMap();
                entry.put("field", "treeSoftPrimaryKey");
                entry.put("checkbox", Boolean.valueOf(true));
                tempList.add(entry);
                Iterator key = mapper.keySet().iterator();

                while(key.hasNext()) {
                    String columnName = (String)key.next();
                    HashMap param = new HashMap();
                    param.put("column_name", columnName);
                    Object param1 = mapper.get(columnName);
                    String data_type = "";
                    if(columnName.equals("_id")) {
                        data_type = "String";
                    } else if(param1 instanceof String) {
                        data_type = "String";
                        param.put("editor", "text");
                    } else if(param1 instanceof Date) {
                        data_type = "Date";
                        param.put("editor", "datetimebox");
                    } else if(param1 instanceof Double) {
                        data_type = "Double";
                        param.put("editor", "numberbox");
                    } else if(param1 instanceof Boolean) {
                        data_type = "Boolean";
                        param.put("editor", "text");
                    } else if(param1 instanceof Integer) {
                        data_type = "Integer";
                        param.put("editor", "numberbox");
                    } else if(param1 instanceof Float) {
                        data_type = "Float";
                        param.put("editor", "numberbox");
                    } else if(param1 instanceof Long) {
                        data_type = "Long";
                        param.put("editor", "numberbox");
                    } else if(param1 instanceof Document) {
                        data_type = "Document";
                    } else if(param1 instanceof ObjectId) {
                        data_type = "ObjectId";
                    } else {
                        data_type = "Object";
                    }

                    param.put("data_type", data_type);
                    param.put("field", columnName);
                    param.put("title", columnName);
                    param.put("sortable", Boolean.valueOf(true));
                    tempList.add(param);
                }
            }

            Iterator var31 = mapper.entrySet().iterator();

            while(var31.hasNext()) {
                Entry var30 = (Entry)var31.next();
                String var32 = (String)var30.getKey();
                Object var33 = var30.getValue();
                if(var33 instanceof String) {
                    jsonfromList.put(var32, var33);
                } else if(var33 instanceof Date) {
                    jsonfromList.put(var32, sdf2.format(var33));
                } else if(var33 instanceof ObjectId) {
                    jsonfromList.put(var32, "ObjectId(\"" + var33.toString() + "\")");
                } else if(var33 instanceof Document) {
                    jsonfromList.put(var32, "{ " + ((Document)var33).size() + " field }");
                } else if(var33 instanceof Object) {
                    jsonfromList.put(var32, var33);
                } else {
                    jsonfromList.put(var32, var33);
                }
            }

            list.add(jsonfromList);
        }

        ObjectMapper var28 = new ObjectMapper();
        String var29 = "[" + var28.writeValueAsString(tempList) + "]";
        page.setTotalCount((long)rowCount);
        page.setResult(list);
        page.setColumns(var29);
        page.setPrimaryKey("_id");
        page.setTableName(tableName);
        db.close();
        return page;
    }

    public int deleteRowsNewForMongoDB(String databaseName, String tableName, String primary_key, List<String> condition, String databaseConfigId) throws Exception {
        MongoDBUtil db = new MongoDBUtil(databaseConfigId);
        MongoCollection dbCollection = db.getCollection(databaseName, tableName);
        int y = 0;

        for(int i = 0; i < condition.size(); ++i) {
            String id = ((String)condition.get(i)).substring(12, ((String)condition.get(i)).length() - 2);
            id = id.trim();
            System.out.println("MongoDB删除数据行, " + DateUtils.getDateTime() + ", _id=" + id);
            y += db.deleteById(dbCollection, id);
        }

        db.close();
        return y;
    }

    public int saveRowsForMongoDB(Map<String, String> map, String databaseName, String tableName, String databaseConfigId) throws Exception {
        MongoDBUtil db = new MongoDBUtil(databaseConfigId);
        MongoCollection collection = db.getCollection(databaseName, tableName);
        byte y = 0;
        boolean isUUID = true;
        Document myDoc = (Document)collection.find().first();
        if(myDoc == null) {
            myDoc = new Document();
        }

        Object param = myDoc.get("_id");
        if(param instanceof String) {
            isUUID = true;
        } else {
            isUUID = false;
        }

        if(isUUID) {
            String obj = UUID.randomUUID().toString().replaceAll("-", "");
            map.put("_id", obj);
        }

        JSONObject obj1 = JSONObject.fromObject(map);
        Document document = new Document(obj1);
        collection.insertOne(document);
        db.close();
        return y;
    }

    public int updateRowsNewForMongoDB(String databaseName, String tableName, List<String> strList, String databaseConfigId) throws Exception {
        MongoDBUtil db = new MongoDBUtil(databaseConfigId);
        MongoCollection collection = db.getCollection(databaseName, tableName);
        byte y = 0;
        Iterator var9 = strList.iterator();

        while(var9.hasNext()) {
            String str = (String)var9.next();
            String[] sss = str.split("#@@#");
            JSONObject obj = JSONObject.fromObject(sss[0]);
            Document newdoc = new Document(obj);
            String id = sss[1];
            db.updateById(collection, id, newdoc);
        }

        db.close();
        return y;
    }

    public int executeSqlNotResForMongoDB(String sql, String databaseName, String databaseConfigId) throws Exception {
        MongoDBUtil db = new MongoDBUtil(databaseConfigId);
        int i = 1;
        String tableName = "";
        String insertStr = "";
        String removeStr = "";
        String[] str2 = sql.split("\\.");
        tableName = str2[1];
        MongoCollection dbCollection = db.getCollection(databaseName, tableName);
        insertStr = sql.replace("db." + tableName + ".insert(", "");
        insertStr = insertStr.substring(0, insertStr.lastIndexOf(")"));
        removeStr = sql.replace("db." + tableName + ".remove(", "");
        removeStr = removeStr.substring(0, removeStr.lastIndexOf(")"));
        if(insertStr.equals("")) {
            insertStr = "{}";
        }

        JSONObject obj;
        Document doc;
        if(sql.indexOf("insert") > 0) {
            obj = JSONObject.fromObject(insertStr);
            doc = new Document(obj);
            dbCollection.insertOne(doc);
        }

        if(sql.indexOf("remove") > 0) {
            obj = JSONObject.fromObject(removeStr);
            doc = new Document(obj);
            DeleteResult ss = dbCollection.deleteMany(doc);
            i = (int)ss.getDeletedCount();
        }

        db.close();
        return i;
    }

    public int executeSqlNotResForMongoDBInsert(List<Document> list, String databaseName, String tableName, String databaseConfigId) throws Exception {
        MongoDBUtil db = new MongoDBUtil(databaseConfigId);
        MongoCollection dbCollection = db.getCollection(databaseName, tableName);
        dbCollection.insertMany(list);
        db.close();
        return 1;
    }

    public Map<String, Object> getConfig(String id) {
        DBUtil db = new DBUtil();
        String sql = " select id, name, databaseType , databaseName, userName ,  password, port, ip ,url ,isdefault from  treesoft_config where id=\'" + id + "\'";
        List list = db.executeQuery2(sql);
        Map map = (Map)list.get(0);
        // 取消密码加密
//        String password = CryptoUtil.decode("" + map.get("password"));
//        if(password.split("`").length > 1) {
//            password = password.split("`")[1];
//        } else {
//            password = "";
//        }
//
//        map.put("password", password);
        return map;
    }

    public boolean deleteBackupFile(String[] ids, String path) throws Exception {
        for(int i = 0; i < ids.length; ++i) {
            File f = new File(path + ids[i]);
            if(f.exists()) {
                f.delete();
            }
        }

        return true;
    }

    public boolean dropTable(String databaseName, String tableName, String databaseConfigId) throws Exception {
        new DBUtil2(databaseName, databaseConfigId);
        String sql = " drop  table " + databaseName + "." + tableName;
        DBUtil2.setupdateData(sql);
        return true;
    }

    public boolean dropTableForOracle(String databaseName, String tableName, String databaseConfigId) throws Exception {
        new DBUtil2(databaseName, databaseConfigId);
        String sql = " drop  table " + tableName;
        DBUtil2.setupdateData(sql);
        return true;
    }

    public boolean dropTableForPostgreSQL(String databaseName, String tableName, String databaseConfigId) throws Exception {
        new DBUtil2(databaseName, databaseConfigId);
        tableName = "\"" + tableName + "\"";
        String sql = " drop  table " + tableName;
        DBUtil2.setupdateData(sql);
        return true;
    }

    public boolean dropDatabase(String databaseName, String databaseConfigId) throws Exception {
        new DBUtil2(databaseName, databaseConfigId);
        String sql = " drop  database " + databaseName;
        DBUtil2.setupdateData(sql);
        return true;
    }

    public boolean dropTableForMongoDB(String databaseName, String tableName, String databaseConfigId) throws Exception {
        MongoDBUtil db = new MongoDBUtil(databaseConfigId);
        db.dropCollection(databaseName, tableName);
        db.close();
        return true;
    }

    public boolean dropDatabaseForMongoDB(String databaseName, String databaseConfigId) throws Exception {
        MongoDBUtil db = new MongoDBUtil(databaseConfigId);
        db.dropDB(databaseName);
        db.close();
        return true;
    }

    public boolean clearTable(String databaseName, String tableName, String databaseConfigId) throws Exception {
        new DBUtil2(databaseName, databaseConfigId);
        tableName = "\"" + tableName + "\"";
        String sql = " delete from  " + tableName;
        DBUtil2.setupdateData(sql);
        return true;
    }

    public boolean clearTableForMongoDB(String databaseName, String tableName, String databaseConfigId) throws Exception {
        MongoDBUtil db = new MongoDBUtil(databaseConfigId);
        db.deleteCollection(databaseName, tableName);
        db.close();
        return true;
    }

    public boolean restoreDBFromFile(String databaseName, String fpath, String databaseConfigId) throws Exception {
        DBUtil2 db = new DBUtil2(databaseName, databaseConfigId);
        StringBuffer sb = new StringBuffer();
        BufferedReader reader = null;
        boolean isZS = false;
        boolean isNull = false;
        boolean isDELIMITER = false;
        ArrayList insertSQLList = new ArrayList();

        try {
            reader = new BufferedReader(new InputStreamReader(new FileInputStream(fpath), "UTF-8"));
            String e = null;

            while((e = reader.readLine()) != null) {
                e = e.trim();
                if(e.equals("")) {
                    isNull = true;
                } else {
                    isNull = false;
                    if(e.equals("DELIMITER ;;")) {
                        isDELIMITER = true;
                    } else if(e.equals("DELIMITER ;")) {
                        isDELIMITER = false;

                        try {
                            DBUtil2.setupdateData(sb.toString());
                        } catch (Exception var24) {
                            System.out.println("数据库还原出错1，" + var24.getMessage());
                            System.out.println(sb.toString());
                        }

                        sb.setLength(0);
                    } else if(isDELIMITER) {
                        sb = sb.append(e + " ");
                    } else if(e.indexOf("/*") == 0) {
                        isZS = true;
                    } else if(e.indexOf("*/") >= 0) {
                        isZS = false;
                    } else if(!isZS && e.indexOf("--") != 0 && e.indexOf("#") != 0) {
                        if(e.indexOf("INSERT INTO") != 0 && e.lastIndexOf(";") != e.length() - 1) {
                            sb = sb.append(e);
                        }

                        if(e.indexOf("INSERT INTO") != 0 && e.lastIndexOf(";") == e.length() - 1) {
                            sb = sb.append(e);

                            try {
                                DBUtil2.setupdateData(sb.toString());
                            } catch (Exception var23) {
                                System.out.println("数据库还原出错2，" + var23.getMessage());
                                System.out.println(sb.toString());
                            }

                            sb.setLength(0);
                        }

                        if(e.indexOf("INSERT INTO") == 0 && e.lastIndexOf(";") == e.length() - 1) {
                            insertSQLList.add(e);
                            if(insertSQLList.size() >= 1000) {
                                db.updateExecuteBatch(insertSQLList);
                                insertSQLList.clear();
                            }
                        }
                    }
                }
            }

            if(insertSQLList.size() > 0) {
                db.updateExecuteBatch(insertSQLList);
                insertSQLList.clear();
            }

            reader.close();
        } catch (IOException var25) {
            var25.printStackTrace();
        } finally {
            if(reader != null) {
                try {
                    reader.close();
                } catch (IOException var22) {
                    ;
                }
            }

        }

        return true;
    }

    public List<Map<String, Object>> viewTableMessForMySql(String dbName, String tableName, String databaseConfigId) throws Exception {
        String sql = "   select  *   from information_schema.tables where   table_name=\'" + tableName + "\' and table_schema=\'" + dbName + "\'  ";
        DBUtil2 db = new DBUtil2(dbName, databaseConfigId);
        List list = db.queryForList(sql);
        return list;
    }

    public List<Map<String, Object>> viewTableMessForMSSQL(String dbName, String tableName, String databaseConfigId) throws Exception {
        String sql = "  select top 1 t1.name as table_name, t1.crdate ,t1.refdate , t2.*  from   sysobjects t1 left join  sysindexes t2  on   t1.id=t2.id  where  t1.name =\'" + tableName + "\'  ";
        DBUtil2 db = new DBUtil2(dbName, databaseConfigId);
        List list = db.queryForList(sql);
        return list;
    }

    public List<Map<String, Object>> viewTableMessForPostgreSQL(String dbName, String tableName, String databaseConfigId) throws Exception {
        String sql = " select * from pg_tables  where tablename=\'" + tableName + "\'  ";
        DBUtil2 db = new DBUtil2(dbName, databaseConfigId);
        List list = db.queryForList(sql);
        return list;
    }

    public List<Map<String, Object>> viewTableMessForOracle(String dbName, String tableName, String databaseConfigId) throws Exception {
        String sql = "  select *  from user_tables   where table_name =\'" + tableName + "\' ";
        DBUtil2 db = new DBUtil2(dbName, databaseConfigId);
        List list = db.queryForList(sql);
        return list;
    }

    public List<Map<String, Object>> viewTableMessForHive2(String dbName, String tableName, String databaseConfigId) throws Exception {
        String sql = "desc formatted " + tableName + " ";
        DBUtil2 db = new DBUtil2(dbName, databaseConfigId);
        List list = db.queryForList(sql);
        return list;
    }

    public String getTableRows(String dbName, String tableName, String databaseConfigId) throws Exception {
        DBUtil2 db = new DBUtil2(dbName, databaseConfigId);
        String sql = " select count(*) as num from  " + tableName;
        List list = db.queryForList(sql);
        Map map = (Map)list.get(0);
        String str = String.valueOf(map.get("num"));
        return str;
    }

    public int saveNewTable(JSONArray insertArray, String databaseName, String tableName, String databaseConfigId) throws Exception {
        new DBUtil2(databaseName, databaseConfigId);
        String sql = " create table " + tableName + " (  ";
        String PRIMARY_KEY = "";

        int y;
        for(y = 0; y < insertArray.size(); ++y) {
            Map map1 = (Map)insertArray.get(y);
            sql = sql + map1.get("COLUMN_NAME") + "  ";
            sql = sql + map1.get("DATA_TYPE") + " ";
            if(!map1.get("CHARACTER_MAXIMUM_LENGTH").equals("")) {
                sql = sql + "(" + map1.get("CHARACTER_MAXIMUM_LENGTH") + ") ";
            }

            if(map1.get("IS_NULLABLE").equals("")) {
                sql = sql + " NOT NULL  ";
            }

            if(!map1.get("COLUMN_COMMENT").equals("")) {
                sql = sql + " COMMENT \'" + map1.get("COLUMN_COMMENT") + "\' ";
            }

            if(map1.get("COLUMN_KEY").equals("PRI")) {
                PRIMARY_KEY = PRIMARY_KEY + map1.get("COLUMN_NAME") + ",";
            }

            if(y < insertArray.size() - 1) {
                sql = sql + " ,";
            }
        }

        if(!PRIMARY_KEY.equals("")) {
            sql = sql + ", PRIMARY KEY (" + PRIMARY_KEY.substring(0, PRIMARY_KEY.length() - 1) + ") ) ";
        } else {
            sql = sql + " ) ";
        }

        boolean var10 = false;
        y = DBUtil2.setupdateData(sql);
        return y;
    }

    public int saveNewTableForPostgreSQL(JSONArray insertArray, String databaseName, String tableName, String databaseConfigId) throws Exception {
        new DBUtil2(databaseName, databaseConfigId);
        String sql = " create table \"" + tableName + "\" (  ";
        String PRIMARY_KEY = "";

        int y;
        for(y = 0; y < insertArray.size(); ++y) {
            Map map1 = (Map)insertArray.get(y);
            sql = sql + "\"" + map1.get("COLUMN_NAME") + "\"  ";
            sql = sql + map1.get("DATA_TYPE") + " ";
            if(!map1.get("CHARACTER_MAXIMUM_LENGTH").equals("")) {
                sql = sql + "(" + map1.get("CHARACTER_MAXIMUM_LENGTH") + ") ";
            }

            if(map1.get("IS_NULLABLE").equals("")) {
                sql = sql + " NOT NULL  ";
            }

            if(!map1.get("COLUMN_COMMENT").equals("")) {
                sql = sql + " COMMENT \'" + map1.get("COLUMN_COMMENT") + "\' ";
            }

            if(map1.get("COLUMN_KEY").equals("PRI")) {
                PRIMARY_KEY = PRIMARY_KEY + map1.get("COLUMN_NAME") + ",";
            }

            if(y < insertArray.size() - 1) {
                sql = sql + " ,";
            }
        }

        if(!PRIMARY_KEY.equals("")) {
            sql = sql + ", PRIMARY KEY (" + PRIMARY_KEY.substring(0, PRIMARY_KEY.length() - 1) + ") ) ";
        } else {
            sql = sql + " ) ";
        }

        boolean var10 = false;
        y = DBUtil2.setupdateData(sql);
        return y;
    }

    public int saveNewTableForMSSQL(JSONArray insertArray, String databaseName, String tableName, String databaseConfigId) throws Exception {
        new DBUtil2(databaseName, databaseConfigId);
        String sql = " create table " + tableName + " (  ";
        String PRIMARY_KEY = "";
        new ArrayList();

        int y;
        for(y = 0; y < insertArray.size(); ++y) {
            Map map1 = (Map)insertArray.get(y);
            sql = sql + map1.get("COLUMN_NAME") + " ";
            sql = sql + map1.get("DATA_TYPE") + " ";
            if(!map1.get("CHARACTER_MAXIMUM_LENGTH").equals("")) {
                sql = sql + "(" + map1.get("CHARACTER_MAXIMUM_LENGTH") + ") ";
            }

            if(map1.get("IS_NULLABLE").equals("")) {
                sql = sql + " NOT NULL  ";
            }

            if(map1.get("COLUMN_KEY").equals("PRI")) {
                PRIMARY_KEY = PRIMARY_KEY + map1.get("COLUMN_NAME") + ",";
            }

            if(y < insertArray.size() - 1) {
                sql = sql + " ,";
            }
        }

        if(!PRIMARY_KEY.equals("")) {
            sql = sql + ", PRIMARY KEY (" + PRIMARY_KEY.substring(0, PRIMARY_KEY.length() - 1) + ") ) ";
        } else {
            sql = sql + " ) ";
        }

        boolean var11 = false;
        y = DBUtil2.setupdateData(sql);
        return y;
    }

    public int saveNewTableForOracle(JSONArray insertArray, String databaseName, String tableName, String databaseConfigId) throws Exception {
        new DBUtil2(databaseName, databaseConfigId);
        String sql = " create table " + tableName + " (  ";
        String PRIMARY_KEY = "";
        ArrayList commentStr = new ArrayList();

        int y;
        for(y = 0; y < insertArray.size(); ++y) {
            Map i = (Map)insertArray.get(y);
            sql = sql + i.get("COLUMN_NAME") + " ";
            sql = sql + i.get("DATA_TYPE") + " ";
            if(!i.get("CHARACTER_MAXIMUM_LENGTH").equals("")) {
                sql = sql + "(" + i.get("CHARACTER_MAXIMUM_LENGTH") + ") ";
            }

            if(i.get("IS_NULLABLE").equals("")) {
                sql = sql + " NOT NULL  ";
            }

            if(!i.get("COLUMN_COMMENT").equals("")) {
                commentStr.add(" COMMENT ON COLUMN  " + tableName + "." + i.get("COLUMN_NAME") + " IS  \'" + i.get("COLUMN_COMMENT") + "\'");
            }

            if(i.get("COLUMN_KEY").equals("PRI")) {
                PRIMARY_KEY = PRIMARY_KEY + i.get("COLUMN_NAME") + ",";
            }

            if(y < insertArray.size() - 1) {
                sql = sql + " ,";
            }
        }

        if(!PRIMARY_KEY.equals("")) {
            sql = sql + ", PRIMARY KEY (" + PRIMARY_KEY.substring(0, PRIMARY_KEY.length() - 1) + ") ) ";
        } else {
            sql = sql + " ) ";
        }

        boolean var11 = false;
        y = DBUtil2.setupdateData(sql);

        for(int var12 = 0; var12 < commentStr.size(); ++var12) {
            DBUtil2.setupdateData((String)commentStr.get(var12));
        }

        return y;
    }

    public Map<String, Object> queryDatabaseStatus(String databaseName, String databaseConfigId) throws Exception {
        DBUtil2 db = new DBUtil2(databaseName, databaseConfigId);
        String sql = " show global status  ";
        List list = db.queryForList(sql);
        HashMap map = new HashMap();
        String Variable_name = "";
        String Value = "";

        for(int i = 0; i < list.size(); ++i) {
            Map temp = (Map)list.get(i);
            Variable_name = (String)temp.get("Variable_name");
            Value = (String)temp.get("Value");
            map.put(Variable_name, Value);
        }

        return map;
    }

    public Map<String, Object> queryDatabaseStatusForPostgreSQL(String databaseName, String databaseConfigId) throws Exception {
        HashMap map = new HashMap();
        String Variable_name = "";
        String Value = "";
        Map mm1 = this.queryDatabaseSQPSForPostgreSQL(databaseName, databaseConfigId);
        map.put("SESSIONS", this.queryDatabaseStatusForPostgreSQLConn(databaseName, databaseConfigId));
        map.put("dbSize", this.queryDatabaseStatusForPostgreSQLDBSize(databaseName, databaseConfigId));
        map.put("LOCK", this.queryDatabaseStatusForPostgreSQLLocks(databaseName, databaseConfigId));
        map.put("version", this.queryDatabaseVersionForPostgreSQL(databaseName, databaseConfigId));
        map.put("tableSpaceSize", this.queryDatabaseTableSpaceForPostgreSQL(databaseName, databaseConfigId));
        map.putAll(mm1);
        return map;
    }

    public String queryDatabaseTableSpaceForPostgreSQL(String databaseName, String databaseConfigId) {
        DBUtil2 db = new DBUtil2(databaseName, databaseConfigId);
        String value = "0";

        try {
            String e = " select  pg_size_pretty(pg_tablespace_size(t1.spcname)) as tableSpaceSize from pg_tablespace t1 left join pg_database t2 on t1.oid = t2.dattablespace where t2.datname=\'" + databaseName + "\' ";
            List list = db.queryForList(e);
            if(list.size() > 0) {
                Map temp = (Map)list.get(0);
                value = "" + temp.get("tablespacesize");
            }
        } catch (Exception var8) {
            var8.printStackTrace();
            System.out.println("error=" + var8.getMessage());
        }

        return value;
    }

    public String queryDatabaseVersionForPostgreSQL(String databaseName, String databaseConfigId) throws Exception {
        DBUtil2 db = new DBUtil2(databaseName, databaseConfigId);
        String sql = " select version() ";
        List list = db.queryForList(sql);
        String value = "0";
        if(list.size() > 0) {
            Map temp = (Map)list.get(0);
            value = "" + temp.get("version");
            if(value.split(",").length > 0) {
                value = value.split(",")[0];
            }
        }

        return value;
    }

    public String queryDatabaseStatusForPostgreSQLConn(String databaseName, String databaseConfigId) throws Exception {
        DBUtil2 db = new DBUtil2(databaseName, databaseConfigId);
        String sql = "select count(*) as connections from pg_stat_activity";
        List list = db.queryForList(sql);
        String value = "0";
        if(list.size() > 0) {
            Map temp = (Map)list.get(0);
            value = "" + temp.get("connections");
        }

        return value;
    }

    public String queryDatabaseStatusForPostgreSQLLocks(String databaseName, String databaseConfigId) throws Exception {
        String value;
        try {
            DBUtil2 e = new DBUtil2(databaseName, databaseConfigId);
            String sql = "select count(*) as locks from pg_stat_activity where waiting=\'t\' ";
            List list = e.queryForList(sql);
            value = "0";
            if(list.size() > 0) {
                Map temp = (Map)list.get(0);
                value = "" + temp.get("locks");
            }
        } catch (Exception var8) {
            System.out.println(var8.getMessage());
            value = "0";
        }

        return value;
    }

    public String queryDatabaseStatusForPostgreSQLDBSize(String databaseName, String databaseConfigId) throws Exception {
        DBUtil2 db = new DBUtil2(databaseName, databaseConfigId);
        String sql = " select pg_size_pretty(pg_database_size(\'" + databaseName + "\'))";
        List list = db.queryForList(sql);
        String value = "0";
        if(list.size() > 0) {
            Map temp = (Map)list.get(0);
            value = "" + temp.get("pg_size_pretty");
        }

        return value;
    }

    public Map<String, Object> queryDatabaseSQPSForPostgreSQL(String databaseName, String databaseConfigId) throws Exception {
        DBUtil2 db = new DBUtil2(databaseName, databaseConfigId);
        Object map = new HashMap();
        String sql = "  select sum(seq_tup_read) as select ,sum(n_tup_ins) as insert ,sum(n_tup_upd) as update ,sum(n_tup_del) as delete from pg_stat_user_tables ";
        List list = db.queryForList(sql);
        String value = "0";
        if(list.size() > 0) {
            map = (Map)list.get(0);
        }

        return (Map)map;
    }

    public Map<String, Object> queryDatabaseStatusForOracle(String databaseName, String databaseConfigId) throws Exception {
        HashMap map = new HashMap();
        String Variable_name = "";
        String Value = "";
        map.put("SESSIONS", this.queryDatabaseStatusForOracleSession(databaseName, databaseConfigId));
        map.put("HIT_RATIO", this.queryDatabaseStatusForOracleHitRatio(databaseName, databaseConfigId));
        map.put("HIT_RADIO", this.queryDatabaseStatusForOracleHitRadio(databaseName, databaseConfigId));
        map.put("LOG_BUFFER", this.queryDatabaseStatusForOracleLogBuffer(databaseName, databaseConfigId));
        map.put("LOCK", this.queryDatabaseStatusForOracleLock(databaseName, databaseConfigId));
        map.put("PHYRDS", this.queryDatabaseStatusForOraclePhyrds(databaseName, databaseConfigId));
        map.put("PHYWRTS", this.queryDatabaseStatusForOraclePhywrts(databaseName, databaseConfigId));
        return map;
    }

    public String queryDatabaseStatusForOracleSession(String databaseName, String databaseConfigId) throws Exception {
        DBUtil2 db = new DBUtil2(databaseName, databaseConfigId);
        String sql = "select count(*) as SESSIONS from  v$session";
        List list = db.queryForList(sql);
        String value = "0";
        if(list.size() > 0) {
            Map temp = (Map)list.get(0);
            value = "" + temp.get("SESSIONS");
        }

        return value;
    }

    public String queryDatabaseStatusForOracleHitRatio(String databaseName, String databaseConfigId) throws Exception {
        DBUtil2 db = new DBUtil2(databaseName, databaseConfigId);
        String sql = "select floor(( 1 - sum(decode(name, \'physical reads\', value, 0)) /(sum(decode(name, \'db block gets\', value, 0)) + sum(decode(name, \'consistent gets\', value, 0)))) *100)  HIT_RATIO from v$sysstat t where name in (\'physical reads\', \'db block gets\', \'consistent gets\')";
        List list = db.queryForList(sql);
        String value = "0";
        if(list.size() > 0) {
            Map temp = (Map)list.get(0);
            value = "" + temp.get("HIT_RATIO");
        }

        return value;
    }

    public String queryDatabaseStatusForOracleHitRadio(String databaseName, String databaseConfigId) throws Exception {
        DBUtil2 db = new DBUtil2(databaseName, databaseConfigId);
        String sql = "select  floor(sum(pinhits)/sum(pins)*100 ) AS HIT_RADIO from v$librarycache";
        List list = db.queryForList(sql);
        String value = "0";
        if(list.size() > 0) {
            Map temp = (Map)list.get(0);
            value = "" + temp.get("HIT_RADIO");
        }

        return value;
    }

    public String queryDatabaseStatusForOracleLogBuffer(String databaseName, String databaseConfigId) throws Exception {
        DBUtil2 db = new DBUtil2(databaseName, databaseConfigId);
        String sql = "select floor((select value  from v$sysstat where name in( \'redo buffer allocation retries\'))/ (select value  from v$sysstat where name in(\'redo entries\' ) )) as LOG_BUFFER from dual";
        List list = db.queryForList(sql);
        String value = "0";
        if(list.size() > 0) {
            Map temp = (Map)list.get(0);
            value = "" + temp.get("LOG_BUFFER");
        }

        return value;
    }

    public String queryDatabaseStatusForOracleLock(String databaseName, String databaseConfigId) throws Exception {
        DBUtil2 db = new DBUtil2(databaseName, databaseConfigId);
        String sql = "select count(*) as LOCKS from v$locked_object ";
        List list = db.queryForList(sql);
        String value = "0";
        if(list.size() > 0) {
            Map temp = (Map)list.get(0);
            value = "" + temp.get("LOCKS");
        }

        return value;
    }

    public String queryDatabaseStatusForOraclePhyrds(String databaseName, String databaseConfigId) throws Exception {
        DBUtil2 db = new DBUtil2(databaseName, databaseConfigId);
        String sql = "select sum(f.phyrds) PHYRDS from v$filestat f, dba_data_files df where f.file# = df.file_id ";
        List list = db.queryForList(sql);
        String value = "0";
        if(list.size() > 0) {
            Map temp = (Map)list.get(0);
            value = "" + temp.get("PHYRDS");
        }

        return value;
    }

    public String queryDatabaseStatusForOraclePhywrts(String databaseName, String databaseConfigId) throws Exception {
        DBUtil2 db = new DBUtil2(databaseName, databaseConfigId);
        String sql = "select sum(f.phywrts) PHYWRTS from v$filestat f, dba_data_files df where f.file# = df.file_id ";
        List list = db.queryForList(sql);
        String value = "0";
        if(list.size() > 0) {
            Map temp = (Map)list.get(0);
            value = "" + temp.get("PHYWRTS");
        }

        return value;
    }

    public List<Map<String, Object>> queryTableSpaceForOracle(String databaseName, String databaseConfigId) throws Exception {
        DBUtil2 db = new DBUtil2(databaseName, databaseConfigId);
        String sql = " SELECT a.tablespace_name TABLESPACE_NAME ,ROUND( free / (1024 * 1024 ),2) TABLESPACE_SIZE_FREE,ROUND( total / (1024 * 1024 ),2) TABLESPACE_SIZE,   ROUND( (total - free) / (1024 * 1024  ),2) TABLESPACE_SIZE_USED FROM (SELECT tablespace_name, SUM(bytes) free FROM dba_free_space GROUP BY tablespace_name) a, (SELECT tablespace_name, SUM(bytes) total FROM dba_data_files GROUP BY tablespace_name) b WHERE a.tablespace_name = b.tablespace_name  ";
        List list = db.queryForList(sql);
        return list;
    }

    public Map<String, Object> queryDatabaseStatusForMongoDB(String databaseName, String databaseConfigId) throws Exception {
        MongoDBUtil db = new MongoDBUtil(databaseConfigId);
        CommandResult stats = db.getMongoStatus(databaseName);
        CommandResult stats2 = db.getMongoStatus2(databaseName);
        db.close();
        if(stats == null) {
            System.out.println("取得MongoDB数据库状态为空1。");
        }

        if(stats2 == null) {
            System.out.println("取得MongoDB数据库状态为空2。");
            System.out.println("需要为MongoDB用户赋予root角色才能执行db.serverStatus()命令。");
        }

        HashMap map = new HashMap();
        map.put("collections", stats.get("collections"));
        map.put("dataSize", stats.get("dataSize"));
        map.put("indexes", stats.get("indexes"));
        Object tempConnections = stats2.get("connections");
        JSONObject jb = JSONObject.fromObject(tempConnections);
        map.put("connections", jb.get("current"));
        map.put("uptime", stats2.get("uptime"));
        Object tempOpcounters2 = stats2.get("opcounters");
        JSONObject jb2 = JSONObject.fromObject(tempOpcounters2);
        map.put("insert", jb2.get("insert"));
        map.put("query", jb2.get("query"));
        map.put("update", jb2.get("update"));
        map.put("delete", jb2.get("delete"));
        Object tempOpcounters3 = stats2.get("network");
        JSONObject jb3 = JSONObject.fromObject(tempOpcounters3);
        map.put("bytesIn", jb3.get("bytesIn"));
        map.put("bytesOut", jb3.get("bytesOut"));
        Object tempOpcounters4 = stats2.get("mem");
        JSONObject jb4 = JSONObject.fromObject(tempOpcounters4);
        map.put("resident", jb4.get("resident"));
        map.put("virtual", jb4.get("virtual"));
        Object tempOpcounters5 = stats2.get("globalLock");
        JSONObject jb5 = JSONObject.fromObject(tempOpcounters5);
        JSONObject jb6 = jb5.getJSONObject("currentQueue");
        map.put("globalLock_total", jb6.get("total"));
        map.put("globalLock_readers", jb6.get("readers"));
        map.put("globalLock_writers", jb6.get("writers"));
        return map;
    }

    public List<Map<String, Object>> monitorItemValue(String databaseName, String databaseConfigId) throws Exception {
        DBUtil2 db = new DBUtil2(databaseName, databaseConfigId);
        String sql = " show global status  ";
        ArrayList list2 = new ArrayList();
        List list = db.queryForList(sql);
        String Variable_name = "";
        String Value = "";

        for(int i = 0; i < list.size(); ++i) {
            Map tempMap = (Map)list.get(i);
            Variable_name = (String)tempMap.get("Variable_name");
            Value = (String)tempMap.get("Value");
            if(Variable_name.equals("Com_select")) {
                tempMap.put("descript", "select数量");
            } else if(Variable_name.equals("Com_update")) {
                tempMap.put("descript", "更新数量");
            } else if(Variable_name.equals("Open_tables")) {
                tempMap.put("descript", "当前打开的表的数量");
            } else if(Variable_name.equals("Open_files")) {
                tempMap.put("descript", "打开的文件的数目");
            } else if(Variable_name.equals("Opened_tables")) {
                tempMap.put("descript", "已经打开的表的数量");
            } else if(Variable_name.equals("Questions")) {
                tempMap.put("descript", "已经发送给服务器的查询的个数");
            } else {
                tempMap.put("descript", "");
            }

            list2.add(tempMap);
        }

        return list2;
    }

    public List<Map<String, Object>> monitorItemValueForOracle(String databaseName, String databaseConfigId) throws Exception {
        DBUtil2 db = new DBUtil2(databaseName, databaseConfigId);
        String sql = "  select NAME \"Variable_name\", VALUE \"Value\"  from v$sysstat ";
        ArrayList list2 = new ArrayList();
        List list = db.queryForList(sql);
        String Variable_name = "";
        String Value = "";

        for(int i = 0; i < list.size(); ++i) {
            Map tempMap = (Map)list.get(i);
            (new StringBuilder()).append(tempMap.get("Variable_name")).toString();
            (new StringBuilder()).append(tempMap.get("Value")).toString();
            list2.add(tempMap);
        }

        return list2;
    }

    public int saveNewTableForMongoDB(JSONArray insertArray, String databaseName, String tableName, String databaseConfigId) throws Exception {
        MongoDBUtil db = new MongoDBUtil(databaseConfigId);
        db.createCollection(databaseName, tableName);
        db.close();
        return 0;
    }

    public List<Map<String, Object>> viewTableMessForMongoDB(String databaseName, String tableName, String databaseConfigId) throws Exception {
        MongoDBUtil db = new MongoDBUtil(databaseConfigId);
        MongoCollection document = db.getCollection(databaseName, tableName);
        ArrayList listAllColumn = new ArrayList();
        HashMap tempMap = new HashMap();
        tempMap.put("propName", "表名");
        tempMap.put("propValue", tableName);
        listAllColumn.add(tempMap);
        HashMap tempMap4 = new HashMap();
        tempMap4.put("propName", "总记录数");
        tempMap4.put("propValue", Long.valueOf(document.count()));
        listAllColumn.add(tempMap4);
        db.close();
        return listAllColumn;
    }

    public Page<Map<String, Object>> configList(Page<Map<String, Object>> page) throws Exception {
        int pageNo = page.getPageNo();
        int pageSize = page.getPageSize();
        int limitFrom = (pageNo - 1) * pageSize;
        DBUtil du = new DBUtil();
        List list = du.getConfigList();
        int rowCount = list.size();
        page.setTotalCount((long)rowCount);
        page.setResult(list);
        return page;
    }

    public List<Map<String, Object>> getAllConfigList() {
        DBUtil du = new DBUtil();
        List list = du.getConfigList();
        return list;
    }

    public boolean deleteConfig(String[] ids) throws Exception {
        DBUtil db = new DBUtil();
        StringBuffer sb = new StringBuffer();

        for(int newStr = 0; newStr < ids.length; ++newStr) {
            sb = sb.append("\'" + ids[newStr] + "\',");
        }

        String var8 = sb.toString();
        String str3 = var8.substring(0, var8.length() - 1);
        String sql = "  delete  from  treesoft_config where id in (" + str3 + ")";
        boolean bl = db.do_update(sql);
        return bl;
    }

    public boolean authorize() throws Exception {
        DBUtil db = new DBUtil();
        String sql = " select id, computer, license,valid  from  treesoft_config   ";
        List list = db.executeQuery(sql);
        Map map = (Map)list.get(0);
        String computer = (String)map.get("computer");
        String license = (String)map.get("license");
        String valid = (String)map.get("valid");
        return true;
    }

    public List<Map<String, Object>> selectAllDataFromSQLForOracle(String databaseName, String databaseConfigId, String sql) throws Exception {
        DBUtil2 db = new DBUtil2(databaseName, databaseConfigId);
        List list = db.queryForList(sql);
        return list;
    }

    public List<Map<String, Object>> selectAllDataFromSQLForPostgreSQL(String databaseName, String databaseConfigId, String sql) throws Exception {
        DBUtil2 db = new DBUtil2(databaseName, databaseConfigId);
        List list = db.queryForList(sql);
        return list;
    }

    public List<Map<String, Object>> selectAllDataFromSQLForMSSQL(String databaseName, String databaseConfigId, String sql) throws Exception {
        DBUtil2 db = new DBUtil2(databaseName, databaseConfigId);
        List list = db.queryForList(sql);
        return list;
    }

    public List<Map<String, Object>> selectAllDataFromSQLForHive2(String databaseName, String databaseConfigId, String sql) throws Exception {
        DBUtil2 db = new DBUtil2(databaseName, databaseConfigId);
        List list = db.queryForList(sql);
        return list;
    }

    public List<Map<String, Object>> selectAllDataFromSQLForMongoDB(String databaseName, String databaseConfigId, String sql) throws Exception {
        String tableName = "";
        String queryStr = "";
        String[] str2 = sql.split("\\.");
        tableName = str2[1];
        queryStr = str2[2];
        queryStr = queryStr.replace("find(", "");
        queryStr = queryStr.substring(0, queryStr.lastIndexOf(")"));
        if(queryStr.equals("")) {
            queryStr = "{}";
        }

        JSONObject obj = JSONObject.fromObject(queryStr);
        MongoDBUtil db = new MongoDBUtil(databaseConfigId);
        MongoCollection dbCollection = db.getCollection(databaseName, tableName);
        SimpleDateFormat sdf2 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Document filter = new Document(obj);
        MongoCursor mongoCursor = db.find(dbCollection, filter);
        ArrayList list = new ArrayList();

        while(mongoCursor.hasNext()) {
            Document doc = (Document)mongoCursor.next();
            HashMap map3 = new HashMap();
            Iterator var17 = doc.entrySet().iterator();

            while(var17.hasNext()) {
                Entry entry = (Entry)var17.next();
                String key = (String)entry.getKey();
                Object param = entry.getValue();
                if(param instanceof String) {
                    map3.put(key, param);
                } else if(param instanceof Date) {
                    map3.put(key, sdf2.format(param));
                } else if(param instanceof ObjectId) {
                    map3.put(key, "ObjectId(\"" + param.toString() + "\")");
                } else if(param instanceof Document) {
                    map3.put(key, "{ " + ((Document)param).size() + " field }");
                } else if(param instanceof Object) {
                    map3.put(key, param);
                } else {
                    map3.put(key, param);
                }
            }

            list.add(map3);
        }

        db.close();
        return list;
    }

    public List<Map<String, Object>> selectAllDataFromSQLForMongoDBForExport(String databaseName, String databaseConfigId, String sql) throws Exception {
        String tableName = "";
        String queryStr = "";
        String[] str2 = sql.split("\\.");
        tableName = str2[1];
        queryStr = str2[2];
        queryStr = queryStr.replace("find(", "");
        queryStr = queryStr.substring(0, queryStr.lastIndexOf(")"));
        if(queryStr.equals("")) {
            queryStr = "{}";
        }

        JSONObject obj = JSONObject.fromObject(queryStr);
        MongoDBUtil db = new MongoDBUtil(databaseConfigId);
        MongoCollection dbCollection = db.getCollection(databaseName, tableName);
        SimpleDateFormat sdf2 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Document filter = new Document(obj);
        MongoCursor mongoCursor = db.find(dbCollection, filter);
        ArrayList list = new ArrayList();

        while(mongoCursor.hasNext()) {
            Document doc = (Document)mongoCursor.next();
            HashMap map3 = new HashMap();
            Iterator var17 = doc.entrySet().iterator();

            while(var17.hasNext()) {
                Entry entry = (Entry)var17.next();
                String key = (String)entry.getKey();
                Object param = entry.getValue();
                if(param instanceof String) {
                    map3.put(key, param);
                } else if(param instanceof Date) {
                    map3.put(key, sdf2.format(param));
                } else if(param instanceof ObjectId) {
                    map3.put(key, "ObjectId(\"" + param.toString() + "\")");
                } else if(param instanceof Document) {
                    map3.put(key, ((Document)param).toJson());
                } else if(param instanceof Object) {
                    map3.put(key, param);
                } else {
                    map3.put(key, param);
                }
            }

            list.add(map3);
        }

        db.close();
        return list;
    }

    public Page<Map<String, Object>> dataSynchronizeList(Page<Map<String, Object>> page) throws Exception {
        int pageNo = page.getPageNo();
        int pageSize = page.getPageSize();
        int limitFrom = (pageNo - 1) * pageSize;
        DBUtil db1 = new DBUtil();
        String sql2 = " select t1.id, t1.state , t1.name, t1.createDate,t1.updateDate ,t1.souceConfig_id as souceConfigId , t1.souceDataBase, t1.doSql ,t1.targetConfig_id as targetConfigId, t1.targetDataBase,t1.targetTable, t1.cron, t1.operation, t1.comments,t1. status , t2.name||\',\'||t2.ip||\':\'||t2.port as souceConfig , t3.ip||\':\'||t3.port as targetConfig from  treesoft_data_synchronize t1 left join treesoft_config t2 on t1.souceConfig_id = t2.id LEFT JOIN treesoft_config t3 on t1.targetConfig_id = t3.id ";
        int rowCount = db1.executeQueryForCount(sql2);
        sql2 = sql2 + "  limit " + limitFrom + "," + pageSize;
        List list = db1.executeQuery(sql2);
        page.setTotalCount((long)rowCount);
        page.setResult(list);
        return page;
    }

    public List<Map<String, Object>> getDataSynchronizeListById(String[] ids) throws Exception {
        DBUtil du = new DBUtil();
        List list = du.getDataSynchronizeListById(ids);
        return list;
    }

    public List<Map<String, Object>> getDataSynchronizeList2(String state) {
        ArrayList list = new ArrayList();

        try {
            DBUtil e = new DBUtil();
            List list1 = e.getDataSynchronizeList2(state);
            return list1;
        } catch (Exception var4) {
            System.out.println("error= " + var4.getMessage());
            var4.printStackTrace();
            return list;
        }
    }

    public boolean deleteDataSynchronize(String[] ids) throws Exception {
        DBUtil db = new DBUtil();
        StringBuffer sb = new StringBuffer();

        for(int newStr = 0; newStr < ids.length; ++newStr) {
            sb = sb.append("\'" + ids[newStr] + "\',");
        }

        String var8 = sb.toString();
        String str3 = var8.substring(0, var8.length() - 1);
        String sql = "  delete  from  treesoft_data_synchronize  where id in (" + str3 + ")";
        boolean bl = db.do_update(sql);
        return bl;
    }

    public boolean dataSynchronizeUpdate(DataSynchronize dataSynchronize) throws Exception {
        DBUtil db = new DBUtil();
        String id = dataSynchronize.getId();
        String sql = "";
        String status = dataSynchronize.getStatus();
        if(status == null || status.equals("")) {
            status = "0";
        }

        String doSql = dataSynchronize.getDoSql();
        doSql = doSql.replaceAll("\'", "\'\'");
        doSql = doSql.replaceAll(";", "");
        doSql = StringEscapeUtils.unescapeHtml4(doSql);
        dataSynchronize.setDoSql(doSql);
        if(!id.equals("")) {
            sql = " update treesoft_data_synchronize  set name=\'" + dataSynchronize.getName() + "\' ," + "souceConfig_id=\'" + dataSynchronize.getSouceConfigId() + "\' ," + "souceDataBase=\'" + dataSynchronize.getSouceDataBase() + "\', " + "doSql=\'" + dataSynchronize.getDoSql() + "\', " + "targetConfig_id=\'" + dataSynchronize.getTargetConfigId() + "\', " + "targetDataBase =\'" + dataSynchronize.getTargetDataBase() + "\', " + "targetTable=\'" + dataSynchronize.getTargetTable() + "\', " + "cron=\'" + dataSynchronize.getCron() + "\', " + "status=\'" + status + "\', " + "state=\'" + dataSynchronize.getState() + "\', " + "qualification=\'" + dataSynchronize.getQualification() + "\', " + "comments=\'" + dataSynchronize.getComments() + "\', " + "operation=\'" + dataSynchronize.getOperation() + "\'  where id=\'" + id + "\'";
        } else {
            sql = " insert into treesoft_data_synchronize ( name, createDate,updateDate ,souceConfig_id,souceDataBase, doSql ,targetConfig_id ,targetDataBase,targetTable, cron,operation,comments,status ,qualification ,state ) values ( \'" + dataSynchronize.getName() + "\',\'" + DateUtils.getDateTime() + "\',\'" + DateUtils.getDateTime() + "\',\'" + dataSynchronize.getSouceConfigId() + "\',\'" + dataSynchronize.getSouceDataBase() + "\',\'" + dataSynchronize.getDoSql() + "\',\'" + dataSynchronize.getTargetConfigId() + "\',\'" + dataSynchronize.getTargetDataBase() + "\',\'" + dataSynchronize.getTargetTable() + "\',\'" + dataSynchronize.getCron() + "\',\'" + dataSynchronize.getOperation() + "\',\'" + dataSynchronize.getComments() + "\',\'" + status + "\',\'" + dataSynchronize.getQualification() + "\',\'" + dataSynchronize.getState() + "\' ) ";
        }

        boolean bl = db.do_update(sql);
        return bl;
    }

    public boolean dataSynchronizeUpdateStatus(String dataSynchronizeId, String status) {
        DBUtil db = new DBUtil();
        boolean bl = true;

        try {
            String e = "update treesoft_data_synchronize set status=\'" + status + "\' " + " where id=\'" + dataSynchronizeId + "\'";
            bl = db.do_update2(e);
        } catch (Exception var6) {
            var6.printStackTrace();
        }

        return bl;
    }

    public Map<String, Object> getDataSynchronize(String id) {
        DBUtil db = new DBUtil();
        String sql = " select id, name, souceConfig_id as souceConfigId ,souceDataBase, doSql,targetConfig_id as targetConfigId, targetDataBase, targetTable,cron, operation,comments ,status ,state,qualification from  treesoft_data_synchronize where id=\'" + id + "\'";
        List list = db.executeQuery(sql);
        Map map = (Map)list.get(0);
        return map;
    }

    public Map<String, Object> getDataSynchronizeById2(String id) {
        DBUtil db = new DBUtil();
        String sql = " select id, name, souceConfig_id as souceConfigId,souceDataBase, doSql ,targetConfig_id as targetConfigId, targetDataBase,targetTable, cron,operation,comments,status,state ,qualification from  treesoft_data_synchronize where id=\'" + id + "\'";
        List list = db.executeQuery(sql);
        Map map = (Map)list.get(0);
        return map;
    }

    public boolean insertByDataListForMySQL(List<Map<String, Object>> dataList, String databaseName, String tableName, String databaseConfigId) throws Exception {
        DBUtil2 db = new DBUtil2(databaseName, databaseConfigId);
        boolean y = false;
        ArrayList sqlList = new ArrayList();
        String colums = " ";
        String values = " ";
        String tempValues = "";
        String insertSQL = "";
        Iterator var13 = dataList.iterator();

        while(var13.hasNext()) {
            Map map4 = (Map)var13.next();
            insertSQL = "INSERT INTO `" + tableName + "` ";
            colums = " ";
            values = "";
            Iterator var15 = map4.entrySet().iterator();

            while(true) {
                while(var15.hasNext()) {
                    Entry entry = (Entry)var15.next();
                    colums = colums + (String)entry.getKey() + ",";
                    if(entry.getValue() == null) {
                        values = values + "null,";
                    } else if(!(entry.getValue() instanceof java.sql.Date) && !(entry.getValue() instanceof Time) && !(entry.getValue() instanceof Timestamp)) {
                        if(!(entry.getValue() instanceof Integer) && !(entry.getValue() instanceof Float) && !(entry.getValue() instanceof Long) && !(entry.getValue() instanceof BigInteger) && !(entry.getValue() instanceof Double) && !(entry.getValue() instanceof BigDecimal)) {
                            if(entry.getValue() instanceof Boolean) {
                                values = values + entry.getValue() + ",";
                            } else if(entry.getValue() instanceof Byte) {
                                byte[] ss = (byte[])entry.getValue();
                                if(ss.length == 0) {
                                    values = values + "null,";
                                } else {
                                    values = values + "0x" + this.bytesToHexString(ss) + ",";
                                }
                            } else if(entry.getValue() instanceof ArrayList) {
                                values = values + "\'" + entry.getValue().toString() + "\',";
                            } else {
                                tempValues = (String)entry.getValue();
                                tempValues = tempValues.replace("\'", "\\\'");
                                tempValues = tempValues.replace("\r\n", "\\r\\n");
                                tempValues = tempValues.replace("\n\r", "\\n\\r");
                                tempValues = tempValues.replace("\r", "\\r");
                                tempValues = tempValues.replace("\n", "\\n");
                                values = values + "\'" + tempValues + "\',";
                            }
                        } else {
                            values = values + entry.getValue() + ",";
                        }
                    } else {
                        values = values + "\'" + entry.getValue() + "\',";
                    }
                }

                colums = colums.substring(0, colums.length() - 1);
                values = values.substring(0, values.length() - 1);
                insertSQL = insertSQL + " ( " + colums + ") VALUES (" + values + " ) ";
                sqlList.add(insertSQL);
                if(sqlList.size() > 2000) {
                    db.updateExecuteBatch(sqlList);
                    sqlList.clear();
                }
                break;
            }
        }

        if(sqlList.size() > 0) {
            db.updateExecuteBatch(sqlList);
        }

        return true;
    }

    public boolean insertByDataListForOracle(List<Map<String, Object>> dataList, String databaseName, String tableName, String databaseConfigId) throws Exception {
        DBUtil2 db = new DBUtil2(databaseName, databaseConfigId);
        boolean y = false;
        ArrayList sqlList = new ArrayList();
        String colums = " ";
        String values = " ";
        String tempValues = "";
        String insertSQL = "";
        Iterator var13 = dataList.iterator();

        while(var13.hasNext()) {
            Map map4 = (Map)var13.next();
            insertSQL = "INSERT INTO " + tableName + " ";
            colums = " ";
            values = "";
            Iterator var15 = map4.entrySet().iterator();

            while(true) {
                while(var15.hasNext()) {
                    Entry entry = (Entry)var15.next();
                    colums = colums + (String)entry.getKey() + ",";
                    if(entry.getValue() == null) {
                        values = values + "null,";
                    } else if(this.isDate("" + entry.getValue())) {
                        values = values + "to_date( \'" + entry.getValue() + "\',\'YYYY-MM-DD HH24:MI:SS\'),";
                    } else if(entry.getValue() instanceof Boolean) {
                        if(((Boolean)entry.getValue()).booleanValue()) {
                            values = values + "1,";
                        } else {
                            values = values + "0,";
                        }
                    } else if(!(entry.getValue() instanceof Integer) && !(entry.getValue() instanceof Float) && !(entry.getValue() instanceof Long) && !(entry.getValue() instanceof BigInteger) && !(entry.getValue() instanceof Double) && !(entry.getValue() instanceof BigDecimal)) {
                        if(entry.getValue() instanceof Byte) {
                            byte[] ss = (byte[])entry.getValue();
                            if(ss.length == 0) {
                                values = values + "null,";
                            } else {
                                values = values + "0x" + this.bytesToHexString(ss) + ",";
                            }
                        } else if(entry.getValue() instanceof ArrayList) {
                            values = values + "\'" + entry.getValue().toString() + "\',";
                        } else {
                            tempValues = (String)entry.getValue();
                            tempValues = tempValues.replace("\'", "\\\'");
                            tempValues = tempValues.replace("\r\n", "\\r\\n");
                            tempValues = tempValues.replace("\n\r", "\\n\\r");
                            tempValues = tempValues.replace("\r", "\\r");
                            tempValues = tempValues.replace("\n", "\\n");
                            values = values + "\'" + tempValues + "\',";
                        }
                    } else {
                        values = values + entry.getValue() + ",";
                    }
                }

                colums = colums.substring(0, colums.length() - 1);
                values = values.substring(0, values.length() - 1);
                insertSQL = insertSQL + " ( " + colums + ") VALUES (" + values + " ) ";
                sqlList.add(insertSQL);
                break;
            }
        }

        db.updateExecuteBatch(sqlList);
        return true;
    }

    public boolean isDate(String date) {
        Pattern pat = Pattern.compile("(\\d{4})(\\-)(\\d{2})(\\-)(\\d{2})(\\s+)(\\d{2})(\\:)(\\d{2})(\\:)(\\d{2})");
        Matcher mat = pat.matcher(date);
        boolean dateType = mat.matches();
        return dateType;
    }

    public boolean insertByDataListForMSSQL(List<Map<String, Object>> dataList, String databaseName, String tableName, String databaseConfigId) throws Exception {
        DBUtil2 db = new DBUtil2(databaseName, databaseConfigId);
        boolean y = false;
        ArrayList sqlList = new ArrayList();
        String colums = " ";
        String values = " ";
        String tempValues = "";
        String insertSQL = "";
        Iterator var13 = dataList.iterator();

        while(var13.hasNext()) {
            Map map4 = (Map)var13.next();
            insertSQL = "INSERT INTO " + tableName + " ";
            colums = " ";
            values = "";
            Iterator var15 = map4.entrySet().iterator();

            while(true) {
                while(var15.hasNext()) {
                    Entry entry = (Entry)var15.next();
                    colums = colums + (String)entry.getKey() + ",";
                    if(entry.getValue() == null) {
                        values = values + "null,";
                    } else if(!(entry.getValue() instanceof java.sql.Date) && !(entry.getValue() instanceof Time) && !(entry.getValue() instanceof Timestamp)) {
                        if(!(entry.getValue() instanceof Integer) && !(entry.getValue() instanceof Float) && !(entry.getValue() instanceof Long) && !(entry.getValue() instanceof BigInteger) && !(entry.getValue() instanceof Double) && !(entry.getValue() instanceof BigDecimal)) {
                            if(entry.getValue() instanceof Boolean) {
                                values = values + entry.getValue() + ",";
                            } else if(entry.getValue() instanceof Byte) {
                                byte[] ss = (byte[])entry.getValue();
                                if(ss.length == 0) {
                                    values = values + "null,";
                                } else {
                                    values = values + "0x" + this.bytesToHexString(ss) + ",";
                                }
                            } else if(entry.getValue() instanceof ArrayList) {
                                values = values + "\'" + entry.getValue().toString() + "\',";
                            } else {
                                tempValues = (String)entry.getValue();
                                tempValues = tempValues.replace("\'", "\\\'");
                                tempValues = tempValues.replace("\r\n", "\\r\\n");
                                tempValues = tempValues.replace("\n\r", "\\n\\r");
                                tempValues = tempValues.replace("\r", "\\r");
                                tempValues = tempValues.replace("\n", "\\n");
                                values = values + "\'" + tempValues + "\',";
                            }
                        } else {
                            values = values + entry.getValue() + ",";
                        }
                    } else {
                        values = values + "\'" + entry.getValue() + "\',";
                    }
                }

                colums = colums.substring(0, colums.length() - 1);
                values = values.substring(0, values.length() - 1);
                insertSQL = insertSQL + " ( " + colums + ") VALUES (" + values + " ) ";
                sqlList.add(insertSQL);
                break;
            }
        }

        db.updateExecuteBatch(sqlList);
        return true;
    }

    public boolean insertByDataListForHive2(List<Map<String, Object>> dataList, String databaseName, String tableName, String databaseConfigId) throws Exception {
        DBUtil2 db = new DBUtil2(databaseName, databaseConfigId);
        boolean y = false;
        ArrayList sqlList = new ArrayList();
        String colums = " ";
        String values = " ";
        String tempValues = "";
        String insertSQL = "";
        Iterator var13 = dataList.iterator();

        while(var13.hasNext()) {
            Map map4 = (Map)var13.next();
            insertSQL = "INSERT INTO " + tableName + " ";
            colums = " ";
            values = "";
            Iterator var15 = map4.entrySet().iterator();

            while(true) {
                while(var15.hasNext()) {
                    Entry entry = (Entry)var15.next();
                    colums = colums + (String)entry.getKey() + ",";
                    if(entry.getValue() == null) {
                        values = values + "null,";
                    } else if(!(entry.getValue() instanceof java.sql.Date) && !(entry.getValue() instanceof Time) && !(entry.getValue() instanceof Timestamp)) {
                        if(!(entry.getValue() instanceof Integer) && !(entry.getValue() instanceof Float) && !(entry.getValue() instanceof Long) && !(entry.getValue() instanceof BigInteger) && !(entry.getValue() instanceof Double) && !(entry.getValue() instanceof BigDecimal)) {
                            if(entry.getValue() instanceof Boolean) {
                                values = values + entry.getValue() + ",";
                            } else if(entry.getValue() instanceof Byte) {
                                byte[] ss = (byte[])entry.getValue();
                                if(ss.length == 0) {
                                    values = values + "null,";
                                } else {
                                    values = values + "0x" + this.bytesToHexString(ss) + ",";
                                }
                            } else if(entry.getValue() instanceof ArrayList) {
                                values = values + "\'" + entry.getValue().toString() + "\',";
                            } else {
                                tempValues = (String)entry.getValue();
                                tempValues = tempValues.replace("\'", "\\\'");
                                tempValues = tempValues.replace("\r\n", "\\r\\n");
                                tempValues = tempValues.replace("\n\r", "\\n\\r");
                                tempValues = tempValues.replace("\r", "\\r");
                                tempValues = tempValues.replace("\n", "\\n");
                                values = values + "\'" + tempValues + "\',";
                            }
                        } else {
                            values = values + entry.getValue() + ",";
                        }
                    } else {
                        values = values + "\'" + entry.getValue() + "\',";
                    }
                }

                colums = colums.substring(0, colums.length() - 1);
                values = values.substring(0, values.length() - 1);
                insertSQL = insertSQL + " ( " + colums + ") VALUES (" + values + " ) ";
                sqlList.add(insertSQL);
                break;
            }
        }

        db.updateExecuteBatch(sqlList);
        return true;
    }

    public boolean insertByDataListForMongoDB(List<Map<String, Object>> dataList, String databaseName, String tableName, String databaseConfigId) throws Exception {
        String insertSQL = "";
        ArrayList list = new ArrayList();
        int num = 0;

        for(int i = 0; i < dataList.size(); ++i) {
            Map map4 = (Map)dataList.get(i);
            JSONObject obj = JSONObject.fromObject(JSONArray.toJSONString(map4));
            Document doc = new Document(obj);
            list.add(doc);
            ++num;
            if(num > 2000) {
                System.out.println("新增数据到目标MongoDB数据库中，2000行提交一次 ");
                this.executeSqlNotResForMongoDBInsert(list, databaseName, tableName, databaseConfigId);
                list.clear();
                num = 0;
            }
        }

        if(list.size() > 0) {
            this.executeSqlNotResForMongoDBInsert(list, databaseName, tableName, databaseConfigId);
            list.clear();
        }

        return true;
    }

    public boolean updateByDataListForMySQL(List<Map<String, Object>> dataList, String databaseName, String tableName, String databaseConfigId, String qualification) throws Exception {
        DBUtil2 db = new DBUtil2(databaseName, databaseConfigId);
        boolean y = false;
        ArrayList sqlList = new ArrayList();
        String colums = " ";
        String tempValues = "";
        String updateSQL = "";
        String whereStr = "";
        qualification = qualification.replaceAll("，", ",");
        qualification = qualification.replaceAll(" ", "");
        String[] whereColumn = qualification.split(",");
        Iterator var15 = dataList.iterator();

        label77:
        while(var15.hasNext()) {
            Map map4 = (Map)var15.next();
            updateSQL = "update `" + tableName + "`  set ";
            Iterator var17 = map4.entrySet().iterator();

            while(true) {
                while(true) {
                    Entry ss;
                    do {
                        if(!var17.hasNext()) {
                            updateSQL = updateSQL.substring(0, updateSQL.length() - 1);
                            whereStr = " where 1=1 ";
                            String[] var19 = whereColumn;
                            int var22 = whereColumn.length;

                            for(int var21 = 0; var21 < var22; ++var21) {
                                String var20 = var19[var21];
                                whereStr = whereStr + " and " + var20 + "=\'" + map4.get(var20) + "\' ";
                            }

                            sqlList.add(updateSQL + whereStr);
                            continue label77;
                        }

                        ss = (Entry)var17.next();
                        colums = (String)ss.getKey();
                    } while(Arrays.asList(whereColumn).contains(colums));

                    updateSQL = updateSQL + (String)ss.getKey() + "=";
                    if(ss.getValue() == null) {
                        updateSQL = updateSQL + "null,";
                    } else if(!(ss.getValue() instanceof java.sql.Date) && !(ss.getValue() instanceof Time) && !(ss.getValue() instanceof Timestamp)) {
                        if(!(ss.getValue() instanceof Integer) && !(ss.getValue() instanceof Float) && !(ss.getValue() instanceof Long) && !(ss.getValue() instanceof BigInteger) && !(ss.getValue() instanceof Double) && !(ss.getValue() instanceof BigDecimal) && !(ss.getValue() instanceof Short)) {
                            if(ss.getValue() instanceof Boolean) {
                                updateSQL = updateSQL + ss.getValue() + ",";
                            } else if(ss.getValue() instanceof Byte) {
                                byte[] ss1 = (byte[])ss.getValue();
                                if(ss1.length == 0) {
                                    updateSQL = updateSQL + "null,";
                                } else {
                                    updateSQL = updateSQL + "0x" + this.bytesToHexString(ss1) + ",";
                                }
                            } else if(ss.getValue() instanceof ArrayList) {
                                updateSQL = updateSQL + "\'" + ss.getValue().toString() + "\',";
                            } else {
                                tempValues = (String)ss.getValue();
                                tempValues = tempValues.replace("\'", "\\\'");
                                tempValues = tempValues.replace("\r\n", "\\r\\n");
                                tempValues = tempValues.replace("\n\r", "\\n\\r");
                                tempValues = tempValues.replace("\r", "\\r");
                                tempValues = tempValues.replace("\n", "\\n");
                                updateSQL = updateSQL + "\'" + tempValues + "\',";
                            }
                        } else {
                            updateSQL = updateSQL + ss.getValue() + ",";
                        }
                    } else {
                        updateSQL = updateSQL + "\'" + ss.getValue() + "\',";
                    }
                }
            }
        }

        db.updateExecuteBatch(sqlList);
        return true;
    }

    public boolean updateByDataListForOracle(List<Map<String, Object>> dataList, String databaseName, String tableName, String databaseConfigId, String qualification) throws Exception {
        DBUtil2 db = new DBUtil2(databaseName, databaseConfigId);
        boolean y = false;
        ArrayList sqlList = new ArrayList();
        String colums = " ";
        String tempValues = "";
        String updateSQL = "";
        String whereStr = "";
        qualification = qualification.replaceAll("，", ",");
        qualification = qualification.replaceAll(" ", "");
        String[] whereColumn = qualification.split(",");
        Iterator var15 = dataList.iterator();

        label77:
        while(var15.hasNext()) {
            Map map4 = (Map)var15.next();
            updateSQL = "update " + tableName + "  set ";
            Iterator var17 = map4.entrySet().iterator();

            while(true) {
                while(true) {
                    Entry ss;
                    do {
                        if(!var17.hasNext()) {
                            updateSQL = updateSQL.substring(0, updateSQL.length() - 1);
                            whereStr = " where 1=1 ";
                            String[] var19 = whereColumn;
                            int var22 = whereColumn.length;

                            for(int var21 = 0; var21 < var22; ++var21) {
                                String var20 = var19[var21];
                                whereStr = whereStr + " and " + var20 + "=\'" + map4.get(var20) + "\' ";
                            }

                            sqlList.add(updateSQL + whereStr);
                            continue label77;
                        }

                        ss = (Entry)var17.next();
                        colums = (String)ss.getKey();
                    } while(Arrays.asList(whereColumn).contains(colums));

                    updateSQL = updateSQL + (String)ss.getKey() + "=";
                    if(ss.getValue() == null) {
                        updateSQL = updateSQL + "null,";
                    } else if(this.isDate("" + ss.getValue())) {
                        updateSQL = updateSQL + "to_date( \'" + ss.getValue() + "\',\'YYYY-MM-DD HH24:MI:SS\'),";
                    } else if(ss.getValue() instanceof Boolean) {
                        if(((Boolean)ss.getValue()).booleanValue()) {
                            updateSQL = updateSQL + "1,";
                        } else {
                            updateSQL = updateSQL + "0,";
                        }
                    } else if(!(ss.getValue() instanceof Integer) && !(ss.getValue() instanceof Float) && !(ss.getValue() instanceof Long) && !(ss.getValue() instanceof BigInteger) && !(ss.getValue() instanceof Double) && !(ss.getValue() instanceof BigDecimal) && !(ss.getValue() instanceof Short)) {
                        if(ss.getValue() instanceof Byte) {
                            byte[] ss1 = (byte[])ss.getValue();
                            if(ss1.length == 0) {
                                updateSQL = updateSQL + "null,";
                            } else {
                                updateSQL = updateSQL + "0x" + this.bytesToHexString(ss1) + ",";
                            }
                        } else if(ss.getValue() instanceof ArrayList) {
                            updateSQL = updateSQL + "\'" + ss.getValue().toString() + "\',";
                        } else {
                            tempValues = (String)ss.getValue();
                            tempValues = tempValues.replace("\'", "\\\'");
                            tempValues = tempValues.replace("\r\n", "\\r\\n");
                            tempValues = tempValues.replace("\n\r", "\\n\\r");
                            tempValues = tempValues.replace("\r", "\\r");
                            tempValues = tempValues.replace("\n", "\\n");
                            updateSQL = updateSQL + "\'" + tempValues + "\',";
                        }
                    } else {
                        updateSQL = updateSQL + ss.getValue() + ",";
                    }
                }
            }
        }

        db.updateExecuteBatch(sqlList);
        return true;
    }

    public boolean updateByDataListForMSSQL(List<Map<String, Object>> dataList, String databaseName, String tableName, String databaseConfigId, String qualification) throws Exception {
        DBUtil2 db = new DBUtil2(databaseName, databaseConfigId);
        boolean y = false;
        ArrayList sqlList = new ArrayList();
        String colums = " ";
        String tempValues = "";
        String updateSQL = "";
        String whereStr = "";
        qualification = qualification.replaceAll("，", ",");
        qualification = qualification.replaceAll(" ", "");
        String[] whereColumn = qualification.split(",");
        Iterator var15 = dataList.iterator();

        label77:
        while(var15.hasNext()) {
            Map map4 = (Map)var15.next();
            updateSQL = "update " + tableName + "  set ";
            Iterator var17 = map4.entrySet().iterator();

            while(true) {
                while(true) {
                    Entry ss;
                    do {
                        if(!var17.hasNext()) {
                            updateSQL = updateSQL.substring(0, updateSQL.length() - 1);
                            whereStr = " where 1=1 ";
                            String[] var19 = whereColumn;
                            int var22 = whereColumn.length;

                            for(int var21 = 0; var21 < var22; ++var21) {
                                String var20 = var19[var21];
                                whereStr = whereStr + " and " + var20 + "=\'" + map4.get(var20) + "\' ";
                            }

                            sqlList.add(updateSQL + whereStr);
                            continue label77;
                        }

                        ss = (Entry)var17.next();
                        colums = (String)ss.getKey();
                    } while(Arrays.asList(whereColumn).contains(colums));

                    updateSQL = updateSQL + (String)ss.getKey() + "=";
                    if(ss.getValue() == null) {
                        updateSQL = updateSQL + "null,";
                    } else if(!(ss.getValue() instanceof java.sql.Date) && !(ss.getValue() instanceof Time) && !(ss.getValue() instanceof Timestamp)) {
                        if(!(ss.getValue() instanceof Integer) && !(ss.getValue() instanceof Float) && !(ss.getValue() instanceof Long) && !(ss.getValue() instanceof BigInteger) && !(ss.getValue() instanceof Double) && !(ss.getValue() instanceof BigDecimal) && !(ss.getValue() instanceof Short)) {
                            if(ss.getValue() instanceof Boolean) {
                                updateSQL = updateSQL + ss.getValue() + ",";
                            } else if(ss.getValue() instanceof Byte) {
                                byte[] ss1 = (byte[])ss.getValue();
                                if(ss1.length == 0) {
                                    updateSQL = updateSQL + "null,";
                                } else {
                                    updateSQL = updateSQL + "0x" + this.bytesToHexString(ss1) + ",";
                                }
                            } else if(ss.getValue() instanceof ArrayList) {
                                updateSQL = updateSQL + "\'" + ss.getValue().toString() + "\',";
                            } else {
                                tempValues = (String)ss.getValue();
                                tempValues = tempValues.replace("\'", "\\\'");
                                tempValues = tempValues.replace("\r\n", "\\r\\n");
                                tempValues = tempValues.replace("\n\r", "\\n\\r");
                                tempValues = tempValues.replace("\r", "\\r");
                                tempValues = tempValues.replace("\n", "\\n");
                                updateSQL = updateSQL + "\'" + tempValues + "\',";
                            }
                        } else {
                            updateSQL = updateSQL + ss.getValue() + ",";
                        }
                    } else {
                        updateSQL = updateSQL + "\'" + ss.getValue() + "\',";
                    }
                }
            }
        }

        db.updateExecuteBatch(sqlList);
        return true;
    }

    public boolean updateByDataListForMongoDB(List<Map<String, Object>> dataList, String databaseName, String tableName, String databaseConfigId, String qualification) throws Exception {
        MongoDBUtil db = new MongoDBUtil(databaseConfigId);
        MongoCollection collection = db.getCollection(databaseName, tableName);
        String[] whereColumn = qualification.split(",");
        if(whereColumn.length == -1) {
            return false;
        } else {
            String id = "";
            Iterator var11 = dataList.iterator();

            while(var11.hasNext()) {
                Map map4 = (Map)var11.next();
                id = "" + map4.get(whereColumn[0]);
                map4.remove(whereColumn[0]);
                JSONObject obj = JSONObject.fromObject(map4);
                Document newdoc = new Document(obj);
                db.updateById(collection, id, newdoc);
            }

            db.close();
            return true;
        }
    }

    public boolean insertOrUpdateByDataListForMySQL(List<Map<String, Object>> dataList, String databaseName, String table, String databaseConfigId, String qualification) throws Exception {
        ArrayList insertDataList = new ArrayList();
        ArrayList updateDataList = new ArrayList();
        new DBUtil2(databaseName, databaseConfigId);
        qualification = qualification.replaceAll("，", ",");
        qualification = qualification.replaceAll(" ", "");
        String[] whereColumn = qualification.split(",");
        Iterator var11 = dataList.iterator();

        while(var11.hasNext()) {
            Map map4 = (Map)var11.next();
            String sql = "select * from " + table + " where 1=1 ";
            String[] var16 = whereColumn;
            int var15 = whereColumn.length;

            for(int var14 = 0; var14 < var15; ++var14) {
                String bl = var16[var14];
                sql = sql + " and " + bl + "=\'" + map4.get(bl) + "\' ";
            }

            boolean var17 = DBUtil2.executeQuery(sql);
            if(var17) {
                updateDataList.add(map4);
            } else {
                insertDataList.add(map4);
            }
        }

        if(insertDataList.size() > 0) {
            this.insertByDataListForMySQL(insertDataList, databaseName, table, databaseConfigId);
        }

        if(updateDataList.size() > 0) {
            this.updateByDataListForMySQL(updateDataList, databaseName, table, databaseConfigId, qualification);
        }

        return true;
    }

    public boolean insertOrUpdateByDataListForOracle(List<Map<String, Object>> dataList, String databaseName, String table, String databaseConfigId, String qualification) throws Exception {
        ArrayList insertDataList = new ArrayList();
        ArrayList updateDataList = new ArrayList();
        new DBUtil2(databaseName, databaseConfigId);
        qualification = qualification.replaceAll("，", ",");
        qualification = qualification.replaceAll(" ", "");
        String[] whereColumn = qualification.split(",");
        Iterator var11 = dataList.iterator();

        while(var11.hasNext()) {
            Map map4 = (Map)var11.next();
            String sql = "select * from " + table + " where 1=1 ";
            String[] var16 = whereColumn;
            int var15 = whereColumn.length;

            for(int var14 = 0; var14 < var15; ++var14) {
                String bl = var16[var14];
                sql = sql + " and " + bl + "=\'" + map4.get(bl) + "\' ";
            }

            boolean var17 = DBUtil2.executeQuery(sql);
            if(var17) {
                updateDataList.add(map4);
            } else {
                insertDataList.add(map4);
            }
        }

        if(insertDataList.size() > 0) {
            this.insertByDataListForOracle(insertDataList, databaseName, table, databaseConfigId);
        }

        if(updateDataList.size() > 0) {
            this.updateByDataListForOracle(updateDataList, databaseName, table, databaseConfigId, qualification);
        }

        return true;
    }

    public boolean insertOrUpdateByDataListForMSSQL(List<Map<String, Object>> dataList, String databaseName, String table, String databaseConfigId, String qualification) throws Exception {
        ArrayList insertDataList = new ArrayList();
        ArrayList updateDataList = new ArrayList();
        new DBUtil2(databaseName, databaseConfigId);
        qualification = qualification.replaceAll("，", ",");
        qualification = qualification.replaceAll(" ", "");
        String[] whereColumn = qualification.split(",");
        Iterator var11 = dataList.iterator();

        while(var11.hasNext()) {
            Map map4 = (Map)var11.next();
            String sql = "select * from " + table + " where 1=1 ";
            String[] var16 = whereColumn;
            int var15 = whereColumn.length;

            for(int var14 = 0; var14 < var15; ++var14) {
                String bl = var16[var14];
                sql = sql + " and " + bl + "=\'" + map4.get(bl) + "\' ";
            }

            boolean var17 = DBUtil2.executeQuery(sql);
            if(var17) {
                updateDataList.add(map4);
            } else {
                insertDataList.add(map4);
            }
        }

        if(insertDataList.size() > 0) {
            this.insertByDataListForMSSQL(insertDataList, databaseName, table, databaseConfigId);
        }

        if(updateDataList.size() > 0) {
            this.updateByDataListForMSSQL(updateDataList, databaseName, table, databaseConfigId, qualification);
        }

        return true;
    }

    public boolean insertOrUpdateByDataListForMongoDB(List<Map<String, Object>> dataList, String databaseName, String table, String databaseConfigId, String qualification) throws Exception {
        MongoDBUtil db = new MongoDBUtil(databaseConfigId);
        MongoCollection collection = db.getCollection(databaseName, table);
        ArrayList insertDataList = new ArrayList();
        ArrayList updateDataList = new ArrayList();
        String[] whereColumn = qualification.split(",");
        Iterator var12 = dataList.iterator();

        while(var12.hasNext()) {
            Map map4 = (Map)var12.next();
            String _id = whereColumn[0];
            Document doc = db.findById(collection, _id);
            if(doc.isEmpty()) {
                insertDataList.add(map4);
            } else {
                updateDataList.add(map4);
            }
        }

        if(insertDataList.size() > 0) {
            this.insertByDataListForMongoDB(insertDataList, databaseName, table, databaseConfigId);
        }

        if(updateDataList.size() > 0) {
            this.updateByDataListForMongoDB(updateDataList, databaseName, table, databaseConfigId, qualification);
        }

        return true;
    }

    public boolean deleteByDataListForMySQL(List<Map<String, Object>> dataList, String databaseName, String table, String databaseConfigId, String qualification) throws Exception {
        new DBUtil2(databaseName, databaseConfigId);
        qualification = qualification.replaceAll("，", ",");
        qualification = qualification.replaceAll(" ", "");
        String[] whereColumn = qualification.split(",");
        Iterator var9 = dataList.iterator();

        while(var9.hasNext()) {
            Map map4 = (Map)var9.next();
            String sql = "delete from " + table + " where 1=1 ";
            String[] var14 = whereColumn;
            int var13 = whereColumn.length;

            for(int var12 = 0; var12 < var13; ++var12) {
                String ss = var14[var12];
                sql = sql + " and " + ss + "=\'" + map4.get(ss) + "\' ";
            }

            System.out.println(sql);
            DBUtil2.setupdateData(sql);
        }

        return true;
    }

    public boolean deleteByDataListForOracle(List<Map<String, Object>> dataList, String databaseName, String table, String databaseConfigId, String qualification) throws Exception {
        new DBUtil2(databaseName, databaseConfigId);
        qualification = qualification.replaceAll("，", ",");
        qualification = qualification.replaceAll(" ", "");
        String[] whereColumn = qualification.split(",");
        Iterator var9 = dataList.iterator();

        while(var9.hasNext()) {
            Map map4 = (Map)var9.next();
            String sql = "delete from " + table + " where 1=1 ";
            String[] var14 = whereColumn;
            int var13 = whereColumn.length;

            for(int var12 = 0; var12 < var13; ++var12) {
                String ss = var14[var12];
                sql = sql + " and " + ss + "=\'" + map4.get(ss) + "\' ";
            }

            DBUtil2.setupdateData(sql);
        }

        return true;
    }

    public boolean deleteByDataListForMSSQL(List<Map<String, Object>> dataList, String databaseName, String table, String databaseConfigId, String qualification) throws Exception {
        new DBUtil2(databaseName, databaseConfigId);
        qualification = qualification.replaceAll("，", ",");
        qualification = qualification.replaceAll(" ", "");
        String[] whereColumn = qualification.split(",");
        Iterator var9 = dataList.iterator();

        while(var9.hasNext()) {
            Map map4 = (Map)var9.next();
            String sql = "delete from " + table + " where 1=1 ";
            String[] var14 = whereColumn;
            int var13 = whereColumn.length;

            for(int var12 = 0; var12 < var13; ++var12) {
                String ss = var14[var12];
                sql = sql + " and " + ss + "=\'" + map4.get(ss) + "\' ";
            }

            DBUtil2.setupdateData(sql);
        }

        return true;
    }

    public boolean deleteByDataListForMongoDB(List<Map<String, Object>> dataList, String databaseName, String table, String databaseConfigId, String qualification) throws Exception {
        MongoDBUtil db = new MongoDBUtil(databaseConfigId);
        MongoCollection collection = db.getCollection(databaseName, table);
        String[] whereColumn = qualification.split(",");
        Iterator var10 = dataList.iterator();

        while(var10.hasNext()) {
            Map map4 = (Map)var10.next();
            String _id = whereColumn[0];
            db.findById(collection, _id);
            db.deleteById(collection, _id);
        }

        return true;
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

    public boolean dataSynchronizeLogSave(String status, String comments, String dataSynchronizeId) {
        DBUtil db = new DBUtil();
        boolean bl = true;
        String comments2 = comments.replaceAll("\'", "\'\'");

        try {
            String e = " insert into treesoft_data_synchronize_log ( createDate, status ,comments, data_synchronize_id ) values ( \'" + DateUtils.getDateTime() + "\',\'" + status + "\',\'" + comments2 + "\',\'" + dataSynchronizeId + "\')";
            bl = db.do_update2(e);
        } catch (Exception var8) {
            System.out.println(var8.getMessage());
            var8.printStackTrace();
        }

        return bl;
    }

    public Page<Map<String, Object>> dataSynchronizeLogList(Page<Map<String, Object>> page, String dataSynchronizeId) throws Exception {
        int pageNo = page.getPageNo();
        int pageSize = page.getPageSize();
        int limitFrom = (pageNo - 1) * pageSize;
        DBUtil db1 = new DBUtil();
        String sql = " select id, createDate, status ,comments  from  treesoft_data_synchronize_log where data_synchronize_id =\'" + dataSynchronizeId + "\' order by createdate desc ";
        int rowCount = db1.executeQueryForCount(sql);
        sql = sql + "  limit " + limitFrom + "," + pageSize;
        List list = db1.executeQuery(sql);
        page.setTotalCount((long)rowCount);
        page.setResult(list);
        return page;
    }

    public boolean deleteDataSynchronizeLog(String[] ids) throws Exception {
        DBUtil db = new DBUtil();
        StringBuffer sb = new StringBuffer();

        for(int newStr = 0; newStr < ids.length; ++newStr) {
            sb = sb.append("\'" + ids[newStr] + "\',");
        }

        String var8 = sb.toString();
        String str3 = var8.substring(0, var8.length() - 1);
        String sql = "  delete  from  treesoft_data_synchronize_log  where id in (" + str3 + ")";
        boolean bl = db.do_update(sql);
        return bl;
    }

    public boolean deleteDataSynchronizeLogByDS(String id) throws Exception {
        DBUtil db = new DBUtil();
        String sql = "  delete  from  treesoft_data_synchronize_log  where data_synchronize_id =\'" + id + "\'";
        boolean bl = db.do_update(sql);
        return bl;
    }

    public boolean isTheConfigUsed(String[] ids) throws Exception {
        DBUtil db1 = new DBUtil();
        StringBuffer sb = new StringBuffer();

        for(int newStr = 0; newStr < ids.length; ++newStr) {
            sb = sb.append("\'" + ids[newStr] + "\',");
        }

        String var7 = sb.toString();
        var7 = var7.substring(0, var7.length() - 1);
        String sql = " select souceConfig_id , targetConfig_id  from  treesoft_data_synchronize where souceConfig_id  in (" + var7 + ") or targetConfig_id  in (" + var7 + ")  ";
        List list = db1.executeQuery(sql);
        return list.size() > 0;
    }

    public int executeQueryForCount(String sql, String dbName, String databaseConfigId) {
        new DBUtil2(dbName, databaseConfigId);
        int rowCount = DBUtil2.executeQueryForCount("select count(*) from (" + sql + ") as temp1 ");
        return rowCount;
    }

    public boolean saveLog(String sql, String username, String ip) throws Exception {
        DBUtil db = new DBUtil();
        sql = sql.replace("\'", "\'\'");
        String sqls = "insert into treesoft_log( createdate,operator,username ,log, ip  ) values ( \'" + DateUtils.getDateTime() + "\'," + "\'operator\'," + "\'" + username + "\'," + "\'" + sql + "\'," + "\'" + ip + "\' )";
        boolean bl = db.do_update(sqls);
        return bl;
    }
}
