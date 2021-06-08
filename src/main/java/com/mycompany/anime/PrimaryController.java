package com.mycompany.anime;

import java.io.IOException;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;

public class PrimaryController {

    private void switchToSecondary() throws IOException {
        App.setRoot("secondary");
    }

    @FXML
    private void onActionButtonNuevo(ActionEvent event) {
    }
}
