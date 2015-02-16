/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ch.makery.address.model;

import java.util.List;
import javax.xml.bind.annotation.XmlRootElement;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
/**
 *Clase de ayuda para envolver a una lista de personas. Esta es usada para guardar
 * la lista de personas a XML
 * @author kaze
 */
@XmlRootElement(name = "persons")
public class PersonListWrapper {
    private List<Person> persons;
    
    @XmlElement(name = "person")
    public List<Person> getPersons(){
        return persons;
    }
    
    public void setPersons(List<Person> persons){
        this.persons = persons;
    }
}
