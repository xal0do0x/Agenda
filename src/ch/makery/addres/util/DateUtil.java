/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ch.makery.addres.util;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Date;

/**
 *
 * @author kaze
 */
public class DateUtil {
    
    /**El patron de fecha es usado para la conversion. Cambialo si lo deseas*/
    private static final String DATE_PATTERN = "dd.MM.yyyy";
    
    /**El formateador de fechas*/
    private static final DateTimeFormatter DATE_FORMATTER = 
            DateTimeFormatter.ofPattern(DATE_PATTERN);
    
    /**
     * Retorna la fecha dada con un formato en string. Lo definido anteriormente 
     * {@link DateUtil#DATE_PATTERN} es usado
     * 
     * @param date la fecha para que sea retornada como un string
     * @return string formateado
     */
    public static String format(LocalDate date){
        if(date == null){
            return null;
        }
        return DATE_FORMATTER.format(date);
    }
        
    /**
     * Convierte el string en un formato definido {@link DateUtil#DATE_PATTERN}
     * to a {@link LocalDate} object.
     * 
     * Retorna nulo si el string no puede ser convertido
     * 
     * @param dateString la fecha es un string
     * @return El objeto date o nulo si este no se puede convertir
     */
    public static LocalDate parse(String dateString){
        try {
            return DATE_FORMATTER.parse(dateString, LocalDate::from);
        } catch (DateTimeParseException e) {
            return null;
        }
    }
    
    /**
     * Verifica si el string esta validado para una fecha
     * 
     * @param dateString
     * @return true si es el string es una date valido
     */
    public static boolean validDate(String dateString){
        //Prueba analizar el string
        return DateUtil.parse(dateString)!= null;
    }
    
}
