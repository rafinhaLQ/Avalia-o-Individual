package uol.compass.ms.order.application.service;

import static org.junit.Assert.assertThrows;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;

import uol.compass.ms.order.builder.ScenarioBuilder;
import uol.compass.ms.order.domain.dto.request.ItemRequestDTO;
import uol.compass.ms.order.domain.model.entities.ItemEntity;
import uol.compass.ms.order.framework.adpater.out.repositories.ItemRepository;
import uol.compass.ms.order.framework.exceptions.InvalidDateException;

@ExtendWith(MockitoExtension.class)
public class ItemServiceImplTest {

    @InjectMocks
    private ItemServiceImpl itemService;

    @Mock
    private ItemRepository itemRepository;

    @Spy
    private ModelMapper mapper;

    @Test
    void shouldCreateItem_sucess() {
        ItemEntity item = ScenarioBuilder.buildItemEntity();
        List<ItemRequestDTO> listRequest = ScenarioBuilder.buildListOfItemRequestDTOs();

        when(itemRepository.save(any())).thenReturn(item);

        List<ItemEntity> list = itemService.createItems(listRequest);

        assertNotNull(list);
        assertEquals("Leite", list.get(0).getName());
        verify(itemRepository).save(any());
    }

    @Test
    void shouldCreateItem_itemAlreadyExists() {
        ItemEntity item = ScenarioBuilder.buildItemEntity();
        List<ItemRequestDTO> listRequest = ScenarioBuilder.buildListOfItemRequestDTOs();

        when(
            itemRepository.findByNameAndCreationDateAndExpirationDateAndValueAndDescription(
                any(),
                any(),
                any(),
                any(),
                any()
            )
        )
            .thenReturn(item);

        List<ItemEntity> list = itemService.createItems(listRequest);

        assertNotNull(list);
        assertEquals("Leite", list.get(0).getName());
    }

    @Test
    void shouldCreateItem_InvalidDateException() {
        ItemRequestDTO request = ScenarioBuilder.builInvalidItemRequestDTO();
        List<ItemRequestDTO> listRequest = new ArrayList<>();
        listRequest.add(request);

        assertThrows(
            InvalidDateException.class,
            () -> {
                itemService.createItems(listRequest);
            }
        );
    }

    @Test
    void shouldSumValuesOfItems_sucess() {
        List<ItemRequestDTO> items = ScenarioBuilder.buildListOfItemRequestDTOs();

        Double total = itemService.getTotalValue(items);

        assertEquals(Double.valueOf(3.95), total);
    }
}
