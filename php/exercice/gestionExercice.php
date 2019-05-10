<?php
/****************************************
Fichier : rendezVousClient.php
Auteur : Karl Boutin
Fonctionnalité : Classe de gestion des rendez-vous
Date : 2019-05-08

Vérification :
Date                  Nom                       Approuvé
=========================================================

Historique de modifications :
Date                  Nom                     Description
=========================================================

****************************************/
  require_once '../outils/connexion.php';

  class GestionExercice{
    /**
    * Obtenir tous les rendez-vous d'un client
    * @param identifiant du client
    */
    public function getAllExerciceOfClient($identifiant){
      $tempconn = new Connexion();
      $conn = $tempconn->getConnexion();

      $query = "SELECT e.date, e.heure, e.duree, e.prix, em.nom, em.prenom, pe.nom as poste FROM evenement AS e
                INNER JOIN employe as em ON e.identifiant_employe = em.identifiant
                INNER JOIN poste_employe as pe ON em.id_poste = pe.id
                INNER JOIN ta_client_evenement as ce ON e.id = ce.id_evenement
                WHERE ce.id_client = '".$identifiant."';";

      $result = $conn->query($query);

      if(!$result){
        trigger_error($conn->error);
      }

      if(mysqli_num_rows($result)==0){
        $evenement = null;
      }
      else{
        $evenement = $result;
      }

      return $evenement;
    }
  }

?>
