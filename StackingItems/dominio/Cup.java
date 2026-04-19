package dominio;

import shape.*;
import java.util.*;

/**
 * Cup representa una taza que guarda datos de esta y ademas puede ser
 * representado visualmente utilizando varios rectangulos.
 * 
 * @author Daniel Valero y Juan Sebastian Nieto 
 * @version 14.02.2026
 */
public class Cup extends Element implements Container{    
    
    private ArrayList<Element> content; 

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
    public Cup(int number, int posicionX, int posicionY, String color){
        super(number, 2*number - 1, 2*number - 1, posicionX, posicionY, color);
        content = new ArrayList<>();
    }
    
    /*=====================================================
       
                    metodos Element
    
    =======================================================*/
    
    /**
     * posiciona el centro de la base de la taza en el 
     * lugar deseado en el eje X.
     * 
     * @param  posicionX    es un numero que dice donde desea que este el
     * centro la base de la taza en el eje X
     */
    @Override
    public void posicionadorX(int xPosition){
        
        /*posiciona la pared izquierda en su ubicacion moviendola 
         * junto al extremo izquiedo de la base*/
        int posicionX0 = xPosition - ((PX_X_CM*width)/2);
        body.get(0).posicionadorX(posicionX0);
        
        /*posiciona el centro de la base en el lugar indicado en 
         * el eje X*/
        
        int posicionX1 = xPosition - ((PX_X_CM*width)/2);
        body.get(2).posicionadorX(posicionX1);
        
        /*posiciona la pared derecha en su ubicacion moviendola 
         * junto al extremo derecho de la base*/
        int posicionX2 = xPosition + ((PX_X_CM*width)/2 -PX_X_CM);
        body.get(1).posicionadorX(posicionX2);
        
        int dPositionX = xPosition - this.posicionX;
        
        this.posicionX = xPosition;
        
        for(Element e: content){
            e.posicionadorX(e.getPosicionX() - dPositionX);
        }
        
    }
    
    /**
     * posiciona el centro de la tapa en el 
     * lugar correspondiente en el eje Y.
     * 
     * @param  posicionY es un numero que dice donde desea que este el
     * centro la tapa en el eje Y
     */
    @Override
    public void posicionadorY(int yPosition){        
        
        /*posciona las paredes encima de la base y actualiza el top*/
        int posicionYP = yPosition - PX_X_CM*height;
        body.get(0).posicionadorY(posicionYP);
        body.get(1).posicionadorY(posicionYP);
        
        /*posiciona el lado inferior de la base en la posicion indicada
           en el eje Y y actualiza el base*/
        int posicionYB = yPosition - PX_X_CM;
        body.get(2).posicionadorY(posicionYB);
        
        int dPositionY = yPosition - this.posicionY;
        
        this.posicionY = yPosition;
        
        for(Element e: content){
            e.posicionadorY(e.getPosicionY() + dPositionY);
        }
    }
    
    /**
     * devuelve la informacion del objeto
     * 
     * @return un arreglo de String en el que la primera posicion es la clase
     * del elemento y en la segunda posicion el numero del elemento
     */
    @Override
    public String[] information(){
        return new String[]{"Cup", String.valueOf(number)};
    }
    
    /**
     * le dice a elemento dado que esta cayendo en una clase especifica para que sepa como reaccionar
     * 
     * @param element es el elemento al que se le dara la orden
     */
    public void fallingElement(Element element){
        if(element != null){
            element.prepareFalling();
            element.fallInCup(this);
        }
    }
    
    /**
     * deja caer este elemento en la taza dada si tiene una cubierta se dejara caer en la cubierta, 
     * si no se vera si puede caer dentro de esta, si no se vera si puede cubrirla sin tener problemas 
     * con los contenedores que contienen directa o indirectamente a la copa, si hay algun problema se colocara
     * este elemento en el primer contenedor con el que se tenga un problema.
     * si cabe en todo contenedor simplemente se colocara este elemento cubriendo la taza
     * 
     * @param cup taza en la que se va a colocar el elemento
     */
    @Override
    public void fallInCup(Cup cup){
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
                    setCoverlet(null);
                    /*la copa aparte de caer como un elemento, debe decirle a su contenido que caiga con el
                    ya que puede haber colision entre un elemento contenido y uno externo con algun elemento externo
                    cuando su contenido caiga*/
                    ArrayList<Element> tempContent = clearContent();
    
                    /*cada elemento que estaba contenido caera en esta copa y redeterminara su posicion para saber si puede seguir*/
                    for(Element e: tempContent){
                        this.fallingElement(e);
                    }

                    this.fallingElement(tempCoverlet);
                }
            }
        }
    }
    
    /**
     * deja caer este elemento en la tapaa dada si tiene una cubierta se dejara caer en la cubierta, 
     * si no se vera si puede cubrirla sin tener problemas 
     * con los contenedores que contienen directa o indirectamente a la tapa, 
     * si hay algun problema se colocara
     * este elemento en el primer contenedor con el que se tenga un problema.
     * si cabe en todo contenedor simplemente se colocara este elemento cubriendo la tapa
     * 
     * @param lid tapa en la que se va a colocar el elemento
     */
    @Override
    public void fallInLid(Lid lid){
        if(lid.isCovered()){
            lid.getCoverlet().fallingElement(this);
        }else{
           
            Container currentContainer = lid.getContainer();
        
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
                lid.putAsCover(this);
                Element tempCoverlet = getCoverlet();
                setCoverlet(null);
                /*la copa aparte de caer como un elemento, debe decirle a su contenido que caiga con el
                ya que puede haber colision entre un elemento contenido y uno externo con algun elemento externo
                cuando su contenido caiga*/
                ArrayList<Element> tempContent = clearContent();

                /*cada elemento que estaba contenido caera en esta copa y redeterminara su posicion para saber si puede seguir*/
                for(Element e: tempContent){
                    this.fallingElement(e);
                }

                this.fallingElement(tempCoverlet);
            }
        
        }
    }
    
    /**
     * hace que el elemento se elimine de las referencias de todos los objetos que lo referencien a el y luego hace que todo lo que
     * se afecte por su eliminacion se actualice. el elemento sigue existiendo pero se reinicia.
     * 
     * para eliminarlo se debe eliminar todas las referencias hacia el, no solo las que maneja este elemento
     * 
     * @param limitFalling es la posicion en la que se pondra la cubierta de este elemento en caso
     * de que tenga y no tenga un elemento en su base
     */
    @Override
    public boolean eliminate(int limitFall){
        //elementos con los que dejara de interactuar con el elemento
        Element oldBase = getBase();
        Element oldCoverlet = getCoverlet();
        Container oldContainer = getContainer();
        ArrayList<Element> oldContent = clearContent();
        
        //olvido de estos elementos
        setBase(null);
        setCoverlet(null);
        setContainer(null);
        
        if(base != null){
            base.setCoverlet(null);
        }
        if(coverlet != null){
            coverlet.setBase(null);
        }
        if(container != null){
            container.eliminateElement(this);
        }
        
        for(Element e: oldContent){
            if(oldBase != null){
                oldBase.fallingElement(e);    
            }
            else{
                e.posicionadorY(limitFall);
                
                oldBase = e;
            }
        }
        
        //colocar encima de la base lo que estaba encima de este elemento
        
        if(oldBase != null){
            oldBase.fallingElement(oldCoverlet);    
        }
        else{
            if(oldCoverlet != null){
                oldCoverlet.posicionadorY(limitFall);
                
                oldCoverlet.fallingElement(oldCoverlet.getCoverlet());
            }
        }
        
        return true;
    }
    
    /*=====================================================
       
                    metodos Container
    
    =======================================================*/
    
    /**
     * determina si el contenedor puede contener un elemento
     * 
     * @param elemento que se desea saber si puede ser contenido por este contenedor
     * 
     * @return true si el elemento no esta cubierta o si el numero es mayor que el
     * de esta copa y el top content de este se encuentra dentro de la copa
     */
    public boolean canContentIt(Element element){
        boolean canContent = false;
        
        Element topContent = getRealTopContent();
        
        int numberElement = element.getNumber();
        int numberThis = getNumber();
        
        boolean isNotCovered = !isCovered();
        boolean insideMe = element.isInContainer(this);
        boolean elementFits = numberElement < numberThis;
        boolean topElementOutsideThis = false;
        
        if(topContent != null){
            int posYTop = topContent.getPosYTop();
            int posY = getPosYTop();
            
            topElementOutsideThis = posYTop < posY;
        }
        
        if((isNotCovered || insideMe) && (elementFits || topElementOutsideThis)){
            canContent = true;
        }
    
        return canContent;
    }
    
    /**
     * pone un elemento en el contenedor cayendo en el contenedor y puede cubrirlo, 
     * cubrir lo que este encima de el o entrar en el.
     * 
     * @param element es el elemento que se deja caer en el contenedor
     */
    public void fallingInContainer(Element element){
        Element coverlet = getCoverlet();
        if(coverlet != null){
            coverlet.fallingElement(element);
        }
        else{
            fallingElement(element);
        }
    }
    
    /**
     * pone el elemento dado en el TopContent de la copa si tiene ya que cupo en ella o coloca el elemento dentro de la copa, 
     * ademas añade el elemento al contenido de la copa y lo posiciona correctamente dentro de el.
     * 
     * @param elemento que se coloca dentro de la copa en la cima del contenido de la copa
     */
    public void fallingInTopContent(Element element){
        if(element != null){
            Element topContent = getTopContent();
            
            if(topContent != null){
                topContent.fallingElement(element);
            }
            else{
                element.setBase(this);
                element.posicionadorY(getPosYBase());
                
                element.setContainer(this);
                insertElement(element);
            }
        }
    }
    
    /**
     * pone al elemento dado, en el elemento en la cima (topContent) de lo contenido en
     * el contendor y lo añade al registro de las cosas que tiene contenidas este
     * contenedor
     * 
     * @param element elemento que se desea colocar en el contenido de este contenedor
     */
    public void insertElement(Element element){
        if(content != null){
            content.add(element);
        }
    }
    
    /**
     * pone al elemento dado justo encima del indice anterior al dado y mueve todo el contenido que
     * estuviera una posicion arriba
     * 
     * @param element elemento que se desea colocar en el contenido de este contenedor
     */
    public void insertElement(Element element, int indx){
        if(content != null){
            if(indx < content.size()){
                content.add(indx, element);
            }
            else{
                content.add(element);
            }
        }
    }
    
    /**
     * pone al elemento dado justo encima del elemento base dado contenido en este
     * contenedor y si base tiene elementos encima los dezplaza una posicion hacia arriba.
     * 
     * si el elemento base no se encuentra contenido en este contenedor lo deja en la cima del
     * contenido
     * 
     * @param element elemento a colocar en el contenedor
     * @param base elemento que define en que posicion del contenido se colocara.
     * 
     */
    public void insertElement(Element element, Element base){
        int indx = 1;

        for(Element e: content){
            if(e.equals(base)){
                break;
            }
            indx++;
        }
        
        if(content != null){
            if(0 < indx && indx< content.size()){
                content.add(indx, element);
            }
            else{
                content.add(element);
            }
        }
    }
    
    /**
     * elimina el elemento dado del contenido del contenedor si no esta en el contenido, no hace nada
     * 
     * @param element elemento a eliminar
     */
    public void eliminateElement(Element element){
        content.remove(element);
    }
    
    /**
     * saca el contenido del contenedor haciendo que ya no lo referencien a el si no 
     * a en donde este contenido este contenedor y luego vacia su registro de elementos
     * contenidos.
     * 
     * no inserta los elementos contenidos en este contenedor en el otro, se debe hacer
     * que los elementos vuelvan a determinar su posicion
     * 
     * @return elementos que estaban contenidos en este contenedor
     */
    public ArrayList<Element> clearContent(){
        //guarda los elementos que antes estaban en este contenedor para devolverlos
        ArrayList<Element> elementsContent = new ArrayList();
        
        /*los elementos ahora referencian al contenedor de este contendor
           para de cierta forma determinar donde se encuentran en este momento al no estar
           contenidos en este contendor,tambien se desacoplaran de entres si ya que que sin
           este contenedor teoricamente no estan stackeados hasta caer nuevamente*/
        for(Element e: content){
            e.setBase(null);
            e.setContainer(getContainer());
            e.setCoverlet(null);
            elementsContent.add(e);
        }
        
        /*el elemento en la base de lo contenido ahora referenciara a null como si estuviera
           suspendido mientras se determina su posicion*/
        Element currentBase = getBaseContent();
        if(currentBase != null){
            currentBase.setBase(null);
        }
        /*se limpiea el registro de lo contenido en este contenedor*/
        content.clear();
        
        return elementsContent;
    }
    
    /**
     * devuelve el elemento que se encuentra encima de cualquier otro 
     * elemento que este contenido en esta copa
     * 
     * @return elemento en la cima dentro del actual, en caso de que no tengo elementos retorna null
     */
    @Override
    public Element getTopContent(){
        Element topContent = null;
        if(content.size()!= 0){
            topContent = content.get(content.size() - 1);
        }
        return topContent;
    }
    
    /**
     * devuelve el verdadero elemento que se encuentra en la cima de todo lo contenido
     * en este, siendo ese el elemento que mas sobre sale de todo lo contenido en esta copa
     * 
     * @return devuelve el elemento que esta mas alto dentro de lo contenido en este
     */
    @Override
    public Element getRealTopContent(){
        Element realTopContent = getTopContent();
        
        Element currentTopContent = null;
        int posYCurrent;
        int posYrealTop;
        
        Element currentElementContainer = getTopContent();
        if(currentElementContainer != null){
            currentTopContent = currentElementContainer.getTopContent();
        }
        
        while(currentElementContainer!= null && currentTopContent != null){
            
            posYCurrent = currentTopContent.getPosYTop();
            
            posYrealTop = realTopContent.getPosYTop();
            
            if(posYCurrent < posYrealTop){
                realTopContent = currentTopContent;
            }
            
            currentElementContainer = currentTopContent;
            currentTopContent = currentElementContainer.getTopContent();
        }
        
        return realTopContent;
    }
    
    /**
     * devuelve el elemento que se encuentra encima de cualquier otro 
     * elemento que este contenido en esta copa
     * 
     * @return elemento en la cima dentro del actual, en caso de que no tengo elementos retorna null
     */
    @Override
    public Element getBaseContent(){
        Element topContent = null;
        if(content.size()!= 0){
            topContent = content.get(0);
        }
        return topContent ;
    }
    
    /**
     * devuelve la posicion del eje Y en la que se encuentra la base interior del
     * contenedor
     * 
     * @return posicion Y de la base interior
     */
    @Override
    public int getPosYBase(){
        return posicionY - PX_X_CM;
    }
    
    /*=====================================================
       
                    metodos auxiliares
    
    =======================================================*/
    
    /**
     * le da la estructura de una taza a los rectangulos dentro
     * del arreglo del cuerpo.
     */
    @Override
    protected void bodyBuilder(){
        
        int posYPared = posicionY - PX_X_CM*height;
        int posXParedIzquierda = posicionX - ((PX_X_CM*width)/2);
        int alturaParedes = (height-1)*PX_X_CM;
        //pared izquierda de la taza
        body.add(new Rectangle(posXParedIzquierda, posYPared, alturaParedes, PX_X_CM, color));
        
        int posXParedDerecha = posicionX + ((PX_X_CM*width)/2 - PX_X_CM);
        //pared derecha de la taza
        body.add(new Rectangle(posXParedDerecha, posYPared, alturaParedes, PX_X_CM, color));
        
        int posYBase = posicionY - PX_X_CM;
        int posXBase = posicionX - ((PX_X_CM*width)/2);
        //base de la taza
        body.add(new Rectangle(posXBase, posYBase, PX_X_CM, PX_X_CM*width, color));
    }
}

