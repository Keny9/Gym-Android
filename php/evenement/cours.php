<?php
  require_once 'gestionEvenement.php';

  $gestionClient = new GestionEvenement();

  //json response array
  $response = array("error" => FALSE);

    $cours = $gestionClient->getAllCours();

    if($cours != null){
      $response["error"] = FALSE;
      $response["cours"]["id"] = $cours["id"];
      $response["cours"]["id_modele"] = $cours["id_modele"];
      $response["cours"]["id_type"] = $cours["id_type"];
      $response["cours"]["id_jour"] = $cours["id_jour"];
      $response["cours"]["identifiant_employe"] = $cours["identifiant_employe"];
      $response["cours"]["date"] = $cours["date"];
      $response["cours"]["heure"] = $cours["heure"];
      $response["cours"]["duree"] = $cours["duree"];
      $response["cours"]["prix"] = $cours["prix"];
      echo json_encode($response);
    }
    else{
      $response["error"] = TRUE;
      $response["error_msg"] = "Les informations entrées sont incorrects. Veuillez réessayer.";
      echo json_encode($response);
    }
  }
  else {
    $response["error"] = TRUE;
    $response["error_msg"] = "Veuillez remplir les champs qui sont vides.";
    echo json_encode($response);
  }

?>
