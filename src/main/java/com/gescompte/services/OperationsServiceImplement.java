package com.gescompte.services;

import com.gescompte.entities.CompteBancaire;
import com.gescompte.entities.Operations;
import com.gescompte.enums.AccountStatus;
import com.gescompte.enums.TypeOperations;
import com.gescompte.repositories.CompteBancaireRepository;
import com.gescompte.repositories.OperationsRepository;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class OperationsServiceImplement implements OperationsService {
    private final OperationsRepository operationsRepository;
    private final CompteBancaireRepository compteBancaireRepository;

    // Constructeur qui injecte les dépendances nécessaires
    OperationsServiceImplement(
            final OperationsRepository operationsRepository,
            final CompteBancaireRepository compteBancaireRepository
    ){
        this.operationsRepository = operationsRepository;
        this.compteBancaireRepository = compteBancaireRepository;
    }

    // Méthode pour effectuer un virement
    @Override
    public CompteBancaire effectuerVirement(String numCompte, double montant){
        // Recherche du compte bancaire par son numéro
        Optional<CompteBancaire> compteOp = compteBancaireRepository.findByNumCompte(numCompte);
        if(compteOp.isPresent()){
            CompteBancaire compte = compteOp.get();
            // Vérification que le compte est activé
            if(compte.getStatus().equals(AccountStatus.ACTIVATED)){
                // Mise à jour du solde du compte
                compte.setBalance(compte.getBalance() + montant);
                // Création d'une nouvelle opération de crédit
                Operations operations = new Operations();
                operations.setDateOperation(new Date());
                operations.setAmount(montant);
                operations.setCompte(compte);
                operations.setTypeOperations(TypeOperations.CREDIT);
                operations.setNumOperation(generateNumOp());
                // Enregistrement du compte mis à jour
                return compteBancaireRepository.save(compte);
            } else {
                throw new RuntimeException("Le compte est bloqué");
            }
        } else {
            return null;
        }
    }


    // Méthode pour effectuer un retrait
    public CompteBancaire effectuerRetrait(String numCompte, double montant){
        // Recherche du compte bancaire par son numéro
        Optional<CompteBancaire> compteOp = compteBancaireRepository.findByNumCompte(numCompte);
        if(compteOp.isPresent()){
            CompteBancaire compte = compteOp.get();
            // Vérification que le compte est activé
            if(compte.getStatus().equals(AccountStatus.ACTIVATED)){
                // Vérification que le solde est suffisant
                if(compte.getBalance() > montant){
                    // Mise à jour du solde du compte
                    compte.setBalance(compte.getBalance() - montant);
                    compte = compteBancaireRepository.save(compte);
                    // Création d'une nouvelle opération de débit
                    Operations operations = new Operations();
                    operations.setDateOperation(new Date());
                    operations.setAmount(montant);
                    operations.setCompte(compte);
                    operations.setTypeOperations(TypeOperations.DEBIT);
                    operations.setNumOperation(generateNumOp());
                    this.operationsRepository.save(operations);
                    // Enregistrement du compte mis à jour
                    return compteBancaireRepository.save(compte);
                } else {
                    throw new RuntimeException("Solde Insuffisant");
                }
            } else {
                throw new RuntimeException("Le compte est bloqué, Opération impossible");
            }
        }
        return null;
    }

    // Méthode pour récupérer les opérations d'un compte
    @Override
    public List<Operations> findByClientNumCompte(String numCompte){
        List<Operations> list = new ArrayList<>();
        for (Operations o: this.operationsRepository.findAll()){
            if (o.getCompte().getNumCompte().equals(numCompte)){
                list.add(o);
            }
        }
        return list;
    }

    // Méthode pour effectuer un virement entre deux comptes
    @Override
    public CompteBancaire virementFromCompteAToCompteB(String numCompteSource, String numCompteDestination, double montant){
        CompteBancaire source = effectuerRetrait(numCompteSource, montant);
        if (source != null){
            return effectuerVirement(numCompteDestination, montant);
        }
        return null;
    }

    // Méthode pour générer un numéro d'opération
    public static String generateNumOp() {
        Random random = new Random();
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < 2; i++) {
            sb.append("0");
        }
        for (int i = 0; i < 3; i++) {
            sb.append(random.nextInt(2));
        }
        for (int i = 0; i < 3; i++) {
            sb.append(random.nextInt(10));
        }
        return sb.toString();
    }
}
