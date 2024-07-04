package Datos;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import javax.servlet.ServletContext;
import javax.sql.DataSource;
import org.apache.commons.dbcp2.BasicDataSource;

public class Conexion {
    private static final String JDBC_URL = "jdbc:mysql://localhost:3306/control_cliente?zeroDateTimeBehavior=CONVERT_TO_NULL";
    private static final String JDBC_USER = "root"; //Cambiar por su usuario que corresponde
    private static final String JDBC_PASSWORD = ""; //Cambiar por la contraseña que corresponde
    
    //Metodo que va gestionar conexion a bd
    public static DataSource getDataSource(){
        BasicDataSource ds = new BasicDataSource();
        ds.setUrl(JDBC_URL);
        ds.setUsername(JDBC_USER);
        ds.setPassword(JDBC_PASSWORD);
        //Tamaño inicial de pool de conexiones (maximo 50 conexiones)
        ds.setInitialSize(50);
        return ds;
    }
    
    //Metodo que genera la conexion
    public static Connection getConnection() throws SQLException{
        return getDataSource().getConnection();
    }
    
    //Cerrar conexion
    public static void closeConnection(Connection cn) throws SQLException{
        cn.close();
    }
    
    public static void probarConexion(ServletContext context) {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/control_cliente", "root", "");
           // System.out.println("Conexión establecida correctamente!");
           // context.setAttribute("mensaje", "Conexión establecida correctamente!");
        } catch (ClassNotFoundException e) {
            System.out.println("Error al cargar el driver: " + e.getMessage());
            context.setAttribute("mensaje", "Error al cargar el driver: " + e.getMessage());
        } catch (SQLException e) {
            System.out.println("Error al conectar a la base de datos: " + e.getMessage());
            context.setAttribute("mensaje", "Error al conectar a la base de datos: " + e.getMessage());
        }
    }
}
