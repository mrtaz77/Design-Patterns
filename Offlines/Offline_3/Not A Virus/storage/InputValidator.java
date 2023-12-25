package storage;

import java.util.Hashtable;

interface InputValidator {
	default void checkNull(String name)throws NullPointerException  {
		if(name == null) throw new NullPointerException("No name specified");
	}

	default void inCurrentDirectory(Hashtable<String, Storable> components, String name)throws IllegalArgumentException {
		checkNull(name);
		if(!components.containsKey(name))throw new IllegalArgumentException(name + " not found");
	}

	default void notInCurrentDirectory(Hashtable<String, Storable> components, String name)throws IllegalArgumentException {
		checkNull(name);
		if(components.containsKey(name))throw new IllegalArgumentException(name + " already exists in current directory");
	}

	default Storable getComponent(Hashtable<String, Storable> components, String name)throws IllegalArgumentException {
		inCurrentDirectory(components, name);
		return components.get(name);
	}
}