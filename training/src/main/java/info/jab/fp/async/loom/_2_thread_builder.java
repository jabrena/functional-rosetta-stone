package info.jab.fp.async.loom;

// $JAVA_HOME/bin/java -cp target/classes  fr.umlv.loom.example._2_thread_builder
public interface _2_thread_builder {
  static void main(String[] args) throws InterruptedException {
    // platform thread
    var pthread = Thread.ofPlatform()
        .name("platform-", 0)
        .start(() -> {
          System.out.println("platform " + Thread.currentThread());
        });
    pthread.join();

    // virtual thread
    var vthread = Thread.ofVirtual()
        .name("virtual-", 0)
        .start(() -> {
          System.out.println("virtual " + Thread.currentThread());
        });
    vthread.join();
  }
}
