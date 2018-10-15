<?php
 
require_once 'include/DB_Functions.php';
$db = new DB_Functions();
$verif=FALSE;
 
// json response array
$response = array("error" => FALSE);
 
if (isset($_POST['name']) && isset($_POST['email']) && isset($_POST['password'])) {
 
    // receiving the post params
   $name = $_POST['name'];
    $password = $_POST['password'];
	 $email = $_POST['email'];
	/* $date = date('Y/m/d');
	 $dates= str_replace("/",".","$date");
	 
	 $path = "images/profil/profil-$name-$dates.png";
 
$actualpath = "http://192.168.137.1/volmap/$path";
$ide=$db->insertimage($actualpath,$dates);*/
//echo $actualpath;
 
    // check if user is already existed with the same email
	if ($db->isUserExisted($name)) {
		 // user already existed
		$verif=TRUE;
        $response["error"] = TRUE;
        $response["error_msg"] = "ce nom a deja ete utilise";	
        echo json_encode($response); }  		
		 
		 if ($verif == FALSE){
   	      // create a new user
        $user = $db->storeUser($name,$password,$email);
		
        if ($user) {						

			//file_put_contents($path,base64_decode($image));
            // user stored successfully
            $response["error"] = FALSE;
			 $response["error_msg"] = "success";
           /* $response["uid"] = $user["unique_id"];
            $response["user"]["name"] = $user["name"];
			$response["user"]["numero"] = $user["num_tel"];
            $response["user"]["email"] = $user["email"];
            $response["user"]["created_at"] = $user["date_inscription"];*/           
            echo json_encode($response);
        } else {
            // user failed to store
            $response["error"] = TRUE;
            $response["error_msg"] = "erreur d'enregistrement, veuillez reessayer plutard";
            echo json_encode($response);
        }}
    
} 

?>