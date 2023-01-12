package uol.compass.ms.order.builder;

import java.time.LocalDate;

import uol.compass.ms.order.model.dto.request.ItemRequestDTO;
import uol.compass.ms.order.model.entities.AddressEntity;
import uol.compass.ms.order.model.entities.ItemEntity;

public class ScenarioBuilder {

    private static final Long ID = 1L;

    private static final String ITEM_NAME = "Leite";

    private static final LocalDate CREATION_DATE = LocalDate.of(2003, 2, 21);

    private static final LocalDate EXPIRATION_DATE = CREATION_DATE.plusDays(1L);

    private static final Double ITEM_VALUE = Double.valueOf(3.95);

    private static final String ITEM_DESCRIPTION = "1L de Leite Piracanjuba";
    
    public static AddressEntity builAddressEntity() {
        return AddressEntity.builder()
            .id(ID)
            .street("Largo do Farol da Barra")
            .number(5)
            .district("Barra")
            .city("Salvador")
            .state("BA")
            .cep("40140-650")
            .build();
    }

    public static ItemEntity buildItemEntity() {
        return ItemEntity.builder()
            .id(ID)
            .name(ITEM_NAME)
            .creationDate(CREATION_DATE)
            .expirationDate(EXPIRATION_DATE)
            .value(ITEM_VALUE)
            .description(ITEM_DESCRIPTION)
            .build();
    }

    public static ItemRequestDTO builItemRequestDTO() {
        return ItemRequestDTO.builder()
            .name(ITEM_NAME)
            .creationDate(CREATION_DATE)
            .expirationDate(EXPIRATION_DATE)
            .value(ITEM_VALUE)
            .description(ITEM_DESCRIPTION)
            .build();
    }

}
