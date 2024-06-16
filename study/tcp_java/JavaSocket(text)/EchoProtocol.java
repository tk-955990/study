import java.net.*; // Socketに必要
import java.io.*; // IOException、Input/OutputStreamに必要
import java.util.*; // ArrayListに必要

class EchoProtocol implements Runnable {
  static public final int BUFSIZE = 32; // I/Oバッファのバイトサイズ

  private Socket clntSock; // 接続ソケット
  private Logger logger; // ロギング機能

  public EchoProtocol(Socket clntSock, Logger logger) {
    this.clntSock = clntSock;
    this.logger = logger;
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

      int recvMsgSize;                      // 受信したメッセージのサイズ
      int totalBytesEchoed = 0;             // クライアントから受信したバイト数
      byte[] echoBuffer = new byte[BUFSIZE];// 受信バッファ
      // クライアントが接続をクローズする（-1で表わされる）まで受信する
      while ((recvMsgSize = in.read(echoBuffer)) != -1) {
        out.write(echoBuffer, 0, recvMsgSize);
        totalBytesEchoed += recvMsgSize;
      }

      entry.add("Client finished; echoed " + totalBytesEchoed + " bytes.");
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
