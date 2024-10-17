package mattiasusin.Capstone_BackEnd.repositories;


import mattiasusin.Capstone_BackEnd.entities.Menu;
import mattiasusin.Capstone_BackEnd.enums.TipoPiatto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;


import java.util.UUID;

public interface MenuRepository extends JpaRepository<Menu, UUID> {

    Page<Menu> findAll(Pageable pageable);

    Page<Menu> findByTipoPiatto(TipoPiatto tipoPiatto, Pageable pageable);

}
