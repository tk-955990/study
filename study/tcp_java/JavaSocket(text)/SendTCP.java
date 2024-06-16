import java.io.*;      // Input/OutputStream��ɬ��
import java.net.*;     // Socket��ɬ��

public class SendTCP {

  public static void main(String args[]) throws Exception {

    if (args.length != 2) // �����ο������������ɤ�����Ĵ�٤�
    throw new IllegalArgumentException("Parameter(s): <Destination> <Port>");

    InetAddress destAddr = InetAddress.getByName(args[0]);  // ���襢�ɥ쥹
    int destPort = Integer.parseInt(args[1]);               // ����ݡ���

    Socket sock = new Socket(destAddr, destPort);

    ItemQuote quote = new ItemQuote(1234567890987654L, "5mm Super Widgets",
                                    1000, 12999, true, false);

    // �ƥ����ȥ��󥳡��ɤ��줿���Ѥ�����������
    ItemQuoteEncoder coder = new ItemQuoteEncoderText();
    byte[] codedQuote = coder.encode(quote);
    System.out.println("Sending Text-Encoded Quote (" +
                        codedQuote.length + " bytes): ");
    System.out.println(quote);
    sock.getOutputStream().write(codedQuote);

    // �Х��ʥꥨ�󥳡��ɤ��줿���Ѥ����������
    ItemQuoteDecoder decoder = new ItemQuoteDecoderBin();
    ItemQuote receivedQuote = decoder.decode(sock.getInputStream());
    System.out.println("Received Binary-Encode Quote:");
    System.out.println(receivedQuote);

    sock.close();
  }
}
