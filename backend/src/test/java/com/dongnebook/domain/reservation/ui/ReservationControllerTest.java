package com.dongnebook.domain.reservation.ui;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.BDDMockito.*;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.time.LocalDateTime;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.SliceImpl;
import org.springframework.data.jpa.mapping.JpaMetamodelMappingContext;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import com.dongnebook.domain.book.application.port.in.response.BookSimpleResponse;
import com.dongnebook.domain.book.domain.BookState;
import com.dongnebook.domain.book.domain.Money;
import com.dongnebook.domain.model.Location;
import com.dongnebook.domain.reservation.application.ReservationService;
import com.dongnebook.domain.reservation.dto.response.ReservationInfoResponse;
import com.dongnebook.domain.reservation.dto.response.ReservationResponse;

@WebMvcTest(ReservationsController.class)
@MockBean(JpaMetamodelMappingContext.class)
class ReservationControllerTest {
    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private ReservationService reservationService;

    static String accessToken = "Bearer eyJhbGciOiJIUzUxMiJ9.eyJyb2xlcyI6W3siYXV0aG9yaXR5IjoiUk9MRV9VU0VSIn1dLCJpZCI6MywiZXhwIjoxNjcwMDM4OTIyfQ.rFjbQ9R1Dtoz1r81xtAmUzudiBduihDSvZ9sE8yW2XgwBjyGIJHsEm71DSxN6Wy9abCDc1NsBxo1URy00LTWZg";

    @Test
    @WithMockUser
    void postReservationTest() throws Exception {
        // given
        long bookId = 1L;
        doNothing().when(reservationService).createReservation(any(), any());

        // when
        ResultActions actions =
                mockMvc.perform(
                        post("/reservation/" + bookId)
                                .param("Authorization", accessToken)
                                .with(csrf())
                );

        // then
        actions
                .andExpect(status().isCreated());
    }

    @Test
    @WithMockUser
    void getReservationAvailabilityTest() throws Exception {
        // given
        long bookId = 1L;
        Boolean result = true;
        given(reservationService.checkReservationAvailability(any(), any())).willReturn(result);

        // when
        ResultActions actions =
                mockMvc.perform(
                        get("/reservation/check/" + bookId)
                                .param("Authorization", accessToken)
                                .accept(MediaType.ALL)
                                .with(csrf())
                );

        // then
        actions
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").value(result));
    }

    @Test
    @WithMockUser
    void getReservationsTest() throws Exception {
        // given
        ReservationInfoResponse reservationInfoResponse = new ReservationInfoResponse(
            new ReservationResponse(1L, LocalDateTime.now()),
                new BookSimpleResponse(1L, "수학의 정석", BookState.RENTABLE, "abc@abvc.com",
                        Money.of(100), new Location(37.5340, 126.7064), "merchant")
        );
        SliceImpl<ReservationInfoResponse> sliceImpl = new SliceImpl<>(List.of(reservationInfoResponse));

        given(reservationService.readReservations(any(), any())).willReturn(sliceImpl);

        // when
        ResultActions actions =
                mockMvc.perform(
                        get("/reservation/")
                                .accept(MediaType.APPLICATION_JSON)
                                .param("Authorization", accessToken)
                                .with(csrf())
                );

        // then
        actions
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content").isArray())
                .andExpect(jsonPath("$.content[0].reservationInfo.reservationId")
                        .value(reservationInfoResponse.getReservationInfo().getReservationId()))
                .andExpect(jsonPath("$.content[0].bookInfo.bookId")
                        .value(reservationInfoResponse.getBookInfo().getBookId()))
                .andExpect(jsonPath("$.numberOfElements").value(sliceImpl.getSize()));
    }

    @Test
    @WithMockUser
    void deleteReservationTest() throws Exception {
        // given
        long reservationId = 1L;

        doNothing().when(reservationService).cancelReservation(any(), any());

        // when
        ResultActions actions =
                mockMvc.perform(
                        delete("/reservation/cancel/" + reservationId)
                                .param("Authorization", accessToken)
                                .with(csrf())
                );

        // then
        actions
                .andExpect(status().isNoContent());
    }

}
