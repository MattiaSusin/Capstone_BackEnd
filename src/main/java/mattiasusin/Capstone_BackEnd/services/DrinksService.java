package mattiasusin.Capstone_BackEnd.services;


import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import mattiasusin.Capstone_BackEnd.entities.Drink;
import mattiasusin.Capstone_BackEnd.entities.Menu;
import mattiasusin.Capstone_BackEnd.enums.TipoDrink;
import mattiasusin.Capstone_BackEnd.enums.TipoPiatto;
import mattiasusin.Capstone_BackEnd.exceptions.NotFoundException;
import mattiasusin.Capstone_BackEnd.payloads.drink.DrinkDTO;
import mattiasusin.Capstone_BackEnd.repositories.DrinksRepository;
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
public class DrinksService {
    // IMPORTI

    @Autowired
    private DrinksRepository drinksRepository;

    @Autowired
    private PasswordEncoder bcrypt;

    @Autowired
    private Cloudinary cloudinaryUploader;

    // METODI

    // 1 --> GET ALL

    public Page<Drink> findAllDrink(int page, int size, String sortBy) {
        if (page > 100) page = 100;

        Pageable pageable = PageRequest.of(page, size, Sort.by(sortBy));
        return this.drinksRepository.findAll(pageable);
    }

    // 2 --> POST/SAVE

    public Drink savedDrink(DrinkDTO body){

        TipoDrink tipoDrink = TipoDrink.valueOf(body.tipoDrink());
        Drink newDrink = new Drink(body.titolo(),body.descrizione(),body.prezzo(),body.tipoDrink(),body.immagine());

        Drink savedDrink = this.drinksRepository.save(newDrink);

        return savedDrink;
    }


    // 3 --> GET ID

    public Drink findByIdDrink(UUID menuId) {
        return this.drinksRepository.findById(menuId).orElseThrow(() -> new NotFoundException(menuId));
    }

    // 4 --> PUT

    public Drink findByIdAndUpdateDrink(UUID drinkId, Drink newDrinkData){

        Drink found = this.findByIdDrink(drinkId);

        found.setTitolo(newDrinkData.getTitolo());
        found.setDescrizione(newDrinkData.getDescrizione());
        found.setPrezzo(newDrinkData.getPrezzo());
        found.setTipoDrink(newDrinkData.getTipoDrink());

        return this.drinksRepository.save(found);

    }

    // 5 --> DELETE

    public void findByIdAndDeleteDrink(UUID drinkId) {
        Drink found = this.findByIdDrink(drinkId);
        this.drinksRepository.delete(found);
    }

    // 6 --> UPLOAD CLOUDIARY

    public void uploadImage(MultipartFile file, UUID drinkId) throws IOException {
        String url = (String) cloudinaryUploader.uploader().upload(file.getBytes(), ObjectUtils.emptyMap()).get("url");
        System.out.println("URL: " + url);
        Drink drink = drinksRepository.findById(drinkId).orElseThrow(() -> new NotFoundException(drinkId));
        drink.setImmagine(url);

        drinksRepository.save(drink);
    }


    // 7 --> FIND BY TIPO PIATTO

    public Page<Drink> findByTipoDrink(TipoDrink tipoDrink, int page, int size, String sortBy) {

        Pageable pageable = PageRequest.of(page, size, Sort.by(sortBy));

        return this.drinksRepository.findByTipoDrink(tipoDrink, pageable);
    }

}
