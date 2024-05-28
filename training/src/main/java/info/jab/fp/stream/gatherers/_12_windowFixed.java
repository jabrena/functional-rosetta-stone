import java.util.ArrayList;
import java.util.List;
import java.util.stream.Gatherer;

Gatherer<String, ?, List<String>> windowFixed() {
  return Gatherer.ofSequential(
      () -> new Object() { List<String> list = new ArrayList<>(); },
      Gatherer.Integrator.ofGreedy((state, element, downstream) -> {
        if (state.list.size() == 2) {
          if (!downstream.push(state.list)) {
            return false;
          }
          state.list = new ArrayList<>();
        }
        state.list.add(element);
        return true;
      }),
      (state, downstream) -> {
        if (!state.list.isEmpty()) {
          downstream.push(state.list);
        }
        state.list = null;  // maybe ?
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
      //.gather(Gatherers.windowFixed(2))
      .gather(windowFixed())
      .toList();

  System.out.println(result);
}
