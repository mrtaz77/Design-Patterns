package observers;

import util.NetworkingConstants;

public abstract class User implements NetworkingConstants {
	protected UserType type;
	protected String name;
	protected boolean isLoggedIn = false;
	public UserType getType() {
		return type;
	}
	public String getName() { return name; }
}
