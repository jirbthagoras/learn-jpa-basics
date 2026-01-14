package jirbthagoras.jpa.core.entity;

import jakarta.persistence.*;
import jirbthagoras.jpa.core.enums.CustomerType;
import lombok.Data;

@Entity
@Data
@Table(name = "customers")
public class Customer {

    @Id
    @Column(name = "id")
    private String id;

    @Column(name = "name")
    private String name;

    @Column(name = "primary_email")
    private String primaryEmail;

    @Enumerated(EnumType.STRING)
    private CustomerType type;

    @Transient
    private String fullName;

}
