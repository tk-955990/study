import java.io.*;         // ByteArrayOutputStream、OutputStreamWriterに必要

public class ItemQuoteEncoderText implements ItemQuoteEncoder, ItemQuoteTextConst {

  private String encoding;      // 文字エンコード方式

  public ItemQuoteEncoderText() {
  encoding = DEFAULT_ENCODING;
  }

  public ItemQuoteEncoderText(String encoding) {
    this.encoding = encoding;
  }

  public byte[] encode(ItemQuote item) throws Exception {
    ByteArrayOutputStream buf = new ByteArrayOutputStream();
    OutputStreamWriter out = new OutputStreamWriter(buf, encoding);
    out.write(item.itemNumber + " ");
    if (item.itemDescription.indexOf('\n') != -1)
      throw new IOException("Invalid description (contains newline)");
    out.write(item.itemDescription + "\n" + item.quantity + " " +
              item.unitPrice + " ");
    if (item.discounted)
      out.write('d');           // 値引きがある場合にのみ「d」を挿入する
    if (item.inStock)
      out.write('s');           // 在庫がある場合にのみ「s」を挿入する
    out.write('\n');
    out.flush();
    if (buf.size() > MAX_WIRE_LENGTH)
      throw new IOException("Encoded length too long");
    return buf.toByteArray();
  }
}
