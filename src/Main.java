import service.DataLoaderService;
import view.WelcomeScreen;

public class Main {

    private static final WelcomeScreen welcomeScreen = new WelcomeScreen();
    private static final DataLoaderService loaderService = new DataLoaderService();

    public static void main(String[] args) {
        boolean result = loaderService.loadAccountData(args[0]);
        while (result) {
            // do something)
            welcomeScreen.launchWelcomeScreen();
        }
    }

}
