package uol.compass.ms.order.application.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import uol.compass.ms.order.application.port.in.ItemService;
import uol.compass.ms.order.application.port.out.ItemRepositoryPortOut;
import uol.compass.ms.order.domain.dto.request.ItemRequestDTO;
import uol.compass.ms.order.domain.dto.response.ItemResponseDTO;
import uol.compass.ms.order.domain.model.entities.ItemEntity;
import uol.compass.ms.order.framework.exceptions.InvalidDateException;

@Slf4j
@Service
@RequiredArgsConstructor
public class ItemServiceImpl implements ItemService {

    private final ModelMapper mapper;
    private final ItemRepositoryPortOut itemRepository;

    @Override
    public ItemResponseDTO create(ItemRequestDTO request) {
        log.info("Starting the method to create item");

        if (request.getExpirationDate().isBefore(request.getCreationDate())) throw new InvalidDateException();

        if (
            itemRepository.findItemByExceptId(
                request.getName(),
                request.getCreationDate(),
                request.getExpirationDate(),
                request.getValue(),
                request.getDescription()
            ) !=
            null
        ) {
            ItemEntity item = itemRepository.findItemByExceptId(
                request.getName(),
                request.getCreationDate(),
                request.getExpirationDate(),
                request.getValue(),
                request.getDescription()
            );
            return mapper.map(item, ItemResponseDTO.class);
        }

        ItemEntity item = itemRepository.save(mapper.map(request, ItemEntity.class));

        log.info("Item created on database");

        return mapper.map(item, ItemResponseDTO.class);
    }

    @Override
    public Page<ItemResponseDTO> findAll(Pageable pageable) {
        log.info("Starting the method to find items in database");

        Page<ItemEntity> page = itemRepository.findAllItems(pageable);

        log.info("Items searched on database");

        return page.map(item -> mapper.map(item, ItemResponseDTO.class));
    }

    @Override
    public ItemEntity findById(Long id) {
        return itemRepository.findById(id);
    }
}
