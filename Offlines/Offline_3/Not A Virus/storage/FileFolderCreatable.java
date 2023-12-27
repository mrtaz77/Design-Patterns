package storage;


public abstract class FileFolderCreatable extends CompoundStorage {

	private String getFilePath(){
		if(getDirectory().isEmpty())return getName();
		String file_path = getDirectory();
		if(!file_path.endsWith("\\"))file_path += "\\";
		return file_path + getName();
	}

	public FileFolderCreatable(String name, String directory) {
		super(name, directory);
	}

	public void mkdir(String name) {
		notInCurrentDirectory(components, name);
		components.put(name, new Folder(name, getFilePath()));
	}

	public void touch(String name, double size) {
		notInCurrentDirectory(components,name);
		components.put(name, new File(name, size, getFilePath()));
	}
	
}