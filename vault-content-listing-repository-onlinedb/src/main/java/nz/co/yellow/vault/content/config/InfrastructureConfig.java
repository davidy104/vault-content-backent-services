package nz.co.yellow.vault.content.config;

import java.util.Properties;

import nz.co.yellow.vault.content.data.OnlineDbDataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.Database;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@Configuration
@ComponentScan(basePackages = { "nz.co.yellow.vault.content" })
@EnableTransactionManagement
public class InfrastructureConfig {

  private static final String PROPERTY_NAME_HIBERNATE_DIALECT = "hibernate.dialect";

  private static final String PROPERTY_NAME_HIBERNATE_FORMAT_SQL = "hibernate.format_sql";

  private static final String PROPERTY_NAME_HIBERNATE_NAMING_STRATEGY = "hibernate.ejb.naming_strategy";

  private static final String PROPERTY_NAME_HIBERNATE_SHOW_SQL = "hibernate.show_sql";

  @Autowired
  private OnlineDbDataSource onlineDbDataSource;

  @Bean
  public LocalContainerEntityManagerFactoryBean entityManagerFactory() {

    HibernateJpaVendorAdapter vendorAdapter = new HibernateJpaVendorAdapter();
    vendorAdapter.setDatabase(Database.ORACLE);
    vendorAdapter.setGenerateDdl(true);

    LocalContainerEntityManagerFactoryBean factory = new LocalContainerEntityManagerFactoryBean();
    factory.setJpaVendorAdapter(vendorAdapter);
    factory.setDataSource(onlineDbDataSource);

    factory.setPackagesToScan("nz.co.yellow.vault.content.data");

    Properties jpaProterties = new Properties();
    jpaProterties.put(PROPERTY_NAME_HIBERNATE_DIALECT,
        "org.hibernate.dialect.OracleDialect");
    jpaProterties.put(PROPERTY_NAME_HIBERNATE_FORMAT_SQL, false);
    jpaProterties.put(PROPERTY_NAME_HIBERNATE_NAMING_STRATEGY,
        "org.hibernate.cfg.ImprovedNamingStrategy");
    jpaProterties.put(PROPERTY_NAME_HIBERNATE_SHOW_SQL, false);

    factory.setJpaProperties(jpaProterties);

    return factory;
  }

  @Bean
  public PlatformTransactionManager transactionManager() {
    JpaTransactionManager txManager = new JpaTransactionManager();
    txManager.setEntityManagerFactory(entityManagerFactory().getObject());
    return txManager;
  }

}
