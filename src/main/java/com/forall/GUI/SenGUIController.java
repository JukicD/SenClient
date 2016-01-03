/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.forall.GUI;

import com.forall.modell.DataProxy;
import java.io.IOException;
import java.net.URI;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Button;
import javafx.scene.control.SplitPane;
import javafx.scene.control.TextField;
import javax.websocket.ClientEndpoint;
import javax.websocket.ContainerProvider;
import javax.websocket.DeploymentException;
import javax.websocket.OnMessage;
import javax.websocket.WebSocketContainer;

/**
 * FXML Controller class
 *
 * @author jd
 */
@ClientEndpoint(decoders = {DataProxyDecoder.class})
public class SenGUIController implements Initializable {

    /**
     * Initializes the controller class.
     */
    @FXML
    LineChart lc;

    @FXML
    NumberAxis xAxis;

    @FXML
    NumberAxis yAxis;

    @FXML
    SplitPane splitPane;

    @FXML
    Button button;

    @FXML
    Button clearButton;

    @FXML
    TextField fromField;

    @FXML
    TextField toField;

    @FXML
    Button fetchButton;

    @FXML
    Button liveButton;

    public SenGUIController() {

    }

    private XYChart.Series<Number, Number> minuteDataSeries;
    private Timeline animation;
    private long time = 0;
    private double y = 0;
    private String address;
    ObservableList<DataProxy> list = FXCollections.observableArrayList();

    @Override
    public void initialize(URL url, ResourceBundle rb) {

        button.setOnAction((ActionEvent event) -> {
            String text = button.getText().equals("Start") ? "Stop" : "Start";
            button.setText(text);
            if (button.getText().equals("Stop")) {
                play();
            } else {
                stop();
            }
        });

        liveButton.setOnAction((ActionEvent event) -> {
            connectToSocket();
        });

        clearButton.setOnAction((ActionEvent event) -> {
            resetValues();
        });

        fetchButton.setOnAction((ActionEvent event) -> {
            connectToEndpoint(fromField.getText(), toField.getText());
        });

        // add starting data
        lc.getStylesheets().add("/css/chart.css");
        minuteDataSeries = new XYChart.Series<>();
        minuteDataSeries.setName("Minute Data");

        lc.getData().add(minuteDataSeries);
    }

    @OnMessage
    public void onMessage(DataProxy object) throws IOException {

        Platform.runLater(() -> {
            y = object.getData();
            time = object.getTimeStamp();
            minuteDataSeries.getData().add(new XYChart.Data<>(time, y));
        });
    }

    public void play() {
        animation.play();
    }

    public void stop() {
        animation.stop();
    }

    public LineChart getLc() {
        return lc;
    }

    private void connectToEndpoint(String from, String to) {
        System.out.println("Client connecting...");
        WebSocketContainer container = ContainerProvider.getWebSocketContainer();

        try {
            System.out.println(address);
            URI uri = URI.create(address + "/historical/" + from + "/" + to);
            container.connectToServer(this, uri);
            System.out.println("Client connected !");

        } catch (DeploymentException | IOException ex) {
            Logger.getLogger(SenClient.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void connectToSocket() {
        System.out.println("Client connecting to " + address + " ...");
        WebSocketContainer container = ContainerProvider.getWebSocketContainer();

        try {
            URI uri = URI.create(address + "/temperature");
            container.connectToServer(this, uri);
            System.out.println("Client connected !");
        } catch (DeploymentException | IOException ex) {
            Logger.getLogger(SenClient.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void resetValues() {
        lc.getData().clear();
        time = 0;
        y = 0;
        xAxis.setLowerBound(0);
        xAxis.setUpperBound(5000);
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }
}