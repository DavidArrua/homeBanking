package com.mindhub.homebanking.service.Impl;

import com.mindhub.homebanking.models.ClientLoan;
import com.mindhub.homebanking.repositories.ClientLoanRepository;
import com.mindhub.homebanking.service.ClientLoanService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ClientLoanServiceImpl implements ClientLoanService {

    @Autowired
    ClientLoanRepository clientLoanRepository;

    @Override
    public ClientLoan saveClientLoan(ClientLoan clientLoan) {
        return clientLoanRepository.save(clientLoan);
    }
}
