package computer;

public class ComputerA implements Computer  {

	@Override
	public String cpu() {
		return "cpuA";
	}

	@Override
	public String mmu() {
		return "mmuA";
	}

	@Override
	public int resoltuionWidth() {
		return 200;
	}

	@Override
	public int resoltuionHeight() {
		return 200;	
	}

	@Override 
	public String toString() { return "computerA"; }
}
