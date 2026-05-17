import java.sql.*;
import java.util.ArrayList;

public class ClientRepository {
    
    private Connection connection;
    
    public ClientRepository(Connection connection) {
        this.connection = connection;
    }
    
    public void insertClient(Client client, String password) {
        String sql = "INSERT OR REPLACE INTO clients (name, email, phoneNumber, password, to_pay) VALUES (?, ?, ?, ?, ?)";
        try(PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setString(1, client.getName());
            pstmt.setString(2, client.getEmail());
            pstmt.setString(3, client.getPhoneNumber());
            pstmt.setString(4, password);
            pstmt.setInt(5, 0);
            pstmt.executeUpdate();
        } catch(SQLException e) {
            e.printStackTrace();
        }
    }
    
    public void updateClient(Client client, String password) {
        String sql = "UPDATE clients SET name = ?, phoneNumber = ?, password = ? WHERE email = ?";
        try(PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setString(1, client.getName());
            pstmt.setString(2, client.getPhoneNumber());
            pstmt.setString(3, password);
            pstmt.setString(4, client.getEmail());
            pstmt.executeUpdate();
        } catch(SQLException e) {
            e.printStackTrace();
        }
    }
    
    public void deleteClient(String email) {
        String sql = "DELETE FROM clients WHERE email = ?";
        try(PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setString(1, email);
            pstmt.executeUpdate();
        } catch(SQLException e) {
            e.printStackTrace();
        }
    }
    
    public void deleteAllClients() {
        String sql = "DELETE FROM clients";
        try(Statement stmt = connection.createStatement()) {
            stmt.executeUpdate(sql);
        } catch(SQLException e) {
            e.printStackTrace();
        }
    }
    
    public boolean clientExists(String email) {
        String sql = "SELECT 1 FROM clients WHERE email = ?";
        try(PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setString(1, email);
            ResultSet rs = pstmt.executeQuery();
            return rs.next();
        } catch(SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
    
    public Client getClient(String email) {
        String sql = "SELECT * FROM clients WHERE email = ?";
        try(PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setString(1, email);
            ResultSet rs = pstmt.executeQuery();
            if(rs.next()) {
                return new Client(rs.getString("name"), rs.getString("phoneNumber"), email);
            }
        } catch(SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
    
    public int getClientToPay(String email) {
        String sql = "SELECT to_pay FROM clients WHERE email = ?";
        try(PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setString(1, email);
            ResultSet rs = pstmt.executeQuery();
            if(rs.next()) {
                return rs.getInt("to_pay");
            }
        } catch(SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }
    
    public void updateClientToPay(String email, int toPay) {
        String sql = "UPDATE clients SET to_pay = ? WHERE email = ?";
        try(PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setInt(1, toPay);
            pstmt.setString(2, email);
            pstmt.executeUpdate();
        } catch(SQLException e) {
            e.printStackTrace();
        }
    }
    
    public void printAllClients() {
        String sql = "SELECT * FROM clients";
        try(Statement stmt = connection.createStatement()) {
            ResultSet rs = stmt.executeQuery(sql);
            while(rs.next()) {
                System.out.println("Name: " + rs.getString("name"));
                System.out.println("Email: " + rs.getString("email"));
                System.out.println("Phone: " + rs.getString("phoneNumber"));
                System.out.println("To Pay: $" + rs.getInt("to_pay"));
                System.out.println("---");
            }
        } catch(SQLException e) {
            e.printStackTrace();
        }
    }
}