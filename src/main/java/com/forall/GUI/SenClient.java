/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.forall.GUI;

import java.io.IOException;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

/**
 *
 * @author jd
 */

public class SenClient extends Application {

    private void init(Stage primaryStage) throws IOException {

        FXMLLoader loginloader = new FXMLLoader(getClass().getResource("/fxml/login.fxml"));

        primaryStage.setWidth(600);
        primaryStage.setHeight(400);

        Pane pane = (Pane) loginloader.load();
        Scene loginScene = new Scene(pane);
        primaryStage.setScene(loginScene);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        init(primaryStage);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}