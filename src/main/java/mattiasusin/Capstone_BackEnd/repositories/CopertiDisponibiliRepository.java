package mattiasusin.Capstone_BackEnd.repositories;

import mattia.susin.CAPBACK.entities.CopertiDisponibili;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.Optional;
import java.util.UUID;

public interface CopertiDisponibiliRepository extends JpaRepository<CopertiDisponibili,UUID> {
    Optional<CopertiDisponibili> findByData(LocalDate data);
}
