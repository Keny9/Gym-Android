package com.example.projetgym;

import android.os.Parcel;
import android.os.Parcelable;

//Parcelable permet de facilement transférer l'objet d'une activitée à l'autre
class Evenement implements Parcelable {
    private int id;
    private String modele;
    private String type;
    private String jour;
    private String identifiant_employe;
    private String date;
    private int heure;
    private int duree;
    private double prix;

    public Evenement(int id, String modele, String type, String jour, String identifiant_employe, String date, int heure, int duree, double prix){
        setId(id);
        setModele(modele);
        setType(type);
        setJour(jour);
        setIdentifiant_employe(identifiant_employe);
        setDate(date);
        setDuree(duree);
        setPrix(prix);
    }


    protected Evenement(Parcel in) {
        id = in.readInt();
        modele = in.readString();
        type = in.readString();
        jour = in.readString();
        identifiant_employe = in.readString();
        date = in.readString();
        heure = in.readInt();
        duree = in.readInt();
        prix = in.readDouble();
    }

    public static final Creator<Evenement> CREATOR = new Creator<Evenement>() {
        @Override
        public Evenement createFromParcel(Parcel in) {
            return new Evenement(in);
        }

        @Override
        public Evenement[] newArray(int size) {
            return new Evenement[size];
        }
    };

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
        dest.writeInt(id);
        dest.writeString(modele);
        dest.writeString(type);
        dest.writeString(jour);
        dest.writeString(identifiant_employe);
        dest.writeString(date);
        dest.writeInt(heure);
        dest.writeInt(duree);
        dest.writeDouble(prix);
    }
}
