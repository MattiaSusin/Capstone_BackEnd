package mattiasusin.Capstone_BackEnd.controllers;


import mattiasusin.Capstone_BackEnd.entities.Drink;
import mattiasusin.Capstone_BackEnd.exceptions.BadRequestException;
import mattiasusin.Capstone_BackEnd.payloads.drink.DrinkDTO;
import mattiasusin.Capstone_BackEnd.payloads.drink.DrinkRespDTO;
import mattiasusin.Capstone_BackEnd.services.DrinksService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.UUID;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/drinks")
public class DrinksController {

    // IMPORTI

    @Autowired
    private DrinksService drinksService;

    // METODI

    // 1 --> GET ALL

    @GetMapping
    public Page<Drink> findAllDrink(@RequestParam(defaultValue = "0") int page,
                                    @RequestParam(defaultValue = "10") int size,
                                    @RequestParam(defaultValue = "id") String sortBy) {
        return this.drinksService.findAllDrink(page, size, sortBy);
    }


    // 2 --> POST

    @PostMapping("/crea")
    @PreAuthorize("hasAnyAuthority('ADMIN')")
    @ResponseStatus(HttpStatus.CREATED)
    public DrinkRespDTO save(@RequestBody @Validated DrinkDTO body, BindingResult validationResult) {

        if (validationResult.hasErrors()) {
            String messages = validationResult.getAllErrors().stream()
                    .map(objectError -> objectError.getDefaultMessage())
                    .collect(Collectors.joining(". "));

            throw new BadRequestException("Ci sono stati errori nel payload. " + messages);
        } else {
            return new DrinkRespDTO(this.drinksService.savedDrink(body).getId());
        }
    }

    // 3 --> GET ID

    @GetMapping("/{drinkId}")
    public Drink findByIdDrink(@PathVariable UUID drinkId) {
        return this.drinksService.findByIdDrink(drinkId);
    }

    // 4 --> PUT

    @PutMapping("/{drinkId}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public Drink findByIdAndUpdateDrink(@PathVariable UUID drinkId, @RequestBody Drink body) {
        return this.drinksService.findByIdAndUpdateDrink(drinkId, body);
    }

    // 5 --> DELETE

    @DeleteMapping("/{drinkId}")
    @PreAuthorize("hasAuthority('ADMIN')")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void findByIdAndDeleteMenu(@PathVariable UUID drinkId) {
        this.drinksService.findByIdAndDeleteDrink(drinkId);
    }

    // 6 --> UPLOAD CLOUDIARY

    @PostMapping("/{drinkId}/immagine")
    @PreAuthorize("hasAuthority('ADMIN')")
    public void uploadImage(@RequestParam("immagine") MultipartFile image, @PathVariable UUID drinkId) throws IOException {

        this.drinksService.uploadImage(image,drinkId);
    }

}
