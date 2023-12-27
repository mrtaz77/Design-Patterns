package storage;

public class Drive extends FileFolderCreatable {

	public Drive(String name, String directory) {
		super(name, directory);
	}

	@Override
	public StorageType getType() { return StorageType.Drive; }

}
