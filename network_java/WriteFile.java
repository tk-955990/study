import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;

class WriteFile {
  public static void main(String[] args) {

    try (BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        FileWriter file = new FileWriter("/home/tk955990/test.txt");
        PrintWriter writer = new PrintWriter(new BufferedWriter(file))) {

      while (true) {
        try {
          String input = reader.readLine();
          if (input.equals(",")) {
            break;
          } else {
            System.out.println(input);
            writer.println(input);
          }
        } catch (FileNotFoundException e) {
          System.err.println("error!");
        }
      }
    } catch (IOException e) {
      System.err.println("Error!");
    }
  }
}
