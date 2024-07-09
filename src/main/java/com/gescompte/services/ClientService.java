package com.gescompte.services;

import com.gescompte.dto.ClientDto;
import com.gescompte.entities.Client;

import java.util.List;

public interface ClientService {
    void createNewClient(ClientDto clientDto);
    List<Client> findAll();
    Client findOne(long id);
}
