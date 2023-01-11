package uol.compass.ms.order.repositories;

import java.time.LocalDate;

import org.springframework.data.jpa.repository.JpaRepository;

import uol.compass.ms.order.model.entities.ItemEntity;

public interface ItemRepository extends JpaRepository<ItemEntity, Long> {
    ItemEntity findByNameAndCreationDateAndExpirationDateAndValueAndDescription(String name, LocalDate creationDate, LocalDate expirationDate, Double value, String description);
}
