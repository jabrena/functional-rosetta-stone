package info.jab.fp.sealed;

public class AgeProblemExample {

    record Age(Integer age) {}

    /*
    // validation returns either problems or the constructed value
    public Either<AgeProblem, Age> validAdult(Integer age) {
        if (age < 0) {
            //return AgeProblem.InvalidAge.left();
        } else if (age < 18) {
            //return AgeProblem.NotLegalAdult.left();
        } else {
            return Age(age).right();
        }
    }
    */

}
