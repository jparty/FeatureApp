<?php 
ini_set('display_errors', 'On');
error_reporting(E_ALL);
include("DBConnect.php");

if(isset($_POST["myHttpData"])) { //if there is data to import in the database
	 $arr=$_POST["myHttpData"];
	 $decarr = json_decode($arr,true); 

	 $count = count($decarr);
	 
	 $values = array();
	 $update_values = array();
	 $sql='';
	 
	 //we parse every table passed in the json
	 for ($x=0;$x<$count;$x++){
		$newrec = $decarr[$x];
		foreach($newrec as $tableName => $table) {
		
			//init the value to put in sql query
			$nbValue=0;
			$allColomn="";
			foreach($table as $colomnName => $colomn){
				if (!is_array($colomn)){ //if it is not an array (so was not a listArray before (//photo))
					if($colomnName != "registredInLocal" && $colomnName != "selected"){
						$nbValue++;
						if($colomn == "")
							$colomn=" ";

						if(is_int($colomn))
							$allColomn .= ''.$colomn.', ';
						else{
							$colomn=pg_escape_string($colomn);
							$allColomn .= '\''.$colomn.'\', ';
						}
					}
				}
				else {
					foreach($colomn as $colomnName => $colomn){
						if($colomnName != "registredInLocal" && $colomnName != "selected"){
							$nbValue++;
							if($colomn =="")
								$colomn=" ";

							if(is_int($colomn))
								$allColomn .= ''.$colomn.', ';
							else{
								$colomn=pg_escape_string($colomn);
								$allColomn .= '\''.$colomn.'\', ';
							}
						}
					}
				}
			}
			if($nbValue!=0) {
				$allColomn = substr($allColomn, 0,-2);
				$sql .="SELECT merge_".$tableName."(".$allColomn.");
				";
			}
		}		
	 }
	
	$bool = pg_query($conn,$sql);
	
	 
	 $fp = fopen('testSqlite.txt', 'w+');
	
	fwrite($fp, $sql);
	fclose($fp);
	
	if ($bool) //success of the request
		echo "OK";
	else
		echo pg_last_error($conn);

	
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


