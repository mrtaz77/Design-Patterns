package spaceMonster;

public class Imposter {
	private String name;

	public Imposter(String name) {
		this.name = name;
	}

	public String getName() { return name; }

	public void damage() {
		System.out.println("Damaging the spaceship.");
	}

	public void kill() {
		System.out.println("Trying to kill a crewmate.");
		System.out.println("Successfully killed a crewmate.");
	}

}
