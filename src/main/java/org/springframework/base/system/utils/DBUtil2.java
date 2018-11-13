//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package org.springframework.base.system.utils;

import com.mongodb.MongoClient;
import com.mongodb.MongoClientURI;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.MongoIterable;
import org.apache.log4j.Logger;

import java.sql.*;
import java.text.SimpleDateFormat;
import java.util.*;

public class DBUtil2 {

    static Logger logger = Logger.getLogger(DBUtil2.class);

    private static String databaseType = "MySql";
    private static String driver = "com.mysql.jdbc.Driver";
    private static String databaseName = "jty";
    private static String url;
    private static String userName;
    private static String password;

    static {
        url = "jdbc:mysql://localhost:3306/" + databaseName;
        userName = "root";
        password = "123456";
    }

    public DBUtil2(String dbName, String databaseConfigId) {
        DBUtil db = new DBUtil();
        String sql = " select id, name, databaseType , databaseName, userName ,  password, port, ip ,url ,isdefault,driver from  treesoft_config where id=\'" + databaseConfigId + "\'";
        List list = db.executeQuery2(sql);
        Map map0 = (Map) list.get(0);
        String ip = "" + map0.get("ip");
        String port = "" + map0.get("port");
        String userName = "" + map0.get("userName");
        String password = "" + map0.get("password");
        // 修改取消密码加密
//        if (password.indexOf("`") > 0) {
//            password = password.split("`")[1];
//        }

        String databaseType = "" + map0.get("databaseType");

        if (databaseType.equals("MySql")) {
            driver = map0.get("driver") + "";
            url = "jdbc:mysql://" + ip + ":" + port + "/" + dbName + "?characterEncoding=utf8&tinyInt1isBit=false&zeroDateTimeBehavior=convertToNull&transformedBitIsBoolean=true";
        }

//        if (databaseType.equals("MySql")) {
//            driver = map0.get("driver") + "";
//            url = "jdbc:cds://" + ip + ":" + port + "/" + dbName + "?app=cif;key=6eb10c38615827f67236d2b04f128454;wycds.ddl.support=true;wycds.createtable.support=true";
//        }

        if (databaseType.equals("MariaDB")) {
            driver = "com.mysql.jdbc.Driver";
            url = "jdbc:mysql://" + ip + ":" + port + "/" + dbName + "?characterEncoding=utf8&tinyInt1isBit=false&zeroDateTimeBehavior=convertToNull&transformedBitIsBoolean=true";
        }


        if (databaseType.equals("Oracle")) {
            driver = "oracle.jdbc.driver.OracleDriver";
            url = "jdbc:oracle:thin:@" + ip + ":" + port + ":" + dbName;
        }

        if (databaseType.equals("PostgreSQL")) {
            driver = "org.postgresql.Driver";
            url = "jdbc:postgresql://" + ip + ":" + port + "/" + dbName;
        }

        if (databaseType.equals("MSSQL")) {
            driver = "com.microsoft.sqlserver.jdbc.SQLServerDriver";
            url = "jdbc:sqlserver://" + ip + ":" + port + ";database=" + dbName;
        }

        if (databaseType.equals("Hive2")) {
            driver = "org.apache.hive.jdbc.HiveDriver";
            url = "jdbc:hive2://" + ip + ":" + port + "/" + dbName;
        }

        if (logger.isDebugEnabled()) {
            logger.debug("driver: " + driver);
            logger.debug("url: " + url);
        }

        DBUtil2.userName = userName;
        DBUtil2.password = password;
    }

    public static final synchronized Connection getConnection() {

        try {
            logger.debug("driver: " + driver);
            logger.debug("url: " + url);
            logger.debug("userName: " + userName);
            logger.debug("password: " + password);
            Properties p = new Properties();
            p.setProperty("user",userName);
            p.setProperty("password",password);
            p.setProperty("wycds.route2all", "true");
//            Properties p1 = new Properties();
//            p.setProperty("useUnicode","yes");
//            p.setProperty("characterEncoding","utf8");
//            p.setProperty("dbpool.class","com.alibaba.druid.pool.DruidDataSource");
//            p.put("connectionProperties",p1);
            Class.forName(driver);
            Connection e = DriverManager.getConnection(url, p);
            return e;
        } catch (Exception e) {
            logger.error("error ", e);
            return null;
        }
    }

    public static boolean testConnection(String databaseType2, String databaseName2, String ip2, String port2, String user2, String pass2) {
        try {
            String e = "";
            if (databaseType2.equals("MySql")) {
                Class.forName("com.mysql.jdbc.Driver");
                e = "jdbc:mysql://" + ip2 + ":" + port2 + "/" + databaseName2 + "?characterEncoding=utf8";
            }

            if (databaseType2.equals("MariaDB")) {
                Class.forName("com.mysql.jdbc.Driver");
                e = "jdbc:mysql://" + ip2 + ":" + port2 + "/" + databaseName2 + "?characterEncoding=utf8";
            }

            if (databaseType2.equals("Oracle")) {
                Class.forName("oracle.jdbc.driver.OracleDriver");
                e = "jdbc:oracle:thin:@" + ip2 + ":" + port2 + ":" + databaseName2;
            }

            if (databaseType2.equals("PostgreSQL")) {
                Class.forName("org.postgresql.Driver");
                e = "jdbc:postgresql://" + ip2 + ":" + port2 + "/" + databaseName2;
            }

            if (databaseType2.equals("MSSQL")) {
                Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
                e = "jdbc:sqlserver://" + ip2 + ":" + port2 + ";database=" + databaseName2;
            }

            if (databaseType2.equals("Hive2")) {
                Class.forName("org.apache.hive.jdbc.HiveDriver");
                e = "jdbc:hive2://" + ip2 + ":" + port2 + "/" + databaseName2;
            }

            Connection conn;
            if (databaseType2.equals("MongoDB")) {
                conn = null;
                MongoClientURI conn1;
                if (user2.equals("")) {
                    conn1 = new MongoClientURI("mongodb://" + ip2 + ":" + port2);
                } else {
                    conn1 = new MongoClientURI("mongodb://" + user2 + ":" + pass2 + "@" + ip2 + ":" + port2 + "/?authSource=" + databaseName2 + "&ssl=false");
                }

                MongoClient mongoClient = new MongoClient(conn1);
                MongoDatabase database = mongoClient.getDatabase(databaseName2);
                MongoIterable colls = database.listCollectionNames();
                if (colls.iterator().hasNext()) {
                    if (mongoClient != null) {
                        mongoClient.close();
                        mongoClient = null;
                    }

                    return true;
                } else {
                    if (mongoClient != null) {
                        mongoClient.close();
                        mongoClient = null;
                    }

                    return false;
                }
            } else {
                conn = DriverManager.getConnection(e, user2, pass2);
                return conn != null;
            }
        } catch (Exception var11) {
            System.out.println(var11.getMessage());
            return false;
        }
    }

    public static int setupdateData(String sql) throws Exception {
        Connection conn = getConnection();
        Statement stmt = null;

        int var5;
        try {
            stmt = conn.createStatement();
            var5 = stmt.executeUpdate(sql);
        } catch (Exception var12) {
            System.out.println(var12.getMessage());
            throw new Exception(var12.getMessage());
        } finally {
            try {
                stmt.close();
                conn.close();
            } catch (SQLException var11) {
                System.out.println(var11.getMessage());
                throw new Exception(var11.getMessage());
            }
        }

        return var5;
    }

    public int updateExecuteBatch(List<String> sqlList) throws Exception {
        Connection conn = getConnection();
        Statement stmt = null;

        try {
            stmt = conn.createStatement();
            conn.setAutoCommit(false);
            Iterator var5 = sqlList.iterator();

            while (var5.hasNext()) {
                String e = (String) var5.next();
                e = e.replaceAll(";", "");
                stmt.addBatch(e);
            }

            int[] e1 = stmt.executeBatch();
            conn.commit();
            int var7 = e1.length;
            return var7;
        } catch (Exception var14) {
            conn.rollback();
            System.out.println(var14.getMessage());
            throw new Exception(var14.getMessage());
        } finally {
            try {
                stmt.close();
                conn.close();
            } catch (SQLException var13) {
                System.out.println(var13.getMessage());
                throw new Exception(var13.getMessage());
            }
        }
    }

    public List<Map<String, Object>> queryForList(String sql) throws Exception {
        try {
            Connection conn = null;
            PreparedStatement pstmt = null;
            ResultSet rs = null;
            ResultSetMetaData rsmd = null;
            boolean maxSize = true;
            String[] fields = (String[]) null;
            ArrayList times = new ArrayList();
            ArrayList clob = new ArrayList();
            ArrayList binary = new ArrayList();
            ArrayList rows = new ArrayList();
            LinkedHashMap row = null;
            conn = getConnection();
            pstmt = conn.prepareStatement(sql);
            rs = pstmt.executeQuery();
            rsmd = rs.getMetaData();
            int var17 = rsmd.getColumnCount();
            fields = new String[var17];

            for (int sdf = 0; sdf < var17; ++sdf) {
                fields[sdf] = rsmd.getColumnLabel(sdf + 1);
                if ("java.sql.Timestamp".equals(rsmd.getColumnClassName(sdf + 1)) || "oracle.sql.TIMESTAMP".equals(rsmd.getColumnClassName(sdf + 1))) {
                    times.add(fields[sdf]);
                }

                if ("oracle.jdbc.OracleClob".equals(rsmd.getColumnClassName(sdf + 1)) || "oracle.jdbc.OracleBlob".equals(rsmd.getColumnClassName(sdf + 1))) {
                    clob.add(fields[sdf]);
                }

                if ("[B".equals(rsmd.getColumnClassName(sdf + 1))) {
                    binary.add(fields[sdf]);
                }
            }

            SimpleDateFormat var18 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

            while (rs.next()) {
                row = new LinkedHashMap();

                for (int e = 0; e < var17; ++e) {
                    Object value = times.contains(fields[e]) ? rs.getTimestamp(fields[e]) : rs.getObject(fields[e]);
                    if (times.contains(fields[e]) && value != null) {
                        value = var18.format(value);
                    }

                    if (clob.contains(fields[e]) && value != null) {
                        value = "(Blob)";
                    }

                    if (binary.contains(fields[e]) && value != null) {
                        value = new String((byte[]) value);
                    }

                    row.put(fields[e], value);
                }

                rows.add(row);
            }

            rs.close();
            pstmt.close();
            conn.close();
            return rows;
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("error ", e);
            throw new Exception(e.getMessage());
        }

    }

    public List<Map<String, Object>> queryForListForPostgreSQL(String sql) throws Exception {
        try {


            Connection conn = null;
            PreparedStatement pstmt = null;
            ResultSet rs = null;
            ResultSetMetaData rsmd = null;
            boolean maxSize = true;
            String[] fields = (String[]) null;
            ArrayList times = new ArrayList();
            ArrayList binary = new ArrayList();
            ArrayList object = new ArrayList();
            ArrayList rows = new ArrayList();
            LinkedHashMap row = null;
            conn = getConnection();
            pstmt = conn.prepareStatement(sql);
            rs = pstmt.executeQuery();
            rsmd = rs.getMetaData();
            int var19 = rsmd.getColumnCount();
            fields = new String[var19];

            for (int sdf = 0; sdf < var19; ++sdf) {
                fields[sdf] = rsmd.getColumnLabel(sdf + 1);
                if ("java.sql.Timestamp".equals(rsmd.getColumnClassName(sdf + 1)) || "oracle.sql.TIMESTAMP".equals(rsmd.getColumnClassName(sdf + 1))) {
                    times.add(fields[sdf]);
                }

                if ("java.lang.Object".equals(rsmd.getColumnClassName(sdf + 1))) {
                    object.add(fields[sdf]);
                }

                if ("[B".equals(rsmd.getColumnClassName(sdf + 1))) {
                    binary.add(fields[sdf]);
                }
            }

            SimpleDateFormat var20 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

            while (rs.next()) {
                row = new LinkedHashMap();

                for (int e = 0; e < var19; ++e) {
                    Object value = times.contains(fields[e]) ? rs.getTimestamp(fields[e]) : rs.getObject(fields[e]);
                    if (times.contains(fields[e]) && value != null) {
                        value = var20.format(value);
                    }

                    try {
                        if (binary.contains(fields[e]) && value != null) {
                            value = new String((byte[]) value);
                        }

                        if (object.contains(fields[e]) && value != null) {
                            value = value.toString();
                        }
                    } catch (Exception var18) {
                        value = "(Object)";
                    }

                    row.put(fields[e], value);
                }

                rows.add(row);
            }


            rs.close();
            pstmt.close();
            conn.close();
            return rows;
        } catch (SQLException var17) {
            var17.printStackTrace();
            logger.error("error ", var17);
            throw new Exception(var17.getMessage());
        }
    }

    public static List<Map<String, Object>> queryForList2(String sql) throws Exception {
        ArrayList rows = new ArrayList();
        try {

            Connection conn = null;
            PreparedStatement pstmt = null;
            ResultSet rs = null;
            ResultSetMetaData rsmd = null;
            boolean maxSize = true;
            String[] fields = (String[]) null;
            HashMap row = null;
            conn = getConnection();
            pstmt = conn.prepareStatement(sql);
            rs = pstmt.executeQuery();
            rsmd = rs.getMetaData();
            int var10 = rsmd.getColumnCount();
            fields = new String[var10];

            while (rs.next()) {
                row = new HashMap();

                for (int i = 0; i < var10; ++i) {
                    row.put(rsmd.getColumnLabel(i + 1), rs.getObject(rsmd.getColumnLabel(i + 1)));
                }

                rows.add(row);
            }
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("error ", e);
        }
        return rows;
    }

    public static List<Map<String, Object>> queryForListPageForMSSQL(String sql, int maxRow, int beginIndex) throws Exception {
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        ResultSetMetaData rsmd = null;
        boolean maxSize = true;
        String[] fields = (String[]) null;
        ArrayList times = new ArrayList();
        ArrayList rows = new ArrayList();
        HashMap row = null;
        conn = getConnection();
        pstmt = conn.prepareStatement(sql, 1005, 1008);
        pstmt.setMaxRows(maxRow);
        rs = pstmt.executeQuery();
        rsmd = rs.getMetaData();
        int var16 = rsmd.getColumnCount();
        fields = new String[var16];

        for (int sdf = 0; sdf < var16; ++sdf) {
            fields[sdf] = rsmd.getColumnLabel(sdf + 1);
            if ("java.sql.Timestamp".equals(rsmd.getColumnClassName(sdf + 1))) {
                times.add(fields[sdf]);
            }
        }

        SimpleDateFormat var17 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        rs.absolute(beginIndex);

        while (rs.next()) {
            row = new HashMap();

            for (int e = 0; e < var16; ++e) {
                Object value = times.contains(fields[e]) ? rs.getTimestamp(fields[e]) : rs.getObject(fields[e]);
                if (times.contains(fields[e]) && value != null) {
                    value = var17.format(value);
                }

                row.put(fields[e], value);
            }

            rows.add(row);
        }

        try {
            rs.close();
            pstmt.close();
            conn.close();
            return rows;
        } catch (SQLException var15) {
            throw new Exception(var15.getMessage());
        }
    }

    public static List<Map<String, Object>> queryForListPageForHive2(String sql, int maxRow, int beginIndex) throws Exception {
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        ResultSetMetaData rsmd = null;
        boolean maxSize = true;
        String[] fields = (String[]) null;
        ArrayList times = new ArrayList();
        ArrayList rows = new ArrayList();
        LinkedHashMap row = null;
        conn = getConnection();
        pstmt = conn.prepareStatement(sql);
        pstmt.setMaxRows(maxRow);
        rs = pstmt.executeQuery();
        rsmd = rs.getMetaData();
        int var16 = rsmd.getColumnCount();
        fields = new String[var16];

        for (int sdf = 0; sdf < var16; ++sdf) {
            fields[sdf] = rsmd.getColumnLabel(sdf + 1);
            if ("java.sql.Timestamp".equals(rsmd.getColumnClassName(sdf + 1))) {
                times.add(fields[sdf]);
            }
        }

        SimpleDateFormat var17 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

        while (rs.next()) {
            row = new LinkedHashMap();

            for (int e = 0; e < var16; ++e) {
                Object value = times.contains(fields[e]) ? rs.getTimestamp(fields[e]) : rs.getObject(fields[e]);
                if (times.contains(fields[e]) && value != null) {
                    value = var17.format(value);
                }

                row.put(fields[e], value);
            }

            rows.add(row);
        }

        try {
            rs.close();
            pstmt.close();
            conn.close();
            return rows;
        } catch (SQLException var15) {
            throw new Exception(var15.getMessage());
        }
    }

    public static List<Map<String, Object>> queryForListWithType(String sql) {
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        ArrayList rows2 = new ArrayList();

        try {
            conn = getConnection();
            pstmt = conn.prepareStatement(sql);
            rs = pstmt.executeQuery();
            ResultSetMetaData e = rs.getMetaData();
            int columnCount = e.getColumnCount();
            rs.next();

            for (int i = 1; i < columnCount + 1; ++i) {
                HashMap map = new HashMap();
                map.put("column_name", e.getColumnName(i));
                map.put("column_value", rs.getObject(e.getColumnName(i)));
                map.put("data_type", e.getColumnTypeName(i));
                map.put("precision", Integer.valueOf(e.getPrecision(i)));
                map.put("isAutoIncrement", Boolean.valueOf(e.isAutoIncrement(i)));
                map.put("is_nullable", Integer.valueOf(e.isNullable(i)));
                map.put("isReadOnly", Boolean.valueOf(e.isReadOnly(i)));
                rows2.add(map);
            }
        } catch (Exception var17) {
            var17.printStackTrace();
        } finally {
            try {
                rs.close();
                pstmt.close();
                conn.close();
            } catch (SQLException var16) {
                var16.printStackTrace();
            }

        }

        return rows2;
    }

    public static List<Map<String, Object>> queryForColumnOnly(String sql) {
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        ArrayList rows2 = new ArrayList();

        try {
            conn = getConnection();
            pstmt = conn.prepareStatement(sql);
            rs = pstmt.executeQuery();
            ResultSetMetaData e = rs.getMetaData();
            int columnCount = e.getColumnCount();

            for (int i = 1; i < columnCount + 1; ++i) {
                HashMap map = new HashMap();
                map.put("column_name", e.getColumnName(i));
                map.put("data_type", e.getColumnTypeName(i));
                map.put("precision", Integer.valueOf(e.getPrecision(i)));
                map.put("isAutoIncrement", Boolean.valueOf(e.isAutoIncrement(i)));
                map.put("is_nullable", Integer.valueOf(e.isNullable(i)));
                map.put("isReadOnly", Boolean.valueOf(e.isReadOnly(i)));
                rows2.add(map);
            }
        } catch (Exception var17) {
            var17.printStackTrace();
        } finally {
            try {
                rs.close();
                pstmt.close();
                conn.close();
            } catch (SQLException var16) {
                var16.printStackTrace();
            }

        }

        return rows2;
    }

    public static List<Map<String, Object>> executeSqlForColumns(String sql) throws Exception {
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        ResultSetMetaData rsmd = null;
        boolean maxSize = true;
        ArrayList rows = new ArrayList();
        conn = getConnection();
        pstmt = conn.prepareStatement(sql);
        rs = pstmt.executeQuery();
        rsmd = rs.getMetaData();
        int var9 = rsmd.getColumnCount();

        for (int i = 0; i < var9; ++i) {
            HashMap map = new HashMap();
            map.put("column_name", rsmd.getColumnLabel(i + 1));
            map.put("data_type", rsmd.getColumnTypeName(i + 1));
            rows.add(map);
        }

        rs.close();
        pstmt.close();
        conn.close();
        return rows;
    }

    public static int executeQueryForCount(String sql) {
        int rowCount = 0;
        Connection conn = getConnection();
        Statement stmt = null;
        ResultSet rs = null;

        try {
            stmt = conn.createStatement();

            Object e;
            for (rs = stmt.executeQuery(sql); rs.next(); rowCount = Integer.parseInt(e.toString())) {
                e = rs.getObject("count(*)");
            }
        } catch (Exception var14) {
            System.out.println(var14.getMessage());
        } finally {
            try {
                rs.close();
                stmt.close();
                conn.close();
            } catch (SQLException var13) {
                ;
            }

        }

        return rowCount;
    }

    public static int executeQueryForCountForPostgesSQL(String sql) {
        int rowCount = 0;
        Connection conn = getConnection();
        Statement stmt = null;
        ResultSet rs = null;

        try {
            stmt = conn.createStatement();

            Object e;
            for (rs = stmt.executeQuery(sql); rs.next(); rowCount = Integer.parseInt(e.toString())) {
                e = rs.getObject("count");
            }
        } catch (Exception var14) {
            System.out.println(var14.getMessage());
        } finally {
            try {
                rs.close();
                stmt.close();
                conn.close();
            } catch (SQLException var13) {
                ;
            }

        }

        return rowCount;
    }

    public static int executeQueryForCount2(String sql) {
        int rowCount = 0;
        Connection conn = getConnection();
        Statement stmt = null;
        ResultSet rs = null;

        try {
            stmt = conn.createStatement();
            rs = stmt.executeQuery(sql);
            rs.last();
            rowCount = rs.getRow();
        } catch (Exception var14) {
            System.out.println(var14.getMessage());
        } finally {
            try {
                rs.close();
                stmt.close();
                conn.close();
            } catch (SQLException var13) {
                ;
            }

        }

        return rowCount;
    }

    public static boolean executeQuery(String sql) {
        boolean bl = false;
        Connection conn = getConnection();
        Statement stmt = null;
        ResultSet rs = null;

        try {
            stmt = conn.createStatement();
            rs = stmt.executeQuery(sql);
            if (rs.next()) {
                bl = true;
            }
        } catch (Exception var14) {
            var14.printStackTrace();
        } finally {
            try {
                rs.close();
                stmt.close();
                conn.close();
            } catch (SQLException var13) {
                var13.printStackTrace();
            }

        }

        return bl;
    }

    public static boolean testConn() {
        boolean bl = false;
        Connection conn = getConnection();
        if (conn != null) {
            bl = true;
        }

        try {
            conn.close();
        } catch (Exception var3) {
            ;
        }

        return bl;
    }

    public String getPrimaryKeys(String databaseName, String tableName) {
        Connection conn = null;
        new ArrayList();

        try {
            conn = getConnection();
            DatabaseMetaData e = conn.getMetaData();
            ResultSet rs2 = e.getPrimaryKeys(databaseName, (String) null, tableName);
            if (rs2.next()) {
                System.out.println("主键名称: " + rs2.getString(4));
                String var8 = rs2.getString(4);
                return var8;
            }
        } catch (Exception var17) {
            System.out.println("queryForColumnOnly  " + var17.getMessage());
        } finally {
            try {
                conn.close();
            } catch (SQLException var16) {
                ;
            }

        }

        return "";
    }

    public List<String> getPrimaryKeyss(String databaseName, String tableName) {
        Connection conn = null;
        ArrayList rows2 = new ArrayList();

        try {
            conn = getConnection();
            DatabaseMetaData e = conn.getMetaData();
            ResultSet rs2 = e.getPrimaryKeys(databaseName, (String) null, tableName);

            while (rs2.next()) {
                System.out.println("主键名称2: " + rs2.getString(4));
                rows2.add(rs2.getString(4));
            }
        } catch (Exception var15) {
            System.out.println("queryForColumnOnly  " + var15.getMessage());
        } finally {
            try {
                conn.close();
            } catch (SQLException var14) {
                ;
            }

        }

        return rows2;
    }

    public static int executeQueryForCountForOracle(String sql) {
        int rowCount = 0;
        Connection conn = getConnection();
        Statement stmt = null;
        ResultSet rs = null;
        String sql3 = " select count(*) as count from  (" + sql + ")";

        try {
            stmt = conn.createStatement();
            rs = stmt.executeQuery(sql3);
            rs.next();
            rowCount = rs.getInt("count");
        } catch (Exception var15) {
            System.out.println(var15.getMessage());
        } finally {
            try {
                rs.close();
                stmt.close();
                conn.close();
            } catch (SQLException var14) {
                ;
            }

        }

        return rowCount;
    }

    public static int executeQueryForCountForPostgreSQL(String sql) {
        int rowCount = 0;
        Connection conn = getConnection();
        Statement stmt = null;
        ResultSet rs = null;
        String sql3 = " select count(*) as count from  (" + sql + ") t ";

        try {
            stmt = conn.createStatement();
            rs = stmt.executeQuery(sql3);
            rs.next();
            rowCount = rs.getInt("count");
        } catch (Exception var15) {
            System.out.println(var15.getMessage());
        } finally {
            try {
                rs.close();
                stmt.close();
                conn.close();
            } catch (SQLException var14) {
                ;
            }

        }

        return rowCount;
    }
}
