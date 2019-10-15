package org.fundamentals.fp.playground.java8;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.junit.jupiter.api.Test;

import static java.util.stream.Collectors.toSet;
import static org.assertj.core.api.BDDAssertions.then;

/**
 * https://github.com/java8/Java8InAction/tree/master/src/main/java/lambdasinaction/chap10
 */
public class Java8InActionOptionalTest {

    @Data
    @AllArgsConstructor
    public class Insurance {

        private String name;

        public String getName() {
            return name;
        }
    }

    @Data
    @AllArgsConstructor
    public class Car {

        private Optional<Insurance> insurance;

        public Optional<Insurance> getInsurance() {
            return insurance;
        }
    }

    @Data
    @AllArgsConstructor
    public class Person {

        private Optional<Car> car;

        public Optional<Car> getCar() {
            return car;
        }
    }

    public class OptionalMain {

        public String getCarInsuranceName(Optional<Person> person) {
            return person.flatMap(Person::getCar)
                    .flatMap(Car::getInsurance)
                    .map(Insurance::getName)
                    .orElse("Unknown");
        }

        public Set<String> getCarInsuranceNames(List<Person> persons) {
            return persons.stream()
                    .map(Person::getCar)
                    .map(optCar -> optCar.flatMap(Car::getInsurance))
                    .map(optInsurance -> optInsurance.map(Insurance::getName))
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
