public interface OrderRepository {
    void insert(Client client, Product product, int quantity);
    void delete(int orderId);
    int getValue(int orderId);
    int getId(Client client, Product product, int quantity);
    ArrayList<Integer> getAllIds();
    void printAll();
    void printByClient(String email);
}