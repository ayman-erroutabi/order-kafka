package order.orderkafka.services.impl;

import order.orderkafka.services.EmailService;
import org.springframework.core.io.FileSystemResource;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.io.File;
import java.util.Date;

@Service
public class EmailServiceImpl implements EmailService {

    private final JavaMailSender mailSender;

    EmailServiceImpl(JavaMailSender mailSender){
        this.mailSender = mailSender;
    }

    @Override
    public void sendMail(String destinationMail, String subject, String message, File attachment) throws MessagingException {

        MimeMessage mimeMessage = this.mailSender.createMimeMessage();

        MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true);
        helper.setFrom("noreply@baeldung.com");
        helper.setTo(destinationMail);
        helper.setSubject(subject);
        helper.setText(message);

        FileSystemResource file = new FileSystemResource(attachment);
        String date = new Date().toString().replace(" ", "_");
        helper.addAttachment("Order_Invoice_" + date + ".pdf", file);

        this.mailSender.send(mimeMessage);
    }
}
