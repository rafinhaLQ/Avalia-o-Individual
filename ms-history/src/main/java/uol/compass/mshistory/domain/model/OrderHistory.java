package uol.compass.mshistory.domain.model;

import java.time.LocalDate;
import javax.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Document
public class OrderHistory {

    @Id
    private String id;

    private Long orderId;

    private Double total;

    private LocalDate orderDate;
}
