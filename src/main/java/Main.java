import HystrixExamples.Basic.CommandHelloWorld;
import HystrixExamples.MyHystrixConcurrencyStrategy;
import com.netflix.hystrix.strategy.HystrixPlugins;
import com.netflix.hystrix.strategy.concurrency.HystrixRequestContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;

public class Main {

    static final Logger LOG = LoggerFactory.getLogger(Main.class);

    public static void main(String[] args) {

//        SLF4JConsoleTest();

        HystrixCommandWorkWithSLF4JMDCTest();
    }

    private static void HystrixCommandWorkWithSLF4JMDCTest() {

        HystrixPlugins.getInstance().registerConcurrencyStrategy(MyHystrixConcurrencyStrategy.getInstance());
        HystrixRequestContext context = HystrixRequestContext.initializeContext();

//        MDC.put("CorrelationID", "aaa");
        for (int i = 0; i < 20; i++) {
            MDC.put("CorrelationID", "00" + i);
            LOG.info(new CommandHelloWorld("Bob" + i).execute());
            MDC.remove("CorrelationID");
        }
        MDC.put("CorrelationID", "zzz");
        LOG.info(new CommandHelloWorld("Bobzzz").execute());
        MDC.remove("CorrelationID");

        context.shutdown();

    }

    private static void SLF4JConsoleTest() {
        LOG.trace("Hello World!");
        LOG.debug("How are you today?");
        LOG.info("I am fine.");
        LOG.warn("I love programming.");
        LOG.error("I am programming.");
    }

}
