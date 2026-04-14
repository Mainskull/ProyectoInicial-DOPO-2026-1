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
    private ArrayList<Rectangle> graphics;
    private Element currentTopElement;
    private ArrayList<Element> stack;
    private ArrayList<String[]> items;
 
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
        stack = new ArrayList<>();
        items = new ArrayList<>();
        
        buildGraphics();        
    }
    
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
     * permite saber si la ultima operacion se ejecuto correctamente
     * 
     * @return retorna un valor booleano, true si la operacion salio bien,
     * false si la operacion salio bien.
     */
    public boolean ok(){
        return ok;
    }
    
    /**
     * hace visible la estructura grafica de la torre
     */
    public void makeVisible(){
        if (visible) return;
        
        for(Rectangle r: graphics){
            r.makeVisible();
        }
        
        for(Element e: stack){
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
        
        for(Rectangle r: graphics){
            r.makeInvisible();
        }
        
        for(Element e: stack){
            e.makeInvisible();
        }
        
        visible = false;
        ok = true;
    }
    
    /**
     * apila una copa en la torre, si no cabe en lo que haya debajo se acumula
     * en la cima, si cabe en otra copa se acumula dentro.
     * 
     * @param number numero que se le asignara a la copa
     */
    public void pushCup(int number){
        
        ok = false;
        
        if (number <= 0) { 
            fail("El numero debe ser mayor que 0."); 
            return; 
        }
        if (cupExists(number)) { 
            fail("Ya existe una taza con ese numero."); 
            return; 
        }
        
        //datos nesesarios para crear la taza
        int posicionX = posicionTowerX;//la copa se posiciona en la mitad de la torre
        int posicionY = CANVAS_H/2;
        String color = pickColor(number);//se elije el color segun su numero
        
        /*elementos para el caso en que se deba encontrar el lugar
           a ubicar la copa*/
        Element previusElement = null;
        Element currentElement = currentTopElement;
        int currentNumber = 0;
        
        /*determina la posicion Y de la taza y los elementos a los que afectara*/
        if(currentTopElement != null){
            currentNumber = currentElement.getNumber();
            while(currentNumber > number){
                previusElement = currentElement;
                boolean haveContent = currentElement.containsSomething();
                if(haveContent){
                    currentElement = currentElement.getTopContent();
                    currentNumber = currentElement.getNumber();
                }
                else{
                    posicionY = currentElement.getPosYBase();
                    break;
                }
            }
            
            if(currentNumber <= number){
                posicionY = currentElement.getPosYTop();
            }
            
        }
        else{
            posicionY = posicionTowerY + height*PX_X_CM/2;
        }
        
        Cup cup = new Cup(number, posicionX, posicionY, color);
        
        stack.add(cup);
        items.add(new String[] { "cup", String.valueOf(number) });
        
        /*si es la primera vez que se coloca una copa se debe hacer el
           currentTopElement de la torre esta primera copa*/
        if(currentTopElement == null){
            currentTopElement = cup;
        }

        /*si la posicion actual de la cima de la nueva copa supera a
           a la posicion de la cima del elemento de la cima de la torre,
           la torre debe actualizar la cima*/
        if(cup.getPosYTop() <= currentTopElement.getPosYTop()){
            currentTopElement = cup;
        }
        
        /*si previusElement no es null implica que el objeto fue contenido
           en previusElement, por lo que se debe actualizar el elemento que lo contuvo
           con este elemento como el top de los elementos contenidos en previus
           element*/
        if(previusElement != null){
            previusElement.setTopContent(cup);
        }
        
        /*si el elemento actual es diferente de null implica que la nueva copa
           cubirar a currentElement, por lo cual se debe actualizar 
           currentElement para que sepa que lo estan cubriendo*/
        if(currentElement != null){
            currentElement.setCoverlet(cup);
        }
        
        if(visible){
            cup.makeVisible();
        }
        
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
        
        if (number <= 0) { 
            fail("El numero debe ser mayor que 0."); 
            return; 
        }
        if (lidExists(number)) { 
            fail("Ya existe una tapa con ese numero."); 
            return; 
        }
        
        //datos nesesarios para crear la tapa
        int posicionX = posicionTowerX;//la tapa se posiciona en la mitad de la torre
        int posicionY = CANVAS_H/2;
        String color = pickColor(number);//se elije el color segun su numero
        
        /*elementos para el caso en que se deba encontrar el lugar
           a ubicar la tapa*/
        Element previusElement = null;
        Element currentElement = currentTopElement;
        int currentNumber = 0;
        
        /*determina la posicion Y de la tapa y los elementos a los que afectara*/
        if(currentTopElement != null){
            currentNumber = currentElement.getNumber();
            while(currentNumber > number){
                previusElement = currentElement;
                boolean haveContent = currentElement.containsSomething();
                if(haveContent){
                    currentElement = currentElement.getTopContent();
                    currentNumber = currentElement.getNumber();
                }
                else{
                    posicionY = currentElement.getPosYBase();
                    break;
                }
            }
            
            if(currentNumber <= number){
                posicionY = currentElement.getPosYTop();
            }
            
        }
        else{
            posicionY = posicionTowerY + height*PX_X_CM/2;
        }
        
        Lid lid = new Lid(number, posicionX, posicionY, color);
        
        stack.add(lid);
        items.add(new String[] { "lid", String.valueOf(number) });
        
        /*si es la primera vez que se coloca una copa se debe hacer el
           currentTopElement de la torre esta primera copa*/
        if(currentTopElement == null){
            currentTopElement = lid;
        }

        /*si la posicion actual de la cima de la nueva copa supera a
           a la posicion de la cima del elemento de la cima de la torre,
           la torre debe actualizar la cima*/
        if(lid.getPosYTop() < currentTopElement.getPosYTop()){
            currentTopElement = lid;
        }
        
        /*si previusElement no es null implica que el objeto fue contenido
           en previusElement, por lo que se debe actualizar el elemento que lo contuvo
           con este elemento como el top de los elementos contenidos en previus
           element*/
        if(previusElement != null){
            previusElement.setTopContent(lid);
        }
        
        /*si el elemento actual es diferente de null implica que la nueva copa
           cubirar a currentElement, por lo cual se debe actualizar 
           currentElement para que sepa que lo estan cubriendo*/
        if(currentElement != null){
            currentElement.setCoverlet(lid);
        }
        
        if(visible){
            lid.makeVisible();
        }
        
        ok = true;
    }
    
    public void popCup() {
        ok = true;
        int idx = findTopIndexOf("cup");
        if (idx == -1) { 
            fail("No hay tazas en la torre."); 
            return;
        }

        int cupNum = Integer.parseInt(items.get(idx)[1]);
        if (idx + 1 < items.size() && items.get(idx + 1)[0].equals("lid") && Integer.parseInt(items.get(idx + 1)[1]) == cupNum) {
            items.remove(idx + 1);
        }   
        items.remove(idx);
        rebuildStack();
    }
    
    public void popLid() {
        ok = true;
        int idx = findTopIndexOf("lid");
        if (idx == -1) { 
            fail("No hay tapas en la torre.");
            return; 
        }
        items.remove(idx);
        rebuildStack();
    }
    
    private void rebuildStack() {

        ArrayList<String[]> previousItems = new ArrayList<>();

        for (String[] item : items) {
            previousItems.add(new String[] { item[0], item[1] });
        }

        for (Element e : stack) {
            e.makeInvisible();
        }

        stack.clear();
        currentTopElement = null;
        items.clear();

        for (int i = 0; i < previousItems.size(); i++) {

            String type = previousItems.get(i)[0];
            int number = Integer.parseInt(previousItems.get(i)[1]);

            if (type.equals("cup")) {
            pushCup(number);
            } 
            else {
            pushLid(number);
            }
        }
    }
    
    public void removeCup(int number) {
        ok = true;
        int idx = findIndexOf("cup", number);
        if (idx == -1) { 
            fail("No existe una taza con ese numero en la torre.");
            return; 
        }

        if (idx + 1 < items.size() && items.get(idx + 1)[0].equals("lid") && Integer.parseInt(items.get(idx + 1)[1]) == number) {
            items.remove(idx + 1);
        }
        items.remove(idx);

        rebuildStack();
    }
    
    public void removeLid(int number) {
        ok = true;
        int idx = findIndexOf("lid", number);

        if (idx == -1) { 
            fail("No existe una tapa con ese numero en la torre.");
            return; 
        }

        if (idx - 1 >= 0 && items.get(idx - 1)[0].equals("cup") && Integer.parseInt(items.get(idx - 1)[1]) == number) {
            items.remove(idx);
            items.remove(idx - 1);
        } else {
            items.remove(idx);
        }

        rebuildStack();
    }
    
    public void orderTower() {
        ok = true;
    
        for (int i = 0; i < items.size(); i++) {
            for (int j = 0; j < items.size() - 1; j++) {
    
                int num1 = Integer.parseInt(items.get(j)[1]);
                int num2 = Integer.parseInt(items.get(j + 1)[1]);
    
                String type1 = items.get(j)[0];
                String type2 = items.get(j + 1)[0];
    
                // Si el de la izquierda es menor, se intercambian
                if (num1 < num2) {
    
                    String[] temp = items.get(j);
                    items.set(j, items.get(j + 1));
                    items.set(j + 1, temp);
    
                } 
                // Si son iguales, cup debe ir antes que lid
                else if (num1 == num2) {
    
                    if (type1.equals("lid") && type2.equals("cup")) {
    
                        String[] temp = items.get(j);
                        items.set(j, items.get(j + 1));
                        items.set(j + 1, temp);
    
                    }
                }
            }
        }
    
        rebuildStack();
    }
    
    public void reverseTower() {
        ok = true;
    
        for (int i = 0; i < items.size() / 2; i++) {
            String[] temp = items.get(i);
            items.set(i, items.get(items.size() - 1 - i));
            items.set(items.size() - 1 - i, temp);
        }
    
        ArrayList<String[]> newItems = new ArrayList<>();
        int totalHeight = 0;
    
        for (int i = 0; i < items.size(); i++) {
            String type = items.get(i)[0];
            int number = Integer.parseInt(items.get(i)[1]);
            int itemHeight;
    
            if (type.equals("cup")) {
                itemHeight = 2 * number - 1;
            } 
            else {
                itemHeight = 1;
            }
    
            if (totalHeight + itemHeight <= height) {
                newItems.add(items.get(i));
                totalHeight += itemHeight;
            }
        }
    
        items = newItems;
        rebuildStack();
    }
    
    public int height(){
        ok = true;
    
        if (currentTopElement == null) {
            return 0;
        }
    
        int baseTower = posicionTowerY + height * PX_X_CM / 2;
        int topStack = currentTopElement.getPosYTop();
    
        return (baseTower - topStack) / PX_X_CM;
    }
    
    /**
     * determina si existe una copa en la torre que tenga asignado el numero 
     * dado
     * 
     * @param number numero de la copa que se desea saber si esta en la torre
     * 
     * @return true si se encuentra en la torre, false si no
     */
    private boolean cupExists(int number) { 
        boolean exists = false;
        for (Element e : stack) { 
            if (e.getNumber() == number && e.item().equals("Cup")){
                exists = true;
                break;
            }
        } 
        return exists;
    }
    
    /**
     * determina si existe una tapa en la torre que tenga asignado el numero 
     * dado
     * 
     * @param number numero de la tapa que se desea saber si esta en la torre
     * 
     * @return true si se encuentra en la torre, false si no
     */
    private boolean lidExists(int number) { 
        boolean exists = false;
        for (Element e : stack) { 
            if (e.getNumber() == number && e.item().equals("Lid")){
                exists = true;
                break;
            }
        } 
        return exists;
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

    private String pickColor(int number) { 
        String[] colors = {"red","blue","green","yellow","magenta","cyan","orange","pink"};
        return colors[number % colors.length];
        
    }
    
    private int findTopIndexOf(String type) { 
        for (int i = items.size() - 1; i >= 0; i--) {
            if (items.get(i)[0].equals(type)) { 
                return i; 
            }
        }    
        return -1;    
    }
    
    private int findIndexOf(String type, int number) {
        for (int i = 0; i < items.size(); i++) {
            if (items.get(i)[0].equals(type) && Integer.parseInt(items.get(i)[1]) == number) {
                return i; 
            }    
        }
        return -1;
    }
    
}
