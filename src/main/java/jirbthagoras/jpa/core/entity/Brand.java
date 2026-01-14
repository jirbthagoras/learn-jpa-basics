package jirbthagoras.jpa.core.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.Data;

import java.util.List;

@Entity
@Data
@Table(name = "brands")
public class Brand {

    @Id
    private Integer id;

    private String name;

    private String description;

    @OneToMany(mappedBy = "brand")
    private List<Product> products;
}
