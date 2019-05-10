package com.example.projetgym.activity;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
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

import com.example.projetgym.R;

import java.util.Locale;

public class exercice extends AppCompatActivity {

    ListView liste;
    String titre[] = {"Haut du Corps", "Bas du Corps", "Tous les exercices"};
    String description[] = {"Cliquer pour voir les exercices du haut du corps (En haut des hanches)", "Cliquer pour voir les exercices du bas du corps (En bas des hanches)", "Cliquer pour voir tout les exercices"};
    int image[] = {R.drawable.bicep, R.drawable.legpress, R.drawable.toutexercice};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_exercice);

        Intent intentCategorie=getIntent();
        Bundle extras= intentCategorie.getExtras();
        TextView titres=(TextView) findViewById(R.id.textView);
        titres.setText(extras.getString("categorie", "Les exercices"));
        configureBackButton();


        Button button = (Button) findViewById(R.id.button3);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                String uri = String.format(Locale.ENGLISH, "http://maps.google.com/maps?daddr=%f,%f (%s)", 28.385233, -81.563873, "Disney's Weightland");
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(uri));
                intent.setPackage("com.google.android.apps.maps");
                startActivity(intent);
            }
        });




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