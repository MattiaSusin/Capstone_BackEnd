package mattiasusin.Capstone_BackEnd.services;


import mattiasusin.Capstone_BackEnd.entities.Admin;
import mattiasusin.Capstone_BackEnd.exceptions.UnauthorizedException;
import mattiasusin.Capstone_BackEnd.payloads.admin.AdminLoginDTO;
import mattiasusin.Capstone_BackEnd.payloads.prenotazione.PrenotazioneLoginDTO;
import mattiasusin.Capstone_BackEnd.security.JWTTools;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

    // IMPORTI

    @Autowired
    private AdminsService adminsService;

    @Autowired
    private JWTTools jwtTools;

    @Autowired
    private PasswordEncoder bcrypt;

    @Autowired
    private PrenotazioniService prenotazioniService;

    // Controllo per vedere dove si rompe il codice

    public AuthService() {
        System.out.println("AuthServices bean creato correttamente");
    }


    // METODI

    // ADMIN

    public String checkCredentialsAndGenerateToken(AdminLoginDTO body) {
        // 1. Controllo le credenziali
        // 1.1 Cerco nel db tramite email se esiste l'utente
        Admin found = this.adminsService.findByEmail(body.email());
        if (bcrypt.matches(body.password(), found.getPassword())) {
            // 1.2 Se lo trovo verifico se la pw trovata combacia con quella passataci tramite body
            // 2. Se è tutto ok --> genero un access token e lo restituisco
            return jwtTools.createToken(found);
        } else {
            // 3. Se le credenziali sono errate --> 401
            throw new UnauthorizedException("Credenziali errate!");
        }
        }


    // PRENOTAZIONE

    public String checkCredentialsAndGenerateToken(PrenotazioneLoginDTO body) {
        // 1. Controllo le credenziali
        // 1.1 Cerco nel db tramite email se esiste l'utente
        Admin found = this.adminsService.findByEmail(body.email());
        return jwtTools.createToken(found);
    }
}
