package uol.compass.ms.order.framework.adpater.out.repository;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import uol.compass.ms.order.builder.ScenarioBuilder;
import uol.compass.ms.order.domain.model.entities.OrderEntity;
import uol.compass.ms.order.framework.exceptions.OrderNotFoundException;

@ExtendWith(MockitoExtension.class)
public class OrderRepositoryAdapterOutTest {

    private static final Long ID = 1L;

    private static final String CPF = "09963606547";

    @InjectMocks
    private OrderRepositoryAdapterOut orderRepository;

    @Mock
    private OrderMySqlRepository mySqlRepository;

    @Test
    void save() {
        OrderEntity orderDTO = ScenarioBuilder.buildOrderEntity();

        when(mySqlRepository.save(any())).thenReturn(orderDTO);

        OrderEntity order = orderRepository.save(orderDTO);

        assertNotNull(order);
        assertEquals(CPF, order.getCpf());
        verify(mySqlRepository).save(any());
    }

    @Test
    void findAllOrders() {
        Pageable pageable = ScenarioBuilder.buildPageable();
        OrderEntity order = ScenarioBuilder.buildOrderEntity();
        Page<OrderEntity> pageDTO = new PageImpl<>(List.of(order));

        when(mySqlRepository.findAll((Pageable) any())).thenReturn(pageDTO);

        Page<OrderEntity> page = orderRepository.findAllOrders(pageable);

        assertNotNull(page);
        assertEquals(CPF, page.getContent().get(0).getCpf());
        verify(mySqlRepository).findAll((Pageable) any());
    }

    @Test
    void findAllOrdersByCpf() {
        Pageable pageable = ScenarioBuilder.buildPageable();
        OrderEntity order = ScenarioBuilder.buildOrderEntity();
        Page<OrderEntity> pageDTO = new PageImpl<>(List.of(order));

        when(mySqlRepository.findByCpf(any(), (Pageable) any())).thenReturn(pageDTO);

        Page<OrderEntity> page = orderRepository.findAllOrdersByCpf(CPF, pageable);

        assertNotNull(page);
        assertEquals(CPF, page.getContent().get(0).getCpf());
        verify(mySqlRepository).findByCpf(any(), (Pageable) any());
    }

    @Test
    void findById() {
        OrderEntity orderDTO = ScenarioBuilder.buildOrderEntity();

        when(mySqlRepository.findById(any())).thenReturn(Optional.of(orderDTO));

        OrderEntity order = orderRepository.findById(ID);

        assertNotNull(order);
        assertEquals(CPF, order.getCpf());
        verify(mySqlRepository).findById(any());
    }

    @Test
    void findById_OrderNotFoundException() {
        assertThrows(
            OrderNotFoundException.class,
            () -> {
                orderRepository.findById(ID);
            }
        );
    }

    @Test
    void delete() {
        doNothing().when(mySqlRepository).deleteById(any());

        orderRepository.delete(ID);

        verify(mySqlRepository).deleteById(any());
    }
}
