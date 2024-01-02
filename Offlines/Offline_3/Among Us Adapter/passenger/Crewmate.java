package passenger;

public class Crewmate {
	
	private String name;

	public Crewmate(String name) {
		this.name = name;
	}

	public String name() { return name; }
	public void setName(String name) { this.name = name; }

	public void repair() {
		System.out.println("Repairing the spaceship.");
	}

	public void work() {
		System.out.println("Doing research.");
	}
}
