public class DatabaseAuthService implements AuthService {
    
    private final OwnerRepository ownerRepo;
    private final ClientRepository clientRepo;
    
    public DatabaseAuthService(OwnerRepository ownerRepo, ClientRepository clientRepo) {
        this.ownerRepo = ownerRepo;
        this.clientRepo = clientRepo;
    }
    
    @Override
    public String checkRole(String email, String password) {
        // Admin login
        if (email.equals("admin") && password.equals("admin")) {
            return "admin";
        }
        
        // Check Owner
        Owner owner = ownerRepo.get();
        if (owner != null && owner.getEmail().equals(email)) {
            String storedPassword = ownerRepo.getPassword();
            if (storedPassword != null && storedPassword.equals(password)) {
                return "owner";
            }
            return "wrongPassword";
        }
        
        // Check Client
        if (clientRepo.emailExists(email)) {
            String storedPassword = clientRepo.getPassword(email);
            if (storedPassword != null && storedPassword.equals(password)) {
                return "client";
            }
            return "wrongPassword";
        }
        
        return "notFound";
    }
    
    @Override
    public boolean isStoreInitialized() {
        return ownerRepo.get() != null;
    }
}