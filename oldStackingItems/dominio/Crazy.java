package dominio;

import shape.*;
/**
 * Write a description of class Crazy here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public final class Crazy extends Lid
{

    /**
     * Constructor for objects of class Crazy
     */
    public Crazy(int number, int posicionX, int posicionY, String color)
    {
        super(number, posicionX, posicionY, color);  
    }
    
    /**
     * posiciona el centro de la tapa en el 
     * lugar deseado en el eje X.
     * 
     * @param  posicionX es un numero que dice donde desea que este la mitad
     * de la tapa en el eje X
     */
    @Override
    public void posicionadorX(int xPosition){
        super.posicionadorX(xPosition);
        body.get(1).posicionadorX(xPosition - ((PX_X_CM*width)/2));
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
        super.posicionadorY(yPosition);
        body.get(1).posicionadorY(yPosition - PX_X_CM);
    }
    
    /*=============================================================================
       
                                 metodos auxiliares
       
    ===============================================================================*/
    
    /**
     * genera el cuerpo visual del elemento
     */
    protected void bodyBuilder(){
        super.bodyBuilder();
        int posYBase = posicionY - PX_X_CM;
        int posXBase = posicionX - ((PX_X_CM*width)/2);
        //signo que representa a la tapa Fearful
        body.add(new Triangle(posXBase, posYBase, PX_X_CM, PX_X_CM, "black"));
    }
    
    /**
     * se deja caer como un elemento pero solo si se encuentra a su taza, 
     * hace que no pueda ser eliminada.
     * 
     * @param cup taza en la que se va a colocar el elemento
     */
    @Override
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

}