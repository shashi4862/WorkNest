package com.worknest.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.orm.hibernate5.HibernateTransactionManager;
import org.springframework.orm.hibernate5.LocalSessionFactoryBean;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.view.InternalResourceViewResolver;

import javax.sql.DataSource;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import java.util.Properties;

@Configuration
@EnableWebMvc
@EnableTransactionManagement
@ComponentScan(basePackages = "com.worknest")
public class AppConfig {

	@Bean
	public DataSource getDataSource()
	{
		DriverManagerDataSource dataSource= new DriverManagerDataSource();
		dataSource.setDriverClassName("com.mysql.cj.jdbc.Driver");
		dataSource.setUrl("jdbc:mysql://localhost:3306/wiprojdbc");
		dataSource.setUsername("root");
		dataSource.setPassword("root");
		return dataSource;
	}
	
	private Properties getProperties() {
		Properties properties = new Properties();
		properties.put("hibernate.dialect", "org.hibernate.dialect.MySQL8Dialect");
        properties.put("hibernate.show_sql", "true");
        properties.put("hibernate.hbm2ddl.auto", "update");
        return properties;
	}
	
	
	@Bean
	public LocalSessionFactoryBean getSession()
	{
		LocalSessionFactoryBean session = new LocalSessionFactoryBean();
		session.setDataSource(getDataSource());
		session.setPackagesToScan("com.worknest.model");
		session.setHibernateProperties(getProperties());
		return session;
	}
	
	@Bean
	public HibernateTransactionManager getTransactionManager()
	{
		HibernateTransactionManager manager = new HibernateTransactionManager();
		manager.setSessionFactory(getSession().getObject());
		
		return manager;
	}
}