/*
 * Created by Emanuele Santoro <santoro@autistici.org>
 * Homepage : http://santoro.homeunix.net
 * 
 * The more recent version of this app is usually available
 * via CVS at santoro.homeunix.net. CVS access may be unavailable.
 */

import javax.swing.* ;
import java.awt.* ;
import java.awt.event.* ;


public class ColFrame extends JFrame {
    private JMenuBar BARRA = null ;
    private ColoumbPanel pannello ;
    
    public ColFrame(String title) {
        super(title) ;
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.genMenuBar();
        
        this.setSize(800 , 600);
        this.setMinimumSize(new Dimension(800, 600));
        this.setMaximumSize(new Dimension(800 , 600));
        this.setResizable(false);
    }
    
    public ColFrame(String title,  ColoumbPanel p0) {
        super(title) ;
        this.setPanel(p0);
        this.pannello = p0 ;
        this.genMenuBar();
        
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
        this.setSize(800 , 600);
        this.setMinimumSize(new Dimension(800, 600));
        this.setMaximumSize(new Dimension(800 , 600));
        this.setResizable(false);

    }
    
    
    public void setPanel(ColoumbPanel p0) { 
        this.add(p0) ;
    }
    
    private void genMenuBar() {
        if ( this.BARRA == null) {
            this.BARRA = new JMenuBar() ;
            
            JMenu fMen = new JMenu("File") ;
            JMenuItem esci = new JMenuItem("Esci") ;
            esci.addActionListener(new ActionListener(){ 
                public void actionPerformed(ActionEvent e0 ) {
                    System.exit(0) ;
                }
            }) ;
            
            JMenu modMenu = new JMenu("Tools") ;
            
            fMen.add (esci) ;
            this.BARRA.add(fMen) ;
            this.BARRA.add(modMenu) ;
            this.setJMenuBar(this.BARRA);
            
        }
    }
}
