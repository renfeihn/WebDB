/*    */ package org.springframework.base.system.scheduler;
/*    */ 
/*    */ import java.text.ParseException;
/*    */ import org.quartz.SchedulerException;
/*    */ import org.quartz.TriggerKey;
/*    */ 
/*    */ public class QuartzTools
/*    */ {
/*    */   public static void startQuartz(String triggerName, String groupName, String corn, QuartzManager quartzManager, boolean oneTimes)
/*    */     throws SchedulerException, ParseException
/*    */   {
/* 11 */     TriggerKey triggerKey = new TriggerKey(triggerName, groupName);
/*    */   }
/*    */ }

/* Location:           C:\Users\renfei\Desktop\treeDMS-2.3.1\treeDMS-2.3.1\webapps\treesoft\WEB-INF\classes\
 * Qualified Name:     org.springframework.base.system.scheduler.QuartzTools
 * JD-Core Version:    0.6.0
 */