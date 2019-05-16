/****************************************
 Fichier : ForfaitTest.java
 Auteur : William Gonin
 Fonctionnalité : Code des test pour l'objet Forfait
 Date : 2019-05-15

 Vérification :
 Date               Nom                   Approuvé
 2019-05-15         Guillaume               Approuve
 =========================================================


 Historique de modifications :
 Date               Nom                   Description
 =========================================================

 ****************************************/
package com.example.projetgym.object;

import org.junit.Test;

import static org.junit.Assert.*;

public class ForfaitTest {

    Forfait forfait = new Forfait(1, "Moana", 10, "Le forfait pour les personnes puissantes", 30, 5,19,5,19,1);

    @Test
    public void getId() {
        assertEquals("identifiant", forfait.getId());
    }

    @Test
    public void setId() {
        forfait.setId(1);
        assertEquals("test", forfait.getId());
    }

    @Test
    public void getNom() {
        assertEquals("Nom", forfait.getNom());

    }

    @Test
    public void setNom() {
        forfait.setNom("test");
        assertEquals("test", forfait.getNom());
    }

    @Test
    public void getPrix() {
        assertEquals(10, forfait.getPrix());
    }

    @Test
    public void setPrix() {
        forfait.setPrix(15);
        assertEquals(15, forfait.getPrix());
    }

    @Test
    public void getDescription() {
        assertEquals("Le forfait pour les personnes puissantes", forfait.getDescription());
    }

    @Test
    public void setDescription() {
        forfait.setDescription("Allo");
        assertEquals("Allo", forfait.getDescription());
    }

    @Test
    public void getDuree() {
        assertEquals(30, forfait.getDuree());
    }

    @Test
    public void setDuree() {
        forfait.setDuree(10);
        assertEquals(10, forfait.getDuree());
    }

    @Test
    public void getHeure_debut_semaine() {
        assertEquals(5, forfait.getHeure_debut_semaine());
    }

    @Test
    public void setHeure_debut_semaine() {
        forfait.setHeure_debut_semaine(6);
        assertEquals(6, forfait.getHeure_debut_semaine());
    }

    @Test
    public void getHeure_fin_semaine() {
        assertEquals(5, forfait.getHeure_fin_semaine());
    }

    @Test
    public void setHeure_fin_semaine() {
        forfait.setHeure_fin_semaine(6);
        assertEquals(6, forfait.getHeure_fin_semaine());
    }

    @Test
    public void getHeure_debut_fds() {
        assertEquals(5, forfait.getHeure_debug_fds());
    }

    @Test
    public void setHeure_debut_fds() {
        forfait.setHeure_debug_fds(6);
        assertEquals(6, forfait.getHeure_debug_fds());
    }

    @Test
    public void getHeure_fin_fds() {
        assertEquals(5, forfait.getHeure_fin_fds());
    }

    @Test
    public void setHeure_fin_fds() {
        forfait.setHeure_fin_fds(6);
        assertEquals(6, forfait.getHeure_fin_fds());
    }
    @Test
    public void getEtat() {
        assertEquals(1, forfait.getEtat());
    }

    @Test
    public void setEtat() {
        forfait.setEtat(2);
        assertEquals(2, forfait.getEtat());
    }
}
