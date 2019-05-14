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

import com.example.projetgym.object.Client;
import com.example.projetgym.object.Evenement;

import java.util.HashMap;

/**
 * Classe permettant la création de la base de donnée et les différentes manipulation de la
 * bd local
 */

public class SQLiteHandler extends SQLiteOpenHelper {

    private static final String TAG = SQLiteHandler.class.getSimpleName();

    // All Static variables
    // Database Version
    private static final int DATABASE_VERSION = 2;

    // Database Name
    private static final String DATABASE_NAME = "gymlocal";

    // Client table name
    private static final String TABLE_CLIENT = "client";

    // Client Table Columns names
    private static final String KEY_ID = "identifiant";
    private static final String KEY_NAME = "nom";
    private static final String KEY_PRENOM = "prenom";
    private static final String KEY_EMAIL = "courriel";
    private static final String KEY_IDFORFAIT = "id_forfait";
    private static final String KEY_DATENAISSANCE = "date_naissance";
    private static final String KEY_TELEPHONE = "telephone";

    public SQLiteHandler(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    /**
     *  Création des tables
     */
    @Override
    public void onCreate(SQLiteDatabase db) {

        //Créer la table poste employé
        String CREATE_POSTEEMPLOYE_TABLE = "CREATE TABLE poste_employe (id INTEGER PRIMARY KEY, nom TEXT);";
        db.execSQL(CREATE_POSTEEMPLOYE_TABLE);

        //Créer la table employé(pour les spécialistes)
        String CREATE_EMPLOYE_TABLE = "CREATE TABLE employe (identifiant TEXT PRIMARY KEY, nom TEXT, prenom TEXT, id_poste INTEGER," +
                "FOREIGN KEY (id_poste) REFERENCES poste_employe(id));";
        db.execSQL(CREATE_EMPLOYE_TABLE);

        //Créer la table type d'événement
        String CREATE_TYPEEVENEMENT_TABLE = "CREATE TABLE type_evenement (id INTEGER PRIMARY KEY," +
                " nom TEXT, description TEXT);";
        db.execSQL(CREATE_TYPEEVENEMENT_TABLE);

        //Créer la table modele de cours
        String CREATE_MODELECOURS_TABLE = "CREATE TABLE modele_cours (id INTEGER PRIMARY KEY, nom TEXT);";
        db.execSQL(CREATE_MODELECOURS_TABLE);

        //Créer la table evenement
        String CREATE_EVENEMENT_TABLE = "CREATE TABLE evenement" + "(" + "id" + " TEXT PRIMARY KEY," +
                "id_modele" + " INTEGER," + "id_type" + " INTEGER," + "identifiant_employe" + " TEXT," +
                "heure" + " INTEGER," + "duree" + " INTEGER," + "prix" + " REAL" + "," +
                "FOREIGN KEY (id_modele) REFERENCES modele_cours(id));";
        db.execSQL(CREATE_EVENEMENT_TABLE);

        //Créer la table de service
        String CREATE_SERVICE_TABLE = "CREATE TABLE service (id INTEGER PRIMARY KEY, nom TEXT, description TEXT);";
        db.execSQL(CREATE_SERVICE_TABLE);

        //Créer la table forfait
        String CREATE_FORFAIT_TABLE = "CREATE TABLE forfait (id INTEGER PRIMARY KEY, nom TEXT, prix REAL, description TEXT, duree INTEGER," +
                "heure_debut_semaine INTEGER, heure_fin_semaine INTEGER, heure_debut_fds INTEGER, heure_fin_fds INTEGER);";
        db.execSQL(CREATE_FORFAIT_TABLE);

        //Créer la table client
        String CREATE_CLIENT_TABLE = "CREATE TABLE " + TABLE_CLIENT + "(" + KEY_ID + " TEXT PRIMARY KEY," +
                KEY_NAME + " TEXT," + KEY_PRENOM + " TEXT," + KEY_EMAIL + " TEXT UNIQUE," + KEY_DATENAISSANCE + " TEXT," +
                KEY_TELEPHONE + " TEXT," + KEY_IDFORFAIT + " INTEGER," + "FOREIGN KEY (id_forfait) REFERENCES forfait(id));";
        db.execSQL(CREATE_CLIENT_TABLE);

        String CREATE_TAFORFSERV_TABLE = "CREATE TABLE ta_forfait_service (id_service INTEGER, id_forfait INTEGER," +
                "FOREIGN KEY (id_service) REFERENCES service(id), FOREIGN KEY (id_forfait) REFERENCES forfait(id));";
        db.execSQL(CREATE_TAFORFSERV_TABLE);

        //Créer la table type exercice
        String CREATE_TYPEEXERCICE_TABLE = "CREATE TABLE type_exercice (id INTEGER PRIMARY KEY, nom TEXT);";
        db.execSQL(CREATE_TYPEEXERCICE_TABLE);

        //Créer la table exercice
        String CREATE_EXERCICE_TABLE = "CREATE TABLE exercice (id INTEGER PRIMARY KEY, id_type INTEGER, nom TEXT, description TEXT, image TEXT," +
                "FOREIGN KEY (id_type) REFERENCES type_exercice(id));";
        db.execSQL(CREATE_EXERCICE_TABLE);

        //Creer la table des plans personnalisé
        String CREATE_PLANPERSO_TABLE = "CREATE TABLE plan_personnalise (id INTEGER PRIMARY KEY, identifiant_client TEXT, FOREIGN KEY (identifiant_client) REFERENCES client(identifiant));";
        db.execSQL(CREATE_PLANPERSO_TABLE);

        //Créer la table ta plan exercice
        String CREATE_TAPLANEXERCICE_TABLE = "CREATE TABLE ta_exercice_plan_personnalise (id_plan INTEGER, id_exercice INTEGER, ordre INTEGER," +
                "FOREIGN KEY (id_plan) REFERENCES plan_personnalise(id), FOREIGN KEY (id_exercice) REFERENCES exercice(id));";
        db.execSQL(CREATE_TAPLANEXERCICE_TABLE);

        //Créer la table ta client evenement
        String CREATE_TACLIENTEVENEMENT_TABLE = "CREATE TABLE ta_client_evenement (id_evenement TEXT, id_client TEXT," +
                "FOREIGN KEY (id_evenement) REFERENCES evenement(id), FOREIGN KEY (id_client) REFERENCES client(identifiant));";
        db.execSQL(CREATE_TACLIENTEVENEMENT_TABLE);

        Log.d(TAG, "Tables de la base de données créés");

        //Ajout des evenements possibles dans la base de donnee
        ajouterTypeEvenement(db,1,"Cours","Les clients s\'inscrivent dans un cours de groupe");
        ajouterTypeEvenement(db,2,"Rendez-vous","Le client prend un rendez-vous avec un employe pour plusieurs raison, par exemple se faire aider par un nutritioniste");
    }

    /**
     * Mise à jour de la base de donnée
     */
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        //Si la vielle table existe on la drop
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_CLIENT);
        db.execSQL("DROP TABLE IF EXISTS evenement");

        //Créer les tables à nouveau
        onCreate(db);
    }

    /**
     *  Ajoute le client (utilisateur actuel) dans la base de donnée local
     */
    public void ajouterClient(Client client){
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_ID, client.getIdentifiant());
        values.put(KEY_NAME, client.getNom());
        values.put(KEY_PRENOM, client.getPrenom());
        values.put(KEY_EMAIL, client.getEmail());
        values.put(KEY_IDFORFAIT, client.getIdForfait());
        values.put(KEY_DATENAISSANCE,client.getDateNaissance());
        values.put(KEY_TELEPHONE, client.getTelephone());

        db.insert(TABLE_CLIENT,null,values);
        db.close();

        Log.d(TAG, "Nouveau client inséré dans sqlite: " + client.getIdentifiant());
    }

    /**
     * Ajout des cours du client dans la base de donnee local
     */
    public void ajouterEvenement(Evenement evenement){
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put("id", evenement.getIdEvenement());
        values.put("id_modele", evenement.getModeleCours());
        values.put("id_type", evenement.getType());
        values.put("identifiant_employe", evenement.getIdEmploye());
        values.put("date", evenement.getDate());
        values.put("heure", evenement.getHeure());
        values.put("duree",evenement.getDuree());
        values.put("prix", evenement.getPrix());

        db.insert("evenement",null,values);
        db.close();

        Log.d(TAG, "Nouvel événement inséré dans sqlite: " + evenement.getIdEvenement());
    }

    /**
     * Ajout des cours du client dans la base de donnee local
     */
    public void inscrireCours(Evenement evenement, String id_client){
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put("id_evenement", evenement.getIdEvenement());
        values.put("id_client", id_client);

        db.insert("evenement",null,values);
        db.close();

        Log.d(TAG, "Nouvel événement inséré dans sqlite: " + evenement.getIdEvenement());
    }

    /**
     * Retourner les informations du client
     * @return information du client dans un tableau
     */
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

    /**
     * Obtenir tous les cours
     * @return les cours existant dans la base de donnee local en lien avec le client
     */
    private HashMap<String, String> getCours(){
        String selectQuery = "SELECT * FROM evenement WHERE id_type = 1";

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        HashMap<String, String> event = new HashMap<String, String>();

        //Aller a la premiere ligne
        cursor.moveToFirst();
        if(cursor.getCount() > 0){
            while(cursor.moveToNext()){
                event.put("id", cursor.getString(0));
                event.put("modele", cursor.getString(1));
                event.put("type", cursor.getString(2));
                event.put("jour", cursor.getString(3));
                event.put("identifiant_employe", cursor.getString(4));
                event.put("date", cursor.getString(5));
                event.put("heure", cursor.getString(6));
                event.put("duree", cursor.getString(7));
                event.put("prix", cursor.getString(8));
            }
        }
        cursor.close();
        db.close();

        // return user
        Log.d(TAG, "Fetching user from Sqlite: " + event.toString());

        return event;
    }

    /**
     *  Ajouter les types d'evenements possibles a la bd local
     */
    public void ajouterTypeEvenement(SQLiteDatabase db,int id, String nom, String description){

        ContentValues values = new ContentValues();
        values.put("id",id);
        values.put("nom",nom);
        values.put("description",description);

        db.insert("type_evenement",null,values);

        Log.d(TAG, "Nouveau type d'evenement inséré dans sqlite: " + id);
    }

    /**
     * Supprimer toutes les tables
     */
    public void deleteTables(){
        SQLiteDatabase db = this.getWritableDatabase();

        deleteClients(db);
        //deleteEvenement(db);
    }

    /**
     * Supprime la table client
     */
    private void deleteClients(SQLiteDatabase db){

        // Delete All Rows
        db.delete(TABLE_CLIENT, null, null);
        db.close();

        Log.d(TAG, "Deleted all client info from sqlite");
    }

    /**
     * Supprime la table evenement
     */
    private void deleteEvenement(SQLiteDatabase db){

        //Supprimer toutes les lignes
        db.delete("evenement",null,null);
        db.close();

        Log.d(TAG,"Tous les evenements du client ont ete supprimés");
    }
}
