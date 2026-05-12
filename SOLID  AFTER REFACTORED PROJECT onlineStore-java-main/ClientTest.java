import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import java.util.List;
class ClientTest {

    @Test
    void testConstructor() {
        Client client = new Client("John Doe", "1234567890", "john@example.com");

        assertEquals("John Doe", client.name);
        assertEquals("1234567890", client.phoneNumber);
        assertEquals("john@example.com", client.email);
        assertTrue(client.getInfo().containsAll(List.of("John Doe", "1234567890", "john@example.com")));
    }

    @Test
    void testToString() {
        Client client = new Client("John Doe", "1234567890", "john@example.com");

        String expected = "Client name is John Doe\nClient phone number is 1234567890\nClient email is john@example.com\n";
        assertEquals(expected, client.toString());
    }

    @Test
    void testCompareTo() {
        Client client1 = new Client("John Doe", "1234567890", "john@example.com");
        Client client2 = new Client("Jane Doe", "0987654321", "jane@example.com");

        assertTrue(client1.compareTo(client2) > 0);
        assertTrue(client2.compareTo(client1) < 0);
        assertEquals(0, client1.compareTo(client1));
    }

    @Test
    void testCheckAttributes() {
        Client validClient = new Client("John Doe", "1234567890", "john@example.com");
        assertDoesNotThrow(validClient::checkAttributes);

        Client invalidNameClient = new Client("Jo", "1234567890", "john@example.com");
        assertThrows(InvalidPersonName.class, invalidNameClient::checkAttributes);

        Client invalidPhoneClient = new Client("John Doe", "abc", "john@example.com");
        assertThrows(InvalidPersonPhoneNumber.class, invalidPhoneClient::checkAttributes);

        Client invalidEmailClient = new Client("John Doe", "1234567890", "john");
        assertThrows(InvalidPersonEmail.class, invalidEmailClient::checkAttributes);
    }
}