package storage;

import java.util.Hashtable;

interface InputValidator {
	default void checkNull(String name)throws NullPointerException  {
		if(name == null) throw new NullPointerException("No name specified\n");
	}

	default void inCurrentDirectory(Hashtable<String, Storable> components, String name)throws IllegalArgumentException {
		checkNull(name);
		if(!components.containsKey(name))throw new IllegalArgumentException(name + " not found\n");
	}

	default void notInCurrentDirectory(Hashtable<String, Storable> components, String name)throws IllegalArgumentException {
		checkNull(name);
		if(components.containsKey(name))throw new IllegalArgumentException(name + " already exists in current directory\n");
	}

	default Storable getComponent(Hashtable<String, Storable> components, String name)throws IllegalArgumentException {
		inCurrentDirectory(components, name);
		return components.get(name);
	}
}