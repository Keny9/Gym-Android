<?php
/****************************************
Fichier : gestionEvenement.php
Auteur : Guillaume Côté et Karl Boutin
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

  /**
  * Obtenir tous les rendez-vous d'un client
  * @param identifiant du client
  */
  public function getAllRendezVousOfClient($identifiant){
    $tempconn = new Connexion();
    $conn = $tempconn->getConnexion();
    $evenement = null;

    $query = "SELECT e.date, e.heure, e.duree, e.prix, em.nom, em.prenom, pe.nom as poste FROM evenement AS e
              INNER JOIN employe as em ON e.identifiant_employe = em.identifiant
              INNER JOIN poste_employe as pe ON em.id_poste = pe.id
              INNER JOIN ta_client_evenement as ce ON e.id = ce.id_evenement
              WHERE ce.id_client = '".$identifiant."' AND e.id_type = 2;";

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

  /**
  * Methode pour enregistrer un rendez-vous dans la base de donnee centrale
  */
  public function registerRendezVous($id,$idModele,$idType,$identifiantEmploye,$date,$heure,$duree,$prix){
    $tempconn = new Connexion();
    $conn = $tempconn->getConnexion();

    $query = "INSERT INTO evenement VALUES ('".$id."', null,'".$idType."', null, '".$identifiantEmploye."','".$date."','".$heure."','".$duree."','".$prix."');";
    $result = $conn->query($query);

    if(!$result){
      trigger_error($conn->error);
    }
  }

  /**
  * Ajouter le lien entre un rendez-vous et un client
  */
  public function registerRendezVousClient($idRendezVous,$idClient){
    $tempconn = new Connexion();
    $conn = $tempconn->getConnexion();

    $query = "INSERT INTO ta_client_evenement(id_client, id_evenement) VALUES ('".$idClient."', '".$idRendezVous."');";
    $result = $conn->query($query);

    if(!$result){
      trigger_error($conn->error);
    }
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
          Retourne un tableau contenant tous les cours contenus dans la BD (evenement)
        */
        public function getAllCoursClient($id_evenement, $id_client){
            $tempconn = new Connexion();
            $conn = $tempconn->getConnexion();
            $evenement = null;

            $requete = "SELECT * from ta_client_evenement WHERE id_evenement = '".$id_evenement."' AND id_client = '".$id_client."'";


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

    $estInscrit = $this->verifierInscription($id_evenement, $id_client);

    if($estInscrit == "faux"){
      $query = "INSERT INTO ta_client_evenement(id_client, id_evenement) VALUES ('".$id_client."', '".$id_evenement."');";
      $result = $conn->query($query);

      if(!$result){
        trigger_error($conn->error);
      }
      return false;
    }else{
      $query = "DELETE FROM ta_client_evenement WHERE id_evenement = '".$id_evenement."' AND id_client = '".$id_client."'";
      $result = $conn->query($query);

      if(!$result){
        trigger_error($conn->error);
      }
      return true;
    }

  }


  public function verifierInscription($id_evenement, $id_client){
    $tempconn = new Connexion();
    $conn = $tempconn->getConnexion();

    $query = "SELECT * from ta_client_evenement WHERE id_evenement = '".$id_evenement."' AND id_client = '".$id_client."'";
    $result = $conn->query($query);

    if(!$result){
      trigger_error($conn->error);
    }

    if(mysqli_num_rows($result)==0){
      return "faux";
    }else{
      return "vrai";
    }
  }

}

?>
