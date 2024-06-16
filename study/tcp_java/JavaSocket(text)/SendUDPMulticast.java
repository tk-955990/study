import java.net.*; // MulticastSocket��DatagramPacket��InetAddress��ɬ��
import java.io.*; // IOException��ɬ��

public class SendUDPMulticast {

  public static void main(String args[]) throws Exception {

    if ((args.length < 2) || (args.length > 3)) // �����ο�����������Ĵ�٤�
      throw new IllegalArgumentException(
        "Parameter(s): <Multicast Addr> <Port> [<TTL>]");

    InetAddress destAddr = InetAddress.getByName(args[0]); // ���襢�ɥ쥹
    if (!destAddr.isMulticastAddress()) // �ޥ�����㥹�ȥ��ɥ쥹��Ĵ�٤�
      throw new IllegalArgumentException("Not a multicast address");

    int destPort = Integer.parseInt(args[1]); // ����ݡ���

    int TTL; // �ǡ�����������¸���֡�Time To Live��
    if (args.length == 3)
      TTL = Integer.parseInt(args[2]);
    else
      TTL = 1; // �ǥե����TTL

    ItemQuote quote = new ItemQuote(1234567890987654L, "5mm Super Widgets",
                                    1000, 12999, true, false);

    MulticastSocket sock = new MulticastSocket(); // �ޥ�����㥹�����������å�
    sock.setTimeToLive(TTL); // ���٤ƤΥǡ���������TTL�����ꤹ��

    ItemQuoteEncoder encoder = new ItemQuoteEncoderText(); // �ƥ����ȤΥ��󥳡���
    byte[] codedQuote = encoder.encode(quote);


    // �ǡ�������������������������
    DatagramPacket message = new DatagramPacket(codedQuote, codedQuote.length,
                                              destAddr, destPort);
    sock.send(message);

    sock.close();
  }
}
