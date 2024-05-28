import java.util.ArrayList;
import java.util.List;
import java.util.stream.Gatherer;
import java.util.stream.Gatherers;

Gatherer<String, ?, List<String>> groupBy2() {
  class State {
    ArrayList<String> list = new ArrayList<>();
  }
  return Gatherer.ofSequential(   // inherently sequential
      State::new,
      (state, element, downstream) -> {
        var list = state.list;
        list.add(element);
        if (state.list.size() == 2) {
          state.list = new ArrayList<>();
          return downstream.push(list);
        }
        return true;
      },
      (state, downstream) -> {
        if (!state.list.isEmpty()) {
          downstream.push(state.list);
        }
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
      //.gather(Gatherers.windowFixed(2)
      .gather(groupBy2())
      .toList();

  System.out.println(result);
}
