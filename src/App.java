import controller.Authentification;
import model.Configs;
import model.ServeurStorage;
import view.ViewManager;

public class App {
    public static void main(String[] args) {
    	ServeurStorage serveurStorage = new ServeurStorage();
    	Authentification auth = new Authentification(serveurStorage);
        new ViewManager(new Configs(), auth);
    }
}
