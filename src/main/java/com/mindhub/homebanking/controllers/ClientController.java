package com.mindhub.homebanking.controllers;

import com.mindhub.homebanking.dtos.ClientDTO;
import com.mindhub.homebanking.models.Account;
import com.mindhub.homebanking.models.AccountType;
import com.mindhub.homebanking.models.Client;
import com.mindhub.homebanking.repositories.AccountRepository;
import com.mindhub.homebanking.repositories.ClientRepository;
import com.mindhub.homebanking.service.AccountService;
import com.mindhub.homebanking.service.ClientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

import static java.util.stream.Collectors.toList;

@RestController
public class ClientController {

    @Autowired
    private AccountService accountService;

    @Autowired
    private ClientService clientService;

    @RequestMapping("/api/clients")
    public List<ClientDTO> getClients() {
        return clientService.getAllClients().stream().map(client -> new ClientDTO(client)).collect(toList());
    }


    @RequestMapping("/api/clients/{id}")
    public ClientDTO getClient(@PathVariable Long id) {
        return new ClientDTO(clientService.getClientById(id));
    }

    @Autowired
    private PasswordEncoder passwordEncoder;


    @RequestMapping(path = "/api/clients", method = RequestMethod.POST)
    public ResponseEntity<Object> register(

            @RequestParam String firstName, @RequestParam String lastName,

            @RequestParam String email, @RequestParam String password) {

        if (firstName.isEmpty() || lastName.isEmpty() || email.isEmpty() || password.isEmpty()) {

            return new ResponseEntity<>("Missing data", HttpStatus.FORBIDDEN);

        }

        if(!email.contains("@")){
            return new ResponseEntity<>("it's not an email", HttpStatus.FORBIDDEN);
        }

        if (clientService.getClientByEmail(email) != null) {

            return new ResponseEntity<>("Name already in use", HttpStatus.FORBIDDEN);

        }
        Client registerClient = new Client(firstName, lastName, email, passwordEncoder.encode(password));
        String randomNumber = "VIN" + getRandomNumber(0, 99999999);

        clientService.saveClient(registerClient);
        accountService.saveAccount(new Account(randomNumber, LocalDateTime.now(), 0.0, registerClient, true, AccountType.CURRENT));
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    public int getRandomNumber(int min, int max) {
        return (int) ((Math.random() * (max - min)) + min);
    }

    @RequestMapping("/api/clients/current")
    public ClientDTO getCurrent(Authentication authentication) {
        return new ClientDTO(clientService.getClientByEmail(authentication.getName()));
    }

}
