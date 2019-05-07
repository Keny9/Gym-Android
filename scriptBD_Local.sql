DROP DATABASE IF EXISTS GymLocal;
CREATE DATABASE GymLocal CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;
USE GymLocal;

CREATE TABLE poste_employe (
id INT NOT NULL AUTO_INCREMENT PRIMARY KEY,
nom VARCHAR(30) NOT NULL,
description VARCHAR(50)
);

CREATE TABLE etat_employe (
id INT NOT NULL AUTO_INCREMENT PRIMARY KEY,
etat  VARCHAR(30) NOT NULL,
description VARCHAR(50)
);

CREATE TABLE employe (
identifiant VARCHAR(30) PRIMARY KEY,
nom VARCHAR(30) NOT NULL,
prenom VARCHAR(30) NOT NULL,
courriel VARCHAR(50) NOT NULL,
telephone BIGINT NOT NULL,
id_poste INTEGER NOT NULL,
id_etat INTEGER NOT NULL,
FOREIGN KEY (id_poste) REFERENCES poste_employe(id),
FOREIGN KEY (id_etat) REFERENCES etat_employe(id)
);

CREATE TABLE jour_semaine (
id INT NOT NULL AUTO_INCREMENT PRIMARY KEY,
nom_jour VARCHAR(20)
);

CREATE TABLE type_evenement (
id INT NOT NULL AUTO_INCREMENT PRIMARY KEY,
nom VARCHAR(30) NOT NULL,
description VARCHAR(100)
);

CREATE TABLE modele_cours (
id INT AUTO_INCREMENT PRIMARY KEY,
nom VARCHAR(30)
);

CREATE TABLE evenement (
id VARCHAR(6) NOT NULL  PRIMARY KEY,
id_modele INTEGER,
id_type INTEGER NOT NULL,
id_jour INTEGER,
identifiant_employe VARCHAR(30) NOT NULL,
date DATE,
heure INTEGER,
duree INTEGER,
prix FLOAT,
FOREIGN KEY (id_modele) REFERENCES modele_cours(id),
FOREIGN KEY (id_type) REFERENCES type_evenement(id),
FOREIGN KEY (id_jour) REFERENCES jour_semaine(id),
FOREIGN KEY (identifiant_employe) REFERENCES employe(identifiant)
);

CREATE TABLE service (
	id INT NOT NULL AUTO_INCREMENT PRIMARY KEY,
	nom VARCHAR(30),
	description VARCHAR(500)
);

CREATE TABLE forfait (
	id INT AUTO_INCREMENT PRIMARY KEY,
	nom VARCHAR(30) NOT NULL,
	prix FLOAT NOT NULL,
	description VARCHAR(800),
	duree INT NOT NULL,
	heure_debut_semaine INT NOT NULL ,
	heure_fin_semaine INT NOT NULL,
	heure_debut_fds INT NOT NULL,
	heure_fin_fds INT NOT NULL,
    etat BOOLEAN NOT NULL DEFAULT 1
	);

CREATE TABLE etat_forfait_client (
	id INT NOT NULL AUTO_INCREMENT PRIMARY KEY,
    etat VARCHAR(30),
    description VARCHAR(50)
);

CREATE TABLE client (
  identifiant VARCHAR(30) NOT NULL PRIMARY KEY,
  id_forfait INT,
  nom VARCHAR(30) NOT NULL,
  prenom VARCHAR(30) NOT NULL,
  date_naissance DATE NOT NULL,
  courriel VARCHAR(50) NOT NULL,
  telephone BIGINT NOT NULL,
  mot_de_passe VARCHAR(30) NOT NULL,
  FOREIGN KEY (id_forfait) REFERENCES forfait(id)
);

CREATE TABLE ta_forfait_client (
	identifiant_client VARCHAR(30) NOT NULL,
    id_forfait INTEGER NOT NULL,
	id_etat INTEGER NOT NULL,
    date_debut DATE NOT NULL,
    date_echeance DATE NOT NULL,
    PRIMARY KEY (identifiant_client, id_forfait),
    FOREIGN KEY (identifiant_client) REFERENCES client(identifiant),
    FOREIGN KEY (id_forfait) REFERENCES forfait(id),
    FOREIGN KEY (id_etat) REFERENCES etat_forfait_client(id)
);

CREATE TABLE ta_forfait_service (
	id_service INT NOT NULL,
	id_forfait INT NOT NULL,
    PRIMARY KEY (id_service, id_forfait),
    FOREIGN KEY (id_service) REFERENCES service(id),
    FOREIGN KEY (id_forfait) REFERENCES forfait(id)
);

CREATE TABLE type_exercice (
id INT NOT NULL AUTO_INCREMENT PRIMARY KEY,
nom VARCHAR(30)
);

CREATE TABLE exercice (
	id INT NOT NULL AUTO_INCREMENT PRIMARY KEY,
	id_type INTEGER NOT NULL,
	nom VARCHAR(30) NOT NULL,
	description VARCHAR(500),
	image VARCHAR(200),
	FOREIGN KEY (id_type) REFERENCES type_exercice(id)
);

CREATE TABLE plan_personnalise (
	id INT NOT NULL AUTO_INCREMENT PRIMARY KEY,
    identifiant_client VARCHAR(30) NOT NULL,
    FOREIGN KEY (identifiant_client) REFERENCES client(identifiant)
);

CREATE TABLE ta_exercice_plan_personnalise (
	id_plan INT NOT NULL,
	id_exercice INT NOT NULL,
    ordre INTEGER,
    FOREIGN KEY (id_plan) REFERENCES plan_personnalise(id),
    FOREIGN KEY (id_exercice) REFERENCES exercice(id)
);

CREATE TABLE ta_client_evenement (
	id_evenement VARCHAR(6) NOT NULL,
	id_client VARCHAR(30) NOT NULL,
    PRIMARY KEY (id_evenement, id_client),
    FOREIGN KEY (id_evenement) REFERENCES evenement(id),
    FOREIGN KEY (id_client) REFERENCES client(identifiant)
);

INSERT INTO forfait(id, nom, prix, description, duree, heure_debut_semaine, heure_fin_semaine, heure_debut_fds, heure_fin_fds) VALUES (1,'Winnie', 100, 'Le plus petit forfait. Vous avez droit que au gym de base',1,8,17,8,17);
INSERT INTO forfait(id, nom, prix, description, duree, heure_debut_semaine, heure_fin_semaine, heure_debut_fds, heure_fin_fds) VALUES (2,'Ariel', 150, 'Accès au gym de base et à la piscine.',6,8,22,11,17);
INSERT INTO forfait(id, nom, prix, description, duree, heure_debut_semaine, heure_fin_semaine, heure_debut_fds, heure_fin_fds) VALUES (3,'Prince Charmant', 200, 'Accès à tous les jours de la semaine.',12,8,22,8,20);
INSERT INTO forfait(id, nom, prix, description, duree, heure_debut_semaine, heure_fin_semaine, heure_debut_fds, heure_fin_fds) VALUES (4,'Hercule', 240, 'Notre plus gros forfait, acces à presque tout',12,8,22,8,20);


INSERT INTO client(identifiant, id_forfait, nom, prenom, date_naissance, courriel,telephone, mot_de_passe) VALUES ('george1',1, 'Gonin','Georges', '2001-07-01','george@hotmail.ca', '8102319302', 'abc123');
INSERT INTO client(identifiant, id_forfait, nom, prenom, date_naissance, courriel,telephone, mot_de_passe) VALUES ('paul1',3, 'Joliette','Ginette', '1998-03-04','paul@hotmail.ca', '8102313257', 'abc123');
INSERT INTO client(identifiant, id_forfait, nom, prenom, date_naissance, courriel,telephone, mot_de_passe) VALUES ('Jean1 ',1, 'Paul','Georges', '1953-10-01','jean@hotmail.ca', '8102318228', 'abc123');
INSERT INTO client(identifiant, id_forfait, nom, prenom, date_naissance, courriel,telephone, mot_de_passe) VALUES ('Marie1 ',2, 'Boisvert','Georges', '2000-11-01','marie@hotmail.ca', '8102311121', 'abc123');
INSERT INTO client(identifiant, id_forfait, nom, prenom, date_naissance, courriel,telephone, mot_de_passe) VALUES ('Pierre1',3, 'Lurft','Georges', '1978-09-01','pierre1@hotmail.ca', '8102319032', 'abc123');

INSERT INTO service (nom, description) VALUES ('Piscine', 'Acces à la piscine');
INSERT INTO service (nom, description) VALUES ('Salle de relaxation', 'Acces à la salle de relaxation');
INSERT INTO service (nom, description) VALUES ('Spinning', 'Acces à la salle de spinning');
INSERT INTO service (nom, description) VALUES ('Crossfit', 'Acces à la salle pour le crossfit');
INSERT INTO service (nom, description) VALUES ('Massage', 'Accès aux massages de nos spécialistes');
INSERT INTO service (nom, description) VALUES ('Sauna', 'Accès à notre au sauna');

INSERT INTO ta_forfait_service(id_forfait, id_service) VALUES (2, 1);
INSERT INTO ta_forfait_service(id_forfait, id_service) VALUES (3, 1);
INSERT INTO ta_forfait_service(id_forfait, id_service) VALUES (3, 2);
INSERT INTO ta_forfait_service(id_forfait, id_service) VALUES (3, 3);
INSERT INTO ta_forfait_service(id_forfait, id_service) VALUES (3, 4);
INSERT INTO ta_forfait_service(id_forfait, id_service) VALUES (4, 1);
INSERT INTO ta_forfait_service(id_forfait, id_service) VALUES (4, 2);
INSERT INTO ta_forfait_service(id_forfait, id_service) VALUES (4, 3);
INSERT INTO ta_forfait_service(id_forfait, id_service) VALUES (4, 4);

INSERT INTO plan_personnalise(identifiant_client) VALUES ('george1');
INSERT INTO plan_personnalise(identifiant_client) VALUES ('paul1');
INSERT INTO plan_personnalise(identifiant_client) VALUES ('george1');

INSERT INTO type_exercice(nom) VALUES ('Haut du corps');
INSERT INTO type_exercice(nom) VALUES ('bas du corps');

INSERT INTO exercice(id_type, nom, description, image) VALUES (1, 'Pompe', 'Flexion des bras, puis extension des bras', 'img/pompe.png');
INSERT INTO exercice(id_type, nom, description, image) VALUES (1, 'Planche', 'Maintien du corps en position de planche', 'img/planche.png');
INSERT INTO exercice(id_type, nom, description, image) VALUES (2, 'Squat', 'Flexion puis extension des genous', 'img/squat.png');
INSERT INTO exercice(id_type, nom, description, image) VALUES (2, 'Fente', 'Flexion des jambes, puis extension', 'img/fente.png');

INSERT INTO ta_exercice_plan_personnalise(id_plan, id_exercice, ordre) VALUES (1, 4, 1);
INSERT INTO ta_exercice_plan_personnalise(id_plan, id_exercice, ordre) VALUES (1, 2, 1);
INSERT INTO ta_exercice_plan_personnalise(id_plan, id_exercice, ordre) VALUES (2, 1,1);
INSERT INTO ta_exercice_plan_personnalise(id_plan, id_exercice, ordre) VALUES (2, 2,2);
INSERT INTO ta_exercice_plan_personnalise(id_plan, id_exercice, ordre) VALUES (2, 3,1);
INSERT INTO ta_exercice_plan_personnalise(id_plan, id_exercice, ordre) VALUES (2, 4,2);
INSERT INTO ta_exercice_plan_personnalise(id_plan, id_exercice, ordre) VALUES (3, 3,2);


INSERT INTO poste_employe(nom) VALUES ('entraineur');
INSERT INTO poste_employe(nom) VALUES ('nutritioniste');
INSERT INTO poste_employe(nom) VALUES ('massothérapeutre');
INSERT INTO poste_employe(nom) VALUES ('administrateur');

INSERT INTO etat_employe(etat) VALUES ('actif');
INSERT INTO etat_employe(etat) VALUES ('vacance');
INSERT INTO etat_employe(etat) VALUES ('inactif');

INSERT INTO type_evenement(nom, description) VALUES ('Cours','Les clients sinscrivent dans un cours de groupe');
INSERT INTO type_evenement(nom, description) VALUES ('Rendez-Vous','Le client prend un rendez-vous avec un employe pour plusieurs raison, par exemple se faire aider par un nutritioniste');

INSERT INTO jour_semaine(nom_jour) VALUES ('Dimanche');
INSERT INTO jour_semaine(nom_jour) VALUES ('Lundi');
INSERT INTO jour_semaine(nom_jour) VALUES ('Mardi');
INSERT INTO jour_semaine(nom_jour) VALUES ('Mercredi');
INSERT INTO jour_semaine(nom_jour) VALUES ('Jeudi');
INSERT INTO jour_semaine(nom_jour) VALUES ('Vendredi');
INSERT INTO jour_semaine(nom_jour) VALUES ('Samedi');

INSERT INTO modele_cours(nom) VALUES ('Zoomba');
INSERT INTO modele_cours(nom) VALUES ('Spinning');
INSERT INTO modele_cours(nom) VALUES ('Natation avec Ariel');
INSERT INTO modele_cours(nom) VALUES ('Relaxation avec Winnie');
INSERT INTO modele_cours(nom) VALUES ('Introduction a la musculation avec Hercules');


INSERT INTO employe(identifiant, nom, prenom, courriel, telephone, id_poste, id_etat) VALUES ('jocelyn1','Lapalme', 'Jocelyn', 'jocelyn@gmail.com', 819999203, 1, 1);
INSERT INTO employe(identifiant, nom, prenom, courriel, telephone, id_poste, id_etat) VALUES ('hugo1','Boucher', 'Hugo', 'hugo@gmail.com', 819382202, 2, 1);
INSERT INTO employe(identifiant, nom, prenom, courriel, telephone, id_poste, id_etat) VALUES ('elsa1','Snow', 'Elsa', 'elsa@gmail.com', 5324546636, 3, 1);
INSERT INTO employe(identifiant, nom, prenom, courriel, telephone, id_poste, id_etat) VALUES ('raiponse1','Hair', 'Raiponse', 'raiponse@gmail.com', 819374202, 1, 2);
INSERT INTO employe(identifiant, nom, prenom, courriel, telephone, id_poste, id_etat) VALUES ('belle1','Bete', 'Belle', 'belle@gmail.com', 819345102, 2, 3);
INSERT INTO employe(identifiant, nom, prenom, courriel, telephone, id_poste, id_etat) VALUES ('nemo1','Doris', 'Nemo', 'nemo@gmail.com', 264321115, 2, 3);


INSERT INTO evenement(id, id_modele, id_type, id_jour, identifiant_employe, date, heure, duree, prix) VALUES ('C00001', 1, 1, 2, 'jocelyn1', '2019-05-01', 14, 1, 5);
INSERT INTO evenement(id, id_modele, id_type, id_jour, identifiant_employe, date, heure, duree, prix) VALUES ('C00002', 3, 1, 5, 'jocelyn1', '2019-05-03', 14, 2, 10);
INSERT INTO evenement(id, id_modele, id_type, id_jour, identifiant_employe, date, heure, duree, prix) VALUES ('R00001', null, 2, 2, 'hugo1', '2019-04-30', 10, 1, 20);
INSERT INTO evenement(id, id_modele, id_type, id_jour, identifiant_employe, date, heure, duree, prix) VALUES ('R00002', null, 2, 2, 'elsa1', '2019-05-01', 18, 1, 20);


INSERT INTO ta_client_evenement(id_client, id_evenement) VALUES ('george1','C00001');
INSERT INTO ta_client_evenement(id_client, id_evenement) VALUES ('paul1','C00001');
INSERT INTO ta_client_evenement(id_client, id_evenement) VALUES ('Jean1','C00001');
INSERT INTO ta_client_evenement(id_client, id_evenement) VALUES ('Marie1','C00001');
INSERT INTO ta_client_evenement(id_client, id_evenement) VALUES ('paul1','C00002');
INSERT INTO ta_client_evenement(id_client, id_evenement) VALUES ('Jean1','R00001');
INSERT INTO ta_client_evenement(id_client, id_evenement) VALUES ('Marie1','R00002');


INSERT INTO etat_forfait_client(id,	etat, description) VALUES (1, 'En cours', 'Le forfait est toujours valide');
INSERT INTO etat_forfait_client(id,	etat,	description) VALUES (2, 'Terminé', 'Le forfait a expiré');
INSERT INTO etat_forfait_client(id,	etat,	description) VALUES (3, 'Annulé', 'Le forfait a été annulé');

INSERT INTO ta_forfait_client(identifiant_client,	id_forfait, id_etat,	date_debut,	date_echeance) VALUES ('george1',1, 1, '2019-04-29', '2019-05-29');
INSERT INTO ta_forfait_client(identifiant_client,	id_forfait,	id_etat,	date_debut,	date_echeance) VALUES ('paul1',1, 2, '2019-03-20', '2019-04-20');
INSERT INTO ta_forfait_client(identifiant_client,	id_forfait,	id_etat,	date_debut,	date_echeance) VALUES ('Marie1',3, 1, '2019-04-25', '2019-05-25');
