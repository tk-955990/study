import java.io.*;      // InputStream��IOException��ɬ��
import java.net.*;     // DatagramPacket����Ѥ��뤿��

public interface ItemQuoteDecoder {
  ItemQuote decode(InputStream source) throws IOException;
  ItemQuote decode(DatagramPacket packet) throws IOException;
}
