package observers;

public abstract class User {
	protected UserType type;
	protected String name;
	protected boolean isLoggedIn = false;
	protected static String serverAddress = "127.0.0.1";
    protected static int serverPort = 33333;

	public UserType getType() {
		return type;
	}

	public String getName() { return name; }

	public boolean isLoggedIn() {
        return isLoggedIn;
    }

    public void setLoggedIn(Boolean loggedIn) {
        this.isLoggedIn = loggedIn;
    }
}
