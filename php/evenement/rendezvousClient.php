<?php
  /****************************************
  Fichier : rendezVousClient.php
  Auteur : Karl Boutin
  Fonctionnalité : Obtenir les rendez-vous du client lorsque le client clic sur
  ses rendez-vous
  Date : 2019-05-08

  Vérification :
  Date                  Nom                       Approuvé
  =========================================================

  Historique de modifications :
  Date                  Nom                     Description
  =========================================================

  ****************************************/
  require_once './gestionEvenement.php';

  $gestionRendezVous = new GestionEvenement();

  //JSON response array
  $response = array();

  if(isset($_POST['identifiant'])){

    $identifiant = $_POST['identifiant'];

    $result = $gestionRendezVous->getAllRendezVousOfClient($identifiant);

    if($result != null){
      $response["rendezvous"] = array();

      while($row = $result->fetch_assoc()){
        $rendezvous = array();
        $rendezvous["date"] = $row["date"];
        $rendezvous["heure"] = $row["heure"];
        $rendezvous["duree"] = $row["duree"];
        $rendezvous["prix"] = $row["prix"];
        $rendezvous["nom"] = $row["nom"];
        $rendezvous["prenom"] = $row["prenom"];
        $rendezvous["poste"] = $row["poste"];

        array_push($response["rendezvous"], $rendezvous);
      }
      //reussi
      $response["success"] = 1;

      echo json_encode($response);
    } else {
      //aucun rendezvous trouver
      $response["success"] = 0;
      $response["message"] = "Aucun rendez-vous trouvé.";

      echo json_encode($response);
    }

  } else{
    $response["success"] = 0;
    $response["message"] = "Le POST n'est pas effectué correctement";

    echo json_encode($response);
  }
?>
