import service.DataLoaderService;
import view.WelcomeScreen;

public class Main {

    private static final WelcomeScreen welcomeScreen = new WelcomeScreen();
    private static final DataLoaderService loaderService = new DataLoaderService();

    public static void main(String[] args) {
        while (loaderService.loadData(args[0])) {
            // do something)
            welcomeScreen.launchWelcomeScreen();
        }
    }

}
