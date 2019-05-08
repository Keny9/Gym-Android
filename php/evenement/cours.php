<?php
  require_once 'gestionEvenement.php';

  $gestionClient = new GestionEvenement();

  //json response array
  $response = array("error" => FALSE);

    $cours = $gestionClient->getAllCours();

    if($cours != null){
      $response["error"] = FALSE;
      $response["evenement"]["id"] = $cours["id"];
      $response["evenement"]["modele_cours.nom"] = $cours["modele_cours.nom"];
      $response["evenement"]["type_evenement.nom"] = $cours["type_evenement.nom"];
      $response["evenement"]["jour_semaine.nom_jour"] = $cours["jour_semaine.nom_jour"];
      $response["evenement"]["identifiant_employe"] = $cours["identifiant_employe"];
      $response["evenement"]["date"] = $cours["date"];
      $response["evenement"]["heure"] = $cours["heure"];
      $response["evenement"]["duree"] = $cours["duree"];
      $response["evenement"]["prix"] = $cours["prix"];
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
