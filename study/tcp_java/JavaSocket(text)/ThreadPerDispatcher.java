import java.net.*; // Socket��ServerSocket��ɬ��
import java.io.*; // IOException��ɬ��

class ThreadPerDispatcher implements Dispatcher {

  public void startDispatching(ServerSocket servSock, Logger logger,
                               ProtocolFactory protoFactory) {
    // �ʵפ˼¹Ԥ�������³������դ���ȶ��˥���åɤ��������ƥ����ӥ���Ԥ�
    for (;;) {
      try{
        Socket clntSock = servSock.accept(); // �֥�å�������³���Ԥ�
        Runnable protocol = protoFactory.createProtocol(clntSock, logger);
        Thread thread = new Thread(protocol);
        thread.start();
        logger.writeEntry("Created and started Thread = " + thread.getName());
      } catch (IOException e) {
        logger.writeEntry("Exception = " + e.getMessage());
      }
    }
    /* ������ʬ�ˤ���ã���ʤ� */
  }
}
