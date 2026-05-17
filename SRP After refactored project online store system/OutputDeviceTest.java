import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class OutputDeviceTest {

    @Test
    void testConstructor() {
        OutputDevice outputDevice = new OutputDevice();

        assertNotNull(outputDevice.consoleOutputStream);
    }
}