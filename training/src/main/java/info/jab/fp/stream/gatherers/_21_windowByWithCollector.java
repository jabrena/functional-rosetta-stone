import java.util.List;
import java.util.Objects;
import java.util.function.BiFunction;
import java.util.function.Predicate;
import java.util.stream.Collector;
import java.util.stream.Collectors;
import java.util.stream.Gatherer;

record Section(String name, List<String> items) {}

<T, R, R2> Gatherer<T, ?, R2> windowBy(Predicate<? super T> predicate,
                                       Collector<? super T, ?, ? extends R> collector,
                                       BiFunction<? super T, ? super R, ? extends R2> mapper) {
  return _windowBy(predicate, collector, mapper);
}

<T, A, R, R2> Gatherer<T, ?, R2> _windowBy(Predicate<? super T> predicate,
                                           Collector<? super T, A, ? extends R> collector,
                                           BiFunction<? super T, ? super R, ? extends R2> mapper) {
  Objects.requireNonNull(predicate);
  Objects.requireNonNull(collector);
  Objects.requireNonNull(mapper);
  var supplier = collector.supplier();
  var accumulator = collector.accumulator();
  var finisher = collector.finisher();
  class State {
    A container;
    T element;
  }
  return Gatherer.ofSequential(
      State::new,
      Gatherer.Integrator.ofGreedy((state, element, downstream) -> {
        if (predicate.test(element)) {
          if (state.container != null) {
            if (!downstream.push(mapper.apply(state.element, finisher.apply(state.container)))) {
              return false;
            }
          }
          state.container = supplier.get();
          state.element = element;
          return true;
        }
        if (state.container == null) {
          throw new IllegalStateException("");
        }
        accumulator.accept(state.container, element);
        return true;
      }),
      (state, downstream) -> {
        if (state.container != null) {
          downstream.push(mapper.apply(state.element, finisher.apply(state.container)));
        }
      }
  );
}

void main() {
  var text = """
      header
      item1
      item2
      header2
      item3
      """;

  var sections = text.lines()
      .gather(windowBy(s -> s.startsWith("header"), Collectors.toList(), Section::new))
      .toList();
  System.out.println(sections);
}
