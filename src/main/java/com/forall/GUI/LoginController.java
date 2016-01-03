/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.forall.GUI;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.SplitPane;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author jd
 */
public class LoginController implements Initializable {

    /**
     * Initializes the controller class.
     */
    @FXML
    private Button loginButton;

    @FXML
    private TextField addressField;

    private String address;

    @Override
    public void initialize(URL url, ResourceBundle rb) {

        loginButton.setOnAction((ActionEvent event) -> {
            setAddress(addressField.getText());
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/SenGUI.fxml"));

            try {

                loader.load();
            } catch (IOException ex) {
                Logger.getLogger(LoginController.class.getName()).log(Level.SEVERE, null, ex);
            }

            SplitPane pane = loader.getRoot();

            SenGUIController con = (SenGUIController) loader.getController();
            con.setAddress(address);
            Scene scene = new Scene(pane);
            Stage stage = (Stage) loginButton.getScene().getWindow();
            stage.setScene(scene);
            stage.show();
        });
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = "ws://" + address;
    }
}
