//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package org.springframework.base.system.service;

import com.alibaba.fastjson.JSONArray;
import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import org.springframework.base.common.persistence.Page;
import org.springframework.base.common.utils.DateUtils;
import org.springframework.base.system.dao.PermissionDao;
import org.springframework.base.system.entity.Config;
import org.springframework.base.system.entity.DataSynchronize;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(
        readOnly = true
)
public class PermissionService {
    @Autowired
    private PermissionDao permissionDao;

    public PermissionService() {
    }

    public List<Map<String, Object>> getAllDataBase(String databaseConfigId) throws Exception {
        return this.permissionDao.getAllDataBase(databaseConfigId);
    }

    public List<Map<String, Object>> getConfigAllDataBase() throws Exception {
        return this.permissionDao.getConfigAllDataBase();
    }

    public List<Map<String, Object>> getAllDataBaseById(String datascope) throws Exception {
        return this.permissionDao.getAllDataBaseById(datascope);
    }

    public List<Map<String, Object>> getAllTables(String dbName, String databaseConfigId) throws Exception {
        return this.permissionDao.getAllTables(dbName, databaseConfigId);
    }

    public List<Map<String, Object>> getAllViews(String dbName, String databaseConfigId) throws Exception {
        return this.permissionDao.getAllViews(dbName, databaseConfigId);
    }

    public List<Map<String, Object>> getAllFuntion(String dbName, String databaseConfigId) throws Exception {
        return this.permissionDao.getAllFuntion(dbName, databaseConfigId);
    }

    public List<Map<String, Object>> getTableColumns(String dbName, String tableName, String databaseConfigId) throws Exception {
        return this.permissionDao.getTableColumns(dbName, tableName, databaseConfigId);
    }

    public Page<Map<String, Object>> getData(Page<Map<String, Object>> page, String tableName, String dbName, String databaseConfigId) throws Exception {
        return this.permissionDao.getData(page, tableName, dbName, databaseConfigId);
    }

    public Page<Map<String, Object>> executeSql(Page<Map<String, Object>> page, String sql, String dbName, String databaseConfigId) throws Exception {
        return this.permissionDao.executeSql(page, sql, dbName, databaseConfigId);
    }

    public List<Map<String, Object>> executeSqlForColumns(String sql, String dbName, String databaseConfigId) throws Exception {
        return this.permissionDao.executeSqlForColumns(sql, dbName, databaseConfigId);
    }

    public List<Map<String, Object>> selectAllDataFromSQLForMysql(String databaseName, String databaseConfigId, String sql) throws Exception {
        return this.permissionDao.selectAllDataFromSQLForMysql(databaseName, databaseConfigId, sql);
    }

    public List<Map<String, Object>> selectAllDataFromSQLForOracle(String databaseName, String databaseConfigId, String sql) throws Exception {
        return this.permissionDao.selectAllDataFromSQLForOracle(databaseName, databaseConfigId, sql);
    }

    public List<Map<String, Object>> selectAllDataFromSQLForPostgreSQL(String databaseName, String databaseConfigId, String sql) throws Exception {
        return this.permissionDao.selectAllDataFromSQLForPostgreSQL(databaseName, databaseConfigId, sql);
    }

    public List<Map<String, Object>> selectAllDataFromSQLForMSSQL(String databaseName, String databaseConfigId, String sql) throws Exception {
        return this.permissionDao.selectAllDataFromSQLForMSSQL(databaseName, databaseConfigId, sql);
    }

    public List<Map<String, Object>> selectAllDataFromSQLForHive2(String databaseName, String databaseConfigId, String sql) throws Exception {
        return this.permissionDao.selectAllDataFromSQLForHive2(databaseName, databaseConfigId, sql);
    }

    public List<Map<String, Object>> selectAllDataFromSQLForMongoDB(String databaseName, String databaseConfigId, String sql) throws Exception {
        return this.permissionDao.selectAllDataFromSQLForMongoDB(databaseName, databaseConfigId, sql);
    }

    public List<Map<String, Object>> selectAllDataFromSQLForMongoDBForExport(String databaseName, String databaseConfigId, String sql) throws Exception {
        return this.permissionDao.selectAllDataFromSQLForMongoDBForExport(databaseName, databaseConfigId, sql);
    }

    public boolean saveSearchHistory(String name, String sql, String dbName, String userId) {
        return this.permissionDao.saveSearchHistory(name, sql, dbName, userId);
    }

    public boolean updateSearchHistory(String id, String name, String sql, String dbName) {
        return this.permissionDao.updateSearchHistory(id, name, sql, dbName);
    }

    public boolean deleteSearchHistory(String id) {
        return this.permissionDao.deleteSearchHistory(id);
    }

    public List<Map<String, Object>> selectSearchHistory() {
        return this.permissionDao.selectSearchHistory();
    }

    public boolean configUpdate(Config config) throws Exception {
        return this.permissionDao.configUpdate(config);
    }

    public List<Map<String, Object>> selectUserByName(String userName) {
        List list = this.permissionDao.selectUserByName(userName);
        return list;
    }

    public String changePassUpdate(String userId, String newPass) throws Exception {
        this.permissionDao.updateUserPass(userId, newPass);
        return "success";
    }

    public int executeSqlNotRes(String sql, String dbName, String databaseConfigId) throws Exception {
        return this.permissionDao.executeSqlNotRes(sql, dbName, databaseConfigId);
    }

    public int deleteRows(String databaseName, String tableName, String primary_key, String[] ids, String databaseConfigId) throws Exception {
        return this.permissionDao.deleteRows(databaseName, tableName, primary_key, ids, databaseConfigId);
    }

    public int deleteRowsNew(String databaseName, String tableName, String primary_key, List<String> condition, String databaseConfigId) throws Exception {
        return this.permissionDao.deleteRowsNew(databaseName, tableName, primary_key, condition, databaseConfigId);
    }

    public int saveRows(Map map, String databaseName, String tableName, String databaseConfigId) throws Exception {
        return this.permissionDao.saveRows(map, databaseName, tableName, databaseConfigId);
    }

    public List<Map<String, Object>> getOneRowById(String databaseName, String tableName, String id, String idValues, String databaseConfigId) {
        return this.permissionDao.getOneRowById(databaseName, tableName, id, idValues, databaseConfigId);
    }

    public int updateRows(Map map, String databaseName, String tableName, String id, String idValues, String databaseConfigId) throws Exception {
        return this.permissionDao.updateRows(map, databaseName, tableName, id, idValues, databaseConfigId);
    }

    public int updateRowsNew(String databaseName, String tableName, List<String> strList, String databaseConfigId) throws Exception {
        String sql = "";
        Iterator var7 = strList.iterator();

        while(var7.hasNext()) {
            String str1 = (String)var7.next();
            if(str1 == null || "".equals(str1)) {
                throw new Exception("数据不完整,保存失败!");
            }

            sql = " update  " + databaseName + "." + tableName + str1;
            this.permissionDao.executeSqlNotRes(sql, databaseName, databaseConfigId);
        }

        return 0;
    }

    public String getViewSql(String databaseName, String tableName, String databaseConfigId) throws Exception {
        return this.permissionDao.getViewSql(databaseName, tableName, databaseConfigId);
    }

    public List<Map<String, Object>> getTableColumns2(String databaseName, String tableName, String databaseConfigId) throws Exception {
        return this.permissionDao.getTableColumns2(databaseName, tableName, databaseConfigId);
    }

    public List<Map<String, Object>> getTableColumns3(String databaseName, String tableName, String databaseConfigId) throws Exception {
        return this.permissionDao.getTableColumns3(databaseName, tableName, databaseConfigId);
    }

    public String getPrimaryKeys(String databaseName, String tableName, String databaseConfigId) {
        return this.permissionDao.getPrimaryKeys(databaseName, tableName, databaseConfigId);
    }

    public boolean testConn(String databaseType, String databaseName, String ip, String port, String user, String pass) {
        return this.permissionDao.testConn(databaseType, databaseName, ip, port, user, pass);
    }

    public List<Map<String, Object>> selectSqlStudy() {
        return this.permissionDao.selectSqlStudy();
    }

    public int saveDesginColumn(Map map, String databaseName, String tableName, String databaseConfigId) throws Exception {
        return this.permissionDao.saveDesginColumn(map, databaseName, tableName, databaseConfigId);
    }

    public int deleteTableColumn(String databaseName, String tableName, String[] ids, String databaseConfigId) throws Exception {
        return this.permissionDao.deleteTableColumn(databaseName, tableName, ids, databaseConfigId);
    }

    public int updateTableColumn(String updated, String databaseName, String tableName, String databaseConfigId) throws Exception {
        if(updated != null) {
            JSONArray updateArray = JSONArray.parseArray(updated);

            for(int i = 0; i < updateArray.size(); ++i) {
                Map map1 = (Map)updateArray.get(i);
                HashMap maps = new HashMap();
                Iterator var10 = map1.keySet().iterator();

                String idValues;
                while(var10.hasNext()) {
                    idValues = (String)var10.next();
                    maps.put(idValues, map1.get(idValues));
                }

                idValues = "" + maps.get("TREESOFTPRIMARYKEY");
                this.permissionDao.updateTableColumn(maps, databaseName, tableName, "column_name", idValues, databaseConfigId);
            }
        }

        return 0;
    }

    public int savePrimaryKey(String databaseName, String tableName, String column_name, String column_key, String databaseConfigId) throws Exception {
        return this.permissionDao.savePrimaryKey(databaseName, tableName, column_name, column_key, databaseConfigId);
    }

    public int updateTableNullAble(String databaseName, String tableName, String column_name, String is_nullable, String databaseConfigId) throws Exception {
        return this.permissionDao.updateTableNullAble(databaseName, tableName, column_name, is_nullable, databaseConfigId);
    }

    public int upDownColumn(String databaseName, String tableName, String column_name, String column_name2, String databaseConfigId) throws Exception {
        return this.permissionDao.upDownColumn(databaseName, tableName, column_name, column_name2, databaseConfigId);
    }

    public List<Map<String, Object>> getAllDataBaseForOracle(String databaseConfigId) throws Exception {
        return this.permissionDao.getAllDataBaseForOracle(databaseConfigId);
    }

    public List<Map<String, Object>> getAllTablesForOracle(String dbName, String databaseConfigId) throws Exception {
        return this.permissionDao.getAllTablesForOracle(dbName, databaseConfigId);
    }

    public List<Map<String, Object>> getTableColumns3ForOracle(String databaseName, String tableName, String databaseConfigId) throws Exception {
        return this.permissionDao.getTableColumns3ForOracle(databaseName, tableName, databaseConfigId);
    }

    public String getViewSqlForOracle(String databaseName, String tableName, String databaseConfigId) throws Exception {
        return this.permissionDao.getViewSqlForOracle(databaseName, tableName, databaseConfigId);
    }

    public List<Map<String, Object>> getAllViewsForOracle(String dbName, String databaseConfigId) throws Exception {
        return this.permissionDao.getAllViewsForOracle(dbName, databaseConfigId);
    }

    public List<Map<String, Object>> getAllFuntionForOracle(String dbName, String databaseConfigId) throws Exception {
        return this.permissionDao.getAllFuntionForOracle(dbName, databaseConfigId);
    }

    public Page<Map<String, Object>> getDataForOracle(Page<Map<String, Object>> page, String tableName, String dbName, String databaseConfigId) throws Exception {
        return this.permissionDao.getDataForOracle(page, tableName, dbName, databaseConfigId);
    }

    public Page<Map<String, Object>> executeSqlHaveResForOracle(Page<Map<String, Object>> page, String sql, String dbName, String databaseConfigId) throws Exception {
        return this.permissionDao.executeSqlHaveResForOracle(page, sql, dbName, databaseConfigId);
    }

    public int updateRowsNewForOracle(String databaseName, String tableName, List<String> strList, String databaseConfigId) throws Exception {
        String sql = "";
        Iterator var7 = strList.iterator();

        while(var7.hasNext()) {
            String str1 = (String)var7.next();
            if(str1 == null || "".equals(str1)) {
                throw new Exception("数据不完整,保存失败!");
            }

            sql = " update  " + tableName + str1;
            System.out.println(DateUtils.getDateTime() + " update语句 =" + sql);
            this.permissionDao.executeSqlNotRes(sql, databaseName, databaseConfigId);
        }

        return 0;
    }

    public int updateTableNullAbleForOracle(String databaseName, String tableName, String column_name, String is_nullable, String databaseConfigId) throws Exception {
        return this.permissionDao.updateTableNullAbleForOracle(databaseName, tableName, column_name, is_nullable, databaseConfigId);
    }

    public int savePrimaryKeyForOracle(String databaseName, String tableName, String column_name, String column_key, String databaseConfigId) throws Exception {
        return this.permissionDao.savePrimaryKeyForOracle(databaseName, tableName, column_name, column_key, databaseConfigId);
    }

    public int saveDesginColumnForOracle(Map map, String databaseName, String tableName, String databaseConfigId) throws Exception {
        return this.permissionDao.saveDesginColumnForOracle(map, databaseName, tableName, databaseConfigId);
    }

    @Transactional
    public int updateTableColumnForOracle(String updated, String databaseName, String tableName, String databaseConfigId) throws Exception {
        if(updated != null) {
            JSONArray updateArray = JSONArray.parseArray(updated);

            for(int i = 0; i < updateArray.size(); ++i) {
                Map map1 = (Map)updateArray.get(i);
                HashMap maps = new HashMap();
                Iterator var10 = map1.keySet().iterator();

                String idValues;
                while(var10.hasNext()) {
                    idValues = (String)var10.next();
                    maps.put(idValues, map1.get(idValues));
                }

                idValues = "" + maps.get("TREESOFTPRIMARYKEY");
                this.permissionDao.updateTableColumnForOracle(maps, databaseName, tableName, "column_name", idValues, databaseConfigId);
            }
        }

        return 0;
    }

    public int deleteTableColumnForOracle(String databaseName, String tableName, String[] ids, String databaseConfigId) throws Exception {
        return this.permissionDao.deleteTableColumnForOracle(databaseName, tableName, ids, databaseConfigId);
    }

    public int saveRowsForOracle(Map map, String databaseName, String tableName, String databaseConfigId) throws Exception {
        return this.permissionDao.saveRowsForOracle(map, databaseName, tableName, databaseConfigId);
    }

    public String selectColumnTypeForMySql(String databaseName, String tableName, String column, String databaseConfigId) throws Exception {
        return this.permissionDao.selectOneColumnType(databaseName, tableName, column, databaseConfigId);
    }

    public String selectColumnTypeForOracle(String databaseName, String tableName, String column, String databaseConfigId) throws Exception {
        return this.permissionDao.selectColumnTypeForOracle(databaseName, tableName, column, databaseConfigId);
    }

    public String selectColumnTypeForPostgreSQL(String databaseName, String tableName, String column, String databaseConfigId) throws Exception {
        return this.permissionDao.selectColumnTypeForPostgreSQL(databaseName, tableName, column, databaseConfigId);
    }

    public String selectColumnTypeForMSSQL(String databaseName, String tableName, String column, String databaseConfigId) throws Exception {
        return this.permissionDao.selectOneColumnTypeForMSSQL(databaseName, tableName, column, databaseConfigId);
    }

    public int deleteRowsNewForOracle(String databaseName, String tableName, String primary_key, List<String> condition, String databaseConfigId) throws Exception {
        return this.permissionDao.deleteRowsNewForOracle(databaseName, tableName, primary_key, condition, databaseConfigId);
    }

    public List<Map<String, Object>> getAllDataBaseForPostgreSQL(String databaseConfigId) throws Exception {
        return this.permissionDao.getAllDataBaseForPostgreSQL(databaseConfigId);
    }

    public List<Map<String, Object>> getAllTablesForPostgreSQL(String dbName, String databaseConfigId) throws Exception {
        return this.permissionDao.getAllTablesForPostgreSQL(dbName, databaseConfigId);
    }

    public List<Map<String, Object>> getTableColumns3ForPostgreSQL(String databaseName, String tableName, String databaseConfigId) throws Exception {
        return this.permissionDao.getTableColumns3ForPostgreSQL(databaseName, tableName, databaseConfigId);
    }

    public List<Map<String, Object>> getAllViewsForPostgreSQL(String dbName, String databaseConfigId) throws Exception {
        return this.permissionDao.getAllViewsForPostgreSQL(dbName, databaseConfigId);
    }

    public List<Map<String, Object>> getAllFuntionForPostgreSQL(String dbName, String databaseConfigId) throws Exception {
        return this.permissionDao.getAllFuntionForPostgreSQL(dbName, databaseConfigId);
    }

    public Page<Map<String, Object>> getDataForPostgreSQL(Page<Map<String, Object>> page, String tableName, String dbName, String databaseConfigId) throws Exception {
        return this.permissionDao.getDataForPostgreSQL(page, tableName, dbName, databaseConfigId);
    }

    public Page<Map<String, Object>> executeSqlHaveResForPostgreSQL(Page<Map<String, Object>> page, String sql, String dbName, String databaseConfigId) throws Exception {
        return this.permissionDao.executeSqlHaveResForPostgreSQL(page, sql, dbName, databaseConfigId);
    }

    public int deleteRowsNewForPostgreSQL(String databaseName, String tableName, String primary_key, List<String> condition, String databaseConfigId) throws Exception {
        return this.permissionDao.deleteRowsNewForPostgreSQL(databaseName, tableName, primary_key, condition, databaseConfigId);
    }

    public int saveRowsForPostgreSQL(Map map, String databaseName, String tableName, String databaseConfigId) throws Exception {
        return this.permissionDao.saveRowsForPostgreSQL(map, databaseName, tableName, databaseConfigId);
    }

    public int updateRowsNewForPostgreSQL(String databaseName, String tableName, List<String> strList, String databaseConfigId) throws Exception {
        String sql = "";
        tableName = "\"" + tableName + "\"";
        Iterator var7 = strList.iterator();

        while(var7.hasNext()) {
            String str1 = (String)var7.next();
            if(str1 == null || "".equals(str1)) {
                throw new Exception("数据不完整,保存失败!");
            }

            sql = " update  " + tableName + str1;
            this.permissionDao.executeSqlNotRes(sql, databaseName, databaseConfigId);
        }

        return 0;
    }

    public int saveDesginColumnForPostgreSQL(Map map, String databaseName, String tableName, String databaseConfigId) throws Exception {
        return this.permissionDao.saveDesginColumnForPostgreSQL(map, databaseName, tableName, databaseConfigId);
    }

    @Transactional
    public int updateTableColumnForPostgreSQL(String updated, String databaseName, String tableName, String databaseConfigId) throws Exception {
        if(updated != null) {
            JSONArray updateArray = JSONArray.parseArray(updated);

            for(int i = 0; i < updateArray.size(); ++i) {
                Map map1 = (Map)updateArray.get(i);
                HashMap maps = new HashMap();
                Iterator var10 = map1.keySet().iterator();

                String idValues;
                while(var10.hasNext()) {
                    idValues = (String)var10.next();
                    maps.put(idValues, map1.get(idValues));
                }

                idValues = "" + maps.get("TREESOFTPRIMARYKEY");
                this.permissionDao.updateTableColumnForPostgreSQL(maps, databaseName, tableName, "column_name", idValues, databaseConfigId);
            }
        }

        return 0;
    }

    public int deleteTableColumnForPostgreSQL(String databaseName, String tableName, String[] ids, String databaseConfigId) throws Exception {
        return this.permissionDao.deleteTableColumnForPostgreSQL(databaseName, tableName, ids, databaseConfigId);
    }

    public int updateTableNullAbleForPostgreSQL(String databaseName, String tableName, String column_name, String is_nullable, String databaseConfigId) throws Exception {
        return this.permissionDao.updateTableNullAbleForPostgreSQL(databaseName, tableName, column_name, is_nullable, databaseConfigId);
    }

    public int savePrimaryKeyForPostgreSQL(String databaseName, String tableName, String column_name, String column_key, String databaseConfigId) throws Exception {
        return this.permissionDao.savePrimaryKeyForPostgreSQL(databaseName, tableName, column_name, column_key, databaseConfigId);
    }

    public String getViewSqlForPostgreSQL(String databaseName, String tableName, String databaseConfigId) throws Exception {
        return this.permissionDao.getViewSqlForPostgreSQL(databaseName, tableName, databaseConfigId);
    }

    public List<Map<String, Object>> getAllDataBaseForMSSQL(String databaseConfigId) throws Exception {
        return this.permissionDao.getAllDataBaseForMSSQL(databaseConfigId);
    }

    public List<Map<String, Object>> getAllDataBaseForHive2(String databaseConfigId) throws Exception {
        return this.permissionDao.getAllDataBaseForHive2(databaseConfigId);
    }

    public List<Map<String, Object>> getAllTablesForMSSQL(String dbName, String databaseConfigId) throws Exception {
        return this.permissionDao.getAllTablesForMSSQL(dbName, databaseConfigId);
    }

    public List<Map<String, Object>> getTableColumns3ForMSSQL(String databaseName, String tableName, String databaseConfigId) throws Exception {
        return this.permissionDao.getTableColumns3ForMSSQL(databaseName, tableName, databaseConfigId);
    }

    public List<Map<String, Object>> getAllViewsForMSSQL(String dbName, String databaseConfigId) throws Exception {
        return this.permissionDao.getAllViewsForMSSQL(dbName, databaseConfigId);
    }

    public List<Map<String, Object>> getAllViewsForHive2(String dbName, String databaseConfigId) throws Exception {
        return this.permissionDao.getAllViewsForHive2(dbName, databaseConfigId);
    }

    public List<Map<String, Object>> getAllFuntionForMSSQL(String dbName, String databaseConfigId) throws Exception {
        return this.permissionDao.getAllFuntionForMSSQL(dbName, databaseConfigId);
    }

    public Page<Map<String, Object>> getDataForMSSQL(Page<Map<String, Object>> page, String tableName, String dbName, String databaseConfigId) throws Exception {
        return this.permissionDao.getDataForMSSQL(page, tableName, dbName, databaseConfigId);
    }

    public Page<Map<String, Object>> getDataForHive2(Page<Map<String, Object>> page, String tableName, String dbName, String databaseConfigId) throws Exception {
        return this.permissionDao.getDataForHive2(page, tableName, dbName, databaseConfigId);
    }

    public Page<Map<String, Object>> executeSqlHaveResForMSSQL(Page<Map<String, Object>> page, String sql, String dbName, String databaseConfigId) throws Exception {
        return this.permissionDao.executeSqlHaveResForMSSQL(page, sql, dbName, databaseConfigId);
    }

    public Page<Map<String, Object>> executeSqlHaveResForHive2(Page<Map<String, Object>> page, String sql, String dbName, String databaseConfigId) throws Exception {
        return this.permissionDao.executeSqlHaveResForHive2(page, sql, dbName, databaseConfigId);
    }

    public int deleteRowsNewForMSSQL(String databaseName, String tableName, String primary_key, List<String> condition, String databaseConfigId) throws Exception {
        return this.permissionDao.deleteRowsNewForMSSQL(databaseName, tableName, primary_key, condition, databaseConfigId);
    }

    public String getViewSqlForMSSQL(String databaseName, String tableName, String databaseConfigId) throws Exception {
        return this.permissionDao.getViewSqlForMSSQL(databaseName, tableName, databaseConfigId);
    }

    public int saveRowsForMSSQL(Map map, String databaseName, String tableName, String databaseConfigId) throws Exception {
        return this.permissionDao.saveRowsForMSSQL(map, databaseName, tableName, databaseConfigId);
    }

    public int updateRowsNewForMSSQL(String databaseName, String tableName, List<String> strList, String databaseConfigId) throws Exception {
        String sql = "";
        Iterator var7 = strList.iterator();

        while(var7.hasNext()) {
            String str1 = (String)var7.next();
            if(str1 == null || "".equals(str1)) {
                throw new Exception("数据不完整,保存失败!");
            }

            sql = " update  " + tableName + str1;
            this.permissionDao.executeSqlNotRes(sql, databaseName, databaseConfigId);
        }

        return 0;
    }

    public int saveDesginColumnForMSSQL(Map map, String databaseName, String tableName, String databaseConfigId) throws Exception {
        return this.permissionDao.saveDesginColumnForMSSQL(map, databaseName, tableName, databaseConfigId);
    }

    @Transactional
    public int updateTableColumnForMSSQL(String updated, String databaseName, String tableName, String databaseConfigId) throws Exception {
        if(updated != null) {
            JSONArray updateArray = JSONArray.parseArray(updated);

            for(int i = 0; i < updateArray.size(); ++i) {
                Map map1 = (Map)updateArray.get(i);
                HashMap maps = new HashMap();
                Iterator var10 = map1.keySet().iterator();

                String idValues;
                while(var10.hasNext()) {
                    idValues = (String)var10.next();
                    maps.put(idValues, map1.get(idValues));
                }

                idValues = "" + maps.get("TREESOFTPRIMARYKEY");
                this.permissionDao.updateTableColumnForMSSQL(maps, databaseName, tableName, "column_name", idValues, databaseConfigId);
            }
        }

        return 0;
    }

    public int deleteTableColumnForMSSQL(String databaseName, String tableName, String[] ids, String databaseConfigId) throws Exception {
        return this.permissionDao.deleteTableColumnForMSSQL(databaseName, tableName, ids, databaseConfigId);
    }

    public int updateTableNullAbleForMSSQL(String databaseName, String tableName, String column_name, String is_nullable, String databaseConfigId) throws Exception {
        return this.permissionDao.updateTableNullAbleForMSSQL(databaseName, tableName, column_name, is_nullable, databaseConfigId);
    }

    public int savePrimaryKeyForMSSQL(String databaseName, String tableName, String column_name, String column_key, String databaseConfigId) throws Exception {
        return this.permissionDao.savePrimaryKeyForMSSQL(databaseName, tableName, column_name, column_key, databaseConfigId);
    }

    public List<Map<String, Object>> getAllDataBaseForMongoDB(String databaseConfigId) throws Exception {
        return this.permissionDao.getAllDataBaseForMongoDB(databaseConfigId);
    }

    public List<Map<String, Object>> getAllTablesForMongoDB(String databaseName, String databaseConfigId) throws Exception {
        return this.permissionDao.getAllTablesForMongoDB(databaseName, databaseConfigId);
    }

    public List<Map<String, Object>> getAllTablesForHive2(String databaseName, String databaseConfigId) throws Exception {
        return this.permissionDao.getAllTablesForHive2(databaseName, databaseConfigId);
    }

    public Page<Map<String, Object>> getDataForMongoDB(Page<Map<String, Object>> page, String tableName, String databaseName, String databaseConfigId) throws Exception {
        return this.permissionDao.getDataForMongoDB(page, tableName, databaseName, databaseConfigId);
    }

    public Page<Map<String, Object>> getDataForMongoJson(Page<Map<String, Object>> page, String tableName, String databaseName, String databaseConfigId) throws Exception {
        return this.permissionDao.getDataForMongoJson(page, tableName, databaseName, databaseConfigId);
    }

    public Page<Map<String, Object>> executeSqlHaveResForMongoDB(Page<Map<String, Object>> page, String sql, String databaseName, String databaseConfigId) throws Exception {
        return this.permissionDao.executeSqlHaveResForMongoDB(page, sql, databaseName, databaseConfigId);
    }

    public int deleteRowsNewForMongoDB(String databaseName, String tableName, String primary_key, List<String> condition, String databaseConfigId) throws Exception {
        return this.permissionDao.deleteRowsNewForMongoDB(databaseName, tableName, primary_key, condition, databaseConfigId);
    }

    public int saveRowsForMongoDB(Map map, String databaseName, String tableName, String databaseConfigId) throws Exception {
        return this.permissionDao.saveRowsForMongoDB(map, databaseName, tableName, databaseConfigId);
    }

    public int updateRowsNewForMongoDB(String databaseName, String tableName, List<String> strList, String databaseConfigId) throws Exception {
        this.permissionDao.updateRowsNewForMongoDB(databaseName, tableName, strList, databaseConfigId);
        return 0;
    }

    public int executeSqlNotResForMongoDB(String sql, String databaseName, String databaseConfigId) throws Exception {
        return this.permissionDao.executeSqlNotResForMongoDB(sql, databaseName, databaseConfigId);
    }

    public List<Map<String, Object>> selectBackupList(String path) {
        ArrayList list = new ArrayList();
        List files = getFileSort(path);
        Iterator var5 = files.iterator();

        while(var5.hasNext()) {
            File file = (File)var5.next();
            HashMap map = new HashMap();
            SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            map.put("fileName", file.getName());
            map.put("fileLength", (float)file.length() / 1024.0F + " KB");
            map.put("fileModifiedDate", df.format(Long.valueOf(file.lastModified())));
            list.add(map);
        }

        return list;
    }

    public static List<File> getFileSort(String path) {
        List list = getFiles(path, new ArrayList());
        if(list != null && list.size() > 0) {
            Collections.sort(list, new Comparator<File>() {
                @Override
                public int compare(File file, File newFile) {
                    return file.lastModified() < newFile.lastModified()?1:(file.lastModified() == newFile.lastModified()?0:-1);
                }
            });
        }

        return list;
    }

    public static List<File> getFiles(String realpath, List<File> files) {
        File realFile = new File(realpath);
        if(realFile.isDirectory()) {
            File[] subfiles = realFile.listFiles();
            File[] var7 = subfiles;
            int var6 = subfiles.length;

            for(int var5 = 0; var5 < var6; ++var5) {
                File file = var7[var5];
                if(file.isFile()) {
                    files.add(file);
                }
            }
        }

        return files;
    }

    public boolean backupDatabaseExecute(String databaseName, String tableName, String path, String databaseConfigId) throws Exception {
        return this.permissionDao.backupDatabaseExecute(databaseName, tableName, path, databaseConfigId);
    }

    public boolean backupDatabaseExecuteForOracle(String databaseName, String tableName, String path, String databaseConfigId) throws Exception {
        return this.permissionDao.backupDatabaseExecuteForOracle(databaseName, tableName, path, databaseConfigId);
    }

    public boolean backupDatabaseExecuteForPostgreSQL(String databaseName, String tableName, String path, String databaseConfigId) throws Exception {
        return this.permissionDao.backupDatabaseExecuteForPostgreSQL(databaseName, tableName, path, databaseConfigId);
    }

    public boolean backupDatabaseExecuteForMSSQL(String databaseName, String tableName, String path, String databaseConfigId) throws Exception {
        return this.permissionDao.backupDatabaseExecuteForMSSQL(databaseName, tableName, path, databaseConfigId);
    }

    public boolean copyTableForMySql(String databaseName, String tableName, String databaseConfigId) throws Exception {
        return this.permissionDao.copyTableForMySql(databaseName, tableName, databaseConfigId);
    }

    public boolean copyTableForOracle(String databaseName, String tableName, String databaseConfigId) throws Exception {
        return this.permissionDao.copyTableForOracle(databaseName, tableName, databaseConfigId);
    }

    public boolean copyTableForPostgreSQL(String databaseName, String tableName, String databaseConfigId) throws Exception {
        return this.permissionDao.copyTableForPostgreSQL(databaseName, tableName, databaseConfigId);
    }

    public boolean renameTableForMySql(String databaseName, String tableName, String databaseConfigId, String newTableName) throws Exception {
        return this.permissionDao.renameTableForMySql(databaseName, tableName, databaseConfigId, newTableName);
    }

    public boolean renameTableForOracle(String databaseName, String tableName, String databaseConfigId, String newTableName) throws Exception {
        return this.permissionDao.renameTableForOracle(databaseName, tableName, databaseConfigId, newTableName);
    }

    public boolean renameTableForPostgreSQL(String databaseName, String tableName, String databaseConfigId, String newTableName) throws Exception {
        return this.permissionDao.renameTableForPostgreSQL(databaseName, tableName, databaseConfigId, newTableName);
    }

    public boolean renameTableForHive2(String databaseName, String tableName, String databaseConfigId, String newTableName) throws Exception {
        return this.permissionDao.renameTableForHive2(databaseName, tableName, databaseConfigId, newTableName);
    }

    public List<Map<String, Object>> exportDataToSQLForMySQL(String databaseName, String tableName, List<String> condition, String databaseConfigId) throws Exception {
        return this.permissionDao.exportDataToSQLForMySQL(databaseName, tableName, condition, databaseConfigId);
    }

    public List<Map<String, Object>> exportDataToSQLForOracle(String databaseName, String tableName, List<String> condition, String databaseConfigId) throws Exception {
        return this.permissionDao.exportDataToSQLForOracle(databaseName, tableName, condition, databaseConfigId);
    }

    public List<Map<String, Object>> exportDataToSQLForPostgreSQL(String databaseName, String tableName, List<String> condition, String databaseConfigId) throws Exception {
        return this.permissionDao.exportDataToSQLForPostgreSQL(databaseName, tableName, condition, databaseConfigId);
    }

    public List<Map<String, Object>> exportDataToSQLForMSSQL(String databaseName, String tableName, List<String> condition, String databaseConfigId) throws Exception {
        return this.permissionDao.exportDataToSQLForMSSQL(databaseName, tableName, condition, databaseConfigId);
    }

    public List<Map<String, Object>> exportDataToSQLForMongoDB(String databaseName, String tableName, List<String> condition, String databaseConfigId) throws Exception {
        return this.permissionDao.exportDataToSQLForMongoDB(databaseName, tableName, condition, databaseConfigId);
    }

    public boolean deleteBackupFile(String[] ids, String path) throws Exception {
        return this.permissionDao.deleteBackupFile(ids, path);
    }

    public boolean restoreDBFromFile(String databaseName, String fpath, String databaseConfigId) throws Exception {
        return this.permissionDao.restoreDBFromFile(databaseName, fpath, databaseConfigId);
    }

    public boolean dropTable(String databaseName, String tableName, String databaseConfigId) throws Exception {
        return this.permissionDao.dropTable(databaseName, tableName, databaseConfigId);
    }

    public boolean dropTableForPostgreSQL(String databaseName, String tableName, String databaseConfigId) throws Exception {
        return this.permissionDao.dropTableForPostgreSQL(databaseName, tableName, databaseConfigId);
    }

    public boolean dropTableForOracle(String databaseName, String tableName, String databaseConfigId) throws Exception {
        return this.permissionDao.dropTableForOracle(databaseName, tableName, databaseConfigId);
    }

    public boolean dropDatabase(String databaseName, String databaseConfigId) throws Exception {
        return this.permissionDao.dropDatabase(databaseName, databaseConfigId);
    }

    public boolean dropTableForMongoDB(String databaseName, String tableName, String databaseConfigId) throws Exception {
        return this.permissionDao.dropTableForMongoDB(databaseName, tableName, databaseConfigId);
    }

    public boolean dropDatabaseForMongoDB(String databaseName, String databaseConfigId) throws Exception {
        return this.permissionDao.dropDatabaseForMongoDB(databaseName, databaseConfigId);
    }

    public boolean clearTable(String databaseName, String tableName, String databaseConfigId) throws Exception {
        return this.permissionDao.clearTable(databaseName, tableName, databaseConfigId);
    }

    public boolean clearTableForMongoDB(String databaseName, String tableName, String databaseConfigId) throws Exception {
        return this.permissionDao.clearTableForMongoDB(databaseName, tableName, databaseConfigId);
    }

    public List<Map<String, Object>> viewTableMessForMySql(String databaseName, String tableName, String databaseConfigId) throws Exception {
        new ArrayList();
        ArrayList listAllColumn2 = new ArrayList();
        List listAllColumn = this.permissionDao.viewTableMessForMySql(databaseName, tableName, databaseConfigId);
        if(listAllColumn.size() > 0) {
            Map map = (Map)listAllColumn.get(0);
            HashMap tempMap = new HashMap();
            tempMap.put("propName", "表名");
            tempMap.put("propValue", tableName);
            listAllColumn2.add(tempMap);
            HashMap tempMap2 = new HashMap();
            tempMap2.put("propName", "数据库");
            tempMap2.put("propValue", databaseName);
            listAllColumn2.add(tempMap2);
            HashMap tempMap4 = new HashMap();
            tempMap4.put("propName", "总记录数");
            String num = this.permissionDao.getTableRows(databaseName, tableName, databaseConfigId);
            tempMap4.put("propValue", num);
            listAllColumn2.add(tempMap4);
            HashMap tempMap5 = new HashMap();
            tempMap5.put("propName", "表类型");
            tempMap5.put("propValue", map.get("ENGINE"));
            listAllColumn2.add(tempMap5);
            HashMap tempMap6 = new HashMap();
            tempMap6.put("propName", "自动递增数值");
            tempMap6.put("propValue", map.get("AUTO_INCREMENT"));
            listAllColumn2.add(tempMap6);
            HashMap tempMap7 = new HashMap();
            tempMap7.put("propName", "栏格式");
            tempMap7.put("propValue", map.get("ROW_FORMAT"));
            listAllColumn2.add(tempMap7);
            HashMap tempMap8 = new HashMap();
            tempMap8.put("propName", "刷新时间");
            tempMap8.put("propValue", map.get("UPDATE_TIME"));
            listAllColumn2.add(tempMap8);
            HashMap tempMap9 = new HashMap();
            tempMap9.put("propName", "创建时间");
            tempMap9.put("propValue", map.get("CREATE_TIME"));
            listAllColumn2.add(tempMap9);
            HashMap tempMap10 = new HashMap();
            tempMap10.put("propName", "校验时间");
            tempMap10.put("propValue", map.get("CHECK_TIME"));
            listAllColumn2.add(tempMap10);
            HashMap tempMap11 = new HashMap();
            tempMap11.put("propName", "索引长度");
            tempMap11.put("propValue", map.get("INDEX_LENGTH"));
            listAllColumn2.add(tempMap11);
            HashMap tempMap12 = new HashMap();
            tempMap12.put("propName", "数据长度");
            tempMap12.put("propValue", map.get("DATA_LENGTH"));
            listAllColumn2.add(tempMap12);
            HashMap tempMap13 = new HashMap();
            tempMap13.put("propName", "最大数据长度");
            tempMap13.put("propValue", map.get("MAX_DATA_LENGTH"));
            listAllColumn2.add(tempMap13);
            HashMap tempMap14 = new HashMap();
            tempMap14.put("propName", "数据空闲");
            tempMap14.put("propValue", map.get("DATA_FREE"));
            listAllColumn2.add(tempMap14);
            HashMap tempMap15 = new HashMap();
            tempMap15.put("propName", "整理");
            tempMap15.put("propValue", map.get("TABLE_COLLATION"));
            listAllColumn2.add(tempMap15);
            HashMap tempMap16 = new HashMap();
            tempMap16.put("propName", "创建选项");
            tempMap16.put("propValue", map.get("CREATE_OPTIONS"));
            listAllColumn2.add(tempMap16);
            HashMap tempMap17 = new HashMap();
            tempMap17.put("propName", "注释");
            tempMap17.put("propValue", map.get("TABLE_COMMENT"));
            listAllColumn2.add(tempMap17);
        }

        return listAllColumn2;
    }

    public List<Map<String, Object>> viewTableMessForOracle(String databaseName, String tableName, String databaseConfigId) throws Exception {
        List list = this.permissionDao.viewTableMessForOracle(databaseName, tableName, databaseConfigId);
        ArrayList listAllColumn2 = new ArrayList();
        if(list.size() > 0) {
            Map map = (Map)list.get(0);
            Iterator var8 = map.entrySet().iterator();

            while(var8.hasNext()) {
                Entry entry = (Entry)var8.next();
                HashMap tempMap = new HashMap();
                tempMap.put("propName", entry.getKey());
                tempMap.put("propValue", entry.getValue());
                listAllColumn2.add(tempMap);
            }
        }

        return listAllColumn2;
    }

    public List<Map<String, Object>> viewTableMessForPostgreSQL(String databaseName, String tableName, String databaseConfigId) throws Exception {
        List list = this.permissionDao.viewTableMessForPostgreSQL(databaseName, tableName, databaseConfigId);
        ArrayList listAllColumn2 = new ArrayList();
        if(list.size() > 0) {
            Map map = (Map)list.get(0);
            Iterator var8 = map.entrySet().iterator();

            while(var8.hasNext()) {
                Entry entry = (Entry)var8.next();
                HashMap tempMap = new HashMap();
                tempMap.put("propName", entry.getKey());
                tempMap.put("propValue", entry.getValue());
                listAllColumn2.add(tempMap);
            }
        }

        return listAllColumn2;
    }

    public List<Map<String, Object>> viewTableMessForHive2(String databaseName, String tableName, String databaseConfigId) throws Exception {
        List list = this.permissionDao.viewTableMessForHive2(databaseName, tableName, databaseConfigId);
        ArrayList listAllColumn2 = new ArrayList();

        for(int i = 0; i < list.size(); ++i) {
            Map map = (Map)list.get(i);
            HashMap tempMap = new HashMap();
            String col_name = (String)map.get("col_name");
            String data_type = (String)map.get("data_type");
            if(data_type != null) {
                data_type = data_type.trim();
                if(data_type.equals("numFiles")) {
                    col_name = data_type;
                    data_type = (String)map.get("comment");
                }

                if(data_type.equals("numRows")) {
                    col_name = data_type;
                    data_type = (String)map.get("comment");
                }

                if(data_type.equals("rawDataSize")) {
                    col_name = data_type;
                    data_type = (String)map.get("comment");
                }

                if(data_type.equals("totalSize")) {
                    col_name = data_type;
                    data_type = (String)map.get("comment");
                }

                if(data_type.equals("transient_lastDdlTime")) {
                    col_name = data_type;
                    data_type = (String)map.get("comment");
                }

                if(data_type.equals("field.delim")) {
                    col_name = data_type;
                    data_type = (String)map.get("comment");
                }

                if(data_type.equals("serialization.format")) {
                    col_name = data_type;
                    data_type = (String)map.get("comment");
                }
            }

            tempMap.put("propName", col_name);
            tempMap.put("propValue", data_type);
            listAllColumn2.add(tempMap);
        }

        return listAllColumn2;
    }

    public List<Map<String, Object>> viewTableMessForMSSQL(String databaseName, String tableName, String databaseConfigId) throws Exception {
        new ArrayList();
        ArrayList listAllColumn2 = new ArrayList();
        List listAllColumn = this.permissionDao.viewTableMessForMSSQL(databaseName, tableName, databaseConfigId);
        if(listAllColumn.size() > 0) {
            Map map = (Map)listAllColumn.get(0);
            HashMap tempMap = new HashMap();
            tempMap.put("propName", "表名");
            tempMap.put("propValue", tableName);
            listAllColumn2.add(tempMap);
            HashMap tempMap2 = new HashMap();
            tempMap2.put("propName", "数据库");
            tempMap2.put("propValue", databaseName);
            listAllColumn2.add(tempMap2);
            HashMap tempMap4 = new HashMap();
            tempMap4.put("propName", "总记录数");
            tempMap4.put("propValue", map.get("rows"));
            listAllColumn2.add(tempMap4);
            HashMap tempMap6 = new HashMap();
            tempMap6.put("propName", "自动递增数值");
            tempMap6.put("propValue", map.get("indid"));
            listAllColumn2.add(tempMap6);
            HashMap tempMap7 = new HashMap();
            tempMap7.put("propName", "状态");
            tempMap7.put("propValue", map.get("status"));
            listAllColumn2.add(tempMap7);
            HashMap tempMap8 = new HashMap();
            tempMap8.put("propName", "刷新时间");
            tempMap8.put("propValue", map.get("refdate"));
            listAllColumn2.add(tempMap8);
            HashMap tempMap9 = new HashMap();
            tempMap9.put("propName", "创建时间");
            tempMap9.put("propValue", map.get("crdate"));
            listAllColumn2.add(tempMap9);
            HashMap tempMap11 = new HashMap();
            tempMap11.put("propName", "索引长度");
            tempMap11.put("propValue", map.get("indid"));
            listAllColumn2.add(tempMap11);
            HashMap tempMap12 = new HashMap();
            tempMap12.put("propName", "数据长度");
            tempMap12.put("propValue", map.get("rowcnt"));
            listAllColumn2.add(tempMap12);
            HashMap tempMap13 = new HashMap();
            tempMap13.put("propName", "最大数据长度");
            tempMap13.put("propValue", map.get("maxlen"));
            listAllColumn2.add(tempMap13);
        }

        return listAllColumn2;
    }

    public List<Map<String, Object>> viewTableMessForMongoDB(String databaseName, String tableName, String databaseConfigId) throws Exception {
        List listAllColumn = this.permissionDao.viewTableMessForMongoDB(databaseName, tableName, databaseConfigId);
        return listAllColumn;
    }

    public int saveNewTable(JSONArray insertArray, String databaseName, String tableName, String databaseConfigId) throws Exception {
        return this.permissionDao.saveNewTable(insertArray, databaseName, tableName, databaseConfigId);
    }

    public int saveNewTableForMSSQL(JSONArray insertArray, String databaseName, String tableName, String databaseConfigId) throws Exception {
        return this.permissionDao.saveNewTableForMSSQL(insertArray, databaseName, tableName, databaseConfigId);
    }

    public int saveNewTableForOracle(JSONArray insertArray, String databaseName, String tableName, String databaseConfigId) throws Exception {
        return this.permissionDao.saveNewTableForOracle(insertArray, databaseName, tableName, databaseConfigId);
    }

    public int saveNewTableForPostgreSQL(JSONArray insertArray, String databaseName, String tableName, String databaseConfigId) throws Exception {
        return this.permissionDao.saveNewTableForPostgreSQL(insertArray, databaseName, tableName, databaseConfigId);
    }

    public int saveNewTableForMongoDB(JSONArray insertArray, String databaseName, String tableName, String databaseConfigId) throws Exception {
        return this.permissionDao.saveNewTableForMongoDB(insertArray, databaseName, tableName, databaseConfigId);
    }

    public Map<String, Object> queryDatabaseStatus(String databaseName, String databaseConfigId) throws Exception {
        return this.permissionDao.queryDatabaseStatus(databaseName, databaseConfigId);
    }

    public Map<String, Object> queryDatabaseStatusForOracle(String databaseName, String databaseConfigId) throws Exception {
        return this.permissionDao.queryDatabaseStatusForOracle(databaseName, databaseConfigId);
    }

    public Map<String, Object> queryDatabaseStatusForPostgreSQL(String databaseName, String databaseConfigId) throws Exception {
        return this.permissionDao.queryDatabaseStatusForPostgreSQL(databaseName, databaseConfigId);
    }

    public List<Map<String, Object>> queryTableSpaceForOracle(String databaseName, String databaseConfigId) throws Exception {
        return this.permissionDao.queryTableSpaceForOracle(databaseName, databaseConfigId);
    }

    public Map<String, Object> queryDatabaseStatusForMongoDB(String databaseName, String databaseConfigId) throws Exception {
        return this.permissionDao.queryDatabaseStatusForMongoDB(databaseName, databaseConfigId);
    }

    public List<Map<String, Object>> monitorItemValue(String databaseName, String databaseConfigId) throws Exception {
        return this.permissionDao.monitorItemValue(databaseName, databaseConfigId);
    }

    public List<Map<String, Object>> monitorItemValueForOracle(String databaseName, String databaseConfigId) throws Exception {
        return this.permissionDao.monitorItemValueForOracle(databaseName, databaseConfigId);
    }

    public Map<String, Object> getConfig(String id) throws Exception {
        return this.permissionDao.getConfig(id);
    }

    public Page<Map<String, Object>> configList(Page<Map<String, Object>> page) throws Exception {
        return this.permissionDao.configList(page);
    }

    public List<Map<String, Object>> getAllConfigList() throws Exception {
        return this.permissionDao.getAllConfigList();
    }

    public boolean deleteConfig(String[] ids) throws Exception {
        return this.permissionDao.deleteConfig(ids);
    }

    public boolean authorize() throws Exception {
        return this.permissionDao.authorize();
    }

    public Page<Map<String, Object>> dataSynchronizeList(Page<Map<String, Object>> page) throws Exception {
        return this.permissionDao.dataSynchronizeList(page);
    }

    public boolean deleteDataSynchronize(String[] ids) throws Exception {
        return this.permissionDao.deleteDataSynchronize(ids);
    }

    public Map<String, Object> getDataSynchronize(String id) throws Exception {
        return this.permissionDao.getDataSynchronize(id);
    }

    public Map<String, Object> getDataSynchronizeById2(String id) throws Exception {
        return this.permissionDao.getDataSynchronizeById2(id);
    }

    public boolean dataSynchronizeUpdate(DataSynchronize dataSynchronize) throws Exception {
        return this.permissionDao.dataSynchronizeUpdate(dataSynchronize);
    }

    public boolean dataSynchronizeUpdateStatus(String dataSynchronizeId, String status) {
        return this.permissionDao.dataSynchronizeUpdateStatus(dataSynchronizeId, status);
    }

    public List<Map<String, Object>> getDataSynchronizeListById(String[] ids) throws Exception {
        return this.permissionDao.getDataSynchronizeListById(ids);
    }

    public List<Map<String, Object>> getDataSynchronizeList2(String state) {
        return this.permissionDao.getDataSynchronizeList2(state);
    }

    public boolean insertByDataListForMySQL(List<Map<String, Object>> dataList, String databaseName, String table, String databaseConfigId) throws Exception {
        return this.permissionDao.insertByDataListForMySQL(dataList, databaseName, table, databaseConfigId);
    }

    public boolean insertByDataListForOracle(List<Map<String, Object>> dataList, String databaseName, String table, String databaseConfigId) throws Exception {
        return this.permissionDao.insertByDataListForOracle(dataList, databaseName, table, databaseConfigId);
    }

    public boolean insertByDataListForMSSQL(List<Map<String, Object>> dataList, String databaseName, String table, String databaseConfigId) throws Exception {
        return this.permissionDao.insertByDataListForMSSQL(dataList, databaseName, table, databaseConfigId);
    }

    public boolean insertByDataListForHive2(List<Map<String, Object>> dataList, String databaseName, String table, String databaseConfigId) throws Exception {
        return this.permissionDao.insertByDataListForHive2(dataList, databaseName, table, databaseConfigId);
    }

    public boolean insertByDataListForMongoDB(List<Map<String, Object>> dataList, String databaseName, String table, String databaseConfigId) throws Exception {
        return this.permissionDao.insertByDataListForMongoDB(dataList, databaseName, table, databaseConfigId);
    }

    public boolean updateByDataListForMySQL(List<Map<String, Object>> dataList, String databaseName, String table, String databaseConfigId, String qualification) throws Exception {
        return this.permissionDao.updateByDataListForMySQL(dataList, databaseName, table, databaseConfigId, qualification);
    }

    public boolean updateByDataListForOracle(List<Map<String, Object>> dataList, String databaseName, String table, String databaseConfigId, String qualification) throws Exception {
        return this.permissionDao.updateByDataListForOracle(dataList, databaseName, table, databaseConfigId, qualification);
    }

    public boolean updateByDataListForMSSQL(List<Map<String, Object>> dataList, String databaseName, String table, String databaseConfigId, String qualification) throws Exception {
        return this.permissionDao.updateByDataListForMSSQL(dataList, databaseName, table, databaseConfigId, qualification);
    }

    public boolean updateByDataListForMongoDB(List<Map<String, Object>> dataList, String databaseName, String table, String databaseConfigId, String qualification) throws Exception {
        return this.permissionDao.updateByDataListForMongoDB(dataList, databaseName, table, databaseConfigId, qualification);
    }

    public boolean insertOrUpdateByDataListForMySQL(List<Map<String, Object>> dataList, String databaseName, String table, String databaseConfigId, String qualification) throws Exception {
        return this.permissionDao.insertOrUpdateByDataListForMySQL(dataList, databaseName, table, databaseConfigId, qualification);
    }

    public boolean insertOrUpdateByDataListForOracle(List<Map<String, Object>> dataList, String databaseName, String table, String databaseConfigId, String qualification) throws Exception {
        return this.permissionDao.insertOrUpdateByDataListForOracle(dataList, databaseName, table, databaseConfigId, qualification);
    }

    public boolean insertOrUpdateByDataListForMSSQL(List<Map<String, Object>> dataList, String databaseName, String table, String databaseConfigId, String qualification) throws Exception {
        return this.permissionDao.insertOrUpdateByDataListForMSSQL(dataList, databaseName, table, databaseConfigId, qualification);
    }

    public boolean insertOrUpdateByDataListForMongoDB(List<Map<String, Object>> dataList, String databaseName, String table, String databaseConfigId, String qualification) throws Exception {
        return this.permissionDao.insertOrUpdateByDataListForMongoDB(dataList, databaseName, table, databaseConfigId, qualification);
    }

    public boolean deleteByDataListForMySQL(List<Map<String, Object>> dataList, String databaseName, String table, String databaseConfigId, String qualification) throws Exception {
        return this.permissionDao.deleteByDataListForMySQL(dataList, databaseName, table, databaseConfigId, qualification);
    }

    public boolean deleteByDataListForOracle(List<Map<String, Object>> dataList, String databaseName, String table, String databaseConfigId, String qualification) throws Exception {
        return this.permissionDao.deleteByDataListForOracle(dataList, databaseName, table, databaseConfigId, qualification);
    }

    public boolean deleteByDataListForMSSQL(List<Map<String, Object>> dataList, String databaseName, String table, String databaseConfigId, String qualification) throws Exception {
        return this.permissionDao.deleteByDataListForMSSQL(dataList, databaseName, table, databaseConfigId, qualification);
    }

    public boolean deleteByDataListForMongoDB(List<Map<String, Object>> dataList, String databaseName, String table, String databaseConfigId, String qualification) throws Exception {
        return this.permissionDao.deleteByDataListForMongoDB(dataList, databaseName, table, databaseConfigId, qualification);
    }

    public boolean dataSynchronizeLogSave(String status, String comments, String dataSynchronizeId) {
        return this.permissionDao.dataSynchronizeLogSave(status, comments, dataSynchronizeId);
    }

    public Page<Map<String, Object>> dataSynchronizeLogList(Page<Map<String, Object>> page, String dataSynchronizeId) throws Exception {
        return this.permissionDao.dataSynchronizeLogList(page, dataSynchronizeId);
    }

    public boolean deleteDataSynchronizeLog(String[] ids) throws Exception {
        return this.permissionDao.deleteDataSynchronizeLog(ids);
    }

    public boolean deleteDataSynchronizeLogByDS(String id) throws Exception {
        return this.permissionDao.deleteDataSynchronizeLogByDS(id);
    }

    public boolean isTheConfigUsed(String[] ids) throws Exception {
        return this.permissionDao.isTheConfigUsed(ids);
    }

    public int executeQueryForCount(String sql, String dbName, String databaseConfigId) {
        return this.permissionDao.executeQueryForCount(sql, dbName, databaseConfigId);
    }

    public boolean saveLog(String sql, String username, String ip) throws Exception {
        return this.permissionDao.saveLog(sql, username, ip);
    }
}
