import java.net.*; // Socket��ɬ��
import java.io.*; // IOException��[File]Input/OutputStream��ɬ��

public class CompressClient {

  public static final int BUFSIZE = 256; // �ɤ߼��Хåե��Υ�����

  public static void main(String[] args) throws IOException {

    if (args.length != 3) // �����ο�����������Ĵ�٤�
      throw new IllegalArgumentException("Parameter(s): <Server> <Port> <File>");

    String server = args[0]; // ������̾�ޤ���IP���ɥ쥹
    int port = Integer.parseInt(args[1]); // �����Хݡ���
    String filename = args[2]; // �ǡ������ɤ߼��ե�����

    // ���ϥե����뤪��ӽ��ϥե������input.gz�Ȥ���������̾���ˤ���ˤ򥪡��ץ󤹤�
    FileInputStream fileIn = new FileInputStream(filename);
    FileOutputStream fileOut = new FileOutputStream(filename + ".gz");

    // �����Фλ���Υݡ��Ȥ���³���륽���åȤ���������
    Socket sock = new Socket(server, port);

    // ̤���̥Х��ȥ��ȥ꡼��򥵡��Ф�����
    sendBytes(sock, fileIn);

    // �����Ф��鰵�̥Х��ȥ��ȥ꡼���������
    InputStream sockIn = sock.getInputStream();
    int bytesRead; // �ɤ߼�ä��Х��ȿ�
    byte[] buffer = new byte[BUFSIZE]; // �Х��ȥХåե�
    while ((bytesRead = sockIn.read(buffer)) != -1) {
      fileOut.write(buffer, 0, bytesRead);
      System.out.print("R"); // �ɤ߼��ʹԥ��󥸥�����
    }
    System.out.println(); // �ʹԥ��󥸥������Ԥν����

    sock.close(); // �����åȤȤ��Υ��ȥ꡼��򥯥�������
    fileIn.close(); // �ե����륹�ȥ꡼��򥯥�������
    fileOut.close();
  }

  private static void sendBytes(Socket sock, InputStream fileIn)
      throws IOException {
    OutputStream sockOut = sock.getOutputStream();
    int bytesRead; // �ɤ߼�ä��Х��ȿ�
    byte[] buffer = new byte[BUFSIZE]; // �Х��ȥХåե�
    while ((bytesRead = fileIn.read(buffer)) != -1) {
      sockOut.write(buffer, 0, bytesRead);
      System.out.print("W"); // �񤭹��߿ʹԥ��󥸥�����
    }
    sock.shutdownOutput(); // ������λ
  }
}
