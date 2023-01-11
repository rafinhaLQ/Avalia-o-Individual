package uol.compass.ms.order.service;

import java.util.List;

import uol.compass.ms.order.model.dto.request.ItemRequestDTO;
import uol.compass.ms.order.model.entities.ItemEntity;

public interface ItemService {
    
    List<ItemEntity> createItems(List<ItemRequestDTO> items);

}
