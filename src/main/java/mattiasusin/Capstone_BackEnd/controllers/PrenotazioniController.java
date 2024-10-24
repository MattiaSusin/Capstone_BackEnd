package mattiasusin.Capstone_BackEnd.controllers;


import mattiasusin.Capstone_BackEnd.entities.Prenotazione;
import mattiasusin.Capstone_BackEnd.payloads.prenotazione.PrenotazioneDTO;
import mattiasusin.Capstone_BackEnd.payloads.prenotazione.PrenotazioneRespDTO;
import mattiasusin.Capstone_BackEnd.services.PrenotazioniService;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/prenotazioni")
@CrossOrigin(origins = {"http://localhost:5173/"})
public class PrenotazioniController {

    // IMPORTI
    @Autowired
    private PrenotazioniService prenotazioniService;

    // METODI

    // 1 --> GET ALL

    @GetMapping
    @PreAuthorize("hasAuthority('ADMIN')")
    public List<Prenotazione> findAllPrenotazione(@RequestParam(defaultValue = "id") String sortBy) {
        return this.prenotazioniService.findAllPrenotazione(sortBy);
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

    // 6 --> FIND BY NOME

    @PreAuthorize("hasAuthority('ADMIN')")
    @GetMapping("/cercaNome")
    public List<Prenotazione> findByNomePrenotazione(@RequestParam String nome) {
        return prenotazioniService.findByNomePrenotazione(nome);
    }

    // 7 --> FIND BY EMAIL --> prenotazioneRepository


    // 8 --> FIND BY COGNOME

    @PreAuthorize("hasAuthority('ADMIN')")
    @GetMapping("/cercaCognome")
    public List<Prenotazione> findByCognomePrenotazione(@RequestParam String cognome) {
        return prenotazioniService.findByCognomePrenotazione(cognome);
    }

    // 9 --> FIND BY DATA
    @PreAuthorize("hasAuthority('ADMIN')")
    @GetMapping("/data/{data}")
    public List<Prenotazione> getPrenotazioniByData(@PathVariable String data) {
        LocalDate localDate = LocalDate.parse(data);
        return prenotazioniService.findByDataPrenotazione(localDate);
    }

    // 10 --> FIND BY TELEFONO

    @PreAuthorize("hasAuthority('ADMIN')")
    @GetMapping("/telefono")
    public List<Prenotazione> findByTelefonoPrenotazione(@RequestParam String telefono) {
        return prenotazioniService.findByTelefonoPrenotazione(telefono);
    }



}
