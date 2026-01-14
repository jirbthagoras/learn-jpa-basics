package jirbthagoras.jpa.core.entity;

import jakarta.persistence.*;
import jirbthagoras.jpa.core.aware.UpdatedAtAware;
import jirbthagoras.jpa.core.listener.UpdatedAtListener;
import lombok.Data;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Calendar;

@Entity
@Data
@Table(name = "categories")
@EntityListeners({
        UpdatedAtListener.class
})
public class Category implements UpdatedAtAware {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String name;

    private String description;

    @Column(name = "created_at")
    private Calendar createdAt;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

}
