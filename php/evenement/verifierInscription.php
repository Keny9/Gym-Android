<?php
/****************************************
Fichier : InscrireCours.php
Auteur : Guillaume Côté
Fonctionnalité : Vérifie si le client fait deja parti du cours
Date : 2019-05-13

Vérification :
Date                  Nom                       Approuvé
=========================================================

Historique de modifications :
Date                  Nom                     Description
=========================================================

****************************************/

require_once './gestionEvenement.php';

$gestionCours = new GestionEvenement();

// JSON response array
$response = array("error" => FALSE);
//
// if(isset($_POST['id_event'],$_POST['id_client'])){
//   $id_event = $_POST['id_event'];
//   $id_client = $_POST['id_client'];

$id_event = "C00001";
$id_client = "Marie1";

  $evenement = $gestionCours->verifierInscription($id_event, $id_client);
  echo $evenement;

// } else {
//   $response["error"] = TRUE;
//   $response["error_msg"] = "Des paramètres sont manquants";
//   echo json_encode($response);
// }



?>
