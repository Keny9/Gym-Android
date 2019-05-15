package com.example.projetgym.activity;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
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
import com.example.projetgym.object.Exercice;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Locale;

public class exercice extends AppCompatActivity {

    ListView liste;
   // String titre[] = {"Haut du Corps", "Bas du Corps", "Tous les exercices"};
    //String description[] = {"Cliquer pour voir les exercices du haut du corps (En haut des hanches)", "Cliquer pour voir les exercices du bas du corps (En bas des hanches)", "Cliquer pour voir tout les exercices"};
    int image[] = {R.drawable.bicep, R.drawable.legpress, R.drawable.toutexercice};



    private static final String TAG = LoginActivity.class.getSimpleName();

    private ProgressDialog pDialog;
    private ListView list;
    private SessionManager session;
    private SQLiteHandler db;

    private ArrayList<String> titre = new ArrayList<>();                   //Liste des titres
    private ArrayList<String> description = new ArrayList<>();             //Liste des descriptions
    //int image[] = {R.drawable.test, R.drawable.test, R.drawable.test};     //Liste des images des cours


    JSONArray exercices = null;                                                //Array JSON
    private ArrayList<Exercice> eventExercices = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_exercice);

        Intent intentCategorie=getIntent();
        Bundle extras= intentCategorie.getExtras();
        TextView titres=(TextView) findViewById(R.id.textView);
        titres.setText(extras.getString("categorie", "Tout les exercices"));

        //Progress dialog
        pDialog = new ProgressDialog(this);
        pDialog.setCancelable(false);

        //SQLite base de donnée gestion
        db = new SQLiteHandler(getApplicationContext());

        //Session manager
        session = new SessionManager(getApplicationContext());
        configureBackButton();


        checkExercices(extras.getString("categorie"));



/*
        liste = findViewById(R.id.listView);

        //Créer l'Adapteur
        exercice.MyAdapter adapter = new exercice.MyAdapter(this, titre, description, image);
        //Attribuer l'Adapteur à la liste
        liste.setAdapter(adapter);

        //Créer le click listener
        liste.setOnItemClickListener(new AdapterView.OnItemClickListener(){
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id){
                if(position == 0){
                    Toast.makeText(exercice.this, "Ca marche", Toast.LENGTH_LONG).show();
                }
            }
        });
        */
    }



    /**
     * Ramasser les cours
     */
    private void checkExercices(String choix) {
        // Tag used to cancel the request
        String tag_string_req = "req_exercices";
       // String appConfig="AppConfig.URL_EXERCICES";

        pDialog.setMessage("Chargement en cours...");
        //showDialog();

        //if(choix.equals("Haut du Corps"))
        //    appConfig="AppConfig.URL_EXERCICESHAUT";
       // else if(choix.equals("Bas du corps"))
        //    appConfig="AppConfig.URL_EXERCICESBAS";
       // else
        //    appConfig="AppConfig.URL_EXERCICES";

        StringRequest strReq = new StringRequest(Request.Method.POST, AppConfig.URL_EXERCICES, new Response.Listener<String>() {

            @Override
            public void onResponse(String response){
                Log.d(TAG, "Exercices Response: " + response.toString());
                //hideDialog();

                try {
                    JSONObject jObj = new JSONObject(response);
                    int success = jObj.getInt("success");

                    // Check for error node in json
                    if (success == 1) {

                        //Obtenir les tableaux de exercice
                        exercices = jObj.getJSONArray("exercices");

                        for(int i = 0; i < exercices.length(); i++){
                            JSONObject r = exercices.getJSONObject(i);

                          //  int idExercice = r.getInt("id");
                            int type = r.getInt("type");
                            String nom = r.getString("nom");
                            String description = r.getString("description");
                            String image = r.getString("image");

                           /* String idEmploye = "";
                            String nom = "";
                            String prenom = "";
                            String poste = "";*/

                            Exercice ex = new Exercice(type,nom,description,image);

                            //Ajoute chaque rendez-vous dans un arrayList
                            eventExercices.add(ex);
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
                //hideDialog();
            }
        }) {

        };

        // Adding request to request queue
        AppController.getInstance().addToRequestQueue(strReq, tag_string_req);
    }

    /**
     * Remplir les tableaux pour l'affichage dans le arrayList
     */
    private void remplirTable(){

        for(int i = 0; i < eventExercices.size();i++){
            Log.d(TAG, "Exercices : " + i);
            //hideDialog();

            titre.add(eventExercices.get(i).getNom());
            description.add(eventExercices.get(i).getDescription());
        }

        //Créer l'Adapteur
        Log.d(TAG,"Cest dla merde");
        MyAdapter adapter = new MyAdapter(this, titre, description, image);
        //Log.d(TAG,"Cest dla merde"+ adapter.toString());
        //Attribuer l'Adapteur à la liste
        list.setAdapter(adapter);
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
         *
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
/*
    class MyAdapter extends ArrayAdapter<String> {
        Context context;
        String rTitle[];
        String rDescription[];
        int rImgs[];

        MyAdapter(Context c, String title[], String description[], int imgs[]) {
            super(c, R.layout.row, R.id.textView1, title);
            this.context = c;
            this.rTitle = title;
            this.rDescription = description;
            this.rImgs = imgs;
        }

        @NonNull
        @Override
        public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
            LayoutInflater layoutInflater = (LayoutInflater) getApplicationContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View row = layoutInflater.inflate(R.layout.row, parent, false);
            ImageView images = row.findViewById(R.id.image);
            TextView myTitle = row.findViewById(R.id.textView1);
            TextView myDescription = row.findViewById(R.id.textView2);

            images.setImageResource(rImgs[position]);
            myTitle.setText(rTitle[position]);
            myDescription.setText(rDescription[position]);


            return row;
        }
    }
*/
    public void configureBackButton()
    {
        Button backButton= (Button) findViewById(R.id.button);
        backButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                startActivity(new Intent(exercice.this, categorie.class));
            }
        });
    }
}