import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import java.util.ArrayList;
import java.util.Arrays;

class OnlineStoreTest {

    @Test
    void testConstructor() {
        Owner owner = new Owner("John Doe", "1234567890", "john@example.com");
        ArrayList<Product> productList = new ArrayList<>();
        productList.add(new TopWear("SHIRT", "M", "BLUE", 10, 20));

        OnlineStore store1 = new OnlineStore(owner);
        assertEquals(owner, store1.owner);
        assertEquals(0, store1.getProductList().length);

        OnlineStore store2 = new OnlineStore(owner, productList);
        assertEquals(owner, store2.owner);
        assertEquals(productList, Arrays.stream(store2.getProductList()).toList());
    }
    @Test
    void testAddProduct() {
        Owner owner = new Owner("John Doe", "1234567890", "john@example.com");
        OnlineStore store = new OnlineStore(owner);
        Product product = new TopWear("SHIRT", "M", "BLUE", 10, 20);

        store.addProduct(product);
        assertEquals(1, store.getProductList().length);
        assertEquals(product, store.getProductList()[0]);
    }

    @Test
    void testRemoveProductIdx() {
        Owner owner = new Owner("John Doe", "1234567890", "john@example.com");
        Product product = new TopWear("SHIRT", "M", "BLUE", 10, 20);
        ArrayList<Product> productList = new ArrayList<>();
        productList.add(product);
        OnlineStore store = new OnlineStore(owner, productList);

        store.removeProductIdx(0);
        assertEquals(0, store.getProductList().length);
    }

    @Test
    void testRemoveProductType() {
        Owner owner = new Owner("John Doe", "1234567890", "john@example.com");
        Product product1 = new TopWear("SHIRT", "M", "BLUE", 10, 20);
        Product product2 = new BottomWear("JEANS", "L", "BLACK", 5, 30);
        ArrayList<Product> productList = new ArrayList<>();
        productList.add(product1);
        productList.add(product2);
        OnlineStore store = new OnlineStore(owner, productList);

        store.removeProductType("SHIRT");
        assertEquals(1, store.getProductList().length);
        assertEquals(product2, store.getProductList()[0]);
    }

    @Test
    void testSetProductList() {
        Owner owner = new Owner("John Doe", "1234567890", "john@example.com");
        Product product1 = new TopWear("SHIRT", "M", "BLUE", 10, 20);
        Product product2 = new BottomWear("JEANS", "L", "BLACK", 5, 30);
        ArrayList<Product> productList = new ArrayList<>();
        productList.add(product1);
        productList.add(product2);
        OnlineStore store = new OnlineStore(owner);

        store.setProductList(productList);
        assertEquals(2, store.getProductList().length);
        assertEquals(product1, store.getProductList()[1]);
        assertEquals(product2, store.getProductList()[0]); // Note that the order of the products is reversed due to appending products when using setProductList
    }
}