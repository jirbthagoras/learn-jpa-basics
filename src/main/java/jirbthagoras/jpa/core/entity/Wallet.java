package jirbthagoras.jpa.core.entity;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
@Table(name = "wallets")
public class Wallet {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private Long balance;

    @OneToOne
    @JoinColumn(
            name = "user_id", referencedColumnName = "id"
    )
    private User user;

}
