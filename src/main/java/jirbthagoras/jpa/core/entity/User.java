package jirbthagoras.jpa.core.entity;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
@Table(name = "users")
public class User {

    @Id
    private Integer id;

    private String name;

    @OneToOne
    @PrimaryKeyJoinColumn(
            name = "id",
            referencedColumnName = "id"
    )
    private Credential credential;

    @OneToOne(mappedBy = "user")
    private Wallet wallet;

}
