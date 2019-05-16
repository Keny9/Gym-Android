<?php
/****************************************
Fichier : InscrireClient.php
Auteur : Maxime Lussier
Fonctionnalité : Inscrire un client à la BD
Date : 2019-05-16

Vérification :
Date                  Nom                       Approuvé
=========================================================

Historique de modifications :
Date                  Nom                     Description
=========================================================

****************************************/

require_once './gestionClient.php';

$gestionClient = new GestionClient();

// JSON response array
$response = array("error" => FALSE);

if(isset($_POST['identifiant'],$_POST['idForfait'],
        $_POST['nom'],$_POST['prenom'],
        $_POST['dateNaissance'],$_POST['courriel'],
        $_POST['telephone'],$_POST['$motDePasse'])){


 $gestionClient->ajouterClient($_POST['identifiant'],
                               $_POST['idForfait'],
                               $_POST['nom'],
                               $_POST['prenom'],
                               $_POST['dateNaissance'],
                               $_POST['courriel'],
                               $_POST['telephone'],
                               $_POST['$motDePasse']);
} else {
  $response["error"] = TRUE;
  $response["error_msg"] = "Des paramètres sont manquants";
  echo json_encode($response);
}



?>
