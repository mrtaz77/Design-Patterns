package light;

public class Red extends Light {

	@Override
	public void show() {
		System.out.println("Red Light");
		try {
			Thread.sleep(5000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		unit.setlight((new Yellow()).setUnit(unit));
	}	
}
