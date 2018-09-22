package com.ice.task.scheduler.core;

import com.ice.task.scheduler.annotation.TaskAnnotation;
import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import org.apache.commons.lang3.ArrayUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.aop.framework.AdvisedSupport;
import org.springframework.aop.framework.AopProxy;
import org.springframework.aop.support.AopUtils;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

/**
 * @author ice
 * @Date 2018/9/17 11:58
 */
@Component
public class TaskBeanPostProcessor implements BeanPostProcessor, ApplicationContextAware {

  private static final Logger logger = LoggerFactory.getLogger(TaskBeanPostProcessor.class);

  @Autowired
  private ScheduleTask scheduleTask;

  private ApplicationContext ctx;

  @Override
  public Object postProcessBeforeInitialization(Object bean, String beanName)
      throws BeansException {
    return bean;
  }

  /**
   * bean被加载之后处理被注解的任务,在使用了切面之后，由于执行的顺序不同，导致cglib加载成俩个不同的实体类，那么如果没有从 ctx获取bean,则获取不到值
   */
  @Override
  public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {

    if (bean instanceof BaseTask) {
      Object target = null;
      try {
        if (AopUtils.isCglibProxy(bean)) {
          target = getCglibProxyTargetObject(bean);
        } else {
          target = getJdkDynamicProxyTargetObject(bean);
        }
      } catch (Exception e) {
        return bean;
      }
      Annotation[] annotations = target.getClass().getAnnotations();
      if (ArrayUtils.isEmpty(annotations)) {
        return bean;
      }

      for (Annotation annotation : annotations) {
        if (annotation.annotationType().equals(TaskAnnotation.class)) {
          logger.debug("task bean post process after initialization execute!");
          TaskAnnotation taskAnnotation = (TaskAnnotation) annotation;//强转
          try {
            Field[] fields = target.getClass().getFields();//需要通过反射将值进行修改，下面的操作仅仅是对象的引用
            if (!ArrayUtils.isEmpty(fields)) {
              for (Field f : fields) {
                f.setAccessible(true);
                if (f.getName().equals("priority")) {
                  f.set(target, taskAnnotation.priority());
                } else if (f.getName().equals("taskName")) {
                  f.set(target, taskAnnotation.taskName());
                } else if (f.getName().equals("queueName")) {
                  f.set(target, taskAnnotation.queueName()[0].getQueueName());
                }
              }
            }
//            BaseTask baseTask = (BaseTask) bean;//强转
//            baseTask.priority = taskAnnotation.priority();
//            baseTask.taskName = taskAnnotation.taskName();
//            baseTask.queueName = taskAnnotation.queueName()[0].getQueueName();
//            //添加任务到任务队列中
            scheduleTask.addTask((BaseTask) target);
            logger.debug("task bean initialization success! task{} added queue", beanName);
            return bean;
          } catch (Exception e) {//强转类型出现异常时
            logger.debug("Strong turn abnormal,object:{}", beanName);
            return bean;
          }
        }
      }
    }
    return bean;
  }

  private Object getTheSingletonObject(String beanName) {
    return ctx.getAutowireCapableBeanFactory().getBean(beanName);
  }

  @Override
  public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
    ctx = applicationContext;
  }

  private Object getCglibProxyTargetObject(Object proxy) throws Exception {
    Field h = proxy.getClass().getDeclaredField("CGLIB$CALLBACK_0");
    h.setAccessible(true);
    Object dynamicAdvisedInterceptor = h.get(proxy);

    Field advised = dynamicAdvisedInterceptor.getClass().getDeclaredField("advised");
    advised.setAccessible(true);

    Object target = ((AdvisedSupport) advised.get(dynamicAdvisedInterceptor)).getTargetSource()
        .getTarget();

    return target;
  }


  private Object getJdkDynamicProxyTargetObject(Object proxy) throws Exception {
    Field h = proxy.getClass().getSuperclass().getDeclaredField("h");
    h.setAccessible(true);
    AopProxy aopProxy = (AopProxy) h.get(proxy);

    Field advised = aopProxy.getClass().getDeclaredField("advised");
    advised.setAccessible(true);

    Object target = ((AdvisedSupport) advised.get(aopProxy)).getTargetSource().getTarget();

    return target;
  }


}
