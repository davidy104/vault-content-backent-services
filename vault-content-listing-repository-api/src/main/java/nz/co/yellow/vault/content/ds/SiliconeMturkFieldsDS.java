package nz.co.yellow.vault.content.ds;

import nz.co.yellow.vault.content.client.model.SiliconeMturkFields;
import nz.co.yellow.vault.content.data.SiliconeMturkModel;

/**
 * domain service for silicone client
 *
 * @author david
 *
 */
public interface SiliconeMturkFieldsDS {

  /**
   * calling oracle procedure for creating or updating data in OnlineDb
   *
   * @param siliconeMturkFields
   * @return
   * @throws MturkFieldsCreateUptException
   */
  String createOrMergeSiliconeMturkFields(SiliconeMturkModel siliconeMturkModel)
      throws Exception;

  void patchSiliconeMturkFields(SiliconeMturkFields siliconeMturkFields)
      throws Exception;

}
