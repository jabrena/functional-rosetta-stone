package info.jab.fp.vavr;

import io.vavr.Function1;
import java.util.HashMap;
import java.util.Map;

import io.vavr.control.Either;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.BDDAssertions.then;

public class EitherDemo {

	//Some initial ideas

	public static Object[] computeWithoutEitherUsingArray(int marks) {
		Object[] results = new Object[2];
		if (marks < 85) {
			results[0] = "Marks not acceptable";
		} else {
			results[1] = marks;
		}
		return results;
	}

	public static Map<String, Object> computeWithoutEitherUsingMap(int marks) {
		Map<String, Object> results = new HashMap<>();
		if (marks < 85) {
			results.put("FAILURE", "Marks not acceptable");
		} else {
			results.put("SUCCESS", marks);
		}
		return results;
	}

	@Test
	public void given_someExamResults_when_validate_then_expectedResults() {

		Function1<Integer, Either<String, Integer>> examValidation = (result) -> {
			if (result < 50) {
				return Either.left("Result not acceptable");
			} else {
				return Either.right(result);
			}
		};

		then(examValidation.apply(49).isLeft()).isTrue();
		then(examValidation.apply(50).isLeft()).isFalse();
		then(examValidation.apply(50).isRight()).isTrue();
		then(examValidation.apply(60).isRight()).isTrue();
	}
}