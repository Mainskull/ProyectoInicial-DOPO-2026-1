import java.util.Collections;
import java.util.ArrayList;
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
    
    private static final int PxPerCm = 20; //pixeles por centimetro
    private static final int canvasW = 600; //anchura del canvas
    private static final int canvasH = 600; //altura del canvas
    
    private int maxHeight;
    private int width;
    
    private int currentHeight; //Altura maxima actual formado por la pila de cups y lids
    private int towerX; //posicion del centro de la torre en el eje x
    private int towerY; //posicion del centro de la torre en el eje y
    
    private boolean visible;
    private boolean ok;
    
    /*las primeras tres posciones de graphics son los tres lados de la
       torre, las demas son las marcas*/
    private ArrayList<Rectangle> graphics = new ArrayList<>();
    
    private ArrayList<Cup> cups = new ArrayList<>();
    private ArrayList<Lid> lids = new ArrayList<>();
    private ArrayList<String[]> items = new ArrayList<>();
 
    /**
     * Constructor for objects of class Tower
     * 
     * @param width la anchura de la interfaz de la torre (0<width<27)
     * @param maxHeight altura de la interfaz de la torre 
     * y numero permitido de altura de la torre (0<maxHeight<29)
     */
    public Tower(int width, int maxHeight){
        this.width = width;
        this.maxHeight = maxHeight;
        
        visible = false;
        ok = true;
        
        buildGraphics();
        
    }
    
    /**
     * construye la estructura grafica de la torre para darle representacion
     * visual por medio de la clase rectangulo
     */
    private void buildGraphics(){
        
        int widthPx = width * PxPerCm;
        int heightPx = maxHeight * PxPerCm;
        
        int markLenght = 20;
        int markGap = 10;
        int extraLeft = markLenght + markGap;
        
        int totalWidth = widthPx + extraLeft;
        
        towerX = (canvasW - totalWidth)/2 + extraLeft;
        towerY = (canvasH - heightPx) / 2;
        
        //generacion de los tres lados del grafico de la torre
        
        Rectangle leftSide = new Rectangle(towerX, towerY, heightPx, 4, "black");
        Rectangle base = new Rectangle(towerX, towerY + heightPx, 4, widthPx, "black");
        Rectangle rightSide = new Rectangle(towerX + widthPx - 4, towerY, heightPx, 4, "black");
        
        graphics.add(leftSide);
        graphics.add(base);
        graphics.add(rightSide);
        
        //generacion de las marcas
        for (int cm = 0; cm <= maxHeight; cm++){
            int y = towerY + heightPx - (cm * PxPerCm);
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
        
        visible = true;
        ok = true;
    }
    
    /**
     * apila una copa en la torre, si no cabe en lo que haya debajo se acumula
     * en la cima, si cabe en otra copa se acumula dentro.
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
        if (heightWithCup(number) > maxHeight) { 
            fail("Se supera la altura maxima."); 
            return;
        }
        
        String color = pickColor(number);
        
        int posYCup = towerY;
        
        if (items.isEmpty()){
            posYCup = towerY + maxHeight * PxPerCm - 4;
        }
        else {
            
            int currentItem = 0;
            
            for (int i = 0; i < items.size(); i++){
                String[] elemento = items.get(i);
                
                if(currentItem == i){
                    if (elemento[0].equals("Cup")){
                        int numberElement = Integer.parseInt(elemento[1]);
                        Cup currentCup = getCup(numberElement);
                        
                        if(i == items.size() - 1){
                            if (number >= currentCup.getNumber()){
                                posYCup = currentCup.getPosicionY() - Cup.PxPerCm*( 1 + currentCup.getSize());
                                currentCup.setCover();
                            }
                            else {
                                posYCup = currentCup.getPosicionY() - Cup.PxPerCm;
                            }
                            break;
                        }
                        
                        if(currentCup.isLided()){
                            Object objectCovering = getDeck(elemento);
                            if (objectCovering instanceof Cup){
                                int numberObjectCovering = ((Cup) objectCovering).getNumber();
                                String[] elementoCovering = new String[]{"cup", String.valueOf(numberObjectCovering)};                                
                            }
                            else if(objectCovering instanceof Lid){
                                int numberObjectCovering = ((Lid) objectCovering).getNumber();
                                String[] elementoCovering = new String[]{"lid", String.valueOf(numberObjectCovering)};
                            }
                            
                            int posTowerObjectCovering = getPosTower(elemento);
                            
                            currentItem = posTowerObjectCovering;
                        }
                        else {
                            if (number >= currentCup.getNumber()){
                                posYCup = currentCup.getPosicionY() - Cup.PxPerCm*( 1 + currentCup.getSize());
                                currentCup.setCover();
                            }
                            else {
                                currentItem += 1;
                            }
                            break;     
                        }
                        
                    } 
                    else{
                        int numberElement = Integer.parseInt(elemento[1]);
                        Lid currentLid = getLid(numberElement);
                        
                        if(i == items.size() - 1){
                            posYCup = currentLid.getPosicionY() - (Lid.height*Lid.PxPerCm);
                            break;
                        }
                        
                        currentItem += 1;
                        
                    }
                }   
            }
        
        }       
        
        Cup cup = new Cup(number, towerX, posYCup, color);
        cups.add(cup);
        items.add(new String[] { "cup", String.valueOf(number) });
        
        if (visible) {
            cup.makeVisible();
        }
        
        ok = true;
    }
    
    /**
     * devuelve la tapa con el numero dado asignado
     * 
     * @param number es el numero de la tapa que se desea obtener
     * @return tapa con el numero asignado que se desea
     */
    private Lid getLid(int number){        
        for (int i = 0; i < lids.size(); i++){
            Lid currentLid = lids.get(i);
            if (currentLid.getNumber() == number){
                return currentLid;
            }
        }
        return null;
    }
    
    //pendiente
    /**
     * devuelve la posicion de pila en la torre de un elemento dado
     */
    private int getPosTower(String[] elemento){
        return 1;
    }
    
    //pendiente
    /**
     * devuelve el objeto que esta cubriendo al elemento dado en la torre
     * 
     * @param elemento es uno de los elementos que se encuentra en items
     */
    private Object getDeck(String[] elemento){
    return null;
    }
    
    private boolean cupExists(int number) { 
        for (Cup c : cups) { 
            if (c.getNumber() == number) 
            return true; 
        } 
        return false; }
        
    private int heightWithCup(int newNumber) {
        
        int baseNumber;
        
        if (cups.isEmpty()) {
            baseNumber = newNumber;
        } else {
            baseNumber = cups.get(0).getNumber();
        }
        
        int baseHeight = 2 * baseNumber - 1;
        int totalItems = items.size() + 1;
        return baseHeight + (totalItems - 1);
    }
    
    private void fail(String message) { 
        ok = false; 
        if (visible) 
            javax.swing.JOptionPane.showMessageDialog(null, message); 
    }

    private String pickColor(int number) { 
        String[] colors = {"red","blue","green","yellow","magenta","cyan","orange","pink"}; 
        return colors[number % colors.length]; 
    }
    
    private Cup getCup(int number){        
        for (int i = 0; i < cups.size(); i++){
            Cup currentCup = cups.get(i);
            if (currentCup.getNumber() == number){
                return currentCup;
            }
        }
        return null;
    }
    
}