package info.jab.fp.lambda;

import java.util.stream.LongStream;

class LongStreamConcat {

    public static void main(String... args) {

        LongStream stream1 = LongStream.of(2L, 4L, 6L);
        LongStream stream2 = LongStream.of(1L, 3L, 5L); 

        LongStream.concat(stream1, stream2)
                .forEach(System.out::println);
    }

} 