package nz.co.yellow.vault.content.data.repository;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Types;

import nz.co.yellow.vault.content.data.OnlineDbDataSource;
import nz.co.yellow.vault.content.data.SiliconeMturkModel;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.CallableStatementCallback;
import org.springframework.jdbc.core.CallableStatementCreator;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class MturkFieldsRepositoryImpl
    implements MturkFieldsRepositoryCustom {

  @Autowired
  private OnlineDbDataSource onlineDbDataSource;

  private static final Logger LOGGER = LoggerFactory
      .getLogger(MturkFieldsRepositoryImpl.class);

  @Override
  public String procedureProcess(SiliconeMturkModel siliconeMturkModel)
      throws Exception {
    String result = null;
    LOGGER.debug("YellowListingId: {}"
        + siliconeMturkModel.getYellowListId());

    LOGGER.debug("start call function:{}", siliconeMturkModel);

    final String email = siliconeMturkModel.getEmail();

    final String fax = siliconeMturkModel.getFax();

    final int yellowListId = siliconeMturkModel.getYellowListId();

    final String freeNumber = siliconeMturkModel.getFreeNumber();

    final String mobileNumber = siliconeMturkModel.getMobileNumber();

    final String packing = siliconeMturkModel.getPacking();

    final String website = siliconeMturkModel.getWebsite();

    final String secondaryNumber = siliconeMturkModel.getSecondaryNumber();

    final int yearEstablish = siliconeMturkModel.getSince();

    final String description = siliconeMturkModel.getDescription();

    LOGGER.debug("siliconeMturkModel:{}", siliconeMturkModel);

    JdbcTemplate jdbcTemplate = new JdbcTemplate(onlineDbDataSource);

    try {
      result = jdbcTemplate.execute(new CallableStatementCreator() {
        public CallableStatement createCallableStatement(Connection con) {
          CallableStatement cs = null;
          try {
            cs = con.prepareCall("{? = call process_mturk_listing(?, ?, ?, ?, ?, ?, ?, ?, ?,?)}");
            cs.registerOutParameter(1, Types.VARCHAR);
            cs.setInt(2, yellowListId);
            cs.setString(3, email);
            cs.setString(4, fax);
            cs.setString(5, freeNumber);
            cs.setString(6, mobileNumber);
            cs.setString(7, packing);
            cs.setString(8, website);
            cs.setString(9, secondaryNumber);
            cs.setInt(10, yearEstablish);
            cs.setString(11, description);
          }
          catch (SQLException e) {
            LOGGER.error("calling procedure error", e);
            throw new RuntimeException("calling procedure error", e);
          }
          return cs;
        }
      }, new CallableStatementCallback<String>() {
        public String doInCallableStatement(CallableStatement cs) {
          try {
            cs.execute();
            return cs.getString(1);
          }
          catch (SQLException e) {
            LOGGER.error("Onlinedb merge error.", e);
            throw new RuntimeException("Onlinedb merge error.", e);
          }
        }
      });
    }
    catch (Exception e) {
      LOGGER.error("Onlinedb merge error:[" + e.getMessage() + "]");
      throw new Exception("Onlinedb merge error:[" + e.getMessage() + "]", e);
    }
    LOGGER.debug("result:{}" + result);
    return result;
  }

}
