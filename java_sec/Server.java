import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.ServerSocket;
import java.net.Socket;

class Server {

  public static void main(String[] args) {
    try (ServerSocket server = new ServerSocket(4444); ) {
      System.out.println("Sending...");
      Socket socket = server.accept();

      String clientIpaddress = socket.getInetAddress().getHostAddress();
      System.out.println("Client IP Address : " + clientIpaddress);

      BufferedReader input = new BufferedReader(new InputStreamReader(socket.getInputStream()));
      String receivedMessage;
      BufferedWriter output = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));

      while (!(receivedMessage = input.readLine()).equals("exit")) {

        System.out.println("receivedMessage : " + receivedMessage);

        output.write(receivedMessage);
        output.newLine();
        output.flush();
      }
    } catch (IOException e) {
      e.printStackTrace();
    }
  }
}
