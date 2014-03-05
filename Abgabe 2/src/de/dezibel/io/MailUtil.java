package de.dezibel.io;

import de.dezibel.ErrorCode;
import java.util.Properties;
import javax.mail.Address;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

/**
 * Utilityclass for sending emails.
 * @author Richard
 */
public abstract class MailUtil {

    /**
     * Send a mail with the given parameters.
     *
     * @param subject The subject of the mail
     * @param text The text to send
     * @param recipient The recipient
     * @return Returns
     * <p>
     * ErrorCode.SUCCESS</p> if there are no errors. Otherwise a fitting
     * ErrorCodde will be returned.
     */
    public static ErrorCode sendMail(String subject, String text, String recipient) {
        try {
            String host = "smtp.strato.de";
            int port = 465;
            String user = "info@dezibel-music.de";
            String pass = "dezibel2014";

            Properties props = new Properties();
            props.put("mail.smtp.auth", "true");
            props.put("mail.smtp.socketFactory.port", "465");
            props.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");

            Session session = Session.getDefaultInstance(props);
            Transport transport = session.getTransport("smtp");
            transport.connect(host, port, user, pass);

            Address[] addresses = InternetAddress.parse(recipient);
            Message message=new MimeMessage(session);
            message.setFrom(new InternetAddress(user));
            message.setRecipients(Message.RecipientType.TO, addresses);
            message.setSubject(subject);
            message.setText(text);
            
            transport.sendMessage(message, addresses);
            transport.close();
            
            return ErrorCode.SUCCESS;
        } catch (MessagingException ex) {
            ex.printStackTrace();
            return ErrorCode.MAILING_ERROR;
        }
    }

}
