package uol.compass.mshistory.application.port.in;

import java.time.LocalDate;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import uol.compass.mshistory.domain.dto.request.OrderHistoryRequestDTO;
import uol.compass.mshistory.domain.dto.response.OrderHistoryResponseDTO;

public interface OrderHistoryService {
    void create(OrderHistoryRequestDTO request);

    Page<OrderHistoryResponseDTO> findAll(LocalDate startDate, LocalDate endDate, Pageable pageable);
}
