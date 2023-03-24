package com.dongnebook.global.test;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.jpa.mapping.JpaMetamodelMappingContext;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(HelloController.class)
@MockBean(JpaMetamodelMappingContext.class)
@AutoConfigureMockMvc(addFilters = false)
public class HelloControllerTest {

 @Autowired
 private MockMvc mockMvc;

 @Test
 public void helloControllerTest() throws Exception {
  // given

  // when
  ResultActions actions =
          mockMvc.perform(
                  get("/")
                          .accept(MediaType.valueOf("text/plain;charset=UTF-8"))
          );

  // then
  actions
          .andExpect(status().isOk())
          .andExpect(content().contentType(MediaType.valueOf("text/plain;charset=UTF-8")));
 }
}


