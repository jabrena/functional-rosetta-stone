package org.fundamentals.fp.playground.java8;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import static java.util.stream.Collectors.toSet;

import static org.assertj.core.api.BDDAssertions.then;
import org.junit.jupiter.api.Test;

/**
 * https://github.com/java8/Java8InAction/tree/master/src/main/java/lambdasinaction/chap10
 */
public class Java8InActionOptionalTest {

    public record Insurance (String name) {}

    public record Car (Optional<Insurance> insurance){}

    public record Person (Optional<Car> car){}

    public class OptionalMain {

        public String getCarInsuranceName(Optional<Person> person) {
            return person.flatMap(Person::car)
                    .flatMap(Car::insurance)
                    .map(Insurance::name)
                    .orElse("Unknown");
        }

        public Set<String> getCarInsuranceNames(List<Person> persons) {
            return persons.stream()
                    .map(Person::car)
                    .map(optCar -> optCar.flatMap(Car::insurance))
                    .map(optInsurance -> optInsurance.map(Insurance::name))
                    .flatMap(Optional::stream)
                    .collect(toSet());
        }
    }

    @Test
    public void getCarInsuranceNameTest() {

        Insurance insurance = new Insurance("Mapfre");
        Car car = new Car(Optional.of(insurance));
        Person person = new Person(Optional.of(car));

        OptionalMain obj = new OptionalMain();

        then(obj.getCarInsuranceName(Optional.of(person)))
            .isEqualTo("Mapfre");

        then(obj.getCarInsuranceName(Optional.ofNullable(null)))
                .isEqualTo("Unknown");

        Person person2 = new Person(Optional.ofNullable(null));
        then(obj.getCarInsuranceName(Optional.of(person2)))
                .isEqualTo("Unknown");
    }

}
