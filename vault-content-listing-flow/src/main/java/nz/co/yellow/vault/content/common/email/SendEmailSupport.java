package nz.co.yellow.vault.content.common.email;

import javax.mail.internet.MimeMessage;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;

@Component("sendEmailSupport")
public class SendEmailSupport {

  private JavaMailSenderImpl mailSender;

  private String toString = "admin@ytech.co.nz";

  private String defaultSubject = "Error for mturkFields process";

  private String transportProtocol = "smtp";

  private boolean smtpAuth = false;

  private String emailHost = "10.8.8.8";

  private String emailFrom = "administrator@yellow.co.nz";

  private boolean debug = false;

  private final static String MAIL_HOST = "mail.host";

  private final static String MAIL_TRANSPORT_PROTOCOL = "mail.transport.protocol";

  private final static String MAIL_SMTP_AUTH = "mail.smtp.auth";

  private final static String MAIL_FROM = "mail.from";

  private final static String MAIL_DEBUG = "mail.debug";

  private final static String MAIL_SMTP_TIMEOUT = "mail.smtp.timeout";

  private static final Logger LOGGER = LoggerFactory
      .getLogger(SendEmailSupport.class);

  public void sendSimpleEmail(String content) {
    try {
      mailSender = new JavaMailSenderImpl();
      mailSender.getJavaMailProperties().put(MAIL_HOST, emailHost);
      mailSender.getJavaMailProperties().put(MAIL_TRANSPORT_PROTOCOL,
          transportProtocol);
      mailSender.getJavaMailProperties().put(MAIL_SMTP_AUTH, smtpAuth);
      mailSender.getJavaMailProperties().put(MAIL_FROM, emailFrom);
      mailSender.getJavaMailProperties().put(MAIL_DEBUG, debug);
      mailSender.getJavaMailProperties().put(MAIL_SMTP_TIMEOUT, 8000);
      MimeMessage mailMessage = mailSender.createMimeMessage();
      MimeMessageHelper messageHelper = new MimeMessageHelper(mailMessage,
          true);
      String[] toArray = null;

      if (!StringUtils.isEmpty(toString)) {
        toArray = this.toAddressArray(toString);
      }

      if (toArray != null && toArray.length > 0) {
        messageHelper.setTo(toArray);
        messageHelper.setText(content, true);
        messageHelper.setSubject(defaultSubject);

        mailSender.send(mailMessage);
      }
    }
    catch (Exception e) {
      LOGGER.debug("sending email errors", e);
    }

  }

  private String[] toAddressArray(String toStr) {
    String[] toAddress = null;
    if (toStr.indexOf(",") != -1) {
      toAddress = toStr.split(",");
    }
    else {
      toAddress = new String[] { toStr };
    }
    return toAddress;
  }

  public String getToString() {
    return toString;
  }

  public void setToString(String toString) {
    this.toString = toString;
  }

  public String getDefaultSubject() {
    return defaultSubject;
  }

  public void setDefaultSubject(String defaultSubject) {
    this.defaultSubject = defaultSubject;
  }

}
