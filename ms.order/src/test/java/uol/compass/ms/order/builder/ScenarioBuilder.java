package uol.compass.ms.order.builder;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import uol.compass.ms.order.domain.dto.request.ItemRequestDTO;
import uol.compass.ms.order.domain.dto.request.OrderRequestDTO;
import uol.compass.ms.order.domain.dto.response.AddressResponseDTO;
import uol.compass.ms.order.domain.dto.response.ItemResponseDTO;
import uol.compass.ms.order.domain.dto.response.OrderResponseDTO;
import uol.compass.ms.order.domain.model.entities.AddressEntity;
import uol.compass.ms.order.domain.model.entities.ItemEntity;
import uol.compass.ms.order.domain.model.entities.OrderEntity;

public class ScenarioBuilder {

    private static final Long ID = 1L;

    private static final String STREET = "Largo do Farol da Barra";

    private static final String ITEM_NAME = "Leite";

    private static final String DISTRICT = "Barra";

    private static final String CITY = "Salvador";

    private static final String STATE = "BA";

    private static final String CEP_WITH_DASH = "40140-650";

    private static final LocalDate CREATION_DATE = LocalDate.of(2003, 2, 21);

    private static final LocalDate EXPIRATION_DATE = CREATION_DATE.plusDays(1L);

    private static final Double ITEM_VALUE = Double.valueOf(3.95);

    private static final String ITEM_DESCRIPTION = "1L de Leite Piracanjuba";

    private static final String CPF = "09963606547";

    private static final Double TOTAL = Double.valueOf(25.99);

    private static final String CEP_WITHOUT_DASH = "40140650";

    private static final Integer NUMBER = Integer.valueOf(5);

    public static AddressEntity builAddressEntity() {
        return AddressEntity
            .builder()
            .id(ID)
            .street(STREET)
            .number(NUMBER)
            .district(DISTRICT)
            .city(CITY)
            .state(STATE)
            .cep(CEP_WITH_DASH)
            .build();
    }

    public static AddressResponseDTO builAddressResponseDTO() {
        return AddressResponseDTO
            .builder()
            .id(ID)
            .street(STREET)
            .number(NUMBER)
            .district(DISTRICT)
            .city(CITY)
            .state(STATE)
            .cep(CEP_WITH_DASH)
            .build();
    }

    public static ItemEntity buildItemEntity() {
        return ItemEntity
            .builder()
            .id(ID)
            .name(ITEM_NAME)
            .creationDate(CREATION_DATE)
            .expirationDate(EXPIRATION_DATE)
            .value(ITEM_VALUE)
            .description(ITEM_DESCRIPTION)
            .build();
    }

    private static ItemRequestDTO builItemRequestDTO() {
        return ItemRequestDTO
            .builder()
            .name(ITEM_NAME)
            .creationDate(CREATION_DATE)
            .expirationDate(EXPIRATION_DATE)
            .value(ITEM_VALUE)
            .description(ITEM_DESCRIPTION)
            .build();
    }

    public static ItemRequestDTO builInvalidItemRequestDTO() {
        return ItemRequestDTO
            .builder()
            .name(ITEM_NAME)
            .creationDate(EXPIRATION_DATE)
            .expirationDate(CREATION_DATE)
            .value(ITEM_VALUE)
            .description(ITEM_DESCRIPTION)
            .build();
    }

    public static List<ItemRequestDTO> buildListOfItemRequestDTOs() {
        List<ItemRequestDTO> list = new ArrayList<>();
        list.add(builItemRequestDTO());
        return list;
    }

    private static ItemResponseDTO buildItemResponseDTO() {
        return ItemResponseDTO
            .builder()
            .id(ID)
            .name(ITEM_NAME)
            .creationDate(CREATION_DATE)
            .expirationDate(EXPIRATION_DATE)
            .value(ITEM_VALUE)
            .description(ITEM_DESCRIPTION)
            .build();
    }

    public static OrderRequestDTO builOrderRequestDTO() {
        return OrderRequestDTO
            .builder()
            .cpf(CPF)
            .items(buildListOfItemRequestDTOs())
            .cep(CEP_WITHOUT_DASH)
            .number(NUMBER)
            .build();
    }

    public static List<ItemEntity> buildListOfItemEntities() {
        List<ItemEntity> list = new ArrayList<>();
        list.add(buildItemEntity());
        return list;
    }

    public static OrderEntity buildOrderEntity() {
        return OrderEntity
            .builder()
            .id(ID)
            .cpf(CPF)
            .items(buildListOfItemEntities())
            .total(TOTAL)
            .address(builAddressEntity())
            .build();
    }

    private static List<ItemResponseDTO> buildListOfItemResponseDTOs() {
        List<ItemResponseDTO> list = new ArrayList<>();
        list.add(buildItemResponseDTO());
        return list;
    }

    public static OrderResponseDTO buildOrderResponseDTO() {
        return OrderResponseDTO
            .builder()
            .id(ID)
            .cpf(CPF)
            .items(buildListOfItemResponseDTOs())
            .total(TOTAL)
            .address(builAddressResponseDTO())
            .build();
    }
}
