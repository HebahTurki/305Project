package Network;

import Implementation.UserDistrubiter;
import com.sun.mail.smtp.SMTPTransport;

import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.activation.FileDataSource;
import javax.mail.*;
import javax.mail.internet.*;
import javax.swing.*;

import java.util.Properties;

import static Implementation.UserDistrubiter.getUser;

public class SendEmail {
    public SendEmail(){
        // SMTP server configuration
        String smtpHost = "smtp.gmail.com";
        String smtpPort = "587"; // Gmail SMTP port
        String username = "hebahturki04@gmail.com";
        String password = "huop odpo ydvh hrxh";

        // Sender and recipient email addresses
        String fromEmail = "hebahturki87@gmail.com";
        String toEmail = UserDistrubiter.getUser().getEmail() ;

        // Email properties
        Properties props = new Properties();
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.host", smtpHost);
        props.put("mail.smtp.port", smtpPort);

        // Authenticator for username and password
        Authenticator auth = new Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(username, password);
            }
        };

        // Creating a session with authentication
        Session session = Session.getInstance(props, auth);

        try {
            // Create a default MimeMessage object
            MimeMessage message = new MimeMessage(session);

            // Set From: header field
            message.setFrom(new InternetAddress(fromEmail));

            // Set To: header field
            message.addRecipient(Message.RecipientType.TO, new InternetAddress(toEmail));

            // Set Subject: header field
            message.setSubject("Expense Overview");

            // Create a multipart message
            Multipart multipart = new MimeMultipart();

            // Create the message body part
            BodyPart messageBodyPart = new MimeBodyPart();
            messageBodyPart.setText("Here is your expense overview. \n" +
                    "Thank you for using Muhfazatak App!");
            multipart.addBodyPart(messageBodyPart);

            // Attach the file
            String filename = "ExpenseOverview.pdf";
            DataSource source = new FileDataSource(filename);
            messageBodyPart = new MimeBodyPart();
            messageBodyPart.setDataHandler(new DataHandler(source));
            messageBodyPart.setFileName(filename);
            multipart.addBodyPart(messageBodyPart);

            // Set the multipart message content
            message.setContent(multipart);

            // Send the message
            Transport.send(message);

            System.out.println("Email with attachment sent successfully!");

        } catch (MessagingException e) {
            JOptionPane.showMessageDialog(null, "There is no Expense Overview file try to request it ");
        }
    }
}
