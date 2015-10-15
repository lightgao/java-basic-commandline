package HystrixExamples.CommonPatterns;

import com.netflix.hystrix.HystrixCommand;
import com.netflix.hystrix.HystrixCommandGroupKey;

/**
 * Created by gl on 10/15/15.
 */
public class CommandWithDefaultFallback extends HystrixCommand<String> {

    private final boolean throwException;

    public CommandWithDefaultFallback(boolean throwException) {
        super(HystrixCommandGroupKey.Factory.asKey("ExampleGroup"));
        this.throwException = throwException;
    }

    @Override
    protected String run() {
        if (throwException) {
            throw new RuntimeException("failure from CommandThatFailsFast");
        } else {
            return "success";
        }
    }

    @Override
    protected String getFallback() {
        return "here is Default String";
    }
}