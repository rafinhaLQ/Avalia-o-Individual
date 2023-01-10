package uol.compass.ms.order.model;

import java.time.LocalDate;

import javax.persistence.Entity;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity(name = "item")
@Table(name = "item")
public class ItemEntity {

    private Long id;

    private String name;

    private LocalDate creationDate;

    private LocalDate expirationDate;

    private Double value;

    private String description;

}
