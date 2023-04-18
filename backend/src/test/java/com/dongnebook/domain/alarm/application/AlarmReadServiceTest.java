package com.dongnebook.domain.alarm.application;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.dongnebook.domain.alarm.repository.AlarmQueryRepository;

@ExtendWith(MockitoExtension.class)
class AlarmReadServiceTest {
    @Mock
    private AlarmQueryRepository alarmQueryRepository;
    @InjectMocks
    private AlarmReadService alarmReadService;

    @Test
    void AlarmReadAllTest() throws Exception {
        // given
        Long memberId = 1L;

        // when & then
        assertDoesNotThrow(() -> alarmReadService.readAll(memberId));
        verify(alarmQueryRepository, times(1)).read(any());
    }
}
