package shape;

public class Rectangle implements Shape {
	private final double width;
	private final double height;

	public Rectangle(double width, double height) {
		this.width = width;
		this.height = height;
	}

	@Override
	public ShapeType getType() {
		return ShapeType.Rectangle;
	}

	@Override
	public double area() {
		return width * height;
	}

	@Override
	public double parameter() {
		return 2 * width * height;
	}
	
}
