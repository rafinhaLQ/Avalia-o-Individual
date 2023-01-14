package uol.compass.ms.order.framework.adpater.out.repositories;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import uol.compass.ms.order.domain.model.entities.OrderEntity;

public interface OrderRepository extends JpaRepository<OrderEntity, Long> {
    Page<OrderEntity> findByCpf(String cpf, Pageable pageable);
}
