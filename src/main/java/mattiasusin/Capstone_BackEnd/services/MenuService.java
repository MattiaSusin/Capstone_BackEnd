package mattiasusin.Capstone_BackEnd.services;


import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import mattia.susin.CAPBACK.entities.Menu;
import mattia.susin.CAPBACK.enums.TipoPiatto;
import mattia.susin.CAPBACK.exceptions.NotFoundException;
import mattia.susin.CAPBACK.payloads.menu.MenuDTO;
import mattia.susin.CAPBACK.repositories.MenuRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.UUID;

@Service
public class MenuService {

    // IMPORTI

    @Autowired
    private MenuRepository menuRepository;

    @Autowired
    private PasswordEncoder bcrypt;

    @Autowired
    private Cloudinary cloudinaryUploader;

    // METODI

    // 1 --> GET ALL

    public Page<Menu> findAllMenu(int page, int size, String sortBy) {
        if (page > 100) page = 100;

        Pageable pageable = PageRequest.of(page, size, Sort.by(sortBy));
        return this.menuRepository.findAll(pageable);
    }

    // 2 --> POST/SAVE

    public Menu savedMenu(MenuDTO body){

        // 2 --> Se va tutto bene aggiungo i campi 'server-generated' ovvero l'avatarUrl

        TipoPiatto tipoPiatto = TipoPiatto.valueOf(body.tipoPiatto());
        Menu newMenu = new Menu(body.titolo(),body.descrizione(),body.prezzo(),body.tipoPiatto(),body.immagine());

        Menu savedMenu = this.menuRepository.save(newMenu);

        return savedMenu;
    }

    // 3 --> GET ID

    public Menu findByIdMenu(UUID menuId) {
        return this.menuRepository.findById(menuId).orElseThrow(() -> new NotFoundException(menuId));
    }

    // 4 --> PUT

    public Menu findByIdAndUpdateMenu(UUID menuId, Menu newMenuData){


        Menu found = this.findByIdMenu(menuId);

        found.setTitolo(newMenuData.getTitolo());
        found.setDescrizione(newMenuData.getDescrizione());
        found.setPrezzo(newMenuData.getPrezzo());
        found.setTipoPiatto(newMenuData.getTipoPiatto());

        return this.menuRepository.save(found);

    }

    // 5 --> DELETE

    public void findByIdAndDeleteMenu(UUID menuId) {
        Menu found = this.findByIdMenu(menuId);
        this.menuRepository.delete(found);
    }


    // 6 --> UPLOAD CLOUDIARY

    public void uploadImage(MultipartFile file,UUID menuId) throws IOException {
        String url = (String) cloudinaryUploader.uploader().upload(file.getBytes(), ObjectUtils.emptyMap()).get("url");
        System.out.println("URL: " + url);
        Menu menu = menuRepository.findById(menuId).orElseThrow(() -> new NotFoundException(menuId));
        menu.setImmagine(url);

        menuRepository.save(menu);
    }

}
