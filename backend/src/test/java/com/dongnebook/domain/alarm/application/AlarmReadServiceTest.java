package com.dongnebook.domain.alarm.application;

import com.dongnebook.domain.alarm.repository.AlarmQueryRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
public class AlarmReadServiceTest {
    @Mock
    private AlarmQueryRepository alarmQueryRepository;
    @InjectMocks
    private AlarmReadService alarmReadService;

    @Test
    public void AlarmReadAllTest() throws Exception {
        // given
        Long memberId = 1L;

        // when & then
        assertDoesNotThrow(() -> alarmReadService.readAll(memberId));
        verify(alarmQueryRepository, times(1)).read(any());
    }
}
