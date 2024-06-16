import java.io.*;     // Input/OutputStreamに必要
import java.net.*;    // Socket、ServerSocketに必要

public class RecvTCP {

  public static void main(String args[]) throws Exception {

    if (args.length != 1)     // 引数の数が正しいかどうかを調べる
      throw new IllegalArgumentException("Parameter(s): <Port>");

  int port = Integer.parseInt(args[0]);    // 受信ポート

  ServerSocket servSock = new ServerSocket(port);
  Socket clntSock = servSock.accept();

  // テキストエンコードされた見積もりを送信する
  ItemQuoteDecoder decoder = new ItemQuoteDecoderText();
  ItemQuote quote = decoder.decode(clntSock.getInputStream());
  System.out.println("Received Text-Encoded Quote:");
  System.out.println(quote);

  // 単価に10セントを追加し、バイナリエンコードで見積もりを返す
  ItemQuoteEncoder encoder = new ItemQuoteEncoderBin();
  quote.unitPrice += 10;                   // 単価に10セントを追加する
  System.out.println("Sending (binary)...");
  clntSock.getOutputStream().write(encoder.encode(quote));

  clntSock.close();
  servSock.close();
  }
}
