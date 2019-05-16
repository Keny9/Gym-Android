<?php
  require_once 'gestionForfait.php';

  $gestionCours = new GestionForfait();

  //JSON response array
   $response = array();
   $response["success"] = 3;
   $response["error"] = false;

   if(isset($_POST['id_forfait'],$_POST['id_client'])){
     $id_forfait = $_POST['id_forfait'];
     $id_client = $_POST['id_client'];

     $result = $gestionCours->getAllForfaitClient($id_forfait, $id_client);

     if($result != null){
       $response["forfait"] = array();

       while($row = $result->fetch_assoc()){
         $cours = array();
         $cours["id_forfait"] = $row["id_forfait"];
         $cours["id_client"] = $row["id_client"];

         array_push($response["forfait"], $cours);
       }
       //reussi
       $response["success"] = 1;

       echo json_encode($response);
     } else {

       //aucun cours trouver
       $response["success"] = 0;
       $response["message"] = "Aucun forfait trouvé.";

       echo json_encode($response);
     }

   } else {
     $response["error"] = TRUE;
     $response["error_msg"] = "Des paramètres sont manquants";
     echo json_encode($response);
   }

?>
