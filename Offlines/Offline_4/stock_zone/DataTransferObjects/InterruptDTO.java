package DataTransferObjects;

import java.io.Serializable;

public class InterruptDTO implements Serializable {
    private boolean status;

    public InterruptDTO(boolean status) {
        this.status = status;
    }

    public boolean isStatus() {
        return status;
    }

    public InterruptDTO setStatus(boolean status) {
        this.status = status;
		return this;
    }
}