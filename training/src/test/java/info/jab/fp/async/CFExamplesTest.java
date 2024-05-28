package info.jab.fp.async;

import java.time.Duration;
import java.util.concurrent.Callable;

import static org.assertj.core.api.BDDAssertions.then;
import static org.awaitility.Awaitility.await;
import static org.hamcrest.Matchers.equalTo;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import info.jab.utils.TestLoggerExtension;

@ExtendWith(TestLoggerExtension.class)
public class CFExamplesTest {

    private static CFExamples cfExamples;

    @BeforeAll
    public static void setUp() {
        cfExamples = new CFExamples();
    }

    @Test
    public void should_call_singleAsyncTask() {

        //Given
        var expectedComputationTime = (cfExamples.getDelay() * 1) + 1;
        var expectedResult = 1+1;

        //When
        Callable demo = () -> cfExamples.callingSingleAsyncTask().join();

        //Then
        await()
                .atMost(Duration.ofSeconds(expectedComputationTime))
                .until(demo, equalTo(expectedResult));
    }

    @Test
    public void should_return_value() {

        //Given
        var expectedResult = 2;

        //When
        var result = cfExamples.callingSingleTask();

        //Then
        then(result).isEqualTo(expectedResult);
    }

    @Test
    public void should_call_twoAsyncTasks() {

        //Given
        var expectedComputationTime = (cfExamples.getDelay() * 2) + 1;
        var expectedResult = 2 + 1;

        //When
        Callable demo = () -> cfExamples.callingTwoAsyncTasks().join();

        //Then
        await()
                .atMost(Duration.ofSeconds(expectedComputationTime))
                .until(demo, equalTo(expectedResult));
    }

    @Test
    public void should_call_threeAsyncTasks() {

        //Given
        var expectedComputationTime = (cfExamples.getDelay() * 3) + 1;
        var expectedResult = 2 + 1 + 1;

        //When
        Callable demo = () -> cfExamples.callingThreeAsyncTasks().join();

        //Then
        await()
                .atMost(Duration.ofSeconds(expectedComputationTime))
                .until(demo, equalTo(expectedResult));
    }

    @Test
    public void should_call_fourAsyncTasks() {

        //Given
        var expectedComputationTime = (cfExamples.getDelay() * 4) + 1;
        var expectedResult = 2 + 2 + 2 + 2;

        //When
        Callable demo = () -> cfExamples.callingFourAsyncTasks();

        //Then
        await()
                .atMost(Duration.ofSeconds(expectedComputationTime))
                .until(demo, equalTo(expectedResult));
    }

    @Test
    public void should_call_fourAsyncTasksAndOneFails() {

        //Given
        var expectedComputationTime = (cfExamples.getDelay() * 4) + 1;
        var expectedResult = 2 + 2 + 2;

        //When
        Callable demo = () -> cfExamples.callingFourAsyncTasksAndOneFails();

        //Then
        await()
                .atMost(Duration.ofSeconds(expectedComputationTime))
                .until(demo, equalTo(expectedResult));
    }

    @Disabled
    @Test
    public void should_call_fourAsyncTasksAndThreeFails() {

        //Given
        var expectedComputationTime = (cfExamples.getDelay() * 4) + 1;
        var expectedResult = 2;

        //When
        Callable demo = () -> cfExamples.callingFourAsyncTasksAndThreeFails();

        //Then
        await()
                .atMost(Duration.ofSeconds(expectedComputationTime))
                .until(demo, equalTo(expectedResult));
    }

    @Test
    public void should_call_fourAsyncTasksWithDefaultValues() {

        //Given
        var expectedComputationTime = (cfExamples.getDelay() * 4) + 1;
        var expectedResult = 104;

        //When
        Callable demo = () -> cfExamples.callingFourAsyncTasksWithDefaultValues();

        //Then
        await()
                .atMost(Duration.ofSeconds(expectedComputationTime))
                .until(demo, equalTo(expectedResult));
    }

    @Test
    public void should_call_fourAsyncTasksWithOptionals() {

        //Given
        var expectedComputationTime = (cfExamples.getDelay() * 4) + 1;
        var expectedResult = 2;

        //When
        Callable demo = () -> cfExamples.callingFourAsyncTasksWithOptionals();

        //Then
        await()
                .atMost(Duration.ofSeconds(expectedComputationTime))
                .until(demo, equalTo(expectedResult));
    }

}