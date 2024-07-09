package com.gescompte.services;

import com.gescompte.dto.ClientDto;
import com.gescompte.entities.Client;
import com.gescompte.repositories.ClientRepository;
import lombok.Data;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class ClientServiceImplement implements ClientService {
    private final ClientRepository clientRepository;
    ClientServiceImplement(final ClientRepository clientRepository){
        this.clientRepository = clientRepository;
    }

    @Override
    public void createNewClient(ClientDto clientDto) {
        Client client = new Client();
        client.setNom(clientDto.getNom());
        client.setPrenom(clientDto.getPrenom());
        client.setEmail(clientDto.getEmail());
        client.setTelephone(clientDto.getTelephone());
        client.setAddresse(clientDto.getAddresse());

        this.clientRepository.save(client);
    }

    @Override
    public List<Client> findAll() {
        return this.clientRepository.findAll();
    }

    @Override
    public Client findOne(long id) {
        return this.clientRepository.getReferenceById(id);
    }
}
