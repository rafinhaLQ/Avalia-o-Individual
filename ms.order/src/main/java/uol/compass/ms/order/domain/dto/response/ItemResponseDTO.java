package uol.compass.ms.order.domain.dto.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import java.time.LocalDate;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ItemResponseDTO {

    private Long id;

    private String name;

    @JsonFormat(pattern = "dd-MM-yyyy")
    private LocalDate creationDate;

    @JsonFormat(pattern = "dd-MM-yyyy")
    private LocalDate expirationDate;

    private Double value;

    private String description;
}
