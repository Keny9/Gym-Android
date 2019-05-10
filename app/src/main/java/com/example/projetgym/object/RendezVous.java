package com.example.projetgym.object;

/**
 * Classe rendez-vous
 */
public class RendezVous {

    private String date; //date du rendez-vous
    private int heure; //L'heure du rendez-vous
    private int duree; //La durée du rendez-vous
    private double prix; //Le prix
    private String nom; //Le nom du spécialiste qui donne le rendez-vous
    private String prenom; //Le prénom du spécialiste qui donne le rendez-vous
    private String poste; //Le poste du spécialiste qui donne le rendez-vous

    /**
     * Constructeur d'un rendez-vous
     * @param date
     * @param heure
     * @param duree
     * @param prix
     * @param nom
     * @param prenom
     * @param poste
     */
    public RendezVous(String date, int heure, int duree, double prix, String nom, String prenom, String poste){
        setDate(date);
        setHeure(heure);
        setDuree(duree);
        setPrix(prix);
        setNom(nom);
        setPrenom(prenom);
        setPoste(poste);
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public int getHeure() {
        return heure;
    }

    public void setHeure(int heure) {
        this.heure = heure;
    }

    public int getDuree() {
        return duree;
    }

    public void setDuree(int duree) {
        this.duree = duree;
    }

    public double getPrix() {
        return prix;
    }

    public void setPrix(double prix) {
        this.prix = prix;
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

    public String getPoste() {
        return poste;
    }

    public void setPoste(String poste) {
        this.poste = poste;
    }
}
