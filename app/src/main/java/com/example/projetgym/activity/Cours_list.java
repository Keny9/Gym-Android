package com.example.projetgym.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
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
import com.example.projetgym.Cours;
import com.example.projetgym.R;
import com.example.projetgym.app.AppConfig;
import com.example.projetgym.app.AppController;
import com.example.projetgym.helper.SQLiteHandler;
import com.example.projetgym.helper.SessionManager;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Cours_list extends AppCompatActivity {
    private static final String TAG = LoginActivity.class.getSimpleName();

    private ProgressDialog pDialog;
    private ListView list;
    private Button btnRetour;
    private SessionManager session;
    private SQLiteHandler db;

    private ArrayList<String> titre = new ArrayList<>();
    private ArrayList<String> description = new ArrayList<>();
    int image[] = {R.drawable.test, R.drawable.test, R.drawable.test};
    JSONArray cours = null;
    private ArrayList<Cours> eventCours = new ArrayList<>();

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

            titre.add(eventCours.get(i).getModele());
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

                             String id = r.getString("id");
                             String modele = r.getString("modele");
                             String description = r.getString("description");
                             String jour = r.getString("jour");
                             String identifiant_employe = r.getString("identifiant_employe");
                             String date = r.getString("date");
                             int heure = r.getInt("heure");
                             int duree= r.getInt("duree");
                             double prix = r.getDouble("prix");

                            Cours rv = new Cours(id, modele, description, jour, identifiant_employe, date, heure, duree, prix);

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

    private void showDialog() {
        if (!pDialog.isShowing())
            pDialog.show();
    }

    private void hideDialog() {
        if (pDialog.isShowing())
            pDialog.dismiss();
    }


    //Gere tous clicks possibles sur la page
    private void clickEvenement(){
        //Créer le click listener
        list.setOnItemClickListener(new AdapterView.OnItemClickListener(){
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id){
                Intent intent = new Intent(Cours_list.this, infoCours.class);
                intent.putExtra("Cours", eventCours.get(position));
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
