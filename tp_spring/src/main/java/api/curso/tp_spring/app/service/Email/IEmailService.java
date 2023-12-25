package api.curso.tp_spring.app.service.Email;

public interface IEmailService {

    public void sendEmail(String to, String subject, String body);
    
}
