/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package javacollecteur;

import java.io.IOException;
import java.util.ArrayList;
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
public class Parser implements Runnable
{
    private Map infosSociete = null;
    private BD maBD = null;
    private List<Map> infosMotCles = null;
    private String url = "";
    private Logger logger= null;

    public Parser(Map laSociete,List<Map> lesMotCles,BD laBD,String laUrl,Logger unLog)
    {
        infosSociete=laSociete;
        infosMotCles=lesMotCles;
        maBD=laBD;
        logger = unLog;

        if(laUrl.equalsIgnoreCase(""))
            url=laSociete.get("url").toString();
        else
            url=laUrl;
    }    

    public List<String> getLinks(Document doc)
    {
        List<String> result = new ArrayList<String>();
        String linkTemp = "";

        Elements links = doc.select("a[href]");
            // href ...
            for (Element link : links)
            {
                linkTemp = link.attr("abs:href");

                /*
                 * Si c'est le même url -> on skip
                 * Si c'est bien le même dommaine -> OK
                 * Si c'est un pdf -> on skip
                 * Si il est deja dans la liste -> on skip
                 * Si en/ surement english -> on skip
                 * Si es/ surement espagnol -> on skip
                 * Si mailto -> on skip
                 * Si le lien est url# -> on skip
                 */               
                if(!url.equalsIgnoreCase(linkTemp) && linkTemp.startsWith(infosSociete.get("url").toString()) && !result.contains(linkTemp) && linkTemp.indexOf(".pdf") == -1 && linkTemp.indexOf("en/") == -1 && linkTemp.indexOf("es/") == -1 && linkTemp.indexOf("mailto") == -1 && !linkTemp.equalsIgnoreCase(url+"#"))
                {                   
                    result.add(linkTemp);
                }
            }
            
            return result;

    }

    public void run() {

        if(infosMotCles == null && infosSociete == null && maBD == null)
            return;       
       
        String pageCourante = url.substring(infosSociete.get("url").toString().length(),url.length());
       
        try
        {
            //Si on a pas vu la page -> On fouille la page
            if(maBD.estDejaVu(infosSociete.get("url").toString(), pageCourante).equalsIgnoreCase("0"))
            {
                logger.log(Level.INFO, "Parsing - {0}", url);
                Document doc = Jsoup.connect(url).get();
                //Si c'est une page 404 on skip
                 if(doc.title().indexOf("404") != -1)
                     return;

                parser(doc,pageCourante);

                if(infosSociete.get("url").toString().equalsIgnoreCase(url))
                {

                //On regarde si il y a des page en plus
                 for (String link : getLinks(doc))
                 {
                     pageCourante = link.substring(infosSociete.get("url").toString().length(),link.length());
                     if(maBD.estDejaVu(infosSociete.get("url").toString(), pageCourante).equalsIgnoreCase("0"))
                     {
                        //On lance un nouveau thread pour cette page
                    	//System.out.println("!"+pageCourante+" a plusieurs pages");
                        new Thread(new Parser(infosSociete, infosMotCles, maBD,link,logger)).start();

                        try {
                            Thread.sleep(3500);//On wait 3.5 sec
                        } catch(Exception e) {
                        }
                     }

                 }
                }
            }
        } 
        catch (Exception ex)
        {
            logger.log(Level.WARNING, "Parsing error - {0}",url+" - "+ex.toString());
        }
        finally
        {
            return;
        }
    }

    private void parser(Document doc,String pageCourante) throws Exception
    {        
        String pos = "";

       // maBD.insertPage(infosSociete.get("url").toString(), pageCourante); //On insère la page courante

        for (Map unMot : infosMotCles)
        {          
            pos = "autre";

            //Inspection de l'entete
            String keywords = doc.getElementsByAttributeValue("name", "keywords").attr("content");
            String description = doc.getElementsByAttributeValue("name", "description").attr("content");

            if(keywords.indexOf(unMot.get("libelleMotCle").toString().toLowerCase().trim()) != -1)
            {
                pos = "keywords";
                maBD.insert(infosSociete.get("url").toString(), pageCourante, unMot.get("idMotCle").toString(),pos, keywords);
                //************* TROUVE KEYWORD ***************
                //System.out.println("keyword "+description);
            }

             if(description.indexOf(unMot.get("libelleMotCle").toString().toLowerCase().trim()) != -1)
            {
                  pos = "description";
                  maBD.insert(infosSociete.get("url").toString(), pageCourante, unMot.get("idMotCle").toString(),pos, description);
                  //************* TROUVE DESCRIPTION ***************
                  //System.out.println("description "+description);
            }

            Elements all = doc.getElementsContainingOwnText(unMot.get("libelleMotCle").toString().toLowerCase().trim());
            
            if(!all.isEmpty())
            {
                for (Element elt : all)
                {                   

                    if(elt.tagName().equalsIgnoreCase("h1"))
                    {
                        pos = "h1";//pos here indicates where the element appears. e.g., here the idMot appears between tag <h1></h1>
                        String ownText=elt.ownText();//The text where the motcle appears
                        //maBD.insert(infosSociete.get("url").toString(), pageCourante, unMot.get("idMotCle").toString(),pos);
                          maBD.insert(infosSociete.get("url").toString(), pageCourante, unMot.get("idMotCle").toString(),pos, ownText);
                         //************* TROUVE H1 ***************
                        //System.out.println("<h1> "+elt.ownText());
                    }
                    else if(elt.tagName().equalsIgnoreCase("h2"))
                    {
                        pos = "h2";
                        String ownText=elt.ownText();//The text where the motcle appears
                        maBD.insert(infosSociete.get("url").toString(), pageCourante, unMot.get("idMotCle").toString(),pos,ownText);
                         //************* TROUVE H2 ***************
                        //System.out.println("<h2> "+elt.ownText());
                    }
                    else if(elt.tagName().equalsIgnoreCase("h3"))
                    {
                        pos = "h3";
                        String ownText=elt.ownText();//The text where the motcle appears
                        maBD.insert(infosSociete.get("url").toString(), pageCourante, unMot.get("idMotCle").toString(),pos, ownText);
                         //************* TROUVE H3 ***************
                        //System.out.println("<h3> "+elt.ownText());
                    }
                     else if(elt.tagName().equalsIgnoreCase("h4"))
                    {
                         pos = "h4";
                         String ownText=elt.ownText();//The text where the motcle appears
                         maBD.insert(infosSociete.get("url").toString(), pageCourante, unMot.get("idMotCle").toString(),pos, ownText);
                          //************* TROUVE H4 ***************
                        //System.out.println("<h4> "+elt.ownText());
                    }
                     else if(elt.tagName().equalsIgnoreCase("li"))
                    {
                         pos = "li";
                         String ownText=elt.ownText();//The text where the motcle appears
                         maBD.insert(infosSociete.get("url").toString(), pageCourante, unMot.get("idMotCle").toString(),pos, ownText);
                          //************* TROUVE MENU ***************
                        //System.out.println("<li> "+elt.ownText());
                    }
                     else if(elt.tagName().equalsIgnoreCase("div"))
                    {
                         pos = "div";
                         String ownText=elt.ownText();//The text where the motcle appears
                         maBD.insert(infosSociete.get("url").toString(), pageCourante, unMot.get("idMotCle").toString(),pos, ownText);
                          //************* TROUVE DIV ***************
                       // System.out.println("<div> "+elt.ownText());
                    }
                     else if(elt.tagName().equalsIgnoreCase("span"))
                    {
                         pos ="span";
                         String ownText=elt.ownText();//The text where the motcle appears
                         maBD.insert(infosSociete.get("url").toString(), pageCourante, unMot.get("idMotCle").toString(),pos, ownText);
                          //************* TROUVE SPAN ***************
                        //System.out.println("<span> "+elt.ownText());
                    }
                     else if(elt.tagName().equalsIgnoreCase("p"))
                    {
                         pos = "p";
                         String ownText=elt.ownText();//The text where the motcle appears
                         maBD.insert(infosSociete.get("url").toString(), pageCourante, unMot.get("idMotCle").toString(),pos, ownText);
                          //************* TROUVE PARAGRAPHE ***************
                        //System.out.println("<p> "+elt.ownText());
                    }
                     else if(elt.tagName().equalsIgnoreCase("a"))
                    {
                         pos = "a";
                         String ownText=elt.ownText();//The text where the motcle appears
                         maBD.insert(infosSociete.get("url").toString(), pageCourante, unMot.get("idMotCle").toString(),pos, ownText);
                          //************* TROUVE LIEN ***************
                       // System.out.println("<a> "+elt.ownText());
                    }
                    else
                    {
                        pos = "autre";
                        String ownText=elt.ownText();//The text where the motcle appears
                        maBD.insert(infosSociete.get("url").toString(), pageCourante, unMot.get("idMotCle").toString(),pos, ownText);
                         //************* TROUVE AUTRE ***************
                        //System.out.println("autre "+elt.tagName()+" - "+elt.ownText());
                    }

                }
            }

            //On check les images
            Elements img = doc.getElementsByTag("img");
            for (Element elt : img)
            {
                if(elt.attr("alt").indexOf(unMot.get("libelleMotCle").toString().toLowerCase().trim()) != -1)
                {                    
                     pos = "img";
                     String ownText=elt.ownText();//The text where the motcle appears
                     maBD.insert(infosSociete.get("url").toString(), pageCourante, unMot.get("idMotCle").toString(),pos, ownText);
                    //************* TROUVE IMAGE ***************
                    //System.out.println("image "+elt.attr("alt").toString());

                }
            }

             //On check les titles liens
            Elements links = doc.getElementsByTag("a");
            for (Element elt : links)
            {
                if(elt.attr("title").indexOf(unMot.get("libelleMotCle").toString().toLowerCase().trim()) != -1)
                {
                     pos = "a";
                     String ownText=elt.ownText();//The text where the motcle appears
                     maBD.insert(infosSociete.get("url").toString(), pageCourante, unMot.get("idMotCle").toString(),pos, ownText);
                    //************* TROUVE LIEN TITLE ***************
                    //System.out.println("a "+elt.attr("title").toString());

                }
            }
            
       }
        //Fin boucle mot clés

    }

}
