package dominio;


/**
 * Write a description of interface Container here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */

public interface Container{
    
    /**
     * determina si el contenedor puede contener un elemento
     * 
     * @param elemento que se desea saber si puede ser contenido por este contenedor
     */
    boolean canContentIt(Element element);
    
    /**
     * le dice al elemento dado que se deje caer en el topContent de de este contenedor
     * en caso de que no tenga lo vuelve su topContent
     * 
     * @param element elemento que se dejara caer en en el topContent
     */
    void fallingInTopContent(Element element);
    
    /**
     * pone al elemento dado, en el elemento en la cima (topContent) de lo contenido en
     * el contendor y lo añade al registro de las cosas que tiene contenidas este
     * contenedor
     * 
     * @param element elemento que se desea colocar en el contenido de este contenedor
     */
    void insertElement(Element element);
    
    /**
     * pone al elemento dado, en el elemento en la cima (topContent) de lo contenido en
     * el contendor y lo añade al registro de las cosas que tiene contenidas este
     * contenedor
     * 
     * @param element elemento que se desea colocar en el contenido de este contenedor
     * @param base elemento que estara debajo del que se colocara en el contenido
     * y ayudara a posicionarlo encima de este.
     */
    void insertElement(Element element, Element base);
    
    /**
     * devuelve el elemento que esta en la cima de los elementos contenidos en este
     * contenedor
     * 
     * @return devuelve el elemento en la cima de lo contenido
     */
    Element getTopContent();
    
    /**
     * devuelve el contenedor en el que se puede encontrar este
     * 
     * @return devuelve el contenedor en el que esta este o null en caso de que no este
     */
    Container getContainer();
    
    /**
     * devuelve la posicion del eje Y en la que se encuentra la base interior del
     * contenedor
     * 
     * @return posicion Y de la base interior
     */
    int getPosYBase();
    
}