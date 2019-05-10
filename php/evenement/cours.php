<?php
  require_once 'gestionEvenement.php';

  $gestionClient = new GestionEvenement();

  //JSON response array
   $response = array();

    $result = $gestionClient->getAllCours();

    if($result != null){
      $response["cours"] = array();

      while($row = $result->fetch_assoc()){
        $cours = array();
        $cours["id"] = $row["id"];
        $cours["modele"] = $row["nom"];
        $cours["description"] = $row["description"];
        $cours["jour"] = $row["nom_jour"];
        $cours["identifiant_employe"] = $row["identifiant_employe"];
        $cours["date"] = $row["date"];
        $cours["heure"] = $row["heure"];
        $cours["duree"] = $row["duree"];
        $cours["prix"] = $row["prix"];

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
?>
