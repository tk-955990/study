import java.net.*; // Socketに必要
import java.io.*; // IOException、Input/OutputStreamに必要
import java.util.*; // ArrayListに必要
import java.util.zip.*; // GZIPOutputStreamに必要

public class CompressProtocolFactory implements ProtocolFactory {

  public static final int BUFSIZE = 1024; // 受信バッファのサイズ

  public Runnable createProtocol(final Socket clntSock, final Logger logger) {
    return new Runnable() {
      public void run() {
        CompressProtocolFactory.handleClient(clntSock, logger);
      }
    };
  }

  public static void handleClient(Socket clntSock, Logger logger) {
    ArrayList entry = new ArrayList();
    entry.add("Client address and port = " +
      clntSock.getInetAddress().getHostAddress() + ":" +
      clntSock.getPort());
    entry.add("Thread = " + Thread.currentThread().getName());

    try{
      // ソケットから入力ストリームと出力ストリームを取得する
      InputStream in = clntSock.getInputStream();
      GZIPOutputStream out = new GZIPOutputStream(clntSock.getOutputStream());

      byte[] buffer = new byte[BUFSIZE]; // 読み取り/書き込みバッファを割り当てる
      int bytesRead; // 読み取ったバイト数
      // クライアントが接続をクローズする（-1で表わされる）まで受信する
      while ((bytesRead = in.read(buffer)) != -1)
        out.write(buffer, 0, bytesRead);

      out.finish(); // GZIPOutputStreamからバイトをフラッシュする
      } catch (IOException e) {
        logger.writeEntry("Exception = + e.getMessage()");
      }

      try { // ソケットをクローズする
        clntSock.close();
      } catch (IOException e) {
        entry.add("Exception = " + e.getMessage());
      }

      logger.writeEntry(entry);
  }
}
