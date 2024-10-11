package mattiasusin.Capstone_BackEnd.repositories;

import mattia.susin.CAPBACK.entities.Contatto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface ContattiRepository extends JpaRepository<Contatto, UUID> {
    Optional<Contatto> findById(UUID id);
}
