package com.example.aaaaaaaaaaaaa;

import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

public class SceneContext {
    private static Scene oldScene = null;

    double yOffset=0;
    double xOffset=0;
    public void switchScene(Scene newScene) {
        Stage stage = (Stage) oldScene.getWindow();
        newScene.setFill(Color.TRANSPARENT);

        stage.setScene(newScene);
        oldScene = null;
        System.gc(); // Bellek toplama işlemi
        newScene.setOnMousePressed(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                xOffset = event.getSceneX();
                yOffset = event.getSceneY();
            }
        });
        newScene.setOnMouseDragged(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                stage.setX(event.getScreenX() - xOffset);
                stage.setY(event.getScreenY() - yOffset);
            }
        });
    }

    public Scene getOldScene() {
        return oldScene;
    }

    public void setOldScene(Scene scene) {
        oldScene = scene;
    }
}
