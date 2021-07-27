package com.my.example.atm.service;

import java.time.format.DateTimeFormatter;
import java.util.HashSet;
import java.util.Random;
import java.util.Set;

public class Utilities {

    public static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
    public static final Integer RESET = 0;
    public static final Integer BACK_TRANSACTION_MENU = 1;
    public static final Integer EXIT_APP = 2;

    public static final Long MAX_AMOUNT_WITHDRAWN_LIMIT = 1000L;
    public static final int ACCOUNT_AND_PIN_LENGTH_VALIDATION = 6;
    public static final String ACCOUNT_KEY = "logged_account";

    private static final String lexicon = "ABCDEFGHIJKLMNOPQRSTUVWXYZ12345674890";
    private static final java.util.Random rand = new java.util.Random();
    private static final Set<String> identifiers = new HashSet<>();

    private static final int balanceLowestLimit = 10;
    private static final int balanceHighestLimit = 1000;

    public static boolean isNumber(String input){
        return input.matches("[0-9]+");
    }

    public static Integer generateRefNumber(){
        return 100000 + new Random().nextInt(900000);
    }

    public static boolean isAccLengthValidation(long length, int i) {
        return length == i;
    }

    public static String generateRandomName(){
        StringBuilder builder = new StringBuilder();
        while(builder.toString().length() == 0) {
            int length = rand.nextInt(5)+5;
            for(int i = 0; i < length; i++) {
                builder.append(lexicon.charAt(rand.nextInt(lexicon.length())));
            }
            if(identifiers.contains(builder.toString())) {
                builder = new StringBuilder();
            }
        }
        return builder.toString();
    }

    public static String generateSixDigitsNumber(){
        int number = rand.nextInt(999999);
        return String.format("%06d", number);
    }

    public static String generateRandomBalance(){
        return String.valueOf(rand.nextInt(balanceHighestLimit - balanceLowestLimit) + balanceLowestLimit);
    }

}
