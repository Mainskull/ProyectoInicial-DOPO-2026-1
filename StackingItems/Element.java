import java.util.*;

/**
 * clase abstracta para definir los elementos del simulador
 * 
 * @author Juan Nieto, Daniel Valero
 * @version (a version number or a date)
 */
public abstract class Element{
    public static final int PX_X_CM = 30;
    
    private int number;
    private int height;
    private int width;
    protected int posicionX;
    protected int posicionY;
    protected int top;
    protected int base;
    protected String color;
    
    protected Element coverlet;    
    protected ArrayList<Rectangle> body;
    
    /**
     * genera un elemento
     * 
     * @param number numero que tendra el elemento
     * @param height altura del elemento
     * @param width base del elemento
     * @param posicionX posicion en la que se desea que se encuentre la mitad
     * del elemento
     * @param posicionY posicion en la que se desea que se encuentre la parte inferior
     * del elemento
     */
    public Element(int number, int height, int width, int posicionX, int posicionY, String color){
        this.number = number;
        this.height = height;
        this.width = width;
        this.posicionX = posicionX;
        this.posicionY = posicionY;
        this.color = color;
        
        top = posicionY - height*PX_X_CM;
        base = posicionY - PX_X_CM;
        
        coverlet = null;
        body = new ArrayList<>();
        
        bodyBuilder();
    }
    
    /**
     * genera el cuerpo visual del elemento
     */
    protected abstract void bodyBuilder();
    
    /**
     * posiciona la mitad del elemento en la posicion que se de en el
     * eje X
     * 
     * actualiza el la posicionX
     * 
     * @param posicionX lugar deseado en el eje X para la ubicacion
     */
    public abstract void posicionadorX(int posicionX);
    
    /**
     * posiciona la parte inferior del elemento en la posicion que se de en el
     * eje y, los numeros positivos aumentan para abajo
     * 
     * actualiza el la posicion top y la posicionY
     * 
     * @param posicionY lugar deseado en el eje Y para la ubicacion
     */
    public abstract void posicionadorY(int posicionY);
    
    /**
     * determina el tipo de clase del que es el elemento
     * 
     * @return String con el nombre de la clase que es
     */
    public abstract String item();
    
    /**
     * hace visible el cuerpo del elemento
     */
    public void makeVisible(){
        for (Rectangle rect : body) {
            rect.makeVisible();
        }  
    }
    
    /**
     * hace invisible el cuerpo del elemento
     */
    public void makeInvisible(){
        for (Rectangle rect : body) {
            rect.makeInvisible();
        }
    }
    
    /**
     * determina si el elemento tiene algo encima de el
     * 
     * @return true si tiene algo encima, false si no
     */
    public boolean isCovered(){
        return coverlet != null;
    }
    
    /**
     * devuelve el elemento que se encuentra encima de este
     * 
     * @return elemento encima del actual
     */
    public Element getCoverlet(){
        return coverlet;
    }
    
    /**
     * hace que el elemento sea cubierto por otro elemento dado
     * 
     * @param elemento es el elemento que cubrira al actual
     */
    public void setCoverlet(Element elemento){
        coverlet = elemento;
    }
    
    /**
     * hace que la cima de lo que tiene dentro de si el elemento actual sea
     * un elemento dado
     * 
     * por defecto como los elementos no contienen cosas por defecto no se
     * hara nada
     * 
     * @param elemento elemento que se desea poner en la cima de lo contenido
     * en este
     */
    public void setTopContent(Element elemento){
    
    }
    
    /**
     * determina si el elemento contiene algun elemento dentro de si
     * 
     * por defecto los elementos no contienen nada
     * 
     * @return true si contiene algo, false si no
     */
    public boolean containsSomething(){
        return false;
    }
    
    /**
     * devuelve la ubicacion de la cima del elemento
     * 
     * @return la posicion en y de la parte superior del elemento en
     * el eje y
     */
    public int getPosYTop(){
        return top;
    }
    
    /**
     * devuelve el numero que tiene asignado el elemento
     * 
     * @return numero del elemento
     */
    public int getNumber(){
        return number;
    }
    
    /**
     * devuelve la altura que tiene el elemento
     * 
     * @return altura del elemento
     */
    public int getHeight(){
        return height;
    }
    
    /**
     * devuelve la anchura que tiene el elemento
     * 
     * @return altura del elemento
     */
    public int getWidth(){
        return width;
    }
    
    /**
     * devuelve el elemento que se encuentra encima de cualquier otro 
     * elemento que este contenido en este
     * 
     * por defecto como los elementos no contienen nada no poseen
     * ningun elemento para devolver
     * 
     * @return elemento en la cima dento del actual
     */
    public Element getTopContent(){
        return null;
    }
    
    /**
     * devuelve la ubicacion interna de la base del elemento
     * 
     * @return posicion de la parte superior de la base del elemento
     */
    public int getPosYBase(){
        return base;
    }
    
    /**
     * Cambia el color del cuerpo del elemento
     * 
     * @param color que se desea, solo se permite:
     * "red", "yellow", "blue", "green", "magenta" y "black"
     */
    public void changeColor(String color){
        
        for(Rectangle rect : body){
            rect.changeColor(color);
        }
        
    }
}






