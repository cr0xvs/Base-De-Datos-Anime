package com.mycompany.anime;

import com.mycompany.anime.entidad.Anime;
import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TablePosition;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javax.persistence.Query;

public class PrimaryController implements Initializable {
    // Declaración de variables usando las columnas de la Base de Datos
    private Anime animeSeleccionado;
    @FXML
    private TableView<Anime> tableViewAnimes;
    @FXML
    private TableColumn<Anime, String> columnNombre;
    @FXML
    private TableColumn<Anime, String> columnFecha_Lanzamiento;
    @FXML
    private TableColumn<Anime, String> columnEstudio;
    @FXML
    private TextField textFieldNombre;
    @FXML
    private TextField textFieldBuscar;
    @FXML
    private CheckBox checkCoincide;
    
    // Para que se muestre la primera pantalla
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        columnNombre.setCellValueFactory(new PropertyValueFactory<>("nombre"));
        columnFecha_Lanzamiento.setCellValueFactory(new PropertyValueFactory<>("fecha_Lanzamiento"));
        columnEstudio.setCellValueFactory(new PropertyValueFactory<>("estudio"));
        columnEstudio.setCellValueFactory(
                cellData -> {
                    SimpleStringProperty property = new SimpleStringProperty();
                    if(cellData.getValue().getEstudio() != null) {
                        String nombreAni = cellData.getValue().getEstudio().getNombre();
                        property.setValue(nombreAni);
                    }
                    System.out.println("El estudio es: "+property);
                    return property;
                });
        // Para modificación rápida de datos desde el textField
        tableViewAnimes.getSelectionModel().selectedItemProperty().addListener(
                (observable, oldValue, newValue) -> {
                    animeSeleccionado = newValue;
                    if (animeSeleccionado != null) {
                        textFieldNombre.setText(animeSeleccionado.getNombre());
                    } else {
                        textFieldNombre.setText("");
                    }
                });
        cargarAnimes();
    }    
    
    // Para cargar los Animes
    private void cargarAnimes() {
        Query queryAnimeFindAll = App.em.createNamedQuery("Anime.findAll");
        List<Anime> listAnime = queryAnimeFindAll.getResultList();
        tableViewAnimes.setItems(FXCollections.observableArrayList(listAnime));
    }

    // Botón que guarda los cambios que hagamos desde el textField
    @FXML
    private void onActionButtonGuardar(ActionEvent event) {
        if(animeSeleccionado != null) {
           animeSeleccionado.setNombre(textFieldNombre.getText());
           App.em.getTransaction().begin();
           App.em.merge(animeSeleccionado);
           App.em.getTransaction().commit();
           
           int numFilaSeleccionada = tableViewAnimes.getSelectionModel().getSelectedIndex();
           tableViewAnimes.getItems().set(numFilaSeleccionada, animeSeleccionado);
           TablePosition pos = new TablePosition(tableViewAnimes, numFilaSeleccionada, null);
           tableViewAnimes.getFocusModel().focus(pos);
           tableViewAnimes.requestFocus();
        }
    }
    
    // Botón para nuevo registro en la base de datos
    @FXML
    private void onActionButtonNuevo(ActionEvent event) {
        try {
            App.setRoot("secondary");
            SecondaryController secondaryController = (SecondaryController)App.fxmlLoader.getController();
            animeSeleccionado = new Anime();
            secondaryController.setAnime(animeSeleccionado, true);
        } catch (IOException ex) {
            Logger.getLogger(PrimaryController.class.getName()).log(Level.SEVERE, ex.getMessage(), ex);
        }
    }
    
    // Botón para editar un registro en la base de datos
    @FXML
    private void onActionButtonEditar(ActionEvent event) {
        if(animeSeleccionado != null) {
            try{
                App.setRoot("secondary");
                SecondaryController secondaryController = (SecondaryController)App.fxmlLoader.getController();
                secondaryController.setAnime(animeSeleccionado, true);
            } catch (IOException ex){
                Logger.getLogger(PrimaryController.class.getName()).log(Level.SEVERE, ex.getMessage(), ex);
            }
        } else {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Atención");
            alert.setHeaderText("Debe seleccionar un registro");
            alert.showAndWait();
        }
    }
    
    // Botón para borrar un registro en la base de datos
    @FXML
    private void onActionButtonSuprimir(ActionEvent event) {
        if(animeSeleccionado != null) {
            Alert alertSup = new Alert(Alert.AlertType.CONFIRMATION);
            alertSup.setTitle("Confirmar");
            alertSup.setHeaderText("¿Desea suprimir el siguiente ?");
            alertSup.setContentText(animeSeleccionado.getNombre());
            Optional<ButtonType> result = alertSup.showAndWait();
            if (result.get() == ButtonType.OK){
                App.em.getTransaction().begin();
                App.em.remove(animeSeleccionado);
                App.em.getTransaction().commit();
                tableViewAnimes.getItems().remove(animeSeleccionado);
                tableViewAnimes.getFocusModel().focus(null);
                tableViewAnimes.requestFocus();
            } else {
                int numFilaSeleccionada = tableViewAnimes.getSelectionModel().getSelectedIndex();
                tableViewAnimes.getItems().set(numFilaSeleccionada, animeSeleccionado);
                TablePosition pos = new TablePosition(tableViewAnimes, numFilaSeleccionada, null);
                tableViewAnimes.getFocusModel().focus(pos);
                tableViewAnimes.requestFocus();
            }
        } else {
            Alert alertSel =  new Alert(Alert.AlertType.INFORMATION);
            alertSel.setTitle("Atención");
            alertSel.setHeaderText("Debes seleccionar un registro");
            alertSel.showAndWait();
        }
    }
    
    // Botón para buscar un registro en la base de datos
    @FXML
    private void onActionButtonBuscar(ActionEvent event) {
        if(!textFieldBuscar.getText().isEmpty()) {
            if(checkCoincide.isSelected()) {
                Query queryAnimeFindByNombre = App.em.createNamedQuery("Anime.findByNombre");
                queryAnimeFindByNombre.setParameter("nombre", textFieldBuscar.getText());
                List<Anime> listAnime = queryAnimeFindByNombre.getResultList();
                tableViewAnimes.setItems(FXCollections.observableArrayList(listAnime));
            } else {
                String strQuery = "SELECT * FROM Anime WHERE LOWER(nombre) LIKE ";
                strQuery += "\'%" + textFieldBuscar.getText().toLowerCase() + "%\'";
                Query queryAnimeFindLikeNombre = App.em.createNamedQuery(strQuery, Anime.class);
                
                List<Anime> listAnime = queryAnimeFindLikeNombre.getResultList();
                tableViewAnimes.setItems(FXCollections.observableArrayList(listAnime));
            }
        } else {
            cargarAnimes();
        }
    }
}
