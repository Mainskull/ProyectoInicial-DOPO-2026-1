package dominio;

import shape.*;
import java.util.*;

/**
 * Cup representa una taza que guarda datos de esta y ademas puede ser
 * representado visualmente utilizando varios rectangulos.
 * 
 * @author Daniel Valero y Juan Sebastian Nieto 
 * @version 14.02.2026
 */
public class Cup extends Element implements Container{    
    
    private ArrayList<Element> content; 

    /**
     * Constructor for objects of class Cup
     * 
     * @param number es el numero asignado a la taza
     * @param posicionX es la ubicacion en el eje X que deseas que tenga 
     * en pantalla, el centro de la base de la taza.
     * @param posicionY es la ubicacion en el eje Y que deseas que tenga 
     * en pantalla, el lado inferior de la base de la taza.
     * @param color sera el color que tenga la taza solo estan
     * permitidos "red", "yellow", "blue", "green", "magenta" y "black"
     */
    public Cup(int number, int posicionX, int posicionY, String color){
        super(number, 2*number - 1, 2*number - 1, posicionX, posicionY, color);
        content = new ArrayList<>();
    }
    
    /*=====================================================
       
                    metodos Element
    
    =======================================================*/
    
    /**
     * posiciona el centro de la base de la taza en el 
     * lugar deseado en el eje X.
     * 
     * @param  posicionX    es un numero que dice donde desea que este el
     * centro la base de la taza en el eje X
     */
    @Override
    public void posicionadorX(int xPosition){
        this.posicionX = xPosition;
        
        /*posiciona la pared izquierda en su ubicacion moviendola 
         * junto al extremo izquiedo de la base*/
        int posicionX0 = xPosition - ((PX_X_CM*width)/2);
        body.get(0).posicionadorX(posicionX0);
        
        /*posiciona el centro de la base en el lugar indicado en 
         * el eje X*/
        
        int posicionX1 = xPosition - ((PX_X_CM*width)/2);
        body.get(2).posicionadorX(posicionX1);
        
        /*posiciona la pared derecha en su ubicacion moviendola 
         * junto al extremo derecho de la base*/
        int posicionX2 = xPosition + ((PX_X_CM*width)/2 -PX_X_CM);
        body.get(1).posicionadorX(posicionX2);
        
        for(Element e: content){
            e.posicionadorX(xPosition);
        }
        
    }
    
    /**
     * posiciona el centro de la tapa en el 
     * lugar correspondiente en el eje Y.
     * 
     * @param  posicionY es un numero que dice donde desea que este el
     * centro la tapa en el eje Y
     */
    @Override
    public void posicionadorY(int yPosition){        
        this.posicionY = yPosition;
        
        /*posciona las paredes encima de la base y actualiza el top*/
        int posicionYP = yPosition - PX_X_CM*height;
        body.get(0).posicionadorY(posicionYP);
        body.get(1).posicionadorY(posicionYP);
        
        /*posiciona el lado inferior de la base en la posicion indicada
           en el eje Y y actualiza el base*/
        int posicionYB = yPosition - PX_X_CM;
        body.get(2).posicionadorY(posicionYB);
        
        for(Element e: content){
            e.posicionadorY(yPosition);
        }
    }
    
    /**
     * devuelve la informacion del objeto
     * 
     * @return un arreglo de String en el que la primera posicion es la clase
     * del elemento y en la segunda posicion el numero del elemento
     */
    @Override
    public String[] information(){
        return new String[]{"Cup", String.valueOf(number)};
    }
    
    /**
     * le dice a elemento dado que esta cayendo en una clase especifica para que sepa como reaccionar
     * 
     * @param element es el elemento al que se le dara la orden
     */
    public void fallingElement(Element element){
        if(element != null){
            element.fallInCup(this);
        }
    }
    
    /*=====================================================
       
                    metodos Container
    
    =======================================================*/
    
    /**
     * determina si el contenedor puede contener un elemento
     * 
     * @param elemento que se desea saber si puede ser contenido por este contenedor
     * 
     * @return true si el elemento no esta cubierta o si el numero es mayor que el
     * de esta copa y el top content de este se encuentra dentro de la copa
     */
    public boolean canContentIt(Element element){
        boolean canContent = false;
        
        Element topContent = getTopContent();
        
        int numberElement = element.getNumber();
        int numberThis = getNumber();
        
        boolean isNotCovered = !isCovered();
        boolean insideMe = element.isInContainer(this);
        boolean elementFits = numberElement < numberThis;
        boolean topElementOutsideThis = false;
        
        if(topContent != null){
            int posYTop = topContent.getPosYTop();
            int posY = getPosYTop();
            
            topElementOutsideThis = posYTop < posY;
        }
        
        if((isNotCovered || insideMe) && (elementFits || topElementOutsideThis)){
            canContent = true;
        }
    
        return canContent;
    }
    
    /**
     * pone el elemento dado en el TopContent de la copa si tiene ya que cupo en ella o coloca el elemento dentro de la copa, 
     * ademas añade el elemento al contenido de la copa y lo posiciona correctamente dentro de el.
     * 
     * @param elemento que se coloca dentro de la copa en la cima del contenido de la copa
     */
    public void fallingInTopContent(Element element){
        if(element != null){
            Element topContent = getTopContent();
            
            if(topContent != null){
                topContent.fallingElement(element);
            }
            else{
                element.setBase(this);
                element.posicionadorY(getPosYBase());
                
                element.setContainer(this);
                insertElement(element);
            }
        }
    }
    
    /**
     * pone al elemento dado, en el elemento en la cima (topContent) de lo contenido en
     * el contendor y lo añade al registro de las cosas que tiene contenidas este
     * contenedor
     * 
     * @param element elemento que se desea colocar en el contenido de este contenedor
     */
    public void insertElement(Element element){
        if(content != null){
            content.add(element);
        }
    }
    
    /**
     * pone al elemento dado, en el elemento en la cima (topContent) de lo contenido en
     * el contendor y lo añade al registro de las cosas que tiene contenidas este
     * contenedor
     * 
     * @param element elemento que se desea colocar en el contenido de este contenedor
     */
    public void insertElement(Element element, Element base){
        int indx = 1;

        for(Element e: content){
            if(e.equals(base)){
                break;
            }
            indx++;
        }
        
        if(content != null){
            if(0 < indx && indx< content.size()){
                content.add(indx, element);
            }
            else{
                content.add(element);
            }
        }
    }
    
    /**
     * devuelve el elemento que se encuentra encima de cualquier otro 
     * elemento que este contenido en esta copa
     * 
     * @return elemento en la cima dentro del actual, en caso de que no tengo elementos retorna null
     */
    @Override
    public Element getTopContent(){
        Element topContent = null;
        if(content.size()!= 0){
            topContent = content.get(content.size() - 1);
        }
        return topContent;
    }
    
    /**
     * devuelve la posicion del eje Y en la que se encuentra la base interior del
     * contenedor
     * 
     * @return posicion Y de la base interior
     */
    public int getPosYBase(){
        return posicionY - PX_X_CM;
    }
    
    /*=====================================================
       
                    metodos auxiliares
    
    =======================================================*/
    
    /**
     * le da la estructura de una taza a los rectangulos dentro
     * del arreglo del cuerpo.
     */
    @Override
    protected void bodyBuilder(){
        
        int posYPared = posicionY - PX_X_CM*height;
        int posXParedIzquierda = posicionX - ((PX_X_CM*width)/2);
        int alturaParedes = (height-1)*PX_X_CM;
        //pared izquierda de la taza
        body.add(new Rectangle(posXParedIzquierda, posYPared, alturaParedes, PX_X_CM, color));
        
        int posXParedDerecha = posicionX + ((PX_X_CM*width)/2 - PX_X_CM);
        //pared derecha de la taza
        body.add(new Rectangle(posXParedDerecha, posYPared, alturaParedes, PX_X_CM, color));
        
        int posYBase = posicionY - PX_X_CM;
        int posXBase = posicionX - ((PX_X_CM*width)/2);
        //base de la taza
        body.add(new Rectangle(posXBase, posYBase, PX_X_CM, PX_X_CM*width, color));
    }
}

