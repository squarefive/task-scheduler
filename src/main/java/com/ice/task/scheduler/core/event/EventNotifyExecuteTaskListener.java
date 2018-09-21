package com.ice.task.scheduler.core.event;

import com.cootf.resim.edw.bos.dw.core.ScheduleTask;
import com.cootf.resim.edw.bos.dw.core.TaskExecuteCondition;
import com.cootf.resim.edw.bos.ods.service.SysConfigService;
import com.cootf.resim.edw.bos.service.entities.SysConfig;
import com.google.common.eventbus.Subscribe;
import com.ice.misc.DateUtils;
import java.util.Date;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @author ice
 * @Date 2018/9/17 17:38
 */
@Component
public class EventNotifyExecuteTaskListener {

  private static final Logger logger = LoggerFactory
      .getLogger(EventNotifyExecuteTaskListener.class);

  @Autowired
  private ScheduleTask scheduleTask;

  @Autowired
  private TaskExecuteCondition condition;


  @Subscribe
  public void executeTask(EventNotifyExecuteTaskMsg msg) {
    boolean success = condition.executeTask(msg.getPriority(), msg.getQueueName());
    if (success) {
      Integer nextPriority = scheduleTask.nextPriority(msg.getPriority());
      if (nextPriority != null) {
        scheduleTask.executeTask(nextPriority, msg.getQueueName(), msg.getDate());//执行下一个队列
      } else {//执行完成，充值任务标识
        scheduleTask.finishTask();
        this.updateSysConfig(DateUtils.yyyymmdd(msg.getDate()));
        logger.info("CoreTask end!");
      }
    }
  }


}
