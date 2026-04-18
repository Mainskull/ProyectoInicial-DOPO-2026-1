package dominio;

import shape.*;
import java.util.*;
import javax.swing.JOptionPane;

/**
 * Tower es la clase principal del juego, encargada de
 * ejecutar el simulador utilizando las otras clases 
 * formadas
 * 
 * @author Daniel Valero y Juan Sebastian Nieto
 * @version 14.02.2026
 */
public class Tower{
    
    private static final int PX_X_CM = 20; //pixeles por centimetro
    private static final int CANVAS_W = 600; //anchura del canvas
    private static final int CANVAS_H = 600; //altura del canvas
    
    private int height;
    private int width;
    
    private int posicionTowerX; //posicion del centro de la torre en el eje x
    private int posicionTowerY; //posicion del centro de la torre en el eje y
    
    private boolean visible;
    private boolean ok;
    
    /*las primeras tres posciones de graphics son los tres lados de la
       torre, las demas son las marcas*/
    private ArrayList<Figure> graphics;
    private Element currentTopElement;
    private ArrayList<Element> elements;
 
    /**
     * Constructor for objects of class Tower
     * 
     * @param width la anchura de la interfaz de la torre (0<width<27)
     * @param maxHeight altura de la interfaz de la torre 
     * y numero permitido de altura de la torre (0<maxHeight<29)
     */
    public Tower(int width, int maxHeight){
        this.width = width;
        this.height = maxHeight;
        
        visible = false;
        ok = true;
        
        graphics = new ArrayList<>();
        currentTopElement = null;
        elements = new ArrayList<>();
        
        buildGraphics();
    }
    
    /**
     * apila una copa en la torre, si no cabe en lo que haya debajo se acumula
     * en la cima, si cabe en otra copa se acumula dentro.
     * 
     * @param number numero que se le asignara a la copa
     */
    public void pushCup(int number){
        ok = false;
        
        if (elementExists(new String[]{"Cup", String.valueOf(number)})) { 
            fail("Ya existe una taza con ese numero."); 
            return; 
        }
        if (number <= 0) { 
            fail("El numero debe ser mayor que 0."); 
            return; 
        }
        
        String color = pickColor(number);
        
        Cup cup = new Cup(number, posicionTowerX, 0, color);
        
        putInTower(cup);
        
        ok = true;
    }
    
    /**
     * apila una tapa en la torre, si no cabe en lo que haya debajo se acumula
     * en la cima, si cabe en otra copa se acumula dentro.
     * 
     * @param number numero que se le asignara a la tapa
     */
    public void pushLid(int number){
        ok = false;
        
        if (elementExists(new String[]{"Lid", String.valueOf(number)})) { 
            fail("Ya existe una tapa con ese numero."); 
            return; 
        }
        if (number <= 0) { 
            fail("El numero debe ser mayor que 0."); 
            return; 
        }
        
        String color = pickColor(number);
        
        Lid lid = new Lid(number, posicionTowerX, 0, color);
        
        putInTower(lid);
        
        ok = true;
    }
    
    /**
     * hace visible la estructura grafica de la torre
     */
    public void makeVisible(){
        if (visible) return;
        
        for(Figure r: graphics){
            r.makeVisible();
        }
        
        for(Element e: elements){
            e.makeVisible();
        }
        
        visible = true; 
        ok = true;
    }
    
    /**
     * hace invisible la estructura grafica de la torre
     */
    public void makeInvisible(){
        if (!visible) return;
        
        for(Figure r: graphics){
            r.makeInvisible();
        }
        
        for(Element e: elements){
            e.makeInvisible();
        }
        
        visible = false;
        ok = true;
    }
    
    /*=============================================================================
       
                                 metodos auxiliares
       
    ===============================================================================*/
    
    /**
     * construye la estructura grafica de la torre para darle representacion
     * visual por medio de la clase rectangulo
     */
    private void buildGraphics(){
        
        int widthPx = width * PX_X_CM;
        int heightPx = height * PX_X_CM;
        
        int markLenght = 20;
        int markGap = 10;
        int extraLeft = markLenght + markGap;
        
        int totalWidth = widthPx + extraLeft;
        
        //posicion base para los rectangulos de la torre
        //este punto apunta a la esquina superior izquierda de la pared izquierda de la torre
        int towerX = (CANVAS_W - totalWidth)/2 + extraLeft;
        int towerY = (CANVAS_H - heightPx) / 2;
        
        //generacion del la posicion del centro de la torre 
        posicionTowerX = towerX + widthPx/2;
        posicionTowerY = towerY + heightPx/2;
        
        //generacion de los tres lados del grafico de la torre
        
        Rectangle leftSide = new Rectangle(towerX, towerY, heightPx, 4, "black");
        Rectangle base = new Rectangle(towerX, towerY + heightPx, 4, widthPx, "black");
        Rectangle rightSide = new Rectangle(towerX + widthPx - 4, towerY, heightPx, 4, "black");
        
        graphics.add(leftSide);//primera posicion la pared izquierda
        graphics.add(base);//segunda posicion base
        graphics.add(rightSide);//tercera posicion la pared derecha
        
        //generacion de las marcas
        for (int cm = 0; cm <= height; cm++){
            int y = towerY + heightPx - (cm * PX_X_CM);
            int x = towerX - (extraLeft);
            
            Rectangle mark = new Rectangle(x, y, 3, markLenght, "black");
            
            graphics.add(mark);
        }
    }
    
    /**
     * determina si un elemento existe
     */
    private boolean elementExists(String[] info){
        boolean exists = false;
        
        try{
            findIndxElement(info);
            exists = true;
        }
        catch(StackingItemsException e){
        
        }
        
        return exists;
    }
    
    private int findIndxElement(String [] info) throws StackingItemsException{
        int indx = 0;
        
        for (Element e: elements){
            String[] infoE = e.information();
            if(Arrays.equals(info, infoE)){
                break;
            }
            indx += 1;
        }
        
        if(indx == elements.size()){
            throw new StackingItemsException(StackingItemsException.ELEMENT_NOT_fOUND);
        }
        
        return indx;
        
    }
    
    /**
     * retorna el nombre de un color
     * 
     * @param number determina el color que se va a devolver
     * @return nombre del color
     */
    private String pickColor(int number) { 
        String[] colors = {"red","blue","green","yellow","magenta","cyan","orange","pink"};
        return colors[number % colors.length];
        
    }
    
    /**
     * muestra un mensaje en pantalla y determina que la ultima operacion no
     * se logro hacer
     * 
     * @param mensaje que se desea mostrar
     */
    private void fail(String message) { 
        ok = false; 
        if (visible) 
            javax.swing.JOptionPane.showMessageDialog(null, message); 
    }
    
    /**
     * coloca o deja caer el elemento dado en la cima de la torre
     * 
     * @param elemento a colocar en la torre
     */
    private void putInTower(Element element){
        element.makeInvisible();
        
        if(currentTopElement != null){
            currentTopElement.fallingElement(element);
        }
        else{
            element.posicionadorY(getPosYBase());
            element.setBase(null);
        }
        
        elements.add(element);
        updateCurrentTopElement();
        
        if(visible){
            element.makeVisible();
        }
        
    }
    
    /**
     * actualiza el elemento actual que se encuentra en la cima de toda la torre
     */
    private void updateCurrentTopElement(){
        Element higherElement = null;
        int higherPosYTop = getPosYBase();
        int currentPosYTop;
        for(Element e: elements){
            currentPosYTop = e.getPosYTop();
            if(currentPosYTop < higherPosYTop){
                higherPosYTop = currentPosYTop;
                higherElement = e;
            }
        }
        
        setCurrentTopElement(higherElement);
    }
    
    /**
     * vuelve al elemento dado, el elemento actual en la cima de la torre
     * 
     * @param element es el elemento que se volvera el elemento en la cima de la torre
     */
    private void setCurrentTopElement(Element element){
        currentTopElement = element;
    }
    
    /**
     * devuelve la posicion de la base de la torre
     * 
     * @return posicion de la base de la torre
     */
    private int getPosYBase(){
        return posicionTowerY + (height*PX_X_CM)/2;
    }
}
