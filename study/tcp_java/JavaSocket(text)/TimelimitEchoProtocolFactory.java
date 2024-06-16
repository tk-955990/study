import java.net.*; // Socket��ɬ��
import java.io.*;  // IOException��Input/OutputStream��ɬ��
import java.util.*; // ArrayList��ɬ��

public class TimelimitEchoProtocolFactory implements ProtocolFactory {

  public Runnable createProtocol(Socket clntSock, Logger logger) {
    return new TimelimitEchoProtocol(clntSock, logger);
  }
}

class TimelimitEchoProtocol implements Runnable {
  private static final int BUFSIZE = 32; // �����Хåե��ΥХ��ȥ�����
  private static final String TIMELIMIT = "10000"; // �ǥե���ȤΥ������ߥåȡʥߥ��á�
  private static final String TIMELIMITPROP = "Timelimit"; // ����åɥץ�ѥƥ�

  private int timelimit;
  private Socket clntSock;
  private Logger logger;

  public TimelimitEchoProtocol(Socket clntSock, Logger logger) {
    this.clntSock = clntSock;
    this.logger = logger;
    // System�ץ�ѥƥ����饿�����ߥåȤ�������뤫���ǥե�����ͤ���Ѥ���
    timelimit = Integer.parseInt(System.getProperty(TIMELIMITPROP, TIMELIMIT));
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

      int recvMsgSize; // ����������å������Υ�����
      int totalBytesEchoed = 0; // ���饤����Ȥ�����������Х��ȿ�
      byte[] echoBuffer = new byte[BUFSIZE]; // �����Хåե�
      long endTime = System.currentTimeMillis() + timelimit;
      int timeBoundMillis = timelimit;

      clntSock.setSoTimeout(timeBoundMillis);

      // ���饤����Ȥ���³�򥯥��������-1��ɽ�蘆���ˤޤǼ�������
      while ((timeBoundMillis > 0) && // �����ͤ���ª����
            ((recvMsgSize = in.read(echoBuffer)) != -1)) {
        out.write(echoBuffer, 0, recvMsgSize);
        totalBytesEchoed += recvMsgSize;
        timeBoundMillis = (int) (endTime - System.currentTimeMillis());
        clntSock. setSoTimeout(timeBoundMillis);
      }

      entry.add("Client finished; echoed " + totalBytesEchoed + " bytes.");
    } catch (InterruptedIOException dummy) {
      entry.add("Read timed out");
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
