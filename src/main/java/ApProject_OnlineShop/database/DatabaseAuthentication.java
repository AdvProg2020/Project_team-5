package ApProject_OnlineShop.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

public class DatabaseAuthentication {
    public static void createConnection() {
        Connection connection;
        String connectionUrl = "jdbc:mysql://192.168.43.236\\MSSQLSERVER:1433;databaseName=AdventureWorks2017;user=sa;password=1051380";
        try {
            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
            connection = DriverManager.getConnection(connectionUrl);
            Statement stmt = connection.createStatement();
            String SQL = "SELECT * FROM Production.Product ORDER BY ProductID DESC";
            ResultSet rs = stmt.executeQuery(SQL);
            while (rs.next()) {
                System.out.println(rs.getString("ProductID") + " " + rs.getString("Name"));
            }
        } catch ( Exception e ) {
            e.printStackTrace();
        }
    }
}
