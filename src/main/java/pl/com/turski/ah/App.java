package pl.com.turski.ah;

import javafx.application.Application;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import pl.com.turski.ah.model.exception.SettingException;
import pl.com.turski.ah.service.SettingService;
import pl.com.turski.ah.view.main.MainController;

public class App extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {
        ApplicationContext context = new ClassPathXmlApplicationContext("application-context.xml");
        MainController mainController =context.getBean(MainController.class);
        primaryStage.setScene(new Scene((Parent) mainController.getView(), 800, 600));
        primaryStage.setTitle("AuctionHelper 1.0");
        primaryStage.show();
        SettingService settingService = context.getBean(SettingService.class);
        try {
            settingService.loadSettings();
        } catch (SettingException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}
