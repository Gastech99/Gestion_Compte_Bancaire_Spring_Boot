package com.gescompte.web;

import com.gescompte.dto.ClientDto;
import com.gescompte.entities.Client;
import com.gescompte.services.ClientService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping(value = "/api/v1")
public class ClientRestController {
    private final ClientService clientService;
    ClientRestController(final ClientService clientService){
        this.clientService = clientService;
    }

    @PostMapping("/clients")
    void createClient(@RequestBody ClientDto dto){
        this.clientService.createNewClient(dto);
    }
    @GetMapping("/clients")
    List<Client> findAll(){
        return this.clientService.findAll();
    }
    @GetMapping("/clients/{id}")
    Client findOne(@PathVariable("id") long id){
        return this.clientService.findOne(id);
    }
}
