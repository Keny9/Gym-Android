package com.example.projetgym.app;

/****************************************
 Fichier : AppConfig.java
 Auteur : Karl Boutin
 Fonctionnalité : Pour la connexion de l'utilisateur a l'application
 Date : 2019-05-07

 Vérification :
 Date               Nom                   Approuvé
 =========================================================


 Historique de modifications :
 Date               Nom                   Description
 =========================================================

 ****************************************/


public class AppConfig {

    // Server user login url
    public static String URL_LOGIN = "http://10.0.2.2/ProjetGymAndroid/gymandroid/php/authentification/login.php";

    // Server user register url
    public static String URL_REGISTER = "http://10.170.11.115/ProjetGymAndroid/php/authentification/register.php";

    public static String URL_INSCRIRE_CLIENT = "http://10.170.11.115/ProjetGymAndroid/php/client/inscrireClient.php";

    // Server user login url
    public static String URL_COURS = "http://10.0.2.2/ProjetGymAndroid/gymandroid/php/evenement/cours.php";

    public static String URL_INSCRIRE_COURS = "http://10.0.2.2/ProjetGymAndroid/gymandroid/php/evenement/inscrireCours.php";

    public static String URL_VERIFIER_COURS = "http://10.0.2.2/ProjetGymAndroid/gymandroid/php/evenement/verifierInscription.php";
    public static String URL_RENDEZVOUS = "http://10.0.2.2/gymandroid/php/rendezvous/rendezVousClient.php";

    public static String URL_EXERCICES = "http://10.0.2.2/gymandroid/php/exercice/exercice.php";
    public static String URL_EXERCICESHAUT = "http://10.0.2.2/gymandroid/php/exercice/exerciceHaut.php";
    public static String URL_EXERCICESBAS = "http://10.0.2.2/gymandroid/php/exercice/exerciceBas.php";
    public static String URL_REGISTER_RDV = "http://10.0.2.2/ProjetGymAndroid/php/evenement/prendreRendezVous.php";
}
