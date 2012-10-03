

CREATE TABLE appartient_indice (
  ISIN varchar(255) default NULL,
  LIBELLE_INDICE varchar(255) default NULL,
  NOM_ENTREPRISE varchar(255) default NULL
);
CREATE TABLE categorie (
  ID_CATEGORIE varchar(255) NOT NULL,
  LIBELLE_CATEGORIE varchar(255) default NULL,
  PRIMARY KEY  (ID_CATEGORIE)
);
CREATE TABLE correspond (
  ID_CATEGORIE varchar(255) NOT NULL,
  MOT_CLE varchar(255) NOT NULL,
  PRIMARY KEY  (ID_CATEGORIE,MOT_CLE)
);
CREATE TABLE entreprise (
  ISIN varchar(255) NOT NULL,
  NOM varchar(255) default NULL,
  ZONE_INVESTISSEMENT varchar(255) default NULL,
  COMPARTIMENT varchar(255) default NULL,
  CHIFFRE_AFFAIRE int default NULL,
  DERNIER decimal(10,3) default NULL,
  VOLUME int default NULL,
  ELIGIBILITE varchar(255) default NULL,
  CAPITAL number,
  CAPITALISATION number,
  NOTE_RESPONSABILITE_ECO int default NULL,
  NOTE_RESPONSABILITE_ENVIRON int default NULL,
  NOTE_RESPONSABILITE_SOCIALE int default NULL,
  PLAN_EPARGNE varchar(255) default NULL,
  MNEMO varchar(255) default NULL,
  SITE_INTERNET varchar(255) CONSTRAINT uk_site_internet UNIQUE, 
  ID_SOUS_SECTEUR varchar(255) default NULL,
  ID_PAYS varchar(255) default NULL,
  PRIMARY KEY (ISIN)
);
CREATE TABLE fait_apparaitre (
  URL varchar(255) NOT NULL,
  MOT_CLE varchar(255) NOT NULL,
  nombre_occurences int default NULL,
  pos varchar(255)
);
CREATE TABLE indice (
  LIBELLE_INDICE varchar(255) NOT NULL,
  PRIMARY KEY  (LIBELLE_INDICE)
);
CREATE TABLE industrie (
  ID_INDUSTRIE varchar(255) NOT NULL,
  LIBELLE_INDUSTRIE varchar(255) default NULL,
  PRIMARY KEY  (ID_INDUSTRIE)
);
CREATE TABLE mot_cle (
  MOT_CLE varchar(255) NOT NULL,
  LIBELLE_MOT_CLE varchar(255) default NULL,
  PRIMARY KEY  (MOT_CLE)
);
CREATE TABLE page_web (
  URL varchar(255) NOT NULL,
  URL_EST_ACCEDE_PAR varchar(255) default NULL,
  PROFONDEUR int, 
  PRIMARY KEY  (URL)
);
CREATE TABLE pays (
  ID_PAYS varchar(255) NOT NULL,
  NOM_CAPITALE varchar(255) default NULL,
  PRIMARY KEY  (ID_PAYS)
);
CREATE TABLE secteur (
  ID_SECTEUR varchar(255) NOT NULL,
  LIBELLE_SECTEUR varchar(255) default NULL,
  ID_SUPER_SECTEUR varchar(255) default NULL,
  PRIMARY KEY  (ID_SECTEUR)
);
CREATE TABLE sous_secteur (
  ID_SOUS_SECTEUR varchar(255) NOT NULL,
  LIBELLE_SOUS_SECTEUR varchar(255) default NULL,
  ID_SECTEUR varchar(255) default NULL,
  PRIMARY KEY  (ID_SOUS_SECTEUR)
);
CREATE TABLE super_secteur (
  ID_SUPER_SECTEUR varchar(255) NOT NULL,
  LIBELLE_SUPER_SECTEUR varchar(255) default NULL,
  ID_INDUSTRIE varchar(255) default NULL,
  PRIMARY KEY  (ID_SUPER_SECTEUR)
);

ALTER TABLE appartient_indice
ADD CONSTRAINT appartient_indice_ibfk_1 FOREIGN KEY (ISIN) REFERENCES entreprise (ISIN);
ALTER TABLE appartient_indice
  ADD CONSTRAINT appartient_indice_ibfk_2 FOREIGN KEY (LIBELLE_INDICE) REFERENCES indice (LIBELLE_INDICE);

ALTER TABLE correspond
ADD CONSTRAINT correspond_ibfk_1 FOREIGN KEY (ID_CATEGORIE) REFERENCES categorie (ID_CATEGORIE);
ALTER TABLE correspond
  ADD CONSTRAINT correspond_ibfk_2 FOREIGN KEY (MOT_CLE) REFERENCES mot_cle (MOT_CLE);

ALTER TABLE entreprise
ADD CONSTRAINT entreprise_ibfk_1 FOREIGN KEY (ID_SOUS_SECTEUR) REFERENCES sous_secteur (ID_SOUS_SECTEUR);
ALTER TABLE entreprise
  ADD CONSTRAINT entreprise_ibfk_2 FOREIGN KEY (ID_PAYS) REFERENCES pays (ID_PAYS);

/*
ALTER TABLE fait_apparaitre
ADD CONSTRAINT fait_apparaitre_ibfk_1 FOREIGN KEY (URL) REFERENCES page_web (URL);
ALTER TABLE fait_apparaitre
  ADD CONSTRAINT fait_apparaitre_ibfk_2 FOREIGN KEY (MOT_CLE) REFERENCES mot_cle (MOT_CLE);
*/

ALTER TABLE page_web
ADD CONSTRAINT page_web_ibfk_1 FOREIGN KEY (URL_EST_ACCEDE_PAR) REFERENCES entreprise (SITE_INTERNET);

ALTER TABLE secteur
ADD CONSTRAINT secteur_ibfk_1 FOREIGN KEY (ID_SUPER_SECTEUR) REFERENCES super_secteur (ID_SUPER_SECTEUR);

ALTER TABLE sous_secteur
ADD CONSTRAINT sous_secteur_ibfk_1 FOREIGN KEY (ID_SECTEUR) REFERENCES secteur (ID_SECTEUR);

ALTER TABLE super_secteur
ADD CONSTRAINT super_secteur_ibfk_1 FOREIGN KEY (ID_INDUSTRIE) REFERENCES industrie (ID_INDUSTRIE);

commit;
