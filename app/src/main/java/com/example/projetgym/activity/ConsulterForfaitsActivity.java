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
import com.example.projetgym.app.AppConfig;
import com.example.projetgym.app.AppController;
import com.example.projetgym.helper.SQLiteHandler;
import com.example.projetgym.helper.SessionManager;
import com.example.projetgym.object.Evenement;
import com.example.projetgym.object.Forfait;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

//Classe pour l'affichage de TOUS les forfaits
public class ConsulterForfaitsActivity extends BaseActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_consulter_forfaits);

        //Progress dialog
        pDialog = new ProgressDialog(this);
        pDialog.setCancelable(false);

        //SQLite base de donnée gestion
        db = new SQLiteHandler(getApplicationContext());

        //Session manager
        session = new SessionManager(getApplicationContext());

        list = findViewById(R.id.listView);


        Button button = (Button) findViewById(R.id.btn_forfaits_retour);

        button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                // Retour à l'interface des forfaits
                Intent intent = new Intent(ConsulterForfaitsActivity.this, ConsulterSonForfaitActivity.class);
                startActivity(intent);
                finish();
            }
        });

        getForfait();
    }


    /**
     * Remplir les tableaux pour l'affichage dans le arrayList
     */
    private void remplirTable(){

        for(int i = 0; i < eventForfait.size(); i++){
            Log.d(TAG, "forfait : " + i);
            hideDialog();

            titre.add(eventForfait.get(i).getNom());
            description.add(eventForfait.get(i).getPrix() + "$ par mois \n" + eventForfait.get(i).getDescription());
        }

        //Créer l'Adapteur
        AdapterForfait adapter = new AdapterForfait(this, titre, description, image);

        //Attribuer l'Adapteur à la liste
        list.setAdapter(adapter);
    }

    /**
     * Ramasser les forfaits
     */
    private void getForfait() {
        // Tag used to cancel the request
        String tag_string_req = "req_cours";

        pDialog.setMessage("Chargement en cours...");
        showDialog();

        StringRequest strReq = new StringRequest(Request.Method.POST, AppConfig.URL_GET_FORFAITS, new Response.Listener<String>() {

            @Override
            public void onResponse(String response){
                Log.d(TAG, "Forfait Response: " + response.toString());
                hideDialog();

                try {
                    JSONObject jObj = new JSONObject(response);
                    int success = jObj.getInt("success");

                    // Check for error node in json
                    if (success == 1) {

                        //Obtenir les tableaux de forfaits
                        forfaits = jObj.getJSONArray("forfait");

                        for(int i = 0; i < forfaits.length(); i++){
                            JSONObject r = forfaits.getJSONObject(i);

                            int id = r.getInt("id");
                            String modele = r.getString("modele");
                            double prix = r.getDouble("prix");
                            String description = r.getString("description");
                            int duree = r.getInt("duree");
                            int heure_debut_semaine = r.getInt("heure_debut_semaine");
                            int heure_fin_semaine = r.getInt("heure_fin_semaine");
                            int heure_debut_fds = r.getInt("heure_debut_fds");
                            int heure_fin_fds = r.getInt("heure_fin_fds");
                            int etat = r.getInt("etat");


                            com.example.projetgym.object.Forfait forfait = new Forfait(id,modele,prix, description, duree, heure_debut_semaine, heure_fin_semaine, heure_debut_fds, heure_fin_fds, etat);

                            //Ajoute chaque rendez-vous dans un arrayList
                            eventForfait.add(forfait);
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
                Intent intent = new Intent(ConsulterForfaitsActivity.this, ConsulterUnForfaitActivity.class);
                intent.putExtra("Forfait", eventForfait.get(position));
                startActivity(intent);
                finish();
            }
        });

        btnRetour.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                Intent intent = new Intent(ConsulterForfaitsActivity.this, AccueilActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }
}

/**
 * Classe qui joue le role d'un adapteur pour le list view
 */
class AdapterForfait extends ArrayAdapter<String> {
    Context context;
    ArrayList<String> rTitle;
    ArrayList<String> rDescription;
    int rImgs[];

    AdapterForfait(Context c, ArrayList<String> title, ArrayList<String> description, int imgs[]) {
        super(c, R.layout.row, R.id.textView1, title);
        this.context = c;
        this.rTitle = title;
        this.rDescription = description;
        this.rImgs = imgs;
    }

    /**
     * Retoure la view d'un forfaits
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
