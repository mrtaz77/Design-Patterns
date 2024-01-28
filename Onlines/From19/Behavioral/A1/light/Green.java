package light;

public class Green extends Light {

	@Override
	public void show() {
		System.out.println("Green");
		try {
			Thread.sleep(10000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		unit.setlight((new Red()).setUnit(unit));
	}
}
