import java.sql.*;
import java.util.ArrayList;

public class DatabaseHandler {

    private Connection connection;

    public DatabaseHandler(String url) {
        try {
            Class.forName("org.sqlite.JDBC");
            this.connection = DriverManager.getConnection(url);
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("Error connecting to the database");
        }
    }

    public void closeConnection() {
        try {
            if (connection != null && !connection.isClosed()) {
                connection.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void insertProduct(Product product) {
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

    public void insertOwner(Owner owner, String password) {
        try {
            String insertQuery = "INSERT INTO owner (name, email, phoneNumber, password) VALUES (?, ?, ?, ?)";
            try (PreparedStatement preparedStatement = connection.prepareStatement(insertQuery)) {
                preparedStatement.setString(1, owner.getName());
                preparedStatement.setString(2, owner.getEmail());
                preparedStatement.setString(3, owner.getPhoneNumber());
                preparedStatement.setString(4, password);
                preparedStatement.executeUpdate();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void updateProductQuantity(Product product) {
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

    public void updateProductPrice(Product product) {
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

    public void updateOwner(Owner owner, String password) {
        try {
            String updateQuery = "UPDATE owner SET name = ?, email = ?, phoneNumber = ?, password = ?";
            try (PreparedStatement preparedStatement = connection.prepareStatement(updateQuery)) {
                preparedStatement.setString(1, owner.getName());
                preparedStatement.setString(2, owner.getEmail());
                preparedStatement.setString(3, owner.getPhoneNumber());
                preparedStatement.setString(4, password);
                preparedStatement.executeUpdate();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void deleteProduct(Product product) {
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

    public void deleteOwner() {
        try {
            String deleteQuery = "DELETE FROM owner";
            try (PreparedStatement preparedStatement = connection.prepareStatement(deleteQuery)) {
                preparedStatement.executeUpdate();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void deleteAllProducts() {
        try {
            String deleteQuery = "DELETE FROM products";
            try (PreparedStatement preparedStatement = connection.prepareStatement(deleteQuery)) {
                preparedStatement.executeUpdate();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void deleteClient(String email) {
        try {
            String deleteQuery = "DELETE FROM clients WHERE email = ?";
            try (PreparedStatement preparedStatement = connection.prepareStatement(deleteQuery)) {
                preparedStatement.setString(1, email);
                preparedStatement.executeUpdate();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void deleteAllClients() {
        try {
            String deleteQuery = "DELETE FROM clients";
            try (PreparedStatement preparedStatement = connection.prepareStatement(deleteQuery)) {
                preparedStatement.executeUpdate();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void wipeStore() {
        deleteAllProducts();
        deleteOwner();
        deleteAllClients();
    }

   public void printProduct(Product product) {
        try {
            String selectQuery = "SELECT * FROM products WHERE name = ? AND size = ? AND color = ? AND price = ?";
            try (PreparedStatement preparedStatement = connection.prepareStatement(selectQuery)) {
                preparedStatement.setString(1, product.getName());
                preparedStatement.setString(2, product.getSize());
                preparedStatement.setString(3, product.getColor());
                preparedStatement.setInt(4, product.getPrice());
                ResultSet resultSet = preparedStatement.executeQuery();
                while (resultSet.next()) {
                    System.out.println("Product ID: " + resultSet.getInt("id"));
                    System.out.println("Product Type: " + resultSet.getString("type"));
                    System.out.println("Product Name: " + resultSet.getString("name"));
                    System.out.println("Product Size: " + resultSet.getString("size"));
                    System.out.println("Product Color: " + resultSet.getString("color"));
                    System.out.println("Product Quantity: " + resultSet.getInt("quantity"));
                    System.out.println("Product Price: " + resultSet.getInt("price"));
                    System.out.println();
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public String getClientPassword(String email) {
        try {
            String selectQuery = "SELECT * FROM clients WHERE email = ?";
            try (PreparedStatement preparedStatement = connection.prepareStatement(selectQuery)) {
                preparedStatement.setString(1, email);
                ResultSet resultSet = preparedStatement.executeQuery();
                if (resultSet.next()) {
                    return resultSet.getString("password");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public String getClientName(String email) {
        try {
            String selectQuery = "SELECT * FROM clients WHERE email = ?";
            try (PreparedStatement preparedStatement = connection.prepareStatement(selectQuery)) {
                preparedStatement.setString(1, email);
                ResultSet resultSet = preparedStatement.executeQuery();
                if (resultSet.next()) {
                    return resultSet.getString("name");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public String getClientPhoneNumber(String email) {
        try {
            String selectQuery = "SELECT * FROM clients WHERE email = ?";
            try (PreparedStatement preparedStatement = connection.prepareStatement(selectQuery)) {
                preparedStatement.setString(1, email);
                ResultSet resultSet = preparedStatement.executeQuery();
                if (resultSet.next()) {
                    return resultSet.getString("phoneNumber");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public String getOwnerPassword() {
        try {
            String selectQuery = "SELECT * FROM owner";
            try (PreparedStatement preparedStatement = connection.prepareStatement(selectQuery)) {
                ResultSet resultSet = preparedStatement.executeQuery();
                if (resultSet.next()) {
                    return resultSet.getString("password");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public String getOwnerEmail() {
        try {
            String selectQuery = "SELECT * FROM owner";
            try (PreparedStatement preparedStatement = connection.prepareStatement(selectQuery)) {
                ResultSet resultSet = preparedStatement.executeQuery();
                if (resultSet.next()) {
                    return resultSet.getString("email");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public String getOwnerName() {
        try {
            String selectQuery = "SELECT * FROM owner";
            try (PreparedStatement preparedStatement = connection.prepareStatement(selectQuery)) {
                ResultSet resultSet = preparedStatement.executeQuery();
                if (resultSet.next()) {
                    return resultSet.getString("name");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public String getOwnerPhoneNumber() {
        try {
            String selectQuery = "SELECT * FROM owner";
            try (PreparedStatement preparedStatement = connection.prepareStatement(selectQuery)) {
                ResultSet resultSet = preparedStatement.executeQuery();
                if (resultSet.next()) {
                    return resultSet.getString("phoneNumber");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public void printAllProducts() {
        try {
            String selectQuery = "SELECT * FROM products";
            try (PreparedStatement preparedStatement = connection.prepareStatement(selectQuery)) {
                ResultSet resultSet = preparedStatement.executeQuery();
                while (resultSet.next()) {
                    System.out.println("Product ID: " + resultSet.getInt("id"));
                    System.out.println("Product Type: " + resultSet.getString("type"));
                    System.out.println("Product Name: " + resultSet.getString("name"));
                    System.out.println("Product Size: " + resultSet.getString("size"));
                    System.out.println("Product Color: " + resultSet.getString("color"));
                    System.out.println("Product Quantity: " + resultSet.getInt("quantity"));
                    System.out.println("Product Price: " + resultSet.getInt("price"));
                    System.out.println();
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void printAllOwners() {
        try {
            String selectQuery = "SELECT * FROM owner";
            try (PreparedStatement preparedStatement = connection.prepareStatement(selectQuery)) {
                ResultSet resultSet = preparedStatement.executeQuery();
                while (resultSet.next()) {
                    System.out.println("Owner Name: " + resultSet.getString("name"));
                    System.out.println("Owner Email: " + resultSet.getString("email"));
                    System.out.println("Owner Phone Number: " + resultSet.getString("phoneNumber"));
                    System.out.println();
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public void printAllClients() {
        try {
            String selectQuery = "SELECT * FROM clients";
            try (PreparedStatement preparedStatement = connection.prepareStatement(selectQuery)) {
                ResultSet resultSet = preparedStatement.executeQuery();
                while (resultSet.next()) {
                    System.out.println("Client Name: " + resultSet.getString("name"));
                    System.out.println("Client Email: " + resultSet.getString("email"));
                    System.out.println("Client Phone Number: " + resultSet.getString("phoneNumber"));
                    System.out.println("Client To Pay: " + resultSet.getInt("to_pay"));
                    System.out.println();
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public ArrayList<Product> retrieveProductListFromDB(){
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
                    if(type.equals("topWear")) productList.add(new TopWear(name, size, color, quantity, price));
                    else productList.add(new BottomWear(name, size, color, quantity, price));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return productList;
    }

    public Owner retrieveOwnerFromDB() {
        try {
            String selectQuery = "SELECT * FROM owner";
            try (PreparedStatement preparedStatement = connection.prepareStatement(selectQuery)) {
                ResultSet resultSet = preparedStatement.executeQuery();
                if (resultSet.next()) {
                    String name = resultSet.getString("name");
                    String email = resultSet.getString("email");
                    String phoneNumber = resultSet.getString("phoneNumber");
                    return new Owner(name, email, phoneNumber);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public OnlineStore retrieveOnlineStoreFromDB(){
        ArrayList<Product> productList = retrieveProductListFromDB();
        Owner owner = retrieveOwnerFromDB();
        return new OnlineStore(owner, productList);
    }

    public void insertClient(Client client, String password) {
        try {
            String insertQuery = "INSERT INTO clients (name, email, phoneNumber, password, to_pay) VALUES (?, ?, ?, ?, 0)";
            try (PreparedStatement preparedStatement = connection.prepareStatement(insertQuery)) {
                preparedStatement.setString(1, client.getName());
                preparedStatement.setString(2, client.getEmail());
                preparedStatement.setString(3, client.getPhoneNumber());
                preparedStatement.setString(4, password);
                preparedStatement.executeUpdate();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public int getClientToPay(String email) {
        try {
            String selectQuery = "SELECT * FROM clients WHERE email = ?";
            try (PreparedStatement preparedStatement = connection.prepareStatement(selectQuery)) {
                preparedStatement.setString(1, email);
                ResultSet resultSet = preparedStatement.executeQuery();
                if (resultSet.next()) {
                    return resultSet.getInt("to_pay");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }

    public void insertOrder(Client client, Product product, int quantity) {
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

    public void removeOrder(int id) {
        int to_pay = getOrderValue(id);
        try{
            String selectQuery = "SELECT * FROM orders WHERE id = ?";
            try (PreparedStatement preparedStatement = connection.prepareStatement(selectQuery)) {
                preparedStatement.setInt(1, id);
                ResultSet resultSet = preparedStatement.executeQuery();
                if (resultSet.next()) {
                    String email = resultSet.getString("client_email");
                    to_pay = getClientToPay(email) - to_pay;
                    updateClientToPay(email, to_pay);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        try {
            String deleteQuery = "DELETE FROM orders WHERE id = ?";
            try (PreparedStatement preparedStatement = connection.prepareStatement(deleteQuery)) {
                preparedStatement.setInt(1, id);
                preparedStatement.executeUpdate();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public ArrayList<Integer> retrieveAllOrdersID(){
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

    public int getOrderValue(int id) {
        try {
            String selectQuery = "SELECT * FROM orders WHERE id = ?";
            try (PreparedStatement preparedStatement = connection.prepareStatement(selectQuery)) {
                preparedStatement.setInt(1, id);
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

    public int getOrderID(Client client, Product product, int quantity) {
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
    public void printOrder(int id) {
        try {
            String selectQuery = "SELECT * FROM orders WHERE id = ?";
            try (PreparedStatement preparedStatement = connection.prepareStatement(selectQuery)) {
                preparedStatement.setInt(1, id);
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

    public void printAllOrders() {
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

    public void printClientOrders(String email) {
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

    public void updateClientToPay(String email, int to_pay) {
        try {
            String updateQuery = "UPDATE clients SET to_pay = ? WHERE email = ?";
            try (PreparedStatement preparedStatement = connection.prepareStatement(updateQuery)) {
                preparedStatement.setInt(1, to_pay);
                preparedStatement.setString(2, email);
                preparedStatement.executeUpdate();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void updateClient(Client client, String password) {
        try {
            String updateQuery = "UPDATE clients SET name = ?, phoneNumber = ?, password = ? WHERE email = ?";
            try (PreparedStatement preparedStatement = connection.prepareStatement(updateQuery)) {
                preparedStatement.setString(1, client.getName());
                preparedStatement.setString(2, client.getPhoneNumber());
                preparedStatement.setString(3, password);
                preparedStatement.setString(4, client.getEmail());
                preparedStatement.executeUpdate();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public boolean checkClientEmail(String email){
        try {
            String selectQuery = "SELECT * FROM clients WHERE email = ?";
            try (PreparedStatement preparedStatement = connection.prepareStatement(selectQuery)) {
                preparedStatement.setString(1, email);
                ResultSet resultSet = preparedStatement.executeQuery();
                if (resultSet.next()) {
                    return true;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public Client retrieveClientDetails(String email){
        try {
            String selectQuery = "SELECT * FROM clients WHERE email = ?";
            try (PreparedStatement preparedStatement = connection.prepareStatement(selectQuery)) {
                preparedStatement.setString(1, email);
                ResultSet resultSet = preparedStatement.executeQuery();
                if (resultSet.next()) {
                    String name = resultSet.getString("name");
                    String phoneNumber = resultSet.getString("phoneNumber");
                    return new Client(name, phoneNumber, email);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public String checkRole(String email, String password){
        if(email.equals("admin") && password.equals("admin")) return "admin";
        try {
            String selectQuery = "SELECT * FROM owner WHERE email = ? AND password = ?";
            try (PreparedStatement preparedStatement = connection.prepareStatement(selectQuery)) {
                preparedStatement.setString(1, email);
                preparedStatement.setString(2, password);
                ResultSet resultSet = preparedStatement.executeQuery();
                if (resultSet.next()) {
                    return "owner";
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        try {
            String selectQuery = "SELECT * FROM owner WHERE email = ?";
            try (PreparedStatement preparedStatement = connection.prepareStatement(selectQuery)) {
                preparedStatement.setString(1, email);
                ResultSet resultSet = preparedStatement.executeQuery();
                if (resultSet.next()) {
                    return "wrongPassword";
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        try {
            String selectQuery = "SELECT * FROM clients WHERE email = ? AND password = ?";
            try (PreparedStatement preparedStatement = connection.prepareStatement(selectQuery)) {
                preparedStatement.setString(1, email);
                preparedStatement.setString(2, password);
                ResultSet resultSet = preparedStatement.executeQuery();
                if (resultSet.next()) {
                    return "client";
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        try {
            String selectQuery = "SELECT * FROM clients WHERE email = ?";
            try (PreparedStatement preparedStatement = connection.prepareStatement(selectQuery)) {
                preparedStatement.setString(1, email);
                ResultSet resultSet = preparedStatement.executeQuery();
                if (resultSet.next()) {
                    return "wrongPassword";
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return "notFound";
    }
}
