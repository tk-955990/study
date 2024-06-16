import java.io.*;      // ByteArrayInputStreamに必要
import java.net.*;     // DatagramPacketに必要

public class ItemQuoteDecoderBin implements ItemQuoteDecoder, ItemQuoteBinConst {

  private String encoding;             // 文字エンコード方式

  public ItemQuoteDecoderBin() {
    encoding = DEFAULT_ENCODING;
  }

  public ItemQuoteDecoderBin(String encoding) {
    this.encoding = encoding;
  }

  public ItemQuote decode(InputStream wire) throws IOException {
    boolean discounted, inStock;
    DataInputStream src = new DataInputStream(wire);
    long itemNumber = src.readLong();
    int quantity = src.readInt();
    int unitPrice = src.readInt();
    byte flags = src.readByte();
    int stringLength = src.read();     // 符号なしのバイトをintとして返す
    if (stringLength == -1)
      throw new EOFException();
    byte[] stringBuf = new byte[stringLength];
    src.readFully(stringBuf);
    String itemDesc = new String(stringBuf,encoding);
    return new ItemQuote(itemNumber,itemDesc, quantity, unitPrice,
      ((flags & DISCOUNT_FLAG) == DISCOUNT_FLAG),
      ((flags & IN_STOCK_FLAG) == IN_STOCK_FLAG));
  }

  public ItemQuote decode(DatagramPacket p) throws IOException {
    ByteArrayInputStream payload =
      new ByteArrayInputStream(p.getData(), p.getOffset(), p.getLength());
    return decode(payload);
  }
}
