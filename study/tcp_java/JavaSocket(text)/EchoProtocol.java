import java.net.*; // Socket��ɬ��
import java.io.*; // IOException��Input/OutputStream��ɬ��
import java.util.*; // ArrayList��ɬ��

class EchoProtocol implements Runnable {
  static public final int BUFSIZE = 32; // I/O�Хåե��ΥХ��ȥ�����

  private Socket clntSock; // ��³�����å�
  private Logger logger; // ���󥰵�ǽ

  public EchoProtocol(Socket clntSock, Logger logger) {
    this.clntSock = clntSock;
    this.logger = logger;
  }

  public void run() {
    ArrayList entry = new ArrayList();
    entry.add("Client address and port = " +
      clntSock.getInetAddress().getHostAddress() + ":" +
      clntSock.getPort());
    entry.add("Thread = " + Thread.currentThread().getName());

    try{
      // �����åȤ������Ϥ���ӽ���I/O���ȥ꡼����������
      InputStream in = clntSock.getInputStream();
      OutputStream out = clntSock.getOutputStream();

      int recvMsgSize;                      // ����������å������Υ�����
      int totalBytesEchoed = 0;             // ���饤����Ȥ�����������Х��ȿ�
      byte[] echoBuffer = new byte[BUFSIZE];// �����Хåե�
      // ���饤����Ȥ���³�򥯥��������-1��ɽ�蘆���ˤޤǼ�������
      while ((recvMsgSize = in.read(echoBuffer)) != -1) {
        out.write(echoBuffer, 0, recvMsgSize);
        totalBytesEchoed += recvMsgSize;
      }

      entry.add("Client finished; echoed " + totalBytesEchoed + " bytes.");
    } catch (IOException e) {
      entry.add("Exception = " + e.getMessage());
    }

    try { // �����åȤ򥯥�������
      clntSock.close();
    } catch (IOException e) {
      entry.add("Exception = " + e.getMessage());
    }

    logger.writeEntry(entry);
  }
}
