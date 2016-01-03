package HystrixExamples;

import com.netflix.hystrix.strategy.concurrency.HystrixConcurrencyStrategy;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.Callable;

/**
 * Created by gl on 10/15/15.
 */
public class MyHystrixConcurrencyStrategy extends HystrixConcurrencyStrategy {

    static final Logger LOG = LoggerFactory.getLogger(MyHystrixConcurrencyStrategy.class);

    private static MyHystrixConcurrencyStrategy INSTANCE = new MyHystrixConcurrencyStrategy();

    public static HystrixConcurrencyStrategy getInstance() {
        return INSTANCE;
    }

    private MyHystrixConcurrencyStrategy() {
    }

    @Override
    public Callable wrapCallable(Callable callable) {
        LOG.info("Enter wrapCallable()");

        return new MyHystrixContextCallable(callable);
    }
}
