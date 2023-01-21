package uol.compass.mshistory.application.port.out;

import java.time.LocalDate;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import uol.compass.mshistory.domain.model.OrderHistory;

public interface OrderHistoryRepositoryPortOut {
    OrderHistory save(OrderHistory orderHistory);

    OrderHistory findByOrderId(Long orderId);

    Page<OrderHistory> findAllOrders(Pageable pageable);

    Page<OrderHistory> findAllOrdersAfter(LocalDate startDate, Pageable pageable);

    Page<OrderHistory> findAllOrdersBefore(LocalDate endDate, Pageable pageable);

    Page<OrderHistory> findAllOrdersBetween(LocalDate starDate, LocalDate endDate, Pageable pageable);
}
