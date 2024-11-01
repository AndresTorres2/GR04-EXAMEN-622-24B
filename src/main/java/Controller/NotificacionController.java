package Controller;

import Model.DAO.EmailDAO;
import jakarta.mail.MessagingException;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

@WebServlet(name = "NotificacionServlet", value = "/NotificacionServlet")
public class NotificacionController extends HttpServlet {
    EmailDAO emailDAO;
    @Override
    public void init() throws ServletException {
        super.init();
        emailDAO = new EmailDAO();
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
        String ruta = (req.getParameter("action") == null) ? "notificar" : req.getParameter("action");
        switch (ruta) {
            case "notificar":
                notificar(req, resp);
                break;
            default:
                break;
        }
    }
    public void notificar(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String destinatario = req.getParameter("destinatario");
        String asunto = req.getParameter("asunto");
        String mensaje = req.getParameter("mensaje");

        try {
            System.out.println("Enviadoo");
            System.out.println(destinatario);
            emailDAO.enviarCorreo(destinatario, asunto, mensaje);
            resp.getWriter().write("Notificación enviada exitosamente.");
        } catch (MessagingException e) {
            e.printStackTrace();
            resp.getWriter().write("Error al enviar la notificación.");
        }
    }


}
