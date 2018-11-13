//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package org.springframework.base.system.scheduler;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import org.apache.log4j.Logger;
import org.quartz.DisallowConcurrentExecution;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.quartz.PersistJobDataAfterExecution;
import org.springframework.base.common.utils.DateUtils;
import org.springframework.base.system.service.PermissionService;
import org.springframework.context.ApplicationContext;
import org.springframework.web.context.WebApplicationContext;

@DisallowConcurrentExecution
@PersistJobDataAfterExecution
public class QuartzJobFactory implements Job {
    private static final Logger log = Logger.getLogger("");
    private PermissionService permissionService;
    private ApplicationContext webApplicationContext;

    public QuartzJobFactory() {
    }

    public void execute(JobExecutionContext context) throws JobExecutionException {
        log.info("数据交换任务运行开始-------- start --------");
        String dataSynchronizeId = "";

        try {
            Map e = (Map)context.getMergedJobDataMap().get("jobMessageMap");
            log.info("数据交换任务运行时具体参数:" + e.get("id") + "," + e.get("name") + ", " + e.get("targetConfigId") + ", " + e.get("targetConfigId") + " ," + DateUtils.getDateTimeString(new Date()));
            dataSynchronizeId = "" + e.get("id");
            String souceConfigId = "" + e.get("souceConfigId");
            String souceDataBase = "" + e.get("souceDataBase");
            String sql = "" + e.get("doSql");
            String targetConfigId = "" + e.get("targetConfigId");
            String targetDataBase = "" + e.get("targetDataBase");
            String targetTable = "" + e.get("targetTable");
            String operation = "" + e.get("operation");
            String qualification = "" + e.get("qualification");
            Map map = this.permissionService.getConfig(souceConfigId);
            String databaseType = (String)map.get("databaseType");
            Map map2 = this.permissionService.getConfig(targetConfigId);
            String targerDatabaseType = (String)map2.get("databaseType");
            Object dataList = new ArrayList();
            if(databaseType.equals("MySql")) {
                int rowCount = this.permissionService.executeQueryForCount(sql, souceDataBase, souceConfigId);
                System.out.println("数据交换总行数 = " + rowCount);
                dataList = this.permissionService.selectAllDataFromSQLForMysql(souceDataBase, souceConfigId, sql);
            }

            if(databaseType.equals("MariaDB")) {
                dataList = this.permissionService.selectAllDataFromSQLForMysql(souceDataBase, souceConfigId, sql);
            }

            if(databaseType.equals("Oracle")) {
                dataList = this.permissionService.selectAllDataFromSQLForOracle(souceDataBase, souceConfigId, sql);
            }

            if(databaseType.equals("PostgreSQL")) {
                dataList = this.permissionService.selectAllDataFromSQLForPostgreSQL(souceDataBase, souceConfigId, sql);
            }

            if(databaseType.equals("MSSQL")) {
                dataList = this.permissionService.selectAllDataFromSQLForMSSQL(souceDataBase, souceConfigId, sql);
            }

            if(databaseType.equals("MongoDB")) {
                dataList = this.permissionService.selectAllDataFromSQLForMongoDB(souceDataBase, souceConfigId, sql);
            }

            if(databaseType.equals("Hive2")) {
                dataList = this.permissionService.selectAllDataFromSQLForHive2(souceDataBase, souceConfigId, sql);
            }

            if(operation.equals("0")) {
                if(targerDatabaseType.equals("MySql")) {
                    this.permissionService.insertByDataListForMySQL((List)dataList, targetDataBase, targetTable, targetConfigId);
                }

                if(targerDatabaseType.equals("MariaDB")) {
                    this.permissionService.insertByDataListForMySQL((List)dataList, targetDataBase, targetTable, targetConfigId);
                }

                if(targerDatabaseType.equals("Oracle")) {
                    this.permissionService.insertByDataListForOracle((List)dataList, targetDataBase, targetTable, targetConfigId);
                }

                if(targerDatabaseType.equals("PostgreSQL")) {
                    this.permissionService.insertByDataListForOracle((List)dataList, targetDataBase, targetTable, targetConfigId);
                }

                if(targerDatabaseType.equals("MSSQL")) {
                    this.permissionService.insertByDataListForMSSQL((List)dataList, targetDataBase, targetTable, targetConfigId);
                }

                if(targerDatabaseType.equals("MongoDB")) {
                    this.permissionService.insertByDataListForMongoDB((List)dataList, targetDataBase, targetTable, targetConfigId);
                }

                if(targerDatabaseType.equals("Hive2")) {
                    throw new Exception("Hive不支持该操作。");
                }
            }

            if(operation.equals("1")) {
                if(targerDatabaseType.equals("MySql")) {
                    this.permissionService.updateByDataListForMySQL((List)dataList, targetDataBase, targetTable, targetConfigId, qualification);
                }

                if(targerDatabaseType.equals("MariaDB")) {
                    this.permissionService.updateByDataListForMySQL((List)dataList, targetDataBase, targetTable, targetConfigId, qualification);
                }

                if(targerDatabaseType.equals("Oracle")) {
                    this.permissionService.updateByDataListForOracle((List)dataList, targetDataBase, targetTable, targetConfigId, qualification);
                }

                if(targerDatabaseType.equals("PostgreSQL")) {
                    this.permissionService.updateByDataListForOracle((List)dataList, targetDataBase, targetTable, targetConfigId, qualification);
                }

                if(targerDatabaseType.equals("MSSQL")) {
                    this.permissionService.updateByDataListForMSSQL((List)dataList, targetDataBase, targetTable, targetConfigId, qualification);
                }

                if(targerDatabaseType.equals("MongoDB")) {
                    this.permissionService.updateByDataListForMongoDB((List)dataList, targetDataBase, targetTable, targetConfigId, qualification);
                }

                if(targerDatabaseType.equals("Hive2")) {
                    throw new Exception("Hive不支持该操作。");
                }
            }

            if(operation.equals("2")) {
                if(targerDatabaseType.equals("MySql")) {
                    this.permissionService.insertOrUpdateByDataListForMySQL((List)dataList, targetDataBase, targetTable, targetConfigId, qualification);
                }

                if(targerDatabaseType.equals("MariaDB")) {
                    this.permissionService.updateByDataListForMySQL((List)dataList, targetDataBase, targetTable, targetConfigId, qualification);
                }

                if(targerDatabaseType.equals("Oracle")) {
                    this.permissionService.updateByDataListForOracle((List)dataList, targetDataBase, targetTable, targetConfigId, qualification);
                }

                if(targerDatabaseType.equals("PostgreSQL")) {
                    this.permissionService.updateByDataListForOracle((List)dataList, targetDataBase, targetTable, targetConfigId, qualification);
                }

                if(targerDatabaseType.equals("MSSQL")) {
                    this.permissionService.updateByDataListForMSSQL((List)dataList, targetDataBase, targetTable, targetConfigId, qualification);
                }

                if(targerDatabaseType.equals("MongoDB")) {
                    this.permissionService.updateByDataListForMongoDB((List)dataList, targetDataBase, targetTable, targetConfigId, qualification);
                }

                if(targerDatabaseType.equals("Hive2")) {
                    throw new Exception("Hive不支持该操作。");
                }
            }

            operation.equals("3");
            if(operation.equals("4")) {
                if(targerDatabaseType.equals("MySql")) {
                    this.permissionService.deleteByDataListForMySQL((List)dataList, targetDataBase, targetTable, targetConfigId, qualification);
                }

                if(targerDatabaseType.equals("MariaDB")) {
                    this.permissionService.deleteByDataListForMySQL((List)dataList, targetDataBase, targetTable, targetConfigId, qualification);
                }

                if(targerDatabaseType.equals("Oracle")) {
                    this.permissionService.deleteByDataListForOracle((List)dataList, targetDataBase, targetTable, targetConfigId, qualification);
                }

                if(targerDatabaseType.equals("PostgreSQL")) {
                    this.permissionService.deleteByDataListForOracle((List)dataList, targetDataBase, targetTable, targetConfigId, qualification);
                }

                if(targerDatabaseType.equals("MSSQL")) {
                    this.permissionService.deleteByDataListForMSSQL((List)dataList, targetDataBase, targetTable, targetConfigId, qualification);
                }

                if(targerDatabaseType.equals("MongoDB")) {
                    this.permissionService.deleteByDataListForMongoDB((List)dataList, targetDataBase, targetTable, targetConfigId, qualification);
                }

                if(targerDatabaseType.equals("Hive2")) {
                    throw new Exception("Hive不支持该操作。");
                }
            }

            this.permissionService.dataSynchronizeLogSave("1", "运行成功!", dataSynchronizeId);
        } catch (Exception var18) {
            System.out.println("数据交换任务运行异常," + var18.getMessage());
            log.info("数据交换任务运行异常," + var18);
            this.permissionService.dataSynchronizeLogSave("0", "运行失败!" + var18.getMessage(), dataSynchronizeId);
        }

        log.info("数据交换任务运行结束-------- end --------");
    }

    public void setPermissionService(PermissionService permissionService) {
        this.permissionService = permissionService;
    }

    public void setWebApplicationContext(WebApplicationContext webApplicationContext) {
        this.webApplicationContext = webApplicationContext;
    }
}
