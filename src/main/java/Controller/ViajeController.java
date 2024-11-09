package Controller;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

import Model.DAO.CalleDAO;
import Model.DAO.ViajeDAO;
import Model.DAO.ReservaDAO;
import Model.Entity.Calle;
import Model.Entity.Ruta;
import Model.Entity.Viaje;
import Model.Entity.Estudiante;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;

@WebServlet(name = "ViajeServlet", value = "/ViajeServlet")
public class ViajeController extends HttpServlet {
    private CalleDAO calleDAO;
    private ViajeDAO viajeDAO;
    private ReservaDAO reservaDAO;

    public void init() {
        viajeDAO = new ViajeDAO();
        calleDAO = new CalleDAO();
        reservaDAO = new ReservaDAO();
    }

    public void listarViajes(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setAttribute("viajes", viajeDAO.listarViajesPorJornada(req.getParameter("jornada")));

        RequestDispatcher dispatcher = req.getRequestDispatcher("View/Estudiante/listarViajes.jsp");
        dispatcher.forward(req, resp);
    }

    public void verDetallesViaje(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        List<Integer> idViajesList = viajeDAO.convertirCadenaAListaDeIds(req.getParameter("ids"));
        Viaje viaje = viajeDAO.obtenerViajePorCodigo(idViajesList.get(0));
        List<Object[]> callesYCoordenadas = calleDAO.obtenerCallesYCoordenadasPorRutaId(viaje.getRuta().getId());

        req.setAttribute("idViajes", req.getParameter("ids"));
        req.setAttribute("viaje", viaje);
        req.setAttribute("callesYCoordenadas", callesYCoordenadas);

        setOrigenYDestino(req,callesYCoordenadas);
        RequestDispatcher dispatcher = req.getRequestDispatcher("View/Estudiante/detallesViaje.jsp");
        dispatcher.forward(req, resp);


    }


    public void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        this.ruteador(req, resp);
    }

    public void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        this.ruteador(req, resp);
    }

    public void ruteador(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String ruta = (req.getParameter("ruta") == null) ? "listarCliente" : req.getParameter("ruta");
        switch (ruta) {
            case "seleccionarJornada":
                this.listarViajes(req, resp);
                break;
            case "verDetalles":
                this.verDetallesViaje(req, resp);
                break;
            case "verificarViajeVacio":
                this.verificarViajeVacio(req, resp);
                break;
            case "verPasajeros":
                this.verPasajeros(req, resp);
                break;
            default:
                break;

        }

    }
    public static void setOrigenYDestino(HttpServletRequest req, List<Object[]> callesYCoordenadas) {
        if (!callesYCoordenadas.isEmpty()) {
            req.setAttribute("origen", callesYCoordenadas.get(0));
            req.setAttribute("destino", callesYCoordenadas.get(callesYCoordenadas.size() - 1));
        }
    }

    // Verificar si el viaje tiene pasajeros
    public void verificarViajeVacio(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        int viajeId = Integer.parseInt(req.getParameter("viajeId"));
        Viaje viaje = viajeDAO.obtenerViajePorCodigo(viajeId);
        boolean isVacio = reservaDAO.verificarViajeVacio(viaje);

        req.setAttribute("isVacio", isVacio);
        req.setAttribute("viaje", viaje);

        RequestDispatcher dispatcher = req.getRequestDispatcher("View/Estudiante/detallesViaje.jsp");
        dispatcher.forward(req, resp);
    }

    // Listar pasajeros del viaje
    public void verPasajeros(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        int viajeId = Integer.parseInt(req.getParameter("viajeId"));
        Viaje viaje = viajeDAO.obtenerViajePorCodigo(viajeId);
        List<Estudiante> pasajeros = reservaDAO.listarPasajerosPorViajeOrdenado(viaje);
        req.setAttribute("pasajeros", pasajeros);
        req.setAttribute("viaje", viaje);
        RequestDispatcher dispatcher = req.getRequestDispatcher("View/Conductor/listaPasajeros.jsp");
        dispatcher.forward(req, resp);
    }


}
