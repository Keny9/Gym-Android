<?php
/****************************************
 Fichier : exercice.php
 Auteur : William Gonin
 Fonctionnalité : W-07 Classe requise pour faire Gestion des Exercices
 Date : Lundi 15 Avril 2019
 Vérification :
 Date               Nom                           Approuvé
 2019-04-24       Guillaume Côté                  Approuvé
 2019-04-26       Karl                            Approuvé
 =========================================================
 Historique de modifications :
 Date               Nom                         Description
 =========================================================
****************************************/
class exercice
{
  private $id_exercice;
  private $id_type;
  private $nom;
  private $description;
  private $image;

/*
Le constructeur de la classe exercice, l'image est le string du chemin ou se trouve l'image
*/

  function __construct( $id_type_e, $nom_e, $description_e, $image_e)
  {
    $this->setId_type($id_type_e);
    $this->setNom($nom_e);
    $this->setDescription($description_e);
    $this->setImage($image_e);

  }

  public function getId_exercice()
  {
    return $this->id_exercice;
  }

  public function setId_exercice($id_exercice_e)
  {
    $this->id_exercice = $id_exercice_e;
  }
  public function getId_type()
  {
    return $this->id_type;
  }

  public function setId_type($id_type_e)
  {
    $this->id_type = $id_type_e;
  }

  public function getNom()
  {
    return $this->nom;
  }

  public function setNom($nom_e)
  {
    $this->nom = $nom_e;
  }

  public function getDescription()
  {
  return $this->description;
  }

  public function setDescription($description_e)
  {
    $this->description = $description_e;
  }

  public function getImage()
  {
    return $this->image;
  }

  public function setImage($image_e)
  {
    $this->image = $image_e;
  }
}
?>
