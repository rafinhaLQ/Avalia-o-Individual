package uol.compass.ms.order.model.dto;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import uol.compass.ms.order.model.entities.AddressEntity;
import uol.compass.ms.order.model.entities.ItemEntity;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderResponseDTO {

    private Long id;

    private String cpf;

    private List<ItemEntity> items;

    private Double total;

    private AddressEntity address;

}
