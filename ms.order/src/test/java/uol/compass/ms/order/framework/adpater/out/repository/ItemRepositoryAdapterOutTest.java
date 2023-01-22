package uol.compass.ms.order.framework.adpater.out.repository;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import uol.compass.ms.order.builder.ScenarioBuilder;
import uol.compass.ms.order.domain.model.entities.ItemEntity;
import uol.compass.ms.order.framework.exceptions.ItemNotFoundException;

@ExtendWith(MockitoExtension.class)
public class ItemRepositoryAdapterOutTest {

    private static final Long ID = 1L;

    private static final String ITEM_NAME = "Leite";

    private static final LocalDate CREATION_DATE = LocalDate.of(2003, 2, 21);

    private static final LocalDate EXPIRATION_DATE = CREATION_DATE.plusDays(1L);

    private static final Double ITEM_VALUE = Double.valueOf(3.95);

    private static final String ITEM_DESCRIPTION = "1L de Leite Piracanjuba";

    @InjectMocks
    private ItemRepositoryAdapterOut itemRepository;

    @Mock
    private ItemMySqlRepository mySqlRepository;

    @Test
    void save() {
        ItemEntity itemDTO = ScenarioBuilder.buildItemEntity();

        when(mySqlRepository.save(any())).thenReturn(itemDTO);

        ItemEntity item = itemRepository.save(itemDTO);

        assertNotNull(item);
        assertEquals(ITEM_NAME, item.getName());
        verify(mySqlRepository).save(any());
    }

    @Test
    void findAllItems() {
        Pageable pageable = ScenarioBuilder.buildPageable();
        ItemEntity item = ScenarioBuilder.buildItemEntity();
        Page<ItemEntity> pageDTO = new PageImpl<>(List.of(item));

        when(mySqlRepository.findAll((Pageable) any())).thenReturn(pageDTO);

        Page<ItemEntity> page = itemRepository.findAllItems(pageable);

        assertNotNull(page);
        assertEquals(ITEM_NAME, page.getContent().get(0).getName());
        verify(mySqlRepository).findAll((Pageable) any());
    }

    @Test
    void findById() {
        ItemEntity itemDTO = ScenarioBuilder.buildItemEntity();

        when(mySqlRepository.findById(any())).thenReturn(Optional.of(itemDTO));

        ItemEntity item = itemRepository.findById(ID);

        assertNotNull(item);
        assertEquals(ITEM_NAME, item.getName());
        verify(mySqlRepository).findById(any());
    }

    @Test
    void findById_ItemNotFoundException() {
        assertThrows(
            ItemNotFoundException.class,
            () -> {
                itemRepository.findById(ID);
            }
        );
    }

    @Test
    void findItemByExceptId() {
        ItemEntity itemDTO = ScenarioBuilder.buildItemEntity();

        when(
            mySqlRepository.findByNameAndCreationDateAndExpirationDateAndValueAndDescription(
                any(),
                any(),
                any(),
                any(),
                any()
            )
        )
            .thenReturn(itemDTO);

        ItemEntity item = itemRepository.findItemByExceptId(
            ITEM_NAME,
            CREATION_DATE,
            EXPIRATION_DATE,
            ITEM_VALUE,
            ITEM_DESCRIPTION
        );

        assertNotNull(item);
        assertEquals(ITEM_NAME, item.getName());
        verify(mySqlRepository)
            .findByNameAndCreationDateAndExpirationDateAndValueAndDescription(any(), any(), any(), any(), any());
    }
}
