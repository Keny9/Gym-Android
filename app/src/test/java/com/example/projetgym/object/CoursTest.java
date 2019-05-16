package com.example.projetgym.object;


import org.junit.Test;

import static org.junit.Assert.*;
import com.example.projetgym.Cours;

public class CoursTest/* extends TestCase*/ {
    private Cours cours = new Cours("a","a","a","a","a",1,1,1);
    @Test
    public void idValidator(){
        String id = "C00006";
        cours.setId(id);

        assertEquals(id, cours.getId());
    }

    @Test
    public void modeleValidator(){
        String modele = "kokokiki";
        cours.setModele(modele);

        assertEquals(modele, cours.getModele());
    }

    @Test public void jourValidator(){
        String jour = "lundididi";
        cours.setJour(jour);

        assertEquals(jour, cours.getJour());
    }

    @Test public void idEmployeValidator(){
        String idEmploye = "mickeyROUSSE";
        cours.setIdentifiant_employe(idEmploye);

        assertEquals(idEmploye, cours.getIdentifiant_employe());
    }

    @Test public void dateValidator(){
        String date = "2019-01-01";
        cours.setDate(date);

        assertEquals(date, cours.getDate());
    }

    @Test public void heureValidator(){
        int heure = 12;
        cours.setHeure(heure);

        assertEquals(heure, cours.getHeure());
    }

    @Test public void dureeValidator(){
        int duree = 10;
        cours.setDuree(duree);

        assertEquals(duree, cours.getDuree());
    }

    @Test public void prixValidator(){
        double prix = 12.12;
        cours.setPrix(prix);

        assertEquals(prix, cours.getPrix(), 0);
    }
}
