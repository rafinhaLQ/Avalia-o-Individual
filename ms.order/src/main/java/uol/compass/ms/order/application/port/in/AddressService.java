package uol.compass.ms.order.application.port.in;

import uol.compass.ms.order.domain.model.entities.AddressEntity;

public interface AddressService {
    AddressEntity createAddressWithCep(String cepRecebido, Integer number);
}
