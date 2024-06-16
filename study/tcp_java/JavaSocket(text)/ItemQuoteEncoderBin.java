import java.io.*;       // ByteArrayOutputStream、DataOutputStreamに必要

public class ItemQuoteEncoderBin implements ItemQuoteEncoder, ItemQuoteBinConst {

  private String encoding;     // 文字エンコード方式

  public ItemQuoteEncoderBin() {
    encoding = DEFAULT_ENCODING;
  }

  public ItemQuoteEncoderBin(String encoding) {
    this.encoding = encoding;
  }

  public byte[] encode(ItemQuote item) throws Exception {

    ByteArrayOutputStream buf = new ByteArrayOutputStream();
    DataOutputStream out = new DataOutputStream(buf);
    out.writeLong(item.itemNumber);
    out.writeInt(item.quantity);
    out.writeInt(item.unitPrice);
    byte flags = 0;
    if (item.discounted)
      flags |= DISCOUNT_FLAG;
    if (item.inStock)
      flags |= IN_STOCK_FLAG;
    out.writeByte(flags);
    byte[] encodedDesc = item.itemDescription.getBytes(encoding);
    if (encodedDesc.length > MAX_DESC_LEN)
      throw new IOException("Item Description exceeds encoded length limit");
    out.writeByte(encodedDesc.length);
    out.write(encodedDesc);
    out.flush();
    return buf.toByteArray();
  }
}
