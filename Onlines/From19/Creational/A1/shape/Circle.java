package shape;

public class Circle implements Shape {
	private final double radius;

	public Circle(double radius) {this.radius = radius;}

	@Override
	public ShapeType getType() { return ShapeType.Circle; }

	@Override
	public double area() { return 3.14159 * radius * radius; }

	@Override
	public double parameter() {
		return 2 * 3.14159 * radius ;
	}
}
