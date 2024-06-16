import java.net.*; // Socket��ServerSocket��InetAddress��ɬ��
import java.io.*; // IOException��Input/OutputStream��ɬ��

public class TCPEchoServerThread {

  public static void main(String[] args) throws IOException {

    if (args.length != 1) // �����ο�����������Ĵ�٤�
      throw new IllegalArgumentException("Parameter(s): <Port>");

    int echoServPort = Integer.parseInt(args[0]); // �����Хݡ���

    // ���饤����Ȥ���³�׵������դ��륵���Х����åȤ���������
    ServerSocket servSock = new ServerSocket(echoServPort);

    Logger logger = new ConsoleLogger(); // ����å������򥳥󥽡���˽��Ϥ���

    // �ʵפ˼¹Ԥ�������³������դ���ȶ��˥���åɤ��������ƥ����ӥ���Ԥ�
    for (;;) {
      try{
        Socket clntSock = servSock.accept(); // �֥�å�������³���Ԥ�
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
