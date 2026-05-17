import java.sql.*;
import java.util.ArrayList;

public class ProductRepository {
    
    private Connection connection;
    
    public ProductRepository(Connection connection) {
        this.connection = connection;
    }
    
    public void insertProduct(Product product) {
        String sql = "INSERT OR REPLACE INTO products (type, name, size, color, quantity, price) VALUES (?, ?, ?, ?, ?, ?)";
        try(PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setString(1, product.getType());
            pstmt.setString(2, product.getName());
            pstmt.setString(3, product.getSize());
            pstmt.setString(4, product.getColor());
            pstmt.setInt(5, product.getQuantity());
            pstmt.setInt(6, product.getPrice());
            pstmt.executeUpdate();
        } catch(SQLException e) {
            e.printStackTrace();
        }
    }
    
    public void updateProductQuantity(Product product) {
        String sql = "UPDATE products SET quantity = ? WHERE name = ? AND size = ? AND color = ?";
        try(PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setInt(1, product.getQuantity());
            pstmt.setString(2, product.getName());
            pstmt.setString(3, product.getSize());
            pstmt.setString(4, product.getColor());
            pstmt.executeUpdate();
        } catch(SQLException e) {
            e.printStackTrace();
        }
    }
    
    public void deleteProduct(Product product) {
        String sql = "DELETE FROM products WHERE name = ? AND size = ? AND color = ?";
        try(PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setString(1, product.getName());
            pstmt.setString(2, product.getSize());
            pstmt.setString(3, product.getColor());
            pstmt.executeUpdate();
        } catch(SQLException e) {
            e.printStackTrace();
        }
    }
    
    public void deleteAllProducts() {
        String sql = "DELETE FROM products";
        try(Statement stmt = connection.createStatement()) {
            stmt.executeUpdate(sql);
        } catch(SQLException e) {
            e.printStackTrace();
        }
    }
    
    public ArrayList<Product> getAllProducts() {
        ArrayList<Product> products = new ArrayList<>();
        String sql = "SELECT * FROM products";
        try(Statement stmt = connection.createStatement()) {
            ResultSet rs = stmt.executeQuery(sql);
            while(rs.next()) {
                String type = rs.getString("type");
                String name = rs.getString("name");
                String size = rs.getString("size");
                String color = rs.getString("color");
                int quantity = rs.getInt("quantity");
                int price = rs.getInt("price");
                
                if(type.equals("topWear")) {
                    products.add(new TopWear(name, size, color, quantity, price));
                } else {
                    products.add(new BottomWear(name, size, color, quantity, price));
                }
            }
        } catch(SQLException e) {
            e.printStackTrace();
        }
        return products;
    }
}