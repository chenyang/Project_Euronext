<?php
header("Content-Type: text/html; charset=utf-8");
ini_set("memory_limit","64M");
set_time_limit(0);
require_once('myconn.php');


//Functionalities:-----------------------------------------------------------------------------------------------------
//Get middle part of a str
function getmidstr($L,$R,$str)
{  
  $int_l=strpos($str,$L);
  $int_r=strpos($str,$R);
  If ($int_l>-1&&$int_l>-1)
    {
    $str_put=substr($str,$int_l+strlen($L),($int_r-$int_l-strlen($L)));
    return $str_put;
    }
}

 function toUTF8($str){
        $encoding = mb_detect_encoding($str, array('ASCII','UTF-8','GB2312','GBK','BIG5'));
        return mb_convert_encoding($str, 'utf-8', $encoding);
    }




//We clear and insert related data into database before we start
function initialisation($conn)
{
			//Initialisation
			$sql1="delete from fait_apparaitre";
			$sql2="delete from correspond"; 
			$sql3="delete from page_web"; 
			$sql4="delete from appartient_indice";
			$sql5="delete from entreprise"; 
			$sql6="delete from sous_secteur";
			$sql7="delete from secteur";
			$sql8="delete from super_secteur";
			$sql9="delete from industrie";
			$sql10="delete from pays";
			$sql11="delete from mot_cle";
			$sql12="delete from categorie";
			$sql13="delete from indice";
			
			$result1=mysqli_query($conn, $sql1);
			$result2=mysqli_query($conn, $sql2);
			$result3=mysqli_query($conn, $sql3);
			$result4=mysqli_query($conn, $sql4);
			$result5=mysqli_query($conn, $sql5);
			$result6=mysqli_query($conn, $sql6);
			$result7=mysqli_query($conn, $sql7);
			$result8=mysqli_query($conn, $sql8);
			$result9=mysqli_query($conn, $sql9);
			$result10=mysqli_query($conn, $sql10);
			$result11=mysqli_query($conn, $sql11);
			$result12=mysqli_query($conn, $sql12);
			$result13=mysqli_query($conn, $sql13);
			
			
			//Insert into pays data
			$sql="insert into PAYS values ('0001', 'Paris')";
			$result=mysqli_query($conn, $sql);
			$sql="insert into PAYS values ('0002', 'Bruxelles')";
			$result=mysqli_query($conn, $sql);
}

function  insert_Categorie($conn)
{			
			
			//Insert into Categorie values
			$sql="insert into CATEGORIE values('CATEGORIE_001','RSE/social')";
			$result=mysqli_query($conn, $sql);
			$sql="insert into CATEGORIE values('CATEGORIE_002','Environnement et Ecologie')";
			$result=mysqli_query($conn, $sql);
			$sql="insert into CATEGORIE values('CATEGORIE_003','Charte')";
			$result=mysqli_query($conn, $sql);
			$sql="insert into CATEGORIE values('CATEGORIE_004','Certification')";
			$result=mysqli_query($conn, $sql);
			$sql="insert into CATEGORIE values('CATEGORIE_005','Labels')";
			$result=mysqli_query($conn, $sql);
			
			//mysqli_close($conn);	//We close the connection
}

function insert_Correspond($conn)
{			
			mysqli_query($conn, "set names utf8");
			//Insert into CORRESPOND Values
			
			$sql="insert into CORRESPOND values('CATEGORIE_001','commerce responsable');";
			$result=mysqli_query($conn, $sql);
			$sql="insert into CORRESPOND values('CATEGORIE_001','commerces responsables');";
			$result=mysqli_query($conn, $sql);
			$sql=toUTF8("insert into CORRESPOND values('CATEGORIE_001','d¨¦veloppement durable');");
			//echo $sql;
			$result=mysqli_query($conn, $sql);
			$sql="insert into CORRESPOND values('CATEGORIE_001','engagement durable');";
			$result=mysqli_query($conn, $sql);
			$sql="insert into CORRESPOND values('CATEGORIE_001','entreprise responsable');";
			$result=mysqli_query($conn, $sql);
			$sql="insert into CORRESPOND values('CATEGORIE_001','entreprises responsables');";
			$result=mysqli_query($conn, $sql);
			$sql=toUTF8("insert into CORRESPOND values('CATEGORIE_001','responsabilit¨¦ sociale et environnementale');");
			$result=mysqli_query($conn, $sql);
			$sql="insert into CORRESPOND values('CATEGORIE_001','investissement socialement responsable');";
			$result=mysqli_query($conn, $sql);
			$sql="insert into CORRESPOND values('CATEGORIE_001','investissement social');";
			$result=mysqli_query($conn, $sql);
			$sql="insert into CORRESPOND values('CATEGORIE_001','investissement sociaux');";
			$result=mysqli_query($conn, $sql);
			$sql="insert into CORRESPOND values('CATEGORIE_001','audit social');";
			$result=mysqli_query($conn, $sql);
			$sql=toUTF8("insert into CORRESPOND values('CATEGORIE_001','audit soci¨¦tal');");
			$result=mysqli_query($conn, $sql);
			$sql=toUTF8("insert into CORRESPOND values('CATEGORIE_001','responsabilit¨¦ soci¨¦tale');");
			$result=mysqli_query($conn, $sql);
			$sql=toUTF8("insert into CORRESPOND values('CATEGORIE_001','bien-¨ºtre des salari¨¦s');");
			$result=mysqli_query($conn, $sql);
			$sql="insert into CORRESPOND values('CATEGORIE_001','humanitaire');";
			$result=mysqli_query($conn, $sql);
			$sql="insert into CORRESPOND values('CATEGORIE_001','humanitaires');";
			$result=mysqli_query($conn, $sql);
			$sql="insert into CORRESPOND values('CATEGORIE_001','droits humains');";
			$result=mysqli_query($conn, $sql);
			$sql="insert into CORRESPOND values('CATEGORIE_001','associations locales');";
			$result=mysqli_query($conn, $sql);
			$sql="insert into CORRESPOND values('CATEGORIE_001','association locale');";
			$result=mysqli_query($conn, $sql);
			
			
			
			$sql="insert into CORRESPOND values('CATEGORIE_002','environnement');";
			$result=mysqli_query($conn, $sql);
			$sql="insert into CORRESPOND values('CATEGORIE_002','environnemental');";
			$result=mysqli_query($conn, $sql);
			$sql="insert into CORRESPOND values('CATEGORIE_002','environnementaux');";
			$result=mysqli_query($conn, $sql);
			$sql="insert into CORRESPOND values('CATEGORIE_002','environnements');";
			$result=mysqli_query($conn, $sql);
			$sql=toUTF8("insert into CORRESPOND values('CATEGORIE_002','haute qualit¨¦ environnementale');");
			$result=mysqli_query($conn, $sql);
			$sql="insert into CORRESPOND values('CATEGORIE_002','risque environnemental');";
			$result=mysqli_query($conn, $sql);
			$sql="insert into CORRESPOND values('CATEGORIE_002','risques environnementaux');";
			$result=mysqli_query($conn, $sql);
			$sql=toUTF8("insert into CORRESPOND values('CATEGORIE_002','d¨¦veloppement ¨¦co-responsable');");
			$result=mysqli_query($conn, $sql);
			$sql=toUTF8("insert into CORRESPOND values('CATEGORIE_002','¨¦cologie');");
			$result=mysqli_query($conn, $sql);
			$sql=toUTF8("insert into CORRESPOND values('CATEGORIE_002','¨¦cologique');");
			$result=mysqli_query($conn, $sql);
			$sql=toUTF8("insert into CORRESPOND values('CATEGORIE_002','¨¦cologiques');");
			$result=mysqli_query($conn, $sql);
			$sql=toUTF8("insert into CORRESPOND values('CATEGORIE_002','responsabilit¨¦ ¨¦cologique');");
			$result=mysqli_query($conn, $sql);
			$sql=toUTF8("insert into CORRESPOND values('CATEGORIE_002','empreinte ¨¦cologique');");
			$result=mysqli_query($conn, $sql);
			
			
			$sql="insert into CORRESPOND values('CATEGORIE_003','code de conduite');";
			$result=mysqli_query($conn, $sql);
			$sql=toUTF8("insert into CORRESPOND values('CATEGORIE_003','code ¨¦thique');");
			$result=mysqli_query($conn, $sql);
			$sql=toUTF8("insert into CORRESPOND values('CATEGORIE_003','charte ¨¦thique');");
			$result=mysqli_query($conn, $sql);
			$sql="insert into CORRESPOND values('CATEGORIE_003','charte RSE');";
			$result=mysqli_query($conn, $sql);
			
			
			$sql="insert into CORRESPOND values('CATEGORIE_004','ISO 8001');";
			$result=mysqli_query($conn, $sql);
			$sql="insert into CORRESPOND values('CATEGORIE_004','ISO 26000');";
			$result=mysqli_query($conn, $sql);
			$sql="insert into CORRESPOND values('CATEGORIE_004','ISO 14001');";
			$result=mysqli_query($conn, $sql);
			$sql="insert into CORRESPOND values('CATEGORIE_004','ISO 9001');";
			$result=mysqli_query($conn, $sql);
			$sql="insert into CORRESPOND values('CATEGORIE_004','AFAQ 1000NR');";
			$result=mysqli_query($conn, $sql);
			$sql=toUTF8("insert into CORRESPOND values('CATEGORIE_004','code ¨¦thique');");
			$result=mysqli_query($conn, $sql);
			$sql="insert into CORRESPOND values('CATEGORIE_004','EMAS');";
			$result=mysqli_query($conn, $sql);
			$sql="insert into CORRESPOND values('CATEGORIE_004','SA 8000');";
			$result=mysqli_query($conn, $sql);
			$sql="insert into CORRESPOND values('CATEGORIE_004','SD 21000');";
			$result=mysqli_query($conn, $sql);
			
			
					
			$sql="insert into CORRESPOND values('CATEGORIE_005','label BBC');";
			$result=mysqli_query($conn, $sql);
			$sql="insert into CORRESPOND values('CATEGORIE_005','label diversit¨¦');";
			$result=mysqli_query($conn, $sql);
			$sql=toUTF8("insert into CORRESPOND values('CATEGORIE_005','label ¨¦galit¨¦ professionnelle');");
			$result=mysqli_query($conn, $sql);
			$sql=toUTF8("insert into CORRESPOND values('CATEGORIE_005','label responsabilit¨¦ sociale');");
			$result=mysqli_query($conn, $sql);
			
}

//Insert Table indice value
function insert_Indice($conn)
{

			$sql="INSERT INTO indice VALUES ('ASPI');";
			$result=mysqli_query($conn, $sql);
			$sql="INSERT INTO indice VALUES ('AUCUN');";
			$result=mysqli_query($conn, $sql);
			$sql="INSERT INTO indice VALUES ('DJSI');";
			$result=mysqli_query($conn, $sql);
			$sql="INSERT INTO indice VALUES ('ESI');";
			$result=mysqli_query($conn, $sql);
			$sql="INSERT INTO indice VALUES ('FTSE');";
			$result=mysqli_query($conn, $sql);
}



//Insert into Mot_cle table values
function insert_mot_cles($chemain, $conn)
{
	$handle=fopen($chemain, "r");

		while($data=fgets($handle, 4096))
		{
			$arr_res=explode(", ", $data);
			foreach ($arr_res as $i)
			{
				//$i=mb_convert_encoding($i, "gb2312");
				//$i=substr_replace($i, "", 0, 1);//elimite first blank
				echo '!'."$i<br/>";
				mysqli_query($conn, "set names utf8");
				$sql="insert into mot_cle values('$i', '$i')";
				$result=mysqli_query($conn, $sql);
			}
		}
		//because we forget to add the first word
		//$i=$arr_res[0];
		//$sql="insert into mot_cle values('$i', '$i')";
		//$result=mysqli_query($conn, $sql);
}


//Insert into industrie, super_secteur, secteur, sous_secteur values..depending on csv file downloaded from the website
function insert_isss($industrie_id, $industrie_nom, $super_secteur_id, $super_secteur_nom, $secteur_id, $secteur_nom, $sous_secteur_id, $sous_secteur_nom, $conn)
{
	$sqlquery="insert into industrie values ('".$industrie_id."', '".$industrie_nom."')";
	$result=mysqli_query($conn, $sqlquery);
	
	$sqlquery="insert into super_secteur values ('".$super_secteur_id."', '".$super_secteur_nom."','".$industrie_id."')";
	$result=mysqli_query($conn, $sqlquery);
	
	$sqlquery="insert into secteur values ('".$secteur_id."', '".$secteur_nom."','".$super_secteur_id."')";
	$result=mysqli_query($conn, $sqlquery);
	
	
	$sqlquery="insert into sous_secteur values ('".$sous_secteur_id."', '".$sous_secteur_nom."','".$secteur_id."')";
	$result=mysqli_query($conn, $sqlquery);
	
}


//Insert into Entreprise, industrie, ss...table values, relying on csv file downloaded from the website
function analyse_csv($chemain, $pay_id, $conn)
{
	$row = 1;
	if (($handle = fopen($chemain, "r")) !== FALSE) {
		while (($data = fgetcsv($handle, 1000, ",")) !== FALSE) {
			if($row<=4)
			{	$row++;
				continue;
			}
			$num = count($data);
			//echo "<p> $num fields in line $row: <br /></p>\n";
			//echo "entreprise name: ".$data[0]."<br/>";
			//echo "sous-secteur:".$data[5]."<br/>";
			$arr1=explode(' ', $data[5], 2);
			//echo "first is:".$arr1[0].", and the second is ".$arr1[1]."<br/>";
				
			//echo "secteur:".$data[38]."<br/>";
			$arr2=explode(' ', $data[38], 2);
			//echo "super-secteur:".$data[37]."<br/>";
			$arr3=explode(' ', $data[37], 2);
			//echo "industrie:".$data[36]."<br/>";
			$arr4=explode(' ', $data[36], 2);

			//Insert into industrie, sous_secteur, secteur, super_secteur values
			insert_isss($arr4[0], $arr4[1],$arr3[0], $arr3[1],$arr2[0], $arr2[1],$arr1[0], $arr1[1], $conn);
			
			
			$isin=$data[1];
			$nom=str_replace("'", "_", $data[0]);
			$temp_site=getmidstr("(", ")", $data[33]);
			$arra_temp=explode(";", $temp_site, 2);
			$site_internet=str_replace("\"", "", $arra_temp[0]);
			$zone_investissement=$data[15];
			$compartiment=$data[14];
			$chiffre_affaire=0;//??????
			$dernier=str_replace(",", ".", $data[7]);
			$volume=$data[8];
			$eligibilite=$data[16];
			$capital=$data[11];	
			$capitalisation=str_replace(",", "", $data[13]);		
			$plan_epargne=$data[39];//Oui ou non
			//$mode_cotation='';//?????????????
			$mnemo=$data[4];
			//$resultat=0;//?????????????
			$id_pay=$pay_id;
			$id_sous_secteur=$arr1[0];
			
			$sqlquery="insert into entreprise(ISIN, NOM, ZONE_INVESTISSEMENT, COMPARTIMENT, CHIFFRE_AFFAIRE, DERNIER, VOLUME, ELIGIBILITE, CAPITAL, CAPITALISATION, PLAN_EPARGNE, MNEMO,  SITE_INTERNET, ID_PAYS, ID_SOUS_SECTEUR) values ('$isin', '$nom', '$zone_investissement', '$compartiment', $chiffre_affaire, $dernier, $volume, '$eligibilite', $capital, $capitalisation, '$plan_epargne', '$mnemo', '$site_internet', '$id_pay', '$id_sous_secteur')";
			
			//echo "TESTING!<br/>".$sqlquery;
			$result=mysqli_query($conn, $sqlquery);
			//mysqli_close($conn);
		}
		fclose($handle);
	}//End of If
	
	else{
		echo "Sorry the file could not be opended..";
	}
	
}//End of Function 

//-------------------------------------------------------------------------------------------------------------


//Main part of php..

//Initialize the database
initialisation($conn);

//Fill table mot_cle values
$chemain="dictionary.txt";
insert_mot_cles($chemain, $conn);

//Fill table Categorie et correspond
insert_Categorie($conn);
insert_Correspond($conn);

//Fill table Indice
insert_Indice($conn);

//Fill entreprise and industry information. Paris and Brusselles
$chemain="PARIS.csv";
$pay_id="0001";//Paris
analyse_csv($chemain, $pay_id, $conn);

$chemain="BRUXELLES.csv";
$pay_id="0002";//Bruxelles
analyse_csv($chemain, $pay_id, $conn);


echo "success!!";


?>