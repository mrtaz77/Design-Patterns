package DataTransferObjects;

import java.io.Serializable;
import util.DoubleToString;

public class ViewDTO implements Serializable,DoubleToString {
	private String name;

	public ViewDTO(String name) {
		this.name = name;
	}

	public String getName() {
		return name;
	}
}
