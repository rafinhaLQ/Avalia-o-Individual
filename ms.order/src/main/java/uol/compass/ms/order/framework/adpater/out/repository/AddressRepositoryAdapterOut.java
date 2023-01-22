package uol.compass.ms.order.framework.adpater.out.repository;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import uol.compass.ms.order.application.port.out.AddressRepositoryPortOut;
import uol.compass.ms.order.domain.model.entities.AddressEntity;

@Repository
@RequiredArgsConstructor
public class AddressRepositoryAdapterOut implements AddressRepositoryPortOut {

    private final AddressMySqlRepository mySqlRepository;

    @Override
    public AddressEntity save(AddressEntity address) {
        return mySqlRepository.save(address);
    }

    @Override
    public AddressEntity findByCepAndNumber(String cep, Integer number) {
        return mySqlRepository.findByCepAndNumber(cep, number);
    }
}
