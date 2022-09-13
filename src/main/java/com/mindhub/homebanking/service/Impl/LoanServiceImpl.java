package com.mindhub.homebanking.service.Impl;

import com.mindhub.homebanking.models.Loan;
import com.mindhub.homebanking.repositories.LoanRepository;
import com.mindhub.homebanking.service.LoanService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class LoanServiceImpl implements LoanService {
    @Autowired
    LoanRepository loanRepository;

    @Override
    public Loan getLoanById(long id) {
        return loanRepository.findById(id);
    }

    @Override
    public List<Loan> getAllLoans() {
        return loanRepository.findAll();
    }

    @Override
    public void saveLoan(Loan loan) {
        loanRepository.save(loan);
    }
}
