package dominio;


/**
 * Write a description of class StackingItemsException here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class StackingItemsException extends Exception
{
    public static final String ELEMENT_NOT_fOUND = "el elemento con la informacion dada no se encuentra en la torre";

    /**
     * Constructor for objects of class StackingItemsException
     */
    public StackingItemsException(String message)
    {
        super(message);
    }

    
}