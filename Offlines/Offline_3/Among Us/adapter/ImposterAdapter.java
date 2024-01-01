package adapter;

import passenger.Crewmate;
import spaceMonster.Imposter;

public class ImposterAdapter extends Crewmate {
	private Imposter imposter;

	public ImposterAdapter(Imposter imposter) {
		super(imposter.getName());
		this.imposter = imposter;
	}

	@Override
	public void login() {
		super.login();
		System.out.println("We won't tell anyone; you are an imposter.");
	}

	@Override
	public void repair() {
		super.repair();
		imposter.damage();
	}

	@Override
	public void work() {
		super.work();
		imposter.kill();
	}

	@Override
	public void logout() {
		super.logout();
		System.out.println("See you again Comrade Imposter.");
	} 

}
