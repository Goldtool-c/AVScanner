package gladyshev.aVScanner;

import gladyshev.util.Logger;
import org.junit.Test;

import static junit.framework.TestCase.assertTrue;

public class Log {
    private static Logger LOGGER = Logger.getInstance();
    @Test
    public void log() {
        LOGGER.log("info");
        assertTrue(true);
    }
}
