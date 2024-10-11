package mattiasusin.Capstone_BackEnd.controllers;

import mattia.susin.CAPBACK.payloads.ContattoDTO;
import mattia.susin.CAPBACK.services.ContattiService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/contatti")
public class ContattiControllers {

    // IMPORTI

    @Autowired
    private ContattiService contattiService;

    // METODI

    // 1 --> POST

    @PostMapping()
    public ResponseEntity<String> sendEmailToAzienda(
            @RequestBody ContattoDTO contattoRichiestaDTO) {
        String response = contattiService.sendEmail(contattoRichiestaDTO);
        if (response.startsWith("Email inviata all'azienda")) {
            return ResponseEntity.ok(response);
        } else {
            return ResponseEntity.badRequest().body(response);
        }
    }
}
