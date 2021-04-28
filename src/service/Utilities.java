package service;

import java.time.format.DateTimeFormatter;
import java.util.Random;

public class Utilities {

    public static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
    public static final Integer SUMMARY_RESET_MENU = 0;
    public static final Integer SUMMARY_BACK_TRANSACTION_MENU = 1;
    public static final Integer SUMMARY_EXIT_MENU = 2;

    public static final boolean isNumber(String input){
        return input.matches("[0-9]+");
    }

    public static final Integer generateRefNumber(){
        return 100000 + new Random().nextInt(900000);
    }

    public static final boolean isAccLengthValidation(long length, int i) {
        return length == i;
    }
}
