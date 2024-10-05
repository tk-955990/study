import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.Socket;

class Client {
  public static void main(String[] args) {

    try (Socket socket = new Socket("192.168.0.8", 4444); ) {
      BufferedWriter output = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));

      BufferedReader inputData = new BufferedReader(new InputStreamReader(System.in));

      String sendMessage = inputData.readLine();
      output.write(sendMessage);
      output.newLine();
      output.flush();

      System.out.println("sendMessage : " + sendMessage);

      BufferedReader input = new BufferedReader(new InputStreamReader(socket.getInputStream()));

      String receivedMessage = input.readLine();
      System.out.println(receivedMessage);
    } catch (IOException e) {
      e.printStackTrace();
    }
  }
}
