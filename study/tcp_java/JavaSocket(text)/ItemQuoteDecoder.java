import java.io.*;      // InputStream、IOExceptionに必要
import java.net.*;     // DatagramPacketを使用するため

public interface ItemQuoteDecoder {
  ItemQuote decode(InputStream source) throws IOException;
  ItemQuote decode(DatagramPacket packet) throws IOException;
}
