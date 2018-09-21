package com.ice.task.scheduler.enums;

/**
 * @author ice
 * @Date 2018/9/18 12:12
 */
public enum TaskQueueEnum {
  INIT("init"),
  SIMCARD("simcard"),
  USERINFO("userInfo"),
  SUMMARY("SUMMARY");

  private String queueName;

  TaskQueueEnum(String queueName) {
    this.queueName = queueName;
  }

  public String getQueueName() {
    return queueName;
  }
}
