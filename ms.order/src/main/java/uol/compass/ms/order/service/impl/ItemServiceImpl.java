package uol.compass.ms.order.service.impl;

import java.util.Collections;
import java.util.List;

import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import uol.compass.ms.order.model.dto.request.ItemRequestDTO;
import uol.compass.ms.order.model.entities.ItemEntity;
import uol.compass.ms.order.service.ItemService;

@Service
@RequiredArgsConstructor
public class ItemServiceImpl implements ItemService {

    @Override
    public List<ItemEntity> createItems(List<ItemRequestDTO> items) {
        return Collections.emptyList();
    }
    
}
