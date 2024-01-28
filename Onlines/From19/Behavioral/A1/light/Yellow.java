package light;

public class Yellow extends Light {

	@Override
	public void show() {
		System.out.println("Yellow");
		try {
			Thread.sleep(2000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		unit.setlight((new Green()).setUnit(unit));
	}
}
