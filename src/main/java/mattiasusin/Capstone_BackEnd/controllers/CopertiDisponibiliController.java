package mattiasusin.Capstone_BackEnd.controllers;

import mattia.susin.CAPBACK.entities.CopertiDisponibili;
import mattia.susin.CAPBACK.services.CopertiDisponibiliService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/disponibili")
public class CopertiDisponibiliController {

    //IMPORTI

    @Autowired
    private CopertiDisponibiliService copertiDisponibiliService;

    // METODI

    // 1 --> GET ALL

    @GetMapping
    public Page<CopertiDisponibili> findAllCoperti(@RequestParam(defaultValue = "0") int page,
                                                   @RequestParam(defaultValue = "10") int size,
                                                   @RequestParam(defaultValue = "id") String sortBy) {
        return this.copertiDisponibiliService.findAllCoperti(page, size, sortBy);
    }
}
