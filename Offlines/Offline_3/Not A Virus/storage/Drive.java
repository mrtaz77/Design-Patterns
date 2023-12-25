package storage;

public class Drive extends CompoundStorage {

	public Drive(String name, String directory) {
		super(name, directory);
	}

	@Override
	public StorageType getType() { return StorageType.Drive; }

}
