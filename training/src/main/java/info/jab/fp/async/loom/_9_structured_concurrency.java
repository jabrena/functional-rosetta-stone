package info.jab.fp.async.loom;

import java.util.concurrent.StructuredTaskScope;

// $JAVA_HOME/bin/java --enable-preview -cp target/classes  fr.umlv.loom.example._9_structured_concurrency
public interface _9_structured_concurrency {
  static void main(String[] args) throws InterruptedException {
    try (var scope = new StructuredTaskScope<Integer>()) {
      var task1 = scope.fork(() -> {
        Thread.sleep(1_000);
        return 1;
      });
      var task2 = scope.fork(() -> {
        Thread.sleep(1_000);
        return 2;
      });
      scope.join();
      var result = task1.get() + task2.get();
      System.out.println(result);
    }
  }
}
