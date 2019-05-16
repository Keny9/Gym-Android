package com.example.projetgym.object;

import android.os.Parcel;
import android.os.Parcelable;

public class Forfait implements Parcelable {
    private int id;
    private String nom;
    private double prix;
    private String description;
    private int duree;
    private int heure_debut_semaine;
    private int heure_fin_semaine;
    private int heure_debug_fds;
    private int heure_fin_fds;
    private int etat;

    public Forfait(int id, String nom, double prix, String description, int duree, int heure_debut_semaine, int heure_fin_semaine, int heure_debug_fds, int heure_fin_fds, int etat) {
        this.id = id;
        this.nom = nom;
        this.prix = prix;
        this.description = description;
        this.duree = duree;
        this.heure_debut_semaine = heure_debut_semaine;
        this.heure_fin_semaine = heure_fin_semaine;
        this.heure_debug_fds = heure_debug_fds;
        this.heure_fin_fds = heure_fin_fds;
        this.etat = etat;
    }

    protected Forfait(Parcel in) {
        id = in.readInt();
        nom = in.readString();
        prix = in.readDouble();
        description = in.readString();
        duree = in.readInt();
        heure_debut_semaine = in.readInt();
        heure_fin_semaine = in.readInt();
        heure_debug_fds = in.readInt();
        heure_fin_fds = in.readInt();
        etat = in.readInt();
    }

    public static final Creator<Forfait> CREATOR = new Creator<Forfait>() {
        @Override
        public Forfait createFromParcel(Parcel in) {
            return new Forfait(in);
        }

        @Override
        public Forfait[] newArray(int size) {
            return new Forfait[size];
        }
    };

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public double getPrix() {
        return prix;
    }

    public void setPrix(double prix) {
        this.prix = prix;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getDuree() {
        return duree;
    }

    public void setDuree(int duree) {
        this.duree = duree;
    }

    public int getHeure_debut_semaine() {
        return heure_debut_semaine;
    }

    public void setHeure_debut_semaine(int heure_debut_semaine) {
        this.heure_debut_semaine = heure_debut_semaine;
    }

    public int getHeure_fin_semaine() {
        return heure_fin_semaine;
    }

    public void setHeure_fin_semaine(int heure_fin_semaine) {
        this.heure_fin_semaine = heure_fin_semaine;
    }

    public int getHeure_debug_fds() {
        return heure_debug_fds;
    }

    public void setHeure_debug_fds(int heure_debug_fds) {
        this.heure_debug_fds = heure_debug_fds;
    }

    public int getHeure_fin_fds() {
        return heure_fin_fds;
    }

    public void setHeure_fin_fds(int heure_fin_fds) {
        this.heure_fin_fds = heure_fin_fds;
    }

    public int getEtat() {
        return etat;
    }

    public void setEtat(int etat) {
        this.etat = etat;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeString(nom);
        dest.writeDouble(prix);
        dest.writeString(description);
        dest.writeInt(duree);
        dest.writeInt(heure_debut_semaine);
        dest.writeInt(heure_fin_semaine);
        dest.writeInt(heure_debug_fds);
        dest.writeInt(heure_fin_fds);
        dest.writeInt(etat);
    }
}
