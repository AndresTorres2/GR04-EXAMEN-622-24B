package Controller;

import Model.DAO.*;
import Model.Entity.*;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;

import java.io.IOException;
import java.sql.Date;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.TextStyle;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@WebServlet(name = "ReservarAsientoServlet", value = "/ReservarAsientoServlet")
public class ReservaController extends HttpServlet {
    private ReservaDAO reservaDAO;

    private EstudianteDAO estudianteDAO;
    private ViajeDAO viajeDAO;
    private CalleDAO calleDAO;
    HttpSession session;

    public void init() {
        reservaDAO = new ReservaDAO();

        estudianteDAO = new EstudianteDAO();
        viajeDAO = new ViajeDAO();
        calleDAO = new CalleDAO();
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        this.ruteador(request, response);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        this.ruteador(request, response);
    }

    private void ruteador(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = (request.getParameter("action") == null) ? "listar" : request.getParameter("action");

        switch (action) {
            case "guardarReserva":
                guardarReserva(request, response);
                break;
            case "formularioReserva":
                mostrarFormularioReserva(request, response);
                break;
            case "consultarReservas":
                consultarReservas(request, response);
                break;
            case "verReservasDia":
                verReservasDia(request, response);
                break;
            case "detalleReserva":
                mostrarReserva(request, response);
                break;
            case "cancelarReserva":
                cancelarReserva(request, response);
                break;
            default:
                break;
        }
    }

    private void mostrarFormularioReserva(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        HttpSession session = request.getSession();
        session.setAttribute("viajesList", viajeDAO.obtenerViajesPorIds(request.getParameter("idsViaje")));
        request.setAttribute("viajesList", viajeDAO.obtenerViajesPorIds(request.getParameter("idsViaje")));
        request.getRequestDispatcher("/View/Estudiante/reservarAsiento.jsp").forward(request, response);


    }

    private void guardarReserva(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        String[] viajesIdsSeleccionados = request.getParameterValues("idsViajesSeleccionados");
        HttpSession session = request.getSession();
        List<Viaje> viajesList = (List<Viaje>) session.getAttribute("viajesList");
        if (viajesIdsSeleccionados == null || viajesIdsSeleccionados.length == 0) {
            request.setAttribute("error", "Debe seleccionar al menos un viaje para realizar la reserva.");
            request.setAttribute("viajesList", viajesList);
            request.getRequestDispatcher("/View/Estudiante/reservarAsiento.jsp").forward(request, response);

            return;
        }

        Integer estudianteId = (Integer) session.getAttribute("usuarioId");
        Estudiante estudiante = estudianteDAO.obtenerEstudiantePorId(estudianteId);
        List<Viaje> listaViajes = viajeDAO.obtenerListaDeViajes(viajesIdsSeleccionados);
        for (Viaje viaje : listaViajes) {
            if (reservaDAO.existeReserva(estudiante.getId(), viaje.getId())) {
                java.sql.Date sqlDate = viaje.getFecha();
                LocalDate localDate = sqlDate.toLocalDate();
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("EEEE", new Locale("es", "ES"));
                String dia = localDate.format(formatter);
                request.setAttribute("error", "Ya tiene una reserva para el viaje para el dia " + dia  + ". No puede reservar el mismo viaje más de una vez.");
                request.setAttribute("viajesList", viajesList);
                request.getRequestDispatcher("/View/Estudiante/reservarAsiento.jsp").forward(request, response);

                return;
            }
        }
        reservaDAO.guardarVariasReservas(listaViajes, estudiante);
        request.setAttribute("successMessage", "Reserva realizada con éxito.");
        request.getRequestDispatcher("/View/Estudiante/listarViajes.jsp").forward(request, response);

    }

    private void verReservasDia(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        Estudiante estudiante = (Estudiante) session.getAttribute("usuario");
        request.setAttribute("reservas", reservaDAO.obtenerReservasPorDia(Integer.parseInt(request.getParameter("dia")),estudiante));
        request.getRequestDispatcher("/View/Estudiante/consultarReservas.jsp").forward(request, response);
    }

    private void consultarReservas(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        request.getRequestDispatcher("/View/Estudiante/consultarReservas.jsp").forward(request, response);

    }

    private void mostrarReserva(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int reservaId = Integer.parseInt(request.getParameter("reservaId"));
        Reserva reservaSeleccionada = reservaDAO.obtenerReservaPorId(reservaId);
        List<Object[]> callesYCoordenadas = calleDAO.obtenerCallesYCoordenadasPorRutaId(reservaSeleccionada.getViaje().getRuta().getId());
        request.setAttribute("callesYCoordenadas", callesYCoordenadas);
        request.setAttribute("reserva", reservaSeleccionada);

        if (!callesYCoordenadas.isEmpty()) {
            request.setAttribute("origen", callesYCoordenadas.get(0)); // Primera calle
            request.setAttribute("destino", callesYCoordenadas.get(callesYCoordenadas.size() - 1)); // Última calle
        }
        request.getRequestDispatcher("/View/Estudiante/detallesReserva.jsp").forward(request, response);

    }

    private void cancelarReserva(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        reservaDAO.cancelarReserva(Integer.parseInt(request.getParameter("reservaId")),
                reservaDAO.obtenerReservaPorId(Integer.parseInt(request.getParameter("reservaId"))).getViaje());
        response.sendRedirect(request.getContextPath() + "/ReservarAsientoServlet?action=consultarReservas");

    }

}