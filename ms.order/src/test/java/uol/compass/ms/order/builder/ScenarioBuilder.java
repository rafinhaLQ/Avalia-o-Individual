package uol.compass.ms.order.builder;

import uol.compass.ms.order.model.entities.AddressEntity;

public class ScenarioBuilder {

    private static final Long ID = 1L;
    
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

}
