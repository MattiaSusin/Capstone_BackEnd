package mattiasusin.Capstone_BackEnd.repositories;

import mattia.susin.CAPBACK.entities.Prenotazione;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface PrenotazioneRepository extends JpaRepository<Prenotazione , UUID> {

    Optional<Prenotazione> findByEmail(String email);

    List<Prenotazione> findByNome(String nome);

    List<Prenotazione> findByCognome(String cognome);

    List<Prenotazione> findByData(LocalDate data);

    List<Prenotazione> findByTelefono(String telefono);

}

