package HystrixExamples.Basic;

import org.junit.Test;
import rx.Observable;
import rx.Observer;
import rx.functions.Action1;

import static org.junit.Assert.assertEquals;

/**
 * Created by gl on 10/15/15.
 */
public class ObservableCommandHelloWorldTest {

    @Test
    public void test_blocking() {
        Observable<String> fWorld = new CommandHelloWorld("World").observe();
        Observable<String> fBob = new CommandHelloWorld("Bob").observe();

        // blocking
        assertEquals("Hello World!", fWorld.toBlocking().single());
        assertEquals("Hello Bob!", fBob.toBlocking().single());
    }

    @Test
    public void test_non_blocking() {

        // ------ ObservableCommandHelloWorld
        Observable<String> fWorld = new ObservableCommandHelloWorld("World").observe();

        // ------ CommandHelloWorld
        Observable<String> fBob = new CommandHelloWorld("Bob").observe();


        // non-blocking
        // - this is a verbose anonymous inner-class approach and doesn't do assertions
        fWorld.subscribe(new Observer<String>() {

            @Override
            public void onCompleted() {
                // nothing needed here
            }

            @Override
            public void onError(Throwable e) {
                e.printStackTrace();
            }

            @Override
            public void onNext(String v) {
                System.out.println("onNext: " + v);
            }

        });

        // non-blocking
        // - also verbose anonymous inner-class
        // - ignore errors and onCompleted signal
        fBob.subscribe(new Action1<String>() {

            @Override
            public void call(String v) {
                System.out.println("onNext: " + v);
            }

        });
    }
}
