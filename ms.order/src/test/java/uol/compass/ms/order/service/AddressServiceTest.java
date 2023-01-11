package uol.compass.ms.order.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import uol.compass.ms.order.builder.ScenarioBuilder;
import uol.compass.ms.order.model.entities.AddressEntity;
import uol.compass.ms.order.repositories.AddressRepository;
import uol.compass.ms.order.service.impl.AddressServiceImpl;

@ExtendWith(MockitoExtension.class)
public class AddressServiceTest {

    public static final String CEP = "40140650";
    
    @InjectMocks
    private AddressServiceImpl addressService;

    @Mock
    private AddressRepository addressRepository;

    @Test
    void shouldCreateAddress_sucess() {
        AddressEntity address = ScenarioBuilder.builAddressEntity();

        when(addressRepository.save(any())).thenReturn(address);

        AddressEntity addressToCreate = addressService.createAddressWithCep(CEP, 5);

        assertNotNull(addressToCreate);
        assertEquals("Barra", addressToCreate.getDistrict());
        verify(addressRepository).save(any());
    }

}
