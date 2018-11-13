//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package org.springframework.base.system.web;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import org.springframework.base.common.persistence.Page;
import org.springframework.base.common.web.BaseController;
import org.springframework.base.system.entity.Task;
import org.springframework.base.system.scheduler.QuartzJobFactoryForTask;
import org.springframework.base.system.scheduler.QuartzManager;
import org.springframework.base.system.service.PermissionService;
import org.springframework.base.system.service.TaskService;
import org.springframework.base.system.web.IdsDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping({"system/task"})
public class TaskController extends BaseController {
    @Autowired
    private TaskService taskService;
    @Autowired
    private PermissionService permissionService;
    @Resource
    private QuartzManager quartzManager;

    public TaskController() {
    }

    @RequestMapping(
            value = {"i/task"},
            method = {RequestMethod.GET}
    )
    public String task(Model model) {
        return "system/taskList";
    }

    @RequestMapping(
            value = {"i/taskList"},
            method = {RequestMethod.GET}
    )
    @ResponseBody
    public Map<String, Object> taskList(HttpServletRequest request) throws Exception {
        Page page = this.getPage(request);

        try {
            page = this.taskService.taskList(page);
        } catch (Exception var4) {
            return this.getEasyUIData(page);
        }

        return this.getEasyUIData(page);
    }

    @RequestMapping(
            value = {"i/addTaskForm"},
            method = {RequestMethod.GET}
    )
    public String addTaskForm(Model model) throws Exception {
        List configList = this.permissionService.getAllConfigList();
        model.addAttribute("configList", configList);
        return "system/taskForm";
    }

    @RequestMapping(
            value = {"i/editTaskForm/{id}"},
            method = {RequestMethod.GET}
    )
    public String editTaskForm(@PathVariable("id") String id, Model model) throws Exception {
        Object map = new HashMap();
        List configList = this.permissionService.getAllConfigList();

        try {
            map = this.taskService.getTask(id);
        } catch (Exception var6) {
            ;
        }

        model.addAttribute("configList", configList);
        model.addAttribute("task", map);
        return "system/taskForm";
    }

    @RequestMapping(
            value = {"i/taskUpdate"},
            method = {RequestMethod.POST}
    )
    @ResponseBody
    public Map<String, Object> taskUpdate(@ModelAttribute @RequestBody Task task, Model model) {
        String mess = "";
        String status = "";

        try {
            String map = task.getId();
            String status2 = task.getStatus();
            String cron = task.getCron();
            String state = task.getState();
            if(state.equals("1")) {
                task.setStatus("0");
            }

            this.taskService.taskUpdate(task);
            if(!map.equals("")) {
                if(status2.equals("1")) {
                    Map job = this.taskService.getTaskById2(map);
                    QuartzManager.removeJob(map);
                    this.quartzManager.addJob(map, QuartzJobFactoryForTask.class, cron, job);
                }

                if(state.equals("1")) {
                    QuartzManager.removeJob(map);
                }
            }

            mess = "修改成功";
            status = "success";
        } catch (Exception var10) {
            mess = "error:" + var10.getMessage();
            status = "fail";
        }

        HashMap map1 = new HashMap();
        map1.put("mess", mess);
        map1.put("status", status);
        return map1;
    }

    @RequestMapping(
            value = {"i/deleteTask"},
            method = {RequestMethod.POST}
    )
    @ResponseBody
    public Map<String, Object> deleteTask(@RequestBody IdsDto tem, HttpServletRequest request) {
        String[] ids = tem.getIds();
        String mess = "";
        String status = "";

        try {
            this.taskService.deleteTask(ids);
            String[] var9 = ids;
            int var8 = ids.length;

            for(int var7 = 0; var7 < var8; ++var7) {
                String map = var9[var7];
                QuartzManager.removeJob(map);
                this.taskService.deleteTaskLogByDS(map);
            }

            mess = "删除成功";
            status = "success";
        } catch (Exception var10) {
            mess = var10.getMessage();
            status = "fail";
        }

        HashMap var11 = new HashMap();
        var11.put("mess", mess);
        var11.put("status", status);
        return var11;
    }

    @RequestMapping(
            value = {"i/startTask"},
            method = {RequestMethod.POST}
    )
    @ResponseBody
    public Map<String, Object> startTask(@RequestBody IdsDto tem, HttpServletRequest request) {
        String[] ids = tem.getIds();
        String mess = "";
        String status = "";
        String state = "";
        String taskId = "";

        try {
            List map = this.taskService.getTaskListById(ids);
            Iterator var10 = map.iterator();

            while(var10.hasNext()) {
                Map job = (Map)var10.next();
                taskId = "" + job.get("id");
                state = "" + job.get("state");
                if(state.equals("1")) {
                    throw new Exception("启用状态的任务才能运行！");
                }

                QuartzManager.removeJob(taskId);
                this.quartzManager.addJob(taskId, QuartzJobFactoryForTask.class, "" + job.get("cron"), job);
                this.taskService.taskUpdateStatus(taskId, "1");
            }

            mess = "操作成功!";
            status = "success";
        } catch (Exception var11) {
            System.out.println(var11.getMessage());
            this.taskService.taskUpdateStatus(taskId, "0");
            mess = var11.getMessage();
            status = "fail";
        }

        HashMap map1 = new HashMap();
        map1.put("mess", mess);
        map1.put("status", status);
        return map1;
    }

    @RequestMapping(
            value = {"i/startTaskOne"},
            method = {RequestMethod.POST}
    )
    @ResponseBody
    public Map<String, Object> startTaskOne(@RequestBody IdsDto tem, HttpServletRequest request) {
        String[] ids = tem.getIds();
        String mess = "";
        String status = "";
        String state = "";
        String taskId = "";

        try {
            List map = this.taskService.getTaskListById(ids);
            Iterator var10 = map.iterator();

            while(var10.hasNext()) {
                Map job = (Map)var10.next();
                taskId = "" + job.get("id");
                this.quartzManager.addJobOne(taskId, QuartzJobFactoryForTask.class, job);
            }

            mess = "操作成功!";
            status = "success";
        } catch (Exception var11) {
            System.out.println(var11.getMessage());
            this.taskService.taskUpdateStatus(taskId, "0");
            mess = var11.getMessage();
            status = "fail";
        }

        HashMap map1 = new HashMap();
        map1.put("mess", mess);
        map1.put("status", status);
        return map1;
    }

    @RequestMapping(
            value = {"i/stopTask"},
            method = {RequestMethod.POST}
    )
    @ResponseBody
    public Map<String, Object> stopTask(@RequestBody IdsDto tem, HttpServletRequest request) {
        String[] ids = tem.getIds();
        String mess = "";
        String status = "";
        String taskId = "";

        try {
            List map = this.taskService.getTaskListById(ids);
            Iterator var9 = map.iterator();

            while(var9.hasNext()) {
                Map job = (Map)var9.next();
                taskId = "" + job.get("id");
                QuartzManager.removeJob(taskId);
                this.taskService.taskUpdateStatus(taskId, "0");
            }

            mess = "操作成功!";
            status = "success";
        } catch (Exception var10) {
            System.out.println(var10.getMessage());
            this.taskService.taskUpdateStatus(taskId, "0");
            mess = var10.getMessage();
            status = "fail";
        }

        HashMap map1 = new HashMap();
        map1.put("mess", mess);
        map1.put("status", status);
        return map1;
    }

    @RequestMapping(
            value = {"i/taskLogForm/{taskId}"},
            method = {RequestMethod.GET}
    )
    public String taskLogForm(@PathVariable("taskId") String taskId, HttpServletRequest request) throws Exception {
        request.setAttribute("taskId", taskId);
        return "system/taskLogForm";
    }

    @RequestMapping(
            value = {"i/taskLogList/{taskId}"},
            method = {RequestMethod.GET}
    )
    @ResponseBody
    public Map<String, Object> taskLogList(@PathVariable("taskId") String taskId, HttpServletRequest request) throws Exception {
        Page page = this.getPage(request);

        try {
            page = this.taskService.taskLogList(page, taskId);
        } catch (Exception var5) {
            return this.getEasyUIData(page);
        }

        return this.getEasyUIData(page);
    }

    @RequestMapping(
            value = {"i/deleteTaskLog"},
            method = {RequestMethod.POST}
    )
    @ResponseBody
    public Map<String, Object> deleteTaskLog(@RequestBody IdsDto tem, HttpServletRequest request) {
        String[] ids = tem.getIds();
        String mess = "";
        String status = "";

        try {
            this.taskService.deleteTaskLog(ids);
            mess = "删除成功";
            status = "success";
        } catch (Exception var7) {
            mess = var7.getMessage();
            status = "fail";
        }

        HashMap map = new HashMap();
        map.put("mess", mess);
        map.put("status", status);
        return map;
    }
}
