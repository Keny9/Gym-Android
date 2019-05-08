package com.example.projetgym;

import java.util.Date;

class Evenement {
    private int id;
    private String modele;
    private String type;
    private String jour;
    private String identifiant_employe;
    private String date;
    private int heure;
    private int duree;
    private float prix;


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getModele() {
        return modele;
    }

    public void setModele(String modele) {
        this.modele = modele;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getJour() {
        return jour;
    }

    public void setJour(String jour) {
        this.jour = jour;
    }

    public String getIdentifiant_employe() {
        return identifiant_employe;
    }

    public void setIdentifiant_employe(String identifiant_employe) {
        this.identifiant_employe = identifiant_employe;
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

    public float getPrix() {
        return prix;
    }

    public void setPrix(float prix) {
        this.prix = prix;
    }
}
