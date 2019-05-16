<?php
/****************************************
Fichier : InscrireCours.php
Auteur : Guillaume Côté
Fonctionnalité : Inscrire un client à un cours
Date : 2019-05-13

Vérification :
Date                  Nom                       Approuvé
=========================================================

Historique de modifications :
Date                  Nom                     Description
=========================================================

****************************************/

require_once 'gestionForfait.php';

$gestionCours = new gestionForfait();

// JSON response array
$response = array("error" => FALSE);

if(isset($_POST['id_forfait'],$_POST['id_client'])){
  $id_forfait = $_POST['id_forfait'];
  $id_client = $_POST['id_client'];

 $gestionCours->inscrireForfait($id_forfait, $id_client);

} else {
  $response["error"] = TRUE;
  $response["error_msg"] = "Des paramètres sont manquants";
  echo json_encode($response);
}



?>
