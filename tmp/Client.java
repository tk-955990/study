import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.Socket;

class Client {
  public static void main(String[] args) {

    try (Socket socket = new Socket("localhost", 4444);
        BufferedReader inputMessage = new BufferedReader(new InputStreamReader(System.in));
        BufferedWriter output =
            new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
        BufferedReader response =
            new BufferedReader(new InputStreamReader(socket.getInputStream()));
        FileWriter writer = new FileWriter("response.txt", true); ) {

      System.out.println("enter message...");
      String sendMessage;
      String responseMessage;
      while (true) {
        sendMessage = inputMessage.readLine();

        if (sendMessage.equals("exit")) {
          System.out.println("connect close");
          break;
        }
        output.write(sendMessage);
        output.newLine();
        output.flush();

        responseMessage = response.readLine();
        if (responseMessage.equals(null)) {
          System.out.println("connect close");
          break;
        }

        writer.write("responseMessage is \" " + responseMessage + " \"\n");
      }
    } catch (IOException e) {
      e.printStackTrace();
    }
  }
}
