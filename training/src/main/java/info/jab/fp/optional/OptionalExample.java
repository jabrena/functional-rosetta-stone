package info.jab.fp.optional;

import java.util.Optional;
import java.util.Set;

import static java.util.stream.Collectors.toSet;

import java.util.List;

public class OptionalExample {

    public record Insurance (String name) {}
    public record Car (Optional<Insurance> insurance){}
    public record Person (Optional<Car> car){}

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
