import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class InputDeviceTest {

    @Test
    void testConstructor() {
        InputDevice inputDevice = new InputDevice();

        assertNotNull(inputDevice.consoleInputStream);
        assertNotNull(inputDevice.scanner);
    }
}