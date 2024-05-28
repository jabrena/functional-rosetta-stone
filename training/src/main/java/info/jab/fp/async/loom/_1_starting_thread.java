package info.jab.fp.async.loom;

// $JAVA_HOME/bin/java -cp target/classes  fr.umlv.loom.example._1_starting_thread
public interface _1_starting_thread {
  static void main(String[] args) throws InterruptedException {
    // platform threads
    var pthread = new Thread(() -> {
      System.out.println("platform " + Thread.currentThread());
    });
    pthread.start();
    pthread.join();

    // virtual threads
    var vthread = Thread.startVirtualThread(() -> {
      System.out.println("virtual " + Thread.currentThread());
    });
    vthread.join();
  }
}
