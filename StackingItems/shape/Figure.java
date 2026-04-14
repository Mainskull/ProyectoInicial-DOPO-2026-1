package shape;


/**
 * clase abstracta que representa a figuras geometricas o en general
 * 
 * @author Daniel Valero, Juan Nieto
 * @version 06.24.2026
 */
public abstract class Figure
{
    
    protected int xPosition;
    protected int yPosition;
    protected String color;
    protected boolean isVisible;
    
    /**
     * Constructor for objects of class Figure
     * 
     * @param xPosition posicion en el eje x de la figura
     * @param yPosition posicion en el eje y de la figura
     * @param color color que tendra la figura
     */
    public Figure(int xPosition, int yPosition, String color)
    {
        this.xPosition = xPosition;
        this.yPosition = yPosition;
        this.color = color;
        this.isVisible = false;
    }

    /**
     * Make this figure visible. If it was already visible, do nothing.
     */
    public void makeVisible(){
        isVisible = true;
        draw();
    }
    
    /**
     * Make this rectangle invisible. If it was already invisible, do nothing.
     */
    public void makeInvisible(){
        erase();
        isVisible = false;
    }
    
    /**
     * Move the rectangle a few pixels to the right.
     */
    public void moveRight(){
        moveHorizontal(20);
    }

    /**
     * Move the rectangle a few pixels to the left.
     */
    public void moveLeft(){
        moveHorizontal(-20);
    }

    /**
     * Move the rectangle a few pixels up.
     */
    public void moveUp(){
        moveVertical(-20);
    }

    /**
     * Move the rectangle a few pixels down.
     */
    public void moveDown(){
        moveVertical(20);
    }

    /**
     * Move the rectangle horizontally.
     * @param distance the desired distance in pixels
     */
    public void moveHorizontal(int distance){
        erase();
        xPosition += distance;
        draw();
    }

    /**
     * Move the rectangle vertically.
     * @param distance the desired distance in pixels
     */
    public void moveVertical(int distance){
        erase();
        yPosition += distance;
        draw();
    }

    /**
     * Slowly move the rectangle horizontally.
     * @param distance the desired distance in pixels
     */
    public void slowMoveHorizontal(int distance){
        int delta;

        if(distance < 0) {
            delta = -1;
            distance = -distance;
        } else {
            delta = 1;
        }

        for(int i = 0; i < distance; i++){
            xPosition += delta;
            draw();
        }
    }

    /**
     * Slowly move the rectangle vertically.
     * @param distance the desired distance in pixels
     */
    public void slowMoveVertical(int distance){
        int delta;

        if(distance < 0) {
            delta = -1;
            distance = -distance;
        } else {
            delta = 1;
        }

        for(int i = 0; i < distance; i++){
            yPosition += delta;
            draw();
        }
    }
    
    /**
     * Change the color. 
     * @param color the new color. Valid colors are "red", "yellow", "blue", "green",
     * "magenta" and "black".
     */
    public void changeColor(String newColor){
        color = newColor;
        draw();
    }
    
    protected abstract void draw();
    
    /*
     * Erase the rectangle on screen.
     */
    protected void erase(){
        if(isVisible) {
            Canvas canvas = Canvas.getCanvas();
            canvas.erase(this);
        }
    }
    
}