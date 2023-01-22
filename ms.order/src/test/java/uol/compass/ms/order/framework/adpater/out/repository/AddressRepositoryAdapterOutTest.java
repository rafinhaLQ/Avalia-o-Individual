package uol.compass.ms.order.framework.adpater.out.repository;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import uol.compass.ms.order.builder.ScenarioBuilder;
import uol.compass.ms.order.domain.model.entities.AddressEntity;

@ExtendWith(MockitoExtension.class)
public class AddressRepositoryAdapterOutTest {

    private static final String CEP_WITHOUT_DASH = "40140650";

    private static final String CEP_WITH_DASH = "40140-650";

    @InjectMocks
    private AddressRepositoryAdapterOut addressRepository;

    @Mock
    private AddressMySqlRepository mySqlRepository;

    @Test
    void save() {
        AddressEntity addressDTO = ScenarioBuilder.builAddressEntity();

        when(mySqlRepository.save(any())).thenReturn(addressDTO);

        AddressEntity address = addressRepository.save(addressDTO);

        assertNotNull(address);
        assertEquals(CEP_WITH_DASH, address.getCep());
        verify(mySqlRepository).save(any());
    }

    @Test
    void findByCepAndNumber() {
        AddressEntity addressDTO = ScenarioBuilder.builAddressEntity();

        when(mySqlRepository.findByCepAndNumber(any(), any())).thenReturn(addressDTO);

        AddressEntity address = addressRepository.findByCepAndNumber(CEP_WITHOUT_DASH, 5);

        assertNotNull(address);
        assertEquals(CEP_WITH_DASH, address.getCep());
        verify(mySqlRepository).findByCepAndNumber(any(), any());
    }
}
