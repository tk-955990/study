import java.net.*; // Socket��ServerSocket��ɬ��
import java.io.*; // IOException��ɬ��

class PoolDispatcher implements Dispatcher {

  static final String NUMTHREADS = "8"; // ����åɥס���Υǥե���ȥ�����
  static final String THREADPROP = "Threads"; // ����åɥץ�ѥƥ���̾��

  private int numThreads; // �ס���˴ޤޤ�륹��åɤο�

  public PoolDispatcher() {
    // System�ץ�ѥƥ����饹��åɤο���������뤫���ǥե�����ͤ���Ѥ���
    numThreads = Integer.parseInt(System.getProperty(THREADPROP, NUMTHREADS));
  }

  public void startDispatching(final ServerSocket servSock, final Logger logger,
                               final ProtocolFactory protoFactory) {
    // ���줾�줬ȿ�������ФȤ���ư��롢N-1�ĤΥ���åɤ���������
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
    // �ᥤ�󥹥�åɤ�N���ܤ�ȿ�������ФȤ��ƻ��Ѥ���
    dispatchLoop(servSock, logger, protoFactory);
    /* NOT REACHED */
  }

  private void dispatchLoop(ServerSocket servSock, Logger logger,
                            ProtocolFactory protoFactory) {
    // �ʵפ˼¹Ԥ�������³������դ��ƽ�������
    for (;;) {
      try{
        Socket clntSock = servSock.accept(); // �֥�å�������³���Ԥ�
        Runnable protocol = protoFactory.createProtocol(clntSock, logger);
        protocol.run();
      } catch (IOException e) {
        logger.writeEntry("Exception = " + e.getMessage());
      }
    }
  }
}
