package info.jab.fp.optional;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import static java.util.stream.Collectors.toSet;

import static org.assertj.core.api.BDDAssertions.then;
import org.junit.jupiter.api.Test;

import info.jab.fp.optional.OptionalExample.Car;
import info.jab.fp.optional.OptionalExample.Insurance;
import info.jab.fp.optional.OptionalExample.Person;

/**
 * https://github.com/java8/Java8InAction/tree/master/src/main/java/lambdasinaction/chap10
 */
public class OptionalExampleTest {

    @Test
    public void getCarInsuranceNameTest() {

        Insurance insurance = new Insurance("Mapfre");
        Car car = new Car(Optional.of(insurance));
        Person person = new Person(Optional.of(car));

        OptionalExample obj = new OptionalExample();

        then(obj.getCarInsuranceName(Optional.of(person)))
            .isEqualTo("Mapfre");

        then(obj.getCarInsuranceName(Optional.ofNullable(null)))
                .isEqualTo("Unknown");

        Person person2 = new Person(Optional.ofNullable(null));
        then(obj.getCarInsuranceName(Optional.of(person2)))
                .isEqualTo("Unknown");
    }

}
