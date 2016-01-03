package HystrixExamples;

import org.slf4j.MDC;

import java.util.Map;
import java.util.concurrent.Callable;

/**
 * Created by gl on 10/15/15.
 */
public class MyHystrixContextCallable<K> implements Callable<K> {

    private final Callable<K> actual;
    private final Map parentThreadMDC;

    public MyHystrixContextCallable(Callable<K> actual) {
        this.actual = actual;
        this.parentThreadMDC = MDC.getCopyOfContextMap();
    }

    @Override
    public K call() throws Exception {
        // get original state
        Map existingMDC = MDC.getCopyOfContextMap();

        try {
            // set the state of this thread to that of its parent
            if (parentThreadMDC == null) MDC.clear();
            else MDC.setContextMap(parentThreadMDC);

            // execute actual Callable with the state of the parent
            return actual.call();
        } finally {
            // restore this thread back to its original state
            if (existingMDC == null) MDC.clear();
            else MDC.setContextMap(existingMDC);
        }
    }
}
