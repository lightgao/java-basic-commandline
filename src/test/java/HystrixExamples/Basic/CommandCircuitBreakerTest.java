package HystrixExamples.Basic;

import com.netflix.config.ConfigurationManager;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class CommandCircuitBreakerTest {

    private boolean originalForceOpenConfig;

    @Before
    public void setUp() throws Exception {
        originalForceOpenConfig = ConfigurationManager.getConfigInstance().getBoolean("hystrix.command.default.circuitBreaker.forceOpen", false);
    }

    @After
    public void tearDown() throws Exception {
        ConfigurationManager.getConfigInstance().setProperty("hystrix.command.default.circuitBreaker.forceOpen", originalForceOpenConfig);
    }


    @Test
    public void test_circuit_breaker_close() {
        //We use Archaius to set the circuit as closed.
        ConfigurationManager.getConfigInstance().setProperty("hystrix.command.default.circuitBreaker.forceOpen", false);

        String successMessage = new CommandHelloWorld("World 1").execute();

        assertEquals(successMessage, "Hello World 1!");
    }

    @Test
    public void test_circuit_breaker_open() {
        //We use Archaius to open the circuit
        ConfigurationManager.getConfigInstance().setProperty("hystrix.command.default.circuitBreaker.forceOpen", true);

        String failMessage = new CommandHelloWorld("World 2").execute();

        assertEquals(failMessage, "Fallback: Hello World 2!");

    }

}