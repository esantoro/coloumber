/*
 * Created by Emanuele Santoro <santoro@autistici.org>
 * Homepage : http://santoro.homeunix.net
 * 
 * The more recent version of this app is usually available
 * via CVS at santoro.homeunix.net. CVS access may be unavailable.
 */

import java.awt.event.* ;
import java.awt.* ;
import javax.swing.* ;


public class Coloumb {
    
    public static void main(String args[]) {
        ColFrame fr = new ColFrame("Couloumb simulator") ;
        ColoumbPanel p = new ColoumbPanel() ;
        
        Operazioni op =  new Operazioni() ;
        op.setColPanel(p) ;
        // op.main(args) ;
        
        
        p.setOperazioni(op) ;
        
        fr.setPanel( p )  ;
        
        fr.setVisible(true); 
        
    }

}
