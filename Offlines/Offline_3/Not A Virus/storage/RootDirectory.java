package storage;

public class RootDirectory extends CompoundStorage {

	private static volatile RootDirectory instance;

	private RootDirectory() {
		super("~", "");
	}

	@Override
	public StorageType getType() {
		return StorageType.RootDirectory;
	}

	public static RootDirectory getRootDirectory() {
		if (instance == null) {
			instance = new RootDirectory();
		}
		return instance;
	}

	public void mkDrive(String name) {
		notInCurrentDirectory(instance.components, name);
		instance.components.put(name + ":", new Drive(name, ""));
	}
}

