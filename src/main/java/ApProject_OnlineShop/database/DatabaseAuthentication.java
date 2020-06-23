package ApProject_OnlineShop.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

public class DatabaseAuthentication {
    public static void createConnection() {
        Connection connection = null;
        String connectionUrl = "jdbc:sqlserver://localhost:1433;databaseName=AdventureWorks2017;user=sa;password=1051380";
        try {
            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
            connection = DriverManager.getConnection(connectionUrl);
            Statement stmt = connection.createStatement();
            String SQL = "SELECT * FROM Production.Product ORDER BY ProductID DESC";
            ResultSet rs = stmt.executeQuery(SQL);

            // Iterate through the data in the result set and display it.
            while (rs.next()) {
                System.out.println(rs.getString("ProductID") + " " + rs.getString("Name"));
            }
        } catch ( Exception e ) {
            e.printStackTrace();
        }
    }
}
