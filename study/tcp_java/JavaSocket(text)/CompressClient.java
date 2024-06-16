import java.net.*; // Socketに必要
import java.io.*; // IOException、[File]Input/OutputStreamに必要

public class CompressClient {

  public static final int BUFSIZE = 256; // 読み取りバッファのサイズ

  public static void main(String[] args) throws IOException {

    if (args.length != 3) // 引数の数が正しいか調べる
      throw new IllegalArgumentException("Parameter(s): <Server> <Port> <File>");

    String server = args[0]; // サーバ名またはIPアドレス
    int port = Integer.parseInt(args[1]); // サーバポート
    String filename = args[2]; // データを読み取るファイル

    // 入力ファイルおよび出力ファイル（input.gzという形式の名前にする）をオープンする
    FileInputStream fileIn = new FileInputStream(filename);
    FileOutputStream fileOut = new FileOutputStream(filename + ".gz");

    // サーバの指定のポートに接続するソケットを生成する
    Socket sock = new Socket(server, port);

    // 未圧縮バイトストリームをサーバに送る
    sendBytes(sock, fileIn);

    // サーバから圧縮バイトストリームを受け取る
    InputStream sockIn = sock.getInputStream();
    int bytesRead; // 読み取ったバイト数
    byte[] buffer = new byte[BUFSIZE]; // バイトバッファ
    while ((bytesRead = sockIn.read(buffer)) != -1) {
      fileOut.write(buffer, 0, bytesRead);
      System.out.print("R"); // 読み取り進行インジケータ
    }
    System.out.println(); // 進行インジケータ行の終わり

    sock.close(); // ソケットとそのストリームをクローズする
    fileIn.close(); // ファイルストリームをクローズする
    fileOut.close();
  }

  private static void sendBytes(Socket sock, InputStream fileIn)
      throws IOException {
    OutputStream sockOut = sock.getOutputStream();
    int bytesRead; // 読み取ったバイト数
    byte[] buffer = new byte[BUFSIZE]; // バイトバッファ
    while ((bytesRead = fileIn.read(buffer)) != -1) {
      sockOut.write(buffer, 0, bytesRead);
      System.out.print("W"); // 書き込み進行インジケータ
    }
    sock.shutdownOutput(); // 送信終了
  }
}
