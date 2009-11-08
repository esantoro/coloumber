/*
 * Created by Emanuele Santoro <santoro@autistici.org>
 * Homepage : http://santoro.homeunix.net
 * 
 * The more recent version of this app is usually available
 * via CVS at santoro.homeunix.net. CVS access may be unavailable.
 */

import javax.swing.* ;
import java.awt.event.* ;
import java.awt.* ;
import java.util.ArrayList ;


public class ColoumbPanel extends JPanel implements Runnable, MouseListener{

    /*
     * Dunque, cominciamo con i commenti:
     * La classe ColoumbPanel è la classe principale, ed è quella che svolge
     * la maggior parte del lavoro: si occupa di mantenere all'interno un 
     * array dinamico in cui salvare le cariche (istanze della classe Carica)
     * 
     * Si occupa anche di disegnare sul pannello tutte le cariche sotto forma
     * di pallini, di disegnare alcune linee che indichino la consistenza della
     * forza di Couloumb tra le due cariche e di generare le freccette (ok, non 
     * vi fate ingannare: le freccette sembrano una cazzata da programmare, ma 
     * hanno preso il loro tempo OK?? TE STA DICU DE SINE!! MOOOOOI M'HAI CHIEDERE
     * SCUSA!! MOOOOIIII!!! <grazie PiERre per le buone maniere che ci insegni>)
     * 
     * Da aggiungere: la cigliegina sulla torta sarebbe l'ultima linea, ovvero
     * quella che indica *dove* dovrebbe (e dico dovrebbe) andare a finire la 
     * carica principale alla fine di tutti questi "sbattuliciamenti" dovuti 
     * all'influenza delle  altre cariche (voglio ricordare, al fine di 
     * mantenere il ragionamento  lucido a un ipotetico me del futuro ed ad altri 
     * eventuali lettori di questo mio capriccio di codice, che il fine di questo 
     * simulatore è vedere che  tipo di forza subisce una carica puntiforme per 
     * effetto di altre cariche puntiformi).
     *
     *  Last edit: 2008/05/12 @ 9:34 p.m.
     * 
     * Edit ( 2008/06/28 @ 20:48 ) e porco de dio, l'ultima freccia è un casino.
     * Come posso fare ?
     * Partiamo dal concetto che il merdoso e MASTODONTICO frammentuzzo di codice
     * che ora come ora disegna le freccettine fa parte, di fatto, di 
     * paint(Grapghics) e che l'ho messa in un altra funzione *solo* per avere
     * ordine tra le varie fasi dello svolgimendo del programma.
     * 
     * Devo risolvere sto problema, eeek!
     * 
     * 
     * Annuncio di oggi (vedi data):
     *      AAA cercasi nuova migliore amica, ma di razza
     *          perchè bastarda l'ho già avuta.
     *      Magari qualche giovane programmatrice legge questo codice
     *      e mi contatta (santoro@autistici.org).
     *  
     * Last edit: 2008/11/2 @ 01:29 p.m. :
     * Ok con le amiche sto a posto, ho risolto, no problem.
     * Cmq devo ancora mettere la freccetta finale. E non mi colla.
     * 
     * 
     */
    private Graphics G ;
    private Operazioni OPZ ;
    private Carica PRINCIPALE = null ; 
    
    // deve generare le forze si o no? questa variabile dovrebbe assolvere il compito di 
    // rispondere a questa domanda ; a dire la verità ora come ora non credo che venga
    // effettivamente usata.
    private boolean genForze  = false ; 
    
    
    private ArrayList<Carica> lista = new ArrayList<Carica>()  ; // l'ArrayList che dovrà tener log delle cariche
    
    
    public ColoumbPanel() {
        this.addMouseListener(this) ;
        Thread t = new Thread(this) ;
        t.start();
    }
    public void paint (Graphics g ) {
        int i = 0;
        while (i < this.lista.size() ) {
            g.setColor(Color.GRAY) ;
            g.fillOval( lista.get(i).getX() , lista.get(i).getY(), 15, 15);
            g.setColor(Color.BLACK) ;
            g.drawString(String.valueOf(lista.get(i).getCarica()) , lista.get(i).getX()-5 , lista.get(i).getY()-5) ;
            g.drawString(
		    "( " + String.valueOf(lista.get(i).getX()) + " ; " + String.valueOf(this.lista.get(i).getY()) + " )" ,
                    this.lista.get(i).getX()-25,
                    this.lista.get(i).getY()+25 
                    ) ;
            ++i ;   
        }
        
        if (this.PRINCIPALE != null) {
            g.setColor(Color.GRAY) ;
            g.fillOval(this.PRINCIPALE.getX(), this.PRINCIPALE.getY(), 16, 16) ;
            g.setColor(Color.DARK_GRAY);
            g.fillOval(this.PRINCIPALE.getX()+3, this.PRINCIPALE.getY()+3, 10, 10) ;
            g.setColor(Color.BLACK); 
            g.drawString( String.valueOf(this.PRINCIPALE.getCarica()) , this.PRINCIPALE.getX()-6 , this.PRINCIPALE.getY()-6) ;
            g.drawString(
		 "( " + String.valueOf(this.PRINCIPALE.getX()) + " ; " + String.valueOf(this.PRINCIPALE.getY()) + " )" ,
		 this.PRINCIPALE.getX()-25,
		 this.PRINCIPALE.getY()+25 
		 ) ;
        }
        
        if (this.genForze) {
                this.calcola_forze(g); 
        }
    }
    public void enableForze() {
        // questo metodo si occupa di attivare/disattivare la visualizzazione delle forze
	// edit: ma lo fa davvero?? :-\ *
        this.genForze = (!(this.genForze)) ;
    }
    
    private void calcola_forze( Graphics g ) {
	/* Perchè?
	 * Per mantenere concettualmente differenziate le varie parti del 
	 * Thread/programma/funzione, ho ``spezzettato'' la funzione paint(Graphics) in
	 * più sotto-funzioni, tipo questa.
	 * Divide et impera! Enjoy :-)
	 */

        int i;
        /*
        Carica seconda = this.lista.get(++i);
        while (i <= this.lista.size()) {
        g.setColor(Color.RED);
        g.drawLine(prima.x, prima.y, seconda.x, seconda.y);
        g.setColor(Color.BLACK);
         */
        g.setColor(Color.RED);
        for (i=0 ; i<this.lista.size() ; i++) {
            double distance = Tools.getDistance(PRINCIPALE, this.lista.get(i)) ;
            
            if ( 
                 ( this.PRINCIPALE.isPositive() && this.lista.get(i).isPositive() )
                    || 
                 ( this.PRINCIPALE.isNegative() && this.lista.get(i).isNegative() )
             ) {
                /*
                 * Bene, ho visto dopo diversi "studi" (*) che possiamo identificare 4 casi diversi, 
                 * in base alla posizione di una carica esploratrice rispetto ad una carica principale
                 * (la carica detta "generatrice di campo").
                 * Il tutto quindi si riassume in quattro semplici if (spero).
                 * If-fiamo, dunque.
                 * 
                 * Quella che nei nostri ``studi'' si chiama GAMMA, qui è rappresentata da this.PRINCIPALE .
                 * Ovvero (per quel programmatore ignorante che non ha letto i 
                 * miei appunti prima di leggere questo codice) : 
                 *        *___________________________________________________ *
                 *         |                                                  |
                 *         | this.PINCIPALE è la carica generatrice di campo. |
                 *         |__________________________________________________|
                 *        *                                                    *
                 *  (*) <---- Diciamo anche "seghe mentali sulla tastiera"
                 */
                
                if (this.lista.get(i).getX() < this.PRINCIPALE.getX() && this.lista.get(i).getY() < this.PRINCIPALE.getY()) {
                    /*
                     * Caso 0 : la carica relativa è situata più a sinistra e più
                     * sotto rispetto alla carica principale.
                     */
                    g.drawLine(
                            this.PRINCIPALE.getX() + 7, 
                            this.PRINCIPALE.getY() + 7, 
                            this.PRINCIPALE.getX() + (this.PRINCIPALE.getX() - this.lista.get(i).getX()),
                            this.PRINCIPALE.getY() + (this.PRINCIPALE.getY() - this.lista.get(i).getY())
                    );
                    
                    g.setColor(Color.BLUE) ;
                    g.drawString(
                            String.valueOf(Tools.forzaCouloumb(PRINCIPALE, this.lista.get(i), this.OPZ.getKappa())) + " C" ,
                            this.PRINCIPALE.getX() + (int)((this.PRINCIPALE.getX() - this.lista.get(i).getX())/2) ,
                            this.PRINCIPALE.getY() + (int)((this.PRINCIPALE.getY() - this.lista.get(i).getY())/2)
                    ) ;
                    g.setColor(Color.RED) ;
                    
                    g.drawLine(
                            this.PRINCIPALE.getX() + (this.PRINCIPALE.getX() - this.lista.get(i).getX()),
                            this.PRINCIPALE.getY() + (this.PRINCIPALE.getY() - this.lista.get(i).getY()),
                            this.PRINCIPALE.getX() + (this.PRINCIPALE.getX() - this.lista.get(i).getX()),
                            (this.PRINCIPALE.getY() + (this.PRINCIPALE.getY() - this.lista.get(i).getY())) -7
                            );
                    g.drawLine(
                            this.PRINCIPALE.getX() + (this.PRINCIPALE.getX() - this.lista.get(i).getX()),
                            this.PRINCIPALE.getY() + (this.PRINCIPALE.getY() - this.lista.get(i).getY()),
                            this.PRINCIPALE.getX() + (this.PRINCIPALE.getX() - this.lista.get(i).getX()) - 7,
                            this.PRINCIPALE.getY() + (this.PRINCIPALE.getY() - this.lista.get(i).getY())
                            );
                }
                
                else if (this.lista.get(i).getX() < this.PRINCIPALE.getX() && this.lista.get(i).getY() > this.PRINCIPALE.getY()) {
                    /*
                     * Caso 1 : la carica relativa è situata più sopra ma più a 
                     * sinistra rispetto alla carica principale.
                     */
                    g.drawLine(
                            this.PRINCIPALE.getX() + 7, 
                            this.PRINCIPALE.getY() + 7, 
                            this.PRINCIPALE.getX() + (this.PRINCIPALE.getX() - this.lista.get(i).getX()),
                            this.PRINCIPALE.getY() + (this.PRINCIPALE.getY() - this.lista.get(i).getY())
                    );
                    
                    g.setColor(Color.BLUE) ;
                    g.drawString(
                            String.valueOf(Tools.forzaCouloumb(PRINCIPALE, this.lista.get(i), this.OPZ.getKappa())) + " C" ,
                            this.PRINCIPALE.getX() + (int)((this.PRINCIPALE.getX() - this.lista.get(i).getX())/2) ,
                            this.PRINCIPALE.getY() + (int)((this.PRINCIPALE.getY() - this.lista.get(i).getY())/2)
                    ) ;
                    g.setColor(Color.RED) ;

                    
                    g.drawLine(
                            this.PRINCIPALE.getX() + (this.PRINCIPALE.getX() - this.lista.get(i).getX()),
                            this.PRINCIPALE.getY() + (this.PRINCIPALE.getY() - this.lista.get(i).getY()),
                            this.PRINCIPALE.getX() + (this.PRINCIPALE.getX() - this.lista.get(i).getX()),
                            (this.PRINCIPALE.getY() + (this.PRINCIPALE.getY() - this.lista.get(i).getY())) + 7
                    );
                    g.drawLine(
                            this.PRINCIPALE.getX() + (this.PRINCIPALE.getX() - this.lista.get(i).getX()),
                            this.PRINCIPALE.getY() + (this.PRINCIPALE.getY() - this.lista.get(i).getY()),
                            this.PRINCIPALE.getX() + (this.PRINCIPALE.getX() - this.lista.get(i).getX()) - 7,
                            this.PRINCIPALE.getY() + (this.PRINCIPALE.getY() - this.lista.get(i).getY())
                    );

                }
                
                else if (this.lista.get(i).getX() > this.PRINCIPALE.getX() && this.lista.get(i).getY() > this.PRINCIPALE.getY()) {
                    /*
                     * Caso 2 : la carica relativa è posta più inalto e più a destra
                     * rispetto alla carica principale .
                     */
                    g.drawLine(
                            this.PRINCIPALE.getX() + 7, 
                            this.PRINCIPALE.getY() + 7, 
                            this.PRINCIPALE.getX() + (this.PRINCIPALE.getX() - this.lista.get(i).getX()),
                            this.PRINCIPALE.getY() + (this.PRINCIPALE.getY() - this.lista.get(i).getY())
                    );
                    g.setColor(Color.BLUE) ;
                    g.drawString(
                            String.valueOf(Tools.forzaCouloumb(PRINCIPALE, this.lista.get(i), this.OPZ.getKappa())) + " C" ,
                            this.PRINCIPALE.getX() + (int)((this.PRINCIPALE.getX() - this.lista.get(i).getX())/2) ,
                            this.PRINCIPALE.getY() + (int)((this.PRINCIPALE.getY() - this.lista.get(i).getY())/2)
                    ) ;
                    g.setColor(Color.RED) ;
                            

                    
                    g.drawLine(
                            this.PRINCIPALE.getX() + (this.PRINCIPALE.getX() - this.lista.get(i).getX()),
                            this.PRINCIPALE.getY() + (this.PRINCIPALE.getY() - this.lista.get(i).getY()),
                            this.PRINCIPALE.getX() + (this.PRINCIPALE.getX() - this.lista.get(i).getX()),
                            (this.PRINCIPALE.getY() + (this.PRINCIPALE.getY() - this.lista.get(i).getY())) +7
                            );
                    g.drawLine(
                            this.PRINCIPALE.getX() + (this.PRINCIPALE.getX() - this.lista.get(i).getX()),
                            this.PRINCIPALE.getY() + (this.PRINCIPALE.getY() - this.lista.get(i).getY()),
                            this.PRINCIPALE.getX() + (this.PRINCIPALE.getX() - this.lista.get(i).getX()) + 7,
                            this.PRINCIPALE.getY() + (this.PRINCIPALE.getY() - this.lista.get(i).getY())
                            );

                }
                
                else if (this.lista.get(i).getX() > this.PRINCIPALE.getX() && this.lista.get(i).getY() < this.PRINCIPALE.getY()) {
                    /*  
                     * Caso 3 : la carica relativa è posta più in basso e più a 
                     * destra rispetto alla carica principale.
                     */
                    g.drawLine(
                            this.PRINCIPALE.getX() + 7, 
                            this.PRINCIPALE.getY() + 7, 
                            this.PRINCIPALE.getX() - Math.abs(this.PRINCIPALE.getX() - this.lista.get(i).getX()),
                            this.PRINCIPALE.getY() + (this.PRINCIPALE.getY() - this.lista.get(i).getY())
                    );
                    
                    g.setColor(Color.BLUE) ;
                    g.drawString(
                            String.valueOf(Tools.forzaCouloumb(PRINCIPALE, this.lista.get(i), this.OPZ.getKappa())) + " C" ,
                            this.PRINCIPALE.getX() + (int)((this.PRINCIPALE.getX() - this.lista.get(i).getX())/2) ,
                            this.PRINCIPALE.getY() + (int)((this.PRINCIPALE.getY() - this.lista.get(i).getY())/2)
                    ) ;
                    g.setColor(Color.RED) ;

                    g.drawLine(
                            this.PRINCIPALE.getX() + (this.PRINCIPALE.getX() - this.lista.get(i).getX()),
                            this.PRINCIPALE.getY() + (this.PRINCIPALE.getY() - this.lista.get(i).getY()),
                            this.PRINCIPALE.getX() + (this.PRINCIPALE.getX() - this.lista.get(i).getX()),
                            (this.PRINCIPALE.getY() + (this.PRINCIPALE.getY() - this.lista.get(i).getY())) -7
                    );
                    g.drawLine(
                            this.PRINCIPALE.getX() + (this.PRINCIPALE.getX() - this.lista.get(i).getX()),
                            this.PRINCIPALE.getY() + (this.PRINCIPALE.getY() - this.lista.get(i).getY()),
                            this.PRINCIPALE.getX() + (this.PRINCIPALE.getX() - this.lista.get(i).getX()) + 7,
                            this.PRINCIPALE.getY() + (this.PRINCIPALE.getY() - this.lista.get(i).getY())
                    );

                }
            }
            else {
                g.drawLine(this.PRINCIPALE.getX()+8, this.PRINCIPALE.getY()+8 , this.lista.get(i).getX()+7, this.lista.get(i).getY()+7);
                /*
                 * Anche qui abbiamo le quattro possibilità di prima: dobbiamo quindi
                 * gestire quattro eveniente, quattro. IF.
                 * 
                 * IF-fiamo, quindi.
                 */
                
             if (this.lista.get(i).getX() < this.PRINCIPALE.getX() && this.lista.get(i).getY() < this.PRINCIPALE.getY()) {
                    /*
                     * Caso 0 : la carica relativa è situata più a sinistra e più
                     * sotto rispetto alla carica principale.
                     */
                    
                    g.drawLine(
                            this.lista.get(i).getX() +7 ,
                            this.lista.get(i).getY() +7 ,
                            this.lista.get(i).getX() +14,
                            this.lista.get(i).getY() +7
                    );
                    
                    g.setColor(Color.BLUE) ;
                    g.drawString(
                            String.valueOf(Tools.forzaCouloumb(PRINCIPALE, this.lista.get(i), this.OPZ.getKappa())) + " C" ,
                            this.PRINCIPALE.getX() - (int)((this.PRINCIPALE.getX() - this.lista.get(i).getX())/2) ,
                            this.PRINCIPALE.getY() - (int)((this.PRINCIPALE.getY() - this.lista.get(i).getY())/2)
                    ) ;
                    g.setColor(Color.RED) ;
                    
                    g.drawLine(
                            this.lista.get(i).getX() +7 ,
                            this.lista.get(i).getY() +7 ,
                            this.lista.get(i).getX() +7 ,
                            this.lista.get(i).getY() +14
                    );
                }
                
                else if (this.lista.get(i).getX() < this.PRINCIPALE.getX() && this.lista.get(i).getY() > this.PRINCIPALE.getY()) {
                   
                    g.drawLine(
                            this.lista.get(i).getX() +7 ,
                            this.lista.get(i).getY() +7 ,
                            this.lista.get(i).getX() +14 ,
                            this.lista.get(i).getY() +7
                    );
                    
                    g.setColor(Color.BLUE) ;
                    g.drawString(
                            String.valueOf(Tools.forzaCouloumb(PRINCIPALE, this.lista.get(i), this.OPZ.getKappa())) + " C" ,
                            this.PRINCIPALE.getX() - (int)((this.PRINCIPALE.getX() - this.lista.get(i).getX())/2) ,
                            this.PRINCIPALE.getY() - (int)((this.PRINCIPALE.getY() - this.lista.get(i).getY())/2)
                    ) ;
                    g.setColor(Color.RED) ;
                    
                    g.drawLine(
                            this.lista.get(i).getX() +7 ,
                            this.lista.get(i).getY() +7 ,
                            this.lista.get(i).getX() +7,
                            this.lista.get(i).getY() -5
                    );


                }
                
                else if (this.lista.get(i).getX() > this.PRINCIPALE.getX() && this.lista.get(i).getY() > this.PRINCIPALE.getY()) {
                    g.drawLine(
                            this.lista.get(i).getX() +7 ,
                            this.lista.get(i).getY() +7 ,
                            this.lista.get(i).getX()  -7,
                            this.lista.get(i).getY() +7
                    );
                    
                    g.setColor(Color.BLUE) ;
                    g.drawString(
                            String.valueOf(Tools.forzaCouloumb(PRINCIPALE, this.lista.get(i), this.OPZ.getKappa())) + " C" ,
                            this.PRINCIPALE.getX() - (int)((this.PRINCIPALE.getX() - this.lista.get(i).getX())/2) ,
                            this.PRINCIPALE.getY() - (int)((this.PRINCIPALE.getY() - this.lista.get(i).getY())/2)
                    ) ;
                    g.setColor(Color.RED) ;
                    g.drawLine(
                            this.lista.get(i).getX() +7 ,
                            this.lista.get(i).getY() +7 ,
                            this.lista.get(i).getX() +7 ,
                            this.lista.get(i).getY() - 7 
                    );
                }
                
                else if (this.lista.get(i).getX() > this.PRINCIPALE.getX() && this.lista.get(i).getY() < this.PRINCIPALE.getY()) {
                    g.drawLine(
                            this.lista.get(i).getX() +7,
                            this.lista.get(i).getY() +7,
                            this.lista.get(i).getX() +7,
                            this.lista.get(i).getY() +14
                    );
                    
                    g.setColor(Color.BLUE) ;
                    g.drawString(
                            String.valueOf(Tools.forzaCouloumb(PRINCIPALE, this.lista.get(i), this.OPZ.getKappa())) + " C" ,
                            this.PRINCIPALE.getX() - (int)((this.PRINCIPALE.getX() - this.lista.get(i).getX())/2) ,
                            this.PRINCIPALE.getY() - (int)((this.PRINCIPALE.getY() - this.lista.get(i).getY())/2)
                    ) ;
                    g.setColor(Color.RED) ;
                    
                    g.drawLine(
                            this.lista.get(i).getX() +7 ,
                            this.lista.get(i).getY() +7,
                            this.lista.get(i).getX() -7 ,
                            this.lista.get(i).getY() +7
                    );
                }
               
            }
            // dio cane che sfacchianta!!!
            // Diahane!® (thanks Alisèf)
        }
        g.setColor(Color.BLACK);
    }
        
    public void addCarica( int x , int y, double columb) { 
        this.lista.add(new Carica( x , y , columb));
    }
    
    public void getRisultante(Graphics g) {
        
        // è un casino da implementare, porcoddio!!!
        
        int i ;
        int tempx=0 ;
        int tempy=0 ;
        
        for (i=0 ; i<=this.lista.size(); i++) {
            
        }
        
    }
    
    public void run() {
        while (true) {
            try {
                Thread.sleep(100);
                this.repaint();
            } 
            catch (InterruptedException ex) {
                // do nothing, bitch!
            }
        }
     }
    

    public boolean getForceStatus() {
        return this.genForze ;
    }
    
    public void setMainForce(int x , int y , double col) {
        this.PRINCIPALE = new Carica ( x , y , col ) ;
    }
    
    
    public void mouseExited(MouseEvent evt0) {}
    
    public void mouseEntered(MouseEvent evt0) {}

    public void mouseReleased(MouseEvent evt0) {
        int x = evt0.getX() ;
        int y = evt0.getY() ;
        this.OPZ.setCoords(x, y);
        // System.out.println("mouseReleased() called");
        
    }
    public void mousePressed(MouseEvent evt0) {}
    public void mouseClicked(MouseEvent evt0) {}

    public void setOperazioni(Operazioni opz) {
        this.OPZ = opz ;
    }
    
    
    protected class Vettore {
        public double coloumb ;
        public Vettore(double coloumb , Carica C1 , Carica C2) {
            this.coloumb = coloumb ;
        }
        
    }
}
