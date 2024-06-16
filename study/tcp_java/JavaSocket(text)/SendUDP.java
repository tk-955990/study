import java.net.*;  // DatagramSocket、DatagramPacket、InetAddressに必要
import java.io.*;   // IOExceptionに必要

public class SendUDP {

  public static void main(String args[]) throws Exception {


    if (args.length != 2 && args.length != 3)               // 引数の数が正しいかどうかを調べる
    throw new IllegalArgumentException("Parameter(s): <Destination>" +
               " <Port> [<encoding]");

    InetAddress destAddr = InetAddress.getByName(args[0]);  // 宛先アドレス
    int destPort = Integer.parseInt(args[1]);               // 宛先ポート

    ItemQuote quote = new ItemQuote(1234567890987654L, "5mm Super Widgets",
                                    1000, 12999, true, false);

    DatagramSocket sock = new DatagramSocket();             // 送信用のUDPソケット

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
