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

    /*
       Tous les forfaits d'un client
     */
     public function getAllForfaitClient($id_forfait, $id_client){
         $tempconn = new Connexion();
         $conn = $tempconn->getConnexion();
         $evenement = null;

         $requete = "SELECT * from ta_forfait_client WHERE id_forfait = '".$id_forfait."' AND id_client = '".$id_client."'";


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
         Inscrire un client à un forfait ou le désinscrire
       */
       public function inscrireCours($id_forfait, $id_client){
         $tempconn = new Connexion();
         $conn = $tempconn->getConnexion();

         $estInscrit = $this->verifierInscription($id_forfait, $id_client);

         if($estInscrit == "faux"){
           $query = "INSERT INTO ta_client_evenement(id_client, id_forfait) VALUES ('".$id_client."', '".$id_forfait."');";
           $result = $conn->query($query);

           if(!$result){
             trigger_error($conn->error);
           }
           return false;
         }else{
           $query = "DELETE FROM ta_client_evenement WHERE id_forfait = '".$id_forfait."' AND id_client = '".$id_client."'";
           $result = $conn->query($query);

           if(!$result){
             trigger_error($conn->error);
           }
           return true;
         }

       }


       public function verifierInscription($id_forfait, $id_client){
         $tempconn = new Connexion();
         $conn = $tempconn->getConnexion();

         $query = "SELECT * from ta_forfait_client WHERE id_forfait = '".$id_forfait."' AND id_client = '".$id_client."'";
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
