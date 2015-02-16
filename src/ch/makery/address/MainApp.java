/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ch.makery.address;

import java.io.IOException;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

import ch.makery.address.model.Person;
import ch.makery.address.model.PersonListWrapper;
import ch.makery.address.view.PersonOverviewController;
import ch.makery.address.view.PersonEditDialogController;
import java.io.File;
import java.util.prefs.Preferences;
import javafx.scene.image.Image;
import javafx.stage.Modality;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.Unmarshaller;

/**
 *
 * @author kaze
 */
public class MainApp extends Application {
    private Stage primaryStage;
    private BorderPane rootLayout;
    private ObservableList<Person> personData = FXCollections.observableArrayList();
    
    public MainApp(){
        personData.add(new Person("Aldo", "Jamanca"));
        personData.add(new Person("Samanda", "Carranza"));
        personData.add(new Person("Heinz", "Kurz"));
        personData.add(new Person("Cornelia", "Meier"));
        personData.add(new Person("Werner", "Meyer"));
        personData.add(new Person("Lydia", "Kunz"));
        personData.add(new Person("Anna", "Best"));
        personData.add(new Person("Stefan", "Meier"));
        personData.add(new Person("Martin", "Mueller"));
    }
     /**
     * Returns the data as an observable list of Persons. 
     * @return
     */
    public ObservableList<Person> getPersonData() {
        return personData;
    }
    
    @Override
    public void start(Stage primaryStage) {
        this.primaryStage = primaryStage;
        this.primaryStage.setTitle("Aplicacion Direcciones");
         
        this.primaryStage.getIcons().add(new Image("file:images/agenda.png"));
        
        initRootLayout();
        
        showPersonOverview();
    }
    
    /**
     * Inicializamos el root layout
     */
    public void initRootLayout(){
        try {
            //cargamos el rootlayout desde el archivo fxml
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(MainApp.class.getResource("view/RootLayout.fxml"));
            rootLayout = (BorderPane) loader.load();
            
            //Mostramos la scene  contenida en el rootlayout
            Scene scene = new Scene(rootLayout);
            primaryStage.setScene(scene);
            primaryStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    /**
     *Mostramos la personoverview en el rootlayout 
     */
    public void showPersonOverview(){
        try {
            //Cargamos la personoverview
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(MainApp.class.getResource("view/PersonOverview.fxml"));
            AnchorPane personOverview = (AnchorPane) loader.load();
            
            //Seteamos la personoverview  en el centro del rootlayout
            rootLayout.setCenter(personOverview);
            
            //Damos al controlador acceso para la main app
            PersonOverviewController controller = loader.getController();
            controller.setMainApp(this);
            
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    /**
     * Open un dialogo  para editar los detalles  de una persona especificada.
     * Si el usuario  da click ok, los cambios son salvados en el objeto persona proveido
     * y true es retornado-
     * 
     * @param person el object person para ser editado
     * @return true si el usuario da click en Ok, false en otros casos.
     */
    public boolean showPersonEditDialog(Person person){
        try {
            //Carga el archivo fxml y crea un nuevo stage para el dialog popup
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(MainApp.class.getResource("view/PersonEditDialog.fxml"));
            AnchorPane page = (AnchorPane) loader.load();
            
            //Crea el stage de dialogo
            Stage dialogStage = new Stage();
            dialogStage.setTitle("Editar Persona");
            dialogStage.initModality(Modality.WINDOW_MODAL);
            dialogStage.initOwner(primaryStage);
            Scene scene = new Scene(page);
            dialogStage.setScene(scene);
            
            //Seteamos la persona en el controlador
            PersonEditDialogController controller = loader.getController();
            controller.setDialogStage(dialogStage);
            controller.setPerson(person);
            
            //Muestra el dialog y espera hasta que el usuario la cierra
            dialogStage.showAndWait();
            
            return controller.isOkClicked();
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }
    /**
     * Retorna al stage principal
     */
    public Stage getPrimaryStage(){
        return primaryStage;
    }
    /**  
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }
    
    /**
     * Retorna el archivo de persona de nuestra preferencia
     * 
     * @return
     */
    public File getPersonFilePath(){
        Preferences prefs =  Preferences.userNodeForPackage(MainApp.class);
        String filePath = prefs.get("filePath",null);
        if(filePath!=null){
            return new File(filePath);
        }else{
            return null;
        }
    }
    
    /**
     * Seteamos la direccion del archivo actual cargado. El path es persistido
     * en el registro del SO
     * 
     * @param file  el documento o null para remover el path
     */
    public void setPersonFilePath(File file){
        Preferences prefs = Preferences.userNodeForPackage(MainApp.class);
        if(file!=null){
            prefs.put("filePath", file.getPath());
            
            //Actualiza el stage del file
            primaryStage.setTitle("AgendaApp - "+file.getName());
        }else{
            prefs.remove("filePath");
            
            //Actualiza el stage del file
            primaryStage.setTitle("AgendaApp");
        }
    }
    
    /**
     * Carga la data de personas de un archivo especifico. La data actual sera
     * reemplazada.
     * 
     * @param file
     */
    public void loaderPersonDataFromFile(File file){
        try {
            JAXBContext context = JAXBContext
                    .newInstance(PersonListWrapper.class);
            Unmarshaller um = context.createUnmarshaller();
            
            //Leer xml del archivo y 
            PersonListWrapper wrapper = (PersonListWrapper) um.unmarshal(file);
            
            
        } catch (Exception e) {
        }
    }
}
