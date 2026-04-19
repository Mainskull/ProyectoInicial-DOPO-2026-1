package dominio;

import shape.*;
import java.util.*;

/**
 * clase abstracta para definir los elementos del simulador
 * 
 * @author Juan Nieto, Daniel Valero
 * @version (a version number or a date)
 */
public abstract class Element{
    public static final int PX_X_CM = 30;
    
    protected int number;
    protected int height;
    protected int width;
    protected int posicionX;
    protected int posicionY;
    protected String color;
    
    protected Element base;
    protected Element coverlet;
    protected Container container;
    protected ArrayList<Figure> body;
    
    /**
     * genera un elemento
     * 
     * @param number numero que tendra el elemento
     * @param height altura del elemento
     * @param width base del elemento
     * @param posicionX posicion en la que se desea que se encuentre la mitad
     * del elemento
     * @param posicionY posicion en la que se desea que se encuentre la parte inferior
     * del elemento
     */
    public Element(int number, int height, int width, int posicionX, int posicionY, String color){
        this.number = number;
        this.height = height;
        this.width = width;
        this.posicionX = posicionX;
        this.posicionY = posicionY;
        this.color = color;
        
        base = null;
        coverlet = null;
        container = null;
        body = new ArrayList<>();
        
        bodyBuilder();
    }
    
    /*=====================================================
           
                    metodos abstractos
    
    =======================================================*/
    
    /**
     * posiciona la mitad del elemento en la posicion que se de en el
     * eje X
     * 
     * actualiza el la posicionX
     * 
     * @param posicionX lugar deseado en el eje X para la ubicacion
     */
    public abstract void posicionadorX(int xPosition);
    
    /**
     * posiciona la parte inferior del elemento en la posicion que se de en el
     * eje y, los numeros positivos aumentan para abajo
     * 
     * actualiza el la posicion top y la posicionY
     * 
     * @param posicionY lugar deseado en el eje Y para la ubicacion
     */
    public abstract void posicionadorY(int yPosition);
    
    /**
     * devuelve la informacion del objeto
     * 
     * @return un arreglo de String en el que la primera posicion es la clase
     * del elemento y en la segunda posicion el numero del elemento
     */
    public abstract String[] information();
    
    /**
     * le dice al elemento que un elemento caera encima de el y este elemento le dira en que va caer
     * al otro elemento.
     * 
     * @param element elemento dejado caer en este
     */
    public abstract void fallingElement(Element element);
    
    /*=====================================================
           
                    metodos implementados
    
    =======================================================*/
    /**
     * determina si el contenedor puede contener un elemento
     * 
     * @param elemento que se desea saber si puede ser contenido por este contenedor
     */
    public boolean isInContainer(Container container){
        boolean isInside = false;
        Container currentContainer = getContainer();
        while(currentContainer != null){
            if(currentContainer.equals(container)){
                isInside = true;
            }
            currentContainer = currentContainer.getContainer();
        }
        return isInside;
    }
    
    /**
     * hace que el elemento se elimine de las referencias de todos los objetos que lo referencien a el y luego hace que todo lo que
     * se afecte por su eliminacion se actualice. el elemento sigue existiendo pero se reinicia.
     * 
     * para eliminarlo se debe eliminar todas las referencias hacia el, no solo las que maneja este elemento
     * 
     * @param limitFalling es la posicion en la que se pondra la cubierta de este elemento en caso
     * de que tenga y no tenga un elemento en su base
     * 
     * @return true si fue posible eliminarlo, false en caso contrario
     */
    public boolean eliminate(int limitFall){
        //elementos con los que dejara de interactuar el elemento
        Element oldBase = getBase();
        Element oldCoverlet = getCoverlet();
        Container oldContainer = getContainer();
        
        //olvido de estos elementos
        setBase(null);
        setCoverlet(null);
        setContainer(null);
        
        if(base != null){
            base.setCoverlet(null);
        }
        if(coverlet != null){
            coverlet.setBase(null);
        }
        if(container != null){
            container.eliminateElement(this);
        }
        

        //colocar encima de la base lo que estaba encima de este elemento
        
        if(oldBase != null){
            oldBase.fallingElement(oldCoverlet);    
        }
        else{
            if(oldCoverlet != null){
                oldCoverlet.posicionadorY(limitFall);
                
                oldCoverlet.fallingElement(oldCoverlet.getCoverlet());
            }
        }
        
        return true;
    }
    
    /**
     * determina si un elemento es igual a este
     * 
     * @param elemento que se va a comparar con este 
     * 
     * @return true en caso de que su informacion sea igual, false si es diferente
     */
    public boolean equals(Object obj){
        boolean equals = false;
        if(obj instanceof Element){
            Element e = (Element) obj;
            equals = Arrays.equals(e.information(), information());
        }
        return equals;
    }
    
    /**
     * hace visible el cuerpo del elemento
     */
    public void makeVisible(){
        for (Figure fig : body) {
            fig.makeVisible();
        }  
    }
    
    /**
     * hace invisible el cuerpo del elemento
     */
    public void makeInvisible(){
        for (Figure fig : body) {
            fig.makeInvisible();
        }
    }
    
    /**
     * determina si el elemento tiene algo encima de el
     * 
     * @return true si tiene algo encima, false si no
     */
    public boolean isCovered(){
        return coverlet != null;
    }
    
    /*=====================================================
           
                    metodos auxiliares
    
    =======================================================*/
    
    /**
     * genera el cuerpo visual del elemento
     */
    protected abstract void bodyBuilder();
    
    /**
     * deja caer este elemento en la taza dada si tiene una cubierta se dejara caer en la cubierta, 
     * si no se vera si puede caer dentro de esta, si no se vera si puede cubrirla sin tener problemas 
     * con los contenedores que contienen directa o indirectamente a la copa, si hay algun problema se colocara
     * este elemento en el primer contenedor con el que se tenga un problema.
     * 
     * @param cup taza en la que se va a colocar el elemento
     */
    protected void fallInCup(Cup cup){
        if(cup.isCovered()){
            cup.getCoverlet().fallingElement(this);
        }else{
           if(cup.canContentIt(this)){
                cup.fallingInTopContent(this);
            }
            else{
                Container currentContainer = cup.getContainer();
            
                while(currentContainer != null){
                    if(!currentContainer.canContentIt(this)){
                        break;
                    }
                    currentContainer = currentContainer.getContainer();
                }
                
                if(currentContainer != null){
                    currentContainer.fallingInContainer(this);
                }
                else{
                    cup.putAsCover(this);
                    Element tempCoverlet = getCoverlet();
                    this.fallingElement(tempCoverlet);
                }
            }
        }
    }
    
    /**
     * deja caer este elemento en la taza dada, se supone que el elemento no
     * tiene informacion, como si estuviera recien creado, solo la info crucial
     * sin interaccion con otros elementos
     * 
     * @param lid tapa en la que se va a colocar el elemento
     */
    protected void fallInLid(Lid lid){
        if(lid.isCovered()){
            lid.getCoverlet().fallingElement(this);
        }else{
            Container currentContainer = lid.getContainer();
            
            while(currentContainer != null){
                if(!currentContainer.canContentIt(this)){
                    break;
                }
                currentContainer = currentContainer.getContainer();
            }
            
            if(currentContainer != null){
                currentContainer.fallingInContainer(this);
            }
            else{
                lid.putAsCover(this);
                Element tempCoverlet = getCoverlet();
                this.fallingElement(tempCoverlet);
            }
        
        }
    }
    
    /**
     * prepara al elemento para caer, actualizando la informacion de este elemento
     * ademas de actualizar a los elementos que tengan que ver con el para que no haya 
     * inconsistencias en el proceso de caida
     * 
     */
    protected void prepareFalling(){
        if(container != null){
            container.eliminateElement(this);
        }
        if(base != null){
            base.setCoverlet(null);
            setBase(null);
        }
    }
    
    /**
     * coloca el elemento dado como cubierta de este y actualiza la informacion tanto al elemento dado como a este 
     * 
     * @param elemento que cubrira a este
     */
    protected void putAsCover(Element element){
        //este elemento se vuelve la base del elemento dado
        element.setBase(this);
        //se posiciona el elemento justo encima de este
        element.posicionadorY(getPosYTop());
        //ya que se posiciono como cubierta de este elemento su contenedor sera el mismo que el de este
        Container containerForElement = getContainer();
        element.setContainer(containerForElement);
        /*si el contenedor es null significa que el elemento esta afuera de cualquier contenedor
           pero si no se debe insertar al elemento dentro del contenido del contenedor en una posicion
           por encima de la que se encuentra este*/
        if(containerForElement != null){
            containerForElement.insertElement(element, this);
        }
        
        setCoverlet(element);
    }
    
    /*====================================================
       
                       getters y setters
       
    ======================================================*/
    
    /**
     *devuelve la posicion de la cima del elemento
     *
     *@return devuelve la posicionY de la cima del elemento
     */
    public int getPosYTop(){
        return posicionY - height*PX_X_CM;
    }
    
    /**
     * devuelve el numero asignado a este elemento
     * 
     * @return el numero asignado a este elemento
     */
    public int getNumber(){
        return number;
    }
    
    /**
     * devuelve la posicionX que tiene este elemento
     * 
     * @return numero que dice la posicion en del centro del elemento en el eje x
     */
    public int getPosicionX(){
        return posicionX;
    }
    
    /**
     * devuelve la posicionY que tiene este elemento
     * 
     * @return numero que dice la posicion en pantalla de la parte inferior del elemento
     * en el eje y
     */
    public int getPosicionY(){
        return posicionY;
    }
    
    /**
     * devuelve el elemento en la base en el que se encuentra este elemento
     */
    public Element getBase(){
        return base;
    }
    
    /**
     * devuelve el contenerdor del elemento
     * 
     * @return devuelve el contenedor actual en el que se encuentra 
     * el elemento
     */
    public Container getContainer(){
        return container;
    }
    
    /**
     * devuelve la cubierta del elemento
     * 
     * @return devuelve el elemento que esta encima cubriendo a este
     */
    public Element getCoverlet(){
        return coverlet;
    }
    
    /**
     * devuelve el elemento que se encuentra encima de cualquier otro 
     * elemento que este contenido en esta copa
     * 
     * @return elemento en la cima dentro del actual, en caso de que no tengo elementos retorna null
     */
    public Element getTopContent(){
        //un elemento en si, no contiene nada por lo que no posee un topContent
        return null;
    }
    
    /**
     * vuelve al elemento dado la base de este
     * 
     * @param newBase es el elemento que se desea colocar como base de este
     */
    public void setBase(Element newBase){
        base = newBase;
    }
    
    /**
     * vuelve el contenerdor del elemento, el dado.
     * 
     * @param container contenedor que asignar al elemento
     */
    public void setContainer(Container newContainer){
        container = newContainer;
    }
    
    /**
     * vuelve al elemento dado la cubierta de este
     * 
     * @param newCoverlet es el elemento que se desea colocar como cubierta de este
     */
    public void setCoverlet(Element newCoverlet){
        coverlet = newCoverlet;
    }
}






