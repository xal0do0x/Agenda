/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ch.makery.address.view;

import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import org.controlsfx.dialog.Dialogs;

import ch.makery.address.model.Person;
import ch.makery.addres.util.DateUtil;
/**
 *Cuadrod de dialogo para editar personas
 * @author kaze
 */
public class PersonEditDialogController {
    @FXML
    private TextField firstNameField;
    @FXML
    private TextField lastNameField;
    @FXML
    private TextField streetField;
    @FXML
    private TextField postalCodeField;
    @FXML
    private TextField cityField;
    @FXML
    private TextField birthdayField;
    
    private Stage dialogStage;
    private Person person;
    private boolean okClicked  = false;
    
    /**
     * Inicializamos la clase del controlador. Este metodo es automaticamente llamado 
     * despues de que el archivo fxml es cargado.
     */
    @FXML
    private void initialize(){
        
    }
    
    
    /**
     * Seteamos el stage de este dialog
     * 
     * @param dialogStage 
     */
    public void setDialogStage(Stage dialogStage){
        this.dialogStage = dialogStage;
    }
    
    /**
     * Seteamos la persona que sera editada en el dialog
     * 
     * @param person
     */
    public void setPerson(Person person){
        this.person = person;
        
        firstNameField.setText(person.getFirstName());
        lastNameField.setText(person.getLastName());
        streetField.setText(person.getStreet());
        postalCodeField.setText(Integer.toString(person.getPostalCode()));
        cityField.setText(person.getCity());
        birthdayField.setText(DateUtil.format(person.getBirthday()));
        birthdayField.setPromptText("dd.mm.yyyy");
    } 
    
    /**
     * Retorna  verdadero si el usuario clickeado esta ok, false en otros casos
     * 
     * @return
     */
    public boolean isOkClicked(){
        return okClicked;
    }
    
    /**
     * Llamado cuando el usuario da click en ok
     */
    @FXML
    private void handleOk(){
        if(isInputValid()){
            person.setFirstName(firstNameField.getText());
            person.setLastName(lastNameField.getText());
            person.setStreet(streetField.getText());
            person.setPostalCode(Integer.parseInt(postalCodeField.getText()));
            person.setCity(cityField.getText());
            person.setBirthday(DateUtil.parse(birthdayField.getText()));
            
            okClicked = true;
            dialogStage.close();
        }
    }
    
    /**
     * Llamado  cuando el usuario da click en cancelar
     */
    @FXML
    private void handleCancel(){
        dialogStage.close();
    }
    
    /**
     * Valida la entrada de texto en los campos de texto
     * 
     * @return true si la entrada es valida
     */
    private boolean isInputValid(){
        String errorMessage = "";
        
        if(firstNameField.getText() == null || firstNameField.getText().length() == 0){
            errorMessage += "Nombre no valido";
        }
        if(lastNameField.getText() == null || lastNameField.getText().length() == 0){
            errorMessage += "No es un apellidos valido";
        }
        if(streetField.getText() == null || streetField.getText().length() == 0){
            errorMessage += "No es una calle valida \n";
        }
        
        if (postalCodeField.getText() == null || postalCodeField.getText().length() == 0) {
            errorMessage += "No valid postal code!\n"; 
        } else {
            // try to parse the postal code into an int.
            try {
                Integer.parseInt(postalCodeField.getText());
            } catch (NumberFormatException e) {
                errorMessage += "No valid postal code (must be an integer)!\n"; 
            }
        }
        
        if (cityField.getText() == null || cityField.getText().length() == 0) {
            errorMessage += "No valid city!\n"; 
        }
        
        if (birthdayField.getText() == null || birthdayField.getText().length() == 0) {
            errorMessage += "No valid birthday!\n";
        } else {
            if (!DateUtil.validDate(birthdayField.getText())) {
                errorMessage += "No valid birthday. Use the format dd.mm.yyyy!\n";
            }
        }
        
        if (errorMessage.length() == 0) {
            return true;
        } else {
            // Show the error message.
            Dialogs.create()
                .title("Invalid Fields")
                .masthead("Please correct invalid fields")
                .message(errorMessage)
                .showError();
            return false;
        }
    }
}