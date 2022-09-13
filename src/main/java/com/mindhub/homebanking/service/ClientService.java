package com.mindhub.homebanking.service;

import com.mindhub.homebanking.models.Client;

import java.util.List;

public interface ClientService {

    public List<Client> getAllClients();

    public Client getClientById(Long id);

    public Client getClientByEmail(String email);

    public Client saveClient(Client client);
}
