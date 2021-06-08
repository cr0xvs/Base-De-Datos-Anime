module com.mycompany.anime {
    requires javafx.controls;
    requires javafx.fxml;

    requires java.instrument;
    requires java.persistence;
    requires java.sql;
    
    opens com.mycompany.anime.entidad;
    opens com.mycompany.anime to javafx.fxml;
    
    exports com.mycompany.anime;
}
