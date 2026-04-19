package dominio;

import shape.*;

/**
 * lid representa una tapa de una copa, puede ser manipulado sus datos y
 * ser representado visualmente por medio de un rectangulo
 * 
 * @author Daniel Valero y Juan Sebastian Nieto 
 * @version 14.02.2026 
 */
public class Lid extends Element{
    
    
    /**
     * Constructor for objects of class Lid
     * 
     * @param number es el numero asignado a la tapa
     * @param posicionX es la ubicacion en el eje X que deseas que tenga 
     * en pantalla, el centro de la tapa.
     * @param posicionY es la ubicacion en el eje Y que deseas que tenga 
     * en pantalla, el centro de la tapa.
     * @param color sera el color que tenga la tapa solo estan
     * permitidos "red", "yellow", "blue", "green", "magenta" y "black"
     */
    public Lid(int number, int posicionX, int posicionY, String color) {
        super(number, 1, 2*number-1, posicionX, posicionY, color);
    }
    
    /**
     * posiciona el centro de la tapa en el 
     * lugar deseado en el eje X.
     * 
     * @param  posicionX es un numero que dice donde desea que este la mitad
     * de la tapa en el eje X
     */
    @Override
    public void posicionadorX(int posicionX){
        /*se le resta la mitad del ancho a la posicion 
         * ya que asi se posiciona donde se desea*/
        this.posicionX = posicionX;
        body.get(0).posicionadorX(posicionX - ((PX_X_CM*width)/2));
    }
    
    /**
     * posiciona el centro de la tapa en el 
     * lugar correspondiente en el eje Y.
     * 
     * @param  posicionY  es un numero que dice donde desea que este el lado
     * inferior de la tapa en el eje Y
     */
    @Override
    public void posicionadorY(int yPosition){
        /*actualiza la posicion de la cima y la base de la tapa y mueve el
           cuerpo*/
        this.posicionY = yPosition;
        body.get(0).posicionadorY(yPosition - PX_X_CM);
    }
    
    /**
     * le dice al elemento dado que caera en una clase especifica para que sepa
     * como debe reaccionar
     * 
     * @param element es el elemento al que se le dara la orden
     */
    @Override
    public void fallingElement(Element element){
        if(element != null){
            element.fallInLid(this);
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
        return new String[]{"lid", String.valueOf(number)};
    }
    
    /*=============================================================================
       
                                 metodos auxiliares
       
    ===============================================================================*/
    
    /**
     * genera el cuerpo visual de la tapa en el arreglo
     */
    @Override
    protected void bodyBuilder(){
        int posYBase = posicionY - PX_X_CM;
        int posXBase = posicionX - ((PX_X_CM*width)/2);
        //base y cuerpo de la tapa
        body.add(new Rectangle(posXBase, posYBase, PX_X_CM, PX_X_CM*width, color));
    }
}