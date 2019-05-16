<?php
  require_once 'gestionForfait.php';

  $gestionClient = new GestionForfait();

  //JSON response array
   $response = array();

    $result = $gestionClient->getAllForfait();

    if($result != null){
      $response["forfait"] = array();

      while($row = $result->fetch_assoc()){
        $forfait = array();
        $forfait["id"] = $row["id"];
        $forfait["modele"] = $row["nom"];
        $forfait["prix"] = $row["prix"];
        $forfait["description"] = $row["description"];
        $forfait["duree"] = $row["duree"];
        $forfait["heure_debut_semaine"] = $row["heure_debut_semaine"];
        $forfait["heure_fin_semaine"] = $row["heure_fin_semaine"];
        $forfait["heure_debut_fds"] = $row["heure_debut_fds"];
        $forfait["heure_fin_fds"] = $row["heure_fin_fds"];
        $forfait["etat"] = $row["etat"];

        array_push($response["forfait"], $forfait);
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
?>
