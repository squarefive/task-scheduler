package com.ice.task.scheduler.core;

import java.util.concurrent.Executor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

/**
 * @author:ice
 * @Date: 2018/5/26 09:40
 */
@Configuration
@EnableAsync
public class AsyncConfig {

  private int corePoolSize = 10;//线程池大小
  private int maxPoolSize = 50;//线程池最大容量
  private int queueCapacity = 20;//队列长度

  @Bean
  public Executor taskExecutor() {
    ThreadPoolTaskExecutor taskExecutor = new ThreadPoolTaskExecutor();
    taskExecutor.setCorePoolSize(corePoolSize);
    taskExecutor.setMaxPoolSize(maxPoolSize);
    taskExecutor.setQueueCapacity(queueCapacity);
    taskExecutor.initialize();
    return taskExecutor;
  }
}
