<?php
/****************************************
Fichier : gestionForfait.php
Auteur : Karl Boutin
Fonctionnalité : Classe qui va gérer les forfaits du gym
pour la fonctionnalité W-05 Gestion des types de forfait
Date : 2019-04-15

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
  require_once('../Outils/connexion.php');

  class GestionForfait{

    /**
    * Obtenir tous les forfaits disponibles auquels un client peut adérer
    */
    public function getAllForfait(){
      $tempconn = new Connexion();
      $conn = $tempconn->getConnexion();
      $forfait = null;

      $requete = "SELECT * FROM forfait;";

      $result = $conn->query($requete);

      if(!$result){
        trigger_error($conn->error);
      }

      if(mysqli_num_rows($result)==0){
        $forfait = null;
      }
      else{
        $forfait = $result;
      }
      return $forfait;
    }
  }
?>
