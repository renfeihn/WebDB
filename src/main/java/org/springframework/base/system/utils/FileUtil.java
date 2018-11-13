//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package org.springframework.base.system.utils;

import com.github.junrar.Archive;
import com.github.junrar.rarfile.FileHeader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Enumeration;
import org.apache.tools.zip.ZipEntry;
import org.apache.tools.zip.ZipFile;
import org.apache.tools.zip.ZipOutputStream;

public class FileUtil {
    public FileUtil() {
    }

    public static void ZipFiles(File srcfile, File zipfile) {
        byte[] buf = new byte[1024];

        try {
            ZipOutputStream e = new ZipOutputStream(new FileOutputStream(zipfile));
            FileInputStream in = new FileInputStream(srcfile);
            e.putNextEntry(new ZipEntry(srcfile.getName()));
            String str = srcfile.getName();

            int len;
            while((len = in.read(buf)) > 0) {
                e.write(buf, 0, len);
            }

            e.closeEntry();
            in.close();
            e.close();
            System.out.println("压缩完成.");
        } catch (IOException var7) {
            var7.printStackTrace();
        }

    }

    public static void unZipFiles(File zipfile, String descDir) throws Exception {
        ZipFile zf = new ZipFile(zipfile);
        Enumeration entries = zf.getEntries();

        while(entries.hasMoreElements()) {
            ZipEntry entry = (ZipEntry)entries.nextElement();
            String zipEntryName = entry.getName();
            InputStream in = zf.getInputStream(entry);
            FileOutputStream out = new FileOutputStream(descDir + zipEntryName);
            byte[] buf1 = new byte[1024];

            int len;
            while((len = in.read(buf1)) > 0) {
                out.write(buf1, 0, len);
            }

            in.close();
            out.close();
        }

    }

    public static void unRarFile(String srcRarPath, String dstDirectoryPath) {
        if(!srcRarPath.toLowerCase().endsWith(".rar")) {
            System.out.println("非rar文件！");
        } else {
            File dstDiretory = new File(dstDirectoryPath);
            if(!dstDiretory.exists()) {
                dstDiretory.mkdirs();
            }

            Archive a = null;

            try {
                a = new Archive(new File(srcRarPath));
                if(a != null) {
                    a.getMainHeader().print();

                    for(FileHeader e = a.nextFileHeader(); e != null; e = a.nextFileHeader()) {
                        File out;
                        if(e.isDirectory()) {
                            out = new File(dstDirectoryPath + File.separator + e.getFileNameString());
                            out.mkdirs();
                        } else {
                            out = new File(dstDirectoryPath + File.separator + e.getFileNameString().trim());

                            try {
                                if(!out.exists()) {
                                    if(!out.getParentFile().exists()) {
                                        out.getParentFile().mkdirs();
                                    }

                                    out.createNewFile();
                                }

                                FileOutputStream ex = new FileOutputStream(out);
                                a.extractFile(e, ex);
                                ex.close();
                            } catch (Exception var7) {
                                var7.printStackTrace();
                            }
                        }
                    }

                    a.close();
                }
            } catch (Exception var8) {
                var8.printStackTrace();
            }

        }
    }
}
