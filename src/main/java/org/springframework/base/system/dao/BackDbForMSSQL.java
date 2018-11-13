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

public class BackDbForMSSQL {
    private File fileName = null;
    private FileOutputStream fos;
    private BufferedOutputStream bos;
    private String fileAllPath = "";
    private String version = "";
    Map map = new HashMap();

    public BackDbForMSSQL() {
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
            } catch (IOException var59) {
                var59.printStackTrace();
            }

            List totalEnd = db.queryForList(" select @@version as version ");
            if(totalEnd.size() > 0) {
                this.version = (String)((Map)totalEnd.get(0)).get("version");
            }

            StringBuffer sb = new StringBuffer();
            sb.append("/* \r\n");
            sb.append("TreeSoft Data Transfer For SQL Server \r\n");
            sb.append(this.version + " \r\n");
            sb.append("Source Server         : " + ip + " \r\n");
            sb.append("Source Host           : " + ip + ":" + port + " \r\n");
            sb.append("Source Database       : " + databaseName + " \r\n");
            sb.append("web site: www.treesoft.cn \r\n");
            sb.append("Date: " + DateUtils.getDateTime() + " \r\n");
            sb.append("*/ \r\n");
            sb.append(" \r\n");
            this.createFile(sb.toString());
            PermissionDao pdao = new PermissionDao();
            Object tableList = new ArrayList();
            if(tableName.equals("")) {
                tableList = pdao.getAllTablesForMSSQL(databaseName, databaseConfigId);
            } else {
                HashMap num = new HashMap();
                num.put("TABLE_NAME", tableName);
                ((List)tableList).add(num);
            }

            int var63 = 0;
            Iterator var22 = ((List)tableList).iterator();

            while(var22.hasNext()) {
                Map map = (Map)var22.next();
                String table_name = (String)map.get("TABLE_NAME");
                StringBuffer sb2 = new StringBuffer();
                sb2.append(" \r\n");
                sb2.append("-- -------------------------------- \r\n");
                sb2.append("-- Table structure for `" + table_name + "`\r\n");
                sb2.append("-- -------------------------------- \r\n");
                sb2.append("IF  EXISTS ( SELECT * FROM sys.objects WHERE object_id = OBJECT_ID(\'" + table_name + "\') AND type in (\'U\') ) DROP TABLE " + table_name + " ;\r\n");
                sb2.append("CREATE TABLE " + table_name + " ( \r\n");
                String primary_key_list = "";
                String tableColumnStr = "";
                String column_type_temp = "";
                HashMap TableColumnType = new HashMap();
                List listTableColumn = pdao.getTableColumns3ForMSSQL(databaseName, table_name, databaseConfigId);

                for(Iterator rowCount = listTableColumn.iterator(); rowCount.hasNext(); sb2.append(",\r\n")) {
                    Map sql1 = (Map)rowCount.next();
                    TableColumnType.put((String)sql1.get("COLUMN_NAME"), (String)sql1.get("DATA_TYPE"));
                    column_type_temp = "" + sql1.get("COLUMN_TYPE");
                    if(column_type_temp.indexOf("date") != 0 && column_type_temp.indexOf("int") != 0 && column_type_temp.indexOf("smallint") != 0) {
                        sb2.append("  " + sql1.get("COLUMN_NAME") + " " + sql1.get("COLUMN_TYPE"));
                    } else {
                        sb2.append("  " + sql1.get("COLUMN_NAME") + " " + sql1.get("DATA_TYPE"));
                    }

                    tableColumnStr = tableColumnStr + sql1.get("COLUMN_NAME") + ",";
                    if(sql1.get("COLUMN_KEY") != null && sql1.get("COLUMN_KEY").equals("PRI")) {
                        primary_key_list = primary_key_list + sql1.get("COLUMN_NAME") + ",";
                    }

                    if(sql1.get("IS_NULLABLE").equals("NO")) {
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
                sb2.append("  \r\n");
                this.createFile(sb2.toString());
                ++var63;
                String var64 = "select count(*) as count from   " + table_name;
                int var65 = DBUtil2.executeQueryForCountForPostgesSQL(var64);
                this.createFile("-- ---------------------------- \r\n");
                this.createFile("-- Records of " + table_name + ", Total rows: " + var65 + " \r\n");
                this.createFile("-- ---------------------------- \r\n");
                int limitFrom = 0;
                short pageSize = 20000;

                for(int e = 0; e < var65; e += 20000) {
                    String sql3 = "select  " + tableColumnStr.substring(0, tableColumnStr.length() - 1) + "  from  " + table_name;
                    List list = DBUtil2.queryForListPageForMSSQL(sql3, pageSize, limitFrom);
                    String key = "";
                    String keys = "";
                    String values = "";
                    String tempValues = "";
                    limitFrom += 20000;
                    Iterator var43 = list.iterator();

                    while(var43.hasNext()) {
                        Map map4 = (Map)var43.next();
                        StringBuffer sb3 = new StringBuffer();
                        sb3.append("INSERT INTO " + table_name + "(");
                        key = "";
                        keys = "";
                        values = "";
                        Iterator var45 = map4.entrySet().iterator();

                        while(true) {
                            while(var45.hasNext()) {
                                Entry e1 = (Entry)var45.next();
                                key = (String)e1.getKey();
                                keys = keys + key + ",";
                                if(e1.getValue() == null) {
                                    values = values + "null,";
                                } else if(!((String)TableColumnType.get(key)).equals("DATE") && !((String)TableColumnType.get(key)).equals("DATETIME")) {
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
                                    values = values + "\'" + e1.getValue() + "\',";
                                }
                            }

                            sb3.append(keys.substring(0, keys.length() - 1));
                            sb3.append(") VALUES (");
                            sb3.append(values.substring(0, values.length() - 1));
                            sb3.append(" ); \r\n");
                            this.createFile(sb3.toString());
                            ++var63;
                            if(var63 >= '썐') {
                                try {
                                    var63 = 0;
                                    this.bos.flush();
                                } catch (IOException var58) {
                                    var58.printStackTrace();
                                }
                            }
                            break;
                        }
                    }
                }

                if(var63 >= '썐') {
                    System.out.println("===============清缓存一次" + var63 + "===========");

                    try {
                        var63 = 0;
                        this.bos.flush();
                    } catch (IOException var57) {
                        var57.printStackTrace();
                    }
                }
            }
        } catch (Exception var60) {
            var60.printStackTrace();
            throw var60;
        } finally {
            try {
                this.bos.flush();
                this.bos.close();
                this.fos.close();
            } catch (IOException var56) {
                System.err.println(var56);
            }

        }

        long var62 = System.currentTimeMillis();
        System.out.println("----备份总耗时：" + (var62 - totalStart) + "毫秒," + DateUtils.getDateTime());
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
