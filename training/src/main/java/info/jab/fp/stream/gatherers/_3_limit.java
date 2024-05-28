import java.util.stream.Gatherer;

Gatherer<String, ?, String> limit() {
  class Counter {
    int counter;
    Counter(int counter) { this.counter = counter; }
  }
  return Gatherer.ofSequential(
      () -> new Counter(0),
      (counter, element, downstream) -> {
        if (counter.counter++ == 3) {
          return false;
        }
        return downstream.push(element);
      }
  );
}

void main() {
  var text = """
      item1
      item2
      --
      item11
      item12
      """;

  var result = text.lines()
      //.limit(3)
      .gather(limit())
      .toList();

  System.out.println(result);
}
