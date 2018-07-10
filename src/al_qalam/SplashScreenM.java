/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package al_qalam;

import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.SplashScreen;

/**
 *
 * @author user
 */
public class SplashScreenM {
    
    static Software s;
    

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        
        int strx = 98 , stry = 145, strw = 150, strh = 10;
        int prox = 95 , proy = 128, prow, proh = 3;
        SplashScreen splash = SplashScreen.getSplashScreen();
        Graphics2D str = splash.createGraphics();
        str.setComposite(AlphaComposite.Clear);
        str.fillRect(strx, stry, strw, strh);
        str.setPaintMode();
        str.setColor(new Color(119, 104, 63));
        Font f = new Font("tahoma", 0, 10);
        str.setFont(f);
        str.drawString("Loading Login Class ...", strx, stry);
        splash.update();
        LogIn l = new LogIn();
        Graphics2D pro = splash.createGraphics();
        for (prow = 0; prow <= 53; prow++) {
            pro.setComposite(AlphaComposite.Clear);
            pro.setPaintMode();
            pro.setColor(new Color(172, 157, 117));
            pro.drawRect(prox, proy, prow, proh);
            splash.update();
        }
        str.setComposite(AlphaComposite.Clear);
        str.fillRect(strx, stry-10, strw, strh+5);
        str.setPaintMode();
        str.setColor(new Color(119, 104, 63));
        str.setFont(f);
        str.drawString("Loading Main Class ...", strx, stry);
        
       s = new Software();
        for (prow = 53; prow <= 160; prow++) {
            pro.setComposite(AlphaComposite.Clear);
            pro.setPaintMode();
            pro.setColor(new Color(172, 157, 117));
            pro.drawRect(prox, proy, prow, proh);
            splash.update();
        }
        str.setComposite(AlphaComposite.Clear);
        str.fillRect(strx, stry-10, strw, strh+5);
        str.setPaintMode();
        str.setColor(new Color(119, 104, 63));
        str.setFont(f);
        str.drawString("Loading Sign Up Class ...", strx, stry);
        SignUp su = new SignUp();
        for (prow = 160; prow <= 213; prow++) {
            pro.setComposite(AlphaComposite.Clear);
            pro.setPaintMode();
            pro.setColor(new Color(172, 157, 117));
            pro.drawRect(prox, proy, prow, proh);
            splash.update();
        }
        
        l.setlookNfeel();
        l.setVisible(true);
        
    }
}
