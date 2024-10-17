package mattiasusin.Capstone_BackEnd.repositories;


import mattiasusin.Capstone_BackEnd.entities.Drink;
import mattiasusin.Capstone_BackEnd.enums.TipoDrink;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;


import java.util.UUID;

public interface DrinksRepository extends JpaRepository<Drink, UUID> {
    Page<Drink> findAll(Pageable pageable);

    Page<Drink> findByTipoDrink(TipoDrink tipoDrink, Pageable pageable);
}
