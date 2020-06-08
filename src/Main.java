import Controller.Controller;
import Controller.IController;
import Model.ADT.*;
import Model.Expression.*;
import Model.ProgramState;
import Model.Statement.*;
import Model.Type.BoolType;
import Model.Type.IntType;
import Model.Type.ReferenceType;
import Model.Type.StringType;
import Model.Value.BoolValue;
import Model.Value.IValue;
import Model.Value.IntValue;
import Model.Value.StringValue;
import Repository.IRepository;
import Repository.Repository;
import View.View;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import View.*;

import java.io.BufferedReader;

public class Main extends Application{

    @Override
    public void start(Stage primaryStage) throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("View/gui/mainWindow/MainWindow.fxml"));
        primaryStage.setTitle("Toy interpreter");
        primaryStage.setScene(new Scene(root));
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
