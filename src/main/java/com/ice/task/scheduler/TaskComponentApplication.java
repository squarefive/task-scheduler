package com.ice.task.scheduler;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.data.mongo.MongoDataAutoConfiguration;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.autoconfigure.mongo.MongoAutoConfiguration;
import org.springframework.scheduling.annotation.EnableScheduling;

/**
 * @author:ice
 * @Date: 2018/6/1 12:56
 */
@EnableScheduling
@SpringBootApplication(exclude = {MongoAutoConfiguration.class, MongoDataAutoConfiguration.class,
    DataSourceAutoConfiguration.class})
public class TaskComponentApplication {

  public static void main(String[] args) {
    SpringApplication.run(TaskComponentApplication.class, args);
  }
}
