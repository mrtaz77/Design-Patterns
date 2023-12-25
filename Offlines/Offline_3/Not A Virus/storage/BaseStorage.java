package storage;

import java.time.LocalDateTime;

abstract class BaseStorage implements Storable {
	private String name;
	private double size;
	private String directory;
	private final LocalDateTime creationTime;

	public BaseStorage(String name, double size, String directory) {
        this.name = name;
        this.size = size;
        this.directory = directory;
        this.creationTime = LocalDateTime.now();
    }

	public void setSize(double size) { this.size = size; }

	@Override
	public String getName() { return name; }

	@Override
	public double getSize() { return size; }

	@Override
	public LocalDateTime getCreationTime() { return creationTime; }

	@Override
	public String getDirectory() { return directory; }

	@Override
	public long getComponentCount() { return 1; }
}
