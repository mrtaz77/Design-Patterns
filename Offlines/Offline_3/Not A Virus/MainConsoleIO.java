import java.util.Scanner;

import storage.CompoundStorage;
import storage.RootDirectory;

public class MainConsoleIO {

	private static CompoundStorage currentDir;

	private static void init() {
		currentDir = RootDirectory.getRootDirectory();
	}

	 public static void main(String[] args) {
        init();

		try (Scanner scanner = new Scanner(System.in)) {
			String command = "";
			while (true) {
				String file_path = currentDir.getDirectory();
				if(!file_path.endsWith("\\") && file_path != "" )file_path += "\\";
				System.out.print(file_path + currentDir.getName() + "> ");
				command = scanner.nextLine();
				System.out.println();
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
						break;
					}
					if (currentDir instanceof RootDirectory) {
						((RootDirectory) currentDir).mkDrive(tokens[1]);
					} else {
						System.out.println("Cannot create drive in the current directory");
					}
					break;
				case "cd":
					if (tokens.length != 2) {
						System.out.println("Wrong number of arguments for cd. Usage: cd <directoryName>");
						break;
					}
					currentDir = currentDir.cd(tokens[1]);
					break;
				case "mkdir":
					if (tokens.length != 2) {
						System.out.println("Wrong number of arguments for mkdir. Usage: mkdir <folderName>");
						break;
					}
					currentDir.mkDir(tokens[1]);
					break;
				case "touch":
					if (tokens.length != 3) {
						System.out.println("Wrong number of arguments for touch. Usage: touch <fileName> <fileSize>");
						break;
					}
					currentDir.touch(tokens[1], Double.parseDouble(tokens[2]));
					break;
				case "list":
					if (tokens.length != 1) {
						System.out.println("Wrong number of arguments for list. Usage: list");
						break;
					}
					System.out.println(currentDir.list());
					break;
				case "ls":
					if (tokens.length != 2) {
						System.out.println("Wrong number of arguments for ls. Usage: ls <directoryName>");
						break;
					}
					System.out.println(currentDir.ls(tokens[1]));
					break;
				case "delete":
					if (tokens.length < 2 || tokens.length > 3) {
						System.out.println("Wrong number of arguments for delete. Usage: delete <fileName> or delete -r <directoryName>");
						break;
					}
					if (!tokens[1].equals("-r")) {
						currentDir.delete(tokens[1]);
					} else {
						currentDir.deleteRecursive(tokens[2]);
					}
					break;
				case "cls":
					if (tokens.length != 1) {
						System.out.println("Wrong number of arguments for cls. Usage: cls");
						break;
					}
					System.out.print("\033c");
					System.out.flush();
					break;
				case "q":
					System.out.println("Killing terminal...\n");
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
