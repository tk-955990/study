import java.net.*; // Socket、ServerSocketに必要
import java.io.*; // IOExceptionに必要

class PoolDispatcher implements Dispatcher {

  static final String NUMTHREADS = "8"; // スレッドプールのデフォルトサイズ
  static final String THREADPROP = "Threads"; // スレッドプロパティの名前

  private int numThreads; // プールに含まれるスレッドの数

  public PoolDispatcher() {
    // Systemプロパティからスレッドの数を取得するか、デフォルト値を使用する
    numThreads = Integer.parseInt(System.getProperty(THREADPROP, NUMTHREADS));
  }

  public void startDispatching(final ServerSocket servSock, final Logger logger,
                               final ProtocolFactory protoFactory) {
    // それぞれが反復サーバとして動作する、N-1個のスレッドを生成する
    for (int i = 0; i < (numThreads - 1); i++) {
      Thread thread = new Thread() {
        public void run() {
          dispatchLoop(servSock, logger, protoFactory);
        }
      };
      thread. start();
      logger.writeEntry("Created and started Thread = " + thread.getName());
    }
    logger.writeEntry("Iterative server starting in main thread " +
                    Thread.currentThread().getName());
    // メインスレッドをN番目の反復サーバとして使用する
    dispatchLoop(servSock, logger, protoFactory);
    /* NOT REACHED */
  }

  private void dispatchLoop(ServerSocket servSock, Logger logger,
                            ProtocolFactory protoFactory) {
    // 永久に実行し、各接続を受け付けて処理する
    for (;;) {
      try{
        Socket clntSock = servSock.accept(); // ブロックして接続を待つ
        Runnable protocol = protoFactory.createProtocol(clntSock, logger);
        protocol.run();
      } catch (IOException e) {
        logger.writeEntry("Exception = " + e.getMessage());
      }
    }
  }
}
