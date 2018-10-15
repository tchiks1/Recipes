<?php 

require_once 'include/DB_Functions.php';
$db = new DB_Functions();
// json response array
$response = array("error" => FALSE);

if (isset($_POST['cookers_id']) && isset($_POST['name']) && isset($_POST['duration']) && isset($_POST['description']) && isset($_POST['guest']) && isset($_POST['ingr']) && isset($_POST['image']) ) {

    $id = $_POST['cookers_id'];
    $name = $_POST['name'];
    $duration = $_POST['duration'];
    $description = $_POST['description'];
    $guest = $_POST['guest'];
    $image = $_POST['image'];
    $ingr = $_POST['ingr'];
    $date = date('Y/m/d');
    $dates = str_replace("/", ".", "$date");
    
    $path = "tchiks-$name-$dates.png";
    $actualpath = "http://192.168.56.1/recette/$path";

         $user = $db->storedescp($image,$name,$duration,$description,$guest,$id,$ingr); 
        if ($user) {
            // user stored successfully
            $response["error"] = FALSE;
			file_put_contents($path, base64_decode($image));
			
            
            echo json_encode($response);

        }else{
            echo 'echec';
        }
}else{
    $response["error"] = TRUE;
    $response["error_msg"] = "entree tte les données";
    echo json_encode($response);
}
	?>

