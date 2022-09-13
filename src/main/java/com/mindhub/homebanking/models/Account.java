package com.mindhub.homebanking.models;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Entity
public class Account {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "native")
    @GenericGenerator(name = "native", strategy = "native")
    private Long id;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name="client_id")
    private Client client;

    @OneToMany(mappedBy="account", fetch=FetchType.EAGER)
    private Set<Transaction> transactions = new HashSet<>();

    @OneToMany(mappedBy="account", fetch=FetchType.EAGER)
    private Set<Card> cards = new HashSet<>();

    private String number;
    private LocalDateTime creationDate;
    private double balance;

    private boolean accountState;

    private AccountType type;

    public Account() { }

    public Account(String number, LocalDateTime creationDate, double balance, boolean accountState, AccountType type){
        this.number = number;
        this.creationDate = creationDate;
        this.balance = balance;
        this.accountState = accountState;
        this.type = type;
    }

    public Account(String number, LocalDateTime creationDate, double balance, Client client, boolean accountState, AccountType type){
        this.number = number;
        this.creationDate = creationDate;
        this.balance = balance;
        this.client = client;
        this.accountState = accountState;
        this.type = type;
    }



    public Long getId() {
        return id;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public LocalDateTime getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(LocalDateTime creationDate) {
        this.creationDate = creationDate;
    }

    public double getBalance() {
        return balance;
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }

    public Client getClient() {
        return client;
    }

    public void setClient(Client client) {
        this.client = client;
    }

    public Set<Transaction> getTransactions() {
        return transactions;
    }

    public void setTransactions(Set<Transaction> transactions) {
        this.transactions = transactions;
    }

    public boolean isAccountState() {
        return accountState;
    }

    public void setAccountState(boolean accountState) {
        this.accountState = accountState;
    }

    public AccountType getType() {
        return type;
    }

    public void setType(AccountType type) {
        this.type = type;
    }

    public Set<Card> getCards() {
        return cards;
    }

    public void setCards(Set<Card> cards) {
        this.cards = cards;
    }
}
