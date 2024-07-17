package com.gescompte.services;

import com.gescompte.dto.OperationsDto;
import com.gescompte.entities.CompteBancaire;
import com.gescompte.entities.Operations;

import java.util.List;

public interface OperationsService {
    CompteBancaire effectuerVirement(String numCompte, double montant);

    List<Operations> findByClientNumCompte(String numCompte);

    CompteBancaire virementFromCompteAToCompteB(String numCompteSource, String numCompteDestination, double montant);

    void effectuerVersement(OperationsDto dto);

    void effectuerRetrait(OperationsDto dto);

    void effectuerVirement(OperationsDto dto);
}
