package factory;

import computer.*;
import shape.*;

public class DisplayFactory {
	private static Computer createComputer(String name){
		switch(name){
			case "A" : return new ComputerA();
			case "B" : return new ComputerB();
			case "C" : return new ComputerC();
			default : return null;
		}
	}

	private static void display(Computer computer, ShapeType type, double... values){
		Shape shape = computer.getShape(type,values);
		System.out.println(computer.resoltuionWidth()+"x"+computer.resoltuionHeight());
		System.out.println(shape.getType().toString());
		System.out.println(shape.area());
		System.out.println(shape.parameter());
		computer.doesShapeFit(shape);
	}



	public static void main(String[] args) {
		Computer computer1 = createComputer("A");
		Computer computer2 = createComputer("B");
		Computer computer3 = createComputer("C");

		display(computer1,ShapeType.Circle,190);
		display(computer2,ShapeType.Rectangle,350,400);
		display(computer3,ShapeType.Square,20);
	}
}
