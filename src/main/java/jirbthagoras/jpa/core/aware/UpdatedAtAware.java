package jirbthagoras.jpa.core.aware;

import java.time.LocalDateTime;

public interface UpdatedAtAware {

    void setUpdatedAt(LocalDateTime dateTime);

}
