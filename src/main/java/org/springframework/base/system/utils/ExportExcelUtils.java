//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package org.springframework.base.system.utils;

import java.io.IOException;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import jxl.Workbook;
import jxl.write.Label;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;

public class ExportExcelUtils {
    public ExportExcelUtils() {
    }

    public static void downloadExcel(HttpServletResponse response, List<Map<String, Object>> list) {
        WritableWorkbook book = null;
        response.reset();
        response.setCharacterEncoding("UTF-8");
        ServletOutputStream os = null;

        try {
            response.setContentType("application/DOWLOAD");
            response.setCharacterEncoding("UTF-8");
            response.setHeader("Content-Disposition", "attachment; filename=data.xls");
            os = response.getOutputStream();
            book = Workbook.createWorkbook(os);
        } catch (IOException var20) {
            System.out.println("导出失败");
        }

        String value = "";

        try {
            WritableSheet e = book.createSheet("查询结果", 0);
            if(list.size() >= 0) {
                Map i = (Map)list.get(0);
                int map4 = 0;

                for(Iterator vo = i.entrySet().iterator(); vo.hasNext(); ++map4) {
                    Entry y = (Entry)vo.next();
                    String key = (String)y.getKey();
                    e.addCell(new Label(map4, 0, key));
                }
            }

            for(int var23 = 0; var23 < list.size(); ++var23) {
                Map var24 = (Map)list.get(var23);
                int var25 = 0;

                for(Iterator var27 = var24.entrySet().iterator(); var27.hasNext(); ++var25) {
                    Entry var26 = (Entry)var27.next();
                    (new StringBuilder()).append(var26.getValue()).toString();
                    if(var26.getValue() == null) {
                        value = "";
                    } else {
                        value = "" + var26.getValue();
                    }

                    e.addCell(new Label(var25, var23 + 1, value));
                }
            }

            book.write();
            book.close();
        } catch (Exception var21) {
            System.out.println("导出失败");
        } finally {
            if(os != null) {
                try {
                    os.close();
                } catch (IOException var19) {
                    var19.printStackTrace();
                }
            }

        }

    }
}
