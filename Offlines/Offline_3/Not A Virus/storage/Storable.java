package storage;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public interface Storable {	
	public String getName();
	public StorageType getType();
	public double getSize();
	public String getDirectory();
	public long getComponentCount();
	public LocalDateTime getCreationTime();
	

	default public String ls() {
		StringBuilder out = new StringBuilder();

		out.append("Name: ").append(getName()).append("\n");
		out.append("Type: ").append(getType().name()).append("\n");
		out.append("Size: ").append(String.format("%.2f", getSize()).replaceAll("\\.0+$", "")).append(" kB\n");
		out.append("Directory: ").append("\"").append(getDirectory()).append("\"").append("\n");
		out.append("Component Count: ").append(getComponentCount()).append("\n");
		out.append("Creation Time: ").append(getCreationTime().format(DateTimeFormatter.ofPattern("dd MMMM, yyyy h:mm a"))).append("\n");

		return out.toString();
	}
}