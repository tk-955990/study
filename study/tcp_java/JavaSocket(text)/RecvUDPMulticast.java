import java.net.*; // MulticastSocket��DatagramPacket��InetAddress��ɬ��
import java.io.*; // IQException��ɬ��

public class RecvUDPMulticast implements ItemQuoteTextConst {

  public static void main(String[] args) throws Exception {

    if (args.length != 2) // �����ο�����������Ĵ�٤�
      throw new IllegalArgumentException("Parameter(s): <Multicast Addr> <Port>");

    InetAddress address = InetAddress.getByName(args[0]); // �ޥ�����㥹�ȥ��ɥ쥹
    if (!address.isMulticastAddress()) // �ޥ�����㥹�ȥ��ɥ쥹��Ĵ�٤�
      throw new IllegalArgumentException("Not a multicast address");

    int port = Integer.parseInt(args[1]); // �ޥ�����㥹�ȥݡ���

    MulticastSocket sock = new MulticastSocket(port); // �ޥ�����㥹�ȼ��������å�
    sock.joinGroup(address); // �ޥ�����㥹�ȥ��롼�פλ��ä���

    // �ǡ����������������Ƽ�������
    DatagramPacket packet = new DatagramPacket(
      new byte[MAX_WIRE_LENGTH], MAX_WIRE_LENGTH);
    sock.receive(packet);

    ItemQuoteDecoder decoder = new ItemQuoteDecoderText(); // �ƥ����ȤΥǥ�����
    ItemQuote quote = decoder.decode(packet);
    System.out.println(quote);

    sock.close();
  }
}
