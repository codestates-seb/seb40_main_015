package com.dongnebook.domain.review.ui;

import com.dongnebook.domain.review.application.ReviewService;
import com.dongnebook.domain.review.dto.request.ReviewRequest;
import com.dongnebook.domain.review.dto.response.ReviewResponse;
import com.dongnebook.global.dto.request.PageRequestImpl;
import com.google.gson.Gson;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.SliceImpl;
import org.springframework.data.jpa.mapping.JpaMetamodelMappingContext;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(ReviewController.class)
@MockBean(JpaMetamodelMappingContext.class)
public class ReviewControllerTest {
    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private ReviewService reviewService;
    @Autowired
    private Gson gson;

    static String accessToken = "Bearer eyJhbGciOiJIUzUxMiJ9.eyJyb2xlcyI6W3siYXV0aG9yaXR5IjoiUk9MRV9VU0VSIn1dLCJpZCI6MywiZXhwIjoxNjcwMDM4OTIyfQ.rFjbQ9R1Dtoz1r81xtAmUzudiBduihDSvZ9sE8yW2XgwBjyGIJHsEm71DSxN6Wy9abCDc1NsBxo1URy00LTWZg";

    @Test
    @WithMockUser
    public void postReviewTest() throws Exception {
        // given
        Long rentalId = 1L;
        ReviewRequest reviewRequest = ReviewRequest.builder()
                .bookId(1L)
                .reviewMessage("good")
                .grade(1L)
                .build();
        String content = gson.toJson(reviewRequest);
        doNothing().when(reviewService).createReview(any(), any(), any());

        // when
        ResultActions actions =
                mockMvc.perform(
                        post("/review/" + rentalId)
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(content)
                                .param("Authorization", accessToken)
                                .with(csrf())
                );

        // then
        actions
                .andExpect(status().isCreated());
    }

    @Test
    @WithMockUser
    public void getReviewsTest() throws Exception {
        // given
        Long merchantId = 2L;
        ReviewResponse reviewResponse =
                new ReviewResponse(
                        1L, merchantId, "tester", "aa@aa.com", "good",
//                        5L, "JAVA의 정석", "aa@aa.com", 1L, 1L
                        5L, "aa@aa.com", 1L, 1L
                );
        SliceImpl<ReviewResponse> reviewResponseSlice =
                new SliceImpl<>(List.of(reviewResponse));

        when(reviewService.readReviews(any(), any())).thenReturn(reviewResponseSlice);

        // when
        ResultActions actions =
                mockMvc.perform(
                        get("/review/" + merchantId)
                                .accept(MediaType.APPLICATION_JSON)
                                .with(csrf())
                );

        // then
        actions
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content").isArray())
                .andExpect(jsonPath("$.content[0].reviewId").value(reviewResponse.getReviewId()))
                .andExpect(jsonPath("$.numberOfElements").value(reviewResponseSlice.getSize()));
    }

}
