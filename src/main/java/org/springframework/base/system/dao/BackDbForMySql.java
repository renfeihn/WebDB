//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package org.springframework.base.system.dao;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import org.springframework.base.common.utils.DateUtils;
import org.springframework.base.system.dao.PermissionDao;
import org.springframework.base.system.utils.DBUtil;
import org.springframework.base.system.utils.DBUtil2;

public class BackDbForMySql {
    private File fileName = null;
    private FileOutputStream fos;
    private BufferedOutputStream bos;
    private String fileAllPath = "";
    private String version = "";
    Map map = new HashMap();

    public BackDbForMySql() {
    }

    public void readDataToFile(String databaseName, String tableName, String path, String databaseConfigId) throws Exception {
        long totalStart = System.currentTimeMillis();
        DBUtil db0 = new DBUtil();
        String sql = " select id, name, databaseType , databaseName, userName ,  password, port, ip ,url ,isdefault from  treesoft_config where id=\'" + databaseConfigId + "\'";
        List list0 = db0.executeQuery(sql);
        Map map0 = (Map)list0.get(0);
        String ip = "" + map0.get("ip");
        String port = "" + map0.get("port");
        DBUtil2 db = new DBUtil2(databaseName, databaseConfigId);
        new Date();
        String nowDateStr = DateUtils.getDateTimeString(new Date());
        this.fileAllPath = path + "backup" + File.separator + databaseName + "_" + tableName + "_" + nowDateStr + ".sql";
        this.fileName = new File(this.fileAllPath);

        try {
            try {
                this.fos = new FileOutputStream(this.fileName);
                this.bos = new BufferedOutputStream(this.fos);
            } catch (IOException var57) {
                var57.printStackTrace();
            }

            List totalEnd = db.queryForList(" select version() as version ");
            if(totalEnd.size() > 0) {
                this.version = (String)((Map)totalEnd.get(0)).get("version");
            }

            StringBuffer sb = new StringBuffer();
            sb.append("/* \r\n");
            sb.append("TreeSoft Data Transfer For MySQL \r\n");
            sb.append("Source Server         : " + ip + " \r\n");
            sb.append("Source Server Version : " + this.version + " \r\n");
            sb.append("Source Host           : " + ip + ":" + port + "\r\n");
            sb.append("Source Database       : " + databaseName + " \r\n");
            sb.append("web site: www.treesoft.cn \r\n");
            sb.append("Date: " + DateUtils.getDateTime() + " \r\n");
            sb.append("*/ \r\n");
            sb.append(" \r\n");
            sb.append("SET FOREIGN_KEY_CHECKS=0; ");
            this.createFile(sb.toString());
            PermissionDao pdao = new PermissionDao();
            new ArrayList();
            List tableList;
            if(tableName.equals("")) {
                tableList = pdao.getAllTables2(databaseName, "", databaseConfigId);
            } else {
                tableList = pdao.getAllTables2(databaseName, tableName, databaseConfigId);
            }

            String createTableSQL = "";
            int num = 0;
            Iterator viewName = tableList.iterator();

            String proc_name;
            List procsList;
            Map map;
            while(viewName.hasNext()) {
                Map viewsList = (Map)viewName.next();
                proc_name = (String)viewsList.get("TABLE_NAME");
                procsList = db.queryForList("show create table `" + proc_name + "`");
                if(procsList.size() > 0) {
                    map = (Map)procsList.get(0);
                    createTableSQL = (String)map.get("Create Table");
                }

                StringBuffer var65 = new StringBuffer();
                var65.append(" \r\n");
                var65.append("-- -------------------------------- \r\n");
                var65.append("-- Table structure for `" + proc_name + "`\r\n");
                var65.append("-- -------------------------------- \r\n");
                var65.append("DROP TABLE IF EXISTS `" + proc_name + "`; \r\n");
                var65.append(createTableSQL);
                var65.append(";\r\n");
                HashMap TableColumnType = new HashMap();
                List listTableColumn = pdao.getTableColumns3(databaseName, proc_name, databaseConfigId);
                Iterator rowCount = listTableColumn.iterator();

                while(rowCount.hasNext()) {
                    Map sql1 = (Map)rowCount.next();
                    TableColumnType.put((String)sql1.get("COLUMN_NAME"), (String)sql1.get("DATA_TYPE"));
                }

                this.createFile(var65.toString());
                ++num;
                String var67 = "select count(*) from   `" + databaseName + "`.`" + proc_name + "`";
                int var68 = DBUtil2.executeQueryForCount(var67);
                this.createFile("-- ---------------------------- \r\n");
                this.createFile("-- Records of " + proc_name + ", Total rows: " + var68 + " \r\n");
                this.createFile("-- ---------------------------- \r\n");
                int limitFrom = 0;
                short pageSize = 20000;

                for(int e = 0; e < var68; e += 20000) {
                    String sql3 = "select  *  from  `" + databaseName + "`.`" + proc_name + "`  LIMIT " + limitFrom + "," + pageSize;
                    List list = db.queryForList(sql3);
                    String key = "";
                    String values = "";
                    String tempValues = "";
                    limitFrom += 20000;
                    Iterator var41 = list.iterator();

                    while(var41.hasNext()) {
                        Map map4 = (Map)var41.next();
                        StringBuffer sb3 = new StringBuffer();
                        sb3.append("INSERT INTO `" + proc_name + "` VALUES (");
                        values = "";
                        Iterator var43 = map4.entrySet().iterator();

                        while(true) {
                            while(var43.hasNext()) {
                                Entry e1 = (Entry)var43.next();
                                key = (String)e1.getKey();
                                if(e1.getValue() == null) {
                                    values = values + "null,";
                                } else if(!((String)TableColumnType.get(key)).equals("int") && !((String)TableColumnType.get(key)).equals("smallint") && !((String)TableColumnType.get(key)).equals("tinyint") && !((String)TableColumnType.get(key)).equals("integer") && !((String)TableColumnType.get(key)).equals("bit") && !((String)TableColumnType.get(key)).equals("bigint") && !((String)TableColumnType.get(key)).equals("double") && !((String)TableColumnType.get(key)).equals("float") && !((String)TableColumnType.get(key)).equals("decimal") && !((String)TableColumnType.get(key)).equals("numeric") && !((String)TableColumnType.get(key)).equals("mediumint")) {
                                    if(!((String)TableColumnType.get(key)).equals("binary") && !((String)TableColumnType.get(key)).equals("varbinary")) {
                                        if(!((String)TableColumnType.get(key)).equals("blob") && !((String)TableColumnType.get(key)).equals("tinyblob") && !((String)TableColumnType.get(key)).equals("mediumblob") && !((String)TableColumnType.get(key)).equals("longblob")) {
                                            tempValues = (String)e1.getValue();
                                            tempValues = tempValues.replace("\'", "\\\'");
                                            tempValues = tempValues.replace("\\", "\\\\");
                                            tempValues = tempValues.replace("\r\n", "\\r\\n");
                                            tempValues = tempValues.replace("\n\r", "\\n\\r");
                                            tempValues = tempValues.replace("\r", "\\r");
                                            tempValues = tempValues.replace("\n", "\\n");
                                            values = values + "\'" + tempValues + "\',";
                                        } else {
                                            values = values + "\'" + e1.getValue() + "\',";
                                        }
                                    } else {
                                        values = values + "\'" + e1.getValue() + "\',";
                                    }
                                } else {
                                    values = values + e1.getValue() + ",";
                                }
                            }

                            sb3.append(values.substring(0, values.length() - 1));
                            sb3.append(" ); \r\n");
                            this.createFile(sb3.toString());
                            ++num;
                            if(num >= '썐') {
                                try {
                                    num = 0;
                                    this.bos.flush();
                                } catch (IOException var56) {
                                    var56.printStackTrace();
                                }
                            }
                            break;
                        }
                    }
                }

                if(num >= '썐') {
                    System.out.println("===============清缓存一次" + num + "===========");

                    try {
                        num = 0;
                        this.bos.flush();
                    } catch (IOException var55) {
                        var55.printStackTrace();
                    }
                }
            }

            if(tableName.equals("")) {
                sb = new StringBuffer();
                List var61 = pdao.getAllViews(databaseName, databaseConfigId);
                String var62 = "";
                Iterator var64 = var61.iterator();

                while(var64.hasNext()) {
                    Map var63 = (Map)var64.next();
                    var62 = (String)var63.get("TABLE_NAME");
                    sb.append("-- ---------------------------- \r\n");
                    sb.append("-- View structure for `" + var62 + "` \r\n");
                    sb.append("-- ---------------------------- \r\n");
                    sb.append("DROP VIEW IF EXISTS `" + var62 + "`; \r\n");
                    sb.append("CREATE VIEW `" + var62 + "` AS " + pdao.getViewSql(databaseName, var62, databaseConfigId) + "; \r\n");
                }

                this.createFile(sb.toString());
                sb = new StringBuffer();
                procsList = pdao.getAllFuntion(databaseName, databaseConfigId);
                Iterator var66 = procsList.iterator();

                while(var66.hasNext()) {
                    map = (Map)var66.next();
                    proc_name = (String)map.get("ROUTINE_NAME");
                    sb.append("-- ---------------------------- \r\n");
                    sb.append("-- Procedure structure for `" + proc_name + "` \r\n");
                    sb.append("-- ---------------------------- \r\n");
                    sb.append("DROP PROCEDURE IF EXISTS `" + proc_name + "`; \r\n");
                    sb.append("DELIMITER ;;\r\n");
                    sb.append(pdao.getProcSqlForSQL(databaseName, proc_name, databaseConfigId) + " ;;\r\n");
                    sb.append("DELIMITER ;\r\n");
                }

                this.createFile(sb.toString());
            }
        } catch (Exception var58) {
            var58.printStackTrace();
        } finally {
            try {
                this.bos.flush();
                this.bos.close();
                this.fos.close();
            } catch (IOException var54) {
                System.err.println(var54);
            }

        }

        long var60 = System.currentTimeMillis();
        System.out.println("----备份总耗时：" + (var60 - totalStart) + "毫秒," + DateUtils.getDateTime());
    }

    public void createFile(String s) {
        try {
            this.bos.write(s.getBytes("UTF-8"));
        } catch (IOException var3) {
            var3.printStackTrace();
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
}
