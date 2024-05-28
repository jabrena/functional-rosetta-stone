import java.util.stream.Gatherer;

Gatherer<String, ?, Integer> map() {
  return Gatherer.of(Gatherer.Integrator.ofGreedy((_, element, downstream) -> {
    return downstream.push(element.length());
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
      //.map(String::length)
      .gather(map())
      .toList();

  System.out.println(result);
}
