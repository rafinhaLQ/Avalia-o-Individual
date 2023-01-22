package uol.compass.ms.order.application.port.out;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import uol.compass.ms.order.domain.model.entities.OrderEntity;

public interface OrderRepositoryPortOut {
    OrderEntity save(OrderEntity order);

    Page<OrderEntity> findAllOrders(Pageable pageable);

    Page<OrderEntity> findAllOrdersByCpf(String cpf, Pageable pageable);

    OrderEntity findById(Long id);

    void delete(Long id);
}
