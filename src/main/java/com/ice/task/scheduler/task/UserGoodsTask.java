package com.ice.task.scheduler.task;

import com.google.common.eventbus.EventBus;
import com.ice.task.scheduler.annotation.TaskAnnotation;
import com.ice.task.scheduler.core.BaseTask;
import com.ice.task.scheduler.enums.TaskQueueEnum;
import java.util.Date;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @author:ice
 * @Date: 2018/6/11 19:59
 */
@Component
@TaskAnnotation(priority = 5, taskName = "UserGoodsTask", queueName = TaskQueueEnum.USERINFO)
public class UserGoodsTask extends BaseTask {

  private static final Logger logger = LoggerFactory.getLogger(UserGoodsTask.class);


  @Autowired
  private EventBus eventBus;

  @Override
  public void doTask(Date date) throws Exception {
    logger.info("task {} is priority {} executing!", this.taskName, this.priority);

    this.finish = true;
    this.notifyExecuteTaskMsg(eventBus, date);
    logger.info("{} execute end!", this.taskName);
  }

}
