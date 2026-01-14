package jirbthagoras.jpa.core.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.util.List;
import java.util.Map;

@Entity
@Data
@Table(name = "members")
public class Member {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Embedded
    private Name name;

    @ElementCollection
    @CollectionTable(
            name = "hobbies",
            joinColumns = @JoinColumn(name = "member_id", referencedColumnName = "id")
    )
    @Column(name = "name")
    private List<String> hobbies;

    @ElementCollection
    @CollectionTable(
            name = "skills",
            joinColumns = @JoinColumn(name = "member_id", referencedColumnName = "id")
    )
    @MapKeyColumn(name = "name")
    @Column(name = "value")
    private Map<String, Integer> skills;

    private String email;
}