package uol.compass.mshistory.framework.adpater.out.repository;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.time.LocalDate;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import uol.compass.mshistory.builder.ScenarioBuilder;
import uol.compass.mshistory.domain.model.OrderHistory;

@ExtendWith(MockitoExtension.class)
public class OrderHistoryRepositoryAdapterOutTest {

    private static final Long ID = 1L;

    private static final LocalDate START_DATE = LocalDate.of(2023, 2, 20);

    private static final LocalDate END_DATE = LocalDate.of(2023, 2, 22);

    @InjectMocks
    private OrderHistoryRepositoryAdapterOut orderHistoryRepository;

    @Mock
    private OrderHistoryMongoRepository mongoRepository;

    @Test
    void save() {
        OrderHistory orderHistory = ScenarioBuilder.buildOrderHistory();

        when(mongoRepository.save(any())).thenReturn(orderHistory);

        OrderHistory history = orderHistoryRepository.save(orderHistory);

        assertNotNull(history);
        assertEquals("63c701fe06236c4192ef38ed", history.getId());
        verify(mongoRepository).save(any());
    }

    @Test
    void findByOrderId() {
        OrderHistory orderHistory = ScenarioBuilder.buildOrderHistory();

        when(mongoRepository.findByOrderId(any())).thenReturn(orderHistory);

        OrderHistory history = orderHistoryRepository.findByOrderId(ID);

        assertNotNull(history);
        assertEquals("63c701fe06236c4192ef38ed", history.getId());
    }

    @Test
    void findAllOrders() {
        Pageable pageable = PageRequest.of(0, 20);
        OrderHistory orderHistory = ScenarioBuilder.buildOrderHistory();
        Page<OrderHistory> pageDTO = new PageImpl<>(List.of(orderHistory));

        when(mongoRepository.findAll((Pageable) any())).thenReturn(pageDTO);

        Page<OrderHistory> page = orderHistoryRepository.findAllOrders(pageable);

        assertNotNull(page);
        assertEquals("63c701fe06236c4192ef38ed", page.getContent().get(0).getId());
    }

    @Test
    void findAllOrdersAfter() {
        Pageable pageable = PageRequest.of(0, 20);
        OrderHistory orderHistory = ScenarioBuilder.buildOrderHistory();
        Page<OrderHistory> pageDTO = new PageImpl<>(List.of(orderHistory));

        when(mongoRepository.findByOrderDateGreaterThan(any(), (Pageable) any())).thenReturn(pageDTO);

        Page<OrderHistory> page = orderHistoryRepository.findAllOrdersAfter(START_DATE, pageable);

        assertNotNull(page);
        assertEquals("63c701fe06236c4192ef38ed", page.getContent().get(0).getId());
    }

    @Test
    void findAllOrdersBefore() {
        Pageable pageable = PageRequest.of(0, 20);
        OrderHistory orderHistory = ScenarioBuilder.buildOrderHistory();
        Page<OrderHistory> pageDTO = new PageImpl<>(List.of(orderHistory));

        when(mongoRepository.findByOrderDateLessThan(any(), (Pageable) any())).thenReturn(pageDTO);

        Page<OrderHistory> page = orderHistoryRepository.findAllOrdersBefore(END_DATE, pageable);

        assertNotNull(page);
        assertEquals("63c701fe06236c4192ef38ed", page.getContent().get(0).getId());
    }

    @Test
    void findAllOrdersBetween() {
        Pageable pageable = PageRequest.of(0, 20);
        OrderHistory orderHistory = ScenarioBuilder.buildOrderHistory();
        Page<OrderHistory> pageDTO = new PageImpl<>(List.of(orderHistory));

        when(mongoRepository.findByOrderDateBetween(any(), any(), (Pageable) any())).thenReturn(pageDTO);

        Page<OrderHistory> page = orderHistoryRepository.findAllOrdersBetween(START_DATE, END_DATE, pageable);

        assertNotNull(page);
        assertEquals("63c701fe06236c4192ef38ed", page.getContent().get(0).getId());
    }
}
