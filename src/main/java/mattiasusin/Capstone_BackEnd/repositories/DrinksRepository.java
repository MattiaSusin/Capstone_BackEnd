package mattiasusin.Capstone_BackEnd.repositories;

import mattia.susin.CAPBACK.entities.Drink;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface DrinksRepository extends JpaRepository<Drink, UUID> {
    Page<Drink> findAll(Pageable pageable);
}
