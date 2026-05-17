import java.sql.*;
import java.util.ArrayList;

public class OrderRepository {
    
    private Connection connection;
    private ClientRepository clientRepository;
    
    public OrderRepository(Connection connection, ClientRepository clientRepository) {
        this.connection = connection;
        this.clientRepository = clientRepository;
    }
    
    public void insertOrder(Client client, Product product, int quantity) {
        int totalValue = product.getPrice() * quantity;
        
        String sql = "INSERT INTO orders (client_email, product_name, product_size, product_color, quantity, total_value) VALUES (?, ?, ?, ?, ?, ?)";
        try(PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setString(1, client.getEmail());
            pstmt.setString(2, product.getName());
            pstmt.setString(3, product.getSize());
            pstmt.setString(4, product.getColor());
            pstmt.setInt(5, quantity);
            pstmt.setInt(6, totalValue);
            pstmt.executeUpdate();
            
            // Update client's to_pay
            int currentToPay = clientRepository.getClientToPay(client.getEmail());
            clientRepository.updateClientToPay(client.getEmail(), currentToPay + totalValue);
            
        } catch(SQLException e) {
            e.printStackTrace();
        }
    }
    
    public void removeOrder(int id) {
        int orderValue = getOrderValue(id);
        String clientEmail = getOrderClientEmail(id);
        
        if(clientEmail != null) {
            int currentToPay = clientRepository.getClientToPay(clientEmail);
            clientRepository.updateClientToPay(clientEmail, currentToPay - orderValue);
        }
        
        String sql = "DELETE FROM orders WHERE id = ?";
        try(PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setInt(1, id);
            pstmt.executeUpdate();
        } catch(SQLException e) {
            e.printStackTrace();
        }
    }
    
    public int getOrderValue(int id) {
        String sql = "SELECT total_value FROM orders WHERE id = ?";
        try(PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setInt(1, id);
            ResultSet rs = pstmt.executeQuery();
            if(rs.next()) {
                return rs.getInt("total_value");
            }
        } catch(SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }
    
    private String getOrderClientEmail(int id) {
        String sql = "SELECT client_email FROM orders WHERE id = ?";
        try(PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setInt(1, id);
            ResultSet rs = pstmt.executeQuery();
            if(rs.next()) {
                return rs.getString("client_email");
            }
        } catch(SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
    
    public ArrayList<Integer> getAllOrderIds() {
        ArrayList<Integer> ids = new ArrayList<>();
        String sql = "SELECT id FROM orders";
        try(Statement stmt = connection.createStatement()) {
            ResultSet rs = stmt.executeQuery(sql);
            while(rs.next()) {
                ids.add(rs.getInt("id"));
            }
        } catch(SQLException e) {
            e.printStackTrace();
        }
        return ids;
    }
    
    public void printAllOrders() {
        String sql = "SELECT * FROM orders";
        try(Statement stmt = connection.createStatement()) {
            ResultSet rs = stmt.executeQuery(sql);
            while(rs.next()) {
                System.out.println("Order ID: " + rs.getInt("id"));
                System.out.println("Client: " + rs.getString("client_email"));
                System.out.println("Product: " + rs.getString("product_name"));
                System.out.println("Quantity: " + rs.getInt("quantity"));
                System.out.println("Total: $" + rs.getInt("total_value"));
                System.out.println("---");
            }
        } catch(SQLException e) {
            e.printStackTrace();
        }
    }
    
    public void printClientOrders(String email) {
        String sql = "SELECT * FROM orders WHERE client_email = ?";
        try(PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setString(1, email);
            ResultSet rs = pstmt.executeQuery();
            while(rs.next()) {
                System.out.println("Order ID: " + rs.getInt("id"));
                System.out.println("Product: " + rs.getString("product_name"));
                System.out.println("Quantity: " + rs.getInt("quantity"));
                System.out.println("Total: $" + rs.getInt("total_value"));
                System.out.println("---");
            }
        } catch(SQLException e) {
            e.printStackTrace();
        }
    }
}