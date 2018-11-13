//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package org.springframework.base.system.utils;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import org.springframework.base.common.utils.DateUtils;

public class FileWriteBuffer {
    private Connection conn_a = null;
    private PreparedStatement pstmt_a = null;
    private ResultSet rs_a = null;
    private String FilePath = "";
    private File fileName = null;
    private FileOutputStream fos;
    private BufferedOutputStream bos;
    private String fileAllPath = "";
    Map map = new HashMap();

    public FileWriteBuffer() {
    }

    public void init() {
    }

    public void readDataToFile() {
        long totalStart = System.currentTimeMillis();
        this.init();

        try {
            String totalEnd = DateUtils.getDateTimeStringNotTime(new Date());
            this.fileAllPath = this.FilePath + totalEnd + ".txt";
            this.fileName = new File(this.fileAllPath);

            try {
                this.fos = new FileOutputStream(this.fileName);
                this.bos = new BufferedOutputStream(this.fos);
            } catch (IOException var19) {
                var19.printStackTrace();
            }

            this.createFile("");
            String sql_a = (String)this.map.get("SQL");
            System.out.println(sql_a);
            this.pstmt_a = this.conn_a.prepareStatement(sql_a);
            this.rs_a = this.pstmt_a.executeQuery();
            int num = 0;

            while(this.rs_a.next()) {
                long startTime = System.currentTimeMillis();
                String size = (String)this.map.get("SIZE");
                String s = "";

                for(int endTime = 1; endTime <= Integer.parseInt(size); ++endTime) {
                    s = s + this.rs_a.getString(endTime) + "|";
                }

                s = s.substring(0, s.length() - 1);
                this.createFile(s);
                ++num;
                long var23 = System.currentTimeMillis();
                System.out.println("写入文件第" + num + "行，耗时" + (var23 - startTime) + "毫秒.");
                if(num >= 1000000) {
                    break;
                }

                if(num == 0) {
                    System.out.println("===============清缓存一次===========");

                    try {
                        this.bos.flush();
                    } catch (IOException var18) {
                        var18.printStackTrace();
                    }
                }
            }
        } catch (SQLException var20) {
            var20.printStackTrace();
        } finally {
            this.finish();
            this.closeDB();
        }

        long var22 = System.currentTimeMillis();
        System.out.println("----总耗时：" + (var22 - totalStart) + "毫秒");
    }

    public void createFile(String s) {
        try {
            this.bos.write(s.getBytes());
        } catch (IOException var3) {
            var3.printStackTrace();
        }

    }

    public void finish() {
        try {
            this.bos.flush();
            this.bos.close();
            this.fos.close();
        } catch (IOException var2) {
            System.err.println(var2);
        }

    }

    public void closeDB() {
        if(this.rs_a != null) {
            try {
                this.rs_a.close();
            } catch (SQLException var64) {
                var64.printStackTrace();
            } finally {
                if(this.pstmt_a != null) {
                    try {
                        this.pstmt_a.close();
                    } catch (SQLException var63) {
                        var63.printStackTrace();
                    } finally {
                        if(this.conn_a != null) {
                            try {
                                this.conn_a.close();
                            } catch (SQLException var62) {
                                var62.printStackTrace();
                            }
                        }

                    }
                }

            }
        }

    }
}
