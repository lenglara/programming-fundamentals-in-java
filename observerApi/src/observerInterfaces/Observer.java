package observerInterfaces;

import java.io.Serializable;

public interface Observer extends Serializable {
    void update();
}
