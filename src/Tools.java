/*
 * Created by Emanuele Santoro <santoro@autistici.org>
 * Homepage : http://santoro.homeunix.net
 * 
 * The more recent version of this app is usually available
 * via CVS at santoro.homeunix.net. CVS access may be unavailable.
 */

import java.lang.Math ;

public class Tools {
    public static Carica getVerso(Carica c1 , Carica c2 , double k) {
        Carica rslt = null ;
        double distanza = Math.sqrt( 
                Math.pow( (double)c2.x - (double)c1.x, 2 )
                +
                Math.pow( (double)c2.y - (double)c1.y , 2)
        );
        
        double forza = k *  ((c1.carica + c2.carica) / Math.pow(distanza, 2)) ;
        
        if ( forza > 0 ) {
            rslt = c1 ;
        }
        if ( forza < 0 ) {
            rslt = c2 ;
        }
        
        return rslt;
    }
    
    public static double getDistance(Carica c1 , Carica c2) {
        double distanza = Math.sqrt( 
                Math.pow( (double)c2.x - (double)c1.x, 2 )
                +
                Math.pow( (double)c2.y - (double)c1.y , 2)
        );
        return distanza ;
    }
    
    public static double forzaCouloumb(Carica c1 , Carica c2 , double KAPPA) {
        double distance = Tools.getDistance(c1, c2) ;
        double forza = ( KAPPA * ((c1.carica * c2.carica)/Math.pow(distance, 2)) ) ;
        return forza ;
    }
}
