package fr.univlyon1.m1if.m1if03.classes;

public class Candidat {
    final String prenom;
    final String nom;

    public Candidat(String prenom, String nom) {
        this.prenom = prenom;
        this.nom = nom;
    }

    public String getPrenom() {
        return prenom;
    }

    public String getNom() {
        return nom;
    }
}