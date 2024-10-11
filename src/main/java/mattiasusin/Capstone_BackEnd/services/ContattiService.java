package mattiasusin.Capstone_BackEnd.services;

import mattia.susin.CAPBACK.payloads.ContattoDTO;
import mattia.susin.CAPBACK.repositories.ContattiRepository;
import mattia.susin.CAPBACK.tools.MailgunSender;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ContattiService {

    @Autowired
    private ContattiRepository contattiRepository;

    @Autowired
    private MailgunSender mailgunSender;

    //METODI

    // 1 --> POST

    public String sendEmail(ContattoDTO body) {
        String bodyEmail = "Nome: " + body.nome() + "" + "Cognome: " + body.cognome() + "" +
                "Messaggio: " + body.messaggio();

        String subject = "Assistenza";
        mailgunSender.sendContattiEmail(body.email(),subject, bodyEmail);
        return "Email inviata all'azienda";
    }


}
