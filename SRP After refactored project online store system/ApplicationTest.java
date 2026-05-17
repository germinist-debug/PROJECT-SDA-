import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;

public class ApplicationTest {

    private InputDevice inputDevice;
    private OutputDevice outputDevice;

    @BeforeEach
    public void setUp() {
        inputDevice = new InputDevice();
        outputDevice = new OutputDevice();
    }

    @Test
    public void testConstructor() {
        Application application = new Application(inputDevice, outputDevice);

        // Use assertions to check if the constructor initialized the fields correctly
        assertEquals(inputDevice, application.getInputDevice());
        assertEquals(outputDevice, application.getOutputDevice());
    }
}
