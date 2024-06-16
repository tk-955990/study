import java.io.*;      // Input/OutputStreamに必要
import java.net.*;     // Socketに必要

public class SendTCP {

  public static void main(String args[]) throws Exception {

    if (args.length != 2) // 引数の数が正しいかどうかを調べる
    throw new IllegalArgumentException("Parameter(s): <Destination> <Port>");

    InetAddress destAddr = InetAddress.getByName(args[0]);  // 宛先アドレス
    int destPort = Integer.parseInt(args[1]);               // 宛先ポート

    Socket sock = new Socket(destAddr, destPort);

    ItemQuote quote = new ItemQuote(1234567890987654L, "5mm Super Widgets",
                                    1000, 12999, true, false);

    // テキストエンコードされた見積もりを送信する
    ItemQuoteEncoder coder = new ItemQuoteEncoderText();
    byte[] codedQuote = coder.encode(quote);
    System.out.println("Sending Text-Encoded Quote (" +
                        codedQuote.length + " bytes): ");
    System.out.println(quote);
    sock.getOutputStream().write(codedQuote);

    // バイナリエンコードされた見積もりを受信する
    ItemQuoteDecoder decoder = new ItemQuoteDecoderBin();
    ItemQuote receivedQuote = decoder.decode(sock.getInputStream());
    System.out.println("Received Binary-Encode Quote:");
    System.out.println(receivedQuote);

    sock.close();
  }
}
