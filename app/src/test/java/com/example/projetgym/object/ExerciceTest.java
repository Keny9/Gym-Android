package com.example.projetgym.object;

import com.example.projetgym.Exercice;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class ExerciceTest {
    private Exercice ex = new Exercice(1,"a","a","a");
    @Test
    public void idValidator(){
        String id = "123";
        ex.setId_type(id);

        assertEquals(id, ex.getId_type());
    }

    @Test
    public void nomValidator(){
        String nom = "kokokiki";
        ex.setNom(nom);

        assertEquals(nom, ex.getNom());
    }

    @Test public void descriptionValidator(){
        String desc = "lundididi";
        ex.setDescription(desc);

        assertEquals(desc, ex.getDescription());
    }

    @Test public void imageValidator(){
        String img = "mickeyROUSSE";
        ex.setImage(img);

        assertEquals(img, ex.getImage());
    }
}
