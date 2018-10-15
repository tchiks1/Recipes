<?php 

require_once 'include/DB_Functions.php';
$db = new DB_Functions();
// json response array
$response = array("error" => FALSE);

if (isset($_POST['cookers_id']) && isset($_POST['name']) && isset($_POST['duration']) && isset($_POST['description']) && isset($_POST['guest']) && isset($_POST['image']) ){
	
$id = $_POST['cookers_id'];
$name = $_POST['name'];
$duration = $_POST['duration'];
$description= $_POST['description'];
$guest = $_POST['guest'];
$image=$_POST['image'];

if(count($image)<5){
	$path = "images/default_profil.png";
}else{
    $date = date('Y/m/d H:i:s');

    $dat= str_replace("/",".",$date);
    $dats= str_replace(" ",".",$dat);
    $dates= str_replace(":",".",$dats);
	$path = "images/img-".$name.$dates."png";

	file_put_contents($path,base64_decode($image));	
}

$user = $db->insertpub($id,$name,$duration,$description,$guest,$path);
	 
		if ($user) {		
        $response["error"] = FALSE;		
        $response["error_msg"] = "post effectue avec succes";
        
	   }else {
        // user is not found with the credentials
        $response["error"] = TRUE;
        $response["error_msg"] = "erreur reessayez1!";
        }
		echo json_encode($response);
	}else{
		$response["error"] = TRUE;
        $response["error_msg"] = "erreur reessayez1!";
        
		echo json_encode($response);
		
	}

