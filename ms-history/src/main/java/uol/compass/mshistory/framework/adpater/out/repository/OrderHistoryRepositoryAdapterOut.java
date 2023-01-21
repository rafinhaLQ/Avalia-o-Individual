package uol.compass.mshistory.framework.adpater.out.repository;

import java.time.LocalDate;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;
import uol.compass.mshistory.application.port.out.OrderHistoryRepositoryPortOut;
import uol.compass.mshistory.domain.model.OrderHistory;

@Repository
@RequiredArgsConstructor
public class OrderHistoryRepositoryAdapterOut implements OrderHistoryRepositoryPortOut {

    private final OrderHistoryMongoRepository mongoRepository;

    @Override
    public OrderHistory save(OrderHistory orderHistory) {
        return mongoRepository.save(orderHistory);
    }

    @Override
    public OrderHistory findByOrderId(Long orderId) {
        return mongoRepository.findByOrderId(orderId);
    }

    @Override
    public Page<OrderHistory> findAllOrders(Pageable pageable) {
        return mongoRepository.findAll(pageable);
    }

    @Override
    public Page<OrderHistory> findAllOrdersAfter(LocalDate startDate, Pageable pageable) {
        return mongoRepository.findByOrderDateGreaterThan(startDate, pageable);
    }

    @Override
    public Page<OrderHistory> findAllOrdersBefore(LocalDate endDate, Pageable pageable) {
        return mongoRepository.findByOrderDateLessThan(endDate, pageable);
    }

    @Override
    public Page<OrderHistory> findAllOrdersBetween(LocalDate starDate, LocalDate endDate, Pageable pageable) {
        return mongoRepository.findByOrderDateBetween(starDate, endDate, pageable);
    }
}
