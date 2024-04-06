package adapter.parser;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

public class Adapter implements Parser {
	private Parser parser;

	public Adapter(Parser parser) {
		this.parser = parser;
	}

	@Override
	public int calculateSum(File file) {
		if (file == null) {
            System.out.println("Please provide a valid file");
            return -1;
        }
		int sum = 0;
		try {
			var intermediate_int = new File("./intermediate_int.txt");
			var reader = new Scanner(new BufferedReader(new FileReader(file)));
			var writer = new BufferedWriter(new FileWriter(intermediate_int));
			while (reader.hasNext()) {
                var temp = (int)reader.next().charAt(0);
				writer.write(String.valueOf(temp));
				writer.write(" ");
            }
			reader.close();
			writer.close();
			sum = parser.calculateSum(intermediate_int);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return sum;
	}
	
}
