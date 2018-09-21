package com.ice.task.scheduler.core.email;

import com.ice.task.scheduler.bean.EmailMsg;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentLinkedQueue;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author ice
 * @Date 2018/9/13 11:38
 */
public class EmailMsgCache {

  private static final Logger logger = LoggerFactory.getLogger(EmailMsgCache.class);

  private static final ConcurrentLinkedQueue<EmailMsg> cache = new ConcurrentLinkedQueue<>();


  public static void putCache(EmailMsg msg) {
    cache.add(msg);
    logger.debug("msg:{}", msg);
  }

  public static void cleanCache() {
    cache.clear();
    logger.debug("clear email msg cache");
  }

  public static boolean isEmpty() {
    return cache.isEmpty();
  }

  public static List<EmailMsg> getCache() {
    return new ArrayList<>(cache);
  }

}
