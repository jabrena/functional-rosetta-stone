package info.jab.fp.sealed;

import static org.assertj.core.api.Assertions.assertThat;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import info.jab.fp.sealed.AgeProblemExample.Age;
import info.jab.fp.sealed.AgeProblemExample.AgeProblem;
import info.jab.fp.sealed.AgeProblemExample.InvalidAge;
import info.jab.fp.sealed.AgeProblemExample.NotLegalAdult;
import info.jab.utils.TestLoggerExtension;

@ExtendWith(TestLoggerExtension.class)
public class AgeProblemExampleTest {

    @Test
    public void should_notValidate() {

        AgeProblemExample example = new AgeProblemExample();

        var result = example.validAdult(-1);

        assertThat(result).isInstanceOf(Either.class);
        assertThat(result.isLeft()).isTrue();

        var result2 = example.validAdult(17);
        assertThat(result2).isInstanceOf(Either.class);
        assertThat(result2.isLeft()).isTrue();

        var obj = result2.fold(left -> left, right -> right);
        if (obj instanceof AgeProblem) {
            AgeProblem ageProblem = (AgeProblem) obj;
            if (ageProblem instanceof InvalidAge) {
                InvalidAge invalidAge = (InvalidAge) ageProblem;
                System.out.println("Handled InvalidAge: " + invalidAge.getMessage());
            } else if (ageProblem instanceof NotLegalAdult) {
                NotLegalAdult notLegalAdult = (NotLegalAdult) ageProblem;
                System.out.println("Handled NotLegalAdult: " + notLegalAdult.getMessage());
            }
        }
    }

    @Test
    public void should_validate() {

        AgeProblemExample example = new AgeProblemExample();

        var result = example.validAdult(18);

        assertThat(result).isInstanceOf(Either.class);
        assertThat(result.isRight()).isTrue();

        var obj = result.fold(left -> left, right -> right);
        if (obj instanceof Age) {
            Age age = (Age) obj;
            System.out.println("Handled Age: " + age);
        }
    }

}