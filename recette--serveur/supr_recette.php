<?php
 
require_once 'include/DB_Functions.php';
$db = new DB_Functions();
 
// json response array
$response = array("error" => FALSE);
 
if (isset($_POST['num_supr']) ) {
		
	$num_supr = $_POST['num_supr'];
	 
 $res=$db->supcompte($num_supr);
 if($res){
	  $response["error"] = TRUE;
        $response["error_msg"] = "suppression effectue avec success" ;	
        echo json_encode($response);
  
	}else{
		 $response["error"] = FALSE;
        $response["error_msg"] = "echec de la suppression" ;	
		echo json_encode($response);
	 }
}
?>

