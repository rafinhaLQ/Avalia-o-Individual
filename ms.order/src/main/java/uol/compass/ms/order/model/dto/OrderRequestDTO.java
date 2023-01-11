package uol.compass.ms.order.model.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class OrderRequestDTO {

    private String cpf;

    private Long idItem;

    private Double total;

    private String cep;

    private Integer number;

}
