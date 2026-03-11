
/**
 * lid representa una tapa de una copa, puede ser manipulado sus datos y
 * ser representado visualmente por medio de un rectangulo
 * 
 * @author Daniel Valero y Juan Sebastian Nieto 
 * @version 14.02.2026 
 */
public class Lid
{
    public static final int height = 1;
    public static final int PxPerCm = 30;
    
    private int number;
    private int width;
    private boolean isliding;
    private Rectangle Body;
    private int posicionX;
    private int posicionY;

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
        this.number = number;
        width = 2*number - 1; //el ancho sera igual a el tamaño de las tazas
        this.posicionX = posicionX;
        this.posicionY = posicionY;
        
        int posXBody = posicionX - ((width*PxPerCm)/2);
        int posYBody = posicionY - PxPerCm;
        int anchura = width*PxPerCm;
        int altura = height*PxPerCm;
        
        Body = new Rectangle(posXBody, posYBody, altura, anchura, color);
        
    }

    /**
     * posiciona el centro de la tapa en el 
     * lugar deseado en el eje X.
     * 
     * @param  posicionX es un numero que dice donde desea que este la mitad
     * de la tapa en el eje X
     */
    public void posicionadorX(int posicionX){
        /*se le resta la mitad del ancho a la posicion 
         * ya que asi se posiciona donde se desea*/
        posicionX = posicionX - ((width*PxPerCm)/2);
        Body.positionHorizontal(posicionX);
    }
    
    /**
     * posiciona el centro de la tapa en el 
     * lugar correspondiente en el eje Y.
     * 
     * @param  posicionY  es un numero que dice donde desea que este el lado
     * inferior de la tapa en el eje Y
     */
    public void posicionadorY(int posicionY){
        posicionY = posicionY - PxPerCm;
        Body.positionVertical(posicionY);
    }
    
    /**
     * retorna el numero que posee la tapa
     * 
     * @return el numero que se le asigno a la tapa
     */
    public int getNumber(){
        return number;
    }
    
    /**
     * retorna la anchura que posee la tapa
     * 
     * @return anchura de la tapa
     */
    public int getwidth(){
        return width;
    }
    
    /**
     * vuelve visible la tapa en el canvas
     */
    public void makeVisible(){
        Body.makeVisible();
    }
    
    /**
     * vuelve invisible la tapa en el canvas
     */
    public void makeInvisible(){
        Body.makeInvisible();
    }
    
    //pendiente
    /**
     * retorna la posicion y dada para la creacion de la tapa
     * 
     * @return retorna un entero que dice en que posicionY se dio para posicionar el lado inferior
     * de la tapa
     */
    public int getPosicionY(){
    return 1;    
    }
}