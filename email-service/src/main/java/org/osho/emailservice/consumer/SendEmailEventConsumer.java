package org.osho.emailservice.consumer;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.osho.emailservice.dtos.SendEmailEventDto;
import org.osho.emailservice.utils.EmailUtil;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import javax.mail.Authenticator;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import java.util.Properties;

@Service
public class SendEmailEventConsumer {


    @Value("${email.password}")
    private String emailPassword;
    private ObjectMapper objectMapper;
    public SendEmailEventConsumer(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    @KafkaListener(topics = "sendEmail" , groupId = "email-service")
    public void consume(String message) {
        try {
            System.out.println("Received message: " + message);
            SendEmailEventDto emailDto = objectMapper.readValue(message, SendEmailEventDto.class);
            Properties props = new Properties();
            props.put("mail.smtp.host", "smtp.gmail.com"); //SMTP Host
            props.put("mail.smtp.port", "587"); //TLS Port
            props.put("mail.smtp.auth", "true"); //enable authentication
            props.put("mail.smtp.starttls.enable", "true"); //enable STARTTLS
            //create Authenticator object to pass in Session.getInstance argument
            Authenticator auth = new Authenticator() {
                //override the getPasswordAuthentication method
                @Override
                protected PasswordAuthentication getPasswordAuthentication() {
                    return new PasswordAuthentication("gbaba8790@gmail.com", emailPassword);
                }
            };
            Session session = Session.getInstance(props, auth);

            EmailUtil.sendEmail(session, emailDto.getTo(), emailDto.getSubject(), emailDto.getBody());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
