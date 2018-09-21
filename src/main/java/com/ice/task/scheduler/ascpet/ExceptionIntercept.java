package com.ice.task.scheduler.ascpet;

import com.cootf.resim.edw.bos.dw.core.EmailMsgCache;
import com.cootf.resim.edw.bos.dw.core.event.EventBusMsg;
import com.cootf.resim.edw.bos.dw.core.event.EventBusMsg.EventType;
import com.cootf.resim.edw.bos.service.entities.EmailMsg;
import com.google.common.eventbus.EventBus;
import com.ice.misc.Misc;
import java.util.Date;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @author ice
 * @Date 2018/9/18 10:00
 */
@Aspect
@Component
public class ExceptionIntercept {

  private static final Logger logger = LoggerFactory.getLogger(ExceptionIntercept.class);

  @Autowired
  private EventBus eventBus;

  @Pointcut("execution(* com.ice.task.scheduler.task.*..*(..))")
  public void methodPointcut() {

  }

  @AfterThrowing(pointcut = "methodPointcut()", throwing = "e")
  public void doThrowingAfterMethodPointcut(JoinPoint jp, Throwable e) {
    logger.warn("exception: {}", Misc.trace(e));
    EmailMsgCache.putCache(
        new EmailMsg(new Date(), Misc.trace(e)));
//    this.notity();
  }

  @Around("methodPointcut()")
  public Object aroundMethodPointcut(ProceedingJoinPoint pjp) throws Throwable {
    Object result = pjp.proceed();
    return result;
  }

  /**
   * 消息通知
   */
  public void notity() {
    // 消息通知邮件发送
    EventBusMsg msg = new EventBusMsg();
    msg.setEventName(EventType.EMAIL.name());
    eventBus.post(msg);
  }
}
