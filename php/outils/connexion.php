<?php
/****************************************
Fichier : connexion.php
Auteur : Maxime Lussier
Fonctionnalité : Crée une connexion mysqli en se basant sur les informations dans
le fichier "connexion.txt" et permet de get la connexion et de la close.
Date : 2019-04-15
Vérification :
Date Nom Approuvé
=========================================================
Historique de modifications :
Date Nom Description
=========================================================
****************************************/
class Connexion{
  private $conn;
  private $dbHost = "localhost";
  private $dbUser = "root";
  private $dbPass = "";
  private $dbName = "gymlocal";

  function __construct() {
    $this->conn = mysqli_connect($this->dbHost, $this->dbUser, $this->dbPass, $this->dbName);
    mysqli_set_charset($con,"utf8");

    if ($this->conn->connect_error){
      $this->closeConnexion();
      die("Connection failed: " . $this->conn->connect_error);
    }
  }

  function __destruct() {
    $this->closeConnexion();
  }

  public function getConnexion(){
    return $this->conn;
  }

  public function closeConnexion(){
    $this->conn->close();
  }
}
?>
