package com.my.example.atm;

import com.my.example.atm.service.DataLoaderService;
import com.my.example.atm.view.WelcomeScreen;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class Main {

    private static final WelcomeScreen welcomeScreen = new WelcomeScreen();
    private static final DataLoaderService loaderService = new DataLoaderService();

    public static void main(String[] args) {
        SpringApplication.run(Main.class, args);
        runTheATM(args);
    }

    public static void runTheATM(String[] args) {
        String argument = args.length > 0 ? args[0] : "";
        boolean result = loaderService.loadAccountData(argument);
        while (result) {
            // do something)
            welcomeScreen.launchWelcomeScreen();
        }
    }

}
