package factory;

import computer.*;
import shape.*;

public class DisplayFactory {
	public static void main(String[] args) {
		Computer computer = new ComputerA();
		Shape shape = computer.getShape(ShapeType.Circle, 190);
		System.out.println(computer);
		System.out.println(computer.resoltuionWidth() + "x" + computer.resoltuionHeight());
		System.out.println(shape.getType().toString());
		System.out.println(shape.area());
		System.out.println(shape.parameter());
		computer.doesShapeFit(shape);
	}
}
