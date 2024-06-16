import java.net.*; // Socketに必要
import java.io.*;  // IOException、Input/OutputStreamに必要
import java.util.*; // ArrayListに必要

public class TimelimitEchoProtocolFactory implements ProtocolFactory {

  public Runnable createProtocol(Socket clntSock, Logger logger) {
    return new TimelimitEchoProtocol(clntSock, logger);
  }
}

class TimelimitEchoProtocol implements Runnable {
  private static final int BUFSIZE = 32; // 受信バッファのバイトサイズ
  private static final String TIMELIMIT = "10000"; // デフォルトのタイムリミット（ミリ秒）
  private static final String TIMELIMITPROP = "Timelimit"; // スレッドプロパティ

  private int timelimit;
  private Socket clntSock;
  private Logger logger;

  public TimelimitEchoProtocol(Socket clntSock, Logger logger) {
    this.clntSock = clntSock;
    this.logger = logger;
    // Systemプロパティからタイムリミットを取得するか、デフォルト値を使用する
    timelimit = Integer.parseInt(System.getProperty(TIMELIMITPROP, TIMELIMIT));
  }

  public void run() {
    ArrayList entry = new ArrayList();
    entry.add("Client address and port = " +
      clntSock.getInetAddress().getHostAddress() + ":" +
      clntSock.getPort());
    entry.add("Thread = " + Thread.currentThread().getName());

    try{
      // ソケットから入力および出力I/Oストリームを取得する
      InputStream in = clntSock.getInputStream();
      OutputStream out = clntSock.getOutputStream();

      int recvMsgSize; // 受信したメッセージのサイズ
      int totalBytesEchoed = 0; // クライアントから受信したバイト数
      byte[] echoBuffer = new byte[BUFSIZE]; // 受信バッファ
      long endTime = System.currentTimeMillis() + timelimit;
      int timeBoundMillis = timelimit;

      clntSock.setSoTimeout(timeBoundMillis);

      // クライアントが接続をクローズする（-1で表わされる）まで受信する
      while ((timeBoundMillis > 0) && // ゼロ値を捕捉する
            ((recvMsgSize = in.read(echoBuffer)) != -1)) {
        out.write(echoBuffer, 0, recvMsgSize);
        totalBytesEchoed += recvMsgSize;
        timeBoundMillis = (int) (endTime - System.currentTimeMillis());
        clntSock. setSoTimeout(timeBoundMillis);
      }

      entry.add("Client finished; echoed " + totalBytesEchoed + " bytes.");
    } catch (InterruptedIOException dummy) {
      entry.add("Read timed out");
    } catch (IOException e) {
      entry.add("Exception = " + e.getMessage());
    }

    try { // ソケットをクローズする
      clntSock.close();
    } catch (IOException e) {
      entry.add("Exception = " + e.getMessage());
    }

    logger.writeEntry(entry);
  }
}
