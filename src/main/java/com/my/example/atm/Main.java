package com.my.example.atm;

import com.my.example.atm.service.DataLoaderService;
import com.my.example.atm.view.WelcomeScreen;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

@SpringBootApplication
public class Main {

    public static void main(String[] args) {
        ConfigurableApplicationContext context = SpringApplication.run(Main.class, args);
        runTheATM(context, args);
    }

    public static void runTheATM(ConfigurableApplicationContext context, String[] args) {

        /*For init purpose in the Main class*/
        DataLoaderService loaderService = context.getBean(DataLoaderService.class);
        WelcomeScreen welcomeScreen = context.getBean(WelcomeScreen.class);

        String argument = args.length > 0 ? args[0] : "";
        boolean result = loaderService.loadAccountData(argument);
        while (result) {
            // do something)
            welcomeScreen.launchWelcomeScreen();
        }
    }

}
