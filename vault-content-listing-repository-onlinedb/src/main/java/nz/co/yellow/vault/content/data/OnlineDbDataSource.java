package nz.co.yellow.vault.content.data;

import nz.co.yellow.espresso.db.AbstractEspressoDatasource;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;

@Repository("onlineDbDataSource")
public class OnlineDbDataSource
    extends AbstractEspressoDatasource {

  @Value("${onlineDbDatasource.driver}")
  private String driver;

  @Value("${onlineDbDatasource.url}")
  private String url;

  @Value("${onlineDbDatasource.username}")
  private String username;

  @Value("${onlineDbDatasource.password}")
  private String password;

  @Value("${onlineDbDatasource.miniumumConnections:1}")
  private Integer miniumumConnections;

  @Value("${onlineDbDatasource.maximumConnections:5}")
  private Integer maximumConnections;

  @Value("${onlineDbDatasource.partitionCount:1}")
  private Integer partitionCount;

  @Value("${onlineDbDatasource.connectionTestStatement}")
  private String connectionTestStatement;

  @Override
  protected String getDriverClassName() {
    return driver;
  }

  @Override
  protected Integer getPartitionCount() {
    return partitionCount;
  }

  @Override
  protected Integer getMaximumConnections() {
    return maximumConnections;
  }

  @Override
  protected Integer getMinimumConnections() {
    return miniumumConnections;
  }

  @Override
  protected String getPassword() {
    return password;
  }

  @Override
  protected String getUsername() {
    return username;
  }

  @Override
  protected String getUrl() {
    return url;
  }

  @Override
  protected String getConnectionTestStatement() {
    return connectionTestStatement;
  }

  @Override
  protected String getCatalogName() {
    return "onlinedb";
  }

}
