import java.net.*; // MulticastSocket、DatagramPacket、InetAddressに必要
import java.io.*; // IOExceptionに必要

public class SendUDPMulticast {

  public static void main(String args[]) throws Exception {

    if ((args.length < 2) || (args.length > 3)) // 引数の数が正しいか調べる
      throw new IllegalArgumentException(
        "Parameter(s): <Multicast Addr> <Port> [<TTL>]");

    InetAddress destAddr = InetAddress.getByName(args[0]); // 宛先アドレス
    if (!destAddr.isMulticastAddress()) // マルチキャストアドレスか調べる
      throw new IllegalArgumentException("Not a multicast address");

    int destPort = Integer.parseInt(args[1]); // 宛先ポート

    int TTL; // データグラムの生存期間（Time To Live）
    if (args.length == 3)
      TTL = Integer.parseInt(args[2]);
    else
      TTL = 1; // デフォルトTTL

    ItemQuote quote = new ItemQuote(1234567890987654L, "5mm Super Widgets",
                                    1000, 12999, true, false);

    MulticastSocket sock = new MulticastSocket(); // マルチキャスト送信ソケット
    sock.setTimeToLive(TTL); // すべてのデータグラムにTTLを設定する

    ItemQuoteEncoder encoder = new ItemQuoteEncoderText(); // テキストのエンコード
    byte[] codedQuote = encoder.encode(quote);


    // データグラムを生成して送信する
    DatagramPacket message = new DatagramPacket(codedQuote, codedQuote.length,
                                              destAddr, destPort);
    sock.send(message);

    sock.close();
  }
}
