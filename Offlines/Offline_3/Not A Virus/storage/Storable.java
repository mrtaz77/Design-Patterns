package storage;

import java.time.LocalDateTime;

public interface Storable {	
	public String getName();
	public StorageType getType();
	public double getSize();
	public String getDirectory();
	public long getComponentCount();
	public LocalDateTime getCreationTime();
}