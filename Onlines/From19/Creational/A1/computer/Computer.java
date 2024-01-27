package computer;

import shape.CreateShape;
import shape.Shape;

public interface Computer extends CreateShape {
	public String cpu();
	public String mmu();
	public int resoltuionWidth();
	public int resoltuionHeight();
	default void doesShapeFit(Shape shape){
		if(shape.area() > resoltuionHeight() * resoltuionWidth()){
			System.out.println("Shape does not fit");
		}
		else {
			System.out.println("Shape fits");
		}
	}
} 