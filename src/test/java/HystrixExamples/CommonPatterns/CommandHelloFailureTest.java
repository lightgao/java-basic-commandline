package HystrixExamples.CommonPatterns;

import HystrixExamples.Basic.CommandHelloFailure;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class CommandHelloFailureTest {

    @Before
    public void setUp() throws Exception {

    }

    @Test
    public void testSynchronous() {
        assertEquals("Hello Failure World!", new CommandHelloFailure("World").execute());
        assertEquals("Hello Failure Bob!", new CommandHelloFailure("Bob").execute());
    }
}