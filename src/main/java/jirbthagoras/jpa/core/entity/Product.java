package jirbthagoras.jpa.core.entity;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
@Table(name = "products")
public class Product {

    @Id
    private Integer id;

    private String name;

    private Long price;

    private String description;

    @ManyToOne()
    @JoinColumn(
            name = "brand_id",
            referencedColumnName = "id"
    )
    private Brand brand;

}
