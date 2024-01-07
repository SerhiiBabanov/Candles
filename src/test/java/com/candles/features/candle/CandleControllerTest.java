package com.candles.features.candle;

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
class CandleControllerTest {
    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private CandleService candleService;
    private CandleEntity candleEntity;

    @BeforeEach
    void setUp() {
        candleEntity = InitTestDataService.getCandleEntity();
    }

    @Test
    void CandleController_GetAllCandles_ReturnResponseWithLinkAndPagination() throws Exception {
        given(candleService.getAllCandles(any(), any())).willReturn(new PageImpl<>(Collections.singletonList(candleEntity)));

        ResultActions response = mockMvc.perform(get("/api/public/candles")
                .contentType(MediaType.APPLICATION_JSON)
                .param("local", "EN"));

        response.andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$._embedded.candles[0].id", CoreMatchers.is(candleEntity.getId())))
                .andExpect(MockMvcResultMatchers.jsonPath("$.page.totalElements", CoreMatchers.is(1)))
                .andExpect(MockMvcResultMatchers.jsonPath("$._links", CoreMatchers.notNullValue()));
    }

    @Test
    void CandleController_GetCandleById_ReturnResponseWithLink() throws Exception {
        given(candleService.getCandleById(any())).willReturn(Optional.ofNullable(candleEntity));

        ResultActions response = mockMvc.perform(get("/api/public/candles/" + candleEntity.getId())
                .contentType(MediaType.APPLICATION_JSON)
                .param("local", "EN"));

        response.andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id", CoreMatchers.is(candleEntity.getId())))
                .andExpect(MockMvcResultMatchers.jsonPath("$._links", CoreMatchers.notNullValue()));
    }
}
