package mattiasusin.Capstone_BackEnd.controllers;


import mattiasusin.Capstone_BackEnd.entities.CopertiDisponibili;
import mattiasusin.Capstone_BackEnd.services.CopertiDisponibiliService;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/disponibili")
@CrossOrigin(origins = {"http://localhost:5173/"})
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
