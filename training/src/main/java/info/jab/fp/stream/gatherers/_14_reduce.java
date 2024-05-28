import java.util.stream.Gatherer;

Gatherer<String, ?, Integer> reduce() {
  class Counter {
    int counter;
    Counter(int counter) {
      this.counter = counter;
    }
  }
  return Gatherer.of(
      () -> new Counter(0),
      Gatherer.Integrator.ofGreedy((state, _, _) -> {
        state.counter++;
        return true;
      }),
      (s1, s2) -> new Counter(s1.counter + s2.counter),
      (state, downstream) -> downstream.push(state.counter)
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
      .parallel()
      .gather(reduce())
      .findFirst().orElseThrow();

  System.out.println(result);
}
