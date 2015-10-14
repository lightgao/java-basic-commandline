import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static org.junit.Assert.assertEquals;

public class CalculatorTest {

    static final Logger LOG = LoggerFactory.getLogger(Main.class);

    private Calculator calculator;

    @Before
    public void setUp() throws Exception {
        calculator = new Calculator();
    }

    @After
    public void tearDown() throws Exception {

    }

    @Test
    public void testAdd() throws Exception {
        LOG.info(">>>> Test Start");

        assert (calculator.add(1, 2) == 3);
        assertEquals(calculator.add(1,2), 3);

        LOG.info(">>>> Test End");
    }
}