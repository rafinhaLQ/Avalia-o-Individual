package uol.compass.ms.order.model.dto;

import java.time.LocalDate;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ItemRequestDTO {

    @NotBlank
    private String name;

    @NotNull
    private LocalDate creationDate;
    
    @NotNull
    private LocalDate expirationDate;
    
    @NotNull
    private Double value;

    @NotBlank
    private String description;

}
