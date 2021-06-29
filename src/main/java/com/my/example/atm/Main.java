package com.my.example.atm;

import com.my.example.atm.service.api.DataLoaderService;
import com.my.example.atm.service.impl.DataLoaderServiceImpl;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.ConfigurableApplicationContext;

@SpringBootApplication
public class Main extends SpringBootServletInitializer {

    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
        return application.sources(Main.class);
    }

    public static void main(String[] args) {
        ConfigurableApplicationContext context = SpringApplication.run(Main.class, args);
        try {
            runTheATM(context, args);
        } catch (Exception e){
            System.out.println(e.getMessage());
        }
    }

    public static void runTheATM(ConfigurableApplicationContext context, String[] args) throws Exception {
        /*For init purpose in the Main class*/
        DataLoaderService loaderService = context.getBean(DataLoaderServiceImpl.class);
//        WelcomeScreen welcomeScreen = context.getBean(WelcomeScreen.class);

        String argument = args.length > 0 ? args[0] : "";
        loaderService.loadAccountData(argument);
        /*while (true) {
            // do something)
            welcomeScreen.launchWelcomeScreen();
        }*/
    }

}
