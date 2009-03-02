/*
 * Created by Emanuele Santoro <santoro@autistici.org>
 * Homepage : http://santoro.homeunix.net
 * 
 * The more recent version of this app is usually available
 * via CVS at santoro.homeunix.net. CVS access may be unavailable.
 */

public class Carica {

    public Carica(int x, int y, double carica) {
        this.x = x;
        this.y = y;
        this.carica = carica;
    }
    public int x;
    public int y;
    public double carica;
    
    public boolean isPositive() {
        boolean rslt = true;
        if (this.carica > 0) {
            rslt  = true ;
        }
        if (this.carica < 0) {
            rslt = false ;
        }
        return rslt ;
    }
    public boolean isNegative() {
        boolean rslt = true;
        if (this.carica > 0) {
            rslt  = false ;
        }
        if (this.carica < 0) {
            rslt = true ;
        }
        return rslt ;
    }
    
    public int getX() {
        return this.x ;
    }
    
    public int getY() {
        return this.y ;
    }
    
    public void setX( int x2) {
        this.x = x2 ;
    }
    
    public void setY( int y2) {
        this.y = y2 ;
    }
}
