//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package org.springframework.base.system.scheduler;

import java.util.Iterator;
import java.util.List;
import java.util.Map;
import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import org.apache.log4j.Logger;
import org.quartz.CronScheduleBuilder;
import org.quartz.CronTrigger;
import org.quartz.JobBuilder;
import org.quartz.JobDataMap;
import org.quartz.JobDetail;
import org.quartz.JobKey;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.SchedulerFactory;
import org.quartz.SimpleScheduleBuilder;
import org.quartz.Trigger;
import org.quartz.TriggerBuilder;
import org.quartz.TriggerKey;
import org.quartz.impl.StdSchedulerFactory;
import org.springframework.base.system.scheduler.QuartzJobFactory;
import org.springframework.base.system.scheduler.QuartzJobFactoryForTask;
import org.springframework.base.system.service.PermissionService;
import org.springframework.base.system.service.TaskService;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

@Component("quartzManager")
public class QuartzManager implements ApplicationContextAware {
    private static SchedulerFactory gSchedulerFactory = new StdSchedulerFactory();
    private static String JOB_GROUP_NAME = "MY_JOBGROUP_NAME";
    private static String TRIGGER_GROUP_NAME = "MY_TRIGGERGROUP_NAME";
    private static final Logger log = Logger.getLogger(QuartzManager.class);
    @Resource
    private PermissionService permissionService;
    @Resource
    private TaskService taskService;
    private ApplicationContext webApplicationContext;

    public QuartzManager() {
    }

    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.webApplicationContext = applicationContext;
    }

    @PostConstruct
    public void init() {
        String dataSynchronizeId = "";
        String taskId = "";

        try {
            List e = this.permissionService.getDataSynchronizeList2("0");
            List taskList = this.taskService.getTaskList2("0");
            Iterator var6 = e.iterator();

            Map job;
            while(var6.hasNext()) {
                job = (Map)var6.next();
                dataSynchronizeId = "" + job.get("id");
                this.addJob(dataSynchronizeId, QuartzJobFactory.class, "" + job.get("cron"), job);
                this.permissionService.dataSynchronizeUpdateStatus(dataSynchronizeId, "1");
            }

            var6 = taskList.iterator();

            while(var6.hasNext()) {
                job = (Map)var6.next();
                taskId = "" + job.get("id");
                this.addJob(taskId, QuartzJobFactoryForTask.class, "" + job.get("cron"), job);
                this.taskService.taskUpdateStatus(taskId, "1");
            }
        } catch (Exception var7) {
            log.error("quartz scheduler 应用启动时, 启动任务失败;" + var7.toString());
            var7.printStackTrace();
        }

    }

    public void addJob(String jobName, Class cls, String time, Object jobMessageMap) {
        try {
            Scheduler e = gSchedulerFactory.getScheduler();
            JobDetail job = JobBuilder.newJob(cls).withIdentity(jobName, JOB_GROUP_NAME).build();
            JobDataMap jobMap = new JobDataMap();
            jobMap.put("taskService", this.taskService);
            jobMap.put("permissionService", this.permissionService);
            jobMap.put("webApplicationContext", this.webApplicationContext);
            job.getJobDataMap().put("jobMessageMap", jobMessageMap);
            CronScheduleBuilder scheduleBuilder = CronScheduleBuilder.cronSchedule(time);
            Trigger trigger = TriggerBuilder.newTrigger().usingJobData(jobMap).withIdentity(jobName, TRIGGER_GROUP_NAME).withSchedule(scheduleBuilder).build();
            e.scheduleJob(job, trigger);
            if(!e.isShutdown()) {
                e.start();
            }

        } catch (Exception var10) {
            System.out.println("添加一个定时任务出错！" + var10.getMessage());
            throw new RuntimeException(var10);
        }
    }

    public void addJob(String jobName, String jobGroupName, String triggerName, String triggerGroupName, Class jobClass, String time) {
        try {
            Scheduler e = gSchedulerFactory.getScheduler();
            JobDetail job = JobBuilder.newJob(jobClass).withIdentity(jobName, jobGroupName).build();
            JobDataMap jobMap = new JobDataMap();
            jobMap.put("taskService", this.taskService);
            jobMap.put("permissionService", this.permissionService);
            jobMap.put("webApplicationContext", this.webApplicationContext);
            CronScheduleBuilder scheduleBuilder = CronScheduleBuilder.cronSchedule(time);
            Trigger trigger = TriggerBuilder.newTrigger().usingJobData(jobMap).withIdentity(triggerName, triggerGroupName).withSchedule(scheduleBuilder).build();
            e.scheduleJob(job, trigger);
            if(!e.isShutdown()) {
                e.start();
            }

        } catch (Exception var12) {
            throw new RuntimeException(var12);
        }
    }

    public void addJobOne(String jobName, Class cls, Object jobMessageMap) {
        try {
            Scheduler e = gSchedulerFactory.getScheduler();
            JobDetail job = JobBuilder.newJob(cls).withIdentity(jobName, JOB_GROUP_NAME).build();
            JobDataMap jobMap = new JobDataMap();
            jobMap.put("taskService", this.taskService);
            jobMap.put("permissionService", this.permissionService);
            jobMap.put("webApplicationContext", this.webApplicationContext);
            job.getJobDataMap().put("jobMessageMap", jobMessageMap);
            Trigger trigger = TriggerBuilder.newTrigger().usingJobData(jobMap).withIdentity(jobName, TRIGGER_GROUP_NAME).withSchedule(SimpleScheduleBuilder.simpleSchedule().withIntervalInSeconds(3).withRepeatCount(0)).build();
            e.scheduleJob(job, trigger);
            if(!e.isShutdown()) {
                e.start();
            }

        } catch (Exception var8) {
            System.out.println("添加一个定时任务出错！" + var8.getMessage());
            throw new RuntimeException(var8);
        }
    }

    public static void modifyJobTime(String jobName, String time) {
        TriggerKey triggerKey = TriggerKey.triggerKey(jobName, TRIGGER_GROUP_NAME);

        try {
            Scheduler e = gSchedulerFactory.getScheduler();
            CronTrigger trigger = (CronTrigger)e.getTrigger(triggerKey);
            if(trigger != null) {
                String oldTime = trigger.getCronExpression();
                if(!oldTime.equalsIgnoreCase(time)) {
                    CronScheduleBuilder scheduleBuilder = CronScheduleBuilder.cronSchedule(time);
                    trigger = (CronTrigger)trigger.getTriggerBuilder().withIdentity(triggerKey).withSchedule(scheduleBuilder).build();
                    e.rescheduleJob(triggerKey, trigger);
                }

            }
        } catch (Exception var7) {
            throw new RuntimeException(var7);
        }
    }

    public static void modifyJobTime(String triggerName, String triggerGroupName, String time) {
        TriggerKey triggerKey = TriggerKey.triggerKey(triggerName, triggerGroupName);

        try {
            Scheduler e = gSchedulerFactory.getScheduler();
            CronTrigger trigger = (CronTrigger)e.getTrigger(triggerKey);
            if(trigger != null) {
                String oldTime = trigger.getCronExpression();
                if(!oldTime.equalsIgnoreCase(time)) {
                    CronScheduleBuilder scheduleBuilder = CronScheduleBuilder.cronSchedule(time);
                    trigger = (CronTrigger)trigger.getTriggerBuilder().withIdentity(triggerKey).withSchedule(scheduleBuilder).build();
                    e.resumeTrigger(triggerKey);
                }

            }
        } catch (Exception var8) {
            throw new RuntimeException(var8);
        }
    }

    public static void removeJob(String jobName) {
        TriggerKey triggerKey = TriggerKey.triggerKey(jobName, TRIGGER_GROUP_NAME);
        JobKey jobKey = JobKey.jobKey(jobName, JOB_GROUP_NAME);

        try {
            Scheduler e = gSchedulerFactory.getScheduler();
            Trigger trigger = e.getTrigger(triggerKey);
            if(trigger != null) {
                e.pauseTrigger(triggerKey);
                e.unscheduleJob(triggerKey);
                e.deleteJob(jobKey);
            }
        } catch (Exception var5) {
            throw new RuntimeException(var5);
        }
    }

    public static void removeJob(String jobName, String jobGroupName, String triggerName, String triggerGroupName) {
        TriggerKey triggerKey = TriggerKey.triggerKey(jobName, triggerGroupName);
        JobKey jobKey = JobKey.jobKey(jobName, jobGroupName);

        try {
            Scheduler e = gSchedulerFactory.getScheduler();
            e.pauseTrigger(triggerKey);
            e.unscheduleJob(triggerKey);
            e.deleteJob(jobKey);
        } catch (Exception var7) {
            throw new RuntimeException(var7);
        }
    }

    public static void pauseJob(String jobName, String jobGroupName) {
        JobKey jobKey = JobKey.jobKey(jobName, jobName);

        try {
            Scheduler e = gSchedulerFactory.getScheduler();
            e.pauseJob(jobKey);
        } catch (SchedulerException var4) {
            var4.printStackTrace();
        }

    }

    public static void pauseJob(String jobName) {
        JobKey jobKey = JobKey.jobKey(jobName, JOB_GROUP_NAME);

        try {
            Scheduler e = gSchedulerFactory.getScheduler();
            e.pauseJob(jobKey);
        } catch (SchedulerException var3) {
            var3.printStackTrace();
        }

    }

    public static void startJobs() {
        try {
            Scheduler e = gSchedulerFactory.getScheduler();
            e.start();
        } catch (Exception var1) {
            throw new RuntimeException(var1);
        }
    }

    public static void shutdownJobs() {
        try {
            Scheduler e = gSchedulerFactory.getScheduler();
            if(!e.isShutdown()) {
                e.shutdown();
            }

        } catch (Exception var1) {
            throw new RuntimeException(var1);
        }
    }
}
