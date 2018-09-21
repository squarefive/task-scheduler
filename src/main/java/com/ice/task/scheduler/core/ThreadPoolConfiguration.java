package com.ice.task.scheduler.core;

import java.util.concurrent.ThreadPoolExecutor;
import javax.annotation.PreDestroy;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

/**
 * Spring 线程池配置
 *
 * @author:ice
 * @Date: 2018/6/7 15:16
 **/
@Configuration
public class ThreadPoolConfiguration {

  private ThreadPoolTaskExecutor threadPoolTaskExecutor;

  @Bean
  public ThreadPoolTaskExecutor threadPoolTaskExecutor() {
    this.threadPoolTaskExecutor = new ThreadPoolTaskExecutor();
    this.threadPoolTaskExecutor.setMaxPoolSize(100);
    this.threadPoolTaskExecutor.setCorePoolSize(20);
    this.threadPoolTaskExecutor.setQueueCapacity(2000);
    this.threadPoolTaskExecutor.setKeepAliveSeconds(100);
    this.threadPoolTaskExecutor
        .setRejectedExecutionHandler(new ThreadPoolExecutor.CallerRunsPolicy());
    return this.threadPoolTaskExecutor;
  }

  @PreDestroy
  public void destroy() {
    if (threadPoolTaskExecutor != null) {
      threadPoolTaskExecutor.shutdown();
    }
  }
}
