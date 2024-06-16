import java.net.*; // MulticastSocket、DatagramPacket、InetAddressに必要
import java.io.*; // IQExceptionに必要

public class RecvUDPMulticast implements ItemQuoteTextConst {

  public static void main(String[] args) throws Exception {

    if (args.length != 2) // 引数の数が正しいか調べる
      throw new IllegalArgumentException("Parameter(s): <Multicast Addr> <Port>");

    InetAddress address = InetAddress.getByName(args[0]); // マルチキャストアドレス
    if (!address.isMulticastAddress()) // マルチキャストアドレスか調べる
      throw new IllegalArgumentException("Not a multicast address");

    int port = Integer.parseInt(args[1]); // マルチキャストポート

    MulticastSocket sock = new MulticastSocket(port); // マルチキャスト受信ソケット
    sock.joinGroup(address); // マルチキャストグループの参加する

    // データグラムを生成して受信する
    DatagramPacket packet = new DatagramPacket(
      new byte[MAX_WIRE_LENGTH], MAX_WIRE_LENGTH);
    sock.receive(packet);

    ItemQuoteDecoder decoder = new ItemQuoteDecoderText(); // テキストのデコード
    ItemQuote quote = decoder.decode(packet);
    System.out.println(quote);

    sock.close();
  }
}
