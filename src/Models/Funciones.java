package Models;

import java.sql.*;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Funciones
{
    public Statement connectToBD() throws SQLException
    {
        Connection conexion = DriverManager.getConnection("jdbc:mysql://localhost:3306/padel","root", "");
        Statement s = conexion.createStatement();
        return s;
    }
    public void update(String query) throws SQLException {
        connectToBD().executeUpdate(query);
    }
    public ResultSet ejecutarQuery(String query) throws SQLException
    {
        return connectToBD().executeQuery(query);
    }
}
