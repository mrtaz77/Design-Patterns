package DataTransferObjects;

import java.io.Serializable;

public class LoginDTO implements Serializable{
    private boolean status = false;
    private String name;
    private String password;

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

    public String getPassword() {
        return password;
    }

    public LoginDTO setPassword(String password) {
        this.password = password;
		return this;
    }
}
