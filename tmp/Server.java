import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;

class Server {
  public static void main(String[] args) {

    System.out.println("waiting...");

    try (ServerSocket server = new ServerSocket(4444);
        Socket socket = server.accept();
        BufferedReader input = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        BufferedWriter output =
            new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
        FileWriter writer = new FileWriter("test.txt", true); ) {

      InetAddress address = InetAddress.getLocalHost();
      System.out.println("connetc from : " + address.getHostAddress());
      String recievedMessage;

      while ((recievedMessage = input.readLine()) != null && !recievedMessage.equals("exit")) {
        System.out.println("recievedMessage : " + recievedMessage);
        writer.write("recievedMessage is \" " + recievedMessage + " \"\n");
        output.write(recievedMessage);
        output.newLine();
        output.flush();
      }
      System.out.println("connect closed");
    } catch (IOException e) {
      e.printStackTrace();
    }
  }
}
