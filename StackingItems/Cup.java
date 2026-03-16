import java.util.*;

/**
 * Cup representa una taza que guarda datos de esta y ademas puede ser
 * representado visualmente utilizando varios rectangulos.
 * 
 * @author Daniel Valero y Juan Sebastian Nieto 
 * @version 14.02.2026
 */
public class Cup extends Element{
    
    private Element topContent;

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
    public Cup(int number, int posicionX, int posicionY, String color) {
        super(number, 2*number - 1, 2*number - 1, posicionX, posicionY, color);
        topContent = null;
    }
    
    /**
     * le da la estructura de una taza a los rectangulos dentro
     * del arreglo del cuerpo.
     */
    @Override
    protected void bodyBuilder(){
        
        int posYPared = posicionY - PX_X_CM*getHeight();
        int posXParedIzquierda = posicionX - ((PX_X_CM*getWidth())/2);
        int alturaParedes = (getHeight()-1)*PX_X_CM;
        //pared izquierda de la taza
        body.add(new Rectangle(posXParedIzquierda, posYPared, alturaParedes, PX_X_CM, color));
        
        int posXParedDerecha = posicionX + ((PX_X_CM*getWidth())/2 - PX_X_CM);
        //pared derecha de la taza
        body.add(new Rectangle(posXParedDerecha, posYPared, alturaParedes, PX_X_CM, color));
        
        int posYBase = posicionY - PX_X_CM;
        int posXBase = posicionX - ((PX_X_CM*getWidth())/2);
        //base de la taza
        body.add(new Rectangle(posXBase, posYBase, PX_X_CM, PX_X_CM*getWidth(), color));
    }
    
    /**
     * posiciona el centro de la base de la taza en el 
     * lugar deseado en el eje X.
     * 
     * @param  posicionX    es un numero que dice donde desea que este el
     * centro la base de la taza en el eje X
     */
    @Override
    public void posicionadorX(int posicionX){
        /*posiciona la pared izquierda en su ubicacion moviendola 
         * junto al extremo izquiedo de la base*/
        int posicionX0 = posicionX - ((PX_X_CM*getWidth())/2);
        body.get(0).positionHorizontal(posicionX0);
        
        /*posiciona el centro de la base en el lugar indicado en 
         * el eje X*/
        
        int posicionX1 = posicionX - ((PX_X_CM*getWidth())/2);
        body.get(1).positionHorizontal(posicionX1);
        
        /*posiciona la pared derecha en su ubicacion moviendola 
         * junto al extremo derecho de la base*/
        int posicionX2 = posicionX + ((PX_X_CM*getWidth())/2 -PX_X_CM);
        body.get(2).positionHorizontal(posicionX2);
    }
    
    /**
     * posiciona el centro de la tapa en el 
     * lugar correspondiente en el eje Y.
     * 
     * @param  posicionY es un numero que dice donde desea que este el
     * centro la tapa en el eje Y
     */
    @Override
    public void posicionadorY(int posicionY){        
        /*posciona las paredes encima de la base y actualiza el top*/
        int posicionYP = posicionY - PX_X_CM*getHeight();
        body.get(0).positionVertical(posicionYP);
        body.get(2).positionVertical(posicionYP);
        top = posicionYP;
        
        /*posiciona el lado inferior de la base en la posicion indicada
           en el eje Y y actualiza el base*/
        int posicionYB = posicionY - PX_X_CM;
        body.get(1).positionVertical(posicionYB);
        base = posicionYB;
    }

    /**
     * dice que el objeto es de la clase cup
     * 
     * @return String "Cup"
     */
    @Override
    public String item(){
        return "Cup";
    }
    
    /**
     * devuelve el elemento que se encuentra encima de cualquier otro 
     * elemento que este contenido en esta copa
     * 
     * @return elemento en la cima dento del actual
     */
    @Override
    public Element getTopContent(){
        return topContent;
    }
    
    /**
     * hace que la cima de lo que tiene dentro de si el elemento actual sea
     * un elemento dado
     * 
     * @param elemento elemento que se desea poner en la cima de lo contenido
     * en este
     */
    @Override
    public void setTopContent(Element elemento){
        topContent = elemento;
    }
    
    /**
     * determina si el elemento contiene algun elemento dentro de si
     * 
     * @return true si contiene algo, false si no
     */
    @Override
    public boolean containsSomething(){
        return topContent != null;
    }
    
}

