
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
     * genera el cuerpo visual de la tapa en el arreglo
     */
    @Override
    protected void bodyBuilder(){
        int posYBase = posicionY - PX_X_CM;
        int posXBase = posicionX - ((PX_X_CM*getWidth())/2);
        //base y cuerpo de la tapa
        body.add(new Rectangle(posXBase, posYBase, PX_X_CM, PX_X_CM*getWidth(), color));
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
        posicionX = posicionX - ((PX_X_CM*getWidth())/2);
        body.get(0).positionHorizontal(posicionX);
    }
    
    /**
     * posiciona el centro de la tapa en el 
     * lugar correspondiente en el eje Y.
     * 
     * @param  posicionY  es un numero que dice donde desea que este el lado
     * inferior de la tapa en el eje Y
     */
    @Override
    public void posicionadorY(int posicionY){
        /*actualiza la posicion de la cima y la base de la tapa y mueve el
           cuerpo*/
        posicionY = posicionY - PX_X_CM;
        top = posicionY;
        base = posicionY;
        body.get(0).positionVertical(posicionY);
    }
    
    /**
     * dice que este objeto es de la clase Lid
     * 
     * @return String Lid
     */
    public String item(){
        return "Lid";
    }
}