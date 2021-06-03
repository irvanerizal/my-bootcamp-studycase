package com.my.example.atm.service;

import java.time.format.DateTimeFormatter;
import java.util.Random;

public class Utilities {

    public static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
    public static final Integer RESET = 0;
    public static final Integer BACK_TRANSACTION_MENU = 1;
    public static final Integer EXIT_APP = 2;

    public static boolean isNumber(String input){
        return input.matches("[0-9]+");
    }

    public static Integer generateRefNumber(){
        return 100000 + new Random().nextInt(900000);
    }

    public static boolean isAccLengthValidation(long length, int i) {
        return length == i;
    }
}
