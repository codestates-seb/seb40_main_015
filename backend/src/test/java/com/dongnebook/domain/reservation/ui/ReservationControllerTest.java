package com.dongnebook.domain.reservation.ui;

import com.dongnebook.domain.book.application.port.in.response.BookSimpleResponse;
import com.dongnebook.domain.book.domain.BookState;
import com.dongnebook.domain.book.domain.Money;
import com.dongnebook.domain.model.Location;
import com.dongnebook.domain.reservation.application.ReservationService;
import com.dongnebook.domain.reservation.dto.response.ReservationInfoResponse;
import com.dongnebook.domain.reservation.dto.response.ReservationResponse;
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

import java.time.LocalDateTime;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.doNothing;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(ReservationsController.class)
@MockBean(JpaMetamodelMappingContext.class)
//@AutoConfigureMockMvc(addFilters = false)
//@EqualsAndHashCode
public class ReservationControllerTest {
    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private ReservationService reservationService;

    @Test
    @WithMockUser
    public void postReservationTest() throws Exception {
        // given
        long bookId = 1L;
        doNothing().when(reservationService).createReservation(any(), any());

        // when
        ResultActions actions =
                mockMvc.perform(
                        post("/reservation/" + bookId)
                                .param("Authorization", "Bearer eyJhbGciOiJIUzUxMiJ9.eyJyb2xlcyI6W3siYXV0aG9yaXR5IjoiUk9MRV9VU0VSIn1dLCJpZCI6MywiZXhwIjoxNjcwMDM4OTIyfQ.rFjbQ9R1Dtoz1r81xtAmUzudiBduihDSvZ9sE8yW2XgwBjyGIJHsEm71DSxN6Wy9abCDc1NsBxo1URy00LTWZg")
                                .with(csrf())
                );

        // then
        actions
                .andExpect(status().isCreated());
    }

    @Test
    @WithMockUser
    public void getReservationsTest() throws Exception {
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
    public void deleteReservationTest() throws Exception {
        // given
        Long reservationId = 1L;

        doNothing().when(reservationService).cancelReservation(any(), any());

        // when
        ResultActions actions =
                mockMvc.perform(
                        delete("/reservation/cancel/" + reservationId)
                                .param("Authorization", "Bearer eyJhbGciOiJIUzUxMiJ9.eyJyb2xlcyI6W3siYXV0aG9yaXR5IjoiUk9MRV9VU0VSIn1dLCJpZCI6MywiZXhwIjoxNjcwMDM4OTIyfQ.rFjbQ9R1Dtoz1r81xtAmUzudiBduihDSvZ9sE8yW2XgwBjyGIJHsEm71DSxN6Wy9abCDc1NsBxo1URy00LTWZg")
                                .with(csrf())
                );

        // then
        actions
                .andExpect(status().isNoContent());
    }

}
