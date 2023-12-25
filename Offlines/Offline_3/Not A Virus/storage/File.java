package storage;

public class File extends BaseStorage {
	
    public File(String name, double size, String directory) {
		super(name, size, directory);
	}

	@Override
    public StorageType getType() { return StorageType.File; }
}
