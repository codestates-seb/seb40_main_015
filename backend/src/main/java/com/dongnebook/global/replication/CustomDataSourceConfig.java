package com.dongnebook.global.replication;

import java.util.LinkedHashMap;
import java.util.Map;

import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;

import org.springframework.boot.autoconfigure.orm.jpa.JpaProperties;
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
public class CustomDataSourceConfig {
	private final CustomDataSourceProperties databaseProperty;
	private final JpaProperties jpaProperties;

	public CustomDataSourceConfig(CustomDataSourceProperties databaseProperty, JpaProperties jpaProperties) {
		this.databaseProperty = databaseProperty;
		this.jpaProperties = jpaProperties;
	}

	public DataSource createDataSource(String url) {
		HikariDataSource hikariDataSource = new HikariDataSource();
		hikariDataSource.setUsername(databaseProperty.getUsername());
		hikariDataSource.setPassword(databaseProperty.getPassword());
		hikariDataSource.setJdbcUrl(url);
		hikariDataSource.setDriverClassName("com.mysql.cj.jdbc.Driver");

		return hikariDataSource;
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
		return new JpaTransactionManager(entityManagerFactory);
	}

}
