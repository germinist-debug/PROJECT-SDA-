public interface AuthService {
    String checkRole(String email, String password);
    boolean isStoreInitialized();
}