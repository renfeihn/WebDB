//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package org.springframework.base.system.utils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.base.system.utils.Constants;

public class DBUtil {
    public DBUtil() {
    }

    public boolean do_update(String sql) throws Exception {
        String dbPath = Constants.DATABASEPATH + "servlet-context.db";
        Class.forName("org.sqlite.JDBC");
        Connection conn = DriverManager.getConnection("jdbc:sqlite:" + dbPath);
        Statement stat = conn.createStatement();
        stat.executeUpdate(sql);
        conn.close();
        return true;
    }

    public boolean do_update2(String sql) throws Exception {
        String dbPath = this.getClass().getResource("/").getPath();
        String os = System.getProperty("os.name");
        if(os.toLowerCase().startsWith("win") && dbPath.startsWith("/")) {
            dbPath = dbPath.substring(1);
            dbPath = dbPath.replace("/", "\\");
        }

        dbPath = dbPath + "servlet-context.db";
        Class.forName("org.sqlite.JDBC");
        Connection conn = DriverManager.getConnection("jdbc:sqlite:" + dbPath);
        Statement stat = conn.createStatement();
        stat.executeUpdate(sql);
        conn.close();
        return true;
    }

    public List executeQuery(String sql) {
//        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
//        ArrayList rslist = new ArrayList();
//        StringBuffer sqlPage = new StringBuffer(sql + " ");
//        Connection conn = null;
//        PreparedStatement pstmt = null;
//        ResultSet rs = null;
//
//        try {
//            String e = Constants.DATABASEPATH + "servlet-context.db";
//            Class.forName("org.sqlite.JDBC");
//            conn = DriverManager.getConnection("jdbc:sqlite:" + e);
//            pstmt = conn.prepareStatement(sqlPage.toString());
//            rs = pstmt.executeQuery();
//            ResultSetMetaData rsmd = rs.getMetaData();
//            int numberOfColumns = rsmd.getColumnCount();
//
//            while(rs.next()) {
//                HashMap row = new HashMap(numberOfColumns);
//
//                for(int i = 1; i <= numberOfColumns; ++i) {
//                    Object o = rs.getObject(i);
//                    if("Date".equalsIgnoreCase(rsmd.getColumnTypeName(i)) && o != null) {
//                        row.put(rsmd.getColumnName(i), formatter.format(o));
//                    } else {
//                        row.put(rsmd.getColumnName(i), o == null?"":o);
//                    }
//                }
//
//                rslist.add(row);
//            }
//        } catch (Exception var24) {
//            try {
//                rs.close();
//                pstmt.close();
//                conn.close();
//            } catch (SQLException var23) {
//                ;
//            }
//        } finally {
//            try {
//                rs.close();
//                pstmt.close();
//                conn.close();
//            } catch (SQLException var22) {
//                ;
//            }
//
//        }

        return executeQuery2(sql);
    }

    public List executeQuery2(String sql) {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        ArrayList rslist = new ArrayList();
        StringBuffer sqlPage = new StringBuffer(sql + " ");
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        try {
            String e = this.getClass().getResource("/").getPath();
            String os = System.getProperty("os.name");
            if(os.toLowerCase().startsWith("win") && e.startsWith("/")) {
                e = e.substring(1);
                e = e.replace("/", "\\");
            }

            e = e.replace("%20", " ");
            e = e + "servlet-context.db";
            Class.forName("org.sqlite.JDBC");
            conn = DriverManager.getConnection("jdbc:sqlite:" + e);
            pstmt = conn.prepareStatement(sqlPage.toString());
            rs = pstmt.executeQuery();
            ResultSetMetaData rsmd = rs.getMetaData();
            int numberOfColumns = rsmd.getColumnCount();

            while(rs.next()) {
                HashMap row = new HashMap(numberOfColumns);

                for(int i = 1; i <= numberOfColumns; ++i) {
                    Object o = rs.getObject(i);
                    if("Date".equalsIgnoreCase(rsmd.getColumnTypeName(i)) && o != null) {
                        row.put(rsmd.getColumnName(i), formatter.format(o));
                    } else {
                        row.put(rsmd.getColumnName(i), o == null?"":o);
                    }
                }

                rslist.add(row);
            }
        } catch (Exception var25) {
            System.out.println("系统启动时，取路径出错，程序布署路径不能有空隔！");
            System.out.println(var25.getMessage());

            try {
                rs.close();
                pstmt.close();
                conn.close();
            } catch (SQLException var24) {
                ;
            }
        } finally {
            try {
                rs.close();
                pstmt.close();
                conn.close();
            } catch (SQLException var23) {
                ;
            }

        }

        return rslist;
    }

    public int executeQueryForCount(String sql) {
        Connection conn = null;
        Object stmt = null;
        ResultSet rs = null;
        int rowCount = 0;

        try {
            String e = Constants.DATABASEPATH + "servlet-context.db";
            Class.forName("org.sqlite.JDBC");
            conn = DriverManager.getConnection("jdbc:sqlite:" + e);
            PreparedStatement pstmt = conn.prepareStatement(sql);

            for(rs = pstmt.executeQuery(); rs.next(); ++rowCount) {
                ;
            }
        } catch (Exception var18) {
            System.out.println(var18.getMessage());

            try {
                rs.close();
                conn.close();
            } catch (SQLException var17) {
                ;
            }
        } finally {
            try {
                rs.close();
                conn.close();
            } catch (SQLException var16) {
                ;
            }

        }

        return rowCount;
    }

    public Object setinsertData(String sql) throws Exception {
        String dbPath = Constants.DATABASEPATH + "servlet-context.db";
        Class.forName("org.sqlite.JDBC");
        Connection conn = DriverManager.getConnection("jdbc:sqlite:" + dbPath);
        Statement stmt = null;
        String flagOper = "0";

        Integer var8;
        try {
            stmt = conn.createStatement();
            var8 = Integer.valueOf(stmt.executeUpdate(sql));
        } catch (SQLException var15) {
            flagOper = "1";
            throw new Exception(var15.getMessage());
        } finally {
            try {
                stmt.close();
                conn.close();
            } catch (SQLException var14) {
                throw new Exception(var14.getMessage());
            }
        }

        return var8;
    }

    public List<Map<String, Object>> getConfigList() {
        DBUtil db1 = new DBUtil();
        List list = db1.executeQuery(" select * from  treesoft_config ");
        return list;
    }

    public List<Map<String, Object>> getDataSynchronizeList2(String state) {
        DBUtil db1 = new DBUtil();
        Object list = new ArrayList();
        String sql = "";

        try {
            if(state.equals("")) {
                sql = " select t1.id, t1.state , t1.name, t1.createDate,t1.updateDate ,t1.souceConfig_id as souceConfigId, t1.souceDataBase, t1.doSql ,t1.targetConfig_id as targetConfigId, t1.targetDataBase,t1.targetTable, t1.cron, t1.operation, t1.comments, t1.status , t2.ip||\':\'||t2.port as souceConfig , t3.ip||\':\'||t3.port as targetConfig from  treesoft_data_synchronize t1 left join treesoft_config t2 on t1.souceConfig_id = t2.id LEFT JOIN treesoft_config t3 on t1.targetConfig_id = t3.id  ";
            } else {
                sql = " select t1.id, t1.state , t1.name, t1.createDate,t1.updateDate ,t1.souceConfig_id as souceConfigId, t1.souceDataBase, t1.doSql ,t1.targetConfig_id as targetConfigId, t1.targetDataBase,t1.targetTable, t1.cron, t1.operation, t1.comments, t1.status , t2.ip||\':\'||t2.port as souceConfig , t3.ip||\':\'||t3.port as targetConfig from  treesoft_data_synchronize t1 left join treesoft_config t2 on t1.souceConfig_id = t2.id LEFT JOIN treesoft_config t3 on t1.targetConfig_id = t3.id where t1.state=\'" + state + "\'";
            }

            list = db1.executeQuery2(sql);
        } catch (Exception var6) {
            System.out.println("getDataSynchronizeList2 error= " + var6.getMessage());
            var6.printStackTrace();
        }

        return (List)list;
    }

    public List<Map<String, Object>> getDataSynchronizeListById(String[] ids) {
        DBUtil db1 = new DBUtil();
        StringBuffer sb = new StringBuffer();

        for(int newStr = 0; newStr < ids.length; ++newStr) {
            sb = sb.append("\'" + ids[newStr] + "\',");
        }

        String var7 = sb.toString();
        String str3 = var7.substring(0, var7.length() - 1);
        List list = db1.executeQuery(" select id, name, souceConfig_id as souceConfigId,souceDataBase, doSql ,targetConfig_id as targetConfigId, targetDataBase,targetTable, cron,operation,comments,status,state, qualification from  treesoft_data_synchronize where id in (" + str3 + ") ");
        return list;
    }

    public List<Map<String, Object>> getTaskListById(String[] ids) {
        DBUtil db1 = new DBUtil();
        StringBuffer sb = new StringBuffer();

        for(int newStr = 0; newStr < ids.length; ++newStr) {
            sb = sb.append("\'" + ids[newStr] + "\',");
        }

        String var7 = sb.toString();
        String str3 = var7.substring(0, var7.length() - 1);
        List list = db1.executeQuery(" select id, name, souceConfig_id as souceConfigId,souceDataBase, doSql ,targetConfig_id as targetConfigId, targetDataBase,targetTable, cron,operation,comments,status,state, qualification from  treesoft_task  where id in (" + str3 + ") ");
        return list;
    }

    public List<Map<String, Object>> getTaskList2(String state) {
        DBUtil db1 = new DBUtil();
        Object list = new ArrayList();
        String sql = "";

        try {
            if(state.equals("")) {
                sql = " select t1.id, t1.state , t1.name, t1.createDate,t1.updateDate ,t1.souceConfig_id as souceConfigId, t1.souceDataBase, t1.doSql ,t1.targetConfig_id as targetConfigId, t1.targetDataBase,t1.targetTable, t1.cron, t1.operation, t1.comments, t1.status , t2.ip||\':\'||t2.port as souceConfig , t3.ip||\':\'||t3.port as targetConfig from  treesoft_task t1 left join treesoft_config t2 on t1.souceConfig_id = t2.id LEFT JOIN treesoft_config t3 on t1.targetConfig_id = t3.id  ";
            } else {
                sql = " select t1.id, t1.state , t1.name, t1.createDate,t1.updateDate ,t1.souceConfig_id as souceConfigId, t1.souceDataBase, t1.doSql ,t1.targetConfig_id as targetConfigId, t1.targetDataBase,t1.targetTable, t1.cron, t1.operation, t1.comments, t1.status , t2.ip||\':\'||t2.port as souceConfig , t3.ip||\':\'||t3.port as targetConfig from  treesoft_task t1 left join treesoft_config t2 on t1.souceConfig_id = t2.id LEFT JOIN treesoft_config t3 on t1.targetConfig_id = t3.id where t1.state=\'" + state + "\'";
            }

            list = db1.executeQuery2(sql);
        } catch (Exception var6) {
            System.out.println("getTaskList2 error= " + var6.getMessage());
            var6.printStackTrace();
        }

        return (List)list;
    }

    public List<Map<String, Object>> getPersonList() {
        DBUtil db1 = new DBUtil();
        List list = db1.executeQuery(" select * from  treesoft_users ");
        return list;
    }
}
