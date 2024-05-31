package info.jab.fp.sealed;

public class AgeProblemExample {

    record Age(Integer age) {}
    
    // these are the potential problems
    sealed interface AgeProblem permits InvalidAge, NotLegalAdult {
        String getMessage();
    }

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

    //Either<AgeProblem, Age> problem1 = Either.left(new InvalidAge());
    //Either<AgeProblem, Age> problem2 = Either.left(new NotLegalAdult());
    //Either<AgeProblem, Age> success = Either.right(new Age(20));

    public Either<AgeProblem, Age> validAdultAge(Integer age) {
        return switch (age) {
            case Integer a when a < 0 -> Either.left(new InvalidAge());
            case Integer a when a < 18 -> Either.left(new NotLegalAdult());
            default -> Either.right(new Age(age));
        };
    }

}
