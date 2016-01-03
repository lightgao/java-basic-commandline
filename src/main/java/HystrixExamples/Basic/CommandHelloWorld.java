package HystrixExamples.Basic;

import com.netflix.hystrix.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;

/**
 * Created by gl on 10/14/15.
 */

public class CommandHelloWorld extends HystrixCommand<String> {

    static final Logger LOG = LoggerFactory.getLogger(CommandHelloWorld.class);

    private final String name;

    public CommandHelloWorld(String name) {
//        super(HystrixCommandGroupKey.Factory.asKey("HelloWorldGroupKey"));
//        super(Setter
//                .withGroupKey(HystrixCommandGroupKey.Factory.asKey("HelloWorldGroupKey"))
//                .andCommandKey(HystrixCommandKey.Factory.asKey("HelloWorldCommandKey")));
//        super(Setter
//                .withGroupKey(HystrixCommandGroupKey.Factory.asKey("HelloWorldGroupKey"))
//                .andCommandKey(HystrixCommandKey.Factory.asKey("HelloWorldCommandKey"))
//                .andThreadPoolKey(HystrixThreadPoolKey.Factory.asKey("HelloWorldPoolKey")));
        super(Setter
                .withGroupKey(HystrixCommandGroupKey.Factory.asKey("HelloWorldGroupKey"))
                .andCommandKey(HystrixCommandKey.Factory.asKey("HelloWorldCommandKey"))
                .andThreadPoolKey(HystrixThreadPoolKey.Factory.asKey("HelloWorldPoolKey"))
                .andCommandPropertiesDefaults(
                        HystrixCommandProperties.Setter().withExecutionTimeoutInMilliseconds(600)));

        this.name = name;
    }

    @Override
    protected String run() {
        LOG.info("CorrelationID in thread " + Thread.currentThread().getName() + " is : " + MDC.get("CorrelationID"));
        // a real example would do work like a network call here
        return "Hello " + name + "!";
    }

    @Override
    protected String getFallback() {
        return "Fallback: Hello " + name + "!";
    }
}


