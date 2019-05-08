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
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.projetgym.Maquette_liste;
import com.example.projetgym.R;

/**
 * Classe de l'activite qui montre tous les rendez-vous du client
 */
public class RendezVousClientActivity extends AppCompatActivity {

    private ListView list;
    private Button btnRetour;

    String titre[] = {"Titre 1", "Titre 2", "Titre 3"};
    String description[] = {"Texte 1", "Texte 2", "Texte 3"};
    int image[] = {R.drawable.test, R.drawable.test, R.drawable.test};

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rendezvousclient);

        list = findViewById(R.id.listRendezVousClient);
        btnRetour = findViewById(R.id.btnRetourRendezVous);

        //Créer l'Adapteur
        MyAdapter adapter = new MyAdapter(this, titre, description, image);
        //Attribuer l'Adapteur à la liste
        list.setAdapter(adapter);

        clickEvenement();

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
}

/**
 * Classe qui joue le role d'un adapteur pour le list view
 */
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
        LayoutInflater layoutInflater = (LayoutInflater) context.getApplicationContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
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