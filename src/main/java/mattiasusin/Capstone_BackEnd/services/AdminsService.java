package mattiasusin.Capstone_BackEnd.services;

import mattiasusin.Capstone_BackEnd.entities.Admin;
import mattiasusin.Capstone_BackEnd.exceptions.BadRequestException;
import mattiasusin.Capstone_BackEnd.exceptions.NotFoundException;
import mattiasusin.Capstone_BackEnd.payloads.admin.AdminDTO;
import mattiasusin.Capstone_BackEnd.repositories.AdminsRepository;
import mattiasusin.Capstone_BackEnd.tools.MailgunSender;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class AdminsService {

    // IMPORTI
    @Autowired
    private AdminsRepository adminsRepository;

    @Autowired
    private PasswordEncoder bcrypt;

    @Autowired
    private MailgunSender mailgunSender;


    // METODI


    // 1 --> GET ALL

    public Page<Admin> findAllAdmin(int page, int size, String sortBy) {
        if (page > 100) page = 100;

        Pageable pageable = PageRequest.of(page, size, Sort.by(sortBy));
        return this.adminsRepository.findAll(pageable);
    }


    // 2 --> POST --> In AuthServices


    // 3 --> GET ID
    public Admin findByIdAdmin(UUID adminId) {
        return this.adminsRepository.findById(adminId).orElseThrow(() ->
                new NotFoundException("Admin con ID " + adminId + " non trovato."));
    }

    // 4 --> PUT

    public Admin findByIdAndUpdateAdmin(UUID adminId, Admin newAdminData){

        // 1 -->  Si controlla se la mail inserita è già in uso
        this.adminsRepository.findByEmail(newAdminData.getEmail()).ifPresent(
                // 1.1 --> Se lo è mando un errore
                admin -> {
                    throw new BadRequestException("l'email " + newAdminData.getEmail() + " è già in uso");
                }
        );
        // 2 --> Mando i campi per il cambiamento dei dati

        Admin found = this.findByIdAdmin(adminId);

        found.setNome(newAdminData.getNome());
        found.setCognome(newAdminData.getCognome());
        found.setEmail(newAdminData.getEmail());
        found.setPassword(newAdminData.getPassword());
        found.setAvatarURL("https://ui-avatars.com/api/?name=" + newAdminData.getNome() + "+" + newAdminData.getCognome());

        return this.adminsRepository.save(found);

    }

    // 5 --> DELETE
    public void findByIdAndDeleteAdmin(UUID userId) {
       Admin found = this.findByIdAdmin(userId);
        this.adminsRepository.delete(found);
    }
    // 6 --> FIND BY EMAIL
    public Admin findByEmail(String email) {
        return adminsRepository.findByEmail(email).orElseThrow(() -> new NotFoundException("L'utente con l'email " + email + " non è stato trovato!"));
    }

    // 7 --> SAVE

    public Admin save(AdminDTO body){
        // 1 --> Verifichiamo che la mail non sia stata utilizzata
        this.adminsRepository.findByEmail(body.email()).ifPresent(
                user ->{
                    throw new BadRequestException("L'email " + body.email() + " l'email è già in uso");
                }
        );

        // 2 --> Se va tutto bene aggiungo i campi 'server-generated' ovvero l'avatarUrl

        Admin newAdmin = new Admin(body.nome(),body.cognome(),body.email(),bcrypt.encode(body.password()),
                "https://ui-avatars.com/api/?name=" + body.nome() + "+" + body.cognome());

        Admin savedAdmin = this.adminsRepository.save(newAdmin);


        return savedAdmin;
    }
}

