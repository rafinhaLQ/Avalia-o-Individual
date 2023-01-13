package uol.compass.ms.order.model.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AddressResponseDTO {

    private Long id;

    private String street;

    private Integer number;

    private String district;

    private String city;

    private String state;

    private String cep;
}
