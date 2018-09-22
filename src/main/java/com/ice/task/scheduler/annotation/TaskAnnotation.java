package com.ice.task.scheduler.annotation;

import com.ice.task.scheduler.enums.TaskQueueEnum;
import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author ice
 * @Date 2018/9/17 12:11
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
@Documented
public @interface TaskAnnotation {

  /**
   * 优先级
   */
  int priority() default 0;

  /**
   * 任务名称
   */
  String taskName() default "";

  /**
   * 队列名称
   */
  TaskQueueEnum[] queueName() default {};

  /**
   * 上一列所有任务都在执行完才能执行当前任务.
   */
  boolean allExecute() default false;

}
