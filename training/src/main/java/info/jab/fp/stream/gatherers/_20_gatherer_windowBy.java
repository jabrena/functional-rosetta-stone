import java.util.ArrayList;
import java.util.List;
import java.util.function.BiFunction;
import java.util.function.Predicate;
import java.util.stream.Gatherer;

record Section(String name, List<String> items) {}

Gatherer<String, ?, Section> windowBy(Predicate<? super String> predicate,
                                      BiFunction<? super String, ? super List<String>, ? extends Section> mapper) {
  class State {
    List<String> container;
    String name;
  }
  return Gatherer.ofSequential(
      State::new,
      Gatherer.Integrator.ofGreedy((state, element, downstream) -> {
        if (predicate.test(element)) {
          if (state.container != null) {
            if (!downstream.push(mapper.apply(state.name, state.container))) {
              return false;
            }
          }
          state.container = new ArrayList<>();
          state.name = element;
          return true;
        }
        if (state.container == null) {
          throw new IllegalStateException();
        }
        state.container.add(element);
        return true;
      }),
      (state, downstream) -> {
        if (state.container != null) {
          downstream.push(mapper.apply(state.name, state.container));
        }
      });
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
      .gather(windowBy(s -> s.startsWith("header"), Section::new))
      .toList();
  System.out.println(sections);
}
