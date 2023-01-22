package uol.compass.ms.order.application.port.out;

import uol.compass.ms.order.domain.model.entities.AddressEntity;

public interface AddressRepositoryPortOut {
    AddressEntity save(AddressEntity address);

    AddressEntity findByCepAndNumber(String cep, Integer number);
}
