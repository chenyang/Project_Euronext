/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package javacollecteur;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import java.util.logging.* ;

/**
 *
 * @author fabien
 */
public class Main {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        try {

            BD laBD = new BD();
            laBD.openDataAccess();
            
            Logger logger = Logger.getLogger("Parser");
            logger.setUseParentHandlers(false);
            
            Handler fh = new FileHandler("JavaCollecteur.log");           
            fh.setFormatter(new LogFormatter());
            ConsoleHandler cf = new ConsoleHandler();
            cf.setFormatter(new LogFormatter());

            logger.addHandler(fh);
            logger.addHandler(cf);

            List<Map> lesSocietes = laBD.getLesSociete();
            List<Map> lesMotCles = laBD.getLesMotsCles();
            
             for (Map uneSociete : lesSocietes) {

               new Parser(uneSociete,lesMotCles,laBD,"",logger).run();              
               //new Thread(new Parser(uneSociete,lesMotCles,laBD,"",logger)).start();
               
             }          
		
        } catch (Exception ex) {           
            System.out.println("Fatal ERROR : "+ex.toString());
        }
    	
        System.out.println("hello");
    }

   
}
