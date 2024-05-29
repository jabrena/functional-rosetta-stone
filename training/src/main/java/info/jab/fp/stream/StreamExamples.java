package info.jab.fp.stream;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Gatherers;

public class StreamExamples {
    
    public void example1() {
          var text = """
                item1
                item2
                --
                item11
                item12
                """;

        var result = text.lines()
        .gather(Gatherers.windowFixed(2))
        .toList();
    }

    public void example2() {
        var text = """
            item1
            item2
            --
            item11
            item12
            """;
      
        var result = text.lines()
            .gather(Gatherers.fold(() -> 0, (value, s) -> value + 1))
            .findFirst().orElseThrow();
      
        System.out.println(result);
    }

}
