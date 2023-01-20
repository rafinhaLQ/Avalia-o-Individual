package uol.compass.ms.order.application.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import uol.compass.ms.order.application.port.in.AddressService;
import uol.compass.ms.order.application.port.in.ApiViaCepInterface;
import uol.compass.ms.order.domain.dto.response.ApiViaCepResponseDTO;
import uol.compass.ms.order.domain.model.entities.AddressEntity;
import uol.compass.ms.order.framework.adpater.out.AddressRepository;
import uol.compass.ms.order.framework.exceptions.InvalidCepException;

@Service
@RequiredArgsConstructor
@Slf4j
public class AddressServiceImpl implements AddressService {

    private final AddressRepository addressRepository;

    private final ApiViaCepInterface apiViaCepInterface;

    @Override
    public AddressEntity createAddressWithCep(String cepRecebido, Integer number) {
        log.info("Starting method for Address");

        cepRecebido = cepRecebido.replace("-", "");

        if (
            addressRepository.findByCepAndNumber(
                cepRecebido.substring(0, 5) + "-" + cepRecebido.substring(5),
                number
            ) !=
            null
        ) {
            return addressRepository.findByCepAndNumber(
                cepRecebido.substring(0, 5) + "-" + cepRecebido.substring(5),
                number
            );
        }

        AddressEntity addressToCreate = new AddressEntity();

        ApiViaCepResponseDTO response = apiViaCepInterface.findAddressWithCep(cepRecebido);

        if (response.getCep() == null) {
            log.error("Inexistent CEP", InvalidCepException.class);
            throw new InvalidCepException();
        }

        addressToCreate.setStreet(response.getLogradouro());
        addressToCreate.setNumber(number);
        addressToCreate.setDistrict(response.getBairro());
        addressToCreate.setCity(response.getLocalidade());
        addressToCreate.setState(response.getUf());
        addressToCreate.setCep(response.getCep());

        log.info("Address created on database");

        return addressRepository.save(addressToCreate);
    }
}
