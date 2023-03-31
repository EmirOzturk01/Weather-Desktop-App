package com.example.aaaaaaaaaaaaa;

import javafx.animation.RotateTransition;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class FavouriteController implements Initializable {
    @FXML
    private ImageView menuIcon;
    @FXML
    private AnchorPane menuBar;
    @FXML
    private ImageView minimize;
    @FXML
    private AnchorPane mainPane;
    @FXML
    private VBox v1;



    int lineCount=0;
    public void lineCounter() throws IOException {
        BufferedReader reader = new BufferedReader(new FileReader("src/favourites.txt"));
        String line = reader.readLine();
        while(line!=null){

                lineCount++;
            line=reader.readLine();
            }

            reader.close();
    }



    SceneContext sceneContext = new SceneContext();

    public void switchToFav() throws IOException, InterruptedException {
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("hello-view.fxml"));
        Parent root = fxmlLoader.load();
        Scene scene = new Scene(root);
        sceneContext.setOldScene(menuBar.getScene());
        sceneContext.switchScene(scene);

    }
    public void minimize(){
        Stage stage = (Stage) minimize.getScene().getWindow();
        stage.setIconified(true);
    }
    public void exit(){
        System.exit(0);
    }
    public void weatherSnapshot(){
        System.out.println("snapshot");
    }
    int menuBarIcon=0;
    public void menuIcon(){
        if(menuBarIcon==0){
            RotateTransition transition = new RotateTransition(Duration.millis(300),menuIcon);
            transition.setToAngle(180);
            transition.play();
            menuBar.setVisible(true);
            menuBarIcon=1;
        }
        else{
            RotateTransition transition = new RotateTransition(Duration.millis(300),menuIcon);
            transition.setToAngle(0);
            transition.play();
            menuBar.setVisible(false);
            menuBarIcon=0;
        }

    }

    public void addCardToPane(){
        try {
            v1.getChildren().clear();
            for(int i=0;i<lineCount;i++){
                System.out.println("line count:"+lineCount);
                FXMLLoader fxmlLoader = new FXMLLoader();
                fxmlLoader.setLocation(getClass().getResource("favCard.fxml"));
                AnchorPane anchorPane = fxmlLoader.load();
                v1.getChildren().add(anchorPane);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        System.gc();
        menuBar.setVisible(false);
        try {
            lineCounter();
        } catch (IOException e) {
            e.printStackTrace();
        }
        addCardToPane();


    }


    }


