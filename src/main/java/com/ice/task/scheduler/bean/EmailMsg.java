package com.ice.task.scheduler.bean;

import java.util.Date;

/**
 * @author ice
 * @Date 2018/9/13 11:57
 */
public class EmailMsg {

  private Date date;//消息产生时间
  private String error;//错误信息

  public EmailMsg() {

  }

  public EmailMsg(Date date, String error) {
    this.date = date;
    this.error = error;
  }

  public Date getDate() {
    return date;
  }

  public void setDate(Date date) {
    this.date = date;
  }

  public String getError() {
    return error;
  }

  public void setError(String error) {
    this.error = error;
  }
}
