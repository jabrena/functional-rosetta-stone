import java.util.stream.Gatherer;

Gatherer<String, ?, String> filter() {
  return Gatherer.of(Gatherer.Integrator.ofGreedy((_, element, downstream) -> {  // ofGreedy: optimization
    if (element.endsWith("1")) {
      return downstream.push(element);
    }
    return true;
  }));
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
      //.filter(s -> s.endsWith("1"))
      .gather(filter())
      .toList();

  System.out.println(result);
}
