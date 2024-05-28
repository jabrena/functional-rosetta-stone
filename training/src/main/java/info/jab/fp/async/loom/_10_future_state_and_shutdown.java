package info.jab.fp.async.loom;

import java.util.concurrent.StructuredTaskScope;

// $JAVA_HOME/bin/java --enable-preview -cp target/classes  fr.umlv.loom.example._10_future_state_and_shutdown
public interface _10_future_state_and_shutdown {
  static void main(String[] args) throws InterruptedException {
    try (var scope = new StructuredTaskScope<Integer>()) {
      var task = scope.fork(() -> {
        Thread.sleep(1_000);
        return 42;
      });
      System.out.println(task.state());  // UNAVAILABLE
      scope.join();
      System.out.println(task.state());  // SUCCESS
    }
  }
}
