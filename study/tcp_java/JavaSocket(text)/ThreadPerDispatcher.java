import java.net.*; // Socket、ServerSocketに必要
import java.io.*; // IOExceptionに必要

class ThreadPerDispatcher implements Dispatcher {

  public void startDispatching(ServerSocket servSock, Logger logger,
                               ProtocolFactory protoFactory) {
    // 永久に実行し、各接続を受け付けると共にスレッドを生成してサービスを行う
    for (;;) {
      try{
        Socket clntSock = servSock.accept(); // ブロックして接続を待つ
        Runnable protocol = protoFactory.createProtocol(clntSock, logger);
        Thread thread = new Thread(protocol);
        thread.start();
        logger.writeEntry("Created and started Thread = " + thread.getName());
      } catch (IOException e) {
        logger.writeEntry("Exception = " + e.getMessage());
      }
    }
    /* この部分には到達しない */
  }
}
