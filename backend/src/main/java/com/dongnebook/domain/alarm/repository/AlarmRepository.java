package com.dongnebook.domain.alarm.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.dongnebook.domain.alarm.domain.Alarm;

public interface AlarmRepository extends JpaRepository<Alarm, Long> {
}
