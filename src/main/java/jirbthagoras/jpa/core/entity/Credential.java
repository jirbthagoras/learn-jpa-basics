package jirbthagoras.jpa.core.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Data
@Table(name = "credentials")
public class Credential {

    @Id
    private Integer id;

    private String email;

    private String password;

    @OneToOne(mappedBy = "credential")
    private User user;

}
