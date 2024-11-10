package Controller;

import Model.DAO.*;
import Model.Entity.*;
import jakarta.mail.MessagingException;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.sql.Date;
import java.sql.Time;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@WebServlet(name = "GestionServlet", value = "/GestionServlet")
public class GestionController extends HttpServlet {
    RutaDAO rutaDAO ;
    CalleDAO calleDAO ;
    ViajeDAO viajeDAO;
    BusDAO busDAO;
    EmailDAO emailDAO;
    UsuarioDAO usuarioDAO ;
    ConductorDAO conductorDAO;
    ReservaDAO reservaDAO;
    EstudianteDAO estudianteDAO;

    @Override
    public void init() throws ServletException {
        super.init();
        rutaDAO = new RutaDAO();
        calleDAO = new CalleDAO();
        usuarioDAO = new UsuarioDAO();
        viajeDAO = new ViajeDAO();
        busDAO = new BusDAO();
        conductorDAO = new ConductorDAO();
        reservaDAO = new ReservaDAO();
        estudianteDAO = new EstudianteDAO();
        emailDAO = new EmailDAO();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            this.ruteador(req, resp);
        } catch (MessagingException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            this.ruteador(req, resp);
        } catch (MessagingException e) {
            throw new RuntimeException(e);
        }
    }

    public void ruteador(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException, MessagingException {
        String ruta = (req.getParameter("action") == null) ? "login" : req.getParameter("action");
        switch (ruta) {
            case "mostrarLogin":
                mostrarLogin(req, resp);
                break;
            case "validarUsuario":
                validarCredenciales(req, resp);
                break;
            case "dashboardAdmin":
                //Aca redireccionamos a una vista donde seleccionara que quiere ver, por ejemplo se muestra Rutas, Viajes, Conductores
                mostrarDashboardAdmin(req,resp);
                break;
            case "gestionRutas":
                gestionarRutas(req,resp);
                break;
            case "gestionReservas":
                gestionarReservas(req,resp);
                break;
            case "eliminarReserva":
                cancelarReservas(req,resp);
                break;
            case "crearReserva":
                crearReserva(req,resp);
                break;
            case "nuevaReserva":
                formNuevaReserva(req,resp);
                break;
            case "nuevaRuta":
                mostrarFormRuta(req,resp);
                break;
            case "guardarRuta":
                guardarRuta(req,resp);
                break;
            case "eliminarRuta":
                eliminarRuta(req,resp);
                break;
            case "formActualizarRuta":
                mostrarFormActualizarRuta(req,resp);
                break;
            case "actualizarRuta":
                actualizarRuta(req,resp);
                break;
            case "dashboardEstudiante":
                break;

            case "gestionViajes":
                gestionarViajes(req, resp);
                break;
            case "nuevoViaje":
                mostrarFormViaje(req, resp);
                break;
            case "guardarViaje":
                guardarViaje(req, resp);
                break;
            case "eliminarViaje":
                eliminarViaje(req, resp);
                break;
            case "formActualizarViaje":
                mostrarFormActualizarViaje(req, resp);
                break;
            case "actualizarViaje":
                actualizarViaje(req, resp);
                break;
            case "gestionConductores":
                mostrarConductores(req,resp);
                break;
            case "eliminarConductor":
                eliminarConductor(req,resp);
                break;
            case "nuevoConductor":
                mostrarFormConductor(req,resp);
                    break;
            case "guardarConductor":
                guardarConductor(req,resp);
                break;
            case "gestionBuses":
                mostrarBuses(req,resp);
                break;
            case "eliminarBus":
                eliminarBus(req,resp);
                break;
            case "nuevoBus":
                mostrarFormBus(req,resp);
                break;
            case "guardarBus":
                guardarBus(req,resp);
                break;
            case "actualizarBus":
                mostrarFormActualizarBus(req,resp);
                break;
            case "busActualizado":
                actualizarBus(req,resp);
                break;
            case "consultarViajesConductor":
                consultarViajesDelConductor(req,resp);
                break;
            case "notificarPasajeros":
                notificarPasajeros(req,resp);
                break;
            case "consultarViajesDetallesConductor":
                consultarViajesDetallesConductor(req,resp);
                break;
            case "compartirUbicacion":
                compartirUbicacion(req, resp);
                break;
            case "obtenerUbicacion":
                obtenerUbicacion(req, resp);
                break;
            case "cerrarSesion":
                cerrarSesion(req, resp);
                break;
            default:
                break;
        }
    }
    public void cerrarSesion(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        HttpSession session = req.getSession(false);

        if (session != null) {
            session.invalidate();
        }
        resp.sendRedirect(req.getContextPath() + "/View/login.jsp"); // Redirige al login
    }

    private void compartirUbicacion(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String latitud = req.getParameter("latitud");
        String longitud = req.getParameter("longitud");

        req.getSession().setAttribute("latitud", latitud);
        req.getSession().setAttribute("longitud", longitud);

        resp.setStatus(HttpServletResponse.SC_OK);
    }
    private void obtenerUbicacion(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        String latitud = (String) req.getSession().getAttribute("latitud");
        String longitud = (String) req.getSession().getAttribute("longitud");

        resp.setContentType("text/plain");
        resp.setCharacterEncoding("UTF-8");

        if (latitud != null && longitud != null) {
            resp.getWriter().write(latitud + "," + longitud);
        } else {
            resp.getWriter().write("Ubicación no disponible");
        }
    }

    private void notificarPasajeros(HttpServletRequest req, HttpServletResponse resp) throws MessagingException {
        int viajeId = Integer.parseInt(req.getParameter("viajeId"));

        Viaje viaje = viajeDAO.obtenerViajePorCodigo(viajeId);
        List<String> correosPasajeros = viajeDAO.obtenerCorreosPasajerosPorViaje(viajeId);

        String fecha = viaje.getFecha().toString();
        String horaSalida = String.valueOf(viaje.getHoraDeSalida());
        String jornada = viaje.getJornada();
        String ruta = viaje.getRuta().getOrigen() + " ➔ " + viaje.getRuta().getDestino();

        String mensaje = String.format("Estimados pasajeros,\n\n" +
                "Les informamos que su viaje programado con los siguientes detalles:\n\n" +
                "Fecha: %s\n" +
                "Hora de Salida: %s\n" +
                "Jornada: %s\n" +
                "Ruta: %s\n\n" +
                "Está a punto de empezar y el conductor ha iniciado la compartición de ubicación. Por favor, asegúrense de estar puntuales.\n" +
                "¡Gracias por elegir Polibus!\n\n" +
                "Atentamente,\n" +
                "El equipo de Polibus", fecha, horaSalida, jornada, ruta);

        for (String email : correosPasajeros) {
            emailDAO.enviarCorreo(email, "Notificación de viaje Polibus", mensaje);
            System.out.println("Correo enviado a: " + email);
        }

        System.out.println("Notificaciones enviadas a los pasajeros.");
    }




    public void mostrarLogin(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        RequestDispatcher dispatcher = req.getRequestDispatcher("/View/login.jsp");
        dispatcher.forward(req, resp);
    }

    public void validarCredenciales(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String email = req.getParameter("email");
        String password = req.getParameter("password");

        if (usuarioDAO.validarCredenciales(email, password)) {
            String tipoUsuario = usuarioDAO.obtenerTipoDeUsuario(email);
            HttpSession session = req.getSession();
            Usuario usuario = usuarioDAO.buscarUsuarioPorEmail(email);
            session.setAttribute("usuario", usuario);
            session.setAttribute("usuarioId", usuario.getId());
            session.setAttribute("tipoUsuario", tipoUsuario);
            if ("U_Administrador".equals(tipoUsuario)) {
                resp.sendRedirect(req.getContextPath() + "/View/Administrador/dashboardAdmin.jsp");
            } else if ("U_Estudiante".equals(tipoUsuario)) {
                session.setAttribute("estudiante", usuario);
                resp.sendRedirect(req.getContextPath() + "/View/Estudiante/listarViajes.jsp");
            } else if ("U_Conductor".equals(tipoUsuario)) {
                Usuario conductor = usuarioDAO.buscarUsuarioPorEmail(email);
                req.setAttribute("conductor", conductor);
                req.getRequestDispatcher("/View/Conductor/dashboardConductor.jsp").forward(req,resp);
            }
        } else {
            req.setAttribute("error", "Credenciales inválidas.");
            RequestDispatcher dispatcher = req.getRequestDispatcher("/View/login.jsp");
            dispatcher.forward(req, resp);
        }
    }


    public void mostrarDashboardAdmin(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        RequestDispatcher dispatcher = req.getRequestDispatcher("/View/Administrador/dashboardAdmin.jsp");
        dispatcher.forward(req, resp);
    }
    public void consultarViajesDelConductor(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException{
            req.setAttribute("viajes",viajeDAO.obtenerListaDeViajesPorConductor(Integer.parseInt(req.getParameter("conductorId"))));
            RequestDispatcher dispatcher = req.getRequestDispatcher("/View/Conductor/viajesConductor.jsp");
            dispatcher.forward(req, resp);
    }

    public void consultarViajesDetallesConductor(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setAttribute("viaje",viajeDAO.obtenerViajePorCodigo(Integer.parseInt(req.getParameter("viajeId"))));
        RequestDispatcher dispatcher = req.getRequestDispatcher("/View/Conductor/viajesConductorDetalles.jsp");
        dispatcher.forward(req, resp);

    }

    public void gestionarRutas(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        req.setAttribute("rutas", rutaDAO.obtenerTodasLasRutas());
        RequestDispatcher dispatcher = req.getRequestDispatcher("/View/Administrador/gestionRutas.jsp");
        dispatcher.forward(req, resp);
    }
    public void gestionarReservas(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setAttribute("reservas", reservaDAO.obtenerTodasLasReservas());
        RequestDispatcher dispatcher = req.getRequestDispatcher("/View/Administrador/gestionReservas.jsp");
        dispatcher.forward(req, resp);
    }
    public void cancelarReservas(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        reservaDAO.cancelarReserva(Integer.parseInt(req.getParameter("reservaId")),
                reservaDAO.obtenerReservaPorId(Integer.parseInt(req.getParameter("reservaId"))).getViaje());
        req.setAttribute("reservas", reservaDAO.obtenerTodasLasReservas());
        RequestDispatcher dispatcher = req.getRequestDispatcher("/View/Administrador/gestionReservas.jsp");
        dispatcher.forward(req, resp);
    }
    public void crearReserva(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
            int viajeId = Integer.parseInt(req.getParameter("viajeId"));
            int estudianteId = Integer.parseInt(req.getParameter("estudianteId"));
            System.out.println("viaje" + viajeId);
        System.out.println("estudiante" + estudianteId);
            Viaje viaje = viajeDAO.obtenerViajePorCodigo(viajeId);
        Reserva reserva = new Reserva(0, viaje, estudianteDAO.obtenerEstudiantePorId(estudianteId), new Date(System.currentTimeMillis()));
            reservaDAO.guardarReserva(reserva,viaje);
        req.setAttribute("mensaje", "Reserva realizada exitosamente.");
        req.setAttribute("reservas", reservaDAO.obtenerTodasLasReservas());
        RequestDispatcher dispatcher = req.getRequestDispatcher("/View/Administrador/gestionReservas.jsp");
        dispatcher.forward(req, resp);
    }
    public void formNuevaReserva(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setAttribute("estudiantes",estudianteDAO.obtenerEstudiantes());
        req.setAttribute("viajes", viajeDAO.obtenerTodosLosViajes());
        RequestDispatcher dispatcher = req.getRequestDispatcher("/View/Administrador/registrarReserva.jsp");
        dispatcher.forward(req, resp);
    }
    public void mostrarFormRuta(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setAttribute("calles", calleDAO.obtenerTodasLasCalles());
        RequestDispatcher dispatcher = req.getRequestDispatcher("/View/Administrador/registrarRuta.jsp");
        dispatcher.forward(req, resp);
    }
    public void guardarRuta(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String origen = req.getParameter("origen");
        String destino = req.getParameter("destino");
        String[] callesIds = req.getParameterValues("calles");
        List<Calle> callesSeleccionadas = calleDAO.obtenerCallesSeleccionadas(callesIds);
        Ruta nuevaRuta = new Ruta(0,origen,destino,callesSeleccionadas);
        rutaDAO.guardarRutaDb(nuevaRuta);
        req.setAttribute("rutas", rutaDAO.obtenerTodasLasRutas());
        RequestDispatcher dispatcher = req.getRequestDispatcher("/View/Administrador/gestionRutas.jsp");
        dispatcher.forward(req, resp);

    }
    public void eliminarRuta(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

       try{
           rutaDAO.eliminarRutaDb(Integer.parseInt(req.getParameter("rutaId")));
           req.setAttribute("rutas", rutaDAO.obtenerTodasLasRutas());
           RequestDispatcher dispatcher = req.getRequestDispatcher("/View/Administrador/gestionRutas.jsp");
           dispatcher.forward(req, resp);
       }catch (Exception e){
           req.setAttribute("errorMessage", e.getMessage());
           req.setAttribute("rutas", rutaDAO.obtenerTodasLasRutas());
           RequestDispatcher dispatcher = req.getRequestDispatcher("/View/Administrador/gestionRutas.jsp");
           dispatcher.forward(req, resp);
       }


    }
    public void mostrarFormActualizarRuta(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        int rutaId = Integer.parseInt(req.getParameter("rutaId"));
        Ruta ruta = rutaDAO.obtenerRutaId(rutaId);
        List<Integer> selectedCalleIds = ruta.getCalles().stream()
                .map(Calle::getId)
                .collect(Collectors.toList());
        req.setAttribute("ruta", ruta);
        req.setAttribute("allCalles", calleDAO.obtenerTodasLasCalles());
        req.setAttribute("selectedCalleIds", selectedCalleIds);
        RequestDispatcher dispatcher = req.getRequestDispatcher("/View/Administrador/actualizarRuta.jsp");
        dispatcher.forward(req, resp);
    }
    public void actualizarRuta(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        int rutaId = Integer.parseInt(req.getParameter("rutaId"));
        String origen = req.getParameter("origen");
        String destino = req.getParameter("destino");
        String[] callesIds = req.getParameterValues("calles");
        List<Calle> callesSeleccionadas = calleDAO.obtenerCallesSeleccionadas(callesIds);
        Ruta ruta = rutaDAO.obtenerRutaId(rutaId);
        ruta.setOrigen(origen);
        ruta.setDestino(destino);
        ruta.setCalles(callesSeleccionadas);
        rutaDAO.actualizarRutaDb(ruta);
        req.setAttribute("rutas", rutaDAO.obtenerTodasLasRutas());
        RequestDispatcher dispatcher = req.getRequestDispatcher("/View/Administrador/gestionRutas.jsp");
        dispatcher.forward(req, resp);

    }

    // Métodos para gestionar viajes
    public void gestionarViajes(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setAttribute("viajes", viajeDAO.obtenerTodosLosViajes()); // Obtener todos los viajes
        RequestDispatcher dispatcher = req.getRequestDispatcher("/View/Administrador/gestionViaje.jsp");
        dispatcher.forward(req, resp);
    }

    public void mostrarFormViaje(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setAttribute("buses", busDAO.obtenerTodosLosBuses());
        req.setAttribute("rutas", rutaDAO.obtenerTodasLasRutas());
        req.setAttribute("conductores", conductorDAO.obtenerConductores());
        RequestDispatcher dispatcher = req.getRequestDispatcher("/View/Administrador/registrarViaje.jsp");
        dispatcher.forward(req, resp);
    }


    public void guardarViaje(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            String busId = req.getParameter("busId");
            int rutaId = Integer.parseInt(req.getParameter("rutaId"));
            String fecha = req.getParameter("fecha");
            String horaDeSalida = req.getParameter("horaDeSalida");
            String jornada = req.getParameter("jornada");
            String conductorId = req.getParameter("conductorId");

            Conductor conductor = conductorDAO.obtenerConductorDb(conductorId);
            Bus bus = busDAO.obtenerBusPorId(busId);
            Ruta ruta = rutaDAO.obtenerRutaId(rutaId);
            Date fechaViaje = Date.valueOf(fecha);

            if (!horaDeSalida.matches("\\d{2}:\\d{2}:\\d{2}") && !horaDeSalida.matches("\\d{2}:\\d{2}")) {
                throw new IllegalArgumentException("Formato de hora de salida no válido.");
            }

            if (horaDeSalida.matches("\\d{2}:\\d{2}")) {
                horaDeSalida += ":00";
            }

            Time horaSalida = Time.valueOf(horaDeSalida);

            Viaje nuevoViaje = new Viaje();
            nuevoViaje.setBus(bus);
            nuevoViaje.setRuta(ruta);
            nuevoViaje.setFecha(fechaViaje);
            nuevoViaje.setHoraDeSalida(horaSalida);
            nuevoViaje.setJornada(jornada);
            nuevoViaje.setConductor(conductor);

            viajeDAO.crearViajeEnDB(nuevoViaje);
            gestionarViajes(req, resp);
        } catch (Exception e) {
            req.setAttribute("error", "Error al guardar el viaje: " + e.getMessage());
            mostrarFormViaje(req, resp);
        }
    }

    public void eliminarViaje(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            viajeDAO.eliminarViajeEnDB(Integer.parseInt(req.getParameter("viajeId")));
            req.setAttribute("viajes", viajeDAO.obtenerTodosLosViajes());
            RequestDispatcher dispatcher = req.getRequestDispatcher("/View/Administrador/gestionViaje.jsp");
            dispatcher.forward(req, resp);
        }catch (Exception e){
            req.setAttribute("errorMessage", e.getMessage());
            req.setAttribute("viajes", viajeDAO.obtenerTodosLosViajes());
            RequestDispatcher dispatcher = req.getRequestDispatcher("/View/Administrador/gestionViaje.jsp");
            dispatcher.forward(req, resp);

        }

    }


    private void mostrarFormActualizarViaje(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        int viajeId = Integer.parseInt(req.getParameter("viajeId"));
        Viaje viaje = viajeDAO.obtenerViajePorCodigo(viajeId);

        req.setAttribute("viaje", viaje);
        req.setAttribute("buses", busDAO.obtenerTodosLosBuses());
        req.setAttribute("rutas", rutaDAO.obtenerTodasLasRutas());
        req.setAttribute("conductores", conductorDAO.obtenerConductores());

        forward(req, resp, "/View/Administrador/actualizarViaje.jsp");
    }

    private void actualizarViaje(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            int viajeId = Integer.parseInt(req.getParameter("viajeId"));
            String busId = req.getParameter("busId");
            int rutaId = Integer.parseInt(req.getParameter("rutaId"));
            String fecha = req.getParameter("fecha");
            String horaDeSalida = req.getParameter("horaDeSalida");
            String jornada = req.getParameter("jornada");

            Conductor conductor = conductorDAO.obtenerConductorDb(req.getParameter("conductorId"));
            Bus bus = busDAO.obtenerBusPorId(busId);
            Ruta ruta = rutaDAO.obtenerRutaId(rutaId);
            Date fechaViaje = Date.valueOf(fecha);

            if (horaDeSalida != null && !horaDeSalida.isEmpty()) {
                horaDeSalida = horaDeSalida.trim();
            } else {
                throw new IllegalArgumentException("Hora de salida no puede ser vacía.");
            }

            if (!horaDeSalida.matches("\\d{2}:\\d{2}:\\d{2}") && !horaDeSalida.matches("\\d{2}:\\d{2}")) {
                throw new IllegalArgumentException("Formato de hora de salida no válido: " + horaDeSalida);
            }

            if (horaDeSalida.matches("\\d{2}:\\d{2}")) {
                horaDeSalida += ":00";
            }

            Time horaSalida;
            try {
                horaSalida = Time.valueOf(horaDeSalida);
            } catch (IllegalArgumentException e) {
                throw new IllegalArgumentException("Formato de hora de salida no válido: " + horaDeSalida, e);
            }

            Viaje viaje = viajeDAO.obtenerViajePorCodigo(viajeId);
            viaje.setBus(bus);
            viaje.setRuta(ruta);
            viaje.setFecha(fechaViaje);
            viaje.setHoraDeSalida(horaSalida);
            viaje.setJornada(jornada);
            viaje.setConductor(conductor);

            viajeDAO.actualizarViajeEnDB(viaje);
            gestionarViajes(req, resp);
        } catch (NumberFormatException e) {
            throw new ServletException("Error al convertir los parámetros numéricos: " + e.getMessage(), e);
        } catch (Exception e) {
            throw new ServletException("Error al actualizar el viaje: " + e.getMessage(), e);
        }
    }


    private void mostrarConductores(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException{

        req.setAttribute("conductores", conductorDAO.obtenerConductores() );
        RequestDispatcher dispatcher = req.getRequestDispatcher("/View/Administrador/gestionConductor.jsp");
        dispatcher.forward(req, resp);
    }

    public void eliminarConductor(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException{
        try{
            String conductorId = (req.getParameter("conductorId"));
            conductorDAO.eliminarConductorDb(conductorId);
            req.setAttribute("conductores", conductorDAO.obtenerConductores() );
            RequestDispatcher dispatcher = req.getRequestDispatcher("/View/Administrador/gestionConductor.jsp");
            dispatcher.forward(req, resp);
        } catch (Exception e) {
            req.setAttribute("errorMessage", e.getMessage());
            req.setAttribute("conductores", conductorDAO.obtenerConductores() );
            RequestDispatcher dispatcher = req.getRequestDispatcher("/View/Administrador/gestionConductor.jsp");
            dispatcher.forward(req, resp);
        }


    }
    public void mostrarFormConductor(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException{
        RequestDispatcher dispatcher = req.getRequestDispatcher("/View/Administrador/registrarConductor.jsp");
        dispatcher.forward(req, resp);
    }
    public void guardarConductor(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException{
        String nombre = req.getParameter("nombre");
        String apellido = req.getParameter("apellido");
        String email = req.getParameter("email");
        String contrasena = req.getParameter("contrasena");
        String telefono = req.getParameter("telefono");
        Conductor nuevoConductor = new Conductor(0, nombre, apellido, email, telefono, contrasena);
        conductorDAO.guardarConductorDb(nuevoConductor);
        req.setAttribute("conductores", conductorDAO.obtenerConductores() );
        RequestDispatcher dispatcher = req.getRequestDispatcher("/View/Administrador/gestionConductor.jsp");
        dispatcher.forward(req, resp);
    }

    private void mostrarBuses(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException{
        req.setAttribute("buses", busDAO.obtenerTodosLosBuses() );
        RequestDispatcher dispatcher = req.getRequestDispatcher("/View/Administrador/gestionBuses.jsp");
        dispatcher.forward(req, resp);
    }

    public void eliminarBus(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException{
        try{
            busDAO.eliminarBusEnDB(req.getParameter("busId"));
            req.setAttribute("buses", busDAO.obtenerTodosLosBuses() );
            RequestDispatcher dispatcher = req.getRequestDispatcher("/View/Administrador/gestionBuses.jsp");
            dispatcher.forward(req,resp);
        }
        catch (Exception e){
            req.setAttribute("errorMessage", e.getMessage());
            req.setAttribute("buses", busDAO.obtenerTodosLosBuses() );
            RequestDispatcher dispatcher = req.getRequestDispatcher("/View/Administrador/gestionBuses.jsp");
            dispatcher.forward(req,resp);
        }


    }
    private void mostrarFormBus(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException{
        List<Usuario> conductores = conductorDAO.obtenerConductores();
        req.setAttribute("conductores", conductores );
        RequestDispatcher dispatcher = req.getRequestDispatcher("/View/Administrador/registrarBus.jsp");
        dispatcher.forward(req, resp);
    }
    public void guardarBus(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException{
        String busIdStr = req.getParameter("busId");
        int capacidad = Integer.parseInt(req.getParameter("capacidad"));
        try{
            Bus nuevoBus = new Bus(busIdStr,capacidad);
            busDAO.crearBusEnDB(nuevoBus);
            req.setAttribute("buses", busDAO.obtenerTodosLosBuses() );
            RequestDispatcher dispatcher = req.getRequestDispatcher("/View/Administrador/gestionBuses.jsp");
            dispatcher.forward(req,resp);
        } catch (Exception e) {
            req.setAttribute("errorMessage", e.getMessage());
            req.setAttribute("buses", busDAO.obtenerTodosLosBuses() );
            RequestDispatcher dispatcher = req.getRequestDispatcher("/View/Administrador/gestionBuses.jsp");
            dispatcher.forward(req,resp);
        }


    }
    public void mostrarFormActualizarBus(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException{
        String busIdStr = req.getParameter("busId");

        req.setAttribute("bus", busDAO.obtenerBusPorId(busIdStr));
        req.setAttribute("conductores", conductorDAO.obtenerConductores());
        RequestDispatcher dispatcher = req.getRequestDispatcher("/View/Administrador/actualizarBus.jsp");
        dispatcher.forward(req,resp);
    }
    public void actualizarBus(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException{
        String busIdStr = req.getParameter("busId");
        int capacidad = Integer.parseInt(req.getParameter("capacidad"));
        Bus bus =  busDAO.obtenerBusPorId(busIdStr);
        bus.setCapacidad(capacidad);
        busDAO.actualizarBusDb(bus);
        req.setAttribute("buses", busDAO.obtenerTodosLosBuses() );
        RequestDispatcher dispatcher = req.getRequestDispatcher("/View/Administrador/gestionBuses.jsp");
        dispatcher.forward(req,resp);
    }

    private void forward(HttpServletRequest req, HttpServletResponse resp, String path) throws ServletException, IOException {
        RequestDispatcher dispatcher = req.getRequestDispatcher(path);
        dispatcher.forward(req, resp);
    }













}
