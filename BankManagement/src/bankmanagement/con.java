
package bankmanagement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;
import java.sql.PreparedStatement;

public class con {
    Connection connection;
    Statement statement;
    PreparedStatement preparedStatement;
    public con(){
        try {
            connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/Bank_Management", "root", "");
            statement = connection.createStatement();
        }catch (Exception e){
            e.printStackTrace();
        }
    }

}

