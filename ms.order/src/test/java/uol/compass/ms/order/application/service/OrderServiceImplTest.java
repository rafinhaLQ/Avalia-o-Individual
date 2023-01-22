package uol.compass.ms.order.application.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.List;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import uol.compass.ms.order.application.port.out.OrderRepositoryPortOut;
import uol.compass.ms.order.builder.ScenarioBuilder;
import uol.compass.ms.order.domain.dto.request.OrderRequestDTO;
import uol.compass.ms.order.domain.dto.request.OrderUpdateRequestDTO;
import uol.compass.ms.order.domain.dto.response.OrderResponseDTO;
import uol.compass.ms.order.domain.model.entities.AddressEntity;
import uol.compass.ms.order.domain.model.entities.ItemEntity;
import uol.compass.ms.order.domain.model.entities.OrderEntity;
import uol.compass.ms.order.framework.adpater.out.event.TopicProducer;

@ExtendWith(MockitoExtension.class)
public class OrderServiceImplTest {

    private static final Long ID = 1L;

    private static final String CPF = "09963606547";

    @InjectMocks
    private OrderServiceImpl orderService;

    @Mock
    private OrderRepositoryPortOut orderRepository;

    @Mock
    private AddressServiceImpl addressService;

    @Mock
    private ItemServiceImpl itemService;

    @Mock
    private TopicProducer topicProducer;

    @Spy
    private ModelMapper mapper;

    @Test
    void shouldCreateOrder_sucess() {
        OrderRequestDTO request = ScenarioBuilder.builOrderRequestDTO();
        OrderEntity order = ScenarioBuilder.buildOrderEntity();
        ItemEntity item = ScenarioBuilder.buildItemEntity();
        AddressEntity address = ScenarioBuilder.builAddressEntity();

        when(itemService.findById(any())).thenReturn(item);
        when(addressService.createAddressWithCep(any(), any())).thenReturn(address);
        when(orderRepository.save(any())).thenReturn(order);
        doNothing().when(topicProducer).send(any());

        OrderResponseDTO response = orderService.create(request);

        assertNotNull(response);
        assertEquals(CPF, response.getCpf());
        verify(itemService, times(2)).findById(any());
        verify(addressService).createAddressWithCep(any(), any());
        verify(orderRepository).save(any());
        verify(topicProducer).send(any());
    }

    @Test
    void shouldFindAllOrders_sucess() {
        Pageable pageable = ScenarioBuilder.buildPageable();
        OrderEntity order = ScenarioBuilder.buildOrderEntity();
        Page<OrderEntity> pageDTO = new PageImpl<>(List.of(order));

        when(orderRepository.findAllOrders(any())).thenReturn(pageDTO);

        Page<OrderResponseDTO> page = orderService.findAll(null, pageable);

        assertNotNull(page);
        assertEquals(CPF, page.getContent().get(0).getCpf());
        verify(orderRepository).findAllOrders(any());
    }

    @Test
    void shouldFindAllOrders_filterWithCpf() {
        Pageable pageable = ScenarioBuilder.buildPageable();
        OrderEntity order = ScenarioBuilder.buildOrderEntity();
        Page<OrderEntity> pageDTO = new PageImpl<>(List.of(order));

        when(orderRepository.findAllOrdersByCpf(any(), any())).thenReturn(pageDTO);

        Page<OrderResponseDTO> page = orderService.findAll(CPF, pageable);

        assertNotNull(page);
        assertEquals(CPF, page.getContent().get(0).getCpf());
        verify(orderRepository).findAllOrdersByCpf(any(), any());
    }

    @Test
    void shouldFindOrderById_sucess() {
        OrderEntity order = ScenarioBuilder.buildOrderEntity();

        when(orderRepository.findById(any())).thenReturn(order);

        OrderResponseDTO response = orderService.findById(ID);

        assertNotNull(response);
        assertEquals(CPF, response.getCpf());
        verify(orderRepository).findById(any());
    }

    @Test
    void shouldUpdateItemsOnOrder_sucess() {
        OrderEntity order = ScenarioBuilder.buildOrderEntity();
        ItemEntity item = ScenarioBuilder.buildItemEntity();

        when(orderRepository.findById(any())).thenReturn(order);
        when(itemService.findById(any())).thenReturn(item);
        when(orderRepository.save(any())).thenReturn(order);
        doNothing().when(topicProducer).send(any());

        OrderResponseDTO response = orderService.updateItems(ID, List.of(ID));

        assertNotNull(response);
        assertEquals(CPF, response.getCpf());
        verify(orderRepository).findById(any());
        verify(itemService, times(2)).findById(any());
        verify(orderRepository).save(any());
        verify(topicProducer).send(any());
    }

    @Test
    void shouldUpdateOrder_sucess() {
        OrderEntity order = ScenarioBuilder.buildOrderEntity();
        OrderUpdateRequestDTO request = ScenarioBuilder.buildOrderUpdateRequestDTO();
        AddressEntity address = ScenarioBuilder.builAddressEntity();

        when(orderRepository.findById(any())).thenReturn(order);
        when(addressService.createAddressWithCep(any(), any())).thenReturn(address);
        when(orderRepository.save(any())).thenReturn(order);

        OrderResponseDTO response = orderService.update(ID, request);

        assertNotNull(response);
        assertEquals(CPF, response.getCpf());
        verify(orderRepository).findById(any());
        verify(addressService).createAddressWithCep(any(), any());
        verify(orderRepository).save(any());
    }

    @Test
    void shouldDeleteOrder_sucess() {
        OrderEntity order = ScenarioBuilder.buildOrderEntity();

        when(orderRepository.findById(any())).thenReturn(order);
        doNothing().when(orderRepository).delete(any());

        orderService.delete(ID);

        verify(orderRepository).findById(any());
        verify(orderRepository).delete(any());
    }
}
