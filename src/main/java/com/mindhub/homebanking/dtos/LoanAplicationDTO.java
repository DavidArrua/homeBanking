package com.mindhub.homebanking.dtos;

import com.mindhub.homebanking.models.ClientLoan;
import com.mindhub.homebanking.models.Loan;
import com.mindhub.homebanking.models.Transaction;

public class LoanAplicationDTO {

    private long id;

    private Integer payments;

    private double amount;

    private String accountDestination;

    public LoanAplicationDTO() {
    }

    public LoanAplicationDTO(Loan loan, double amount, Integer payments, String accountDestination) {
        this.id = loan.getId();
        this.amount = amount;
        this.payments = payments;
        this.accountDestination = accountDestination;
    }


    public long getId() {
        return id;
    }

    public Integer getPayments() {
        return payments;
    }

    public void setPayments(Integer payments) {
        this.payments = payments;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public String getAccountDestination() {
        return accountDestination;
    }

    public void setAccountDestination(String accountDestination) {
        this.accountDestination = accountDestination;
    }
}
