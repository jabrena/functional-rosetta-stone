package org.fundamentals.fp.first;

import java.math.BigDecimal;
import java.util.List;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class BookServiceTest {

    @Test
    public void testBothSearchMethodsAreEqual() {

        final BigDecimal priceLimit = new BigDecimal("40.00");

        BookService bookService = new BookService();
        List<Book> list = bookService.search(priceLimit);
        List<Book> list2 = bookService.searchNew(priceLimit);

        assertThat(list.size()).isEqualTo(1L);
        assertThat(list2.size()).isEqualTo(1L);
        assertThat(list).isEqualTo(list2);
        assertThat(list.get(0)).isEqualTo(list2.get(0));
    }
}