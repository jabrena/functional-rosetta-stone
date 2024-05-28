import java.util.stream.Gatherer;

Gatherer<String, ?, Integer> fold() {
  return Gatherer.ofSequential(
      () -> new Object() { int counter; },
      Gatherer.Integrator.ofGreedy((state, _, _) -> {
        state.counter++;
        return true;
      }),
      (state, downstream) -> {
        downstream.push(state.counter);
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
      //.gather(Gatherers.fold(() -> 0, (value, s) -> value + 1))
      .gather(fold())
      .findFirst().orElseThrow();

  System.out.println(result);
}
