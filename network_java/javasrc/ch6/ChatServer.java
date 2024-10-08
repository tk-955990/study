// チャットサーバChatServer.java
// このプログラムは,チャットのサーバプログラムです
// 使い方java ChatServer [ポート番号]
// ポート番号を省略すると,ポート番号6000 番を使います
// 起動の例java ChatServer
// 終了にはコントロールC を入力してください

// このサーバへの接続にはTelnet.javaなどを使ってください
// 接続を止めたいときには,行頭でquitと入力してください

// ライブラリの利用
import java.io.*;
import java.net.*;
import java.util.*;

// ChatServerクラス
public class ChatServer {
	static final int DEFAULT_PORT = 6000;//ポート番号省略時は6000 番を使います
	static ServerSocket serverSocket;
	static Vector connections;

	// sendAllメソッド
	// 各クライアントにメッセージを送ります
	public static void sendAll(String s){
		if (connections != null){// コネクションがあれば実行します
			for (Enumeration e = connections.elements();
					e.hasMoreElements() ;) {
				try {
					PrintWriter pw = new PrintWriter((
					  (Socket) e.nextElement()).getOutputStream());
					pw.println(s);
					pw.flush();
				}catch (IOException ex){}
			}
		}
		System.out.println(s);
	}

	// addConnectionメソッド
	// クライアントとの接続を追加します
	public static void addConnection(Socket s){
		if (connections == null){
			connections = new Vector();
		}
		connections.addElement(s);
	}

	// deleteConnectionメソッド
	// あるクライアントとのコネクションを削除します
	public static void deleteConnection(Socket s){
		if (connections != null){
			connections.removeElement(s);
		}
	}

	// mainメソッド
	// サーバソケットを作り,クライアントからの接続を待ち受けます
	public static void main(String[] arg){
		int port = DEFAULT_PORT ;
		if (arg.length > 0) port = Integer.parseInt(arg[0]) ;
		try {
			serverSocket = new ServerSocket(port);
		}catch (IOException e){
			System.err.println(e);
			System.exit(1);
		}
		while (true) {
			try {
				Socket cs = serverSocket.accept();
				addConnection(cs);
				Thread ct = new Thread(new clientProc(cs));
				ct.start();
			}catch (IOException e){
				System.err.println(e);
			}
		}
	}
}

// clientProcクラス
// クライアント処理用スレッドのひな形です
class clientProc implements Runnable {
	Socket s;
	BufferedReader in;
	PrintWriter out;
	String name = null;
	ChatServer server = null ;

	//コンストラクタ
	public clientProc(Socket s) throws IOException {
		this.s = s;
		in = new BufferedReader(new InputStreamReader(
		  s.getInputStream()));
		out = new PrintWriter(s.getOutputStream());
	}

	// スレッドの本体
	// 各クライアントとの接続処理を行います
	public void run(){
		try {
			while (name == null){
				out.print("お名前は？: ");
				out.flush();
				name = in.readLine();
			}
			String line = in.readLine();
			while (!"quit".equals(line)){
				ChatServer.sendAll(name + "> " +line);
				line = in.readLine();
			}
			ChatServer.deleteConnection(s);
			s.close();
		}catch (IOException e){
			try {
				s.close();
			}catch (IOException e2){}
		}
	}
}