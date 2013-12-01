package nz.co.yellow.vault.content.test;

import static junit.framework.Assert.assertNotNull;
import nz.co.yellow.vault.content.data.SiliconeMturkModel;
import nz.co.yellow.vault.content.ds.SiliconeMturkFieldsDS;

import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = TestAppConfig.class)
@Ignore("not run all the time")
public class ProcedureCallTest {

	@Autowired
	private SiliconeMturkFieldsDS siliconeMturkFieldsDs;

	private static final Logger LOGGER = LoggerFactory
			.getLogger(ProcedureCallTest.class);

	@Test
	// @Transactional(readOnly = false, propagation = Propagation.REQUIRES_NEW,
	// rollbackFor = {
	// MturkFieldsPatchException.class,
	// MturkFieldsCreateUptException.class })
	public void testProcedure() throws Exception {

		SiliconeMturkModel siliconeMturkModel = new SiliconeMturkModel();
		siliconeMturkModel.setYellowListId(152);
		siliconeMturkModel.setEmail("test123@email.com");
		siliconeMturkModel.setFax("12345");
		siliconeMturkModel.setFreeNumber("12345");
		siliconeMturkModel.setMobileNumber("12345");
		siliconeMturkModel.setPacking("free_off_street");
		siliconeMturkModel.setSecondaryNumber("12345");
		siliconeMturkModel.setSince(2013);

		String message = siliconeMturkFieldsDs
				.createOrMergeSiliconeMturkFields(siliconeMturkModel);
		LOGGER.debug("message from calling procedure: " + message);
		assertNotNull(message);
	}

}
