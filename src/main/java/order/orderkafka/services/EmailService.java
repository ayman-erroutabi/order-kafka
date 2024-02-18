package order.orderkafka.services;

import javax.mail.MessagingException;
import java.io.File;

public interface EmailService {
    void sendMail(String destinationMail, String subject, String message, File attachment ) throws MessagingException;
}
