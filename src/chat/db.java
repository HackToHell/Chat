/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package chat;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Date;
import java.sql.Timestamp;



/**
 *
 * @author Gowtham
 */
public  class db {


    static private Connection connect = null;
 static private Statement statement = null;
 static private PreparedStatement preparedStatement = null;
 static private ResultSet resultSet = null;


 static void pushdata(int userid, String g, Timestamp t)throws Exception{
      Class.forName("com.mysql.jdbc.Driver");
      // Setup the connection with the DB
      connect = DriverManager
          .getConnection("jdbc:mysql://localhost/stacke?"
              + "user=sqler&password=sqluser");

      // Statements allow to issue SQL queries to the database
      statement = connect.createStatement();
       resultSet = statement
          .executeQuery("select * from stacke.messages");
       preparedStatement = connect
          .prepareStatement("insert into  stacke.messages values ( ? , ?, ?)");
       preparedStatement.setInt(1, userid);
       preparedStatement.setString(2, g);
       preparedStatement.setTimestamp(3, t);
         preparedStatement.executeUpdate();



      }

  void close() {
    try {
      if (resultSet != null) {
        resultSet.close();
      }

      if (statement != null) {
        statement.close();
      }

      if (connect != null) {
        connect.close();
      }
    } catch (Exception e) {

    }
  }

}
