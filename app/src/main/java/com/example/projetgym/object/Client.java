package com.example.projetgym.object;
/****************************************
 Fichier : Client.java
 Auteur : Karl Boutin
 Fonctionnalité : Reprsente un client pour la base de donnée
 Date : 2019-05-13

 Vérification :
 Date               Nom                   Approuvé
 =========================================================


 Historique de modifications :
 Date               Nom                   Description
 =========================================================

/**
 * Classe qui represente un client du gym
 */
public class Client {

    private String identifiant;
    private String nom;
    private String prenom;
    private String email;
    private int idForfait;
    private String dateNaissance;
    private String telephone;

    /**
     * Constructeur d'un client
     * @param identifiant
     * @param nom
     * @param prenom
     * @param email
     * @param idForfait
     * @param dateNaissance
     * @param telephone
     */
    public Client(String identifiant,String nom,String prenom,String email,int idForfait,String dateNaissance,String telephone){
        setIdentifiant(identifiant);
        setNom(nom);
        setPrenom(prenom);
        setEmail(email);
        setIdForfait(idForfait);
        setDateNaissance(dateNaissance);
        setTelephone(telephone);
    }

    public String getIdentifiant() {
        return identifiant;
    }

    public void setIdentifiant(String identifiant) {
        this.identifiant = identifiant;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getPrenom() {
        return prenom;
    }

    public void setPrenom(String prenom) {
        this.prenom = prenom;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public int getIdForfait() {
        return idForfait;
    }

    public void setIdForfait(int idForfait) {
        this.idForfait = idForfait;
    }

    public String getDateNaissance() {
        return dateNaissance;
    }

    public void setDateNaissance(String dateNaissance) {
        this.dateNaissance = dateNaissance;
    }

    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }
}
