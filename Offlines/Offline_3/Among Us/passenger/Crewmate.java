package passenger;

public class Crewmate {
	
	private String name;

	public Crewmate() {}

	public Crewmate(String name) {
		this.name = name;
	}

	public String getName() { return name; }

	public void login() {
		System.out.println("Welcome Crewmate!");
	}

	public void repair() {
		System.out.println("Repairing the spaceship.");
	}

	public void work() {
		System.out.println("Doing research.");
	}

	public void logout() {
		System.out.println("Bye Bye crewmate.");
	}
	
}
