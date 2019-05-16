<?php
/****************************************
Fichier : gestionClient.php
Auteur : Karl Boutin
Fonctionnalité : Classe qui va gérer les clients d'un gym dans la bd
Date : 2019-05-08

Vérification :
Date                  Nom                       Approuvé
=========================================================
2019-05-03            William Gonin               Approuvé
2019-05-03            Guillaume Côté              Approuvé
2019-05-03            Maxime Lussier              Approuvé

Historique de modifications :
Date                  Nom                     Description
=========================================================

****************************************/
require_once '../outils/connexion.php';

class GestionClient{

  /**
  * Lors de la connexion d'un client, on cherche dans la base de donnee
  * a l'aide de l'identifiant et du mot de passe
  * Retourne un client
  * @param identifiant
  * @param password
  */
  public function getClientLogin($identifiant, $password){
    $tempconn = new Connexion();
    $conn = $tempconn->getConnexion();

    $requete= "SELECT * FROM client WHERE identifiant = '".$identifiant."' AND mot_de_passe = '".$password."';";
    $result = $conn->query($requete);

    if(!$result){
      trigger_error($conn->error);
    }

    if(mysqli_num_rows($result)==0){
      $client = null;
    }
    else{
      $row = $result->fetch_assoc();
      $client = $row;
    }

    return $client;
  }

  /*
  Ajoute un employe à la BD ainsi que son adresse
  */
  public function ajouterClient($identifiant, $idForfait, $nom, $prenom, $dateNaissance, $courriel, $telephone, $motDePasse){
    $tempconn = new Connexion();
    $conn = $tempconn->getConnexion();

    //Crée l'employé
    $requete= "INSERT INTO employe VALUES(
      '".$identifiant."',
      '".$idForfait."',
      '".$nom."',
      '".$prenom."',
      '".$dateNaissance."',
      '".$courriel."',
      '".$telephone."',
      '".$motDePasse."');";
      $result = $conn->query($requete);
      if(!$result){
        trigger_error($conn->error);

      }
    }
  }

?>
