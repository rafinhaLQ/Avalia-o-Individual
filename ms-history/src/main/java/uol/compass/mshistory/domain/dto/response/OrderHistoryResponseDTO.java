package uol.compass.mshistory.domain.dto.response;

import java.time.LocalDate;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class OrderHistoryResponseDTO {

    private String id;

    private Long orderId;

    private Double total;

    private LocalDate orderDate;
}
