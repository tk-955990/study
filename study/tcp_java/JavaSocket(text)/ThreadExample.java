public class ThreadExample implements Runnable {

  private String greeting; // コンソールにメッセージを出力する

  public ThreadExample(String greeting) {
    this.greeting = greeting;
  }

  public void run() {
    for (;;) {
      System.out.println(Thread.currentThread().getName() + ": "+ greeting);
      try{
        Thread.sleep((long) (Math.random() * 100)); // 0〜00ミリ秒スリープする

      } catch (InterruptedException e) {} // 発生しない
    }
  }

    public static void main(String[] args) {
      new Thread(new ThreadExample("Hello")).start();
      new Thread(new ThreadExample("Aloha")).start();
      new Thread(new ThreadExample("Ciao")).start();
    }
}
