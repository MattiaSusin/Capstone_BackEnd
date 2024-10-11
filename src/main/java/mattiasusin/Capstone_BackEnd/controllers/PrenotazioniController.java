package mattiasusin.Capstone_BackEnd.controllers;

import mattia.susin.CAPBACK.entities.Prenotazione;
import mattia.susin.CAPBACK.payloads.prenotazione.PrenotazioneDTO;
import mattia.susin.CAPBACK.payloads.prenotazione.PrenotazioneRespDTO;
import mattia.susin.CAPBACK.services.PrenotazioniService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/prenotazioni")
public class PrenotazioniController {

    // IMPORTI
    @Autowired
    private PrenotazioniService prenotazioniService;

    // METODI

    // 1 --> GET ALL

    @PreAuthorize("hasAuthority('ADMIN')")
    @GetMapping
    public Page<Prenotazione> findAllPrenotazione(@RequestParam(defaultValue = "0") int page,
                                             @RequestParam(defaultValue = "10") int size,
                                             @RequestParam(defaultValue = "id") String sortBy) {
        return this.prenotazioniService.findAllPrenotazione(page, size, sortBy);
    }

    // 2 --> POST/SAVE --> AuthControllers


    // 3 --> GET ID
    @GetMapping("/{prenotazioneId}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public Prenotazione findIdPrenotazione(@PathVariable UUID prenotazioneId) {
        return this.prenotazioniService.findIdPrenotazione(prenotazioneId);
    }

    // 4 --> DELETE
    @DeleteMapping("/{prenotazioneId}")
    @PreAuthorize("hasAuthority('ADMIN')")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void findIdClienteAndDelete(@PathVariable UUID prenotazioneId) {
        this.prenotazioniService.findIdPrenotazioneAndDelete(prenotazioneId);
    }

    // 5 --> PUT

    @PutMapping("/prenotazioneId")
    @PreAuthorize("hasAuthority('ADMIN')")
    public PrenotazioneRespDTO findIdAndUpdatePrenotazione(@PathVariable UUID clienteId, @RequestBody @Validated PrenotazioneDTO body) {
        return this.prenotazioniService.findIdAndUpdatePrenotazione(clienteId, body);
    }

    // 7 --> FIND BY EMAIL --> prenotazioneRepository



}
