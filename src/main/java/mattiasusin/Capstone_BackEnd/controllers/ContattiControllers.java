package mattiasusin.Capstone_BackEnd.controllers;


import mattiasusin.Capstone_BackEnd.payloads.contatto.ContattoDTO;
import mattiasusin.Capstone_BackEnd.services.ContattiService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/contatti")
@CrossOrigin(origins = {"http://localhost:5173/"})
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
