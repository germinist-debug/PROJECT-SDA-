public interface OwnerRepository {
    void insert(Owner owner, String password);
    void update(Owner owner, String password);
    void delete();
    Owner get();
    String getPassword();
    String getEmail();
    String getName();
    String getPhoneNumber();
    void print();
}