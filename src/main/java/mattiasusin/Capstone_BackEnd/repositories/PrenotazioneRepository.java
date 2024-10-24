package mattiasusin.Capstone_BackEnd.repositories;


import mattiasusin.Capstone_BackEnd.entities.Prenotazione;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
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

    @Query("SELECT p FROM Prenotazione p WHERE " +
            "LOWER(p.nome) LIKE %:query% OR " +
            "LOWER(p.cognome) LIKE %:query% OR " +
            "LOWER(p.telefono) LIKE %:query% OR " +
            "LOWER(p.email) LIKE %:query%")
    List<Prenotazione> cercaGlobale(@Param("query") String query);
}

