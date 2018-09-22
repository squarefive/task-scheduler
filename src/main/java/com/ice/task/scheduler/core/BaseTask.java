package com.ice.task.scheduler.core;

import com.google.common.eventbus.EventBus;
import com.ice.task.scheduler.core.event.EventNotifyExecuteTaskMsg;
import java.util.Date;

/**
 * @author:ice
 * @Date: 2018/6/8 09:24
 */
public abstract class BaseTask {

  public int nextPriority;//子级节点的优先级

  public String taskName;//任务名称

  public Integer priority; //优先级

  public String queueName;//队列名称

  public boolean finish; //任务完成？

  public boolean allExecute;

  /**
   * 执行的任务
   */
  public abstract void doTask(Date date) throws Exception;


  public void notifyExecuteTaskMsg(EventBus eventBus, Date date) {
    EventNotifyExecuteTaskMsg msg = new EventNotifyExecuteTaskMsg();
    msg.setDate(date);
    msg.setNextPriority(nextPriority);
    msg.setQueueName(queueName);
    msg.setPriority(priority);
    msg.setTaskName(taskName);
    eventBus.post(msg);
  }

}
