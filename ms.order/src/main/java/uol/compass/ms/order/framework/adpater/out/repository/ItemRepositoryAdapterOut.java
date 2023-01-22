package uol.compass.ms.order.framework.adpater.out.repository;

import java.time.LocalDate;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;
import uol.compass.ms.order.application.port.out.ItemRepositoryPortOut;
import uol.compass.ms.order.domain.model.entities.ItemEntity;
import uol.compass.ms.order.framework.exceptions.ItemNotFoundException;

@Repository
@RequiredArgsConstructor
public class ItemRepositoryAdapterOut implements ItemRepositoryPortOut {

    private final ItemMySqlRepository mySqlRepository;

    @Override
    public ItemEntity save(ItemEntity item) {
        return mySqlRepository.save(item);
    }

    @Override
    public Page<ItemEntity> findAllItems(Pageable pageable) {
        return mySqlRepository.findAll(pageable);
    }

    @Override
    public ItemEntity findById(Long id) {
        return mySqlRepository.findById(id).orElseThrow(ItemNotFoundException::new);
    }

    @Override
    public ItemEntity findItemByExceptId(
        String name,
        LocalDate creationDate,
        LocalDate expiratonDate,
        Double value,
        String description
    ) {
        return mySqlRepository.findByNameAndCreationDateAndExpirationDateAndValueAndDescription(
            name,
            creationDate,
            expiratonDate,
            value,
            description
        );
    }
}
