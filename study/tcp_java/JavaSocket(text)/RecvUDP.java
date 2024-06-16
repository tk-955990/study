import java.net.*;  // DatagramSocket��DatagramPacket��ɬ��
import java.io.*;   // IOException��ɬ��

public class RecvUDP implements ItemQuoteTextConst {

  public static void main(String[] args) throws Exception {

  if (args.length != 1 && args.length != 2)        // �����ο������������ɤ�����Ĵ�٤�
  throw new IllegalArgumentException("Parameter(s): <Port> [<encoding>]");

  int port = Integer.parseInt(args[0]);            // �����ݡ���

  DatagramSocket sock = new DatagramSocket(port);  // �����Ѥ�UDP�����å�
  ItemQuoteDecoder decoder = (args.length == 2 ?   // ���󥳡��������μ���
                              new ItemQuoteDecoderText(args [1]) :
                              new ItemQuoteDecoderText() );

  DatagramPacket packet = new DatagramPacket(
  new byte[MAX_WIRE_LENGTH], MAX_WIRE_LENGTH);
  sock.receive(packet);

  ItemQuote quote = decoder.decode(packet);
  System.out.println(quote);

  sock.close();
  }
}
