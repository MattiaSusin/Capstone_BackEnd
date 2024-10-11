package mattiasusin.Capstone_BackEnd.controllers;

import mattia.susin.CAPBACK.exceptions.BadRequestException;
import mattia.susin.CAPBACK.payloads.admin.AdminDTO;
import mattia.susin.CAPBACK.payloads.admin.AdminLoginDTO;
import mattia.susin.CAPBACK.payloads.admin.AdminLoginRespDTO;
import mattia.susin.CAPBACK.payloads.admin.AdminRespDTO;
import mattia.susin.CAPBACK.payloads.prenotazione.PrenotazioneDTO;
import mattia.susin.CAPBACK.payloads.prenotazione.PrenotazioneRespDTO;
import mattia.susin.CAPBACK.services.AdminsService;
import mattia.susin.CAPBACK.services.AuthService;
import mattia.susin.CAPBACK.services.PrenotazioniService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.stream.Collectors;

@RestController
@RequestMapping("/auth")
public class AuthControllers {

    // IMPORTI

    @Autowired
    private AdminsService adminsService;
    @Autowired
    private AuthService authService;
    @Autowired
    private PrenotazioniService prenotazioniService;

    // METODI

    // 1 --> LOGIN

    @PostMapping("/login")
    public AdminLoginRespDTO login(@RequestBody AdminLoginDTO loginDTO) {
        return new AdminLoginRespDTO(this.authService.checkCredentialsAndGenerateToken(loginDTO));
    }

    // 2 --> SAVE/REGISTER --> ADMIN

    @PostMapping("/register")
    @ResponseStatus(HttpStatus.CREATED)
    public AdminRespDTO save(@RequestBody @Validated AdminDTO body, BindingResult validationResult) {
        // @Validated serve per 'attivare' le regole di validazione descritte nel DTO
        // BindingResult mi permette di capire se ci sono stati errori e quali errori ci sono stati

        if (validationResult.hasErrors()) {
            // Se ci sono stati errori lanciamo un'eccezione custom
            String messages = validationResult.getAllErrors().stream()
                    .map(objectError -> objectError.getDefaultMessage())
                    .collect(Collectors.joining(". "));

            throw new BadRequestException("Ci sono stati errori nel payload. " + messages);
        } else {
            // Se non ci sono stati salviamo l'utente

            return new AdminRespDTO(this.adminsService.save(body).getId());
        }

    }


    // CREAZIONE PRENOTAZIONE

    @PostMapping("/crea")
    @ResponseStatus(HttpStatus.CREATED)
    public PrenotazioneRespDTO save(@RequestBody @Validated PrenotazioneDTO body, BindingResult validationResult) {

        if (validationResult.hasErrors()) {
            String messages = validationResult.getAllErrors().stream()
                    .map(objectError -> objectError.getDefaultMessage())
                    .collect(Collectors.joining(". "));

            throw new BadRequestException("Ci sono stati errori nel payload. " + messages);
        } else {
            return new PrenotazioneRespDTO(this.prenotazioniService.save(body).getId());
        }
    }
}
