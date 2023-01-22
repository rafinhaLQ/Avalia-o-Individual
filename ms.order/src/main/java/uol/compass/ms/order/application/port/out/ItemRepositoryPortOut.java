package uol.compass.ms.order.application.port.out;

import java.time.LocalDate;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import uol.compass.ms.order.domain.model.entities.ItemEntity;

public interface ItemRepositoryPortOut {
    ItemEntity save(ItemEntity item);

    Page<ItemEntity> findAllItems(Pageable pageable);

    ItemEntity findById(Long id);

    ItemEntity findItemByExceptId(
        String name,
        LocalDate creationDate,
        LocalDate expiratonDate,
        Double value,
        String description
    );
}
