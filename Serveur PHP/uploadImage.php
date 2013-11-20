<?php 

$date=getdate();

      $urldate=$date['year'].$date['month'].$date['month'].$date['hours'].$date['minutes'].$date['seconds'];
 $target_path  = "";
 $target_path = $target_path . basename( $_FILES['uploadedfile']['name']);
 if(move_uploaded_file($_FILES['uploadedfile']['tmp_name'], $target_path)) {
  echo $target_path;
 } else{
  echo "ERROR";
 }

?>