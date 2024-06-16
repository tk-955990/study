import java.net.*;  // DatagramSocket��DatagramPacket��InetAddress��ɬ��
import java.io.*;   // IOException��ɬ��

public class SendUDP {

  public static void main(String args[]) throws Exception {


    if (args.length != 2 && args.length != 3)               // �����ο������������ɤ�����Ĵ�٤�
    throw new IllegalArgumentException("Parameter(s): <Destination>" +
               " <Port> [<encoding]");

    InetAddress destAddr = InetAddress.getByName(args[0]);  // ���襢�ɥ쥹
    int destPort = Integer.parseInt(args[1]);               // ����ݡ���

    ItemQuote quote = new ItemQuote(1234567890987654L, "5mm Super Widgets",
                                    1000, 12999, true, false);

    DatagramSocket sock = new DatagramSocket();             // �����Ѥ�UDP�����å�

    ItemQuoteEncoder encoder = (args.length == 3 ?
      new ItemQuoteEncoderText(args[2]):
      new ItemQuoteEncoderText());

    byte[] codedQuote = encoder.encode(quote);

    DatagramPacket message = new DatagramPacket(codedQuote, codedQuote.length,
                                          destAddr, destPort);
    sock. send(message);

    sock.close();
  }
}
