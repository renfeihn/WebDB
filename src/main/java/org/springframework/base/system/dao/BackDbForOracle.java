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

public class BackDbForOracle {
    private File fileName = null;
    private FileOutputStream fos;
    private BufferedOutputStream bos;
    private String fileAllPath = "";
    private String version = "";
    Map map = new HashMap();

    public BackDbForOracle() {
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
            } catch (IOException var60) {
                var60.printStackTrace();
            }

            List totalEnd = db.queryForList(" select version  from product_component_version  where rownum<=1  ");
            if(totalEnd.size() > 0) {
                this.version = (String)((Map)totalEnd.get(0)).get("VERSION");
            }

            StringBuffer sb = new StringBuffer();
            sb.append("/* \r\n");
            sb.append("TreeSoft Data Transfer For Oracle \r\n");
            sb.append("Source Server         : " + ip + " \r\n");
            sb.append("Source Server Version : " + this.version + " \r\n");
            sb.append("Source Host           : " + ip + ":" + port + "\r\n");
            sb.append("Source Database       : " + databaseName + " \r\n");
            sb.append("web site: www.treesoft.cn \r\n");
            sb.append("Date: " + DateUtils.getDateTime() + " \r\n");
            sb.append("*/ \r\n");
            sb.append(" \r\n");
            this.createFile(sb.toString());
            PermissionDao pdao = new PermissionDao();
            Object tableList = new ArrayList();
            if(tableName.equals("")) {
                tableList = pdao.getAllTablesForOracle(databaseName, databaseConfigId);
            } else {
                HashMap num = new HashMap();
                num.put("TABLE_NAME", tableName);
                ((List)tableList).add(num);
            }

            int var64 = 0;
            Iterator var22 = ((List)tableList).iterator();

            while(var22.hasNext()) {
                Map map = (Map)var22.next();
                String table_name = (String)map.get("TABLE_NAME");
                StringBuffer sb2 = new StringBuffer();
                sb2.append(" \r\n");
                sb2.append("-- -------------------------------- \r\n");
                sb2.append("-- Table structure for `" + table_name + "`\r\n");
                sb2.append("-- -------------------------------- \r\n");
                sb2.append("CREATE TABLE " + table_name + " ( \r\n");
                String primary_key_list = "";
                String tableColumnStr = "";
                HashMap TableColumnType = new HashMap();
                List listTableColumn = pdao.getTableColumns3ForOracle(databaseName, table_name, databaseConfigId);

                for(Iterator column_comment = listTableColumn.iterator(); column_comment.hasNext(); sb2.append(",\r\n")) {
                    Map column_name = (Map)column_comment.next();
                    TableColumnType.put((String)column_name.get("COLUMN_NAME"), (String)column_name.get("DATA_TYPE"));
                    sb2.append("  " + column_name.get("COLUMN_NAME") + " " + column_name.get("COLUMN_TYPE"));
                    tableColumnStr = tableColumnStr + column_name.get("COLUMN_NAME") + ",";
                    if(column_name.get("COLUMN_KEY") != null && column_name.get("COLUMN_KEY").equals("PRI")) {
                        primary_key_list = primary_key_list + column_name.get("COLUMN_NAME") + ",";
                    }

                    if(column_name.get("IS_NULLABLE") != null) {
                        sb2.append(" NOT NULL ");
                    }
                }

                if("".equals(primary_key_list)) {
                    sb2.delete(sb2.length() - 3, sb2.length() - 1);
                    sb2.append("  \r\n");
                } else {
                    sb2.append("PRIMARY KEY (" + primary_key_list.substring(0, primary_key_list.length() - 1) + " )  \r\n");
                }

                sb2.append("); \r\n ");
                sb2.append("-- Add comments to the columns  \r\n");
                String var65 = "";
                String var66 = "";
                List tableColumns3ForOracle = pdao.getTableColumns3ForOracle(databaseName, table_name, databaseConfigId);
                Iterator rowCount = tableColumns3ForOracle.iterator();

                while(rowCount.hasNext()) {
                    Map sql1 = (Map)rowCount.next();
                    var65 = (String)sql1.get("COLUMN_NAME");
                    var66 = (String)sql1.get("COLUMN_COMMENT");
                    if(var66 != null) {
                        sb2.append("comment on column " + table_name + "." + var65 + " \r\n");
                        sb2.append("  is \'" + var66 + "\'; \r\n");
                    }
                }

                sb2.append("  \r\n");
                this.createFile(sb2.toString());
                ++var64;
                String var67 = "select count(*) from   " + table_name;
                int var68 = DBUtil2.executeQueryForCount(var67);
                this.createFile("-- ---------------------------- \r\n");
                this.createFile("-- Records of " + table_name + ", Total rows: " + var68 + " \r\n");
                this.createFile("-- ---------------------------- \r\n");
                int limitFrom = 0;
                short pageSize = 20000;

                for(int e = 0; e < var68; e += 20000) {
                    String sql3 = "select " + tableColumnStr.substring(0, tableColumnStr.length() - 1) + " from (select rownum rn, t1.* from " + table_name + " t1) where rn between " + limitFrom + " and  " + (limitFrom + pageSize);
                    List list = db.queryForList(sql3);
                    String key = "";
                    String values = "";
                    String tempValues = "";
                    limitFrom += 20000;
                    Iterator var44 = list.iterator();

                    label349:
                    while(var44.hasNext()) {
                        Map map4 = (Map)var44.next();
                        StringBuffer sb3 = new StringBuffer();
                        sb3.append("INSERT INTO " + table_name + " (" + tableColumnStr.substring(0, tableColumnStr.length() - 1) + ")  VALUES (");
                        values = "";
                        Iterator var46 = map4.entrySet().iterator();

                        while(true) {
                            while(true) {
                                Entry e1;
                                do {
                                    if(!var46.hasNext()) {
                                        sb3.append(values.substring(0, values.length() - 1));
                                        sb3.append(" ); \r\n");
                                        this.createFile(sb3.toString());
                                        ++var64;
                                        if(var64 >= '썐') {
                                            try {
                                                var64 = 0;
                                                this.bos.flush();
                                            } catch (IOException var59) {
                                                var59.printStackTrace();
                                            }
                                        }
                                        continue label349;
                                    }

                                    e1 = (Entry)var46.next();
                                    key = (String)e1.getKey();
                                } while(key.equals("RN"));

                                if(e1.getValue() == null) {
                                    values = values + "null,";
                                } else if(!((String)TableColumnType.get(key)).equals("DATE") && !((String)TableColumnType.get(key)).equals("DATETIME") && ((String)TableColumnType.get(key)).indexOf("TIMESTAMP") < 0) {
                                    if(!((String)TableColumnType.get(key)).equals("int") && !((String)TableColumnType.get(key)).equals("smallint") && !((String)TableColumnType.get(key)).equals("tinyint") && !((String)TableColumnType.get(key)).equals("integer") && !((String)TableColumnType.get(key)).equals("bit") && !((String)TableColumnType.get(key)).equals("NUMBER") && !((String)TableColumnType.get(key)).equals("bigint") && !((String)TableColumnType.get(key)).equals("long") && !((String)TableColumnType.get(key)).equals("float") && !((String)TableColumnType.get(key)).equals("decimal") && !((String)TableColumnType.get(key)).equals("numeric") && !((String)TableColumnType.get(key)).equals("mediumint")) {
                                        if(!((String)TableColumnType.get(key)).equals("binary") && !((String)TableColumnType.get(key)).equals("varbinary") && !((String)TableColumnType.get(key)).equals("blob") && !((String)TableColumnType.get(key)).equals("tinyblob") && !((String)TableColumnType.get(key)).equals("mediumblob") && !((String)TableColumnType.get(key)).equals("longblob")) {
                                            tempValues = (String)e1.getValue();
                                            tempValues = tempValues.replace("\'", "\\\'");
                                            tempValues = tempValues.replace("\\", "\\\\");
                                            tempValues = tempValues.replace("\r\n", "\\r\\n");
                                            tempValues = tempValues.replace("\n\r", "\\n\\r");
                                            tempValues = tempValues.replace("\r", "\\r");
                                            tempValues = tempValues.replace("\n", "\\n");
                                            values = values + "\'" + tempValues + "\',";
                                        } else {
                                            byte[] ss = (byte[])e1.getValue();
                                            if(ss.length == 0) {
                                                values = values + "null,";
                                            } else {
                                                values = values + "0x" + this.bytesToHexString(ss) + ",";
                                            }
                                        }
                                    } else {
                                        values = values + e1.getValue() + ",";
                                    }
                                } else {
                                    values = values + "to_date( \'" + e1.getValue() + "\',\'YYYY-MM-DD HH24:MI:SS\'),";
                                }
                            }
                        }
                    }
                }

                if(var64 >= '썐') {
                    System.out.println("===============清缓存一次" + var64 + "===========");

                    try {
                        var64 = 0;
                        this.bos.flush();
                    } catch (IOException var58) {
                        var58.printStackTrace();
                    }
                }
            }
        } catch (Exception var61) {
            var61.printStackTrace();
            throw var61;
        } finally {
            try {
                this.bos.flush();
                this.bos.close();
                this.fos.close();
            } catch (IOException var57) {
                System.err.println(var57);
            }

        }

        long var63 = System.currentTimeMillis();
        System.out.println("----备份总耗时：" + (var63 - totalStart) + "毫秒," + DateUtils.getDateTime());
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
