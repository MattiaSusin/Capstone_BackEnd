package mattiasusin.Capstone_BackEnd.services;


import mattiasusin.Capstone_BackEnd.entities.CopertiDisponibili;
import mattiasusin.Capstone_BackEnd.repositories.CopertiDisponibiliRepository;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

@Service
public class CopertiDisponibiliService {


    //IMPORTI

    @Autowired
    private CopertiDisponibiliRepository copertiDisponibiliRepository;

    // METODI

    // 1 --> GET ALL

    public Page<CopertiDisponibili> findAllCoperti(int page, int size, String sortBy) {
        if (page > 100) page = 100;

        Pageable pageable = PageRequest.of(page, size, Sort.by(sortBy));
        return this.copertiDisponibiliRepository.findAll(pageable);
    }
}
