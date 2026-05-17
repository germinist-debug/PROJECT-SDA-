import java.sql.*;

public class DatabaseInitializer {

    // SQLite database connection URL
    private static final String URL = "jdbc:sqlite:E:/IdeaProjects/onlineStore-java/sqlite";

    public static String getURL(){
        return URL;
    }
    public static void initializeDatabase() {
        try {

            Class.forName("org.sqlite.JDBC");
            Connection connection = DriverManager.getConnection(URL);
            Statement statement = connection.createStatement();

            String createProductsTable = "CREATE TABLE IF NOT EXISTS products (" +
                    "type TEXT NOT NULL," + // topWear or bottomWear
                    "name TEXT NOT NULL," +
                    "size TEXT NOT NULL," +
                    "color TEXT NOT NULL," +
                    "quantity INTEGER NOT NULL," +
                    "price INTEGER NOT NULL," +
                    "PRIMARY KEY (name, size, color))";
            statement.execute(createProductsTable);

            String createOwnerTable = "CREATE TABLE IF NOT EXISTS owner (" +
                    "name TEXT NOT NULL," +
                    "email TEXT NOT NULL," +
                    "phoneNumber TEXT NOT NULL, " +
                    "password TEXT NOT NULL, " +
                    "UNIQUE (email))";
            statement.execute(createOwnerTable);

            String createClientsTable = "CREATE TABLE IF NOT EXISTS clients (" +
                    "name TEXT NOT NULL," +
                    "email TEXT NOT NULL," +
                    "phoneNumber TEXT NOT NULL," +
                    "password TEXT NOT NULL," +
                    "to_pay INTEGER NOT NULL," +
                    "UNIQUE (email))";
            statement.execute(createClientsTable);

            String createOrdersTable = "CREATE TABLE IF NOT EXISTS orders (" +
                    "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                    "client_email TEXT NOT NULL," +
                    "product_name TEXT NOT NULL," +
                    "product_size TEXT NOT NULL," +
                    "product_color TEXT NOT NULL," +
                    "quantity INTEGER NOT NULL," +
                    "total_value INTEGER NOT NULL," +
                    "FOREIGN KEY (client_email) REFERENCES clients(email))";
            statement.execute(createOrdersTable);

            System.out.println("Database initialized successfully.");

        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }
    public static void main(String[] args) {
        initializeDatabase();
    }
}
