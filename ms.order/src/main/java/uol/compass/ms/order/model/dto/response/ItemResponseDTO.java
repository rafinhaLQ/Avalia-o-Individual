package uol.compass.ms.order.model.dto.response;

import java.time.LocalDate;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ItemResponseDTO {

    private Long id;

    private String name;

    private LocalDate creationDate;

    private LocalDate expirationDate;

    private Double value;

    private String description;

}
