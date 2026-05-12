// This is an ABSTRACTION for database operations
public interface ProductRepository {
    void insert(Product product);
    void updateQuantity(Product product);
    void updatePrice(Product product);
    void delete(Product product);
    void deleteAll();
    ArrayList<Product> getAll();
    Product findByNameSizeColor(String name, String size, String color);
}