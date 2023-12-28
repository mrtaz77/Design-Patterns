package spaceMonster;

public class Imposter {
	private String name;

	public Imposter(String name) {
		this.name = name;
	}

	public String getName() { return name; }

	public void login() {
		System.out.println("We won't tell anyone; you are an imposter.");
	}

	public void damage() {
		System.out.println("Damaging the spaceship.");
	}

	public void kill() {
		System.out.println("Trying to kill a crewmate.");
		System.out.println("Successfully killed a crewmate.");
	}

	public void logout() {
		System.out.println("See you again Comrade Imposter.");
	}
}
