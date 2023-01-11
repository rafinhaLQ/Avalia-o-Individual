package uol.compass.ms.order.service;

import org.springframework.stereotype.Service;

import com.gtbr.ViaCepClient;
import com.gtbr.domain.Cep;
import com.gtbr.utils.CEPUtils;

import lombok.RequiredArgsConstructor;
import uol.compass.ms.order.exceptions.InvalidCepException;
import uol.compass.ms.order.model.entities.AddressEntity;
import uol.compass.ms.order.repositories.AddressRepository;

@Service
@RequiredArgsConstructor
public class AddressService {

    private final AddressRepository addressRepository;

    public AddressEntity createAddressWithCep(String cepRecebido, Integer number) {
        if(addressRepository.findByCepAndNumber(CEPUtils.mascararCep(cepRecebido), number) != null) {
            return addressRepository.findByCepAndNumber(CEPUtils.mascararCep(cepRecebido), number);
        }

        AddressEntity addressToCreate = new AddressEntity();

        Cep cep = ViaCepClient.findCep(cepRecebido);

        if (cep.getCep() == null) {
            throw new InvalidCepException();
        }

        addressToCreate.setStreet(cep.getLogradouro());
        addressToCreate.setNumber(number);
        addressToCreate.setDistrict(cep.getBairro());
        addressToCreate.setCity(cep.getLocalidade());
        addressToCreate.setState(cep.getUf());
        addressToCreate.setCep(cep.getCep());

        return addressRepository.save(addressToCreate);
    }

}
