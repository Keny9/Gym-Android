package com.example.projetgym;

import android.os.Parcel;
import android.os.Parcelable;

//Parcelable permet de facilement transférer l'objet d'une activitée à l'autre
public class Cours implements Parcelable {
    private String id;
    private String modele;
    private String jour;
    private String identifiant_employe;
    private String date;
    private int heure;
    private int duree;
    private double prix;

    public Cours(String id, String modele, String jour, String identifiant_employe, String date, int heure, int duree, double prix){
        setId(id);
        setModele(modele);
        setJour(jour);
        setIdentifiant_employe(identifiant_employe);
        setDate(date);
        setHeure(heure);
        setDuree(duree);
        setPrix(prix);
    }


    protected Cours(Parcel in) {
        id = in.readString();
        modele = in.readString();
        jour = in.readString();
        identifiant_employe = in.readString();
        date = in.readString();
        heure = in.readInt();
        duree = in.readInt();
        prix = in.readDouble();
    }

    public static final Creator<Cours> CREATOR = new Creator<Cours>() {
        @Override
        public Cours createFromParcel(Parcel in) {
            return new Cours(in);
        }

        @Override
        public Cours[] newArray(int size) {
            return new Cours[size];
        }
    };

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getModele() {
        return modele;
    }

    public void setModele(String modele) {
        this.modele = modele;
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

    public double getPrix() {
        return prix;
    }

    public void setPrix(double prix) {
        this.prix = prix;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(id);
        dest.writeString(modele);
        dest.writeString(jour);
        dest.writeString(identifiant_employe);
        dest.writeString(date);
        dest.writeInt(heure);
        dest.writeInt(duree);
        dest.writeDouble(prix);
    }
}
