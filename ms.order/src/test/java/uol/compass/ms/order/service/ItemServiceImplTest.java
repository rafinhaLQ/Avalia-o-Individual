package uol.compass.ms.order.service;

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
import uol.compass.ms.order.model.dto.request.ItemRequestDTO;
import uol.compass.ms.order.model.entities.ItemEntity;
import uol.compass.ms.order.repositories.ItemRepository;
import uol.compass.ms.order.service.impl.ItemServiceImpl;

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
        ItemRequestDTO request = ScenarioBuilder.builItemRequestDTO();
        ItemEntity item = ScenarioBuilder.buildItemEntity();
        List<ItemRequestDTO> listRequest = new ArrayList<>();
        listRequest.add(request);

        when(itemRepository.save(any())).thenReturn(item);

        List<ItemEntity> list = itemService.createItems(listRequest);

        assertNotNull(list);
        assertEquals("Leite", list.get(0).getName());
        verify(itemRepository).save(any());
    }

}
