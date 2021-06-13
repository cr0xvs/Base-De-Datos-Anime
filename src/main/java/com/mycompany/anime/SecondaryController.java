package com.mycompany.anime;

import com.mycompany.anime.entidad.Estudio;
import com.mycompany.anime.entidad.Anime;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.nio.file.FileAlreadyExistsException;
import java.nio.file.Files;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.ButtonType;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.stage.FileChooser;
import javafx.util.StringConverter;
import javax.persistence.Query;
import javax.persistence.RollbackException;

public class SecondaryController implements Initializable {
    // Declaración de variables usando las columnas de la Base de Datos
    private Anime anime;
    private boolean nuevoAnime;
    private static final String IMAGENES = "Imagenes";

    @FXML
    private TextField textFieldNombre;
    @FXML
    private TextField textFieldCapitulos;
    @FXML
    private CheckBox checkBoxFavorito;
    @FXML
    private DatePicker datePickerFecha_Lanzamiento;
    @FXML
    private ImageView imageViewImagen;
    @FXML
    private ComboBox<Estudio> comboBoxEstudio;
    @FXML
    private BorderPane rootSecondary;

    // Para que se muestre la segunda pantalla
    @Override
    public void initialize(URL url, ResourceBundle rb) {
    }
    
    // Para crear un nuevo Anime
    public void setAnime(Anime anime, boolean nuevoAnime){
        App.em.getTransaction().begin();
        if(!nuevoAnime) {
            this.anime = App.em.find(Anime.class, anime.getId());
        } else {
            this.anime = anime;
        }
        this.nuevoAnime = nuevoAnime;  
        
        mostrarDatos();
    }
    
    // Para rellenar los campos de la aplicación
    private void mostrarDatos(){
        textFieldNombre.setText(anime.getNombre());
        if (anime.getCapitulos() != null) {
            textFieldCapitulos.setText(String.valueOf(anime.getCapitulos()));
        }
        if (anime.getFavorito() != null){
            checkBoxFavorito.setSelected(anime.getFavorito());
        }
        if (anime.getFechaLanzamiento() != null) {
            Date date = anime.getFechaLanzamiento();
            Instant instant = date.toInstant();
            ZonedDateTime zdt = instant.atZone(ZoneId.systemDefault());
            LocalDate localDate = zdt.toLocalDate();
            datePickerFecha_Lanzamiento.setValue(localDate);
        }
        
        Query queryEstudioFindAll = App.em.createNamedQuery("Estudio.findAll");
        List<Estudio> listEstudio = queryEstudioFindAll.getResultList();
        
        comboBoxEstudio.setItems(FXCollections.observableList(listEstudio));
        if (anime.getEstudio() != null) {
            comboBoxEstudio.setValue(anime.getEstudio());
        }       

        comboBoxEstudio.setCellFactory((ListView<Estudio> l) -> new ListCell<Estudio>() {
            @Override
            protected void updateItem(Estudio estudio, boolean empty){
                super.updateItem(estudio, empty);
                if(estudio == null || empty) {
                    setText("");
                } else {
                    setText(estudio.getCodigo() + "-" + estudio.getNombre());
                }
            }
        });
        
        comboBoxEstudio.setConverter(new StringConverter<Estudio>() {
           @Override
           public String toString(Estudio estudio) {
               if(estudio == null) {
                   return null;
               } else {
                   return estudio.getCodigo() + "-" + estudio.getNombre();
               }
           }
           
           @Override
           public Estudio fromString(String userId) {
               return null;
           }
        });
        
        if (anime.getImagen() != null) {
            String imageFileName = anime.getImagen();
            File file = new File(IMAGENES + "/" + imageFileName);
            if (file.exists()) {
                Image image = new Image(file.toURI().toString());
                imageViewImagen.setImage(image);
            } else {
                Alert alert = new Alert(Alert.AlertType.INFORMATION, "No se encuentra la imagen");
                alert.showAndWait();
            }
        }

    }
    
    // Guardar los datos
    @FXML
    private void onActionButtonGuardar(ActionEvent event) {
        boolean errorFormato = false;
        
        anime.setNombre(textFieldNombre.getText());
        
        if (!textFieldCapitulos.getText().isEmpty()) {
            try {
                anime.setCapitulos(Integer.valueOf(textFieldCapitulos.getText()));
            } catch (NumberFormatException ex) {
                errorFormato = true;
                Alert alert = new Alert(Alert.AlertType.INFORMATION, "Número de Capítulos no válido");
                alert.showAndWait();
                textFieldCapitulos.requestFocus();
            }
        }
        
        anime.setFavorito(checkBoxFavorito.isSelected());
        
        if (datePickerFecha_Lanzamiento.getValue() != null) {
            LocalDate localDate = datePickerFecha_Lanzamiento.getValue();
            ZonedDateTime zonedDateTime = localDate.atStartOfDay(ZoneId.systemDefault());
            Instant instant = zonedDateTime.toInstant();
            Date date = Date.from(instant);
            anime.setFechaLanzamiento(date);
        } else {
            anime.setFechaLanzamiento(null);
        }
        
        anime.setEstudio(comboBoxEstudio.getValue());
        
        if (!errorFormato) {
            try {
                if (anime.getId() == null) {
                    System.out.println("Guardando nuevo anime en la Base de Datos");
                    App.em.persist(anime);
                } else {
                    System.out.println("Actualizando anime en la Base de Datos");
                    App.em.merge(anime);
                }
                App.em.getTransaction().commit();
                
                App.setRoot("primary");
            } catch (RollbackException ex) {
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setHeaderText("No se han podido guardar los cambios en la Base de Datos."
                        + "Comprobar si los datos cumplen los requisitos para guardarse en la Base de Datos.");
                alert.setContentText(ex.getLocalizedMessage());
                alert.showAndWait();
            } catch (IOException ex) {
                Logger.getLogger(SecondaryController.class.getName()).log(Level.SEVERE, ex.getMessage(), ex);
            }
        }
        
        
    }
    
    // Para no guardar los datos
    @FXML
    private void onActionButtonCancelar(ActionEvent event) {
        App.em.getTransaction().rollback();
        
        try {
            App.setRoot("primary");
        } catch (IOException ex) {
            Logger.getLogger(SecondaryController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    // Para buscar la imágen que vayamos a poner con el imgview
    @FXML
    private void onActionButtonExaminar(ActionEvent event) {
        File carpetaImagenes = new File(IMAGENES);
        if (!carpetaImagenes.exists()) {
            carpetaImagenes.mkdir();
        }
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Seleccionar imagen");
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("Imágenes (jpg, png)", "*.jpg", "*.png"),
                new FileChooser.ExtensionFilter("Todos los archivos", "*.*")
        );
        File file = fileChooser.showOpenDialog(rootSecondary.getScene().getWindow());
        if (file != null) {
            try {
                Files.copy(file.toPath(), new File(IMAGENES + "/" + file.getName()).toPath());
                anime.setImagen(file.getName());
                Image image = new Image(file.toURI().toString());
                imageViewImagen.setImage(image);
            } catch (FileAlreadyExistsException ex) {
                Alert alert = new Alert(Alert.AlertType.WARNING, "Nombre del archivo duplicado");
                alert.showAndWait();
            } catch (IOException ex) {
                Alert alert = new Alert(Alert.AlertType.WARNING, "No se ha podido guardar la imagen");
                alert.showAndWait();
            }
        }
    }
    
    // Para eliminar la imagen, examinada previamente
    @FXML
    private void onActionButtonEliminar(ActionEvent event) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirmar la eliminación de la imagen");
        alert.setHeaderText("¿Desea eliminar el archivo de la imagen, \n"
                + "quitar la imagen pero mantener el archivo, \no CANCELAR la operación");
        alert.setContentText("Elija una opción:");
        
        ButtonType buttonTypeEliminar = new ButtonType("Suprimir");
        ButtonType buttonTypeMantener = new ButtonType("Mantener");
        ButtonType buttonTypeCancel = new ButtonType("Cancelar", ButtonBar.ButtonData.CANCEL_CLOSE);
        
        alert.getButtonTypes().setAll(buttonTypeEliminar, buttonTypeMantener, buttonTypeCancel);
        
        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == buttonTypeEliminar) {
            String imageFileName = anime.getImagen();
            File file = new File(IMAGENES + "/" + imageFileName);
            if (file.exists()) {
                file.delete();
            }
            anime.setImagen(null);
            imageViewImagen.setImage(null);
        } else if (result.get() == buttonTypeMantener) {
            anime.setImagen(null);
            imageViewImagen.setImage(null);
        }
    }
}
