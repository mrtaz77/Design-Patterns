package DataTransferObjects;

import java.io.Serializable;

public class LoginDTO implements Serializable{
    private boolean status = false;
    private String name;

	public LoginDTO(String name, boolean status) {
		this.name = name;
		this.status = status;
	}

    public boolean isStatus() {
        return status;
    }

    public LoginDTO setStatus(boolean status) {
        this.status = status;
		return this;
    }

    public String getName() {
        return name;
    }

    public LoginDTO setName(String name) {
        this.name = name;
		return this;
    }
}
