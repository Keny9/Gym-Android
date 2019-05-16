/****************************************
 Fichier : ClientTest.java
 Auteur : Guillaume Côté
 Fonctionnalité : Testes unitaires de la classe Client
 Date : 2019-05-15

 Vérification :
 Date               Nom                   Approuvé
 =========================================================


 Historique de modifications :
 Date               Nom                   Description
 =========================================================
****************************************/

 package com.example.projetgym.object;

import org.junit.Test;

import static org.junit.Assert.*;

public class ClientTest {
    Client client = new Client("identifiant", "Nom", "Prenom", "email@email.com", 1, "6666-66-66","666-6666-666");

    @Test
    public void getIdentifiant() {
        assertEquals("identifiant", client.getIdentifiant());
    }

    @Test
    public void setIdentifiant() {
        client.setIdentifiant("test");
        assertEquals("test", client.getIdentifiant());
    }

    @Test
    public void getNom() {
        assertEquals("Nom", client.getNom());

    }

    @Test
    public void setNom() {
        client.setNom("test");
        assertEquals("test", client.getNom());
    }

    @Test
    public void getPrenom() {
        assertEquals("Prenom", client.getPrenom());
    }

    @Test
    public void setPrenom() {
        client.setPrenom("test");
        assertEquals("test", client.getPrenom());
    }

    @Test
    public void getEmail() {
        assertEquals("email@email.com", client.getEmail());
    }

    @Test
    public void setEmail() {
        client.setEmail("test");
        assertEquals("test", client.getEmail());
    }

    @Test
    public void getIdForfait() {
        assertEquals(1, client.getIdForfait());
    }

    @Test
    public void setIdForfait() {
        client.setIdForfait(10);
        assertEquals(10, client.getIdForfait());
    }

    @Test
    public void getDateNaissance() {
        assertEquals("6666-66-66", client.getDateNaissance());
    }

    @Test
    public void setDateNaissance() {
        client.setDateNaissance("test");
        assertEquals("test", client.getDateNaissance());
    }

    @Test
    public void getTelephone() {
        assertEquals("666-6666-666", client.getTelephone());
    }

    @Test
    public void setTelephone() {
        client.setTelephone("test");
        assertEquals("test", client.getTelephone());
    }
}