package uol.compass.ms.order.framework.adpater.in.rest;

import static org.junit.jupiter.api.Assertions.assertEquals;
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
import uol.compass.ms.order.application.service.OrderServiceImpl;
import uol.compass.ms.order.builder.ScenarioBuilder;
import uol.compass.ms.order.domain.dto.request.ItemRequestDTO;
import uol.compass.ms.order.domain.dto.request.OrderRequestDTO;
import uol.compass.ms.order.domain.dto.request.OrderUpdateRequestDTO;
import uol.compass.ms.order.domain.dto.response.OrderResponseDTO;
import uol.compass.ms.order.utils.TestUtils;

@WebMvcTest(controllers = OrderController.class)
public class OrderControllerTest {

    public static final String BASE_URL = "/pedidos";

    public static final String ID_URL = BASE_URL + "/1";

    public static final String ITEMS_URL = BASE_URL + "/itens/1";

    @Autowired
    private MockMvc mvc;

    @MockBean
    private OrderServiceImpl orderService;

    @Test
    void create() throws Exception {
        OrderRequestDTO request = ScenarioBuilder.builOrderRequestDTO();
        OrderResponseDTO responseDTO = ScenarioBuilder.buildOrderResponseDTO();

        when(orderService.create(any())).thenReturn(responseDTO);

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
    }

    @Test
    void findAll() throws Exception {
        OrderResponseDTO responseDTO = ScenarioBuilder.buildOrderResponseDTO();
        Page<OrderResponseDTO> pageDTO = new PageImpl<>(List.of(responseDTO));

        when(orderService.findAll(any(), any())).thenReturn(pageDTO);

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

    @Test
    void findById() throws Exception {
        OrderResponseDTO responseDTO = ScenarioBuilder.buildOrderResponseDTO();

        when(orderService.findById(any())).thenReturn(responseDTO);

        MvcResult result = mvc
            .perform(
                MockMvcRequestBuilders
                    .get(ID_URL)
                    .accept(MediaType.APPLICATION_JSON)
                    .contentType(MediaType.APPLICATION_JSON)
            )
            .andReturn();

        MockHttpServletResponse response = result.getResponse();

        assertEquals(HttpStatus.OK.value(), response.getStatus());
    }

    @Test
    void updateItens() throws Exception {
        List<ItemRequestDTO> request = ScenarioBuilder.buildListOfItemRequestDTOs();
        OrderResponseDTO responseDTO = ScenarioBuilder.buildOrderResponseDTO();

        when(orderService.updateItems(any(), any())).thenReturn(responseDTO);

        String input = TestUtils.mapToJson(request);

        MvcResult result = mvc
            .perform(
                MockMvcRequestBuilders
                    .patch(ITEMS_URL)
                    .accept(MediaType.APPLICATION_JSON)
                    .content(input)
                    .contentType(MediaType.APPLICATION_JSON)
            )
            .andReturn();

        MockHttpServletResponse response = result.getResponse();

        assertEquals(HttpStatus.OK.value(), response.getStatus());
    }

    @Test
    void update() throws Exception {
        OrderUpdateRequestDTO request = ScenarioBuilder.buildOrderUpdateRequestDTO();
        OrderResponseDTO responseDTO = ScenarioBuilder.buildOrderResponseDTO();

        when(orderService.update(any(), any())).thenReturn(responseDTO);

        String input = TestUtils.mapToJson(request);

        MvcResult result = mvc
            .perform(
                MockMvcRequestBuilders
                    .put(ID_URL)
                    .accept(MediaType.APPLICATION_JSON)
                    .content(input)
                    .contentType(MediaType.APPLICATION_JSON)
            )
            .andReturn();

        MockHttpServletResponse response = result.getResponse();

        assertEquals(HttpStatus.OK.value(), response.getStatus());
    }

    @Test
    void delete() throws Exception {
        MvcResult result = mvc
            .perform(
                MockMvcRequestBuilders
                    .delete(ID_URL)
                    .accept(MediaType.APPLICATION_JSON)
                    .contentType(MediaType.APPLICATION_JSON)
            )
            .andReturn();

        MockHttpServletResponse response = result.getResponse();

        assertEquals(HttpStatus.NO_CONTENT.value(), response.getStatus());
    }
}
