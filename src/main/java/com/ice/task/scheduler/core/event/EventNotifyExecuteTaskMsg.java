package com.ice.task.scheduler.core.event;

import java.util.Date;

/**
 * @author ice
 * @Date 2018/9/17 17:37
 */
public class EventNotifyExecuteTaskMsg {

  private Integer priority;

  private String taskName;

  private String queueName;

  private Integer nextPriority;

  private Date date;

  public EventNotifyExecuteTaskMsg() {

  }

  public EventNotifyExecuteTaskMsg(Integer priority, String taskName, String queueName,
      Integer nextPriority, Date date) {
    this.priority = priority;
    this.taskName = taskName;
    this.queueName = queueName;
    this.nextPriority = nextPriority;
    this.date = date;
  }

  public Integer getPriority() {
    return priority;
  }

  public void setPriority(Integer priority) {
    this.priority = priority;
  }

  public String getTaskName() {
    return taskName;
  }

  public void setTaskName(String taskName) {
    this.taskName = taskName;
  }

  public Integer getNextPriority() {
    return nextPriority;
  }

  public void setNextPriority(Integer nextPriority) {
    this.nextPriority = nextPriority;
  }

  public String getQueueName() {
    return queueName;
  }

  public void setQueueName(String queueName) {
    this.queueName = queueName;
  }

  public Date getDate() {
    return date;
  }

  public void setDate(Date date) {
    this.date = date;
  }
}
