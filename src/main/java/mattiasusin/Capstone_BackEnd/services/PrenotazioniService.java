package mattiasusin.Capstone_BackEnd.services;

import mattia.susin.CAPBACK.entities.CopertiDisponibili;
import mattia.susin.CAPBACK.entities.Prenotazione;
import mattia.susin.CAPBACK.exceptions.BadRequestException;
import mattia.susin.CAPBACK.exceptions.NotFoundException;
import mattia.susin.CAPBACK.payloads.prenotazione.PrenotazioneDTO;
import mattia.susin.CAPBACK.payloads.prenotazione.PrenotazioneRespDTO;
import mattia.susin.CAPBACK.repositories.CopertiDisponibiliRepository;
import mattia.susin.CAPBACK.repositories.PrenotazioneRepository;
import mattia.susin.CAPBACK.tools.MailgunSender;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@Service
public class PrenotazioniService {

    // IMPORTI
    @Autowired
    private PrenotazioneRepository prenotazioneRepository;

    @Autowired
    private PasswordEncoder bcrypt;

    @Autowired
    private MailgunSender mailgunSender;

    @Autowired
    CopertiDisponibiliRepository copertiDisponibiliRepository;

    // METODI

    // 1 --> GET ALL
    public Page<Prenotazione> findAllPrenotazione(int page, int size, String sortBy) {
        if (page > 100) page = 100;

        Pageable pageable = PageRequest.of(page, size, Sort.by(sortBy));
        return this.prenotazioneRepository.findAll(pageable);
    }


    public List<Prenotazione> findByNome(String nome){
        return this.prenotazioneRepository.findByNome(nome);
    }

    public List<Prenotazione> findByCognome(String cognome){
        return this.prenotazioneRepository.findByCognome(cognome);
    }

    public List<Prenotazione> findByData(String data){
        return this.prenotazioneRepository.findByData(LocalDate.parse(data));
    }

    public List<Prenotazione> findByTelefono(String telefono){
        return this.prenotazioneRepository.findByTelefono(telefono);
    }


    // 2 --> POST

    public Prenotazione save(PrenotazioneDTO body) {
        Prenotazione newPrenotazione = new Prenotazione(
                body.nome(), body.cognome(), body.email(),
                body.telefono(), body.data(), body.numeroCoperti(), body.orario()
        );

        // 2.1 --> CREIAMO I POSTI DISPONIBLI
        CopertiDisponibili copertiDisponibili = copertiDisponibiliRepository
                .findByData(body.data())
                .orElseGet(() -> {
                    CopertiDisponibili newCoperti = new CopertiDisponibili();
                    newCoperti.setData(body.data());
                    newCoperti.setCopertiDisponibili(120); // Imposta il numero massimo
                    copertiDisponibiliRepository.save(newCoperti);
                    return newCoperti;
                });

        // 2.2 --> CONTROLLIAMO SE I COPERTI SONO SUFFICENTI
        if (copertiDisponibili.getCopertiDisponibili() < body.numeroCoperti()) {
            throw new BadRequestException("Coperti non sufficienti per il giorno selezionato.");
        }

        // 2.3 --> SCALIAMO I COPERTI DISPONIBILI
        copertiDisponibili.scalaCoperti(body.numeroCoperti());

        // 2.4 --> SALVIAMO I POSTI
        copertiDisponibiliRepository.save(copertiDisponibili);
        Prenotazione savedPrenotazione = this.prenotazioneRepository.save(newPrenotazione);

        // 2.5 --> INVIAMO LA MAIL DI CONFERMA DELLA PRENOTAZIONE
        mailgunSender.sendRegistrationEmailPrenotazione(savedPrenotazione);

        return savedPrenotazione;
    }

    // 3 --> GET ID
    public Prenotazione findIdPrenotazione(UUID clienteId) {
        return prenotazioneRepository.findById(clienteId)
                .orElseThrow(() -> new NotFoundException("Cliente con id " + clienteId + " non trovato."));
    }

    // 4 --> DELETE
    public void findIdPrenotazioneAndDelete(UUID fattureId) {
        Prenotazione found = this.findIdPrenotazione(fattureId);
        try {
            this.prenotazioneRepository.delete(found);
        } catch (Exception e) {
            throw new BadRequestException("Errore nella cancellazione del cliente: " + e.getMessage());
        }
    }

    // 5 --> PUT
    public PrenotazioneRespDTO findIdAndUpdatePrenotazione(UUID prenotazioneId, PrenotazioneDTO newPrenotazioneData){
        Prenotazione found = this.findIdPrenotazione(prenotazioneId);
        found.setNome(newPrenotazioneData.nome());
        found.setCognome(newPrenotazioneData.cognome());
        found.setEmail(newPrenotazioneData.email());
        found.setTelefono(newPrenotazioneData.telefono());
        found.setData(newPrenotazioneData.data());
        found.setOrario(Double.parseDouble(newPrenotazioneData.orario()));
        found.getNumeroCoperti(newPrenotazioneData.numeroCoperti());

        return new PrenotazioneRespDTO(this.prenotazioneRepository.save(found).getId());
    }


    // 7 --> EMAIL
    public Prenotazione findByEmail(String email) {
        return prenotazioneRepository.findByEmail(email).orElseThrow(() -> new NotFoundException("L'utente con l'email " + email + " non è stato trovato!"));
    }

}
