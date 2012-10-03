package javacollecteur;
import java.util.regex.*;
import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *
 * Classe dÃ©rivÃ©e de gestion de la couche persistance base de donnÃ©es Oracle
 * @author Fabien RETIF
 * @version 1.0
 */
/**
 * Attention: 
 * parameter "url" est (homepage), comme: http://groupe.boursorama.fr
 * parameter "page" est comme-> /fr/contact OU plutot comme-> /acceuil.php..
 */

public class BD
{

    private String connexionString = "";
    private Connection laConnexion = null;

    public BD()
    {
        //connexionString = "jdbc:oracle:thin:@v240.ig.polytech.univ-montp2.fr:1521:ORA10";
    	//!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
    	//connexionString="jdbc:oracle:thin:@192.168.237.128:1521:orcl";
    	
    }

    /**
      * @fn openDataAccess () throws Exception
      * @brief ProcÃ©dure d'ouverture du conteneur
     *  @throws Exception en cas d'erreur
    */
    public synchronized  void openDataAccess () throws Exception
    {
        //Class cDriverOracle=Class.forName("oracle.jdbc.driver.OracleDriver");
        //Driver dDriverOracle=(java.sql.Driver)cDriverOracle.newInstance();
        //DriverManager.registerDriver(dDriverOracle);
    	//laConnexion=DriverManager.getConnection("jdbc:oracle:thin:@v240.ig.polytech.univ-montp2.fr:1521:ORA10","damien.vacher","oracle");
        
        Class cDriverMysql=Class.forName("com.mysql.jdbc.Driver");
    	Driver dDriverMysql=(java.sql.Driver)cDriverMysql.newInstance(); 
    	DriverManager.registerDriver(dDriverMysql);
        laConnexion=DriverManager.getConnection("jdbc:mysql://localhost:3306/mysi", "root", "123");
    }

    /**
      * @fn clisenDataAccess () throws Exception
      * @brief ProcÃ©dure de fermeture du conteneur
     * @throws Exception en cas d'erreur
    */
    public synchronized void closeDataAccess() throws Exception
    {
        laConnexion.close();
    }
   
    public synchronized List<Map> getLesSociete()  throws Exception
    {
       List<Map> lesResultats = new ArrayList<Map>();
       Map<String,String> uneLigne = null;
       Statement laRequete = null;

       openDataAccess();
       laRequete = laConnexion.createStatement();
       ResultSet rs= laRequete.executeQuery("Select ISIN, site_internet from Entreprise");
//!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
       while(rs.next())
       {
           uneLigne = new HashMap<String,String>();
           //On ajoute couple nomAttribut/Valeur
           uneLigne.put("idEuroNext", rs.getObject(1).toString());
           uneLigne.put("url", rs.getObject(2).toString());
         
           //On ajoute la ligne dans les rÃ©sultats
           lesResultats.add(uneLigne);
       }

       laRequete.close();
       closeDataAccess();
       return lesResultats;

    }

     public synchronized List<Map> getLesMotsCles()  throws Exception
    {
       List<Map> lesResultats = new ArrayList<Map>();
       Map<String,String> uneLigne = null;
       Statement laRequete = null;

       openDataAccess();
       laRequete = laConnexion.createStatement();
       ResultSet rs= laRequete.executeQuery("Select mot_cle , LIBELLE_MOT_CLE  from mot_cle");//!!!!!!c'est mot_cle!!
//!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
       while(rs.next())
       {
           uneLigne = new HashMap<String,String>();

           //On ajoute couple nomAttribut/Valeur
           uneLigne.put("idMotCle", rs.getObject(1).toString());
           uneLigne.put("libelleMotCle", rs.getObject(2).toString());

           //On ajoute la ligne dans les rÃ©sultats
           lesResultats.add(uneLigne);
       }

       laRequete.close();
       closeDataAccess();

       return lesResultats;
    }
  
    public synchronized String estDejaVu(String url,String page)  throws Exception
    {      
       String res = "0";
       Statement laRequete = null;

        if(page.length()==0)
           page = "/";
      
       openDataAccess();
       laRequete = laConnexion.createStatement();
       //!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
       //ResultSet rs= laRequete.executeQuery("select count(*) from Site_Web where Adresse_Site='"+url+"' and Est_accede_Par = '"+page+"'");
       ResultSet rs= laRequete.executeQuery("select count(*) from page_web p where URL='"+url+page+"' and URL_EST_ACCEDE_PAR = '"+url+"'");
       if(rs.next())
       {
          res = rs.getObject(1).toString();
       }

       laRequete.close();
       closeDataAccess();

       return res;

       
    }
   
    //Get number of occurence of one defined string
    public int getNumberOfOccurences(String str, String object){
    	int result=0;
    	Pattern p=Pattern.compile(object, Pattern.CASE_INSENSITIVE);
    	Matcher m=p.matcher(str);
    	while(m.find())
    		result++;
    	return result;
    }
    
    
    
    //public synchronized void insert(String url,String page,String idMotCle,String pos)  throws Exception
    public synchronized void insert(String url,String page,String idMotCle,String pos, String ownText)  throws Exception
    {       
       Statement laRequete = null;
       openDataAccess();      

       if(page.length()==0)
           page = "/";       
    
       laRequete = laConnexion.createStatement();
       //if(estDejaTrouve(url, page, idMotCle, pos).equalsIgnoreCase("0"))
       if(estDejaTrouve(url, page, idMotCle, pos).equalsIgnoreCase("0"))
       {
    	   System.out.println("J'ins�re un nouveau mot pour une nouvelle page");
    	   System.out.println(idMotCle);
    	   System.out.println("this word appears in: "+ownText);
    	   System.out.println("the position is <"+pos+">");
    	   System.out.println("real url est:"+url+page+" et le profondeur est"+getProfondeur(url+page));
    	   //See if this page is already inserted in page_web
    	   ResultSet rs=laRequete.executeQuery("select * from page_web where URL='"+url+page+"'");
    	   if(!rs.next()){
    	   laRequete.execute("Insert into page_web(URL, URL_EST_ACCEDE_PAR, PROFONDEUR) values('"+url+page+"', '"+url+"',"+getProfondeur(url+page)+")");
    	   }
           laRequete.execute("Insert into fait_apparaitre values('"+url+page+"', '"+idMotCle+"', "+getNumberOfOccurences(ownText, idMotCle)+", '"+pos+"')");
       }
      else
       {
    	  System.out.println("J'update une page pour laquelle j'ai dŽjˆ trouvŽ un mot");
    	  System.out.println(idMotCle);
   	   	  System.out.println("this word appears in: "+ownText);
    	  laRequete.execute("update fait_apparaitre set nombre_occurences =nombre_occurences+"+getNumberOfOccurences(ownText, idMotCle)+" where URL='"+url+page+"' and mot_cle='"+idMotCle+"' and pos='"+pos+"'");
          //laRequete.execute("update est_presente set nombre_occurences = nombre_occurences+1 where adresse_page='"+url+"' and Mot='"+idMotCle+"'");
       }
    	 
      laRequete.close();
      
       closeDataAccess();
       
    }

     //public synchronized String estDejaTrouve(String url,String page,String idMotCle,String pos)  throws Exception
     public synchronized String estDejaTrouve(String url,String page,String idMotCle,String pos)  throws Exception
    {
       String res = "0";
       Statement laRequete = null;

        if(page.length()==0)
           page = "/";

       openDataAccess();
       laRequete = laConnexion.createStatement();
       //ResultSet rs= laRequete.executeQuery("select count(*) from est_present where adresse_page='"+url+"' and Mot='"+idMotCle+"'");
       ResultSet rs= laRequete.executeQuery("select count(*) from fait_apparaitre where URL='"+url+page+"' and mot_cle='"+idMotCle+"' and pos='"+pos+"'");

       if(rs.next())
       {
          res = rs.getObject(1).toString();
       }

       laRequete.close();
       closeDataAccess();

       return res;


    }  
     
     
     private int getProfondeur(String s){
    	 int profondeur=0;
    	 
    	 for(int i=0; i<s.length(); i++){
    		 if(s.charAt(i)=='/')
    			 profondeur++;
    	 }
    	 
    	 if(s.charAt(s.length()-1)=='/')
    			 profondeur=profondeur-3;
    	 else if(s.charAt(s.length()-1)=='/'&&s.charAt(s.length()-2)=='/')
    		 	profondeur=profondeur-4;
    	 else	profondeur=profondeur-2;
    	 return profondeur; 
     }
     
     
/*
     public synchronized void insertPage(String url,String page)  throws Exception
    {
       Statement laRequete = null;
       openDataAccess();  

       if(page.length()==0)
           page = "/";

       laRequete = laConnexion.createStatement();
       laRequete.execute("Insert into page_web values('"+url+"','"+page+"')");
       laRequete.close();


       closeDataAccess();

    }
*/
}
