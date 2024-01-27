package computer;

public class ComputerB implements Computer  {

	@Override
	public String cpu() {
		return "cpuB";
	}

	@Override
	public String mmu() {
		return "mmuB";
	}

	@Override
	public int resoltuionWidth() {
		return 350;
	}

	@Override
	public int resoltuionHeight() {
		return 250;	
	}

	@Override 
	public String toString() { return "computerB"; }
}
