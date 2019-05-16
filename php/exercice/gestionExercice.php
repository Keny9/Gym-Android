<?php
/****************************************
 Fichier : gestionExercices.php
 Auteur : William Gonin
 Fonctionnalité : W-07 Classe requise pour faire Gestion des Exercices
 Date : Lundi 15 Avril 2019
 Vérification :
 Date                  Nom                       Approuvé
2019-04-24          Guillaume Côte              Approuvé
2019-04-26            Karl                      Approuvé
 =========================================================
 Historique de modifications :
 Date Nom Description
 =========================================================
****************************************/
require_once '../Outils/connexion.php';
require_once 'exercice1.php';

class gestionExercices
{

/*
La fonction qui sert a ajouter un exercice dans la base de donnee
*/
  function ajouterExercices( $exercice)
  {
    $tempconn = new Connexion;
    $conn = $tempconn->getConnexion();

    $requete="INSERT INTO exercice( nom, description, image id_type) VALUES (  '".$exercice->getNom()."', '".$exercice->getDescription()."', '".$exercice->getImage()."','".$exercice->getId_type()."');";

    $result = $conn->query($requete);
      if(!$result){
        trigger_error($conn->error);
      }
  }

  /*
  La fonction qui sert a retirer un exercice de la base de donnee
  */
  function supprimerExercices($id_exercice_e)
  {
    $tempconn = new Connexion;
    $conn = $tempconn->getConnexion();


    $requete="DELETE FROM exercice WHERE id = '".$id_exercice_e."'";

    $result = $conn->query($requete);
      if(!$result){
        trigger_error($conn->error);
      }
  }

  /*
  La fonction qui sert a modifier un exercice dans la base de donnee
  */
  function modifierExercices($exercice)
  {
    $tempconn = new Connexion;
    $conn = $tempconn->getConnexion();

    $requete="UPDATE exercice
              SET
              nom = '".$exercice->getNom()."',
              description = '".$exercice->getDescription()."',
              image = '".$exercice->getImage()."',
              id_type = '".$exercice->getId_type()."'
              WHERE id ='".$exercice->getId_exercice()."'";


    $result = $conn->query($requete);
      if(!$result){
        trigger_error($conn->error);
      }
  }

  /*
  La fonction qui sert a lire tous les exercices dans la base de donnee
  pour qu'on puissent les mettre dans un tableau
  */
  function getAllExercices(){
    $tempconn = new Connexion();
    $conn = $tempconn->getConnexion();
    $exercices;

    $requete= "SELECT * FROM exercice;";
    $result = $conn->query($requete);

    if(!$result){
      trigger_error($conn->error);
    }

    if(mysqli_num_rows($result)==0){
      $exercices = null;
    }
    else{
      $exercices = $result;
    }

  /*  if ($result->num_rows > 0) {

      while($row = $result->fetch_assoc()) {
          $exercices[] = new Exercice($row['id_type'], $row['nom'], $row['description'], $row['image']);
        }
    }*/


    return $exercices;
  }

  /*
  La fonction qui sert a lire les exercices du haut du corps dans la base de donnee
  pour qu'on puissent les mettre dans un tableau
  */
  function getHautExercices(){
    $tempconn = new Connexion();
    $conn = $tempconn->getConnexion();
    $exercices;

    $requete= "SELECT * FROM exercice WHERE id_type = '1';";
    $result = $conn->query($requete);

    if(!$result){
      trigger_error($conn->error);
    }

    if ($result->num_rows > 0) {

      while($row = $result->fetch_assoc()) {
          $exercices[] = new Exercice($row['id_type'], $row['nom'], $row['description'], $row['image']);
        }
    }


    return $exercices;
  }

  /*
  La fonction qui sert a lire les exercices du bas du corps dans la base de donnee
  pour qu'on puissent les mettre dans un tableau
  */
  function getBasExercices(){
    $tempconn = new Connexion();
    $conn = $tempconn->getConnexion();
    $exercices;

    $requete= "SELECT * FROM exercice WHERE id_type = '2';";
    $result = $conn->query($requete);

    if(!$result){
      trigger_error($conn->error);
    }

    if ($result->num_rows > 0) {

      while($row = $result->fetch_assoc()) {
          $exercices[] = new Exercice($row['id_type'], $row['nom'], $row['description'], $row['image']);
        }
    }


    return $exercices;
  }

  /**
  * Methode qui permet de recuperer un exercice seulement à partir son id
  * Retourne un exercice
  * @param id
  */
  public function getExercice($id){
    $tempconn = new Connexion();
    $conn = $tempconn->getConnexion();
    $exercice = null;


      $requete= "SELECT *  FROM exercice WHERE id = '".$id."';";
      $result = $conn->query($requete);

      if(!$result){
        trigger_error($conn->error);
      }


      if($result->num_rows > 0){
        $row = $result->fetch_assoc();
        $exercice = new Exercice( $row['id_type'],$row['nom'], $row['description'], $row['image']);
      }

    return $exercice;
  }

}
?>
