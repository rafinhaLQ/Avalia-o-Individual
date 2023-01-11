package uol.compass.ms.order.model.dto;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class OrderRequestDTO {

    @NotNull
    @Size(min = 11, max = 11)
    private String cpf;

    @NotNull
    private Long idItem;

    @NotNull
    private Double total;

    @NotNull
    @Size(min = 8, max = 8)
    private String cep;

    @NotNull
    private Integer number;

}
