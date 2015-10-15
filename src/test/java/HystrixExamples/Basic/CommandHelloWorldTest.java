package HystrixExamples.Basic;

import com.netflix.config.ConfigurationManager;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.concurrent.Future;

import static org.junit.Assert.assertEquals;

public class CommandHelloWorldTest {

    @Before
    public void setUp() throws Exception {
        ConfigurationManager.getConfigInstance().setProperty("hystrix.command.default.circuitBreaker.forceOpen", false);
    }

    // --- Synchronous Execution
    @Test
    public void testSynchronous() {
        Assert.assertEquals("Hello World!", new CommandHelloWorld("World").execute());
        assertEquals("Hello Bob!", new CommandHelloWorld("Bob").execute());
    }


    // --- Asynchronous Execution

    @Test
    public void testAsynchronous1() throws Exception {
        assertEquals("Hello World!", new CommandHelloWorld("World").queue().get());
        assertEquals("Hello Bob!", new CommandHelloWorld("Bob").queue().get());
    }

    @Test
    public void testAsynchronous2() throws Exception {

        Future<String> fWorld = new CommandHelloWorld("World").queue();
        Future<String> fBob = new CommandHelloWorld("Bob").queue();

        assertEquals("Hello World!", fWorld.get());
        assertEquals("Hello Bob!", fBob.get());
    }

//    // --- Reactive Execution
//
//    @Test
//    public void testObservable() throws Exception {
//
//        Observable<String> fWorld = new HystrixExamples.Basic.CommandHelloWorld("World").observe();
//        Observable<String> fBob = new HystrixExamples.Basic.CommandHelloWorld("Bob").observe();
//
//        // blocking
//        assertEquals("Hello World!", fWorld.toBlocking().single());
//        assertEquals("Hello Bob!", fBob.toBlocking().single());
//
//        // non-blocking
//        // - this is a verbose anonymous inner-class approach and doesn't do assertions
//        fWorld.subscribe(new Observer<String>() {
//
//            @Override
//            public void onCompleted() {
//                // nothing needed here
//            }
//
//            @Override
//            public void onError(Throwable e) {
//                e.printStackTrace();
//            }
//
//            @Override
//            public void onNext(String v) {
//                System.out.println("onNext: " + v);
//            }
//
//        });
//
//        // non-blocking
//        // - also verbose anonymous inner-class
//        // - ignore errors and onCompleted signal
//        fBob.subscribe(new Action1<String>() {
//
//            @Override
//            public void call(String v) {
//                System.out.println("onNext: " + v);
//            }
//
//        });
//    }
}