//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package org.springframework.base.system.utils;

import com.sun.management.OperatingSystemMXBean;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.io.LineNumberReader;
import java.lang.management.ManagementFactory;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.StringTokenizer;
import org.apache.log4j.Logger;

public class ComputerMonitorUtil {
    private static String osName = System.getProperty("os.name");
    private static final int CPUTIME = 500;
    private static final int PERCENT = 100;
    private static final int FAULTLENGTH = 10;
    private static final Logger logger = Logger.getLogger(ComputerMonitorUtil.class);

    public ComputerMonitorUtil() {
    }

    public static double getCpuUsage() {
        if(!osName.toLowerCase().contains("windows") && !osName.toLowerCase().contains("win")) {
            try {
                Map e1 = cpuinfo();
                Thread.sleep(500L);
                Map map21 = cpuinfo();
                long user11 = Long.parseLong(e1.get("user").toString());
                long nice1 = Long.parseLong(e1.get("nice").toString());
                long system1 = Long.parseLong(e1.get("system").toString());
                long idle11 = Long.parseLong(e1.get("idle").toString());
                long user2 = Long.parseLong(map21.get("user").toString());
                long nice2 = Long.parseLong(map21.get("nice").toString());
                long system2 = Long.parseLong(map21.get("system").toString());
                long idle2 = Long.parseLong(map21.get("idle").toString());
                long total1 = user11 + system1 + nice1;
                long total2 = user2 + system2 + nice2;
                float total = (float)(total2 - total1);
                long totalIdle1 = user11 + nice1 + system1 + idle11;
                long totalIdle2 = user2 + nice2 + system2 + idle2;
                float totalidle = (float)(totalIdle2 - totalIdle1);
                float cpusage1 = total / totalidle * 100.0F;
                BigDecimal b1 = new BigDecimal((double)cpusage1);
                double cpuUsage1 = b1.setScale(2, 4).doubleValue();
                return cpuUsage1;
            } catch (InterruptedException var32) {
                logger.debug(var32);
                return 0.0D;
            }
        } else {
            try {
                String e = System.getenv("windir") + "//system32//wbem//wmic.exe process get Caption,CommandLine,KernelModeTime,ReadOperationCount,ThreadCount,UserModeTime,WriteOperationCount";
                long[] map2 = readCpu(Runtime.getRuntime().exec(e));
                Thread.sleep(500L);
                long[] user1 = readCpu(Runtime.getRuntime().exec(e));
                if(map2 != null && user1 != null) {
                    long idletime = user1[0] - map2[0];
                    long busytime = user1[1] - map2[1];
                    Double cpusage = Double.valueOf((double)(100L * busytime) * 1.0D / (double)(busytime + idletime));
                    BigDecimal idle1 = new BigDecimal(cpusage.doubleValue());
                    double cpuUsage = idle1.setScale(2, 4).doubleValue();
                    return cpuUsage;
                } else {
                    return 0.0D;
                }
            } catch (Exception var33) {
                logger.debug(var33);
                return 0.0D;
            }
        }
    }

    public static Map<?, ?> cpuinfo() {
        InputStreamReader inputs = null;
        BufferedReader buffer = null;
        HashMap map = new HashMap();

        try {
            inputs = new InputStreamReader(new FileInputStream("/proc/stat"));
            buffer = new BufferedReader(inputs);
            String e = "";

            while(true) {
                e = buffer.readLine();
                if(e == null) {
                    break;
                }

                if(e.startsWith("cpu")) {
                    StringTokenizer tokenizer = new StringTokenizer(e);
                    ArrayList temp = new ArrayList();

                    while(tokenizer.hasMoreElements()) {
                        String value = tokenizer.nextToken();
                        temp.add(value);
                    }

                    map.put("user", temp.get(1));
                    map.put("nice", temp.get(2));
                    map.put("system", temp.get(3));
                    map.put("idle", temp.get(4));
                    map.put("iowait", temp.get(5));
                    map.put("irq", temp.get(6));
                    map.put("softirq", temp.get(7));
                    map.put("stealstolen", temp.get(8));
                    break;
                }
            }
        } catch (Exception var15) {
            logger.debug(var15);
        } finally {
            try {
                buffer.close();
                inputs.close();
            } catch (Exception var14) {
                logger.debug(var14);
            }

        }

        return map;
    }

    public static double getMemUsage() {
        if(!osName.toLowerCase().contains("windows") && !osName.toLowerCase().contains("win")) {
            HashMap map1 = new HashMap();
            InputStreamReader inputs1 = null;
            BufferedReader buffer = null;

            try {
                inputs1 = new InputStreamReader(new FileInputStream("/proc/meminfo"));
                buffer = new BufferedReader(inputs1);
                String e1 = "";

                while(true) {
                    e1 = buffer.readLine();
                    if(e1 == null) {
                        long memTotal2 = Long.parseLong(map1.get("MemTotal").toString());
                        long memFree2 = Long.parseLong(map1.get("MemFree").toString());
                        long memused1 = memTotal2 - memFree2;
                        long buffers = Long.parseLong(map1.get("Buffers").toString());
                        long cached = Long.parseLong(map1.get("Cached").toString());
                        double usage = (double)(memused1 - buffers - cached) / (double)memTotal2 * 100.0D;
                        BigDecimal b1 = new BigDecimal(usage);
                        double memoryUsage = b1.setScale(2, 4).doubleValue();
                        double var20 = memoryUsage;
                        return var20;
                    }

                    byte memTotal = 0;
                    int endIndex1 = e1.indexOf(":");
                    if(endIndex1 != -1) {
                        String memFree1 = e1.substring(memTotal, endIndex1);
                        int memTotal1 = endIndex1 + 1;
                        endIndex1 = e1.length();
                        String memory1 = e1.substring(memTotal1, endIndex1);
                        String memused = memory1.replace("kB", "").trim();
                        map1.put(memFree1, memused);
                    }
                }
            } catch (Exception var30) {
                logger.debug(var30);
                return 0.0D;
            } finally {
                try {
                    buffer.close();
                    inputs1.close();
                } catch (Exception var29) {
                    logger.debug(var29);
                }

            }
        } else {
            try {
                OperatingSystemMXBean map = (OperatingSystemMXBean)ManagementFactory.getOperatingSystemMXBean();
                long inputs = map.getTotalSwapSpaceSize();
                long e = map.getFreePhysicalMemorySize();
                Double endIndex = Double.valueOf(Double.valueOf(1.0D - (double)e * 1.0D / (double)inputs).doubleValue() * 100.0D);
                BigDecimal memFree = new BigDecimal(endIndex.doubleValue());
                double memory = memFree.setScale(2, 4).doubleValue();
                return memory;
            } catch (Exception var32) {
                logger.debug(var32);
                return 0.0D;
            }
        }
    }

    public static double getDiskUsage() throws Exception {
        double totalHD = 0.0D;
        double usedHD = 0.0D;
        BigDecimal var24;
        if(!osName.toLowerCase().contains("windows") && !osName.toLowerCase().contains("win")) {
            Runtime var19 = Runtime.getRuntime();
            Process p = var19.exec("df -hl");
            BufferedReader var20 = null;

            try {
                var20 = new BufferedReader(new InputStreamReader(p.getInputStream()));
                String precent = null;
                String[] var23 = (String[])null;

                while((precent = var20.readLine()) != null) {
                    int var25 = 0;
                    var23 = precent.split(" ");
                    String[] var28 = var23;
                    int var12 = var23.length;

                    for(int var27 = 0; var27 < var12; ++var27) {
                        String var26 = var28[var27];
                        if(var26.trim().length() != 0) {
                            ++var25;
                            if(var26.indexOf("G") != -1) {
                                if(var25 == 2 && !var26.equals("") && !var26.equals("0")) {
                                    totalHD += Double.parseDouble(var26.substring(0, var26.length() - 1)) * 1024.0D;
                                }

                                if(var25 == 3 && !var26.equals("none") && !var26.equals("0")) {
                                    usedHD += Double.parseDouble(var26.substring(0, var26.length() - 1)) * 1024.0D;
                                }
                            }

                            if(var26.indexOf("M") != -1) {
                                if(var25 == 2 && !var26.equals("") && !var26.equals("0")) {
                                    totalHD += Double.parseDouble(var26.substring(0, var26.length() - 1));
                                }

                                if(var25 == 3 && !var26.equals("none") && !var26.equals("0")) {
                                    usedHD += Double.parseDouble(var26.substring(0, var26.length() - 1));
                                }
                            }
                        }
                    }
                }
            } catch (Exception var17) {
                logger.debug(var17);
            } finally {
                var20.close();
            }

            double var21 = usedHD / totalHD * 100.0D;
            var24 = new BigDecimal(var21);
            var21 = var24.setScale(2, 4).doubleValue();
            return var21;
        } else {
            long rt = 0L;
            long in = 0L;

            for(char strArray = 65; strArray <= 90; ++strArray) {
                String b1 = strArray + ":/";
                File tmp = new File(b1);
                if(tmp.exists()) {
                    long total = tmp.getTotalSpace();
                    long free = tmp.getFreeSpace();
                    rt += total;
                    in += free;
                }
            }

            Double var22 = Double.valueOf(Double.valueOf(1.0D - (double)in * 1.0D / (double)rt).doubleValue() * 100.0D);
            var24 = new BigDecimal(var22.doubleValue());
            var22 = Double.valueOf(var24.setScale(2, 4).doubleValue());
            return var22.doubleValue();
        }
    }

    private static long[] readCpu(Process proc) {
        long[] retn = new long[2];

        try {
            proc.getOutputStream().close();
            InputStreamReader ex = new InputStreamReader(proc.getInputStream());
            LineNumberReader input = new LineNumberReader(ex);
            String line = input.readLine();
            if(line != null && line.length() >= 10) {
                int capidx = line.indexOf("Caption");
                int cmdidx = line.indexOf("CommandLine");
                int rocidx = line.indexOf("ReadOperationCount");
                int umtidx = line.indexOf("UserModeTime");
                int kmtidx = line.indexOf("KernelModeTime");
                int wocidx = line.indexOf("WriteOperationCount");
                long idletime = 0L;
                long kneltime = 0L;
                long usertime = 0L;

                while((line = input.readLine()) != null) {
                    if(line.length() >= wocidx) {
                        String caption = substring(line, capidx, cmdidx - 1).trim();
                        String cmd = substring(line, cmdidx, kmtidx - 1).trim();
                        if(cmd.indexOf("wmic.exe") < 0) {
                            String s1 = substring(line, kmtidx, rocidx - 1).trim();
                            String s2 = substring(line, umtidx, wocidx - 1).trim();
                            List digitS1 = getDigit(s1);
                            List digitS2 = getDigit(s2);
                            if(!caption.equals("System Idle Process") && !caption.equals("System")) {
                                if(s1.length() > 0 && !((String)digitS1.get(0)).equals("") && digitS1.get(0) != null) {
                                    kneltime += Long.valueOf((String)digitS1.get(0)).longValue();
                                }

                                if(s2.length() > 0 && !((String)digitS2.get(0)).equals("") && digitS2.get(0) != null) {
                                    kneltime += Long.valueOf((String)digitS2.get(0)).longValue();
                                }
                            } else {
                                if(s1.length() > 0 && !((String)digitS1.get(0)).equals("") && digitS1.get(0) != null) {
                                    idletime += Long.valueOf((String)digitS1.get(0)).longValue();
                                }

                                if(s2.length() > 0 && !((String)digitS2.get(0)).equals("") && digitS2.get(0) != null) {
                                    idletime += Long.valueOf((String)digitS2.get(0)).longValue();
                                }
                            }
                        }
                    }
                }

                retn[0] = idletime;
                retn[1] = kneltime + usertime;
                long[] var24 = retn;
                return var24;
            }
        } catch (Exception var33) {
            logger.debug(var33);
            return null;
        } finally {
            try {
                proc.getInputStream().close();
            } catch (Exception var32) {
                logger.debug(var32);
            }

        }

        return null;
    }

    private static List<String> getDigit(String text) {
        ArrayList digitList = new ArrayList();
        digitList.add(text.replaceAll("\\D", ""));
        return digitList;
    }

    private static String substring(String src, int start_idx, int end_idx) {
        byte[] b = src.getBytes();
        String tgt = "";

        for(int i = start_idx; i <= end_idx; ++i) {
            tgt = tgt + (char)b[i];
        }

        return tgt;
    }

    public static void main(String[] args) throws Exception {
        double cpuUsage = getCpuUsage();
        double memUsage = getMemUsage();
        double diskUsage = getDiskUsage();
        System.out.println("cpuUsage:" + cpuUsage);
        System.out.println("memUsage:" + memUsage);
        System.out.println("diskUsage:" + diskUsage);
    }
}
