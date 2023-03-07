package com.dongnebook.global.replication;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

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

		List<String> list = new ArrayList<>();
		for (Object o : targetDataSources.keySet()) {
			String string = String.valueOf(o);
			if (string.contains(SLAVE)) {
				list.add(string);
			}
		}
		dataSourceNameList = new CircularList<>(
			list
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
