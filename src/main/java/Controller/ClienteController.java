package Controller;

import Entity.ECliente;
import Entity.EResponse;
import Model.Cliente;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import org.json.JSONObject;

@WebServlet("/ServletCliente")
public class ClienteController extends HttpServlet {

    @Override
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        try {
            ECliente cliente = new ECliente();
            String type = request.getParameter("type");

            if ("2".equals(type)) {
                cliente.setDescnombre(request.getParameter("descnombre"));
                cliente.setDescapellido(request.getParameter("descapellido"));
                cliente.setDescemail(request.getParameter("descemail"));
                cliente.setNumtelefono(request.getParameter("numtelefono"));
                cliente.setMntsaldo(Float.parseFloat(request.getParameter("mntsaldo")));
            } else if ("3".equals(type)) {
                // OBTENER ID CLIENTE PARA ELIMINAR
                cliente.setIdcliente(Integer.parseInt(request.getParameter("idcliente"))); 
            } else if ("4".equals(type)) {
                // OBTENER ID CLIENTE PARA ACTUALIZAR
                cliente.setIdcliente(Integer.parseInt(request.getParameter("idcliente"))); 
                cliente.setDescnombre(request.getParameter("descnombre"));
                cliente.setDescapellido(request.getParameter("descapellido"));
                cliente.setDescemail(request.getParameter("descemail"));
                cliente.setNumtelefono(request.getParameter("numtelefono"));
                cliente.setMntsaldo(Float.parseFloat(request.getParameter("mntsaldo")));
            }

            EResponse<EResponse> respuesta = (EResponse<EResponse>) mantenimientoCliente(type, cliente);

            JSONObject json = new JSONObject();
            json.put("success", respuesta.isSuccess());
            json.put("status", respuesta.getStatus());
            json.put("message", respuesta.getMessage());

            response.setContentType("application/json");
            PrintWriter out = response.getWriter();
            out.print(json);
            out.close();
        } catch (SQLException ex) {
            Logger.getLogger(ClienteController.class.getName()).log(Level.SEVERE, null, ex);
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, ex.getMessage());
        }
    }

    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        try {
            String type = request.getParameter("type");
            if ("1".equals(type)){
                List<ECliente> respuesta = (List<ECliente>) mantenimientoCliente(type, null);
                JSONObject json = new JSONObject();
                json.put("body", respuesta);
                response.setContentType("application/json");
                PrintWriter out = response.getWriter();
                out.print(json);
                out.close();
            } else if ("5".equals(type)) {
                ECliente cliente = new ECliente();
                cliente.setDescnombre(request.getParameter("descnombre"));
                cliente.setDescapellido(request.getParameter("descapellido"));
                cliente.setDescemail(request.getParameter("descemail"));
                cliente.setNumtelefono(request.getParameter("numtelefono"));
                cliente.setMntsaldo(Float.parseFloat(request.getParameter("mntsaldo")));
                int respuesta = (int) mantenimientoCliente(type, cliente);
                JSONObject json = new JSONObject();
                json.put("body", respuesta);
                response.setContentType("application/json");
                PrintWriter out = response.getWriter();
                out.print(json);
                out.close();
            }else if ("6".equals(type) ||"8".equals(type) || "9".equals(type)) {
                ECliente cliente = new ECliente();
                int respuesta = (int) mantenimientoCliente(type, cliente);
                JSONObject json = new JSONObject();
                json.put("body", respuesta);
                response.setContentType("application/json");
                PrintWriter out = response.getWriter();
                out.print(json);
                out.close();
            }else if ("7".equals(type)) {
                ECliente cliente = new ECliente();
                float respuesta = (float) mantenimientoCliente(type, cliente);
                JSONObject json = new JSONObject();
                json.put("body", respuesta);
                response.setContentType("application/json");
                PrintWriter out = response.getWriter();
                out.print(json);
                out.close();
            }
        } catch (SQLException ex) {
            Logger.getLogger(ClienteController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    private Object mantenimientoCliente(String type, ECliente cliente) throws SQLException {
        EResponse<EResponse> response = new EResponse<>();
        List<ECliente> lstCliente = new ArrayList<>();
        int idCliente = 0;
        float saldoCliente=0;
        switch (type) {
            case "1":
                lstCliente = Cliente.getCliente();
                break;
            // Prueba
            case "2":
                response = Cliente.insertCliente(cliente);
                break;
            case "3": // eliminar cliente
                response = Cliente.deleteCliente(cliente.getIdcliente());
                break;
            case "4": // actualizar cliente
                response = Cliente.updateCliente(cliente);
                break;
            case "5": 
                idCliente = Cliente.obtenerId(cliente);
                break;
            case "6": 
                idCliente = Cliente.getCountCliente(cliente);
                break;
            case "7": 
                saldoCliente = Cliente.getTotalSaldoCliente(cliente);
                break;
            case "8": 
                idCliente = Cliente.getCantidadTelefonoCliente(cliente);
                break;
            case "9":
                idCliente = Cliente.getCountClienteEliminado(cliente);
                break;
        }
        if (type.equals("1")) {
            return lstCliente;
        } else if (type.equals("2") || type.equals("3") || type.equals("4")) {
            return response;
        } else if (type.equals("5") || type.equals("6") || type.equals("8") || type.equals("9")) {
            return idCliente;
        } else if (type.equals("7")){
            return saldoCliente;
        }
        return true;
    }
    
}
