package Model;

import Datos.Conexion;
import Entity.ECliente;
import Entity.EResponse;
import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Cliente {

    private static final String INSERT_SQL = "INSERT INTO cliente (descnombre,descapellido,descemail,numtelefono,mntsaldo,flgestado) VALUES (?,?,?,?,?,1)";
    private static final String SELECT_SQL = "SELECT idcliente,descnombre,descapellido,descemail,numtelefono,mntsaldo FROM cliente WHERE flgestado=1";
    private static final String DELETE_SQL = "UPDATE cliente SET flgestado=0 WHERE idcliente = ?"; //PARA ELIMINAR
    private static final String UPDATE_SQL = "UPDATE cliente SET descnombre=?, descapellido=?, descemail=?, numtelefono=?, mntsaldo=? WHERE idcliente=?";
    private static final String SELECTID_SQL = "SELECT idcliente FROM cliente WHERE descnombre=? AND descapellido=? AND descemail=? AND numtelefono=? AND mntsaldo=? AND flgestado=1";
    private static final String CONTEO_SQL = "SELECT COUNT(idcliente) FROM cliente WHERE flgestado=1";
    private static final String SALDO_SQL = "SELECT SUM(mntsaldo) FROM cliente WHERE flgestado=1";
    private static final String NUMERO_SQL = "SELECT COUNT(numtelefono) FROM cliente WHERE numtelefono IS NOT NULL AND TRIM(numtelefono) != '' AND flgestado = 1";
    private static final String CONTEOELIMINADOS_SQL = "SELECT COUNT(idcliente) FROM cliente WHERE flgestado=0";
    private static final String DASHBOARD_SQL = "SELECT flgestado, SUM(mntsaldo) AS total_saldo FROM cliente WHERE flgestado IN (0, 1) GROUP BY flgestado;";

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

    public static List<ECliente> getCliente() throws SQLException {
        List<ECliente> lstCliente = new ArrayList<>();
        Connection cn = Conexion.getConnection();

        try {
            PreparedStatement ps = cn.prepareStatement(SELECT_SQL);
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
        int cantNumeroCliente = 0;
        Connection cn = Conexion.getConnection();

        try {
            PreparedStatement ps = cn.prepareStatement(NUMERO_SQL);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                // Obtener el valor del saldo total desde la primera columna
                cantNumeroCliente = rs.getInt(1);
            }
        } catch (Exception e) {
        } finally {
            Conexion.closeConnection(cn);
        }
        return cantNumeroCliente;
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

    public static Map<Integer, BigDecimal> getTotalesPorEstado(ECliente cliente) throws SQLException {
        Map<Integer, BigDecimal> totalesPorEstado = new HashMap<>();
        Connection cn = null;

        try {
            cn = Conexion.getConnection();
            PreparedStatement ps = cn.prepareStatement(DASHBOARD_SQL);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                int estado = rs.getInt("flgestado");
                BigDecimal totalSaldo = rs.getBigDecimal("total_saldo");
                totalesPorEstado.put(estado, totalSaldo);
            }
        } catch (SQLException e) {
            throw new SQLException("Error al obtener los totales por estado del cliente", e);
        } finally {
            Conexion.closeConnection(cn);
        }

        return totalesPorEstado;
    }
}
