package uol.compass.ms.order.service;

import uol.compass.ms.order.model.entities.AddressEntity;

public interface AddressService {

    AddressEntity createAddressWithCep(String cepRecebido, Integer number);

}
