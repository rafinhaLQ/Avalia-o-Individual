package uol.compass.ms.order.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import uol.compass.ms.order.model.dto.request.ItemRequestDTO;
import uol.compass.ms.order.model.entities.ItemEntity;
import uol.compass.ms.order.repositories.ItemRepository;
import uol.compass.ms.order.service.ItemService;

@Service
@RequiredArgsConstructor
public class ItemServiceImpl implements ItemService {

    private final ModelMapper mapper;
    private final ItemRepository itemRepository;

    @Override
    public List<ItemEntity> createItems(List<ItemRequestDTO> items) {
        List<ItemEntity> itemsAlreadySaved = new ArrayList<>();
        List<ItemEntity> itemsNotSaved = items.stream()
                        .map(item -> mapper.map(item, ItemEntity.class))
                        .collect(Collectors.toList());

        itemsNotSaved.forEach(item -> {
            if(itemRepository.findByNameAndCreationDateAndExpirationDateAndValueAndDescription(item.getName(), item.getCreationDate(), item.getExpirationDate(), item.getValue(), item.getDescription()) != null) {
                itemsAlreadySaved.add(itemRepository.findByNameAndCreationDateAndExpirationDateAndValueAndDescription(item.getName(), item.getCreationDate(), item.getExpirationDate(), item.getValue(), item.getDescription()));
            } else {
                ItemEntity itemJustSaved = itemRepository.save(item);
                itemsAlreadySaved.add(itemJustSaved);
            }
        });

        return itemsAlreadySaved;
    }
    
}
