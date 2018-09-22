package com.ice.task.scheduler.core;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Enumeration;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;
import org.springframework.stereotype.Component;

/**
 * @author ice
 * @Date 2018/9/17 19:27
 */
@Component
public class TaskExecuteCondition {

  private ConcurrentHashMap<String, AtomicInteger> executeMap = new ConcurrentHashMap<>();

  /**
   * 初始化
   */
  public void init(ConcurrentHashMap<Integer, List<BaseTask>> tasks) {
    Enumeration<Integer> keys = tasks.keys();
    List<Integer> prioritys = new ArrayList<>();
    while (keys.hasMoreElements()) {
      prioritys.add(keys.nextElement());
    }
    Collections.sort(prioritys);//升序
    for (Integer priority : prioritys) {
      List<BaseTask> list = tasks.get(priority);
      if (list.isEmpty()) {
        continue;
      }
      Map<String, List<BaseTask>> collect = list.stream()
          .collect(Collectors.groupingBy(x -> x.queueName, Collectors.toList()));
      for (Entry<String, List<BaseTask>> entry : collect.entrySet()) {
        for (BaseTask task : entry.getValue()) {
          addCondition(task.priority, task.queueName);
        }
      }
    }
  }

  /**
   * 执行任务完成
   */
  public boolean executeTask(Integer priority, String queueName) {
    String name = this.getQueue(priority, queueName);
    AtomicInteger count = executeMap.get(name);
    int sum = count.decrementAndGet();
    if (sum == 0) {
      return true;
    }
    return false;
  }

  /**
   * 对个某个队列的条件
   */
  public int getCondition(Integer priority, String queueName) {
    String name = this.getQueue(priority, queueName);
    return executeMap.get(name).get();
  }

  private void addCondition(Integer priority, String queueName) {
    String name = this.getQueue(priority, queueName);
    AtomicInteger count = executeMap.get(name);
    if (count == null) {
      count = new AtomicInteger(0);
      executeMap.put(name, count);
    }
    count.incrementAndGet();
  }

  private void addCondition(Integer priority, String queueName, int sum) {
    String name = this.getQueue(priority, queueName);
    AtomicInteger count = executeMap.get(name);
    if (count == null) {
      count = new AtomicInteger(sum);
      executeMap.put(name, count);
    } else {
      count.set(sum);
    }
  }


  private String getQueue(Integer priority, String queueName) {
    return priority + queueName;
  }

  /**
   * 清除队列
   */
  public void clear() {
    this.executeMap.clear();
  }
}
