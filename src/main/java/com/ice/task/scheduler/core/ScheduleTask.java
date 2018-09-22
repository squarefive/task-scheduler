package com.ice.task.scheduler.core;

import com.ice.misc.Misc;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.Enumeration;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Component;

/**
 * @author:ice
 * @Date: 2018/6/8 09:33
 */
@Component
public class ScheduleTask {

  private static final Logger logger = LoggerFactory.getLogger(ScheduleTask.class);


  public ConcurrentHashMap<Integer/* 优先级. */, List<BaseTask>/* 任务集合. */> tasks = new ConcurrentHashMap<>();

  @Autowired
  private ThreadPoolTaskExecutor executor;

  /**
   * 任务的执行
   */
  public void run(Date date) {
    Enumeration<Integer> keys = tasks.keys();
    List<Integer> prioritys = new ArrayList<>();
    while (keys.hasMoreElements()) {
      prioritys.add(keys.nextElement());
    }
    Collections.sort(prioritys);//升序
    for (Integer priority : prioritys) {
      List<BaseTask> taskList = tasks.get(priority);
      if (taskList.isEmpty()) {
        continue;
      }
      logger.info("execute priority {} task ", taskList.get(0).priority);
      for (BaseTask task : taskList) {
        executor.execute(() -> {
          try {
            task.doTask(date);
          } catch (Exception e) {
            e.printStackTrace();
          }
        });//线程中执行任务
      }
      while (true) {
        boolean finish = true;
        for (BaseTask t : taskList) {
          if (!t.finish) {
            finish = false;
          }
        }
        if (finish) {//当前任务都执行完毕
          break;
        }
        Misc.sleep(1000);
      }
      Misc.sleep(1000);
    }
  }

  public void run1(Date date) {
    Enumeration<Integer> keys = tasks.keys();
    List<Integer> prioritys = new ArrayList<>();
    while (keys.hasMoreElements()) {
      prioritys.add(keys.nextElement());
    }
    Collections.sort(prioritys);//升序
    Integer priority = prioritys.get(0);
    executeTask(priority, date);//执行第一行的任务.
  }

  /**
   * 获取下个队列的优先级
   * @param priority
   * @return
   */
  public Integer nextPriority(Integer priority) {
    Enumeration<Integer> keys = tasks.keys();
    List<Integer> prioritys = new ArrayList<>();
    while (keys.hasMoreElements()) {
      prioritys.add(keys.nextElement());
    }
    Collections.sort(prioritys);//升序
    for (Integer pri : prioritys) {
      if (priority < pri) {
        return pri;
      }
    }
    return null;//没有下一个队列
  }

  public void executeTask(Integer priority, Date date) {
    List<BaseTask> list = tasks.get(priority);
    if (list.isEmpty()) {
      return;
    }
    for (BaseTask task : list) {
      execute(task, date);
    }
  }

  public void executeTask(Integer priority, String queueName, Date date) {
    List<BaseTask> list = this.tasks.get(priority);
    list = list.stream().filter(task -> queueName.equals(task.queueName))
        .collect(Collectors.toList());
    if (list.isEmpty()) {
      return;
    }
    for (BaseTask task : list) {
      execute(task, date);
    }
  }

  public void execute(BaseTask task, Date date) {
    executor.execute(() -> {
      try {
        task.doTask(date);//
      } catch (Exception e) {//异常处理已经Aop拦截处理

      }
    });//线程中执行任务
  }

  /**
   * 增加任务
   */
  public void addTask(BaseTask task) {
    List<BaseTask> baseTasks = tasks.get(task.priority);
    if (baseTasks == null) {
      baseTasks = new ArrayList<>();
      List<BaseTask> putIfAbsent = tasks.putIfAbsent(task.priority, baseTasks);
      if (putIfAbsent != null) {
        baseTasks = putIfAbsent;
      }
    }
    baseTasks.add(task);
  }

  /**
   * 将任务结束标识重新设置
   */
  public void finishTask() {
    tasks.forEach((key, value) -> {
      for (BaseTask task : value) {
        task.finish = false;
      }
    });
  }


}
