import java.net.*; // ServerSocket��ɬ��
import java.io.*;  // IOException��ɬ��

public class ThreadMain {

  public static void main(String[] args) throws Exception {

    if (args.length != 3) // �����ο�����������Ĵ�٤�
      throw new IllegalArgumentException("Parameter(s): [<Optional properties>]"
                                         + " <Port> <Protocol> <Dispatcher>");

    int servPort = Integer.parseInt(args[0]); // �����Хݡ���
    String protocolName = args[1] ; // �ץ�ȥ���̾
    String dispatcherName = args[2]; // �ǥ����ѥå���̾

    ServerSocket servSock = new ServerSocket(servPort);
    Logger logger = new ConsoleLogger(); // ����å������򥳥󥽡���˽��Ϥ���
    ProtocolFactory protoFactory = (ProtocolFactory) // �ץ�ȥ���ե����ȥ���������
      Class.forName(protocolName + "ProtocolFactory").newInstance();
    Dispatcher dispatcher = (Dispatcher) // �ǥ����ѥå�����������
      Class.forName(dispatcherName + "Dispatcher").newInstance();

    dispatcher.startDispatching(servSock, logger, protoFactory);
    /* ������ʬ�ˤ���ã���ʤ� */
  }
}
