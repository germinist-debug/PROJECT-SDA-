import java.sql.*;
import java.util.ArrayList;

public class SQLiteProductRepository implements ProductRepository {
    
    private Connection connection;
    
    public SQLiteProductRepository(String url) {
        try {
            Class.forName("org.sqlite.JDBC");
            this.connection = DriverManager.getConnection(url);
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("Error connecting to the database");
        }
    }
    
    @Override
    public void insert(Product product) {
        try {
            String insertQuery = "INSERT INTO products (type, name, size, color, quantity, price) VALUES (?, ?, ?, ?, ?, ?)";
            try (PreparedStatement preparedStatement = connection.prepareStatement(insertQuery)) {
                preparedStatement.setString(1, product.getType());
                preparedStatement.setString(2, product.getName());
                preparedStatement.setString(3, product.getSize());
                preparedStatement.setString(4, product.getColor());
                preparedStatement.setInt(5, product.getQuantity());
                preparedStatement.setInt(6, product.getPrice());
                preparedStatement.executeUpdate();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    
    @Override
    public void updateQuantity(Product product) {
        try {
            String updateQuery = "UPDATE products SET quantity = ? WHERE name = ? AND size = ? AND color = ?";
            try (PreparedStatement preparedStatement = connection.prepareStatement(updateQuery)) {
                preparedStatement.setInt(1, product.getQuantity());
                preparedStatement.setString(2, product.getName());
                preparedStatement.setString(3, product.getSize());
                preparedStatement.setString(4, product.getColor());
                preparedStatement.executeUpdate();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    
    @Override
    public void updatePrice(Product product) {
        try {
            String updateQuery = "UPDATE products SET price = ? WHERE name = ? AND size = ? AND color = ?";
            try (PreparedStatement preparedStatement = connection.prepareStatement(updateQuery)) {
                preparedStatement.setInt(1, product.getPrice());
                preparedStatement.setString(2, product.getName());
                preparedStatement.setString(3, product.getSize());
                preparedStatement.setString(4, product.getColor());
                preparedStatement.executeUpdate();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    
    @Override
    public void delete(Product product) {
        try {
            String deleteQuery = "DELETE FROM products WHERE name = ? AND size = ? AND color = ? AND PRICE = ?";
            try (PreparedStatement preparedStatement = connection.prepareStatement(deleteQuery)) {
                preparedStatement.setString(1, product.getName());
                preparedStatement.setString(2, product.getSize());
                preparedStatement.setString(3, product.getColor());
                preparedStatement.setInt(4, product.getPrice());
                preparedStatement.executeUpdate();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    
    @Override
    public void deleteAll() {
        try {
            String deleteQuery = "DELETE FROM products";
            try (PreparedStatement preparedStatement = connection.prepareStatement(deleteQuery)) {
                preparedStatement.executeUpdate();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    
    @Override
    public ArrayList<Product> getAll() {
        ArrayList<Product> productList = new ArrayList<>();
        try {
            String selectQuery = "SELECT * FROM products";
            try (PreparedStatement preparedStatement = connection.prepareStatement(selectQuery)) {
                ResultSet resultSet = preparedStatement.executeQuery();
                while (resultSet.next()) {
                    String type = resultSet.getString("type");
                    String name = resultSet.getString("name");
                    String size = resultSet.getString("size");
                    String color = resultSet.getString("color");
                    int quantity = resultSet.getInt("quantity");
                    int price = resultSet.getInt("price");
                    if (type.equals("topWear")) {
                        productList.add(new TopWear(name, size, color, quantity, price));
                    } else {
                        productList.add(new BottomWear(name, size, color, quantity, price));
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return productList;
    }
    
    @Override
    public Product findByNameSizeColor(String name, String size, String color) {
        try {
            String selectQuery = "SELECT * FROM products WHERE name = ? AND size = ? AND color = ?";
            try (PreparedStatement preparedStatement = connection.prepareStatement(selectQuery)) {
                preparedStatement.setString(1, name);
                preparedStatement.setString(2, size);
                preparedStatement.setString(3, color);
                ResultSet resultSet = preparedStatement.executeQuery();
                if (resultSet.next()) {
                    String type = resultSet.getString("type");
                    int quantity = resultSet.getInt("quantity");
                    int price = resultSet.getInt("price");
                    if (type.equals("topWear")) {
                        return new TopWear(name, size, color, quantity, price);
                    } else {
                        return new BottomWear(name, size, color, quantity, price);
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
}