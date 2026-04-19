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
    
    public static final int PX_X_CM = 20; //pixeles por centimetro
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
        
        if (elementExists(new String[]{"cup", String.valueOf(number)})) { 
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
        
        if (elementExists(new String[]{"lid", String.valueOf(number)})) { 
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
     * apila una copa en la torre, si no cabe en lo que haya debajo se acumula
     * en la cima, si cabe en otra copa se acumula dentro.
     * 
     * @param number numero que se le asignara a la copa
     */
    public void pushCup(String type, int number){
        ok = false;
        
        if (elementExists(new String[]{"cup", String.valueOf(number)})) { 
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
    public void pushLid(String type, int number){
        ok = false;
        
        if (elementExists(new String[]{"lid", String.valueOf(number)})) { 
            fail("Ya existe una tapa con ese numero."); 
            return; 
        }
        if (number <= 0) { 
            fail("El numero debe ser mayor que 0."); 
            return; 
        }
        
        String color = pickColor(number);
        
        
        if(type.equals("normal")){
            Lid element = new Lid(number, posicionTowerX, 0, color);
            putInTower(element);
        }
        else if(type.equals("fearful")){
            Fearful element= new Fearful(number, posicionTowerX, 0, color);
            putInTower(element);
        }
        else if(type.equals("crazy")){
            Crazy element = new Crazy(number, posicionTowerX, 0, color);
            putInTower(element);
        }
        else{
            fail("el tipo ingresado no existe"); 
            return; 
        }
        
        
        ok = true;
    }
    
    /**
     * elimina la taza con el numero asignado si esta puesta en la torre
     * 
     * @param number es el mumero de la taza que se desea eliminar
     */
    public void removeCup(int number) {
        ok = false;
        
        
        int indxElementEliminate = 0;
        
        /*se busca el indice del elemento que se desea eliminar en el ArrayList para obtener y 
        saber si esta el elemento si no se encuentra se captura la excepcion que mande y se
        envia un mensaje a pantalla de que la taza no existe*/
        try{
             indxElementEliminate = findIndxElement(new String[]{"cup", String.valueOf(number)});
        }
        catch(StackingItemsException e){
            fail("no existe ninguna taza con ese numero asignado");
            return;
        }
        //se hace invisible a los objetos mientras se hacen los cambios
        makeElementsInvisible();
        
        //se obtiene la tapa a eliminar
        Element eliminateCup = elements.get(indxElementEliminate);
        
        /*se le dice a la taza que se elimine y esta se encargara de avisarle a los
           elementos con los que tenga alguna relacion para que se actualicen tambien
            
           se le envia como parametro la posicion de la base de la torre en caso de que 
           los elementos
           que se actualicen neseciten saber hasta donde pueden caer*/
        if(eliminateCup.canEliminate()){
            elements.remove(indxElementEliminate);
        }
        
        //actualizar la torre
        
        rebuild();
        
        //se vuelve visible los elementos si la torre es visible
        if(visible){
            makeElementsVisible();
        }
    }
    
    /**
     * elimina la tapa con el numero asignado si esta puesta en la torre
     * 
     * @param number es el mumero de la tapa que se desea eliminar
     */
    public void removeLid(int number) {
        ok = false;
        
        int indxElementEliminate = 0;
        
        /*se busca el indice del elemento que se desea eliminar en el ArrayList para obtener y 
        saber si esta el elemento si no se encuentra se captura la excepcion que mande y se
        envia un mensaje a pantalla de que la taza no existe*/
        try{
             indxElementEliminate = findIndxElement(new String[]{"lid", String.valueOf(number)});
        }
        catch(StackingItemsException e){
            fail("no existe ninguna tapa con ese numero asignado");
            return;
        }
        //se hace invisible a los objetos mientras se hacen los cambios
        makeElementsInvisible();
        
        //se obtiene la tapa a eliminar
        Element eliminateLid = elements.get(indxElementEliminate);
        
        /*se le dice a la tapa que se elimine y esta se encargara de avisarle a los
           elementos con los que tenga alguna relacion para que se actualicen tambien
            
           se le envia como parametro la posicion de la base de la torre en caso de que 
           los elementos
           que se actualicen neseciten saber hasta donde pueden caer*/
        if(eliminateLid.canEliminate()){
            elements.remove(indxElementEliminate);
        }
        
        //actualizar la torre
        rebuild();

        
        //se vuelve visible los elementos si la torre es visible
        if(visible){
            makeElementsVisible();
        }
    }
    
    /**
     * elimina la ultima taza puesta en la torre
     */
    public void popCup() {
        ok = false;
        try{
            int indx = findIndxTopElement("cup");
            Element topElement = elements.get(indx);
            int number = topElement.getNumber();
            removeCup(number);
            ok = true;
        }
        catch(StackingItemsException e){
            fail("no hay ninguna taza en la torre");
        }
    }
    
    /**
     * elimina la ultima tapa puesta en la torre
     */
    public void popLid() {
       ok = false;
       try{
            int indx = findIndxTopElement("lid");
            Element topElement = elements.get(indx);
            int number = topElement.getNumber();
            removeLid(number);
            ok = true;
       }
        catch(StackingItemsException e){
            fail("no hay ninguna tapa en la torre");
       }
    }
    
    public String[][] stackingItems(){
        int cantElements = elements.size();
        String[][] result = new String[cantElements][2];
        
        int indx = 0;
        for(Element e: elements){
            result[indx] = e.information();
            indx ++;
        }
        
        return result;
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
    
    /**
     * retorna la altura actual que posee la torre en estos momentos desde su base
     * hasta el elemento actual en la cima
     * 
     * @return altura actual de la torre
     */
    public int height(){
        ok = false;
    
        if (currentTopElement == null) {
            ok =true;
            return 0;
        }
    
        int baseTower = getPosYBase();
        int topStack = currentTopElement.getPosYTop();
        
        ok = true;
        return (baseTower - topStack) / PX_X_CM;
    }
    
    /**
     * determina si la ultima operacion se completo exitosamente
     */
    public boolean ok(){
        return ok;
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
    
    /**
     * encuentra la posicion del elemento que posea la informacion dada
     * 
     * @param arreglo con el tipo y el numero asignados al elemento
     * @return entero que dice la posicion en la cual esta el elemento
     */
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
     * encuentra la posicion del elemento del mas reciente pues del tipo que se diga
     * 
     * @param type nombre del tipo del elemento que se busca
     * @return numero del elemento que del tipo dado mas recientemente puesto
     */
    private int findIndxTopElement(String type) throws StackingItemsException{
        int indx = 0;
        
        for (int i = elements.size() - 1; i >= 0; i--) {
            String[] infoE = elements.get(i).information();
            if(infoE[0].equals(type)){
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
        
        addElementToElements(element);
        updateCurrentTopElement();
        
        if(visible){
            element.makeVisible();
        }
        
    }
    
    /**
     * añade el elemento en la posicion correcta que le corresponde en la lista de elementos
     * 
     * @param element elemento a posicionar en la lista
     */
    private void addElementToElements(Element element){
        int indx = 1;
        Element currentBase = element.getBase();
        
        for(Element e: elements){
            
            if(e.equals(currentBase)){
                break;
            }
    
            indx++;
        }
        
        if(elements != null){
            if(0 < indx && indx< elements.size()){
                elements.add(indx, element);
            }
            else{
                elements.add(element);
            }
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
     * coge la lista de elementos como esta actualmente, reinicia cada elemento y vuelve a colocarlos en el orden en el que se encuentran en la
     * lista elements
     */
    private void rebuild(){
        ArrayList<Element> copyElements = new ArrayList(elements);
        
        elements.clear();
        setCurrentTopElement(null);
        
        for(Element e: copyElements){
            e.reset();
            putInTower(e);
        }
        
    }
    
    /**
     * hace visible la estructura grafica de la torre
     */
    private void makeElementsVisible(){
        for(Element e: elements){
            e.makeVisible();
        }
    }
    
    /**
     * hace invisible la estructura grafica de la torre
     */
    private void makeElementsInvisible(){
        for(Element e: elements){
            e.makeInvisible();
        }
    }
    
    /**
     * devuelve la posicion de la base de la torre
     * 
     * @return posicion de la base de la torre
     */
    private int getPosYBase(){
        return posicionTowerY + (height*PX_X_CM)/2;
    }
    
    /**
     * vuelve el elemento que es la cima de la torre actualmente
     * 
     * @return elemento actual que es la cima de la torre.
     */
    private Element getCurrentTopElement(){
        return currentTopElement;
    }
    
    /**
     * vuelve al elemento dado, el elemento actual en la cima de la torre
     * 
     * @param element es el elemento que se volvera el elemento en la cima de la torre
     */
    private void setCurrentTopElement(Element element){
        currentTopElement = element;
    }
    
    
}
