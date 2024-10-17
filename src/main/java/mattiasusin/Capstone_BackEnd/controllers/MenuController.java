package mattiasusin.Capstone_BackEnd.controllers;

import mattiasusin.Capstone_BackEnd.entities.Menu;
import mattiasusin.Capstone_BackEnd.enums.TipoPiatto;
import mattiasusin.Capstone_BackEnd.exceptions.BadRequestException;
import mattiasusin.Capstone_BackEnd.payloads.menu.MenuDTO;
import mattiasusin.Capstone_BackEnd.payloads.menu.MenuRespDTO;
import mattiasusin.Capstone_BackEnd.services.MenuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.UUID;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/menu")
@CrossOrigin(origins = {"http://localhost:5173/"})
public class MenuController {

    // IMPORTI

    @Autowired
    private MenuService menuService;

    // METODI

    // 1 --> GET ALL

    @GetMapping("view/food")
    public Page<Menu> findAllMenu(@RequestParam(defaultValue = "0") int page,
                                  @RequestParam(defaultValue = "100") int size,
                                  @RequestParam(defaultValue = "id") String sortBy) {
        return this.menuService.findAllMenu(page, size, sortBy);
    }


    // 2 --> POST

    @PostMapping("/crea")
    @PreAuthorize("hasAnyAuthority('ADMIN')")
    @ResponseStatus(HttpStatus.CREATED)
    public MenuRespDTO save(@RequestBody @Validated MenuDTO body, BindingResult validationResult) {

        if (validationResult.hasErrors()) {
            String messages = validationResult.getAllErrors().stream()
                    .map(objectError -> objectError.getDefaultMessage())
                    .collect(Collectors.joining(". "));

            throw new BadRequestException("Ci sono stati errori nel payload. " + messages);
        } else {
            return new MenuRespDTO(this.menuService.savedMenu(body).getId());
        }
    }


    // 3 --> GET ID

    @GetMapping("/{menuId}")
    public Menu findByIdMenu(@PathVariable UUID menuId) {
        return this.menuService.findByIdMenu(menuId);
    }

    // 4 --> PUT

    @PutMapping("/{menuId}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public Menu findByIdAndUpdateMenu(@PathVariable UUID menuId, @RequestBody Menu body) {
        return this.menuService.findByIdAndUpdateMenu(menuId, body);
    }

    // 5 --> DELETE

    @DeleteMapping("/{menuId}")
    @PreAuthorize("hasAuthority('ADMIN')")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void findByIdAndDeleteMenu(@PathVariable UUID menuId) {
        this.menuService.findByIdAndDeleteMenu(menuId);
    }

    // 6 --> UPLOAD CLOUDIARY

    @PostMapping("/{menuId}/immagine")
    @PreAuthorize("hasAuthority('ADMIN')")
    public void uploadImage(@RequestParam("immagine") MultipartFile image,@PathVariable UUID menuId) throws IOException {

        this.menuService.uploadImage(image,menuId);
    }



    // 7 --> FIND BY TIPO PIATTO

    @GetMapping("/filtro")
    public ResponseEntity<Page<Menu>> filterMenuByTipoDrink(@RequestParam TipoPiatto tipoPiatto,
                                                             @RequestParam(defaultValue = "0") int page,
                                                             @RequestParam(defaultValue = "6") int size,
                                                             @RequestParam(defaultValue = "id") String sortBy) {

        Page<Menu> filterMenu = this.menuService.findByTipoPiatto(tipoPiatto, page, size, sortBy);
        return new ResponseEntity<>(filterMenu, HttpStatus.OK);
    }

}
