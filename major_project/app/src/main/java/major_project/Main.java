package major_project;

import major_project.model.App;
import major_project.model.http.Reddit;
import major_project.view.AppWindow;
import major_project.controller.AppController;
import javafx.application.Application;
import javafx.stage.Stage;
import javafx.application.HostServices;
import java.io.IOException;
import major_project.model.Setting;

public class Main extends Application  {
    private  HostServices hostServices; 
    private  App model; 
    private  AppWindow view ;
    private  AppController controller;
    public static void main(String[] args)   {
        if (args[1].equals("online") && System.getenv("SENDGRID_API_KEY") == null){
            System.out.println("enviromental variable SENDGRID_API_KEY not set");
            System.exit(0);
        } else if (args[1].equals("online") && System.getenv("SENDGRID_API_EMAIL") == null){
            System.out.println("enviromental variable SENDGRID_API_EMAIL not set");
            System.exit(0);
        } else if (args[0].equals("online") && System.getenv("INPUT_API_KEY") == null){
            System.out.println("enviromental variable  INPUT_API_KEY not set");
            System.exit(0);
        } else if (args.length != 2){
            System.out.println("Invalid number of argument");
            System.exit(0);
        }
        else if ((args[0].equals("offline") || args[0].equals("online")) && (args[1].equals("online")|| args[1].equals("offline"))){
            launch(args);
        } else{
            System.out.println("Invalid argument");
            System.exit(0);
        }
    }
    @Override
    public void start(Stage primaryStage){
        String input_api = getParameters().getRaw().get(0);
        String output_api = getParameters().getRaw().get(1);
        hostServices = getHostServices();
        model = new App(input_api, output_api, new Setting()); 
        view = new AppWindow(model, primaryStage);
        controller = new AppController(model,view,hostServices, primaryStage);
        primaryStage.setScene(view.getSplashScene());
        primaryStage.setTitle("Application");
        primaryStage.show();
    }
}
