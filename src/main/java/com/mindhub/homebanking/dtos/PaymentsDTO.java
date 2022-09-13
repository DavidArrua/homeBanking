package com.mindhub.homebanking.dtos;


import java.time.LocalDate;

public class PaymentsDTO {

    private long id;

    private String cardHolder;

    private String number;

    private Double amount;

    private Integer cvv;

    private LocalDate thruDate;

    private String description;


    public PaymentsDTO() {
    }

    public PaymentsDTO(long id, String cardHolder, String number, Double amount, Integer cvv, LocalDate thruDate, String description) {
        this.id = id;
        this.cardHolder = cardHolder;
        this.number = number;
        this.amount = amount;
        this.cvv = cvv;
        this.thruDate = thruDate;
        this.description = description;
    }

    public long getId() {
        return id;
    }

    public String getCardHolder() {
        return cardHolder;
    }

    public void setCardHolder(String cardHolder) {
        this.cardHolder = cardHolder;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    public Integer getCvv() {
        return cvv;
    }

    public void setCvv(Integer cvv) {
        this.cvv = cvv;
    }

    public LocalDate getThruDate() {
        return thruDate;
    }

    public void setThruDate(LocalDate thruDate) {
        this.thruDate = thruDate;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
