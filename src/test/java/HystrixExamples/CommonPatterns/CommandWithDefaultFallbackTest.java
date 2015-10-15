package HystrixExamples.CommonPatterns;

import com.netflix.hystrix.exception.HystrixRuntimeException;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

public class CommandWithDefaultFallbackTest {

    @Test
    public void testSuccess() {
        assertEquals("success", new CommandWithDefaultFallback(false).execute());
    }

    @Test
    public void testFailure() {
        try {
            assertEquals("here is Default String", new CommandWithDefaultFallback(true).execute());
        } catch (HystrixRuntimeException e) {
            fail("we should not get an exception as we fail silently with a fallback");
        }
    }
}