<?php
  require_once 'gestionEvenement.php';

  $gestionCours = new GestionEvenement();

  //JSON response array
   $response = array();
   $response["success"] = 3;
   $response["error"] = false;

   if(isset($_POST['id_event'],$_POST['id_client'])){
     $id_event = $_POST['id_event'];
     $id_client = $_POST['id_client'];

     $result = $gestionCours->getAllCoursClient($id_event, $id_client);

     if($result != null){
       $response["cours"] = array();

       while($row = $result->fetch_assoc()){
         $cours = array();
         $cours["id_event"] = $row["id_evenement"];
         $cours["id_client"] = $row["id_client"];

         array_push($response["cours"], $cours);
       }
       //reussi
       $response["success"] = 1;

       echo json_encode($response);
     } else {

       //aucun cours trouver
       $response["success"] = 0;
       $response["message"] = "Aucun cours trouvé.";

       echo json_encode($response);
     }

   } else {
     $response["error"] = TRUE;
     $response["error_msg"] = "Des paramètres sont manquants";
     echo json_encode($response);
   }

?>
