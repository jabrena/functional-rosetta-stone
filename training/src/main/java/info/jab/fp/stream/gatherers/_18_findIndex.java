import java.lang.foreign.Arena;
import java.util.Objects;
import java.util.function.Predicate;
import java.util.stream.Gatherer;

import static java.util.stream.Gatherer.Integrator.ofGreedy;

<T> Gatherer<T, ?, Integer> findIndex(Predicate<? super T> predicate) {
  Objects.requireNonNull(predicate);
  return Gatherer.ofSequential(
      () -> new Object() { int index; },
      ofGreedy((state, element, downstream) -> {
        var index = state.index++;
        if (predicate.test(element)) {
          return downstream.push(index);
        }
        return true;
      }));
}

void main() {
  var list = java.util.List.of("foo", "bar", "baz");
  var findIndex = list.stream().gather(findIndex(s -> s.contains("o"))).findFirst().orElse(-1);
  System.out.println(findIndex);
  var findLastIndex = list.reversed().stream().gather(findIndex(s -> s.contains("o"))).findFirst().orElse(-1);
  System.out.println(findLastIndex);
}
