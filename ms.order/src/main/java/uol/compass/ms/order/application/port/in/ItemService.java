package uol.compass.ms.order.application.port.in;

import java.util.List;

import uol.compass.ms.order.domain.dto.request.ItemRequestDTO;
import uol.compass.ms.order.domain.model.entities.ItemEntity;

public interface ItemService {
    List<ItemEntity> createItems(List<ItemRequestDTO> items);

    Double getTotalValue(List<ItemRequestDTO> items);
}
