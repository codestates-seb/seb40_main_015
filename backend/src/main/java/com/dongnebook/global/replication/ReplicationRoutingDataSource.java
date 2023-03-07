package com.dongnebook.global.replication;

import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;
import org.springframework.transaction.support.TransactionSynchronizationManager;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class ReplicationRoutingDataSource extends AbstractRoutingDataSource {
	private static final String MASTER = "master";
	private static final String SLAVE = "slave";
	private CircularList<String> dataSourceNameList;

	@Override
	public void setTargetDataSources(Map<Object, Object> targetDataSources) {
		super.setTargetDataSources(targetDataSources);

		dataSourceNameList = new CircularList<>(
			targetDataSources.keySet()
				.stream()
				.map(String::valueOf)
				.filter(string -> string.contains(SLAVE))
				.collect(Collectors.toList())
		);
	}
	@Override
	protected Object determineCurrentLookupKey() {
		boolean isReadOnly = TransactionSynchronizationManager.isCurrentTransactionReadOnly();
		if(isReadOnly){
			return dataSourceNameList.getOne();
		}
		return MASTER;
	}
}
