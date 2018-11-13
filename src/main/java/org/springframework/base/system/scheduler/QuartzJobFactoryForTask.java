//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package org.springframework.base.system.scheduler;

import java.util.Date;
import java.util.Map;
import org.apache.log4j.Logger;
import org.quartz.DisallowConcurrentExecution;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.quartz.PersistJobDataAfterExecution;
import org.springframework.base.common.utils.DateUtils;
import org.springframework.base.system.service.PermissionService;
import org.springframework.base.system.service.TaskService;
import org.springframework.context.ApplicationContext;
import org.springframework.web.context.WebApplicationContext;

@DisallowConcurrentExecution
@PersistJobDataAfterExecution
public class QuartzJobFactoryForTask implements Job {
    private static final Logger log = Logger.getLogger("");
    private TaskService taskService;
    private PermissionService permissionService;
    private ApplicationContext webApplicationContext;

    public QuartzJobFactoryForTask() {
    }

    public void execute(JobExecutionContext context) throws JobExecutionException {
        log.info("定时任务运行开始-------- start --------");
        String dataSynchronizeId = "";

        try {
            Map e = (Map)context.getMergedJobDataMap().get("jobMessageMap");
            log.info("定时任务运行时具体参数:" + e.get("id") + "," + e.get("name") + ", " + e.get("targetConfigId") + ", " + e.get("targetConfigId") + " ," + DateUtils.getDateTimeString(new Date()));
            dataSynchronizeId = "" + e.get("id");
            String souceConfigId = "" + e.get("souceConfigId");
            String souceDataBase = "" + e.get("souceDataBase");
            String sql = "" + e.get("doSql");
            this.permissionService.executeSqlNotRes(sql, souceDataBase, souceConfigId);
            this.taskService.taskLogSave("1", "运行成功!", dataSynchronizeId);
        } catch (Exception var7) {
            System.out.println("定时任务运行异常," + var7.getMessage());
            log.info("定时任务运行异常," + var7);
            this.permissionService.dataSynchronizeLogSave("0", "运行失败!" + var7.getMessage(), dataSynchronizeId);
        }

        log.info("定时任务运行结束-------- end --------");
    }

    public void setPermissionService(PermissionService permissionService) {
        this.permissionService = permissionService;
    }

    public void setTaskService(TaskService taskService) {
        this.taskService = taskService;
    }

    public void setWebApplicationContext(WebApplicationContext webApplicationContext) {
        this.webApplicationContext = webApplicationContext;
    }
}
