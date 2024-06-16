import java.net.*; // Socket、ServerSocket、InetAddressに必要
import java.io.*; // IOException、Input/OutputStreamに必要

public class TCPEchoServerThread {

  public static void main(String[] args) throws IOException {

    if (args.length != 1) // 引数の数が正しいか調べる
      throw new IllegalArgumentException("Parameter(s): <Port>");

    int echoServPort = Integer.parseInt(args[0]); // サーバポート

    // クライアントの接続要求を受け付けるサーバソケットを生成する
    ServerSocket servSock = new ServerSocket(echoServPort);

    Logger logger = new ConsoleLogger(); // ログメッセージをコンソールに出力する

    // 永久に実行し、各接続を受け付けると共にスレッドを生成してサービスを行う
    for (;;) {
      try{
        Socket clntSock = servSock.accept(); // ブロックして接続を待つ
        EchoProtocol protocol = new EchoProtocol(clntSock, logger);
        Thread thread = new Thread(protocol);
        thread.start();
        logger.writeEntry("Created and started Thread = " + thread.getName());
      } catch (IOException e) {
        logger.writeEntry("Exception = " + e.getMessage());
      }
    }

    /* NOT REACHED */
  }
}
