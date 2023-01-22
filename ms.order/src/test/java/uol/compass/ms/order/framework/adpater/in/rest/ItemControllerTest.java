package uol.compass.ms.order.framework.adpater.in.rest;

import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
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
import uol.compass.ms.order.application.service.ItemServiceImpl;
import uol.compass.ms.order.builder.ScenarioBuilder;
import uol.compass.ms.order.domain.dto.request.ItemRequestDTO;
import uol.compass.ms.order.domain.dto.response.ItemResponseDTO;
import uol.compass.ms.order.utils.TestUtils;

@WebMvcTest(controllers = ItemController.class)
public class ItemControllerTest {

    public static final String BASE_URL = "/itens";

    @Autowired
    private MockMvc mvc;

    @MockBean
    private ItemServiceImpl itemService;

    @Test
    void create() throws Exception {
        ItemRequestDTO request = ScenarioBuilder.builItemRequestDTO();
        ItemResponseDTO responseDTO = ScenarioBuilder.buildItemResponseDTO();

        when(itemService.create(any())).thenReturn(responseDTO);

        String input = TestUtils.mapToJson(request);

        MvcResult result = mvc
            .perform(
                MockMvcRequestBuilders
                    .post(BASE_URL)
                    .accept(MediaType.APPLICATION_JSON)
                    .content(input)
                    .contentType(MediaType.APPLICATION_JSON)
            )
            .andReturn();

        MockHttpServletResponse response = result.getResponse();

        assertEquals(HttpStatus.CREATED.value(), response.getStatus());
        verify(itemService).create(any());
    }

    @Test
    void findAll() throws Exception {
        ItemResponseDTO responseDTO = ScenarioBuilder.buildItemResponseDTO();
        Page<ItemResponseDTO> pageDTO = new PageImpl<>(List.of(responseDTO));

        when(itemService.findAll(any())).thenReturn(pageDTO);

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
        verify(itemService).findAll(any());
    }
}
