import java.sql.*;
import java.util.ArrayList;

public class SQLiteOrderRepository implements OrderRepository {
    
    private Connection connection;
    
    public SQLiteOrderRepository(String url) {
        try {
            Class.forName("org.sqlite.JDBC");
            this.connection = DriverManager.getConnection(url);
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("Error connecting to the database");
        }
    }
    
    @Override
    public void insert(Client client, Product product, int quantity) {
        try {
            String insertQuery = "INSERT INTO orders (client_email, product_name, product_size, product_color, quantity, total_value) VALUES (?, ?, ?, ?, ?, ?)";
            try (PreparedStatement preparedStatement = connection.prepareStatement(insertQuery)) {
                preparedStatement.setString(1, client.getEmail());
                preparedStatement.setString(2, product.getName());
                preparedStatement.setString(3, product.getSize());
                preparedStatement.setString(4, product.getColor());
                preparedStatement.setInt(5, quantity);
                preparedStatement.setInt(6, product.getPrice() * quantity);
                preparedStatement.executeUpdate();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    
    @Override
    public void delete(int orderId) {
        try {
            String deleteQuery = "DELETE FROM orders WHERE id = ?";
            try (PreparedStatement preparedStatement = connection.prepareStatement(deleteQuery)) {
                preparedStatement.setInt(1, orderId);
                preparedStatement.executeUpdate();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    
    @Override
    public int getValue(int orderId) {
        try {
            String selectQuery = "SELECT * FROM orders WHERE id = ?";
            try (PreparedStatement preparedStatement = connection.prepareStatement(selectQuery)) {
                preparedStatement.setInt(1, orderId);
                ResultSet resultSet = preparedStatement.executeQuery();
                if (resultSet.next()) {
                    return resultSet.getInt("total_value");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }
    
    @Override
    public int getId(Client client, Product product, int quantity) {
        try {
            String selectQuery = "SELECT * FROM orders WHERE client_email = ? AND product_name = ? AND product_size = ? AND product_color = ? AND quantity = ? AND total_value = ?";
            try (PreparedStatement preparedStatement = connection.prepareStatement(selectQuery)) {
                preparedStatement.setString(1, client.getEmail());
                preparedStatement.setString(2, product.getName());
                preparedStatement.setString(3, product.getSize());
                preparedStatement.setString(4, product.getColor());
                preparedStatement.setInt(5, quantity);
                preparedStatement.setInt(6, product.getPrice() * quantity);
                ResultSet resultSet = preparedStatement.executeQuery();
                if (resultSet.next()) {
                    return resultSet.getInt("id");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }
    
    @Override
    public ArrayList<Integer> getAllIds() {
        ArrayList<Integer> ordersID = new ArrayList<>();
        try {
            String selectQuery = "SELECT * FROM orders";
            try (PreparedStatement preparedStatement = connection.prepareStatement(selectQuery)) {
                ResultSet resultSet = preparedStatement.executeQuery();
                while (resultSet.next()) {
                    ordersID.add(resultSet.getInt("id"));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return ordersID;
    }
    
    @Override
    public void printAll() {
        try {
            String selectQuery = "SELECT * FROM orders";
            try (PreparedStatement preparedStatement = connection.prepareStatement(selectQuery)) {
                ResultSet resultSet = preparedStatement.executeQuery();
                while (resultSet.next()) {
                    System.out.println("Order ID: " + resultSet.getInt("id"));
                    System.out.println("Client Email: " + resultSet.getString("client_email"));
                    System.out.println("Product Name: " + resultSet.getString("product_name"));
                    System.out.println("Product Size: " + resultSet.getString("product_size"));
                    System.out.println("Product Color: " + resultSet.getString("product_color"));
                    System.out.println("Quantity: " + resultSet.getInt("quantity"));
                    System.out.println("Total Value: " + resultSet.getInt("total_value"));
                    System.out.println();
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    
    @Override
    public void printByClient(String email) {
        try {
            String selectQuery = "SELECT * FROM orders WHERE client_email = ?";
            try (PreparedStatement preparedStatement = connection.prepareStatement(selectQuery)) {
                preparedStatement.setString(1, email);
                ResultSet resultSet = preparedStatement.executeQuery();
                while (resultSet.next()) {
                    System.out.println("Order ID: " + resultSet.getInt("id"));
                    System.out.println("Client Email: " + resultSet.getString("client_email"));
                    System.out.println("Product Name: " + resultSet.getString("product_name"));
                    System.out.println("Product Size: " + resultSet.getString("product_size"));
                    System.out.println("Product Color: " + resultSet.getString("product_color"));
                    System.out.println("Quantity: " + resultSet.getInt("quantity"));
                    System.out.println("Total Value: " + resultSet.getInt("total_value"));
                    System.out.println();
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}