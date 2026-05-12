import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class DatabaseHandlerTest {

    @Test
    void testDatabaseHandlerConstructor() {
        assertDoesNotThrow(() -> new DatabaseHandler("jdbc:sqlite:E:/IdeaProjects/onlineStore-java/sqlite"));
    }
}