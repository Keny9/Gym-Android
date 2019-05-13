/****************************************
 Fichier : Cours_list.java
 Auteur : Guillaume Côté
 Fonctionnalité : Code de l'Activité Cours_liste. Affiche la liste des cours
 Date : 2019-05-08

 Vérification :
 Date               Nom                   Approuvé
 =========================================================


 Historique de modifications :
 Date               Nom                   Description
 =========================================================

 ****************************************/

package com.example.projetgym.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import android.app.ProgressDialog;


import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.example.projetgym.R;
import com.example.projetgym.app.AppConfig;
import com.example.projetgym.app.AppController;
import com.example.projetgym.helper.SQLiteHandler;
import com.example.projetgym.helper.SessionManager;
import com.example.projetgym.object.Evenement;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class Cours_list extends BaseActivity {
    private static final String TAG = LoginActivity.class.getSimpleName();

    private ProgressDialog pDialog;
    private ListView list;
    private Button btnRetour;
    private SessionManager session;
    private SQLiteHandler db;

    private ArrayList<String> titre = new ArrayList<>();                   //Liste des titres
    private ArrayList<String> description = new ArrayList<>();             //Liste des descriptions
    int image[] = {R.drawable.test, R.drawable.test, R.drawable.test};     //Liste des images des cours

    JSONArray cours = null;                                                //Array JSON
    private ArrayList<Evenement> eventCours = new ArrayList<>();           //liste de tous les cours

    /**
     * Création de la page
     * @param savedInstanceState
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.cours_liste);

        //Progress dialog
        pDialog = new ProgressDialog(this);
        pDialog.setCancelable(false);

        //SQLite base de donnée gestion
        db = new SQLiteHandler(getApplicationContext());

        //Session manager
        session = new SessionManager(getApplicationContext());

        list = findViewById(R.id.listView);
        btnRetour = findViewById(R.id.retour);

        checkCours();

        clickEvenement();
    }


    /**
     * Remplir les tableaux pour l'affichage dans le arrayList
     */
    private void remplirTable(){

        for(int i = 0; i < eventCours.size();i++){
            Log.d(TAG, "Cours : " + i);
            hideDialog();

            titre.add(eventCours.get(i).getModeleCours());
            description.add("Le " + eventCours.get(i).getDate() + " à " + eventCours.get(i).getHeure()+ "h");
        }

        //Créer l'Adapteur
        MyAdapter adapter = new MyAdapter(this, titre, description, image);

        //Attribuer l'Adapteur à la liste
        list.setAdapter(adapter);
    }

    /**
     * Ramasser les cours
     */
    private void checkCours() {
        // Tag used to cancel the request
        String tag_string_req = "req_cours";

        pDialog.setMessage("Chargement en cours...");
        showDialog();

        StringRequest strReq = new StringRequest(Request.Method.POST, AppConfig.URL_COURS, new Response.Listener<String>() {

            @Override
            public void onResponse(String response){
                Log.d(TAG, "Cours Response: " + response.toString());
                hideDialog();

                try {
                    JSONObject jObj = new JSONObject(response);
                    int success = jObj.getInt("success");

                    // Check for error node in json
                    if (success == 1) {

                        //Obtenir les tableaux de cours
                        cours = jObj.getJSONArray("cours");

                        for(int i = 0; i < cours.length(); i++){
                            JSONObject r = cours.getJSONObject(i);

                            String idEvent = r.getString("id");
                            String modele = r.getString("modele");
                            int heure = r.getInt("heure");
                            int duree = r.getInt("duree");
                            String date = r.getString("date");
                            double prix = r.getDouble("prix");

                            String idEmploye = "";
                            String nom = "";
                            String prenom = "";
                            String poste = "";

                            Evenement rv = new Evenement(idEvent,modele,1,idEmploye,date,heure,duree,prix,nom,prenom, poste);

                            //Ajoute chaque rendez-vous dans un arrayList
                            eventCours.add(rv);
                        }
                        remplirTable();

                    } else {
                        //Erreur dans le chargement, obtenir le message d'erreur
                        String errorMsg = jObj.getString("message");
                        Toast.makeText(getApplicationContext(), errorMsg, Toast.LENGTH_LONG).show();
                    }
                } catch (JSONException e) {
                    // JSON erreur
                    e.printStackTrace();
                    Toast.makeText(getApplicationContext(), "Json erreur: 2222 " + e.getMessage(), Toast.LENGTH_LONG).show();
                }
            }
        }, new Response.ErrorListener(){
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e(TAG, "Erreur de chargement: " + error.getMessage());
                Toast.makeText(getApplicationContext(), error.getMessage(), Toast.LENGTH_LONG).show();
                hideDialog();
            }
        }) {

        };

        // Adding request to request queue
        AppController.getInstance().addToRequestQueue(strReq, tag_string_req);
    }

    /**
     * Afficher le dialog
     */
    private void showDialog() {
        if (!pDialog.isShowing())
            pDialog.show();
    }

    /**
     * Cacher le dialog
     */
    private void hideDialog() {
        if (pDialog.isShowing())
            pDialog.dismiss();
    }


    /**
     * Générer les clicks listeners pour les boutons de la page
     */
    private void clickEvenement(){
        //Créer le click listener
        list.setOnItemClickListener(new AdapterView.OnItemClickListener(){
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id){
                Intent intent = new Intent(Cours_list.this, infoCours.class);
                intent.putExtra("Evenement", eventCours.get(position));
                startActivity(intent);
                finish();
            }
        });

        btnRetour.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                Intent intent = new Intent(Cours_list.this, AccueilActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }


}

/**
 * Classe qui joue le role d'un adapteur pour le list view
 */
class MyAdapter extends ArrayAdapter<String> {
    Context context;
    ArrayList<String> rTitle;
    ArrayList<String> rDescription;
    int rImgs[];

    MyAdapter(Context c, ArrayList<String> title, ArrayList<String> description, int imgs[]) {
        super(c, R.layout.row, R.id.textView1, title);
        this.context = c;
        this.rTitle = title;
        this.rDescription = description;
        this.rImgs = imgs;
    }

    /**
     * Retoure la view d'un cours
     * @param position
     * @param convertView
     * @param parent
     * @return
     */
    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater layoutInflater = (LayoutInflater) context.getApplicationContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View row = layoutInflater.inflate(R.layout.row, parent, false);
        ImageView images = row.findViewById(R.id.image);
        TextView myTitle = row.findViewById(R.id.textView1);
        TextView myDescription = row.findViewById(R.id.textView2);

        images.setImageResource(rImgs[0]);
        myTitle.setText(rTitle.get(position));
        myDescription.setText(rDescription.get(position));


        return row;
    }
}
