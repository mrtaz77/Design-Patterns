package light;

import traffic.TrafficLight;

public abstract class Light {
	protected TrafficLight unit;

	public Light setUnit(TrafficLight unit) { this.unit = unit; return this;}

	public abstract void show();
}