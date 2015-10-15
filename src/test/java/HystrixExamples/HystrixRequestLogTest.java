package HystrixExamples;

import HystrixExamples.Basic.CommandHelloWorld;
import HystrixExamples.Basic.CommandUsingRequestCache;
import com.netflix.hystrix.HystrixRequestLog;
import com.netflix.hystrix.strategy.concurrency.HystrixRequestContext;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertTrue;

public class HystrixRequestLogTest {

    @Before
    public void setUp() throws Exception {
    }

    @After
    public void tearDown() throws Exception {
    }

    @Test
    public void test() {
        HystrixRequestContext context = HystrixRequestContext.initializeContext();
        try {
            new CommandHelloWorld("World 1").execute();
            new CommandHelloWorld("World 2").execute();

            new CommandUsingRequestCache(2).execute();

            String executedCommandsAsString = HystrixRequestLog.getCurrentRequest().getExecutedCommandsAsString();

            //"HelloWorldCommandKey[SUCCESS][.*ms]x2, CommandUsingRequestCache[SUCCESS][.*ms]"
            assertTrue(executedCommandsAsString.matches("HelloWorldCommandKey\\[SUCCESS\\]\\[.*ms\\]x2, CommandUsingRequestCache\\[SUCCESS\\]\\[.*ms\\]"));
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            context.shutdown();
        }
    }

}