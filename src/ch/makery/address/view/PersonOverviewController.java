/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ch.makery.address.view;

import ch.makery.addres.util.DateUtil;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import ch.makery.address.MainApp;
import ch.makery.address.model.Person;
import org.controlsfx.dialog.Dialogs;
/**
 *
 * @author kaze
 */
public class PersonOverviewController {
    @FXML
    private TableView<Person> personTable;
    @FXML
    private TableColumn<Person,String> firstNameColumn;
    @FXML
    private TableColumn<Person,String> lastNameColumn;
    
    @FXML
    private Label firstNameLabel;
    @FXML
    private Label lastNameLabel;
    @FXML
    private Label streetLabel;
    @FXML
    private Label postCodeLabel;
    @FXML
    private Label cityLabel;
    @FXML
    private Label birthdayLabel;
    
    //Refencia a la clase principal
    private MainApp mainApp;
    
    /**
     * El constructor
     * es llamado despues de la inicializacion del metodo
     */
    public PersonOverviewController(){}
    
    /**
     * Inicializamos el controllador de clase. Este metodo es automaticamente llamado
     * despues de que el archivo fxml ha sido cargado.
     */
    @FXML
    private void initialize(){
        //Inicializamos la tabla person con las dos columnas
        firstNameColumn.setCellValueFactory(cellData -> 
                cellData.getValue().firstNameProperty());
        lastNameColumn.setCellValueFactory(cellData -> 
                cellData.getValue().lastNameProperty());
        
        //Limpiamos detalles de persona
        showPersonDetails(null);
        
        //Escuchamos los cambios de seleccion y mostramos los detalles de persona cuando cambiamos
        personTable.getSelectionModel().selectedItemProperty().addListener(
            (observable, oldValue, newValue) -> showPersonDetails(newValue));
    }
    
    /**
     * Es llamada por la aplicacion principal  para dar  una referencia para regresar a si misma
     * @param mainApp
     */
    public void setMainApp(MainApp mainApp){
        this.mainApp = mainApp;
        
        //agregamos una  lista observable de data a la tabla
        personTable.setItems(mainApp.getPersonData());
    }
    
    /**
     * Llena todos los campos de texto para mostrar los detalles sobre la persona
     * Si la persona especifica esta vacia, todos los textos deben  limpiarse
     * @param person la persona o vacio
     */
    private void showPersonDetails(Person person){
        if (person != null) {
            //Llenar los labels con info del objeto persona
            firstNameLabel.setText(person.getFirstName());
            lastNameLabel.setText(person.getLastName());
            streetLabel.setText(person.getStreet());
            cityLabel.setText(person.getCity());
            postCodeLabel.setText(Integer.toString(person.getPostalCode()));
            birthdayLabel.setText(DateUtil.format(person.getBirthday()));
       }else{
            //Person es null, removemos todo el texto
            firstNameLabel.setText("");
            lastNameLabel.setText("");
            streetLabel.setText("");
            cityLabel.setText("");
            postCodeLabel.setText("");
            birthdayLabel.setText("");
       }
    }
    
    /**
     * Llamado cuando el usuario da click en el boton Nuevo. Abre el dialog para 
     * editar los detalles de la nueva persona.
     */
    @FXML
    private void handleNewPerson(){
        Person tempPerson = new Person();
        boolean okClicked = mainApp.showPersonEditDialog(tempPerson);
        if(okClicked){
            mainApp.getPersonData().add(tempPerson);
        }
    }
   
    /**
     *Llamado cuando el usuario da click en el boton Editar. Abre el dialog  para editar
     * los detalles de la persona seleccionada.
     */
    @FXML
    private void handleEditPerson(){
        Person selectPerson = personTable.getSelectionModel().getSelectedItem();
        if(selectPerson != null){
            boolean okClicked =  mainApp.showPersonEditDialog(selectPerson);
            if(okClicked){
                showPersonDetails(selectPerson);
            }
        }else{
            //Nada seleccionado
            Dialogs.create()
                    .title("No seleccionado")
                    .masthead("No se selecciono una persona")
                    .message("Por favor selecciona una persona de la lista")
                    .showWarning();
        }
    }
    
    /**
     * Llamado cuando el usuarios presiona el boton de delete
     */
    @FXML
    private void handleDeletePerson(){
        int selectedIndex =  personTable.getSelectionModel().getSelectedIndex();
        if(selectedIndex >= 0 ){
            personTable.getItems().remove(selectedIndex);
        }else{
            //Nada seleccionado
            Dialogs.create()
                    .title("No hay selección")
                    .masthead("No hay persona seleccionada")
                    .message("Por favor selecciona una persona de la lista")
                    .showWarning();        
        }
        
        
    }
}
