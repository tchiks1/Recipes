

<?php
 
require_once 'include/DB_Functions.php';
$db = new DB_Functions();
 
// json response array
//$response = array("error" => FALSE);
$resultat = array();
$i=0;
if (isset($_POST['id'])){
	
    $id = $_POST['id'];		
	
	$resultat=$db->selectdonnee($id) ;
	$valeur = array();
    $quantity=array("4","5","6");																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																									
	$name=array("saucisson","pate","poivron"); 
	
	if($resultat){
	    $i = count($resultat);
		
	    for ($is = 0; $is<$i ;$is++){
			
			$quantity=$db->get_ingr($resultat[$is][5]);
			
	        $valeur[] = array(
			"img_user"=>$resultat[$is][0],
			"nom_rec"=>$resultat[$is][1],
			"duration"=>$resultat[$is][2],
			"descp"=>$resultat[$is][3],
			"guest"=>$resultat[$is][4],
			"id"=>$resultat[$is][5],			
			"ingr"=>array("qte"=>$quantity,"name"=>$name));
	        
	}
        header('Content-type: application/json');
		
	echo (json_encode($valeur));
}}


/*else {
	$quantity=array("4","5","6");																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																									
	$name=array("saucisson","pate","poivron");
    $resultat = $db->sel();
    $valeur = array();
    if ($resultat) {
        $i = count($resultat);
        for ($is = 0; $is < $i; $is++) {
            $valeur[] = array("img_user" => $resultat[$is][0], "nom_rec" => $resultat[$is][1], "duration" => $resultat[$is][2], 
			"descp" => $resultat[$is][3], "guest" => $resultat[$is][4], "id" => $resultat[$is][5],"ingr"=>array("qte"=>$quantity,
             "name"=>$name));
        }
        header('Content-type: application/json');
        echo(json_encode($valeur));
    }
}*/
		?>