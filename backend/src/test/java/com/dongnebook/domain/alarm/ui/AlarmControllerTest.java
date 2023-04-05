package com.dongnebook.domain.alarm.ui;

import com.dongnebook.domain.alarm.application.AlarmReadService;
import com.dongnebook.domain.alarm.application.AlarmService;
import com.dongnebook.domain.alarm.dto.AlarmResponse;
import com.dongnebook.global.enums.AlarmType;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.jpa.mapping.JpaMetamodelMappingContext;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

@WebMvcTest(AlarmController.class)
@MockBean(JpaMetamodelMappingContext.class)
public class AlarmControllerTest {
    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private AlarmService alarmService;

    static String accessToken = "Bearer eyJhbGciOiJIUzUxMiJ9.eyJyb2xlcyI6W3siYXV0aG9yaXR5IjoiUk9MRV9VU0VSIn1dLCJpZCI6MywiZXhwIjoxNjcwMDM4OTIyfQ.rFjbQ9R1Dtoz1r81xtAmUzudiBduihDSvZ9sE8yW2XgwBjyGIJHsEm71DSxN6Wy9abCDc1NsBxo1URy00LTWZg";

    @Test
    @WithMockUser
    public void getAlarmTest() throws Exception {
        // given
        AlarmResponse alarmResponse = new AlarmResponse(
                1L, AlarmType.RENTAL, 1L, "JAVA의 정석", true
        );
        List<AlarmResponse> alarmResponseList = List.of(alarmResponse);
        given(alarmService.getMyAlarm(any())).willReturn(alarmResponseList);

        // when
        ResultActions actions =
                mockMvc.perform(
                        get("/alarm")
                                .param("Authorization", accessToken)
                                .accept(MediaType.APPLICATION_JSON)
                                .with(csrf())
                );

        // then
        actions
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$[0].alarmId").value(alarmResponse.getAlarmId()))
                .andExpect(jsonPath("$[0].alarmType").value(alarmResponse.getAlarmType().toString()));
    }

    @Test
    @WithMockUser
    public void deleteAlarmTest() throws Exception {
        // given
        doNothing().when(alarmService).deleteAllById(any());

        // when
        mockMvc.perform(
                delete("/alarm")
                        .param("Authorization", accessToken)
                        .with(csrf())
        );

        // then
        verify(alarmService, times(1)).deleteAllById(any());
    }

    @Test
    @WithMockUser
    public void subscribeTest() throws Exception {
        // given
        long memberId = 1L;
        SseEmitter sseEmitter = new SseEmitter(30L);
        given(alarmService.sub(any(), any())).willReturn(sseEmitter);

        // when
        ResultActions actions =
                mockMvc.perform(
                        get("/sub/" + memberId)
                                .param("lastEventId", "1")
                                .accept(MediaType.TEXT_EVENT_STREAM)
                                .with(csrf())
                );

        // then
        verify(alarmService, times(1)).sub(any(), any());
    }

    @Test
    @WithMockUser
    public void deleteSpecificAlarmTest() throws Exception {
        // given
        long alarmId = 1L;
        doNothing().when(alarmService).deleteAlarmById(any(), any());

        // when
        mockMvc.perform(
                delete("/alarm/" + alarmId)
                        .param("Authorization", accessToken)
                        .with(csrf())
        );

        // then
        verify(alarmService, times(1)).deleteAlarmById(any(), any());
    }
}
