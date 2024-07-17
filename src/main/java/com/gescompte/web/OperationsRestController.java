package com.gescompte.web;

import com.gescompte.dto.OperationsDto;
import com.gescompte.entities.CompteBancaire;
import com.gescompte.services.OperationsService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/api/v1")
public class OperationsRestController {
    final OperationsService operationsService;
    OperationsRestController(final OperationsService operationsService){
        this.operationsService = operationsService;
    }

    @PostMapping("/operations/virement")
    void effectuerVirement(@RequestBody OperationsDto dto){

        this.operationsService.effectuerVirement(dto);
    }

    @PostMapping("/operations/versement")
    void effectuerVersement(@RequestBody OperationsDto dto){

        this.operationsService.effectuerVersement(dto);
    }

    @PostMapping("/operations/retrait")
    void effectuerRetrait(@RequestBody OperationsDto dto){

       this.operationsService.effectuerRetrait(dto);
    }
}
