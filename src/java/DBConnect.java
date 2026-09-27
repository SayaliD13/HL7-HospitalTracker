import java.sql.Connection;
import java.sql.DriverManager;

// this file only has one job - give a connection to the MySQL database
public class DBConnect {

    static final String URL = "jdbc:mysql://localhost:3306/hospital_db";
    static final String USER = "root";
    static final String PASSWORD = "sayaligauri";

    public static Connection getConnection() throws Exception {
        Class.forName("com.mysql.cj.jdbc.Driver");
        return DriverManager.getConnection(URL, USER, PASSWORD);
    }
}
