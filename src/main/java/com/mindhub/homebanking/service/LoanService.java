package com.mindhub.homebanking.service;

import com.mindhub.homebanking.models.Loan;

import java.util.List;

public interface LoanService {

    public Loan getLoanById(long id);

    public List<Loan> getAllLoans();

    public void saveLoan(Loan loan);
}
