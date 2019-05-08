package com.example.projetgym.helper;

/****************************************
 Fichier : SQLiteHandler.java
 Auteur : Karl Boutin
 Fonctionnalité : Classe pour SQLite
 Date : 2019-05-07

 Vérification :
 Date               Nom                   Approuvé
 =========================================================


 Historique de modifications :
 Date               Nom                   Description
 =========================================================

 ****************************************/

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.content.Context;
import android.util.Log;

import java.util.HashMap;

public class SQLiteHandler extends SQLiteOpenHelper {

    private static final String TAG = SQLiteHandler.class.getSimpleName();

    // All Static variables
    // Database Version
    private static final int DATABASE_VERSION = 1;

    // Database Name
    private static final String DATABASE_NAME = "gymlocal";

    // Client table name
    private static final String TABLE_CLIENT = "client";

    // Client Table Columns names
    private static final String KEY_ID = "identifiant";
    private static final String KEY_NAME = "nom";
    private static final String KEY_PRENOM = "prenom";
    private static final String KEY_EMAIL = "email";
    private static final String KEY_IDFORFAIT = "id_forfait";
    private static final String KEY_DATENAISSANCE = "date_naissance";
    private static final String KEY_TELEPHONE = "telephone";

    public SQLiteHandler(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    //Création des tables
    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_CLIENT_TABLE = "CREATE TABLE " + TABLE_CLIENT + "(" + KEY_ID + " TEXT PRIMARY KEY," +
                KEY_NAME + " TEXT," + KEY_PRENOM + " TEXT," + KEY_EMAIL + " TEXT UNIQUE," + KEY_DATENAISSANCE + " TEXT," +
                KEY_TELEPHONE + " TEXT," + KEY_IDFORFAIT + " INTEGER" + ")";

        db.execSQL(CREATE_CLIENT_TABLE);

        Log.d(TAG, "Tables de la base de données créés");
    }

    //Mise à jour de la base de donnée
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        //Si la vielle table existe on la drop
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_CLIENT);

        //Créer les tables à nouveau
        onCreate(db);
    }

    //Ajoute le client (utilisateur actuel) dans la base de donnée local
    public void ajouterClient(String identifiant, String nom,String prenom, String email, String idForfait, String dateNaissance, String telephone){
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_ID, identifiant);
        values.put(KEY_NAME, nom);
        values.put(KEY_PRENOM, prenom);
        values.put(KEY_EMAIL, email);
        values.put(KEY_IDFORFAIT, idForfait);
        values.put(KEY_DATENAISSANCE, dateNaissance);
        values.put(KEY_TELEPHONE, telephone);

        db.insert(TABLE_CLIENT,null,values);
        db.close();

        Log.d(TAG, "Nouveau client inséré dans sqlite: " + identifiant);
    }

    public HashMap<String, String> getClientDetails(){
        HashMap<String, String> client = new HashMap<String, String>();
        String selectQuery = "SELECT * FROM " + TABLE_CLIENT;

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        //Aller a la premiere ligne
        cursor.moveToFirst();
        if(cursor.getCount() > 0){
            client.put("identifiant", cursor.getString(0));
            client.put("nom", cursor.getString(1));
            client.put("prenom", cursor.getString(2));
            client.put("email", cursor.getString(3));
            client.put("date_naissance",cursor.getString(4));
            client.put("telephone", cursor.getString(5));
            client.put("id_forfait", cursor.getString(6));
        }
        cursor.close();
        db.close();

        // return user
        Log.d(TAG, "Fetching user from Sqlite: " + client.toString());

        return client;
    }

    public void deleteClients(){
        SQLiteDatabase db = this.getWritableDatabase();
        // Delete All Rows
        db.delete(TABLE_CLIENT, null, null);
        db.close();

        Log.d(TAG, "Deleted all client info from sqlite");
    }
}
