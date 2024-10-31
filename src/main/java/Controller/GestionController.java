package Controller;

import Model.DAO.*;
import Model.Entity.*;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.sql.Date;
import java.sql.Time;
import java.util.ArrayList;
import java.util.List;

@WebServlet(name = "GestionServlet", value = "/GestionServlet")
public class GestionController extends HttpServlet {
    RutaDAO rutaDAO ;
    CalleDAO calleDAO ;
    ViajeDAO viajeDAO;
    BusDAO busDAO;
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
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        this.ruteador(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        this.ruteador(req, resp);
    }


    public void ruteador(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
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

            default:
                break;
        }
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
            String idUsuario = usuarioDAO.obtenerIdUsuario(email); // Obtener el ID del usuario

            if ("U_Administrador".equals(tipoUsuario)) {
                resp.sendRedirect(req.getContextPath() + "/View/dashboardAdmin.jsp");
            } else if ("U_Estudiante".equals(tipoUsuario)) {
                resp.sendRedirect(req.getContextPath() + "/View/listarViajes.jsp");
            } else if ("U_Conductor".equals(tipoUsuario)) {
                req.getSession().setAttribute("conductorId", idUsuario); // Guardar el ID del conductor en la sesión
                resp.sendRedirect(req.getContextPath() + "/View/dashboardConductor.jsp");
            }
        } else {
            req.setAttribute("error", "Credenciales inválidas.");
            RequestDispatcher dispatcher = req.getRequestDispatcher("/View/login.jsp");
            dispatcher.forward(req, resp);
        }
    }


    public void mostrarDashboardAdmin(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        RequestDispatcher dispatcher = req.getRequestDispatcher("/View/dashboardAdmin.jsp");
        dispatcher.forward(req, resp);
    }

    public void gestionarRutas(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        req.setAttribute("rutas", rutaDAO.obtenerTodasLasRutas());
        RequestDispatcher dispatcher = req.getRequestDispatcher("/View/gestionRutas.jsp");
        dispatcher.forward(req, resp);
    }
    public void gestionarReservas(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setAttribute("reservas", reservaDAO.obtenerTodasLasReservas());
        RequestDispatcher dispatcher = req.getRequestDispatcher("/View/gestionReservas.jsp");
        dispatcher.forward(req, resp);
    }
    public void cancelarReservas(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        reservaDAO.cancelarReserva(Integer.parseInt(req.getParameter("reservaId")),
                reservaDAO.obtenerReservaPorId(Integer.parseInt(req.getParameter("reservaId"))).getViaje());
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
        RequestDispatcher dispatcher = req.getRequestDispatcher("/View/gestionReservas.jsp");
        dispatcher.forward(req, resp);
    }
    public void formNuevaReserva(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setAttribute("estudiantes",estudianteDAO.obtenerEstudiantes());
        req.setAttribute("viajes", viajeDAO.obtenerTodosLosViajes());
        RequestDispatcher dispatcher = req.getRequestDispatcher("/View/registrarReserva.jsp");
        dispatcher.forward(req, resp);
    }
    public void mostrarFormRuta(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {


        req.setAttribute("calles", calleDAO.obtenerTodasLasCalles());
        RequestDispatcher dispatcher = req.getRequestDispatcher("/View/registrarRuta.jsp");
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
        RequestDispatcher dispatcher = req.getRequestDispatcher("/View/gestionRutas.jsp");
        dispatcher.forward(req, resp);

    }
    public void eliminarRuta(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        rutaDAO.eliminarRutaDb(Integer.parseInt(req.getParameter("rutaId")));
        req.setAttribute("rutas", rutaDAO.obtenerTodasLasRutas());
        RequestDispatcher dispatcher = req.getRequestDispatcher("/View/gestionRutas.jsp");
        dispatcher.forward(req, resp);

    }
    public void mostrarFormActualizarRuta(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        int rutaId = Integer.parseInt(req.getParameter("rutaId"));
        Ruta ruta = rutaDAO.obtenerRutaId(rutaId);
        req.setAttribute("ruta", ruta);
        RequestDispatcher dispatcher = req.getRequestDispatcher("/View/actualizarRuta.jsp");
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
        RequestDispatcher dispatcher = req.getRequestDispatcher("/View/gestionRutas.jsp");
        dispatcher.forward(req, resp);

    }

    // Métodos para gestionar viajes
    public void gestionarViajes(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setAttribute("viajes", viajeDAO.obtenerTodosLosViajes()); // Obtener todos los viajes
        RequestDispatcher dispatcher = req.getRequestDispatcher("/View/gestionViaje.jsp");
        dispatcher.forward(req, resp);
    }

    public void mostrarFormViaje(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setAttribute("buses", busDAO.obtenerTodosLosBuses());
        req.setAttribute("rutas", rutaDAO.obtenerTodasLasRutas());
        req.setAttribute("conductores", conductorDAO.obtenerConductores());
        RequestDispatcher dispatcher = req.getRequestDispatcher("/View/registrarViaje.jsp");
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
        viajeDAO.eliminarViajeEnDB(Integer.parseInt(req.getParameter("viajeId")));

        req.setAttribute("viajes", viajeDAO.obtenerTodosLosViajes());
        RequestDispatcher dispatcher = req.getRequestDispatcher("/View/gestionViaje.jsp");
        dispatcher.forward(req, resp);
    }


    private void mostrarFormActualizarViaje(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        int viajeId = Integer.parseInt(req.getParameter("viajeId"));
        Viaje viaje = viajeDAO.obtenerViajePorCodigo(viajeId);

        req.setAttribute("viaje", viaje);
        req.setAttribute("buses", busDAO.obtenerTodosLosBuses());
        req.setAttribute("rutas", rutaDAO.obtenerTodasLasRutas());
        req.setAttribute("conductores", conductorDAO.obtenerConductores());

        forward(req, resp, "/View/actualizarViaje.jsp");
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
        RequestDispatcher dispatcher = req.getRequestDispatcher("/View/gestionConductor.jsp");
        dispatcher.forward(req, resp);
    }

    public void eliminarConductor(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException{
        String conductorId = (req.getParameter("conductorId"));
        conductorDAO.eliminarConductorDb(conductorId);
        req.setAttribute("conductores", conductorDAO.obtenerConductores() );
        RequestDispatcher dispatcher = req.getRequestDispatcher("/View/gestionConductor.jsp");
        dispatcher.forward(req, resp);
    }
    public void mostrarFormConductor(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException{
        RequestDispatcher dispatcher = req.getRequestDispatcher("/View/registrarConductor.jsp");
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
        RequestDispatcher dispatcher = req.getRequestDispatcher("/View/gestionConductor.jsp");
        dispatcher.forward(req, resp);
    }

    private void mostrarBuses(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException{
        req.setAttribute("buses", busDAO.obtenerTodosLosBuses() );
        RequestDispatcher dispatcher = req.getRequestDispatcher("/View/gestionBuses.jsp");
        dispatcher.forward(req, resp);
    }

    public void eliminarBus(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException{
        String busId = req.getParameter("busId");
        busDAO.eliminarBusEnDB(busId);
        req.setAttribute("buses", busDAO.obtenerTodosLosBuses() );
        RequestDispatcher dispatcher = req.getRequestDispatcher("/View/gestionBuses.jsp");
        dispatcher.forward(req,resp);

    }
    private void mostrarFormBus(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException{
        List<Usuario> conductores = conductorDAO.obtenerConductores();
        req.setAttribute("conductores", conductores );
        RequestDispatcher dispatcher = req.getRequestDispatcher("/View/registrarBus.jsp");
        dispatcher.forward(req, resp);
    }
    public void guardarBus(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException{
        String busIdStr = req.getParameter("busId");
        int capacidad = Integer.parseInt(req.getParameter("capacidad"));
        Bus nuevoBus = new Bus(busIdStr,capacidad);
        busDAO.crearBusEnDB(nuevoBus);
        req.setAttribute("buses", busDAO.obtenerTodosLosBuses() );
        RequestDispatcher dispatcher = req.getRequestDispatcher("/View/gestionBuses.jsp");
        dispatcher.forward(req,resp);

    }
    public void mostrarFormActualizarBus(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException{
        String busIdStr = req.getParameter("busId");

        req.setAttribute("bus", busDAO.obtenerBusPorId(busIdStr));
        req.setAttribute("conductores", conductorDAO.obtenerConductores());
        RequestDispatcher dispatcher = req.getRequestDispatcher("/View/actualizarBus.jsp");
        dispatcher.forward(req,resp);
    }
    public void actualizarBus(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException{
        String busIdStr = req.getParameter("busId");
        int capacidad = Integer.parseInt(req.getParameter("capacidad"));
        Bus bus =  busDAO.obtenerBusPorId(busIdStr);
        bus.setCapacidad(capacidad);
        busDAO.actualizarBusDb(bus);
        req.setAttribute("buses", busDAO.obtenerTodosLosBuses() );
        RequestDispatcher dispatcher = req.getRequestDispatcher("/View/gestionBuses.jsp");
        dispatcher.forward(req,resp);
    }

    private void forward(HttpServletRequest req, HttpServletResponse resp, String path) throws ServletException, IOException {
        RequestDispatcher dispatcher = req.getRequestDispatcher(path);
        dispatcher.forward(req, resp);
    }













}
