import java.net.*; // ServerSocketに必要
import java.io.*;  // IOExceptionに必要

public class ThreadMain {

  public static void main(String[] args) throws Exception {

    if (args.length != 3) // 引数の数が正しいか調べる
      throw new IllegalArgumentException("Parameter(s): [<Optional properties>]"
                                         + " <Port> <Protocol> <Dispatcher>");

    int servPort = Integer.parseInt(args[0]); // サーバポート
    String protocolName = args[1] ; // プロトコル名
    String dispatcherName = args[2]; // ディスパッチャ名

    ServerSocket servSock = new ServerSocket(servPort);
    Logger logger = new ConsoleLogger(); // ログメッセージをコンソールに出力する
    ProtocolFactory protoFactory = (ProtocolFactory) // プロトコルファクトリを取得する
      Class.forName(protocolName + "ProtocolFactory").newInstance();
    Dispatcher dispatcher = (Dispatcher) // ディスパッチャを取得する
      Class.forName(dispatcherName + "Dispatcher").newInstance();

    dispatcher.startDispatching(servSock, logger, protoFactory);
    /* この部分には到達しない */
  }
}
