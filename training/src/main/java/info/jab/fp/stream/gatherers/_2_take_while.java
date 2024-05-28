import java.util.stream.Gatherer;

Gatherer<String, ?, String> takeWhile() {
  return Gatherer.of((_, element, downstream) -> {
    if (element.startsWith("item")) {
      return downstream.push(element);
    }
    return false;
  });
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
      //.takeWhile(s -> s.startsWith("item"))
      .gather(takeWhile())
      .toList();

  System.out.println(result);
}
