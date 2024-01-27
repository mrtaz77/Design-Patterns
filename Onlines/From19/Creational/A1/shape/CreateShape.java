package shape;

public interface CreateShape {
	default Shape getShape(ShapeType type,double... values){
		switch(type){
			case Circle:
				return new Circle(values[0]);
			case Square:
				return new Square(values[0]);
			case Rectangle:
				return new Rectangle(values[0],values[1]);
			default: 
				return null;
		}
	}
}
