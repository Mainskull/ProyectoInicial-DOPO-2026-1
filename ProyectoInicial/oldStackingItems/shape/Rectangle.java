package shape;

import java.awt.*;

/**
 * A rectangle that can be manipulated and that draws itself on a canvas.
 * 
 * @author  Michael Kolling and David J. Barnes (Modified)
 * @version 1.0  (15 July 2000)()
 */
public class Rectangle extends Figure{

    public static int EDGES = 4;
    
    private int height;
    private int width;

    /**
     * Create a new rectangle at default position with default color.
     */
    public Rectangle(){
        super(400 ,15,"magenta");
        height = 30;
        width = 40;
    }
    
    /**
     * Crea un cuadrado en una posicion y con un color definido
     * 
     * @param perimeter es un parametro que ayuda a definir la longitud 
     * de cada lado
     */
    public Rectangle(int perimeter){
        super(70, 15, "magenta");
        
        int side = perimeter / 4;

        height = side;
        width = side;
    }

    /**
     * Change the size to the new size
     * @param newHeight the new height in pixels. newHeight must be >=0.
     * @param newWidht the new width in pixels. newWidth must be >=0.
     */
    public void changeSize(int newHeight, int newWidth) {
        erase();
        height = newHeight;
        width = newWidth;
        draw();
    }

    /*
     * Draw the rectangle with current specifications on screen.
     */
    @Override
    protected void draw() {
        if(isVisible) {
            Canvas canvas = Canvas.getCanvas();
            canvas.draw(this, color,
                new java.awt.Rectangle(xPosition, yPosition, 
                                       width, height));
            canvas.wait(10);
        }
    }
    
    //------------------------------------------------
    //                  Extenciones
    //------------------------------------------------
    
    /**
     * Crea un rectangulo segun unos parametros dados
     * 
     * @param xPosition define la posicion horizontal de la esquina superior
     * izquierda del cuadrado
     * @param yPosition define la posicion Vertical de la esquina superior
     * izquierda del cuadrado
     * @param height define la altura del cuadrado desde la esquina 
     * superior izquierda para abajo
     * @param width define la anchura del cuadrado desde la esquina
     * superior izquierda para la derecha
     * @param color define el color del rectangulo
     * 
     */
    public Rectangle(int xPosition, int yPosition, int height, int width, String color) {
        super(xPosition, yPosition, color);
        
        this.height = height;
        this.width = width;
        this.isVisible = false;
    }
}


