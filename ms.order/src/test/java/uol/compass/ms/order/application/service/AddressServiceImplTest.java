package uol.compass.ms.order.application.service;

import static org.junit.Assert.assertThrows;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import uol.compass.ms.order.application.port.in.ApiViaCepInterface;
import uol.compass.ms.order.application.port.out.AddressRepositoryPortOut;
import uol.compass.ms.order.builder.ScenarioBuilder;
import uol.compass.ms.order.domain.dto.response.ApiViaCepResponseDTO;
import uol.compass.ms.order.domain.model.entities.AddressEntity;
import uol.compass.ms.order.framework.exceptions.InvalidCepException;

@ExtendWith(MockitoExtension.class)
public class AddressServiceImplTest {

    private static final String CEP = "40140650";

    private static final String INVALID_CEP = "40140659";

    private static final String DISTRICT = "Barra";

    @InjectMocks
    private AddressServiceImpl addressService;

    @Mock
    private AddressRepositoryPortOut addressRepository;

    @Mock
    private ApiViaCepInterface apiViaCepInterface;

    @Test
    void shouldCreateAddress_sucess() {
        AddressEntity address = ScenarioBuilder.builAddressEntity();
        ApiViaCepResponseDTO apiResponse = ScenarioBuilder.buildApiViaCepResponseDTO();

        when(apiViaCepInterface.findAddressWithCep(any())).thenReturn(apiResponse);
        when(addressRepository.save(any())).thenReturn(address);

        AddressEntity addressEntity = addressService.createAddressWithCep(CEP, 5);

        assertNotNull(addressEntity);
        assertEquals(DISTRICT, addressEntity.getDistrict());
        verify(apiViaCepInterface).findAddressWithCep(any());
        verify(addressRepository).save(any());
    }

    @Test
    void shouldCreateAddress_addressAlreadyExists() {
        AddressEntity address = ScenarioBuilder.builAddressEntity();

        when(addressRepository.findByCepAndNumber(any(), any())).thenReturn(address);

        AddressEntity addressEntity = addressService.createAddressWithCep(CEP, 5);
        assertNotNull(addressEntity);
        assertEquals(DISTRICT, addressEntity.getDistrict());
        verify(addressRepository, times(2)).findByCepAndNumber(any(), any());
    }

    @Test
    void shouldCreateAddress_InvalidCepException() {
        ApiViaCepResponseDTO apiResponse = new ApiViaCepResponseDTO();

        when(apiViaCepInterface.findAddressWithCep(any())).thenReturn(apiResponse);

        assertThrows(
            InvalidCepException.class,
            () -> {
                addressService.createAddressWithCep(INVALID_CEP, 5);
            }
        );
        verify(apiViaCepInterface).findAddressWithCep(any());
    }
}
