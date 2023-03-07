package com.dongnebook.global.replication;

import java.util.LinkedHashMap;
import java.util.Map;

import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;

import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.autoconfigure.orm.jpa.JpaProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.boot.orm.jpa.EntityManagerFactoryBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.jdbc.datasource.LazyConnectionDataSourceProxy;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.AbstractJpaVendorAdapter;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;

import com.zaxxer.hikari.HikariDataSource;

@Configuration
@Profile("rds")
@EnableAutoConfiguration(exclude = {DataSourceAutoConfiguration.class}) // DataSource 자동 설정을 제외시킨다.
@EnableConfigurationProperties(CustomDataSourceProperties.class)
public class CustomDataSourceConfig {
	private final CustomDataSourceProperties databaseProperty;
	private final JpaProperties jpaProperties;

	public CustomDataSourceConfig(CustomDataSourceProperties databaseProperty, JpaProperties jpaProperties) {
		this.databaseProperty = databaseProperty;
		this.jpaProperties = jpaProperties;
	}

	public DataSource createDataSource(String url){

		return DataSourceBuilder.create()
			.type(HikariDataSource.class)
			.url(url)
			.driverClassName("com.mysql.cj.jdbc.Driver")
			.username(databaseProperty.getUsername())
			.password(databaseProperty.getPassword())
			.build();
	}

	@Bean
	public DataSource dataSource() {
		return new LazyConnectionDataSourceProxy(routingDataSource());
	}

	@Bean
	public DataSource routingDataSource() {
		CustomDataSourceProperties.Master master = databaseProperty.getMaster();
		DataSource masterDataSource = createDataSource(master.getUrl());

		Map<Object, Object> dataSourceMap = new LinkedHashMap<>();
		dataSourceMap.put(master.getName(), masterDataSource);
		databaseProperty.getSlaves()
			.forEach((slave-> dataSourceMap.put(slave.getName(), createDataSource(slave.getUrl()))));

		ReplicationRoutingDataSource replicationRoutingDataSource = new ReplicationRoutingDataSource();
		replicationRoutingDataSource.setDefaultTargetDataSource(masterDataSource);
		replicationRoutingDataSource.setTargetDataSources(dataSourceMap);
		replicationRoutingDataSource.afterPropertiesSet();

		return replicationRoutingDataSource;
	}

	@Bean
	public LocalContainerEntityManagerFactoryBean entityManagerFactory() {
		EntityManagerFactoryBuilder entityManagerFactoryBuilder = createEntityManagerFactoryBuilder(jpaProperties);
		return entityManagerFactoryBuilder.dataSource(dataSource()).packages("com.dongnebook").build();
	}

	private EntityManagerFactoryBuilder createEntityManagerFactoryBuilder(JpaProperties jpaProperties) {
		AbstractJpaVendorAdapter vendorAdapter = new HibernateJpaVendorAdapter();
		return new EntityManagerFactoryBuilder(vendorAdapter, jpaProperties.getProperties(), null);
	}

	@Bean
	public PlatformTransactionManager transactionManager(EntityManagerFactory entityManagerFactory) {
		JpaTransactionManager tm = new JpaTransactionManager();
		tm.setEntityManagerFactory(entityManagerFactory);
		return tm;
	}

}