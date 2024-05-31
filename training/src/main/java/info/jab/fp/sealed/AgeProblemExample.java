package info.jab.fp.sealed;

public class AgeProblemExample {

    record Age(Integer age) {}

    record InvalidAge() implements AgeProblem {
    
        @Override
        public String getMessage() {
            return "Hello";
        }
    }

    record NotLegalAdult() implements AgeProblem {
    
        @Override
        public String getMessage() {
            return "World";
        }
    }

    // these are the potential problems
    sealed interface AgeProblem permits InvalidAge, NotLegalAdult {
        String getMessage();
    }

    // validation returns either problems or the constructed value
    public Either<AgeProblem, Age> validatAge(Integer age) {
        return switch (age) {
            case Integer a when a < 0 -> Either.left(new InvalidAge());
            case Integer a when a < 18 -> Either.left(new NotLegalAdult());
            default -> Either.right(new Age(age));
        };
    }

}
