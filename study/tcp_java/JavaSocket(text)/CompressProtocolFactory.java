import java.net.*; // Socket��ɬ��
import java.io.*; // IOException��Input/OutputStream��ɬ��
import java.util.*; // ArrayList��ɬ��
import java.util.zip.*; // GZIPOutputStream��ɬ��

public class CompressProtocolFactory implements ProtocolFactory {

  public static final int BUFSIZE = 1024; // �����Хåե��Υ�����

  public Runnable createProtocol(final Socket clntSock, final Logger logger) {
    return new Runnable() {
      public void run() {
        CompressProtocolFactory.handleClient(clntSock, logger);
      }
    };
  }

  public static void handleClient(Socket clntSock, Logger logger) {
    ArrayList entry = new ArrayList();
    entry.add("Client address and port = " +
      clntSock.getInetAddress().getHostAddress() + ":" +
      clntSock.getPort());
    entry.add("Thread = " + Thread.currentThread().getName());

    try{
      // �����åȤ������ϥ��ȥ꡼��Ƚ��ϥ��ȥ꡼����������
      InputStream in = clntSock.getInputStream();
      GZIPOutputStream out = new GZIPOutputStream(clntSock.getOutputStream());

      byte[] buffer = new byte[BUFSIZE]; // �ɤ߼��/�񤭹��ߥХåե��������Ƥ�
      int bytesRead; // �ɤ߼�ä��Х��ȿ�
      // ���饤����Ȥ���³�򥯥��������-1��ɽ�蘆���ˤޤǼ�������
      while ((bytesRead = in.read(buffer)) != -1)
        out.write(buffer, 0, bytesRead);

      out.finish(); // GZIPOutputStream����Х��Ȥ�ե�å��夹��
      } catch (IOException e) {
        logger.writeEntry("Exception = + e.getMessage()");
      }

      try { // �����åȤ򥯥�������
        clntSock.close();
      } catch (IOException e) {
        entry.add("Exception = " + e.getMessage());
      }

      logger.writeEntry(entry);
  }
}
