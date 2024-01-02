package passenger;

public abstract class PassengerDecorator implements Passenger{

	protected Passenger wrappee;

	public PassengerDecorator(Passenger passenger) {
		this.wrappee = passenger;
	}

	@Override
	public String name() {
		return wrappee.name();	
	}
}
