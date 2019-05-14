<?php
  /****************************************
  Fichier : exercice.php
  Auteur : William Gonin
  Fonctionnalité : Obtenir tous les exercices
  Date : 2019-05-08

  Vérification :
  Date                  Nom                       Approuvé
  =========================================================

  Historique de modifications :
  Date                  Nom                     Description
  =========================================================

  ****************************************/
  require_once 'gestionExercice.php';


  $gestionExercice = new gestionExercices();

  //JSON response array
   $response = array();

    $result = $gestionExercice->getAllCours();

    if($result != null){
      $response["exercices"] = array();

      while($row = $result->fetch_assoc()){
        $exercices = array();
        $exercices["id"] = $row["id"];
        $exercices["type"] = $row["id_type"];
        $exercices["nom"] = $row["nom"];
        $exercices["description"] = $row["description"];
        $exercices["image"] = $row["image"];

        array_push($response["exercices"], $exercices);
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
