<?php
/****************************************
Fichier : gestionClient.php
Auteur : Guillaume Côté
Fonctionnalité : Classe qui va gérer les evenement d'un gym dans la bd
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
require_once '../outils/connexion.php';

class GestionEvenement{

  /*
    Retourne un tableau contenant tous les evenements contenus dans la BD
    Prend des critères de recherche en paramètres.
    Le paramètre doit être 'null' s'il ne contient pas de critère de recherche
  */
    public function getAllEvenement(){
      $tempconn = new Connexion();
      $conn = $tempconn->getConnexion();
      $evenement = null;

      $requete= "SELECT * FROM evenement";

      $result = $conn->query($requete);
      if(!$result){
        trigger_error($conn->error);
      }

      if ($result->num_rows > 0) {
        while($row = $result->fetch_assoc()) {
          $evenement[] = new Evenement($row['id'], $row['id_modele'], $row['id_type'], $row['id_jour'], $row['identifiant_employe'], $row['date'], $row['heure'], $row['duree'], $row['prix']);
        }
      }
      return $evenement;
    }

    /*
       Retourne un tableau contenant tous les cours contenus dans la BD (evenement)
     */
     public function getAllCours(){
         $tempconn = new Connexion();
         $conn = $tempconn->getConnexion();
         $evenement = null;

         $requete = "SELECT evenement.id, modele_cours.nom,jour_semaine.nom_jour, identifiant_employe, date, heure, duree, prix
                       FROM evenement
                          INNER JOIN modele_cours ON modele_cours.id = evenement.id_modele
                          INNER JOIN type_evenement ON type_evenement.id = evenement.id_type
                          INNER JOIN jour_semaine ON jour_semaine.id = evenement.id_jour
                       WHERE id_type = 1;";


         $result = $conn->query($requete);

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

  /*
    Inscrire un client à un cours
  */
  public function inscrireCours($id_evenement, $id_client){
    $tempconn = new Connexion();
    $conn = $tempconn->getConnexion();

    $query = "INSERT INTO ta_client_evenement(id_client, id_evenement) VALUES ('".$id_client."', '".$id_evenement."');";
    $result = $conn->query($query);

    if(!$result){
      trigger_error($conn->error);
    }

  }

  /*
    Vérifie si un client fait deja partie d'un cours
  */
  public function verifierInscription($id_evenement, $id_client){
    $tempconn = new Connexion();
    $conn = $tempconn->getConnexion();
    $evenement = null;

    $requete = "SELECT COUNT(*) FROM `ta_client_evenement` WHERE `id_evenement` = '.$id_evenement.' AND `id_client` = '.$id_client.';";
    $result = $conn->query($requete);

     if(!$result){
       trigger_error($conn->error);
     }
    return mysqli_num_rows($result);
  }

}

?>
