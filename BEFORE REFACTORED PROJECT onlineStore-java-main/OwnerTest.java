import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class OwnerTest {

    @Test
    void testConstructor() {
        Owner owner = new Owner("John Doe", "1234567890", "john@example.com");

        assertEquals("John Doe", owner.name);
        assertEquals("1234567890", owner.phoneNumber);
        assertEquals("john@example.com", owner.email);
    }
}