package uol.compass.mshistory.application.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.time.LocalDate;
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
import uol.compass.mshistory.application.port.out.OrderHistoryRepositoryPortOut;
import uol.compass.mshistory.builder.ScenarioBuilder;
import uol.compass.mshistory.domain.dto.request.OrderHistoryRequestDTO;
import uol.compass.mshistory.domain.dto.response.OrderHistoryResponseDTO;
import uol.compass.mshistory.domain.model.OrderHistory;

@ExtendWith(MockitoExtension.class)
public class OrderHistoryServiceImplTest {

    private static final LocalDate START_DATE = LocalDate.of(2023, 2, 20);

    private static final LocalDate END_DATE = LocalDate.of(2023, 2, 22);

    private static final String HISTORY_ID = "63c701fe06236c4192ef38ed";

    @InjectMocks
    private OrderHistoryServiceImpl historyService;

    @Mock
    private OrderHistoryRepositoryPortOut historyRepository;

    @Spy
    private ModelMapper mapper;

    @Test
    void shouldCreateOrderHistory_sucess() {
        OrderHistory order = ScenarioBuilder.buildOrderHistory();
        OrderHistoryRequestDTO request = ScenarioBuilder.buildOrderHistoryRequestDTO();

        when(historyRepository.save(any())).thenReturn(order);

        historyService.create(request);

        verify(historyRepository).save(any());
    }

    @Test
    void shouldCreateOrderHistory_updateOrder() {
        OrderHistory order = ScenarioBuilder.buildOrderHistory();
        OrderHistoryRequestDTO request = ScenarioBuilder.buildOrderHistoryRequestDTO();

        when(historyRepository.findByOrderId(any())).thenReturn(order);
        when(historyRepository.save(any())).thenReturn(order);

        historyService.create(request);

        verify(historyRepository, times(2)).findByOrderId(any());
        verify(historyRepository).save(any());
    }

    @Test
    void shouldFindAllOrdersHistorys_sucess() {
        Pageable pageable = ScenarioBuilder.buildPageable();
        OrderHistory order = ScenarioBuilder.buildOrderHistory();
        Page<OrderHistory> pageDTO = new PageImpl<>(List.of(order));

        when(historyRepository.findAllOrders(any())).thenReturn(pageDTO);

        Page<OrderHistoryResponseDTO> page = historyService.findAll(null, null, pageable);

        assertNotNull(page);
        assertEquals(HISTORY_ID, page.getContent().get(0).getId());
        verify(historyRepository).findAllOrders(any());
    }

    @Test
    void shouldFindAllOrdersHistorys_FilterWithStartDateAndEndDate() {
        Pageable pageable = ScenarioBuilder.buildPageable();
        OrderHistory order = ScenarioBuilder.buildOrderHistory();
        Page<OrderHistory> pageDTO = new PageImpl<>(List.of(order));

        when(historyRepository.findAllOrdersBetween(any(), any(), any())).thenReturn(pageDTO);

        Page<OrderHistoryResponseDTO> page = historyService.findAll(START_DATE, END_DATE, pageable);

        assertNotNull(page);
        assertEquals(HISTORY_ID, page.getContent().get(0).getId());
        verify(historyRepository).findAllOrdersBetween(any(), any(), any());
    }

    @Test
    void shouldFindAllOrdersHistorys_FilterWithStartDate() {
        Pageable pageable = ScenarioBuilder.buildPageable();
        OrderHistory order = ScenarioBuilder.buildOrderHistory();
        Page<OrderHistory> pageDTO = new PageImpl<>(List.of(order));

        when(historyRepository.findAllOrdersAfter(any(), any())).thenReturn(pageDTO);

        Page<OrderHistoryResponseDTO> page = historyService.findAll(START_DATE, null, pageable);

        assertNotNull(page);
        assertEquals(HISTORY_ID, page.getContent().get(0).getId());
        verify(historyRepository).findAllOrdersAfter(any(), any());
    }

    @Test
    void shouldFindAllOrdersHistorys_FilterWithEndDate() {
        Pageable pageable = ScenarioBuilder.buildPageable();
        OrderHistory order = ScenarioBuilder.buildOrderHistory();
        Page<OrderHistory> pageDTO = new PageImpl<>(List.of(order));

        when(historyRepository.findAllOrdersBefore(any(), any())).thenReturn(pageDTO);

        Page<OrderHistoryResponseDTO> page = historyService.findAll(null, END_DATE, pageable);

        assertNotNull(page);
        assertEquals(HISTORY_ID, page.getContent().get(0).getId());
        verify(historyRepository).findAllOrdersBefore(any(), any());
    }
}
