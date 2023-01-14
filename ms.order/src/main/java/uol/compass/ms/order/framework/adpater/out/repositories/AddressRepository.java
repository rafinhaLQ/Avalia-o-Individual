package uol.compass.ms.order.framework.adpater.out.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import uol.compass.ms.order.domain.model.entities.AddressEntity;

public interface AddressRepository extends JpaRepository<AddressEntity, Long> {
    AddressEntity findByCepAndNumber(String cep, Integer number);
}
