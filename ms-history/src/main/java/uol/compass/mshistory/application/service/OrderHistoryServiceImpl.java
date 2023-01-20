package uol.compass.mshistory.application.service;

import java.time.LocalDate;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import uol.compass.mshistory.application.port.in.OrderHistoryService;
import uol.compass.mshistory.domain.dto.request.OrderHistoryRequestDTO;
import uol.compass.mshistory.domain.dto.response.OrderHistoryResponseDTO;
import uol.compass.mshistory.domain.model.OrderHistory;
import uol.compass.mshistory.framework.adpater.out.OrderHistoryRepository;

@Slf4j
@Service
@RequiredArgsConstructor
public class OrderHistoryServiceImpl implements OrderHistoryService {

    private final OrderHistoryRepository historyRepository;
    private final ModelMapper mapper;

    @Override
    public void create(OrderHistoryRequestDTO request) {
        if (historyRepository.findByOrderId(request.getId()) != null) {
            OrderHistory history = historyRepository.findByOrderId(request.getId());

            history.setTotal(request.getTotal());

            historyRepository.save(history);

            log.info("Order history updated");

            return;
        }

        OrderHistory history = new OrderHistory();
        history.setOrderId(request.getId());
        history.setTotal(request.getTotal());
        history.setOrderDate(LocalDate.now());

        historyRepository.save(history);

        log.info("Order history created");
    }

    @Override
    public Page<OrderHistoryResponseDTO> findAll(LocalDate startDate, LocalDate endDate, Pageable pageable) {
        Page<OrderHistory> page;

        if (startDate != null && endDate != null) {
            page = historyRepository.findByOrderDateBetween(startDate, endDate, pageable);
        } else if (startDate != null) {
            page = historyRepository.findByOrderDateGreaterThan(startDate, pageable);
        } else if (endDate != null) {
            page = historyRepository.findByOrderDateLessThan(endDate, pageable);
        } else {
            page = historyRepository.findAll(pageable);
        }

        log.info("Historico de pedidos buscados no banco");

        return page.map(history -> mapper.map(history, OrderHistoryResponseDTO.class));
    }
}
