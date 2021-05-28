import service.DataLoaderService;
import view.WelcomeScreen;

public class Main {

    private static final WelcomeScreen welcomeScreen = new WelcomeScreen();
    private static final DataLoaderService loaderService = new DataLoaderService();

    public static void main(String[] args) {
        String argument = args.length > 0 ? args[0] : "";
        boolean result = loaderService.loadAccountData(argument);
        while (result) {
            // do something)
            welcomeScreen.launchWelcomeScreen();
        }
    }

}
