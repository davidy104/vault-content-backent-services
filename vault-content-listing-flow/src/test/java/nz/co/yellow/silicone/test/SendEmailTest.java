package nz.co.yellow.silicone.test;

import nz.co.yellow.vault.content.common.email.SendEmailSupport;

import org.junit.Ignore;
import org.junit.Test;

public class SendEmailTest {

  private SendEmailSupport sendEmailSupport;

  @Test
  @Ignore("not run all the time")
  public void testSendEmail()
      throws Exception {
    sendEmailSupport = new SendEmailSupport();
    sendEmailSupport.setToString("david.yuan@yellow.co.nz");
    sendEmailSupport.setDefaultSubject("EmailNotice testing");
    sendEmailSupport.sendSimpleEmail("failed yellowListingIds: 123,456,");
  }

}
