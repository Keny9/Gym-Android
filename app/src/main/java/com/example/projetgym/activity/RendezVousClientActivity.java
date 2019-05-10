package com.example.projetgym.activity;

import android.app.ProgressDialog;
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

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.example.projetgym.R;
import com.example.projetgym.object.RendezVous;
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

/**
 * Classe de l'activite qui montre tous les rendez-vous du client
 */
public class RendezVousClientActivity extends AppCompatActivity {

    private static final String TAG = RendezVousClientActivity.class.getSimpleName();

    private ProgressDialog pDialog;
    private SessionManager session;
    private SQLiteHandler db;

    private ListView list;
    private Button btnRetour;

    JSONArray rendezvous = null;

    private ArrayList<RendezVous> rendez = new ArrayList<>();

    private ArrayList<String> titre = new ArrayList<>();
    private ArrayList<String> description = new ArrayList<>();
    private int image[] = {R.drawable.pomme};

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rendezvousclient);

        //Progress dialog
        pDialog = new ProgressDialog(this);
        pDialog.setCancelable(false);

        //SQLite base de donnée gestion
        db = new SQLiteHandler(getApplicationContext());

        //Session manager
        session = new SessionManager(getApplicationContext());

        list = findViewById(R.id.listRendezVousClient);
        btnRetour = findViewById(R.id.btnRetourRendezVous);

        checkRendezvous(session.getIdentifiant());

        clickEvenement();
    }

    /**
     * Remplir les tableaux pour l'affichage dans le arrayList
     */
    private void remplirTable(){

        for(int i = 0; i < rendez.size();i++){
            titre.add(rendez.get(i).getPoste());
            description.add(rendez.get(i).getDate() + " " + rendez.get(i).getHeure());
        }

        //Créer l'Adapteur
        MyAdapter adapter = new MyAdapter(this, titre, description, image);

        //Attribuer l'Adapteur à la liste
        list.setAdapter(adapter);
    }

    /**
     * Ramasser les rendez-vous du client
     */
    private void checkRendezvous(final String identifiant) {
        // Tag used to cancel the request
        String tag_string_req = "req_rendezvous";

        pDialog.setMessage("Chargement en cours...");
        showDialog();

        StringRequest strReq = new StringRequest(Request.Method.POST, AppConfig.URL_RENDEZVOUS, new Response.Listener<String>() {

            @Override
            public void onResponse(String response){
                Log.d(TAG, "Rendez-vous Response: " + response.toString());
                hideDialog();

                try {
                    JSONObject jObj = new JSONObject(response);
                    int success = jObj.getInt("success");

                    // Check for error node in json
                    if (success == 1) {

                        //Obtenir les tableaux de rendez-vous
                        rendezvous = jObj.getJSONArray("rendezvous");

                        for(int i = 0; i < rendezvous.length(); i++){
                            JSONObject r = rendezvous.getJSONObject(i);

                            String date = r.getString("date");
                            int heure = r.getInt("heure");
                            int duree = r.getInt("duree");
                            double prix = r.getDouble("prix");
                            String nom = r.getString("nom");
                            String prenom = r.getString("prenom");
                            String poste = r.getString("poste");

                            RendezVous rv = new RendezVous(date,heure,duree,prix,nom,prenom,poste);

                            //Ajoute chaque rendez-vous dans un arrayList
                            rendez.add(rv);

                            remplirTable();
                        }

                    } else {
                        //Erreur dans le chargement, obtenir le message d'erreur
                        String errorMsg = jObj.getString("message");
                        Toast.makeText(getApplicationContext(), errorMsg, Toast.LENGTH_LONG).show();
                    }
                } catch (JSONException e) {
                    // JSON erreur
                    e.printStackTrace();
                    Toast.makeText(getApplicationContext(), "Json erreur: " + e.getMessage(), Toast.LENGTH_LONG).show();
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

            @Override
            protected Map<String, String> getParams() {
                // Posting parameters to login url
                Map<String, String> params = new HashMap<String, String>();
                params.put("identifiant", identifiant);

                return params;
            }

        };

        // Adding request to request queue
        AppController.getInstance().addToRequestQueue(strReq, tag_string_req);
    }

    //Gere tous clicks possibles sur la page
    private void clickEvenement(){
        //Créer le click listener
        list.setOnItemClickListener(new AdapterView.OnItemClickListener(){
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id){
                if(position == 0){
                    Toast.makeText(RendezVousClientActivity.this, "Ca marche", Toast.LENGTH_LONG).show();
                }
            }
        });

        btnRetour.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                Intent intent = new Intent(RendezVousClientActivity.this,AccueilActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }

    private void showDialog() {
        if (!pDialog.isShowing())
            pDialog.show();
    }

    private void hideDialog() {
        if (pDialog.isShowing())
            pDialog.dismiss();
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