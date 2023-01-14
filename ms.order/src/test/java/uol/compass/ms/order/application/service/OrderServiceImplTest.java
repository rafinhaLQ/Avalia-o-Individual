package uol.compass.ms.order.application.service;

import static org.junit.Assert.assertThrows;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.List;
import java.util.Optional;
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
import uol.compass.ms.order.builder.ScenarioBuilder;
import uol.compass.ms.order.domain.dto.request.ItemRequestDTO;
import uol.compass.ms.order.domain.dto.request.OrderRequestDTO;
import uol.compass.ms.order.domain.dto.request.OrderUpdateRequestDTO;
import uol.compass.ms.order.domain.dto.response.OrderResponseDTO;
import uol.compass.ms.order.domain.model.entities.AddressEntity;
import uol.compass.ms.order.domain.model.entities.ItemEntity;
import uol.compass.ms.order.domain.model.entities.OrderEntity;
import uol.compass.ms.order.framework.adpater.out.repositories.OrderRepository;
import uol.compass.ms.order.framework.exceptions.OrderNotFoundException;

@ExtendWith(MockitoExtension.class)
public class OrderServiceImplTest {

    public static final Long ID = 1L;

    @InjectMocks
    private OrderServiceImpl orderService;

    @Mock
    private OrderRepository orderRepository;

    @Mock
    private AddressServiceImpl addressService;

    @Mock
    private ItemServiceImpl itemService;

    @Spy
    private ModelMapper mapper;

    @Test
    void shouldCreateOrder_sucess() {
        OrderRequestDTO request = ScenarioBuilder.builOrderRequestDTO();
        OrderEntity order = ScenarioBuilder.buildOrderEntity();
        List<ItemEntity> itemEntities = ScenarioBuilder.buildListOfItemEntities();
        AddressEntity address = ScenarioBuilder.builAddressEntity();

        when(itemService.createItems(any())).thenReturn(itemEntities);
        when(addressService.createAddressWithCep(any(), any())).thenReturn(address);
        when(orderRepository.save(any())).thenReturn(order);

        OrderResponseDTO response = orderService.create(request);

        assertNotNull(response);
        assertEquals("09963606547", response.getCpf());
        verify(orderRepository).save(any());
    }

    @Test
    void shouldFindAllOrders_sucess() {
        OrderEntity order = ScenarioBuilder.buildOrderEntity();
        Page<OrderEntity> pageDTO = new PageImpl<>(List.of(order));

        when(orderRepository.findAll((Pageable) any())).thenReturn(pageDTO);

        Page<OrderResponseDTO> page = orderService.findAll(null, any(Pageable.class));

        assertNotNull(page);
        assertEquals("09963606547", page.getContent().get(0).getCpf());
    }

    @Test
    void shouldFindAllOrders_filterWithCpf() {
        OrderEntity order = ScenarioBuilder.buildOrderEntity();
        Page<OrderEntity> pageDTO = new PageImpl<>(List.of(order));

        when(orderRepository.findByCpf(any(), (Pageable) any())).thenReturn(pageDTO);

        Page<OrderResponseDTO> page = orderService.findAll(anyString(), any(Pageable.class));

        assertNotNull(page);
        assertEquals("09963606547", page.getContent().get(0).getCpf());
    }

    @Test
    void shouldFindOrderById_sucess() {
        OrderEntity order = ScenarioBuilder.buildOrderEntity();

        when(orderRepository.findById(any())).thenReturn(Optional.of(order));

        OrderResponseDTO response = orderService.findById(ID);

        assertNotNull(response);
        assertEquals("09963606547", response.getCpf());
    }

    @Test
    void shouldFindOrderById_OrderNotFoundException() {
        assertThrows(
            OrderNotFoundException.class,
            () -> {
                orderService.findById(ID);
            }
        );
    }

    @Test
    void shouldUpdateItemsOnOrder_sucess() {
        OrderEntity order = ScenarioBuilder.buildOrderEntity();
        List<ItemEntity> itemEntities = ScenarioBuilder.buildListOfItemEntities();
        List<ItemRequestDTO> itemRequests = ScenarioBuilder.buildListOfItemRequestDTOs();

        when(orderRepository.findById(any())).thenReturn(Optional.of(order));
        when(itemService.createItems(any())).thenReturn(itemEntities);
        when(orderRepository.save(any())).thenReturn(order);

        OrderResponseDTO response = orderService.updateItems(ID, itemRequests);

        assertNotNull(response);
        assertEquals("09963606547", response.getCpf());
        verify(orderRepository).save(any());
    }

    @Test
    void shouldUpdateOrder_sucess() {
        OrderEntity order = ScenarioBuilder.buildOrderEntity();
        OrderUpdateRequestDTO request = ScenarioBuilder.buildOrderUpdateRequestDTO();
        AddressEntity address = ScenarioBuilder.builAddressEntity();

        when(orderRepository.findById(any())).thenReturn(Optional.of(order));
        when(addressService.createAddressWithCep(any(), any())).thenReturn(address);
        when(orderRepository.save(any())).thenReturn(order);

        OrderResponseDTO response = orderService.update(ID, request);

        assertNotNull(response);
        assertEquals("09963606547", response.getCpf());
        verify(orderRepository).save(any());
    }

    @Test
    void shouldDeleteOrder_sucess() {
        OrderEntity order = ScenarioBuilder.buildOrderEntity();

        when(orderRepository.findById(any())).thenReturn(Optional.of(order));

        orderService.delete(ID);

        verify(orderRepository).deleteById(any());
    }
}
