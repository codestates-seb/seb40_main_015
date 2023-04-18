package com.dongnebook.domain.dibs.ui;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.BDDMockito.*;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

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
import com.dongnebook.domain.dibs.service.DibsService;
import com.dongnebook.domain.model.Location;

@WebMvcTest(DibsController.class)
@MockBean(JpaMetamodelMappingContext.class)
class DibsControllerTest {
    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private DibsService dibsService;

    static String accessToken = "Bearer eyJhbGciOiJIUzUxMiJ9.eyJyb2xlcyI6W3siYXV0aG9yaXR5IjoiUk9MRV9VU0VSIn1dLCJpZCI6MywiZXhwIjoxNjcwMDM4OTIyfQ.rFjbQ9R1Dtoz1r81xtAmUzudiBduihDSvZ9sE8yW2XgwBjyGIJHsEm71DSxN6Wy9abCDc1NsBxo1URy00LTWZg";

    @Test
    @WithMockUser
    void dibsFindAllTest() throws Exception {
        // given
        Long id = 1L;

        // when
        ResultActions actions =
                mockMvc.perform(
                        post("/dibs/" + id)
                                .param("Authorization", accessToken)
                                .with(csrf())
                );

        // then
        actions.equals(null);
    }

    @Test
    @WithMockUser
    void findAllMyDibsTest() throws Exception {
        // given
        BookSimpleResponse bookSimpleResponse1 = new BookSimpleResponse(
                1L, "자바의 정석1", BookState.RENTABLE, "aa1@aaa.com", Money.of(1000),
                new Location(37.5340, 126.7064), "test1"
        );
        BookSimpleResponse bookSimpleResponse2 = new BookSimpleResponse(
                2L, "자바의 정석2", BookState.RENTABLE, "aa2@aaa.com", Money.of(1000),
                new Location(37.5340, 126.7064), "test1"
        );
        SliceImpl<BookSimpleResponse> bookSimpleResponseSlice = new SliceImpl<>(
                List.of(bookSimpleResponse1, bookSimpleResponse2)
        );

        given(dibsService.findAll(any(), any()))
                .willReturn(bookSimpleResponseSlice);

        // when
        ResultActions actions =
                mockMvc.perform(
                        get("/dibs/")
                                .param("Authorization", accessToken)
                                .accept(MediaType.APPLICATION_JSON)
                                .with(csrf())
                );

        // then
        actions
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content").isArray())
                .andExpect(jsonPath("$.content[0].bookId")
                        .value(bookSimpleResponseSlice.getContent().get(0).getBookId()))
                .andExpect(jsonPath("$.content[1].title")
                        .value(bookSimpleResponseSlice.getContent().get(1).getTitle()))
                .andExpect(jsonPath("$.numberOfElements").value(bookSimpleResponseSlice.getSize()));
    }
}
