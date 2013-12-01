package nz.co.yellow.silicone.test;

import nz.co.yellow.espresso.testutils.YellowComponentTestContext;

import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = YellowComponentTestContext.class)
@Ignore("not run all the time")
public class PollingTest {

  @Test
  public void testpolling()
      throws Exception {
    Thread.sleep(5000000);
  }

  // @Test
  // public void testPolling()
  // throws Exception {
  // CamelContext context = new DefaultCamelContext();
  // context.addRoutes(new RouteBuilder() {
  //
  // @Override
  // public void configure()
  // throws Exception {
  // from(
  // "quartz://mturkTimer?trigger.repeatInterval=2000&trigger.repeatCount=4&stateful=true")
  // .process(new Processor() {
  // public void process(Exchange exchange)
  // throws Exception {
  //
  // StdScheduler stdScheduler = (StdScheduler) exchange
  // .getIn().getHeader("scheduler");
  // assertNotNull(stdScheduler);
  // }
  // });
  // }
  // });
  //
  // // start the route and let it do its work
  // context.start();
  // Thread.sleep(10000);
  //
  // // stop the CamelContext
  // context.stop();
  // }
}
