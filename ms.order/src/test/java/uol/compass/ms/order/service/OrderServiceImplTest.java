package uol.compass.ms.order.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
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

import uol.compass.ms.order.builder.ScenarioBuilder;
import uol.compass.ms.order.model.dto.request.OrderRequestDTO;
import uol.compass.ms.order.model.dto.response.OrderResponseDTO;
import uol.compass.ms.order.model.entities.AddressEntity;
import uol.compass.ms.order.model.entities.ItemEntity;
import uol.compass.ms.order.model.entities.OrderEntity;
import uol.compass.ms.order.repositories.OrderRepository;
import uol.compass.ms.order.service.impl.AddressServiceImpl;
import uol.compass.ms.order.service.impl.ItemServiceImpl;
import uol.compass.ms.order.service.impl.OrderServiceImpl;

@ExtendWith(MockitoExtension.class)
public class OrderServiceImplTest {
    
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

}
