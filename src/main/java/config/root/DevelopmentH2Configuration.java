package config.root;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.instrument.classloading.InstrumentationLoadTimeWeaver;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import java.util.HashMap;
import java.util.Map;
/**
 * Created on 5/26/16.
 */
@Configuration
//@Profile("development")
@EnableTransactionManagement
@ComponentScan(basePackages = "blog.repos")
public class DevelopmentH2Configuration {

  /*@Bean(initMethod = "init")
  public TestDataInitializer initTestData() {
    return new TestDataInitializer();
  }*/

  @Bean(name = "datasource")
  public DriverManagerDataSource dataSource() {
    DriverManagerDataSource dataSource = new DriverManagerDataSource();
    dataSource.setDriverClassName("org.h2.Driver");
    dataSource.setUrl("jdbc:h2:mem:testdb");
    dataSource.setUsername("sa");
    dataSource.setPassword("");
    return dataSource;
  }

  @Bean(name = "entityManagerFactory")
  public LocalContainerEntityManagerFactoryBean entityManagerFactory(DriverManagerDataSource dataSource) {

    LocalContainerEntityManagerFactoryBean entityManagerFactoryBean = new LocalContainerEntityManagerFactoryBean();
    entityManagerFactoryBean.setDataSource(dataSource);
    entityManagerFactoryBean.setPackagesToScan(new String[]{"blog.model"});
    entityManagerFactoryBean.setLoadTimeWeaver(new InstrumentationLoadTimeWeaver());
    entityManagerFactoryBean.setJpaVendorAdapter(new HibernateJpaVendorAdapter());

    Map<String, Object> jpaProperties = new HashMap<String, Object>();
    jpaProperties.put("hibernate.hbm2ddl.auto", "create-drop");
    jpaProperties.put("hibernate.show_sql", "true");
    jpaProperties.put("hibernate.format_sql", "true");
    jpaProperties.put("hibernate.use_sql_comments", "true");
    jpaProperties.put("hibernate.dialect", "org.hibernate.dialect.H2Dialect");
    entityManagerFactoryBean.setJpaPropertyMap(jpaProperties);

    return entityManagerFactoryBean;
  }

}
