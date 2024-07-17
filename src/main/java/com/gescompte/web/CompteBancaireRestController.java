package com.gescompte.web;

import com.gescompte.dto.ClientDto;
import com.gescompte.dto.CompteBancaireDto;
import com.gescompte.entities.CompteBancaire;
import com.gescompte.entities.CompteCourant;
import com.gescompte.entities.CompteEpargne;
import com.gescompte.services.CompteService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/api/v1")
public class CompteBancaireRestController {
    final CompteService compteService;
    CompteBancaireRestController(final CompteService compteService){
        this.compteService = compteService;
    }

    @PostMapping("/comptes")
    void createAccount(@RequestBody CompteBancaireDto compteBancaireDto){
        this.compteService.createAccount(compteBancaireDto);
    }

    @GetMapping(value = "/comptes/type/{type}")
    List<?> findAll(@PathVariable("type") String type){
        if(type.equals("CC"))
            this.compteService.findComptesCourant();
        if (type.equals("CE"))
            return this.compteService.findComptesEpargne();
        return null;
    }

    @GetMapping(value = "/comptes/{numcompte}/{type}")
    ResponseEntity<?> findCompte(@PathVariable("numCompte") String numCompte,
                                 @PathVariable("type") String type){
        CompteBancaire compteBancaire = this.compteService.findOne(numCompte);
        if(type.equals("CC") && (compteBancaire instanceof CompteCourant))
                return ResponseEntity.ok((CompteCourant)compteBancaire);
        if(type.equals("CE") && (compteBancaire instanceof CompteEpargne))
            return ResponseEntity.ok((CompteEpargne)compteBancaire);
        return null;
    }

    @GetMapping(value = "/comptes/active/{numCompte}")
     boolean activeCompte(@PathVariable("numCompte") String numCompte){
        return this.compteService.activeCompte((numCompte));
    }

    @GetMapping(value = "/comptes/suspendre/{numCompte}")
    boolean suspendreCompte(@PathVariable("numCompte") String numCompte){
        return this.compteService.suspendCompte((numCompte));
    }
}
