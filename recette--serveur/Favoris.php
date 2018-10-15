<?php
/**
 * Created by PhpStorm.
 * User: ALI_SINHA
 * Date: 13/10/2018
 * Time: 14:38
 */

require_once 'include/DB_Functions.php';
$db = new DB_Functions();
// json response array
$response = array("error" => FALSE);
    if (isset($_POST["cooker_id"])&& isset($_POST["id"]) && isset($_POST["en"])){
        
		$cookerid = $_POST["cooker_id"];
        $id = $_POST["id"]; 
		$en = $_POST["en"];

        $verif = $db->veriffavoris($cookerid,$id,$en);
       /* if (count($verif)!=0){
            $response["error"] = FALSE;
            echo json_encode($response);
        }else{
            echo 'ndem2';
            $store = $db->storefavoris($cookerid,$id,$en);
            if ($store){
                $response["error"] = FALSE;
                echo json_encode($response);
            }else{
                echo 'ndem';
            }
        }*/
       if ($verif){
           //echo 'true';
          $update=  $db->updatefav($cookerid,$id,$en);
          if ($update){
              $response["error"] = FALSE;
              echo json_encode($response);
          }else{
              $response["error"] = TRUE;
              echo json_encode($response);
          }
       }else{
           $store = $db->storefavoris($cookerid,$id,$en);
           if ($store){
               $response["error"] = FALSE;
               echo json_encode($response);
           }else{
               $response["error"] = TRUE;
               echo json_encode($response);
           }

       }
    }elseif (isset($_POST["id2"])){
        $id2 = $_POST["id2"];
        $resultat=$db->selectfavoris($id2) ;
        $valeur = array();
        if($resultat) {
            $i = count($resultat);
            for ($is = 0; $is < $i; $is++) {
                $valeur[] = array("img_user" => $resultat[$is][0], "nom_rec" => $resultat[$is][1], "duration" => $resultat[$is][2], "descp" => $resultat[$is][3], "guest" => $resultat[$is][4], "id" => $resultat[$is][5]);
            }
            header('Content-type: application/json');
            echo(json_encode($valeur));
        }
    }else{
        $response["error"] = TRUE;
        echo json_encode($response);
    }

?>