package nz.co.yellow.vault.content.ds.impl;

import javax.annotation.Resource;

import nz.co.yellow.vault.content.client.model.SiliconeMturkFields;
import nz.co.yellow.vault.content.data.SiliconeMturkModel;
import nz.co.yellow.vault.content.data.repository.MturkFieldsRepositoryCustom;
import nz.co.yellow.vault.content.ds.MturkFieldsCreateUptException;
import nz.co.yellow.vault.content.ds.SiliconeMturkFieldsDS;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author david
 *
 */
@Service("siliconeMturkFieldsDs")
public class SiliconeMturkFieldsDSImpl
    implements SiliconeMturkFieldsDS {

  @Resource
  private MturkFieldsRepositoryCustom mturkFieldsCustRepository;

  // @Autowired
  // private SiliconeRestfulClientSupport siliconeRestfulClientSupport;

  private static final Logger LOGGER = LoggerFactory
      .getLogger(SiliconeMturkFieldsDSImpl.class);

  @Override
  public void patchSiliconeMturkFields(SiliconeMturkFields siliconeMturkFields)
      throws Exception {
    // LOGGER.debug("patchSiliconeMturkFields start:{}", siliconeMturkFields);
    // SiliconeMturkModel siliconeMturkModel = this
    // .mapping(siliconeMturkFields);
    // String resultMsg = createOrMergeSiliconeMturkFields(siliconeMturkModel);
    //
    // if (StringUtils.isEmpty(resultMsg)) {
    // throw new MturkFieldsCreateUptException(
    // "Calling Procedure process failed {}");
    // }
    // else if (resultMsg.startsWith("Error")) {
    // LOGGER.debug("get error msg from procedure: {}" + resultMsg);
    // throw new MturkFieldsCreateUptException(
    // "Procedure process failed{}: " + resultMsg);
    // }
    // else if (resultMsg.startsWith("Success")) {
    // doPatch(siliconeMturkModel);
    // }
    // else {
    // throw new MturkFieldsCreateUptException(
    // "Return value from procedure can not be identified");
    // }
  }

  // private void doPatch(SiliconeMturkModel siliconeMturkModel)
  // throws MturkFieldsPatchException {
  // String resourceUri = siliconeMturkModel.getResourceUri();
  // LOGGER.info("URI{}: " + resourceUri);
  // try {
  // siliconeRestfulClientSupport.patchMturkFields(resourceUri);
  // }
  // catch (Exception e) {
  // LOGGER.error("patch errors", e);
  // throw new MturkFieldsPatchException("patch errors", e);
  // }
  //
  // }

  @Override
  @Transactional(readOnly = false, propagation = Propagation.REQUIRES_NEW)
  public String createOrMergeSiliconeMturkFields(SiliconeMturkModel siliconeMturkModel)
      throws Exception {
    LOGGER.debug("createOrMergeSiliconeMturkFields start{}", siliconeMturkModel);
    String message = null;
    try {
      message = mturkFieldsCustRepository
          .procedureProcess(siliconeMturkModel);
    }
    catch (Exception e) {
      LOGGER.debug("Calling Procedure process failed {}" + e);
      new MturkFieldsCreateUptException(e);
    }
    LOGGER.debug("get Message: {}" + message);
    return message;
  }

  // private SiliconeMturkModel mapping(SiliconeMturkFields siliconeMturkFields) {
  // SiliconeMturkModel siliconeMturkModel = new SiliconeMturkModel();
  // siliconeMturkModel.setEmail(siliconeMturkFields.getEmail());
  // siliconeMturkModel.setFax(siliconeMturkFields.getFax_number());
  // siliconeMturkModel.setFreeNumber(siliconeMturkFields.getFree_number());
  // siliconeMturkModel.setMobileNumber(siliconeMturkFields
  // .getMobile_number());
  // siliconeMturkModel.setPacking(siliconeMturkFields.getParking());
  // siliconeMturkModel
  // .setResourceUri(siliconeMturkFields.getResource_uri());
  // siliconeMturkModel.setSecondaryNumber(siliconeMturkFields
  // .getSecondary_number());
  // int since = 0;
  // if (!StringUtils.isEmpty(siliconeMturkFields.getSince())) {
  // since = Integer.valueOf(siliconeMturkFields.getSince());
  // }
  //
  // String desc = siliconeMturkFields.getDescription();
  // if (desc != null) {
  // if (desc.length() > 4000) {
  // LOGGER.debug(
  // "yellowListId["
  // + siliconeMturkFields.getYellow_listing_id()
  // + "] description is more than 4000:{}",
  // desc.length());
  // LOGGER.debug("desc:{}", desc);
  // desc = desc.substring(0, 3999);
  // }
  // siliconeMturkModel.setDescription(desc);
  // }
  // siliconeMturkModel.setSince(since);
  // siliconeMturkModel.setYellowListId(Integer.valueOf(siliconeMturkFields
  // .getYellow_listing_id()));
  // return siliconeMturkModel;
  // }

}
