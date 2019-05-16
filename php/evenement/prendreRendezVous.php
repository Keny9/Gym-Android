<?php
/****************************************
Fichier : prendreRendezVous.php
Auteur : Karl Boutin
Fonctionnalité : Ajouter un rendez-vous prév u pour le client
Date : 2019-05-13

Vérification :
Date                  Nom                       Approuvé
=========================================================

Historique de modifications :
Date                  Nom                     Description
=========================================================

****************************************/

require_once './gestionEvenement.php';

$gestionRendezVous = new GestionEvenement();

// JSON response array
$response = array("error" => FALSE);

if(isset($_POST['id'],$_POST['id_modele'],$_POST['id_type'],$_POST['identifiant_employe'],$_POST['date'],$_POST['heure'],$_POST['duree'],$_POST['prix'],$_POST['id_client'])){
  $id = $_POST['id'];
  $id_modele = $_POST['id_modele'];
  $id_type = $_POST['id_type'];
  $identifiant_employe = $_POST['identifiant_employe'];
  $date = $_POST['date'];
  $heure = $_POST['heure'];
  $duree = $_POST['duree'];
  $prix = $_POST['prix'];
  $idClient = $_POST['id_client'];

  $gestionRendezVous->registerRendezVous($id,$id_modele,$id_type,$identifiant_employe,$date,$heure,$duree,$prix);
  $gestionRendezVous->registerRendezVousClient($id, $idClient);
}
else {
  $response["error"] = TRUE;
  $response["error_msg"] = "Des paramètres sont manquants";
  echo json_encode($response);
}

?>
