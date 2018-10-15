<?php
 
class DB_Functions {
 
    private $conn;
 
    // constructor
    function __construct() {
        require_once 'DB_Connect.php';
        // connecting to database
        $db = new Db_Connect();
        $this->conn = $db->connect();
    }
 
    // destructor
    function __destruct() {
         
    } 
    /**
     * Storing new user
     * returns user details
     */
    public function storeUser($name,$password,$email) {
        $uuid = uniqid('', true);
        $hash = $this->hashSSHA($password);
        $encrypted_password = $hash["encrypted"]; // encrypted password
       $confpasse= $hash["encrypted"];
 
        $stmt = $this->conn->prepare("INSERT INTO 
		cookers(name,password,email) VALUES(?, ?, ?)");
        $stmt->bind_param("sss", $name,$encrypted_password,$email);
        $result = $stmt->execute();
        $stmt->close();
 
        // check for successful store
        if ($result) {
            $stmt = $this->conn->prepare("SELECT * FROM  cookers WHERE email = ?");
            $stmt->bind_param("s", $email);
            $stmt->execute();
            $user = $stmt->get_result()->fetch_assoc();
            $stmt->close();
 
            return $user;
        } else {
            return false;
        }
    }

public function envoidonnee($is){
		
	    $stmt = $this->conn->prepare("SELECT * FROM users WHERE id = ?");
		$stmt->bind_param("s", $is);
		$stmt->execute();
		$user = $stmt->get_result()->fetch_assoc();
			return $user;
		 $stmt->close();
		}
		public function envoi_sms($num,$code){
		
	    $stmt = $this->conn->prepare("INSERT INTO sms (numero,code) VALUES (?,?)");
		$stmt->bind_param("ss", $num,$code);
		if($stmt->execute()){
		
			return TRUE;
		 $stmt->close();
		}else{
			return FALSE;}
		
		}
		public function col_num(){
		
	    $stmt = $this->conn->prepare("SELECT name,num_tel FROM inscription");
		$stmt->execute();
		$user = $stmt->get_result()->fetch_all();
			return $user;
		 $stmt->close();
		}
		
		public function getimage($id){
 		
            $stmt = $this->conn->prepare("SELECT *
			FROM  image WHERE id_img = ?");
            $stmt->bind_param("s", $id);
            if($stmt->execute()){
            $user = $stmt->get_result()->fetch_assoc();
            $stmt->close();
 
            return $user;
			}else{
				return FALSE;}
		}
		public function getimageprofil($id){
 		
            $stmt = $this->conn->prepare("SELECT *
			FROM  inscription WHERE id = ?");
            $stmt->bind_param("s", $id);
            if($stmt->execute()){
            $user = $stmt->get_result()->fetch_assoc();
            $stmt->close();
 
            return $user;
			}else{
				return FALSE;}
				
				
       
    }

    public function storedescp($image,$name,$duration,$description,$guest,$id,$ingr) {
        $a=array();
		$i=0;
        $stmt = $this->conn->prepare("INSERT INTO description(img, name, duration,description, guest, cookers_id) VALUES(?, ?, ?, ?, ?, ?)");
        $stmt->bind_param("ssssss",$image, $name, $duration,$description, $guest, $id);
        $result = $stmt->execute();
        $stmt->close();

        // check for successful store
        if ($result) {
            $stmt = $this->conn->prepare("SELECT * FROM  description WHERE cookers_id = ?");
            $stmt->bind_param("s", $id);
            $stmt->execute();
            $user = $stmt->get_result()->fetch_assoc();
            $stmt->close();
			
			$a=explode(";",$ingr);
             
			 while($i<count($a)){
				 $c=explode("/",$a[$i]); 
				 
			   $stmt = $this->conn->prepare("INSERT INTO ingredients(name,quantity,receipts_id) VALUES(?, ?, ?)");
               $stmt->bind_param("sss", $c[1],$c[0],$user["id"]);  
               $result = $stmt->execute();
               $stmt->close();	 
			   $i++;
			 }

            return $user;
        } else {
            return false;
        }
    }


    public function storefavoris($cookers_id,$id,$enable) {

        $stmt = $this->conn->prepare("INSERT INTO 
		favorites(cookers_id,receipts_id,enable,created_at) VALUES(?, ?, ?, NOW())");
        $stmt->bind_param("iii", $cookers_id,$id,$enable);
        $result = $stmt->execute();
        $stmt->close();

        // check for successful store
        if ($result) {
            $stmt = $this->conn->prepare("SELECT * FROM  favorites WHERE cookers_id = ?");
            $stmt->bind_param("s", $cookers_id);
            $stmt->execute();
            $user = $stmt->get_result()->fetch_assoc();
            $stmt->close();

            return $user;
        } else {
            return false;
        }
    }

	public function verifcode($num,$code){
 		$id=intval($num);
		$a="1";
		
				$stmt = $this->conn->prepare("UPDATE sms SET activated=? where id_sms=?");		
 
        $stmt->bind_param("ss",$a,$id);
 		if($stmt->execute()){
			
			$stmt = $this->conn->prepare("SELECT * FROM  sms WHERE id_sms = ?");
            $stmt->bind_param("s", $id);
            $stmt->execute();
            $user = $stmt->get_result()->fetch_assoc();
            $stmt->close();
 
            return $user;
			
            //$user = $stmt->get_result()->fetch_assoc();
          }else{
				return FALSE;
				}
		}
    public function veriffavoris($cooker_id,$id,$enable)
    {
        $stmt = $this->conn->prepare("SELECT * FROM  favorites WHERE receipts_id = ? AND cookers_id = ?");

        $stmt->bind_param("ii", $id, $cooker_id);
        $stmt->execute();
        $user = $stmt->get_result()->fetch_assoc();
        $stmt->close();
        if ($user) {
            return true;
        }else{
            return false;
        }
    }
				
				public function getteridsms($chemin){
           $stmt = $this->conn->prepare("SELECT * FROM 
		sms WHERE code =?");
 
        $stmt->bind_param("s", $chemin);
  if($stmt->execute()){
            $user = $stmt->get_result()->fetch_assoc();
            $stmt->close();
 
            return $user;
        } else {
            return false;
        }
    }
public function selectiondonnee(){
	    $stmt = $this->conn->prepare("Select *, SUM(id) From users Group By id_localisation Having SUM(id)=0");
		//$stmt->bind_param("s", $id);
		 $stmt->execute();
		$user = $stmt->get_result()->fetch_all();
	   // $rows = $stmt->num_rows;
			return $user;
			//return $rows;
		 $stmt->close();
		}

public function storeLocation($latitude, $longitude, $is2) {
       // $uuid = uniqid('', true);
		
		
        $stmt = $this->conn->prepare("INSERT INTO users(latitude, longitude, id, created_at) VALUES(?, ?, ?, NOW())");
        $stmt->bind_param("sss",$latitude, $longitude, $is2);
        $result = $stmt->execute();
        $stmt->close();
		return $result;
        // check for successful store
       /* if ($result) {
            $stmt = $this->conn->prepare("SELECT * FROM users WHERE latitude = ?");
            $stmt->bind_param("s", $latitude);
            $stmt->execute();
            $user = $stmt->get_result()->fetch_assoc();
            $stmt->close(); 
            return $user;
        } else {
            return false;
        }*/
    }
	 public function register($id,$id2) {
 
        $stmt = $this->conn->prepare("INSERT INTO 
		amis(id_pers1,id_pers2,created_at) VALUES(?, ?, NOW())");
        
		$stmt->bind_param("ss", $id,$id2);
		
		 $result=$stmt->execute();
		 $stmt->close();			
			
			 if ($result) {
            $stmt = $this->conn->prepare("SELECT * FROM  inscription WHERE id= ?");
			
            $stmt->bind_param("s", $id2);
			
            $stmt->execute();
            $user = $stmt->get_result()->fetch_assoc();
            $stmt->close();
 
            return $user;
        } else {
            return false;
        }}
		
		public function register2($id,$id2) {
 
        $stmt = $this->conn->prepare("INSERT INTO 
		amis(id_pers1,id_pers2,created_at) VALUES(?, ?, NOW())");
        
		$stmt->bind_param("ss", $id2,$id);
		
		 $result=$stmt->execute();
		 $stmt->close();			
		 
             return $result;
        
		}
		
	public function insertpub($id,$name,$duration,$description,$guest,$path){
		/*$id=(int)$id;
        $duration=(int)$duration;
        $guest=(int)$guest;*/
 		
        $stmt = $this->conn->prepare("INSERT INTO description(img,name,duration,description,guest,cookers_id) VALUES(?,?,?,?,?,?)");		   
	
      //  $stmt->bind_param("ssisii",$path,$name,$duration,$description,$guest,$id);
        $stmt->bind_param("ssssss",$path,$name,$duration,$description,$guest,$id);
	    $result=$stmt->execute();
			 
		 	 if ($result) {
				 $stmt->close();
            return $result;
        } else {
            return false;
        }
		}
		
		
	public function insertimage($chemin,$id){
 		
        $stmt = $this->conn->prepare("INSERT INTO 
		image(image1,datetime) VALUES (?,?)");	   
	
	$stmt->bind_param("ss",$chemin,$id);
	 $result = $stmt->execute();
 		
        // check for successful store
        if ($result) {
			$stmt = $this->conn->prepare("SELECT * FROM 
		image where image1=?");
		$stmt->bind_param("s",$chemin);
		$stmt->execute();
            $user = $stmt->get_result()->fetch_assoc();
			        $stmt->close();
				return $user;
			
		}else{
			return FALSE;
		}}
		
		
		public function getteridimg($chemin){
           $stmt = $this->conn->prepare("SELECT * FROM 
		image WHERE datetime =?");
 
        $stmt->bind_param("s", $chemin);
  if($stmt->execute()){
            $user = $stmt->get_result()->fetch_assoc();
            $stmt->close();
 
            return $user;
        } else {
            return false;
        }
    }
	
	

 public function updatenumber($number,$numero) {
 
        $stmt = $this->conn->prepare("UPDATE `localisation`.`numbers` SET `number` = ?,`numero` = ?");
 
        $stmt->bind_param("ss", $number,$numero);
 		$result = $stmt->execute();
        if ($result) {
           // $user = $stmt->get_result()->fetch_assoc();
            $stmt->close();
            return $result;
        } else {
            return NULL;
        }
    }
	 public function updatenum($anc,$nvo) {
 
        $stmt = $this->conn->prepare("UPDATE inscription SET 
		num_tel=? where num_tel=?");
 
        $stmt->bind_param("ss", $nvo,$anc);
 
        if ($user=$stmt->execute()) {
            
            $stmt->close();
            return $user;
        } else {
            return NULL;
        }
    }
	
	 public function updatefav($cook,$id,$en) {

         $stmt = $this->conn->prepare("UPDATE favorites SET enable=? WHERE receipts_id = ? AND cookers_id = ?");
         $stmt->bind_param("iii", $en, $id, $cook);
         $user1=  $stmt->execute();
         //$user1 = $stmt->get_result()->fetch_assoc();
         if ($user1) {
             $stmt->close();
             return true;

         } else {
             return false;
         }
    }
	
	 public function updatenom($anc,$nvo) {
 		
        $stmt = $this->conn->prepare("UPDATE inscription SET 
		name=? where name=?");
 
        $stmt->bind_param("ss", $nvo,$anc);
 
        if ($user=$stmt->execute()) {
            
            $stmt->close();
            return $user;
        } else {
            return NULL;
        }
    }
	public function verif($id,$id2) {
 
        $stmt = $this->conn->prepare("SELECT * FROM 
		amis WHERE id_pers1 = ? and id_pers2=?");
 
        $stmt->bind_param("ss", $id,$id2);
 
        if ($stmt->execute()) {
            $user = $stmt->get_result()->num_rows;
            $stmt->close();
            return $user;
        } else {
            return NULL;
        }
    }
	
	public function storeinvit($pseudo,$mail,$num,
		 $num_sender,$message,$img){
	 $stmt = $this->conn->prepare("INSERT INTO  
		amitie(image,pseudo, email,numero,num_sender,msg_inv,received_at) VALUES(
		?,?,?,?,?,?, NOW())");
        $stmt->bind_param("ssssss", $img,$pseudo,$mail,$num,
		 $num_sender,$message );
        $resultat = $stmt->execute();
        $stmt->close();
		return $resultat;
	}
 
    /**
     * Get user by name and password
     */
    public function getUserByNameAndPassword($name, $password) {
 
        $stmt = $this->conn->prepare("SELECT * FROM cookers WHERE name = ?");   
 
        $stmt->bind_param("s", $name);
 
        if ($stmt->execute()) {
            $user = $stmt->get_result()->fetch_assoc();
            $stmt->close();
            return $user;
        } else {
            return NULL;
        }
    }
	 public function getid($name) {
 
        $stmt = $this->conn->prepare("SELECT * FROM 
		cookers where name = ?");
 
        $stmt->bind_param("s", $name);
 
        if ($stmt->execute()) {
            $user = $stmt->get_result()->fetch_assoc();
            $stmt->close();
            return $user;
        } else {
            return NULL;
        }
    }

 public function getname($id1) {
 
        $stmt = $this->conn->prepare("SELECT name FROM personne WHERE id = ?");
 	  $stmt->bind_param("s", $id1);
 
        if ($stmt->execute()) {
            $user = $stmt->get_result()->fetch_assoc();
            $stmt->close();
            return $user;
        } else {
            return NULL;
        }
    }

	  public function getnumber() {
 
        $stmt = $this->conn->prepare("SELECT * FROM numbers WHERE id_numbers = 1");
 
       // $stmt->bind_param("ss", $number,$numero);
 
        if ($stmt->execute()) {
            $user = $stmt->get_result()->fetch_assoc();
            $stmt->close();
            return $user;
        } else {
            return NULL;
        }
    }
	 public function update($id,$id2) {
 
        $stmt = $this->conn->prepare("UPDATE amitie SET 
		id_pers2=?,created_at=NOW() where id_pers1=?");
 
        $stmt->bind_param("ss", $id,$id2);
 
        if ($user=$stmt->execute()) {
            
            $stmt->close();
            return $user;
        } else {
            return NULL;
        }
    }
	 public function get2id($num,$num2) {
 
        $stmt = $this->conn->prepare("SELECT * FROM 
		amitie WHERE numero=? and num_sender=?");
 
        $stmt->bind_param("ss", $num,$num2);
 
        if ($stmt->execute()) {
            $user = $stmt->get_result()->fetch_assoc();
            $stmt->close();
            return $user;
        } else {
            return NULL;
        }
    }
	
	    /**
     * Store user and returning user details
     */
	 public function storegcmUser($gcm_regid) {
        // insert user into database
        $result = mysql_query("INSERT INTO reg_users(gcm_regid, created_at) VALUES('$gcm_regid', NOW())");
        // check for successful store
        if ($result) {
            // get user details
            $id = mysql_insert_id(); // last inserted id
            $result = mysql_query("SELECT * FROM 
			inscription WHERE id = $id") or die(mysql_error());
            // return user details
            if (mysql_num_rows($result) > 0) {
                return mysql_fetch_array($result);
            } else {
                return false;
            }
        } else {
            return false;
        }
    }
 //list users register
  public function getAllgcmUsers() {
	  
        $stmt = $this->conn->prepare("select * FROM reg_users");
 
        $stmt->execute();
 
        return $stmt;
    }
    /**
     * Check user is existed or not
     */
    public function isUserExisted($name) {
        $stmt = $this->conn->prepare("SELECT * from	cookers WHERE name = ?");
 
        $stmt->bind_param("s",$name);
 
        $stmt->execute();
 
        $stmt->store_result();
 
        if ($stmt->num_rows > 0) {
            // user existed 
            $stmt->close();
            return true;
        } else {
            // user not existed
            $stmt->close();
            return false;
        }
    }
	
		
		public function selectiondonnée($num){
	    $stmt = $this->conn->prepare("Select *, SUM(num_sender) From amitie Group By id_amitie Having SUM(num_sender)=?");
		$stmt->bind_param("s", $num);
		 $stmt->execute();
		//$rows = $stmt->num_rows;
		$user = $stmt->get_result()->fetch_all();
	   // $rows = $stmt->num_rows;
			return $user;
			//return $rows;
		 $stmt->close();

		}
		public function selectdonnee($id){
	    $stmt = $this->conn->prepare("SELECT description.img,description.name,description.duration,
		description.description,description.guest,description.id FROM description,cookers WHERE cookers.id=? and cookers.id=description.cookers_id ");
		$stmt->bind_param("s", $id);
		$stmt->execute();		
		$user = $stmt->get_result()->fetch_all();
	  
			return $user;   
			//return $rows;
		 $stmt->close();

		}
		
		public function get_ingr($receipt_id){
	    $stmt = $this->conn->prepare("SELECT ingredients.id,ingredients.name,ingredients.quantity FROM ingredients,description WHERE ingredients.receipts_id=? and description.id=ingredients.receipts_id");
		$stmt->bind_param("i", $receipt_id);
		$stmt->execute();		
		$user = $stmt->get_result()->fetch_all();
	  
			return $user;   
			//return $rows;
		 $stmt->close();

		}
		
		
    public function selectfavoris($id){
        $stmt = $this->conn->prepare("SELECT description.img,description.name,description.duration, description.description,description.guest,description.id FROM description,favorites WHERE favorites.receipts_id=description.id AND favorites.enable = 1 AND favorites.cookers_id = ?");
        $stmt->bind_param("i", $id);
        $stmt->execute();

        $user = $stmt->get_result()->fetch_all();

        return $user;
        //return $rows;
        $stmt->close();

    }
		
		public function selectactu($pp){
	    $stmt = $this->conn->prepare("Select  publication.id,publication.id_pub,publication.pseudo,publication.type,publication.contenu,publication.id_img,publication.lat,publication.longitude,publication.date_pub,publication.mention_jaime,publication.popularite FROM amis,publication where 	                                      amis.id_pers1=? and 		                                      amis.id_pers2=publication.id_pub and publication.cible=1 " );
		
		$stmt->bind_param("s", $pp);
		 $stmt->execute();
		
		$user = $stmt->get_result()->fetch_all();
			return $user;			
			//return $rows;
		 $stmt->close();

		}
		public function selectactutag($lat,$long){
	    $stmt = $this->conn->prepare("Select publication.id, publication.id_pub,publication.pseudo,publication.type,publication.contenu,publication.id_img,publication.lat,publication.longitude,publication.date_pub,publication.mention_jaime,publication.popularite FROM publication where 	publication.lat=? and publication.longitude=?  ");
		
		$stmt->bind_param("ss", $lat,$long);
		 $stmt->execute();
		
		$user = $stmt->get_result()->fetch_all();
			return $user;			
			//return $rows;
		 $stmt->close();

		}
			public function ligneactutag($lat,$long){
	    $stmt = $this->conn->prepare("Select publication.id,publication.id_pub,publication.pseudo,publication.type,publication.contenu,publication.id_img,publication.lat,publication.longitude,publication.date_pub,publication.mention_jaime,publication.popularite FROM publication where 	publication.lat=? and publication.longitude=?  ");
		
		$stmt->bind_param("ss", $lat,$long);
		 $stmt->execute();
		
		$user = $stmt->get_result()->num_rows;
			return $user;			
			//return $rows;
		 $stmt->close();

		}
		public function ligneactu($id){
			 $stmt = $this->conn->prepare("Select publication.id,publication.id_pub,publication.pseudo,publication.type,publication.contenu,publication.id_img,publication.lat,publication.longitude,publication.date_pub,publication.mention_jaime,publication.popularite FROM amis,publication where 	                                      amis.id_pers1=? and 		                                      amis.id_pers2=publication.id_pub and publication.cible=1");
		
		$stmt->bind_param("s", $id);
		 $stmt->execute();
		   
			$user = $stmt->get_result()->num_rows;
			return $user;
		   
		 $stmt->close();
		 }
		public function selectactupp(){
	    $stmt = $this->conn->prepare("Select publication.id,publication.id_pub,publication.pseudo,publication.type,publication.contenu,publication.id_img,publication.lat,publication.longitude,publication.date_pub,publication.mention_jaime,publication.popularite FROM publication where 	                                      cible=0");
		
		 $stmt->execute();
		
		$user = $stmt->get_result()->fetch_all();
			return $user;			
			//return $rows;
		 $stmt->close();

		}
			public function sel(){
	    $stmt = $this->conn->prepare("SELECT description.img,description.name,description.duration,
		description.description,description.guest,description.id FROM description");
		
		 $stmt->execute();
		
		$user = $stmt->get_result()->fetch_all();
			return $user;			
			//return $rows;
		 $stmt->close();

		}
		public function lin(){
			$stmt = $this->conn->prepare("Select * FROM inscription");
		
		 $stmt->execute();
		   
			$user = $stmt->get_result()->num_rows;
			return $user;
		   
		 $stmt->close();
		 }
		 
		public function lignepp(){
			$stmt = $this->conn->prepare("Select * FROM publication where 	                                      cible=0");
		
		 $stmt->execute();
		   
			$user = $stmt->get_result()->num_rows;
			return $user;
		   
		 $stmt->close();
		 }
		
		
		public function lignes($id){
			$stmt = $this->conn->prepare("Select id_pers2,name,image,num_tel FROM amis,inscription where 	                                      id_pers1=? and 		                                      amis.id_pers2=inscription.id");
		$stmt->bind_param("s", $id);
		 $stmt->execute();
		   
			$user = $stmt->get_result()->num_rows;
			return $user;
		   
		 $stmt->close();
		 }
		 
		public function ligne($num){
	    $stmt = $this->conn->prepare("Select *, SUM(num_sender) From amitie Group By id_amitie Having SUM(num_sender)=?");
		$stmt->bind_param("s", $num);
		 $stmt->execute();

	   $rows = $stmt->get_result()->num_rows;
			
			return $rows;
		 $stmt->close();
		}



		public function supcompte($num){
	    $stmt = $this->conn->prepare("DELETE FROM inscription where num_tel=?");
		
		$stmt->bind_param("s", $num);
		
		 if($stmt->execute()){
			 $stmt->close();
			 return TRUE;
		 }else{
				 $stmt->close();
				 return NULL;
		}
		}
		
		public function supnotif($id){
	    $stmt = $this->conn->prepare("DELETE FROM amitie where id_amitie=?");
		
		$stmt->bind_param("s", $id);
		
		 if($stmt->execute()){
			 $stmt->close();
			 return TRUE;
		 }else{
				 $stmt->close();
				 return NULL;
		}
		}
		
	
	 public function isinvitExisted($num_sender,$num) {
        $stmt = $this->conn->prepare("SELECT * from amitie WHERE num_sender = ? and numero=?");
 
        $stmt->bind_param("ss", $num_sender,$num);
 
        $stmt->execute();
 
        $stmt->get_result();
 
        if ($stmt->num_rows > 0) {
            // ligne existante 
            $stmt->close();
            return true;
        } else {
            // numero not existed
            $stmt->close();
            return false;
        }
    }
 
    /**
     * Encrypting password
     * @param password
     * returns salt and encrypted password
     */
    public function hashSSHA($password) {
 
        $salt = sha1(rand());
        $salt = substr($salt, 0, 10);
        $encrypted = base64_encode(sha1($password . $salt, true) . $salt);
        $hash = array( "encrypted" => $encrypted);
        return $hash;
    }
 
    /**
     * Decrypting password
     * @param salt, password
     * returns hash string
     */
    public function checkhashSSHA($salt, $password) {
 
        $hash = base64_encode(sha1($password . $salt, true) . $salt);
 
        return $hash;
    }
 
}
 
?>
