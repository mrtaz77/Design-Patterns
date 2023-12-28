import java.util.Scanner;

import storage.CompoundStorage;
import storage.FileFolderCreatable;
import storage.RootDirectory;

public class MainConsoleIO {

	private static CompoundStorage currentDir;

	private static void init() {
		currentDir = RootDirectory.getRootDirectory();
	}

	private static void displayCurrentDirectory() {
		String file_path = currentDir.getDirectory();
		if(!file_path.endsWith("\\") && file_path != "" )file_path += "\\";
		System.out.print(file_path + currentDir.getName() + "> ");
	}

	public static void main(String[] args) {
        init();

		try (Scanner scanner = new Scanner(System.in)) {
			String command = "";
			while (true) {
				displayCurrentDirectory();
				command = scanner.nextLine();
				executeCommand(command);
				if(command.equals("q"))break;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
    }

    private static void executeCommand(String command) {
        String[] tokens = command.split(" ");
        String action = tokens[0];

		try {
			switch (action) {
				case "mkdrive":
					if (tokens.length != 2) {
						System.out.println("Wrong number of arguments for mkdrive. Usage: mkdrive <driveName>");
					}
					else if (currentDir instanceof RootDirectory) {
						((RootDirectory) currentDir).mkDrive(tokens[1]);
					} else {
						System.out.println("Cannot create drive in the current directory");
					}
					break;
				case "cd":
					if (tokens.length != 2) {
						System.out.println("Wrong number of arguments for cd. Usage: cd <directoryName>");
					}
					else currentDir = currentDir.cd(tokens[1]);
					break;
				case "mkdir":
					if (tokens.length != 2) {
						System.out.println("Wrong number of arguments for mkdir. Usage: mkdir <folderName>");
					}
					else if(currentDir instanceof RootDirectory) {
						System.out.println("Cannot create folder in root directory");
					}
					else ((FileFolderCreatable) currentDir).mkdir(tokens[1]);
					break;
				case "touch":
					if (tokens.length != 3) {
						System.out.println("Wrong number of arguments for touch. Usage: touch <fileName> <fileSize>");
						break;
					}
					if(currentDir instanceof RootDirectory) {
						System.out.println("Cannot create file in root directory");
					}
					else ((FileFolderCreatable) currentDir).touch(tokens[1], Double.parseDouble(tokens[2]));
					break;
				case "list":
					if (tokens.length != 1) {
						System.out.println("Wrong number of arguments for list. Usage: list");
					}
					else System.out.print(currentDir.list());
					break;
				case "ls":
					if (tokens.length != 2) {
						System.out.println("Wrong number of arguments for ls. Usage: ls <directoryName>");
					}
					else System.out.print(currentDir.ls(tokens[1]));
					break;
				case "delete":
					if (tokens.length < 2 || tokens.length > 3) {
						System.out.println("Wrong number of arguments for delete. Usage: delete <fileName> or delete -r <directoryName>");
					}
					else if (!tokens[1].equals("-r")) {
						currentDir.delete(tokens[1]);
					} else {
						currentDir.deleteRecursive(tokens[2]);
					}
					break;
				case "clear":
					if (tokens.length != 1) {
						System.out.println("Wrong number of arguments for clear. Usage: clear");
					}
					else {
						System.out.print("\033c");
						System.out.flush();
					}
					break;
				case "q":
					System.out.println("Closing terminal...");
					break;
				default:
					System.out.println("Invalid command.");
					break;
			}
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
    }
}
