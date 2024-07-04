package Model;

import Datos.Conexion;
import Entity.ECliente;
import Entity.EResponse;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class Cliente {

    private static final String INSERT_SQL = "INSERT INTO cliente (descnombre,descapellido,descemail,numtelefono,mntsaldo,flgestado) VALUES (?,?,?,?,?,1)";
    private static final String SELECT_SQL = "SELECT idcliente, descnombre, descapellido, descemail, numtelefono, mntsaldo FROM (SELECT idcliente, descnombre, descapellido, descemail, numtelefono, mntsaldo FROM cliente WHERE flgestado = 1 ORDER BY idcliente) AS data LIMIT ? OFFSET ?";
    private static final String DELETE_SQL = "UPDATE cliente SET flgestado=0 WHERE idcliente = ?"; //PARA ELIMINAR
    private static final String UPDATE_SQL = "UPDATE cliente SET descnombre=?, descapellido=?, descemail=?, numtelefono=?, mntsaldo=? WHERE idcliente=?";
    private static final String SELECTID_SQL = "SELECT idcliente FROM cliente WHERE descnombre=? AND descapellido=? AND descemail=? AND numtelefono=? AND mntsaldo=? AND flgestado=1";
    private static final String CONTEO_SQL = "SELECT COUNT(idcliente) FROM cliente WHERE flgestado=1";
    private static final String SALDO_SQL = "SELECT SUM(mntsaldo) FROM cliente WHERE flgestado=1";
    private static final String NUMERO_SQL = "SELECT COUNT(numtelefono) FROM cliente WHERE numtelefono IS NOT NULL AND TRIM(numtelefono) != '' AND flgestado = 1";
    private static final String CONTEOELIMINADOS_SQL = "SELECT COUNT(idcliente) FROM cliente WHERE flgestado=0";
    private static final String SUELDOCLIENTE_SQL = "SELECT descnombre,mntsaldo FROM cliente WHERE flgestado=1 ORDER BY mntsaldo DESC LIMIT 5;";

    public static EResponse insertCliente(ECliente objCliente) throws SQLException {
        EResponse<EResponse> response = new EResponse<>();
        Connection cn = Conexion.getConnection();
        int exec = 0;
        try {

            PreparedStatement ps = cn.prepareStatement(INSERT_SQL);
            ps.setString(1, objCliente.getDescnombre());
            ps.setString(2, objCliente.getDescapellido());
            ps.setString(3, objCliente.getDescemail());
            ps.setString(4, objCliente.getNumtelefono());
            ps.setFloat(5, objCliente.getMntsaldo());
            exec = ps.executeUpdate();
            if (exec > 0) {
                response.setSuccess(true);
                response.setStatus("200");
                response.setMessage("Se insertó correctamente el cliente");
            }
        } catch (Exception e) {
            response.setSuccess(false);
            response.setStatus("500");
            response.setMessage(e.getMessage());
        } finally {
            Conexion.closeConnection(cn);
        }
        return response;
    }

    public static List<ECliente> getCliente(String limit, String offset) throws SQLException {
        List<ECliente> lstCliente = new ArrayList<>();
        Connection cn = Conexion.getConnection();

        try {
            PreparedStatement ps = cn.prepareStatement(SELECT_SQL);
            ps.setInt(1, Integer.parseInt(limit));
            ps.setInt(2, Integer.parseInt(offset));

            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                ECliente objCliente = new ECliente();
                objCliente.setIdcliente(rs.getInt(1));
                objCliente.setDescnombre(rs.getString(2));
                objCliente.setDescapellido(rs.getString(3));
                objCliente.setDescemail(rs.getString(4));
                objCliente.setNumtelefono(rs.getString(5));
                objCliente.setMntsaldo(rs.getFloat(6));
                lstCliente.add(objCliente);
            }
        } catch (Exception e) {
        } finally {
            Conexion.closeConnection(cn);
        }
        return lstCliente;
    }

    //ELIMINAR CLIENTE
    public static EResponse deleteCliente(int idcliente) throws SQLException {
        EResponse<EResponse> response = new EResponse<>();
        Connection cn = Conexion.getConnection();
        int exec = 0;
        try {
            PreparedStatement ps = cn.prepareStatement(DELETE_SQL);
            ps.setInt(1, idcliente);
            exec = ps.executeUpdate();
            if (exec > 0) {
                response.setSuccess(true);
                response.setStatus("200");
                response.setMessage("Se eliminó el cliente correctamente");
            }
        } catch (Exception e) {
            response.setSuccess(false);
            response.setStatus("500");
            response.setMessage(e.getMessage());
        } finally {
            Conexion.closeConnection(cn);
        }
        return response;
    }

    // ACTUALIZAR CLIENTE
    public static EResponse updateCliente(ECliente objCliente) throws SQLException {
        EResponse<EResponse> response = new EResponse<>();
        Connection cn = Conexion.getConnection();
        int exec = 0;
        try {
            PreparedStatement ps = cn.prepareStatement(UPDATE_SQL);
            ps.setString(1, objCliente.getDescnombre());
            ps.setString(2, objCliente.getDescapellido());
            ps.setString(3, objCliente.getDescemail());
            ps.setString(4, objCliente.getNumtelefono());
            ps.setFloat(5, objCliente.getMntsaldo());
            ps.setInt(6, objCliente.getIdcliente());
            exec = ps.executeUpdate();
            if (exec > 0) {
                response.setSuccess(true);
                response.setStatus("200");
                response.setMessage("Se actualizó el cliente correctamente");
            }
        } catch (Exception e) {
            response.setSuccess(false);
            response.setStatus("500");
            response.setMessage(e.getMessage());
        } finally {
            Conexion.closeConnection(cn);
        }
        return response;
    }

    // OBTENER ID
    public static int obtenerId(ECliente objCliente) throws SQLException {
        int idCliente = 0;
        Connection cn = Conexion.getConnection();
        try {
            PreparedStatement ps = cn.prepareStatement(SELECTID_SQL);
            ps.setString(1, objCliente.getDescnombre());
            ps.setString(2, objCliente.getDescapellido());
            ps.setString(3, objCliente.getDescemail());
            ps.setString(4, objCliente.getNumtelefono());
            ps.setFloat(5, objCliente.getMntsaldo());
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                idCliente = rs.getInt("idcliente");
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        } finally {
            Conexion.closeConnection(cn);
        }
        return idCliente;
    }

    public static int getCountCliente(ECliente objCliente) throws SQLException {
        int conteoIdCliente = 0;
        Connection cn = Conexion.getConnection();

        try {
            PreparedStatement ps = cn.prepareStatement(CONTEO_SQL);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                conteoIdCliente = rs.getInt(1);
            }
        } catch (Exception e) {
        } finally {
            Conexion.closeConnection(cn);
        }
        return conteoIdCliente;
    }

    public static float getTotalSaldoCliente(ECliente objCliente) throws SQLException {
        float conteoSaldoCliente = 0;
        Connection cn = Conexion.getConnection();

        try {
            PreparedStatement ps = cn.prepareStatement(SALDO_SQL);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                // Obtener el valor del saldo total desde la primera columna
                conteoSaldoCliente = rs.getFloat(1);
            }
        } catch (Exception e) {
        } finally {
            Conexion.closeConnection(cn);
        }
        return conteoSaldoCliente;
    }

    public static int getCantidadTelefonoCliente(ECliente objCliente) throws SQLException {
        int sueldoCliente = 0;
        Connection cn = Conexion.getConnection();

        try {
            PreparedStatement ps = cn.prepareStatement(NUMERO_SQL);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                // Obtener el valor del saldo total desde la primera columna
                sueldoCliente = rs.getInt(1);
            }
        } catch (Exception e) {
        } finally {
            Conexion.closeConnection(cn);
        }
        return sueldoCliente;
    }

    //PARA EL DASHBOARD:
    public static int getCountClienteEliminado(ECliente objCliente) throws SQLException {
        int conteoIdCliente = 0;
        Connection cn = Conexion.getConnection();

        try {
            PreparedStatement ps = cn.prepareStatement(CONTEOELIMINADOS_SQL);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                conteoIdCliente = rs.getInt(1);
            }
        } catch (Exception e) {
        } finally {
            Conexion.closeConnection(cn);
        }
        return conteoIdCliente;
    }
    public static List<ECliente> getSueldoCliente() throws SQLException {
        List<ECliente> lstCliente = new ArrayList<>();
        Connection cn = Conexion.getConnection();

        try {
            PreparedStatement ps = cn.prepareStatement(SUELDOCLIENTE_SQL);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                ECliente objCliente = new ECliente();
                objCliente.setDescnombre(rs.getString(1));
                objCliente.setMntsaldo(rs.getFloat(2));
                lstCliente.add(objCliente);
            }
        } catch (Exception e) {
        } finally {
            Conexion.closeConnection(cn);
        }
        return lstCliente;
    }
}
