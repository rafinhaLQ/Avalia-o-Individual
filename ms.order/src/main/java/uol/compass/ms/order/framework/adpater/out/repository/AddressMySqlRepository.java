package uol.compass.ms.order.framework.adpater.out.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import uol.compass.ms.order.domain.model.entities.AddressEntity;

public interface AddressMySqlRepository extends JpaRepository<AddressEntity, Long> {
    AddressEntity findByCepAndNumber(String cep, Integer number);
}
