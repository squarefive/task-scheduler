package com.ice.task.scheduler.core;


import java.util.Date;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @author:ice
 * @Date: 2018/6/8 10:15
 */
@Component
public class ScheduleJob {

  private static final Logger logger = LoggerFactory.getLogger(ScheduleJob.class);

  @Autowired
  private ScheduleTask scheduleTask;

  @Autowired
  private TaskExecuteCondition condition;


  public void init() {
    logger.info("初始化任务");
    condition.init(scheduleTask.tasks);//初始化下个队列执行条件

  }

  //启动任务执行
  public void run(Date date) {
    scheduleTask.run1(date);
  }

  public void finish() {
    scheduleTask.finishTask();
    logger.info("任务结束，重置标识");
  }

}
