package uol.compass.ms.order.application.port.in;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import uol.compass.ms.order.domain.dto.request.ItemRequestDTO;
import uol.compass.ms.order.domain.dto.response.ItemResponseDTO;
import uol.compass.ms.order.domain.model.entities.ItemEntity;

public interface ItemService {
    ItemResponseDTO create(ItemRequestDTO item);

    Page<ItemResponseDTO> findAll(Pageable pageable);

    ItemEntity findById(Long id);
}
