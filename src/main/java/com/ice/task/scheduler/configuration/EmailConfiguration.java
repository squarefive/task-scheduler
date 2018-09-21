package com.ice.task.scheduler.configuration;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

@Configuration
@PropertySource(value = "classpath:email.properties")
public class EmailConfiguration {

  @Value("${mail.account}")
  private String account;
  @Value("${mail.password}")
  private String password;
  @Value("${mail.receiveServer}")
  private String receiveServer;
  @Value("${mail.sendServer}")
  private String sendServer;

  @Value("${mail.transport.protocal}")
  private String transportProtocal;
  @Value("${mail.store.protocal}")
  private String storeProtocal;
  @Value("${mail.imap.port}")
  private String imapPort;

  @Value("${mail.smtp.auth}")
  private String smtpAuth;

  public String getSmtpAuth() {
    return smtpAuth;
  }

  public String getImapPort() {
    return imapPort;
  }

  public String getAccount() {

    return account;
  }

  public String getPassword() {
    return password;
  }

  public String getReceiveServer() {
    return receiveServer;
  }

  public String getSendServer() {
    return sendServer;
  }

  public String getTransportProtocal() {
    return transportProtocal;
  }

  public String getStoreProtocal() {
    return storeProtocal;
  }
}
