package shape;

public class Square implements Shape {
	private final double length;

	public Square(double length) { this.length = length; }

	@Override
	public ShapeType getType() {
		return ShapeType.Square;
	}

	@Override
	public double area() {
		return length * length ;
	}

	@Override
	public double parameter() {
		return 4 * length;
	}
}
