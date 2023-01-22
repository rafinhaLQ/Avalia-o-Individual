package uol.compass.ms.order.application.service;

import static org.junit.Assert.assertThrows;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.List;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import uol.compass.ms.order.application.port.out.ItemRepositoryPortOut;
import uol.compass.ms.order.builder.ScenarioBuilder;
import uol.compass.ms.order.domain.dto.request.ItemRequestDTO;
import uol.compass.ms.order.domain.dto.response.ItemResponseDTO;
import uol.compass.ms.order.domain.model.entities.ItemEntity;
import uol.compass.ms.order.framework.exceptions.InvalidDateException;

@ExtendWith(MockitoExtension.class)
public class ItemServiceImplTest {

    private static final String ITEM_NAME = "Leite";

    private static final Long ID = 1L;

    @InjectMocks
    private ItemServiceImpl itemService;

    @Mock
    private ItemRepositoryPortOut itemRepository;

    @Spy
    private ModelMapper mapper;

    @Test
    void shouldCreateItem_sucess() {
        ItemEntity item = ScenarioBuilder.buildItemEntity();
        ItemRequestDTO request = ScenarioBuilder.builItemRequestDTO();

        when(itemRepository.save(any())).thenReturn(item);

        ItemResponseDTO response = itemService.create(request);

        assertNotNull(response);
        assertEquals(ITEM_NAME, response.getName());
        verify(itemRepository).save(any());
    }

    @Test
    void shouldCreateItem_itemAlreadyExists() {
        ItemEntity item = ScenarioBuilder.buildItemEntity();
        ItemRequestDTO request = ScenarioBuilder.builItemRequestDTO();

        when(itemRepository.findItemByExceptId(any(), any(), any(), any(), any())).thenReturn(item);

        ItemResponseDTO response = itemService.create(request);

        assertNotNull(response);
        assertEquals(ITEM_NAME, response.getName());
        verify(itemRepository, times(2)).findItemByExceptId(any(), any(), any(), any(), any());
    }

    @Test
    void shouldCreateItem_InvalidDateException() {
        ItemRequestDTO request = ScenarioBuilder.builInvalidItemRequestDTO();

        assertThrows(
            InvalidDateException.class,
            () -> {
                itemService.create(request);
            }
        );
    }

    @Test
    void shouldFindAllItems_sucess() {
        Pageable pageable = ScenarioBuilder.buildPageable();
        ItemEntity item = ScenarioBuilder.buildItemEntity();
        Page<ItemEntity> pageDTO = new PageImpl<>(List.of(item));

        when(itemRepository.findAllItems(any())).thenReturn(pageDTO);

        Page<ItemResponseDTO> page = itemService.findAll(pageable);

        assertNotNull(page);
        assertEquals(ITEM_NAME, page.getContent().get(0).getName());
        verify(itemRepository).findAllItems(any());
    }

    @Test
    void shouldFindItemById_sucess() {
        ItemEntity itemDTO = ScenarioBuilder.buildItemEntity();

        when(itemRepository.findById(any())).thenReturn(itemDTO);

        ItemEntity item = itemService.findById(ID);

        assertNotNull(item);
        assertEquals(ITEM_NAME, item.getName());
        verify(itemRepository).findById(any());
    }
}
