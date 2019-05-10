package com.example.projetgym.helper;

/****************************************
 Fichier : SessionManager.java
 Auteur : Karl Boutin
 Fonctionnalité : Classe qui va gérer la session de l'application
 Date : 2019-05-07

 Vérification :
 Date               Nom                   Approuvé
 =========================================================


 Historique de modifications :
 Date               Nom                   Description
 =========================================================

 ****************************************/

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.util.Log;

public class SessionManager {

    private static String TAG = SessionManager.class.getSimpleName();

    //Shared preferences
    SharedPreferences sharedPreferences;
    Editor editor;
    Context context;

    // Shared pref mode
    int PRIVATE_MODE = 0;

    //Shared preference nom du fichier
    private static final String PREF_NAME = "ProjetGymLogin";
    private static final String KEY_IS_LOGGEDIN = "isLoggedIn";
    private static final String KEY_ID = "Identifiant";

    public SessionManager(Context context) {
        this.context = context;
        sharedPreferences = this.context.getSharedPreferences(PREF_NAME, PRIVATE_MODE);
        editor = sharedPreferences.edit();
    }

    public void setLogin(boolean isLoggedIn, String identifiant) {

        editor.putBoolean(KEY_IS_LOGGEDIN, isLoggedIn);
        editor.putString(KEY_ID, identifiant);

        // commit changes
        editor.commit();
        editor.apply();

        Log.d(TAG, "User login session modified!");
    }

    public boolean isLoggedIn(){
        return sharedPreferences.getBoolean(KEY_IS_LOGGEDIN, false);
    }

    public String getIdentifiant(){return sharedPreferences.getString(KEY_ID,null);}
}
