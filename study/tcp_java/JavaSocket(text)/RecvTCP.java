import java.io.*;     // Input/OutputStream��ɬ��
import java.net.*;    // Socket��ServerSocket��ɬ��

public class RecvTCP {

  public static void main(String args[]) throws Exception {

    if (args.length != 1)     // �����ο������������ɤ�����Ĵ�٤�
      throw new IllegalArgumentException("Parameter(s): <Port>");

  int port = Integer.parseInt(args[0]);    // �����ݡ���

  ServerSocket servSock = new ServerSocket(port);
  Socket clntSock = servSock.accept();

  // �ƥ����ȥ��󥳡��ɤ��줿���Ѥ�����������
  ItemQuoteDecoder decoder = new ItemQuoteDecoderText();
  ItemQuote quote = decoder.decode(clntSock.getInputStream());
  System.out.println("Received Text-Encoded Quote:");
  System.out.println(quote);

  // ñ����10����Ȥ��ɲä����Х��ʥꥨ�󥳡��ɤǸ��Ѥ����֤�
  ItemQuoteEncoder encoder = new ItemQuoteEncoderBin();
  quote.unitPrice += 10;                   // ñ����10����Ȥ��ɲä���
  System.out.println("Sending (binary)...");
  clntSock.getOutputStream().write(encoder.encode(quote));

  clntSock.close();
  servSock.close();
  }
}
