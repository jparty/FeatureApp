<?php 

$date=getdate();

 $target_path  = "images/";
 $target_path = $target_path . basename( $_FILES['uploadedfile']['name']);
 if(move_uploaded_file($_FILES['uploadedfile']['tmp_name'], $target_path)) {
  echo $target_path;
 } else{
  echo "ERROR";
 }

?>