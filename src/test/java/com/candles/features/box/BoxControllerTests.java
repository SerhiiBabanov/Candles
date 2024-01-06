package com.candles.features.box;

import com.candles.service.InitTestDataService;
import org.hamcrest.CoreMatchers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.PageImpl;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.Collections;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;

@SpringBootTest
@AutoConfigureMockMvc(addFilters = false)
@ExtendWith(MockitoExtension.class)
public class BoxControllerTests {
    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private BoxService boxService;
    private BoxEntity boxEntity;

    @BeforeEach
    void setUp() {
        boxEntity = InitTestDataService.getBoxEntity();
    }

    @Test
    public void BoxController_GetAllBoxes_ReturnResponseWithLinkAndPagination() throws Exception {
        given(boxService.getAllBoxes(any(), any())).willReturn(new PageImpl<>(Collections.singletonList(boxEntity)));

        ResultActions response = mockMvc.perform(get("/api/public/boxes")
                .contentType(MediaType.APPLICATION_JSON)
                .param("local", "EN"));

        response.andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$._embedded.boxes[0].id", CoreMatchers.is(boxEntity.getId())))
                .andExpect(MockMvcResultMatchers.jsonPath("$.page.totalElements", CoreMatchers.is(1)))
                .andExpect(MockMvcResultMatchers.jsonPath("$._links", CoreMatchers.notNullValue()));
    }

    @Test
    public void BoxController_GetBoxById_ReturnResponseWithLink() throws Exception {
        given(boxService.getBoxById(any())).willReturn(Optional.ofNullable(boxEntity));

        ResultActions response = mockMvc.perform(get("/api/public/boxes/" + boxEntity.getId())
                .contentType(MediaType.APPLICATION_JSON)
                .param("local", "EN"));

        response.andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id", CoreMatchers.is(boxEntity.getId())))
                .andExpect(MockMvcResultMatchers.jsonPath("$._links", CoreMatchers.notNullValue()));
    }
}
