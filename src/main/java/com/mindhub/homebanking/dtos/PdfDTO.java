package com.mindhub.homebanking.dtos;

import com.mindhub.homebanking.models.Account;

import java.time.LocalDateTime;

public class PdfDTO {

    private LocalDateTime from;

    private LocalDateTime thru;

    private String account;

    public PdfDTO() {
    }

    public PdfDTO(LocalDateTime from, LocalDateTime thru, String account) {
        this.from = from;
        this.thru = thru;
        this.account = account;
    }

    public LocalDateTime getFrom() {
        return from;
    }

    public void setFrom(LocalDateTime from) {
        this.from = from;
    }

    public LocalDateTime getThru() {
        return thru;
    }

    public void setThru(LocalDateTime thru) {
        this.thru = thru;
    }

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }
}
