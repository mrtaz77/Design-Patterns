package storage;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Hashtable;
import java.util.Map;

public abstract class CompoundStorage extends BaseStorage implements InputValidator {

	protected Hashtable<String, Storable> components = new Hashtable<>();

	private void deleteAllComponents() {
		components.clear();
	}

	public CompoundStorage(String name, String directory) {
		super(name, 0, directory);
	}

	@Override
	public long getComponentCount() {
		return components.size();
	}

	public void touch(String name, double size) {
		notInCurrentDirectory(components,name);
		components.put(name, new File(name, size, getDirectory() + "\\" + name));
		setSize(getSize() + size);
	}

	public void makeDir(String name) {
		notInCurrentDirectory(components, name);
		components.put(name, new Folder(name, getDirectory() + "\\" + name));
	}

	public void delete(String name) {
		Storable component = getComponent(components, name);
		if(component instanceof File)components.remove(name);
		else {
			if(component.getSize() == 0)components.remove(name);
			else throw new IllegalArgumentException(name + " is not empty\n");
		}
	}
	
	public void deleteRecursive(String name) {
		Storable component = getComponent(components, name);
		if(component instanceof File) {
			System.out.println("Warning : Deleting file "+name+" permanently...\n");
			components.remove(name);
		}
		else {
			((CompoundStorage) component).deleteAllComponents();
			components.remove(name);
		}
	}

	public String list() {
		StringBuilder output = new StringBuilder();

		for (Map.Entry<String, Storable> entry : components.entrySet()) {
			Storable component = entry.getValue();
			String name = component.getName();
			double size = component.getSize();
			LocalDateTime creationTime = component.getCreationTime();

			String formattedCreationTime = creationTime.format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss"));
			String formattedSize = String.format("%.2f", size).replaceAll("\\.0+$", "");

			output.append(String.format("%-20s%-10s kB%-20s%n", name, formattedSize, formattedCreationTime));
		}

		return output.toString();
	}

	public CompoundStorage cd(String name) {
		if(name.equals("~"))return RootDirectory.getRootDirectory();
		Storable component = getComponent(components, name);
		if(component instanceof File) {
			throw new IllegalArgumentException(name + " is not a directory\n");
		}
		else {
			return (CompoundStorage) component;
		}
	}
}