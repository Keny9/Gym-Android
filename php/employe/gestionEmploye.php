<?php
/****************************************
Fichier : gestionEmploye.php
Auteur : Maxime Lussier
Fonctionnalité : Gestionnaire de communication entre l'affichage et la base de donnée
                 en ce qui concerne les employés
Date : 2019-04-15
Vérification :
Date Nom Approuvé
2019-05-03            Guillaume                  Approuvé
2019-05-03            Karl                       Approuvé
2019-05-03            William                    Approuvé
=========================================================
Historique de modifications :
Date Nom Description
=========================================================
****************************************/
include_once '../outils/connexion.php';
include_once 'employe.php';
include_once 'poste.php';
include_once 'etat.php';
class GestionEmploye{
  /*
    Retourne l'employé dans la BD avec l'identifiant passé en paramètre
  */
  public function getEmploye($identifiant){
    $tempconn = new Connexion();
    $conn = $tempconn->getConnexion();
    $employe = null;

    $requete= "SELECT * FROM employe WHERE identifiant = '".$identifiant."';";
    $result = $conn->query($requete);
    if(!$result){
      trigger_error($conn->error);
    }

    if($result->num_rows > 0){
      $row = $result->fetch_assoc();
      $employe = new Employe( $row['identifiant'],
                                $row['nom'],
                                $row['prenom'],
                                $row['courriel'],
                                $row['date_naissance'],
                                $row['date_embauche'],
                                $row['telephone'],
                                $row['nas'],
                                $row['mot_passe'],
                                $row['ville'],
                                $row['nom_rue'],
                                $row['no_porte'],
                                $row['id_poste'],
                                $row['id_etat']);


  }
  return $employe;
}

/*
  Retourne un tableau contenant tous les employés contenus dans la BD
  Prend des critères de recherche en paramètres.
  Le paramètre doit être 'null' s'il ne contient pas de critère de recherche
*/
  public function getAllEmploye( $column, $critere){
    $tempconn = new Connexion();
    $conn = $tempconn->getConnexion();
    $employe = null;

    $requete= "SELECT * FROM employe";

    if($critere != ""){
      $requete .= " WHERE ".$column." LIKE '%{$critere}%';";
    }

    $result = $conn->query($requete);
    if(!$result){
      trigger_error($conn->error);
    }

    if ($result->num_rows > 0) {
      while($row = $result->fetch_assoc()) {
        $employe[] = new Employe( $row['identifiant'],
                                  $row['nom'],
                                  $row['prenom'],
                                  $row['courriel'],
                                  $row['date_naissance'],
                                  $row['date_embauche'],
                                  $row['telephone'],
                                  $row['nas'],
                                  $row['mot_passe'],
                                  $row['ville'],
                                  $row['nom_rue'],
                                  $row['no_porte'],
                                  $row['id_poste'],
                                  $row['id_etat']);
      }
    }

    return $employe;
  }

/*
Ajoute un employe à la BD ainsi que son adresse
*/
  public function ajouterEmploye($employe){
    if (!is_a($employe, 'Employe')){
      echo "L'objet en paramètre doit être un employé";
      return;
    }
    else{
      $tempconn = new Connexion();
      $conn = $tempconn->getConnexion();

      //Crée l'employé
      $requete= "INSERT INTO employe VALUES(
                  '".$employe->getIdentifiant()."',
                  '".$employe->getNom()."',
                  '".$employe->getPrenom()."',
                  '".$employe->getCourriel()."',
                  '".$employe->getDateNaissance()."',
                  '".$employe->getDateEmbauche()."',
                  '".$employe->getTelephone()."',
                  '".$employe->getNas()."',
                  '".$employe->getMotDePasse()."',
                  '".$employe->getVille()."',
                  '".$employe->getRue()."',
                  '".$employe->getNo()."',
                  '".$employe->getPoste()."',
                  '".$employe->getEtat()."');";
      $result = $conn->query($requete);
      if(!$result){
        trigger_error($conn->error);
      }
    }
  }

  /*
      Modifie un employe dans la BD
      Le paramètre oldId contient l'identifiant de l'employe avant la modification,
      puisque l'identifiant peut être modifié et qu'il est la clé primaire
  */
  public function modifierEmploye($employe, $oldId){
    if (!is_a($employe, 'Employe')){
      echo "L'objet en paramètre doit être un employé";
      return;
    }
    else{
      $tempconn = new Connexion();
      $conn = $tempconn->getConnexion();

      $requete= "UPDATE employe
                SET identifiant = '".$employe->getIdentifiant()."',
                nom = '".$employe->getNom()."',
                prenom = '".$employe->getPrenom()."',
                courriel = '".$employe->getCourriel()."',
                date_naissance = '".$employe->getDateNaissance()."',
                date_embauche = '".$employe->getDateEmbauche()."',
                telephone = '".$employe->getTelephone()."',
                nas = '".$employe->getNas()."',
                mot_passe = '".$employe->getMotDePasse()."',
                ville = '".$employe->getVille()."',
                nom_rue = '".$employe->getRue()."',
                no_porte = '".$employe->getNo()."',
                id_poste = '".$employe->getPoste()."',
                id_etat = '".$employe->getEtat()."'
                WHERE identifiant = '$oldId';";
      $result = $conn->query($requete);
      if(!$result){
        trigger_error($conn->error);
      }
    }
  }

/*
  Supprime un employe dans la BD en prenant son identifiant en paramètre
*/
  public function supprimerEmploye($idEmploye){
    $tempconn = new Connexion();
    $conn = $tempconn->getConnexion();

    $requete= "DELETE FROM employe
              WHERE identifiant = '$idEmploye';";
    $result = $conn->query($requete);
    if(!$result){
      trigger_error($conn->error);
    }
  }

/*
  Retourne un tableau de tous les postes d'employé
*/
  public function getAllPoste(){
    $tempconn = new Connexion();
    $conn = $tempconn->getConnexion();
    $poste = null;

    $requete= "SELECT * FROM poste_employe;";
    $result = $conn->query($requete);
    if(!$result){
      trigger_error($conn->error);
    }
    else{
      if ($result->num_rows > 0) {
        while($row = $result->fetch_assoc()) {
          $poste[] = new Poste($row['id'], $row['nom'], $row['description']);
        }
      }
    }
    return $poste;
  }

  /*
    Retourne un tableau de tous les états d'employé
  */
    public function getAllEtat(){
      $tempconn = new Connexion();
      $conn = $tempconn->getConnexion();
      $etat = null;

      $requete= "SELECT * FROM etat_employe;";
      $result = $conn->query($requete);
      if(!$result){
        trigger_error($conn->error);
      }
      else{
        if ($result->num_rows > 0) {
          while($row = $result->fetch_assoc()) {
            $etat[] = new Etat($row['id'], $row['etat'], $row['description']);
          }
        }
      }
      return $etat;
    }

    /**
   * Lors de l'authentification de l'employe, on le recherche dans la base de donnee
   * @param password
   * @param identifiant
   * a l'aide de son identifiant et de son mot de passe
   */
   public function getEmployeLogin($identifiant, $password){
     $tempconn = new Connexion();
     $conn = $tempconn->getConnexion();

    $requete= "SELECT * FROM employe WHERE identifiant = '".$identifiant."' AND mot_passe = '".$password."';";
    $result = $conn->query($requete);

     if(!$result){
       trigger_error($conn->error);
     }

     if(mysqli_num_rows($result)==0){
       $employe = null;
     }
     else{
       $row = $result->fetch_assoc();
       $employe = new Employe($row['identifiant'],
                                 $row['nom'],
                                 $row['prenom'],
                                 $row['courriel'],
                                 $row['date_naissance'],
                                 $row['date_embauche'],
                                 $row['telephone'],
                                 $row['nas'],
                                 $row['mot_passe'],
                                 $row['ville'],
                                 $row['nom_rue'],
                                 $row['no_porte'],
                                 $row['id_poste'],
                                 $row['id_etat']);
     }

     return $employe;
   }
}
?>
