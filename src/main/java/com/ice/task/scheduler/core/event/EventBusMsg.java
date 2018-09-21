package com.ice.task.scheduler.core.event;

/**
 * @author ice
 * @Date 2018/9/15 11:05
 */
public class EventBusMsg {

  /**
   * 事件类型
   */
  public enum EventType {
    EMAIL
  }

  private String eventName;//事件名称
  private long lazy;//延迟执行,秒


  public String getEventName() {
    return eventName;
  }

  public void setEventName(String eventName) {
    this.eventName = eventName;
  }

  public long getLazy() {
    return lazy;
  }

  public void setLazy(long lazy) {
    this.lazy = lazy;
  }
}
