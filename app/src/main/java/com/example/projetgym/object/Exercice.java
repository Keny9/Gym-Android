package com.example.projetgym.object;

public class Exercice {
    //private int id_exercice;
    private int id_type;
    private String nom;
    private String description;
    private String image;

/*
Le constructeur de la classe exercice, l'image est le string du chemin ou se trouve l'image
*/

    public Exercice(int id_type,String nom,String description,String image){
        //setId_exercice(id_exercice);
        setId_type(id_type);
        setNom(nom);
        setDescription(description);
        setImage(image);

    }

   /* public int getId_exercice() {
        return id_exercice;
    }

    public void setId_exercice(int id_exercice) {
        this.id_exercice = id_exercice;
    }*/

    public int getId_type() {
        return id_type;
    }

    public void setId_type(int id_type) {
        this.id_type = id_type;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }
}