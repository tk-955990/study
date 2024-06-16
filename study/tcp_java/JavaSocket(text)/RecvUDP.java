import java.net.*;  // DatagramSocket、DatagramPacketに必要
import java.io.*;   // IOExceptionに必要

public class RecvUDP implements ItemQuoteTextConst {

  public static void main(String[] args) throws Exception {

  if (args.length != 1 && args.length != 2)        // 引数の数が正しいかどうかを調べる
  throw new IllegalArgumentException("Parameter(s): <Port> [<encoding>]");

  int port = Integer.parseInt(args[0]);            // 受信ポート

  DatagramSocket sock = new DatagramSocket(port);  // 受信用のUDPソケット
  ItemQuoteDecoder decoder = (args.length == 2 ?   // エンコード方式の種類
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
