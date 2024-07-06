//package com.datn.config;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.core.env.Environment;
//import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
//import org.springframework.jdbc.datasource.DriverManagerDataSource;
//import org.springframework.orm.jpa.JpaTransactionManager;
//import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
//import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
//import org.springframework.transaction.PlatformTransactionManager;
//
//import javax.sql.DataSource;
//import java.util.HashMap;
//
//@Configuration
//@EnableJpaRepositories(basePackages = {"com.datn.repository"},
//        entityManagerFactoryRef = "db1EntityManager",
//        transactionManagerRef = "db1TransactionManager")
//public class DataSourceMariaDbConfig {
//    @Autowired
//    private Environment env;
//    @Bean
//    public LocalContainerEntityManagerFactoryBean db1EntityManager() {
//        LocalContainerEntityManagerFactoryBean em = new LocalContainerEntityManagerFactoryBean();
//        em.setDataSource(db1Datasource());
//        em.setPackagesToScan(new String[]{"com.datn.entity"});
//        em.setPersistenceUnitName("db1EntityManager");
//        HibernateJpaVendorAdapter vendorAdapter = new HibernateJpaVendorAdapter();
//        em.setJpaVendorAdapter(vendorAdapter);
//        HashMap properties = new HashMap<>();
//        properties.put("hibernate.dialect",
//                env.getProperty("hibernate.dialect"));
//        properties.put("hibernate.show-sql",
//                env.getProperty("jdbc.show-sql"));
//        em.setJpaPropertyMap(properties);
//        return em;
//    }
//
//    @Bean
//    public DataSource db1Datasource() {
//        DriverManagerDataSource dataSource = new DriverManagerDataSource();
//        dataSource.setDriverClassName("com.mysql.cj.jdbc.Driver"); // Set the correct MySQL driver class name
//        dataSource.setUrl(env.getProperty("jdbc:mysql://localhost:3306/moodle"));
//        dataSource.setUsername(env.getProperty("root"));
//        dataSource.setPassword(env.getProperty("ductu1005"));
//        return dataSource;
//    }
//
//    @Bean
//    public PlatformTransactionManager db1TransactionManager() {
//
//        JpaTransactionManager transactionManager
//                = new JpaTransactionManager();
//        transactionManager.setEntityManagerFactory(
//                db1EntityManager().getObject());
//        return transactionManager;
//    }
//}
