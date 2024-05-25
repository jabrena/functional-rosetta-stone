package info.jab.fp.rxjava;

import io.reactivex.Observable;
import io.reactivex.observers.TestObserver;
import java.util.List;
import java.util.function.Function;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.BDDAssertions.then;

public class ObservableMonadLawsTest {

    /**
     * Monad law 1, Left Identity
     *
     * From LYAHFGG [1] above:
     *   The first monad law states that if we take a value, put it in a default context
     *   with return and then feed it to a function by using >>=, it’s the same as just
     *   taking the value and applying the function to it
     */
    @Test
    public void law1LeftIdentityTest() {

        Integer value = 42;
        Function<Integer, Observable<Integer>> addOne = x -> Observable.just(x + 1);

        TestObserver<Integer> observer = new TestObserver<>();

        Observable.just(value).flatMap(x -> addOne.apply(x)).subscribe(observer);

        observer.assertComplete();
        observer.assertNoErrors();
        observer.assertValueCount(1);

        then(observer.values()).isEqualTo(List.of(addOne.apply(value).blockingFirst()));
    }

    /**
     * Monad law 2, Right Identity
     *
     * From LYAHFGG [1] above:
     *   The second law states that if we have a monadic value and we use >>= to feed
     *   it to return, the result is our original monadic value.
     */
    @Test
    public void law2RightIdentityTest() {

        Integer value = 42;

        TestObserver<Integer> observer = new TestObserver<>();

        Observable.just(value).flatMap(x -> Observable.just(x)).subscribe(observer);

        observer.assertComplete();
        observer.assertNoErrors();
        observer.assertValueCount(1);

        then(observer.values()).isEqualTo(List.of(Observable.just(value).blockingFirst()));
    }

    /**
     * Monad law 3, Associativity
     *
     * From LYAHFGG [1] above:
     *   The final monad law says that when we have a chain of monadic function
     *   applications with >>=, it shouldn’t matter how they’re nested.
     */
    @Test
    public void law3AssociativityTest() {

        Integer value = 42;

        Function<Integer, Observable<Integer>> addOne = x -> Observable.just(x + 1);
        Function<Integer, Observable<Integer>> addTwo = i -> Observable.just(i + 2);
        Function<Integer, Observable<Integer>> addThree = i -> addOne.apply(i).flatMap(x -> addTwo.apply(x));

        TestObserver<Integer> observer = new TestObserver<>();

        Observable.just(value)
                .flatMap(i -> addOne.apply(i))
                .flatMap(i -> addTwo.apply(i))
                .subscribe(observer);

        observer.assertComplete();
        observer.assertNoErrors();
        observer.assertValueCount(1);

        then(observer.values()).isEqualTo(List.of(Observable.just(value)
                .flatMap(x -> addThree.apply(x))
                .blockingFirst()));
    }

}