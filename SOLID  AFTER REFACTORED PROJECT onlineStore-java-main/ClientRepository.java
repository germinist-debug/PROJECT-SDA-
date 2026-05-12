public interface ClientRepository {
    void insert(Client client, String password);
    void update(Client client, String password);
    void delete(String email);
    void deleteAll();
    Client findByEmail(String email);
    boolean emailExists(String email);
    String getPassword(String email);
    String getName(String email);
    String getPhoneNumber(String email);
    int getToPay(String email);
    void updateToPay(String email, int amount);
    void printAll();
}