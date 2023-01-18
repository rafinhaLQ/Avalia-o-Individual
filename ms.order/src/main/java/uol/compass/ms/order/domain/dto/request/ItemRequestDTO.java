package uol.compass.ms.order.domain.dto.request;

import com.fasterxml.jackson.annotation.JsonFormat;
import java.time.LocalDate;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
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
    @JsonFormat(pattern = "dd/MM/yyyy")
    private LocalDate creationDate;

    @NotNull
    @JsonFormat(pattern = "dd/MM/yyyy")
    private LocalDate expirationDate;

    @NotNull
    @Positive
    private Double value;

    @NotBlank
    private String description;
}
