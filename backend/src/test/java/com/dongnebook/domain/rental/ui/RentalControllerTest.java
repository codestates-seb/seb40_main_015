package com.dongnebook.domain.rental.ui;

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

import com.dongnebook.domain.book.application.port.in.response.BookInfoResponse;
import com.dongnebook.domain.book.domain.BookState;
import com.dongnebook.domain.model.Location;
import com.dongnebook.domain.rental.application.RentalService;
import com.dongnebook.domain.rental.domain.RentalState;
import com.dongnebook.domain.rental.dto.response.RentalBookResponse;
import com.dongnebook.domain.rental.dto.response.RentalInfoResponse;

@WebMvcTest(RentalController.class)
@MockBean(JpaMetamodelMappingContext.class)
class RentalControllerTest {
    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private RentalService rentalService;

    static String accessToken = "Bearer eyJhbGciOiJIUzUxMiJ9.eyJyb2xlcyI6W3siYXV0aG9yaXR5IjoiUk9MRV9VU0VSIn1dLCJpZCI6MywiZXhwIjoxNjcwMDM4OTIyfQ.rFjbQ9R1Dtoz1r81xtAmUzudiBduihDSvZ9sE8yW2XgwBjyGIJHsEm71DSxN6Wy9abCDc1NsBxo1URy00LTWZg";

    @Test
    @WithMockUser
   void postRentalTest() throws Exception {
        // given
        long bookId = 1L;
        doNothing().when(rentalService).createRental(any(), any());

        // when
        ResultActions actions =
                mockMvc.perform(
                        post("/rental/" + bookId)
                                .param("Authorization", accessToken)
                                .with(csrf())
                );

        // then
        actions
                .andExpect(status().isCreated());
    }

    @Test
    @WithMockUser
   void cancelRentalByCustomerTest() throws Exception {
        // given
        Long rentalId = 1L;
        doNothing().when(rentalService).cancelRentalByCustomer(any(), any());

        // when
        ResultActions actions =
                mockMvc.perform(
                        patch("/rental/" + rentalId + "/cancelByCustomer")
                                .param("Authorization", accessToken)
                                .with(csrf())
                );

        // then
        actions
                .andExpect(status().isOk());
    }

    @Test
    @WithMockUser
   void cancelRentalByMerchantTest() throws Exception {
        // given
        Long rentalId = 1L;
        doNothing().when(rentalService).cancelRentalByMerchant(any(), any());

        // when
        ResultActions actions =
                mockMvc.perform(
                        patch("/rental/" + rentalId + "/cancelByMerchant")
                                .param("Authorization", accessToken)
                                .with(csrf())
                );

        // then
        actions
                .andExpect(status().isOk());
    }

    @Test
    @WithMockUser
    void receiveBookTest() throws Exception {
        // given
        Long rentalId = 1L;
        doNothing().when(rentalService).receiveBook(any(), any());

        // when
        ResultActions actions =
                mockMvc.perform(
                        patch("/rental/" + rentalId + "/receive")
                                .param("Authorization", accessToken)
                                .with(csrf())
                );

        // then
        actions
                .andExpect(status().isOk());
    }

    @Test
    @WithMockUser
    void returnRentalTest() throws Exception {
        // given
        Long rentalId = 1L;
        doNothing().when(rentalService).returnRental(any(), any());

        // when
        ResultActions actions =
                mockMvc.perform(
                        patch("/rental/" + rentalId + "/return")
                                .param("Authorization", accessToken)
                                .with(csrf())
                );

        // then
        actions
                .andExpect(status().isOk());
    }

    @Test
    @WithMockUser
    void getRentalsByMerchantTest() throws Exception {
        // given
        LocalDateTime rentalStartedAt = LocalDateTime.now();
        LocalDateTime rentalDeadline = rentalStartedAt.plusDays(9);

        BookInfoResponse bookInfoResponse1 =
                new BookInfoResponse(
                        1L, "IMG_1.jpg", "자바의 정석1","남궁성", "도우출판", 1500,
                        "apple is delicious", new Location(37.5340, 126.7064), BookState.TRADING, "jujang", 1L
                );
        BookInfoResponse bookInfoResponse2 =
                new BookInfoResponse(
                        2L, "IMG_2.jpg", "자바의 정석2","남궁성", "도우출판", 1500,
                        "banana is cool", new Location(37.5340, 126.7064), BookState.TRADING, "jujang", 1L
                );
        RentalInfoResponse rentalInfoResponse1 =
                new RentalInfoResponse(
                        8L, "jj", RentalState.TRADING, rentalStartedAt, rentalDeadline, null, null
                );
        RentalInfoResponse rentalInfoResponse2 =
                new RentalInfoResponse(
                        9L, "jj", RentalState.TRADING, rentalStartedAt, rentalDeadline, null, null
                );

        SliceImpl<RentalBookResponse> rentalBookResponseSlice =
                new SliceImpl<>(
                        List.of(
                                new RentalBookResponse(bookInfoResponse1, rentalInfoResponse1),
                                new RentalBookResponse(bookInfoResponse2, rentalInfoResponse2)
                        )
                );

        given(rentalService.getRentalsByMerchant(any(), any(), any()))
                .willReturn(rentalBookResponseSlice);


        // when
        ResultActions actions =
                mockMvc.perform(
                        get("/rental/from")
                                .param("Authorization", accessToken)
                                .accept(MediaType.APPLICATION_JSON)
                                .with(csrf())
                );

        // then
        actions
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content").isArray())
                .andExpect(jsonPath("$.content[0].bookInfo.bookId")
                        .value(rentalBookResponseSlice.getContent().get(0).getBookInfo().getBookId()))
                .andExpect(jsonPath("$.content[0].rentalInfo.rentalId")
                        .value(rentalBookResponseSlice.getContent().get(0).getRentalInfo().getRentalId()))
                .andExpect(jsonPath("$.numberOfElements").value(rentalBookResponseSlice.getSize()));
    }

    @Test
    @WithMockUser
    void getRentalsByCustomerTest() throws Exception {
        // given
        LocalDateTime rentalStartedAt = LocalDateTime.now();
        LocalDateTime rentalDeadline = rentalStartedAt.plusDays(9);

        BookInfoResponse bookInfoResponse1 =
                new BookInfoResponse(
                        1L, "IMG_1.jpg", "자바의 정석1","남궁성", "도우출판", 1500,
                        "apple is delicious", new Location(37.5340, 126.7064), BookState.TRADING, "jujang", 1L
                );
        BookInfoResponse bookInfoResponse2 =
                new BookInfoResponse(
                        2L, "IMG_2.jpg", "자바의 정석2","남궁성", "도우출판", 1500,
                        "banana is cool", new Location(37.5340, 126.7064), BookState.TRADING, "jujang", 1L
                );
        RentalInfoResponse rentalInfoResponse1 =
                new RentalInfoResponse(
                        8L, "jj", RentalState.TRADING, rentalStartedAt, rentalDeadline, null, null
                );
        RentalInfoResponse rentalInfoResponse2 =
                new RentalInfoResponse(
                        9L, "jj", RentalState.TRADING, rentalStartedAt, rentalDeadline, null, null
                );

        SliceImpl<RentalBookResponse> rentalBookResponseSlice =
                new SliceImpl<>(
                        List.of(
                                new RentalBookResponse(bookInfoResponse1, rentalInfoResponse1),
                                new RentalBookResponse(bookInfoResponse2, rentalInfoResponse2)
                        )
                );

        given(rentalService.getRentalsByCustomer(any(), any(), any()))
                .willReturn(rentalBookResponseSlice);


        // when
        ResultActions actions =
                mockMvc.perform(
                        get("/rental/to")
                                .param("Authorization", accessToken)
                                .accept(MediaType.APPLICATION_JSON)
                                .with(csrf())
                );

        // then
        actions
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content").isArray())
                .andExpect(jsonPath("$.content[0].bookInfo.bookId")
                        .value(rentalBookResponseSlice.getContent().get(0).getBookInfo().getBookId()))
                .andExpect(jsonPath("$.content[0].rentalInfo.rentalId")
                        .value(rentalBookResponseSlice.getContent().get(0).getRentalInfo().getRentalId()))
                .andExpect(jsonPath("$.numberOfElements").value(rentalBookResponseSlice.getSize()));
    }

}
