<?php

require_once 'include/DB_Functions.php';
$db = new DB_Functions();
 
// json response array
$response = array("error" => FALSE);
 
    // receiving the post params
    $name = $_POST['name'];
    $password = $_POST['password'];
 
    // get the user by email and password
    $user = $db->getUserByNameAndPassword ($name, $password);

    if ($user != false) {
        // user is found
        $response["error"] = FALSE;
		$response["SUCCESS"] = 1;
		
        $response["uid"] = $user["id"];
		
        $response["name"] = $user["name"];
		$response["email"] = $user["email"];
        $response["password"] = $user["password"];
        
        echo json_encode($response);
    } else {
        // user is not found with the credentials
        $response["error"] = TRUE;
        $response["SUCCESS"] = 0;
        echo json_encode($response);
    }

?>
