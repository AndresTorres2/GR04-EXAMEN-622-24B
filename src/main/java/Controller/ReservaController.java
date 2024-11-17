package Controller;

import Model.DAO.*;
import Model.Entity.*;
import jakarta.mail.MessagingException;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;
import org.json.JSONObject;

import java.io.IOException;
import java.sql.Date;
import java.time.*;
import java.time.format.TextStyle;
import java.time.temporal.TemporalAdjusters;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;
import java.time.format.DateTimeFormatter;

@WebServlet(name = "ReservarAsientoServlet", value = "/ReservarAsientoServlet")
public class ReservaController extends HttpServlet {
    private ReservaDAO reservaDAO;
    private EstudianteDAO estudianteDAO;
    private ViajeDAO viajeDAO;
    private CalleDAO calleDAO;
    private  EmailDAO emailDAO;
    private ParadaEstudianteDAO paradaEstudianteDAO;
    HttpSession session;

    public void init() {
        reservaDAO = new ReservaDAO();
        emailDAO = new EmailDAO();
        estudianteDAO = new EstudianteDAO();
        viajeDAO = new ViajeDAO();
        calleDAO = new CalleDAO();
        paradaEstudianteDAO = new ParadaEstudianteDAO();
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
            case "mostrarParadasDeReserva":
                mostrarParadasDeReserva(request, response);
                break;
            default:
                break;
        }
    }
    private void mostrarParadasDeReserva(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int reservaId = Integer.parseInt(request.getParameter("reservaId"));
        Reserva reserva =  reservaDAO.obtenerReservaPorId(reservaId);

        ParadaEstudiante paradaEstudiante = paradaEstudianteDAO.obtenerParadaPorReserva(reserva);

        if (paradaEstudiante != null) {
            JSONObject jsonResponse = new JSONObject();
            jsonResponse.put("latitud", paradaEstudiante.getUbicacion().getLatitud());
            jsonResponse.put("longitud", paradaEstudiante.getUbicacion().getLongitud());
            System.out.println("----------- mostrarParadasDeReserva -------------");
            System.out.println(paradaEstudiante);
            System.out.println(jsonResponse);
            System.out.println("----------------------");

            response.setContentType("application/json");
            response.getWriter().write(jsonResponse.toString());
        } else {
            response.setContentType("application/json");
            response.getWriter().write("{\"success\": false, \"message\": \"No se encontró la parada.\"}");
        }
    }
    private void mostrarFormularioReserva(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        HttpSession session = request.getSession();
        LocalDate today = LocalDate.now();
        List<LocalDate> diasSemana = obtenerDiasProximaSemana(today);
        String idsViajes = request.getParameter("idsViaje");
        List<Viaje> viajesDeEstaSemana = viajeDAO.obtenerViajesPorIdsYFechas(idsViajes, diasSemana);
        if (viajesDeEstaSemana.isEmpty()) {
            System.out.println("No se encontraron viajes para los IDs y fechas especificadas.");
        } else {
            System.out.println("Viajes encontrados para la próxima semana:");
            for (Viaje viaje : viajesDeEstaSemana) {
                System.out.println(viaje);
            }
        }
        session.setAttribute("viajesList",viajesDeEstaSemana);
        request.setAttribute("viajesList", viajesDeEstaSemana);
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
            if (viaje.getAsientosOcupados() == viaje.getBus().getCapacidad()) {
                Date sqlDate = viaje.getFecha();
                LocalDate localDate = sqlDate.toLocalDate();
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy", new Locale("es", "ES"));
                String fecha = localDate.format(formatter);

                request.setAttribute("error", "El viaje del " + fecha + " está completo. Por favor elija otro viaje.");
                request.setAttribute("viajesList", viajesList);
                request.getRequestDispatcher("/View/Estudiante/reservarAsiento.jsp").forward(request, response);
                return;
            }



            if (reservaDAO.existeReserva(estudiante.getId(), viaje.getId())) {
                Date sqlDate = viaje.getFecha();
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
        notificarReserva(request, response, listaViajes, estudiante);
        request.setAttribute("successMessage", "Reserva realizada con éxito.");
        request.getRequestDispatcher("/View/Estudiante/listarViajes.jsp").forward(request, response);

    }

    private void verReservasDia(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        Estudiante estudiante = (Estudiante) session.getAttribute("usuario");
        LocalDate hoy = LocalDate.now();


        List<LocalDate> diasSemana = obtenerDiasDeSemana(hoy);
        List<Reserva> reservas = reservaDAO.obtenerReservasPorDiaYFechas(diasSemana, estudiante,Integer.parseInt(request.getParameter("dia")));
        request.setAttribute("reservas",reservas);
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
        Reserva reserva = reservaDAO.obtenerReservaPorId(Integer.parseInt(request.getParameter("reservaId")));
        reservaDAO.cancelarReserva(Integer.parseInt(request.getParameter("reservaId")),
                reserva.getViaje());
        notificarCancelarReserva(request, response, reserva.getViaje(), reserva.getEstudiante());
        response.sendRedirect(request.getContextPath() + "/ReservarAsientoServlet?action=consultarReservas");

    }



    private void notificarReserva(HttpServletRequest req, HttpServletResponse resp, List<Viaje> listaViajes, Estudiante estudiante) {
        try {
            // Construir el mensaje
            StringBuilder mensaje = new StringBuilder();
            mensaje.append("Estimado/a ").append(estudiante.getNombre()).append(",\n\n");
            mensaje.append("Le confirmamos que ha realizado la reserva de los siguientes viajes:\n\n");


            for (Viaje viaje : listaViajes) {
                Date sqlDate = viaje.getFecha();
                LocalDate localDate = sqlDate.toLocalDate();
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("EEEE", new Locale("es", "ES"));
                String dia = localDate.format(formatter);

                mensaje.append("- Viaje: ").append(viaje.getRuta().getOrigen()).append(" ➔ ").append(viaje.getRuta().getDestino()).append("\n");
                mensaje.append("  Fecha: ").append(viaje.getFecha()).append(" (").append(dia).append(")\n");
                mensaje.append("  Hora de salida: ").append(viaje.getHoraDeSalida()).append("\n\n");
            }

            mensaje.append("Por favor, asegúrese de estar puntual y listo para abordar. ¡Gracias por elegir Polibus!\n\n");
            mensaje.append("Atentamente,\n");
            mensaje.append("El equipo de Polibus");


            emailDAO.enviarCorreo(estudiante.getEmail(), "Confirmación de Reserva - Polibus", mensaje.toString());

        } catch (MessagingException e) {

            e.printStackTrace();

            try {
                req.setAttribute("error", "Hubo un problema al enviar el correo de confirmación.");
                req.getRequestDispatcher("/View/Error.jsp").forward(req, resp);
            } catch (ServletException | IOException servletException) {
                servletException.printStackTrace();
            }
        }
    }
    private void notificarCancelarReserva(HttpServletRequest req, HttpServletResponse resp, Viaje viaje, Estudiante estudiante) {
        try {
            StringBuilder mensaje = new StringBuilder();
            mensaje.append("Estimado/a ").append(estudiante.getNombre()).append(",\n\n");
            mensaje.append("Le informamos que su reserva ha sido cancelada con exito  para el siguiente viaje:\n\n");

            Date sqlDate = viaje.getFecha();
            LocalDate localDate = sqlDate.toLocalDate();
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("EEEE", new Locale("es", "ES"));
            String dia = localDate.format(formatter);

            mensaje.append("- Viaje: ").append(viaje.getRuta().getOrigen()).append(" ➔ ").append(viaje.getRuta().getDestino()).append("\n");
            mensaje.append("  Fecha: ").append(viaje.getFecha()).append(" (").append(dia).append(")\n");
            mensaje.append("  Hora de salida: ").append(viaje.getHoraDeSalida()).append("\n\n");

            mensaje.append("Si tiene alguna pregunta o necesita asistencia, no dude en contactarnos.\n\n");
            mensaje.append("Atentamente,\n");
            mensaje.append("El equipo de Polibus");

            emailDAO.enviarCorreo(estudiante.getEmail(), "Cancelación de Reserva - Polibus", mensaje.toString());

        } catch (MessagingException e) {
            e.printStackTrace();
            try {
                req.setAttribute("error", "Hubo un problema al enviar el correo de cancelación.");
                req.getRequestDispatcher("/View/Error.jsp").forward(req, resp);
            } catch (ServletException | IOException servletException) {
                servletException.printStackTrace();
            }
        }
    }

    private List<LocalDate> obtenerDiasProximaSemana(LocalDate hoy) {
        List<LocalDate> diasSemana = new ArrayList<>();

        // Encontrar el próximo lunes
        LocalDate proximoLunes = hoy.with(TemporalAdjusters.next(DayOfWeek.MONDAY));

        // Agregar los días de lunes a viernes de la próxima semana
        for (int i = 0; i < 5; i++) { // 5 días laborales
            diasSemana.add(proximoLunes.plusDays(i));
        }
        System.out.println("Días de la próxima semana (lunes a viernes):");
        for (LocalDate dia : diasSemana) {
            System.out.println(dia);
        }
        return diasSemana;
    }
    private List<LocalDate> obtenerDiasDeSemana(LocalDate hoy) {
        List<LocalDate> diasSemana = new ArrayList<>();


        LocalDate proximoLunes = hoy.with(TemporalAdjusters.nextOrSame(DayOfWeek.MONDAY));


        int diasRestantes;


        if (hoy.getDayOfWeek() == DayOfWeek.MONDAY) {
            diasRestantes = 5;
        }

        else if (hoy.getDayOfWeek() == DayOfWeek.TUESDAY) {
            diasRestantes = 4;
        }

        else if (hoy.getDayOfWeek() == DayOfWeek.WEDNESDAY) {
            diasRestantes = 3;
        }

        else if (hoy.getDayOfWeek() == DayOfWeek.THURSDAY) {
            diasRestantes = 2;
        }

        else if (hoy.getDayOfWeek() == DayOfWeek.FRIDAY) {
            diasRestantes = 1;  // Solo viernes
        }

        else {
            diasRestantes = 5;
        }

        LocalDate diaInicio = (hoy.getDayOfWeek() == DayOfWeek.SATURDAY || hoy.getDayOfWeek() == DayOfWeek.SUNDAY) ? proximoLunes : hoy;
        for (int i = 0; i < diasRestantes; i++) {
            LocalDate dia = diaInicio.plusDays(i);

            if (dia.isAfter(hoy) || dia.isEqual(hoy)) {
                diasSemana.add(dia);
                System.out.println(dia);
            }
        }
        return diasSemana;
    }








}