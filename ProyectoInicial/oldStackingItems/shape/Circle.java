package shape;

import java.awt.*;
import java.awt.geom.*;

/**
 * A circle that can be manipulated and that draws itself on a canvas.
 * 
 * @author  Michael Kolling and David J. Barnes
 * @version 1.0.  (15 July 2000) 
 */

public class Circle extends Figure{

    public static final double PI=3.1416;
    
    private int diameter;

    public Circle(){
        super(20, 15, "blue");
        diameter = 30;
    }
    
    /**
     * Change the size.
     * @param newDiameter the new size (in pixels). Size must be >=0.
     */
    public void changeSize(int newDiameter){
        erase();
        diameter = newDiameter;
        draw();
    }

    @Override
    protected void draw(){
        if(isVisible) {
            Canvas canvas = Canvas.getCanvas();
            canvas.draw(this, color, 
                new Ellipse2D.Double(xPosition, yPosition, 
                diameter, diameter));
            canvas.wait(10);
        }
    }
    
    //------------------------------------------------
    //                  Extenciones
    //------------------------------------------------
    
    /**
     * Crea un circulo segun unos parametros dados
     * 
     * @param xPosition define la posicion horizontal
     * @param yPosition define la posicion Vertical
     * @param diameter define el diametro
     * @param color define el color
     * 
     */
    public Circle(int xPosition, int yPosition, int diameter, String color) {
        super(xPosition, yPosition, color);
        
        this.diameter = diameter;
    }

}
