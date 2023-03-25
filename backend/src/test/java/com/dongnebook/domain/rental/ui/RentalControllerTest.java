package com.dongnebook.domain.rental.ui;


import com.dongnebook.domain.book.application.port.in.response.BookInfoResponse;
import com.dongnebook.domain.book.domain.BookState;
import com.dongnebook.domain.model.Location;
import com.dongnebook.domain.rental.application.RentalService;
import com.dongnebook.domain.rental.domain.RentalState;
import com.dongnebook.domain.rental.dto.response.RentalBookResponse;
import com.dongnebook.domain.rental.dto.response.RentalInfoResponse;
import com.dongnebook.global.dto.request.PageRequestImpl;
import lombok.EqualsAndHashCode;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
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

import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.startsWith;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.doNothing;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(RentalController.class)
@MockBean(JpaMetamodelMappingContext.class)
//@AutoConfigureMockMvc(addFilters = false)
public class RentalControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private RentalService rentalService;

    @Test
    @WithMockUser
    public void postRentalTest() throws Exception {
        // given
        long bookId = 1L;
        doNothing().when(rentalService).createRental(Mockito.any(Long.class), Mockito.any(Long.class));

        // when
        ResultActions actions =
                mockMvc.perform(
                        post("/rental/" + bookId)
                                .with(csrf())
                );

        // then
        actions
                .andExpect(status().isCreated());
//                .andExpect(header().string("Location", startsWith("/rental/")));
    }

    @Test
    @WithMockUser
    public void cancelRentalByCustomerTest() throws Exception {
        // given
        Long rentalId = 1L;
        doNothing().when(rentalService).cancelRentalByCustomer(Mockito.any(Long.class), Mockito.any(Long.class));

        // when
        ResultActions actions =
                mockMvc.perform(
                        patch("/rental/" + rentalId + "/cancelByCustomer")
                                .with(csrf())
                );

        // then
        actions
                .andExpect(status().isOk());
    }

    @Test
    @WithMockUser
    public void cancelRentalByMerchantTest() throws Exception {
        // given
        Long rentalId = 1L;
        doNothing().when(rentalService).cancelRentalByMerchant(any(Long.class), any(Long.class));

        // when
        ResultActions actions =
                mockMvc.perform(
                        patch("/rental/" + rentalId + "/cancelByMerchant")
                                .with(csrf())
                );

        // then
        actions
                .andExpect(status().isOk());
    }

    @Test
    @WithMockUser
    public void receiveBookTest() throws Exception {
        // given
        Long rentalId = 1L;
        doNothing().when(rentalService).receiveBook(any(Long.class), any(Long.class));

        // when
        ResultActions actions =
                mockMvc.perform(
                        patch("/rental/" + rentalId + "/receive")
                                .with(csrf())
                );

        // then
        actions
                .andExpect(status().isOk());
    }

    @Test
    @WithMockUser
    public void returnRentalTest() throws Exception {
        // given
        Long rentalId = 1L;
        doNothing().when(rentalService).returnRental(any(Long.class), any(Long.class));

        // when
        ResultActions actions =
                mockMvc.perform(
                        patch("/rental/" + rentalId + "/return")
                                .with(csrf())
                );

        // then
        actions
                .andExpect(status().isOk());
    }

    @Test
    @WithMockUser
    public void getRentalsByMerchantTest() throws Exception {
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

        given(rentalService.getRentalsByMerchant(any(Long.class), any(String.class), any(PageRequestImpl.class)))
                .willReturn(rentalBookResponseSlice);


        // when
        ResultActions actions =
                mockMvc.perform(
                        get("/rental/from")
                                .param("index", "2")
                                .accept(MediaType.APPLICATION_JSON)
                                .with(csrf())
                );

        // then
        actions
                .andExpect(status().isOk());
//                .andExpect(jsonPath("$.content").isArray());
    }

    @Test
    @WithMockUser
    public void getRentalsByCustomerTest() throws Exception {
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

        given(rentalService.getRentalsByMerchant(
                any(Long.class), any(String.class), any(PageRequestImpl.class)))
                .willReturn(rentalBookResponseSlice);


        // when
        ResultActions actions =
                mockMvc.perform(
                        get("/rental/to")
                                .param("index", "2")
                                .accept(MediaType.APPLICATION_JSON)
                                .with(csrf())
                );

        // then
        actions
                .andExpect(status().isOk());
    }

}
