import java.sql.*;

public class OwnerRepository {
    
    private Connection connection;
    
    public OwnerRepository(Connection connection) {
        this.connection = connection;
    }
    
    public void insertOwner(Owner owner, String password) {
        String sql = "INSERT OR REPLACE INTO owner (name, email, phoneNumber, password) VALUES (?, ?, ?, ?)";
        try(PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setString(1, owner.getName());
            pstmt.setString(2, owner.getEmail());
            pstmt.setString(3, owner.getPhoneNumber());
            pstmt.setString(4, password);
            pstmt.executeUpdate();
        } catch(SQLException e) {
            e.printStackTrace();
        }
    }
    
    public void updateOwner(Owner owner, String password) {
        String sql = "UPDATE owner SET name = ?, email = ?, phoneNumber = ?, password = ?";
        try(PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setString(1, owner.getName());
            pstmt.setString(2, owner.getEmail());
            pstmt.setString(3, owner.getPhoneNumber());
            pstmt.setString(4, password);
            pstmt.executeUpdate();
        } catch(SQLException e) {
            e.printStackTrace();
        }
    }
    
    public Owner getOwner() {
        String sql = "SELECT * FROM owner";
        try(Statement stmt = connection.createStatement()) {
            ResultSet rs = stmt.executeQuery(sql);
            if(rs.next()) {
                return new Owner(rs.getString("name"), rs.getString("email"), rs.getString("phoneNumber"));
            }
        } catch(SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
    
    public void deleteOwner() {
        String sql = "DELETE FROM owner";
        try(Statement stmt = connection.createStatement()) {
            stmt.executeUpdate(sql);
        } catch(SQLException e) {
            e.printStackTrace();
        }
    }
    
    public String getOwnerPassword() {
        String sql = "SELECT password FROM owner";
        try(Statement stmt = connection.createStatement()) {
            ResultSet rs = stmt.executeQuery(sql);
            if(rs.next()) {
                return rs.getString("password");
            }
        } catch(SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
    
    public boolean ownerExists() {
        String sql = "SELECT 1 FROM owner";
        try(Statement stmt = connection.createStatement()) {
            ResultSet rs = stmt.executeQuery(sql);
            return rs.next();
        } catch(SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
}