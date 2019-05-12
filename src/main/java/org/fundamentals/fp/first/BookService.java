package org.fundamentals.fp.first;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.vavr.control.Try;
import java.math.BigDecimal;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class BookService {

    private List<Book> loadBooks() {

        return Try.of(() -> {
            ObjectMapper objectMapper = new ObjectMapper();
            String readContent = new String(Files.readAllBytes(Paths.get(getClass().getClassLoader()
                    .getResource("data/books.json")
                    .toURI())));

            List<Book> deserializedData = objectMapper.readValue(readContent, new TypeReference<List<Book>>() {});
            return deserializedData;
        }).onFailure(System.out::println)
          .getOrElse(Collections.emptyList());

    }

    // https://docs.oracle.com/javase/7/docs/api/java/math/BigDecimal.html#compareTo(java.math.BigDecimal)
    private boolean reachLimit(BigDecimal price, BigDecimal limit) {

        if (price.compareTo(limit) == 1) {
            return true;
        } else {
            return false;
        }
    }

    public List<Book> search(BigDecimal priceLimit) {

        List<Book> books = loadBooks();
        List<Book> newList = new ArrayList<>();

        for(Book book : books) {
            if(reachLimit(book.getPrice(), priceLimit)) {
                newList.add(book);
            }
        }

        return newList;
    }

    public List<Book> searchNew(BigDecimal priceLimit) {

        return loadBooks().stream()
                .filter(x -> reachLimit(x.getPrice(), priceLimit))
                .collect(Collectors.toList());
    }

}
