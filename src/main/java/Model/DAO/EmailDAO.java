package Model.DAO;

import jakarta.mail.Authenticator;
import jakarta.mail.Message;
import jakarta.mail.MessagingException;
import jakarta.mail.PasswordAuthentication;
import jakarta.mail.Session;
import jakarta.mail.Transport;
import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeMessage;

import java.util.Properties;

public class EmailDAO {
    private static final String EMAIL_FROM = "polinombrepoliapellido@gmail.com";
    private static final String APP_PASSWORD = "uzil faou nwsr blhk";

    public void enviarCorreo(String destinatario, String asunto, String mensaje) throws MessagingException {
        if (destinatario == null || destinatario.isEmpty()) {
            throw new IllegalArgumentException("El destinatario no puede ser nulo o vacío");
        }
        Message emailMessage = new MimeMessage(getEmailSession());
        emailMessage.setFrom(new InternetAddress(EMAIL_FROM));
        emailMessage.setRecipients(Message.RecipientType.TO, InternetAddress.parse(destinatario));
        emailMessage.setSubject(asunto);
        emailMessage.setText(mensaje);

        Transport.send(emailMessage);
    }


    private Session getEmailSession() {
        return Session.getInstance(getGmailProperties(), new Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(EMAIL_FROM, APP_PASSWORD);
            }
        });
    }

    private Properties getGmailProperties() {
        Properties prop = new Properties();
        prop.put("mail.smtp.auth", "true");
        prop.put("mail.smtp.starttls.enable", "true");
        prop.put("mail.smtp.host", "smtp.gmail.com");
        prop.put("mail.smtp.port", "587");
        prop.put("mail.smtp.ssl.trust", "smtp.gmail.com");
        return prop;
    }
}
