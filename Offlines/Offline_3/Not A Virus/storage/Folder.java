package storage;

public class Folder extends FileFolderCreatable{

	public Folder(String name, String directory) {
		super(name, directory);
	}

	@Override
	public StorageType getType() { return StorageType.Folder; }
}
