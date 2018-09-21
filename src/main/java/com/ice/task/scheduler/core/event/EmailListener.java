package com.ice.task.scheduler.core.event;

import com.google.common.base.Joiner;
import com.google.common.eventbus.Subscribe;
import com.ice.misc.Misc;
import com.ice.task.scheduler.bean.EmailMsg;
import com.ice.task.scheduler.core.email.EmailMsgCache;
import com.ice.task.scheduler.core.email.EmailUtils;
import com.ice.task.scheduler.core.event.EventBusMsg.EventType;
import java.util.ArrayList;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @author ice
 * @Date 2018/9/15 10:58
 */
@Component
public class EmailListener {

  private static final Logger logger = LoggerFactory.getLogger(EmailListener.class);

  private static final String EMAIL_SEND_CODE = "EMAIL_SEND";

  private static final String EMAIL_SEND_TYPE = "EMAIL_SEND";

  @Autowired
  private EmailUtils emailNotify;

  private String email = "1163569270@qq.com";

  @Subscribe
  public void sendEmail(EventBusMsg msg) {
    if (!EventType.EMAIL.name().equals(msg.getEventName())) {
      return;
    }
    if (EmailMsgCache.isEmpty()) {
      return;
    }
    List<EmailMsg> msgs = EmailMsgCache.getCache();
    List<String> list = new ArrayList<>();
    msgs.forEach(email -> list.add(Misc.obj2json(email)));
    String content = Joiner.on("\n").join(list);
    logger.info("send email, person:{},content:{}", email, content);
    try {
      emailNotify.sendEmai(email, null, "edw 异常", content);
      EmailMsgCache.cleanCache();//清除缓存.
    } catch (Exception e) {
      logger.error("邮件发送异常,err:{}", Misc.trace(e));
    }
  }

}
