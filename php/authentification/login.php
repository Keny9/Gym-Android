<?php
  require_once '../client/gestionClient.php';

  $gestionClient = new GestionClient();

  //json response array
  $response = array("error" => FALSE);

  if(isset($_POST['identifiant']) && isset($_POST['password'])){

    $identifiant = $_POST['identifiant'];
    $password = $_POST['password'];

    $client = $gestionClient->getClientLogin($identifiant, $password);

    if($client != null){
      $response["error"] = FALSE;
      $response["client"]["identifiant"] = $client["identifiant"];
      $response["client"]["id_forfait"] = $client["id_forfait"];
      $response["client"]["nom"] = $client["nom"];
      $response["client"]["prenom"] = $client["prenom"];
      $response["client"]["date_naissance"] = $client["date_naissance"];
      $response["client"]["courriel"] = $client["courriel"];
      $response["client"]["telephone"] = $client["telephone"];
      $response["client"]["mot_de_passe"] = $client["mot_de_passe"];
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
