package computer;

public class ComputerC implements Computer  {

	@Override
	public String cpu() {
		return "cpuC";
	}

	@Override
	public String mmu() {
		return "mmuC";
	}

	@Override
	public int resoltuionWidth() {
		return 550;
	}

	@Override
	public int resoltuionHeight() {
		return 430;	
	}

	@Override 
	public String toString() { return "computerC"; }
}
