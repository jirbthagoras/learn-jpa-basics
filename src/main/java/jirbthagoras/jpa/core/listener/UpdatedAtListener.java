package jirbthagoras.jpa.core.listener;

import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import jirbthagoras.jpa.core.aware.UpdatedAtAware;

import java.time.LocalDateTime;

public class UpdatedAtListener {

    @PrePersist
    @PreUpdate
    public void setLastUpdatedAt(UpdatedAtAware obj) {
        obj.setUpdatedAt(LocalDateTime.now());
    }

}
