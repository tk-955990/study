import java.io.*;
import java.net.*;

private static final int BUFSIZE = 32;

public class TCPEchoServer {
    public static void main(String[] args) throws IOException {
        if (args.length != 1)
            throw new IllegalAccessException("Paramater(s): <port>");

        int servPort = Integer.parseInt(args[0]);

        ServerSocket servSock = new ServerSocket(servPort);

        int recvMsgSize;
        byte[] byteBuffer = new byte[BUFSIZE];

        for (;;) {
            Socket clntSock = servSock.accept();

            System.out.println("Handling client at" + clntSock.getInetAddress() + " on port " + clntSock.getPort());

            InputStream in = clntSock.getInputStream();
            OutputStream out = clntSock.getOutputStream();

            while ((recvMsgSize = in.read(byteBuffer)) != -1)
                out.write(byteBuffer, 0, recvMsgSize);

            clntSock.close();
        }
    }
}
