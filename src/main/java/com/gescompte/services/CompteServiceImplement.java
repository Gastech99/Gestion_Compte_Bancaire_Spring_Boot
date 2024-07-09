package com.gescompte.services;

import com.gescompte.dto.CompteBancaireDto;
import com.gescompte.entities.Client;
import com.gescompte.entities.CompteBancaire;
import com.gescompte.entities.CompteCourant;
import com.gescompte.entities.CompteEpargne;
import com.gescompte.enums.AccountStatus;
import com.gescompte.repositories.ClientRepository;
import com.gescompte.repositories.CompteBancaireRepository;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class CompteServiceImplement implements CompteService {
    private final CompteBancaireRepository compteBancaireRepository;
    private final ClientRepository clientRepository;
    CompteServiceImplement(
            final CompteBancaireRepository compteBancaireRepository,
            final ClientRepository clientRepository){
        this.clientRepository = clientRepository;
        this.compteBancaireRepository = compteBancaireRepository;
    }
    @Override
    public void createAccount(CompteBancaireDto compteBancaireDto){
        Optional<Client> clientOpt = this.clientRepository.findById(compteBancaireDto.getClientId());
                if(clientOpt.isPresent() && (compteBancaireDto.getDecouvert() > 0 && compteBancaireDto.getTauxInteret() == 0)){
                    CompteCourant compteCourant = new CompteCourant();
                    compteCourant.setCreatedAt(new Date());
                    compteCourant.setBalance(compteBancaireDto.getBalance());
                    compteCourant.setDecouvert(compteBancaireDto.getDecouvert());
                    compteCourant.setClient(clientOpt.get());
                    compteCourant.setDevis(compteBancaireDto.getDevis());
                    compteCourant.setStatus(AccountStatus.ACTIVATED);
                    compteCourant.setNumCompte(generatedAccountNumber());

                    this.compteBancaireRepository.save(compteCourant);
                }
        if(clientOpt.isPresent() && (compteBancaireDto.getDecouvert() == 0 && compteBancaireDto.getTauxInteret() > 0)){
            CompteEpargne compteEpargne = new CompteEpargne();
            compteEpargne.setCreatedAt(new Date());
            compteEpargne.setBalance(compteBancaireDto.getBalance());
            compteEpargne.setClient(clientOpt.get());
            compteEpargne.setDevis(compteBancaireDto.getDevis());
            compteEpargne.setTauxInteret(compteBancaireDto.getTauxInteret());
            compteEpargne.setStatus(AccountStatus.ACTIVATED);
            compteEpargne.setNumCompte(generatedAccountNumber());

            this.compteBancaireRepository.save(compteEpargne);
        }
    }

    @Override
    public List<CompteEpargne> findComptesEpargne() {
        List<CompteEpargne> list = new ArrayList<>();
        for (CompteBancaire c: compteBancaireRepository.findAll()){
            if(c instanceof CompteEpargne){
                list.add((CompteEpargne) c);
            }
        }
        return list;
    }

    @Override
    public List<CompteCourant> findComptesCourant() {
        List<CompteCourant> list = new ArrayList<>();
        for (CompteBancaire c: compteBancaireRepository.findAll()){
            if(c instanceof CompteCourant){
                list.add((CompteCourant) c);
            }
        }
        return list;
    }

    @Override
    public CompteBancaire findOne(String numCompte) {
        return this.compteBancaireRepository.findByNumCompte(numCompte).get();
    }

    private static String generatedAccountNumber(){
        Random random = new Random();
        StringBuilder sb = new StringBuilder();
        // Les 4 premiers chiffres 0
        for (int i = 0; i < 4; i++) {
            sb.append(0);
        }
        // Les 4 chiffres suivants sont des 0 ou 1
        for (int i = 0; i < 4; i++) {
            sb.append(random.nextInt(2));
        }
        // Les 10 derniers chiffres sont générés aléatoirements
        for (int i = 0; i < 10; i++) {
            sb.append(random.nextInt(10));
        }
        return sb.toString();
    }
}
