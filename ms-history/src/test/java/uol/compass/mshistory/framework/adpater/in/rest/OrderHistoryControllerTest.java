package uol.compass.mshistory.framework.adpater.in.rest;

import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import java.util.List;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import uol.compass.mshistory.application.service.OrderHistoryServiceImpl;
import uol.compass.mshistory.builder.ScenarioBuilder;
import uol.compass.mshistory.domain.dto.response.OrderHistoryResponseDTO;

@WebMvcTest(controllers = OrderHistoryController.class)
public class OrderHistoryControllerTest {

    public static final String BASE_URL = "/historico";

    @Autowired
    private MockMvc mvc;

    @MockBean
    private OrderHistoryServiceImpl historyService;

    @Test
    void findAll() throws Exception {
        OrderHistoryResponseDTO responseDTO = ScenarioBuilder.buildOrderHistoryResponseDTO();
        Page<OrderHistoryResponseDTO> page = new PageImpl<>(List.of(responseDTO));

        when(historyService.findAll(any(), any(), any())).thenReturn(page);

        MvcResult result = mvc
            .perform(
                MockMvcRequestBuilders
                    .get(BASE_URL)
                    .accept(MediaType.APPLICATION_JSON)
                    .contentType(MediaType.APPLICATION_JSON)
            )
            .andReturn();

        MockHttpServletResponse response = result.getResponse();

        assertEquals(HttpStatus.OK.value(), response.getStatus());
    }
}
