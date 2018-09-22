package com.ice.task.scheduler.core;

import com.ice.misc.DateUtils;
import java.util.Date;
import java.util.concurrent.atomic.AtomicBoolean;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.PropertySource;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

/**
 * @author:ice
 * @Date: 2018/6/8 10:19
 */
@Component
public class CoreTask {

  private static final Logger log = LoggerFactory.getLogger(CoreTask.class);

  @Autowired
  private ScheduleJob scheduleJob;

  private AtomicBoolean first = new AtomicBoolean(true);

  @Async
  //每天凌晨一点开始执行任务
  @Scheduled(cron = "0 */1 * * * ?")
  public void sheduled() {
    log.info("CoreTask start!");
    Date date1 = new Date();
    if (first.get()) {
      scheduleJob.init();//初始化项目
    }
    first.set(false);
    log.info("core 输出");
    scheduleJob.run(date1);//项目运行
  }


}
