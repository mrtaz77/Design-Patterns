package storage;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.Map;

public abstract class CompoundStorage extends BaseStorage implements InputValidator {

	protected Hashtable<String, Storable> components = new Hashtable<>();

	private String getFilePath(){
		if(getDirectory().isEmpty())return getName();
		String file_path = getDirectory();
		if(!file_path.endsWith("\\"))file_path += "\\";
		return file_path + getName();
	}

	public CompoundStorage(String name, String directory) {
		super(name, 0, directory);
	}

	@Override
	public double getSize() {
		double totalSize = 0;
		
        Enumeration<Storable> values = components.elements();
        while (values.hasMoreElements()) totalSize += values.nextElement().getSize();

        return totalSize;
	}

	@Override
	public long getComponentCount() {
		return components.size();
	}

	public void touch(String name, double size) {
		notInCurrentDirectory(components,name);
		components.put(name, new File(name, size, getFilePath()));
	}

	public void mkDir(String name) {
		notInCurrentDirectory(components, name);
		components.put(name, new Folder(name, getFilePath()));
	}

	public void delete(String name) {
		Storable component = getComponent(components, name);
		if(component instanceof File || component.getSize() == 0)components.remove(name);
		else throw new IllegalArgumentException(name + " is not empty");
	}
	
	public void deleteRecursive(String name) {
		Storable component = getComponent(components, name);
		if(component instanceof File) {
			System.out.println("Warning : Deleting file " + name + " permanently...");
		}
		else {
			Enumeration<Storable> entries = ((CompoundStorage) component).components.elements();
			while (entries.hasMoreElements()) {
				Storable entry = entries.nextElement();
				((CompoundStorage) component).deleteRecursive(entry.getName());
			}
			System.out.println("Warning : Deleting directory " + name + " permanently...");
		}
		components.remove(name);
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

			output.append(String.format("%-16s%8s kB%25s%n", name, formattedSize, formattedCreationTime));
		}

		return output.toString();
	}

	public CompoundStorage cd(String name) {
		if(name.equals("~"))return RootDirectory.getRootDirectory();
		Storable component = getComponent(components, name);
		if(component instanceof File) {
			throw new IllegalArgumentException(name + " is not a directory");
		}
		else {
			return (CompoundStorage) component;
		}
	}

	public String ls(String name) {
		Storable component = getComponent(components, name);
		StringBuilder out = new StringBuilder();

		out.append("Name: ").append(component.getName()).append("\n");
		out.append("Type: ").append(component.getType().name()).append("\n");
		out.append("Size: ").append(String.format("%.2f", component.getSize()).replaceAll("\\.0+$", "")).append(" kB\n");
		out.append("Directory: ").append("\"").append(component.getDirectory()).append("\"").append("\n");
		out.append("Component Count: ").append(component.getComponentCount()).append("\n");
		out.append("Creation Time: ").append(component.getCreationTime().format(DateTimeFormatter.ofPattern("dd MMMM, yyyy h:mm a"))).append("\n");

		return out.toString();
	}
}