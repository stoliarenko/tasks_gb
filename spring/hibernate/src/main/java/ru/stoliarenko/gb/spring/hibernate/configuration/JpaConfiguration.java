package ru.stoliarenko.gb.spring.hibernate.configuration;

import com.mchange.v2.c3p0.ComboPooledDataSource;
import org.hibernate.cfg.Environment;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.sql.DataSource;
import java.beans.PropertyVetoException;
import java.util.Properties;

@Configuration
@EnableTransactionManagement
@ComponentScan("ru.stoliarenko.gb.spring.hibernate")
public class JpaConfiguration {

    @Bean(name = "dataSource")
    public DataSource dataSource() {
        try {
            final ComboPooledDataSource pooledDataSource = new ComboPooledDataSource();
            pooledDataSource.setDriverClass("org.hsqldb.jdbc.JDBCDriver");
            pooledDataSource.setJdbcUrl("jdbc:hsqldb:mem:testdb;DB_CLOSE_DELAY=-1");
            pooledDataSource.setMinPoolSize(5);
            pooledDataSource.setMaxPoolSize(30);
            return pooledDataSource;
        } catch (PropertyVetoException e) {
            final DriverManagerDataSource dataSource = new DriverManagerDataSource();
            dataSource.setDriverClassName("org.hsqldb.jdbc.JDBCDriver");
            dataSource.setUrl("jdbc:hsqldb:mem:testdb;DB_CLOSE_DELAY=-1");
            e.printStackTrace();
            return dataSource;
        }
    }

    @Bean(name = "entityManagerFactory")
    public LocalContainerEntityManagerFactoryBean entityManagerFactory() {
        final LocalContainerEntityManagerFactoryBean factory;
        factory = new LocalContainerEntityManagerFactoryBean();

        factory.setDataSource(dataSource());
        factory.setJpaVendorAdapter(new HibernateJpaVendorAdapter());
        factory.setPackagesToScan("ru.stoliarenko.gb.spring.hibernate.model");

        final Properties jpaProperties = new Properties();
        jpaProperties.put(Environment.DIALECT, "org.hibernate.dialect.HSQLDialect");
        jpaProperties.put(Environment.HBM2DDL_AUTO, "update");
        jpaProperties.put(Environment.SHOW_SQL, "true");
        factory.setJpaProperties(jpaProperties);

        return factory;
    }

    @Bean
    public PlatformTransactionManager transactionManager() {
        final JpaTransactionManager transactionManager;
        transactionManager = new JpaTransactionManager();
        transactionManager.setEntityManagerFactory(entityManagerFactory().getObject());
        return transactionManager;
    }

}
