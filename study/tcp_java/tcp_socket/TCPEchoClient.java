import java.io.*;
import java.net.*;

public class TCPEchoClient {

    public static void main(String[] args) throws IOException {

        if ((args.length < 2) || (args.length > 3)) // 引数の数が正しいかどうかを調べる
            throw new IllegalArgumentException("Parameter(s): <Server> <Word> [<Port>]");

        String server = args[0]; // サーバー名またはIPアドレス
        // デフォルトの文字エンコードを方式を使って入力　Stringをバイトに変換する
        byte[] byteBuffer = args[1].getBytes();

        int servPort = (args.length == 3) ? Integer.parseInt(args[2]) : 7;
        //
        Socket socket = new Socket(server, servPort);
        System.out.println("Connected to server ... sending echo string");

        InputStream in = socket.getInputStream();
        OutputStream out = socket.getOutputStream();
        out.write(byteBuffer);

        int totalBytesRcvd = 0;
        int bytesRcvd;
        while (totalBytesRcvd < byteBuffer.length) {
            if ((bytesRcvd = in.read(byteBuffer, totalBytesRcvd, byteBuffer.length - totalBytesRcvd)) == -1)

                throw new SocketException("Connection closed prematurely");
            totalBytesRcvd += bytesRcvd;
        }

        System.out.println("Received: " + new String(byteBuffer));

        socket.close();
    }
}