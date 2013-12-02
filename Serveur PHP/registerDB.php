<?php 

//connection to the database
$conn = pg_connect("host=localhost
port=5432
dbname=urbapp
user=postgres
password='postgres'");

if(isset($_POST["myHttpData"])) { //if there is data to import in the database
	 $arr=$_POST["myHttpData"];
	 $decarr = json_decode($arr,true); 
	 
	 $count = count($decarr);
	 /*
	 $values = array();
	$update_values = array();
	 
	 for ($x=0;$x<$count;$x++){
		$newrec = $decarr[$x];
		$id=$newrec['id'];
		$description=$newrec['description'];
		
	 
	 
	 $values = "('".$id."', '".$description."')";
	 $update_values="description=".$description;
	 
	 $exist = pg_query($conn,"SELECT COUNT(*) FROM Comments WHERE id=".$id);
	 if (pg_fetch_array($exist)['0'] == 0)
		$postgisQuery .= "INSERT INTO Comments (comments_id, comments_description) VALUES ".$values.";";
	else
		$postgisQuery .= "UPDATE Comments SET comments_description=".$description.";";
	 }*/

	$resultats=array('comments' => array()); 
	
	 
	 $fp = fopen('testSqlite.txt', 'w');
	
	fwrite($fp, $arr);
	fclose($fp);
	
	pg_query($conn,$postgisQuery);
	/*
	$aImporter = json_decode($_POST["myHttpData"]);

	foreach($aImporter as $valeur) {
		echo $valeur;
		
	}*/
	
}
else {
	$retour = pg_query($conn,"SELECT * FROM Comments");

	$resultats=array('comments' => array()); 
	
	While($ligne = pg_fetch_assoc($retour)) { 
		foreach($ligne as $cle => $valeur){ 
			$tableau[$cle] = $valeur; 
		}
		$resultats['comments'][]=$tableau; 
	}
	
	$resultatsJSON = json_encode($resultats);

	print($resultatsJSON); pg_close($conn); 
}
?>


