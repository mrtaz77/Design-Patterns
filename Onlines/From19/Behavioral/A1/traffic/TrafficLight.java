package traffic;

import light.Light;
import light.Red;

public class TrafficLight {
	private Light light;

	public void setlight(Light light) { this.light = light; }

	public void start() {
		light = new Red();
		light.setUnit(this);
		while(true) {
			light.show();
		}
	}
}
