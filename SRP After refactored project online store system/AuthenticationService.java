import java.sql.*;

public class AuthenticationService {
    
    private Connection connection;
    private OwnerRepository ownerRepository;
    private ClientRepository clientRepository;
    
    public AuthenticationService(Connection connection, OwnerRepository ownerRepository, ClientRepository clientRepository) {
        this.connection = connection;
        this.ownerRepository = ownerRepository;
        this.clientRepository = clientRepository;
    }
    
    public String authenticate(String email, String password) {
        // Check admin
        if(email.equals("admin") && password.equals("admin")) {
            return "admin";
        }
        
        // Check owner
        if(ownerRepository.ownerExists()) {
            String ownerPassword = ownerRepository.getOwnerPassword();
            Owner owner = ownerRepository.getOwner();
            if(owner != null && owner.getEmail().equals(email) && ownerPassword != null && ownerPassword.equals(password)) {
                return "owner";