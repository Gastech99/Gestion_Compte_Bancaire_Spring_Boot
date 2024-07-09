package com.gescompte.services;

import com.gescompte.dto.CompteBancaireDto;
import com.gescompte.entities.CompteBancaire;
import com.gescompte.entities.CompteCourant;
import com.gescompte.entities.CompteEpargne;

import java.util.List;

public interface CompteService {
    void createAccount(CompteBancaireDto compteBancaireDto);

    List<CompteEpargne> findComptesEpargne();
    List<CompteCourant> findComptesCourant();
    CompteBancaire findOne(String numCompte);
}
