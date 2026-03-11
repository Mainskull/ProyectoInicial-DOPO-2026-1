
/**
 * Cup representa una taza que guarda datos de esta y ademas puede ser
 * representado visualmente utilizando varios rectangulos.
 * 
 * @author Daniel Valero y Juan Sebastian Nieto 
 * @version 14.02.2026
 */
public class Cup
{
    
    public static final int PxPerCm = 30; 
    
    private int number;
    private int size;
    private boolean Lided;
    private Rectangle[] Body = new Rectangle[3];
    private int posicionX;
    private int posicionY;


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
        this.number = number;
        size = 2*number - 1;
        Lided = false;
        this.posicionX = posicionX;
        this.posicionY = posicionY;
        creadorCuerpo(posicionX, posicionY, color);
        

    }
    
    /**
     * le da la estructura de una taza a los rectangulos dentro
     * del arreglo del cuerpo.
     * 
     * @param posicionX posicion inicial en el eje x de la mitad de la taza
     * @param posicionY posicion inicial en el eje Y del lado inferior de la
     * base de la taza
     */
    private void creadorCuerpo(int posicionX, int posicionY, String color){
        
        int posYPared = posicionY - PxPerCm*size;
        int posXParedIzquierda = posicionX - ((size*PxPerCm)/2);
        int alturaParedes = (size-1)*PxPerCm;
        //pared izquierda de la taza
        Body[0] = new Rectangle(posXParedIzquierda, posYPared, alturaParedes, PxPerCm, color);
        
        int posXParedDerecha = posicionX + ((size*PxPerCm)/2 - PxPerCm);
        //pared derecha de la taza
        Body[2] = new Rectangle(posXParedDerecha, posYPared, alturaParedes, PxPerCm, color);
        
        int posYBase = posicionY - PxPerCm;
        int posXBase = posicionX - ((size*PxPerCm)/2);
        //base de la taza
        Body[1] = new Rectangle(posXBase, posYBase, PxPerCm, size*PxPerCm, color);
    }
    
    /**
     * Cambia el color de la taza
     * 
     * @param color que se desea, solo se permite:
     * "red", "yellow", "blue", "green", "magenta" y "black"
     */
    public void setColor(String color){
        
        for(Rectangle rect : Body){
            rect.changeColor(color);
        }
        
    }
    
    /**
     * 
     */
    public void makeVisible(){
        for (Rectangle rect : Body) {
            rect.makeVisible();
        }
    }
    
    /**
     * 
     */
    public void makeInvisible(){
        for (Rectangle rect : Body) {
            rect.makeInvisible();
        }
    }
    
    /**
     * posiciona el centro de la base de la taza en el 
     * lugar deseado en el eje X.
     * 
     * @param  posicionX    es un numero que dice donde desea que este el
     * centro la base de la taza en el eje X
     */
    public void posicionadorX(int posicionX){
        /*posiciona la pared izquierda en su ubicacion moviendola 
         * junto al extremo izquiedo de la base*/
        int posicionX0 = posicionX - ((size*PxPerCm)/2);
        Body[0].positionHorizontal(posicionX0);
        
        /*posiciona el centro de la base en el lugar indicado en 
         * el eje X*/
        
        int posicionX1 = posicionX - ((size*PxPerCm)/2);
        Body[1].positionHorizontal(posicionX1);
        
        /*posiciona la pared derecha en su ubicacion moviendola 
         * junto al extremo derecho de la base*/
        int posicionX2 = posicionX + ((size*PxPerCm)/2 - PxPerCm);
        Body[2].positionHorizontal(posicionX2);
    }
    
    /**
     * posiciona el centro de la tapa en el 
     * lugar correspondiente en el eje Y.
     * 
     * @param  posicionY es un numero que dice donde desea que este el
     * centro la tapa en el eje Y
     */
    public void posicionadorY(int posicionY){        
        /*posciona las paredes encima de la base*/
        int posicionYP = posicionY - PxPerCm*size;
        Body[0].positionVertical(posicionYP);
        Body[2].positionVertical(posicionYP);
        
        /*posiciona el lado inferior de la base en la posicion indicada
           en el eje Y*/
        int posicionYB = posicionY - PxPerCm;
        Body[1].positionVertical(posicionYB);
    }

    /**
     * retorna el numero asignado a la taza
     */
    public int getNumber(){
        return number;
    }
    
    /**
     * retorna el tamaño de la taza
     */
    public int getSize(){
        return size;
    }
    
    /**
     * devuelve si esta o no tapado por algo
     */
    public boolean isLided(){
        return Lided;
    }
    
    /**
     * hace que la taza este tapada
     */
    public void setCover(){
        Lided = true;
    }
    
    /**
     * hace que la taza este destapada
     */
    public void setUncover(){
        Lided = false;
    }
    
    //pendiente
    /**
     * retorna la posicion Y dada para la creacion de la taza
     */
    public int getPosicionY(){
        return 1;
    }
}

