package mattiasusin.Capstone_BackEnd.controllers;


import mattiasusin.Capstone_BackEnd.entities.Admin;
import mattiasusin.Capstone_BackEnd.services.AdminsService;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/admins")
public class AdminsController {

    // IMPORTI

    @Autowired
    private AdminsService adminsService;

    // METODI

    // 1 --> GET ALL

    @GetMapping
    public Page<Admin> findAll(@RequestParam(defaultValue = "0") int page,
                               @RequestParam(defaultValue = "10") int size,
                               @RequestParam(defaultValue = "id") String sortBy) {
        return this.adminsService.findAllAdmin(page, size, sortBy);
    }

    // 2 --> POST --> in AuthControllers

    // 3 --> GET ID
    @GetMapping("/{adminId}")
    public Admin findByIdAdmin(@PathVariable UUID adminId) {
        return this.adminsService.findByIdAdmin(adminId);
    }

    // 4 --> PUT
    @PutMapping("/{adminId}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public Admin findByIdAndUpdateAdmin(@PathVariable UUID adminId, @RequestBody Admin body) {
        return this.adminsService.findByIdAndUpdateAdmin(adminId, body);
    }
    // 5 --> DELETE


    @DeleteMapping("/{adminId}")
    @PreAuthorize("hasAuthority('ADMIN')")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void findByIdAndDeleteAdmin(@PathVariable UUID adminId) {
        this.adminsService.findByIdAndDeleteAdmin(adminId);
    }

    // 6 --> FIND BY EMAIL --> nella classe AdminsRepository


    // 7 --> SAVE --> nella classe AuthControllers

}
