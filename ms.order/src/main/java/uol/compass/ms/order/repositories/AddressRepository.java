package uol.compass.ms.order.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import uol.compass.ms.order.model.entities.AddressEntity;

public interface AddressRepository extends JpaRepository<AddressEntity, Long> {
    AddressEntity findByCepAndNumber(String cep, Integer number);
}
