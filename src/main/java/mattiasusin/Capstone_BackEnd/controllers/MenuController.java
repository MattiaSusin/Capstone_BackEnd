package mattiasusin.Capstone_BackEnd.controllers;

import mattia.susin.CAPBACK.entities.Menu;
import mattia.susin.CAPBACK.exceptions.BadRequestException;
import mattia.susin.CAPBACK.payloads.menu.MenuDTO;
import mattia.susin.CAPBACK.payloads.menu.MenuRespDTO;
import mattia.susin.CAPBACK.services.MenuService;
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
@RequestMapping("/menu")
public class MenuController {

    // IMPORTI

    @Autowired
    private MenuService menuService;

    // METODI

    // 1 --> GET ALL

    @GetMapping("view/food")
    public Page<Menu> findAllMenu(@RequestParam(defaultValue = "0") int page,
                                  @RequestParam(defaultValue = "10") int size,
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

}
