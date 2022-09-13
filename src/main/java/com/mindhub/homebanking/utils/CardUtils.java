package com.mindhub.homebanking.utils;

public final class CardUtils {

    private CardUtils(){}

    public static int getCardCvv() {
        int cardCvv = getRandomNumber(100, 999);
        return cardCvv;
    }

    public static String getCardNumber() {
        String cardNumber = getRandomNumber(1000, 9999) + " " + getRandomNumber(1000, 9999) + " " + getRandomNumber(1000, 9999) + " " + getRandomNumber(1000, 9999);
        return cardNumber;
    }

    public static int getRandomNumber(int min, int max) {
        return (int) ((Math.random() * (max - min)) + min);
    }
}
