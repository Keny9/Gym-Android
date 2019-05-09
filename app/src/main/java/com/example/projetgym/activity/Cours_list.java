package com.example.projetgym.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.example.projetgym.R;

public class Cours_list extends AppCompatActivity {
    private static final String TAG = LoginActivity.class.getSimpleName();
//    private ProgressDialog pDialog = new ProgressDialog(this);
//    private SQLiteHandler db;

    ListView liste;
    String titre[] = {"BBBBBB 1", "Titre 2", "Titre 3"};
    String description[] = {"Texte 1", "Texte 2", "Texte 3"};
    int image[] = {R.drawable.test, R.drawable.test, R.drawable.test};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
    //    db = new SQLiteHandler(getApplicationContext());
     //   setCours();

        super.onCreate(savedInstanceState);
        setContentView(R.layout.cours_liste);

        liste = findViewById(R.id.listView);

        //Créer l'Adapteur
        MyAdapter adapter = new MyAdapter(this, titre, description, image);
        //Attribuer l'Adapteur à la liste
        liste.setAdapter(adapter);

        final Evenement cours = new Evenement(1, "modele","type","jour", "idEmploye", "date", 1,1,1);

        //Créer le click listener
        liste.setOnItemClickListener(new AdapterView.OnItemClickListener(){
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id){
                Intent intent = new Intent(Cours_list.this, infoCours.class);
                intent.putExtra("Cours", cours);
                startActivity(intent);
                finish();
            }
        });

    }

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




//    private void setCours(){
//
//        // Tag used to cancel the request
//        String tag_string_req = "req_login";
//
//        pDialog.setMessage("Connexion en cours...");
//        showDialog();
//
//        StringRequest strReq = new StringRequest(Request.Method.POST, AppConfig.URL_LOGIN, new Response.Listener<String>() {
//
//            @Override
//            public void onResponse(String response) {
//                Log.d(TAG, "Login Response: " + response.toString());
//                hideDialog();
//
//                try {
//                    JSONObject jObj = new JSONObject(response);
//                    boolean error = jObj.getBoolean("error");
//
//                    // Check for error node in json
//                    if (!error) {
//
//                        // Mettre les cours dans SQLite
//                        JSONObject cours = jObj.getJSONObject("cours");
//                        int id = cours.getInt("id");
//                        int id_modele = cours.getInt("id_modele");
//                        int id_type = cours.getInt("id_type");
//                        int id_jour = cours.getInt("id_jour");
//                        String identifiant_employe = cours.getString("identifiant_employe");
//                        String date = cours.getString("date");
//                        int heure = cours.getInt("heure");
//                        int duree = cours.getInt("duree");
//                        double prix = cours.getDouble("prix");
//
//
//                        // Insérer le client dans la table client
//                        db.ajouterCours(id,id_modele,id_type,id_jour,identifiant_employe,date,heure, duree, prix);
//
//                    } else {
//                        //Erreur dans la connexion, obtenir le message d'erreur
//                        String errorMsg = jObj.getString("error_msg");
//                        Toast.makeText(getApplicationContext(), errorMsg, Toast.LENGTH_LONG).show();
//                    }
//                } catch (JSONException e) {
//                    // JSON erreur
//                    e.printStackTrace();
//                    Toast.makeText(getApplicationContext(), "Json erreur: " + e.getMessage(), Toast.LENGTH_LONG).show();
//                }
//
//            }
//        }, new Response.ErrorListener() {
//
//            @Override
//            public void onErrorResponse(VolleyError error) {
//                Log.e(TAG, "Erreur de connexion: " + error.getMessage());
//                Toast.makeText(getApplicationContext(), error.getMessage(), Toast.LENGTH_LONG).show();
//                hideDialog();
//            }
//        });
//
//        // Adding request to request queue
//        AppController.getInstance().addToRequestQueue(strReq, tag_string_req);
//    }
//    private void showDialog() {
//        if (!pDialog.isShowing())
//            pDialog.show();
//    }
//
//    private void hideDialog() {
//        if (pDialog.isShowing())
//            pDialog.dismiss();
//    }

}
