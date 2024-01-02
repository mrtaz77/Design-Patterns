package passenger;

public class ImposterDecorator extends PassengerDecorator {

	public ImposterDecorator(Passenger passenger) {
		super(passenger);
	}

	@Override 
	public void repair() {
		wrappee.repair();
		System.out.println("Damaging the spaceship.");
	}

	@Override
	public void work() {
		wrappee.work();
		System.out.println("Trying to kill a crewmate.");
		System.out.println("Successfully killed a crewmate.");
	}		
}
