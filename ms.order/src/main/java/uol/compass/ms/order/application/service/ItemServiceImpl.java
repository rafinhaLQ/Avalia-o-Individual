package uol.compass.ms.order.application.service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import uol.compass.ms.order.application.port.in.ItemService;
import uol.compass.ms.order.domain.dto.request.ItemRequestDTO;
import uol.compass.ms.order.domain.model.entities.ItemEntity;
import uol.compass.ms.order.framework.adpater.out.repositories.ItemRepository;
import uol.compass.ms.order.framework.exceptions.InvalidDateException;

@Service
@RequiredArgsConstructor
public class ItemServiceImpl implements ItemService {

    private final ModelMapper mapper;
    private final ItemRepository itemRepository;

    @Override
    public List<ItemEntity> createItems(List<ItemRequestDTO> items) {
        items.forEach(
            item -> {
                if (item.getExpirationDate().isBefore(item.getCreationDate())) throw new InvalidDateException();
            }
        );

        List<ItemEntity> itemsAlreadySaved = new ArrayList<>();
        List<ItemEntity> itemsNotSaved = items
            .stream()
            .map(item -> mapper.map(item, ItemEntity.class))
            .collect(Collectors.toList());

        itemsNotSaved.forEach(
            item -> {
                if (
                    itemRepository.findByNameAndCreationDateAndExpirationDateAndValueAndDescription(
                        item.getName(),
                        item.getCreationDate(),
                        item.getExpirationDate(),
                        item.getValue(),
                        item.getDescription()
                    ) !=
                    null
                ) {
                    itemsAlreadySaved.add(
                        itemRepository.findByNameAndCreationDateAndExpirationDateAndValueAndDescription(
                            item.getName(),
                            item.getCreationDate(),
                            item.getExpirationDate(),
                            item.getValue(),
                            item.getDescription()
                        )
                    );
                } else {
                    ItemEntity itemJustSaved = itemRepository.save(item);
                    itemsAlreadySaved.add(itemJustSaved);
                }
            }
        );

        return itemsAlreadySaved;
    }

    @Override
    public Double getTotalValue(List<ItemRequestDTO> items) {
        return items.stream().mapToDouble(ItemRequestDTO::getValue).sum();
    }
}
