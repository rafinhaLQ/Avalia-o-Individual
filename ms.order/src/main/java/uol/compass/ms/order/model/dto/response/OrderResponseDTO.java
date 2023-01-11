package uol.compass.ms.order.model.dto.response;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderResponseDTO {

    private Long id;

    private String cpf;

    private List<ItemResponseDTO> items;

    private Double total;

    private AddressResponseDTO address;

}
