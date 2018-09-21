package com.ice.task.scheduler.core.event;

import com.google.common.eventbus.EventBus;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author ice
 * @Date 2018/9/15 11:31
 */
@Configuration
public class EventBusConfig {

  @Bean
  public EventBus eventBus() {
    return new EventBus();
  }
}
