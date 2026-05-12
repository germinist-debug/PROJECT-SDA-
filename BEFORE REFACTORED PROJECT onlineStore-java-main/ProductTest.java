import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class ProductTest {

    @Test
    void testTopWearConstructor() {
        Product product = new TopWear("SHIRT", "M", "BLUE", 10, 20);

        assertEquals("SHIRT", product.getName());
        assertEquals("M", product.getSize());
        assertEquals("BLUE", product.getColor());
        assertEquals(10, product.getQuantity());
        assertEquals(20, product.getPrice());
    }

    @Test
    void testBottomWearConstructor() {
        Product product = new BottomWear("JEANS", "M", "BLUE", 10, 20);

        assertEquals("JEANS", product.getName());
        assertEquals("M", product.getSize());
        assertEquals("BLUE", product.getColor());
        assertEquals(10, product.getQuantity());
        assertEquals(20, product.getPrice());
    }
}