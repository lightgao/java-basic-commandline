package HystrixExamples.CommonPatterns;

import com.netflix.hystrix.exception.HystrixRuntimeException;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

public class CommandThatFailsSilentlyTest {

    @Test
    public void testSuccess() {
        assertEquals("success", new CommandThatFailsSilently(false).execute());
    }

    @Test
    public void testFailure() {
        try {
            assertEquals(null, new CommandThatFailsSilently(true).execute());
        } catch (HystrixRuntimeException e) {
            fail("we should not get an exception as we fail silently with a fallback");
        }
    }
}