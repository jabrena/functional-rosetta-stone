import java.util.List;
import java.util.stream.Collector;
import java.util.stream.Collectors;
import java.util.stream.Gatherer;
import java.util.stream.Gatherers;

<E, A, T> Gatherer<E, A, T> asGatherer(Collector<? super E, A, ? extends T> collector) {
  var supplier = collector.supplier();
  var accumulator = collector.accumulator();
  var combiner = collector.combiner();
  var finisher = collector.finisher();
  return Gatherer.of(supplier,
      Gatherer.Integrator.ofGreedy((state, element, _) -> {
        accumulator.accept(state, element);
        return true;
      }),
      combiner,
      (state, downstream) -> downstream.push(finisher.apply(state)));
}

<E, A, T> Collector<E, A, T> asCollector(Gatherer<? super E, A, T> gatherer) {
  var initializer = gatherer.initializer();
  var integrator = gatherer.integrator();
  var combiner = gatherer.combiner();
  var finisher = gatherer.finisher();
  return Collector.of(
    initializer,
    (state, element) -> integrator.integrate(state, element, __ -> { throw new IllegalStateException(); }),
    combiner,
     state -> {
      var box = new Object() { T result; };
      finisher.accept(state, value -> { box.result = value; return true; });
      return box.result;
    });
}

void main() {
  var list = List.of(1, 2, 3, 4, 5);

  var result = list.stream()
      .gather(asGatherer(Collectors.toList()))
      .findFirst().orElseThrow();
  System.out.println(result);

  var result2 = list.stream()
      .collect(asCollector(Gatherers.fold(() -> 0, Integer::sum)));
  System.out.println(result2);
}
