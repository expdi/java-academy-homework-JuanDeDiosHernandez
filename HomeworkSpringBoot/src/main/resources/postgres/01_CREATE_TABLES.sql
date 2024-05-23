set role larku;

SET statement_timeout = 0;
SET lock_timeout = 0;
SET client_encoding = 'UTF8';
SET standard_conforming_strings = on;
SET check_function_bodies = false;
SET client_min_messages = warning;
SET default_tablespace = '';
SET default_with_oids = false;

DROP TABLE IF EXISTS adopters;
DROP TABLE IF EXISTS animals;


CREATE TABLE Adopters (
    adopter_id serial primary key NOT NULL,
    name varchar(150) NOT NULL,
    phone varchar(20),
    date_adoption date,
	animal_id int
);

CREATE TABLE Animals (
    animal_id serial primary key NOT NULL,
    type_pet varchar(50) NOT NULL,
    pet_name varchar(150),
    pet_breed varchar(150)
);


CREATE UNIQUE INDEX IDX_adopter_id ON Adopters (adopter_id ASC);

CREATE UNIQUE INDEX IDX_animal_id ON Animals (animal_id ASC);


ALTER TABLE Adopters ADD CONSTRAINT FK_Adopters_Animals FOREIGN KEY(animal_id) REFERENCES Animals (animal_id);

INSERT INTO Animals (type_pet, pet_name, pet_breed) VALUES ('CAT', 'Fuchsia', 'Aegean');
INSERT INTO Animals (type_pet, pet_name, pet_breed) VALUES ('CAT', 'Alsha', 'Bengal');
INSERT INTO Animals (type_pet, pet_name, pet_breed) VALUES ('CAT', 'Kobe', 'Bombay');
INSERT INTO Animals (type_pet, pet_name, pet_breed) VALUES ('CAT', 'Newton', 'Cyprus');
INSERT INTO Animals (type_pet, pet_name, pet_breed) VALUES ('CAT', 'Omen', 'Himalayan');
INSERT INTO Animals (type_pet, pet_name, pet_breed) VALUES ('DOG', 'Valentina', 'Beagle');
INSERT INTO Animals (type_pet, pet_name, pet_breed) VALUES ('DOG', 'Kayle', 'Akita');
INSERT INTO Animals (type_pet, pet_name, pet_breed) VALUES ('DOG', 'Siri', 'Chihuahua');
INSERT INTO Animals (type_pet, pet_name, pet_breed) VALUES ('DOG', 'Aster', 'Chow Chow');
INSERT INTO Animals (type_pet, pet_name, pet_breed) VALUES ('DOG', 'Maiki', 'Georgian Shepherd');
INSERT INTO Animals (type_pet, pet_name, pet_breed) VALUES ('TURTLE', 'Q-ball', 'Red-Eared Slider');
INSERT INTO Animals (type_pet, pet_name, pet_breed) VALUES ('TURTLE', 'Acadia', 'African Sideneck Turtle');
INSERT INTO Animals (type_pet, pet_name, pet_breed) VALUES ('TURTLE', 'Sonny', 'Eastern Box Turtle');
INSERT INTO Animals (type_pet, pet_name, pet_breed) VALUES ('TURTLE', 'Skye', 'Western Painted Turtle');
INSERT INTO Animals (type_pet, pet_name, pet_breed) VALUES ('TURTLE', 'Danu', 'Common Musk Turtle');

INSERT INTO Adopters (name, phone, date_adoption, animal_id) VALUES ('Jennifer Quiteria', '111-1111', CURRENT_DATE, 1);
INSERT INTO Adopters (name, phone, date_adoption, animal_id) VALUES ('Amelia Amarilis', '111-1112', CURRENT_DATE - 1, 2);
INSERT INTO Adopters (name, phone, date_adoption, animal_id) VALUES ('Arleth Salvador', '111-1113', CURRENT_DATE - 2, 3);
INSERT INTO Adopters (name, phone, date_adoption, animal_id) VALUES ('Andres Felipe', '111-1114', CURRENT_DATE - 3, 4);
INSERT INTO Adopters (name, phone, date_adoption, animal_id) VALUES ('Jesica Augusto', '111-1115', CURRENT_DATE - 4, 5);
INSERT INTO Adopters (name, phone, date_adoption, animal_id) VALUES ('Luisita Sandalio', '111-1116', CURRENT_DATE - 5, 6);
INSERT INTO Adopters (name, phone, date_adoption, animal_id) VALUES ('Custodia Carmelo', '111-1117', CURRENT_DATE - 6, 7);
INSERT INTO Adopters (name, phone, date_adoption, animal_id) VALUES ('Yamila Fidelia', '111-1118', CURRENT_DATE - 7, 8);
INSERT INTO Adopters (name, phone, date_adoption, animal_id) VALUES ('Jhon Celestino', '111-1119', CURRENT_DATE - 8, 9);
INSERT INTO Adopters (name, phone, date_adoption, animal_id) VALUES ('Roman Placido', '111-1120', CURRENT_DATE - 9, 10);
INSERT INTO Adopters (name, phone, date_adoption, animal_id) VALUES ('Ernestina Arturo', '111-1121', CURRENT_DATE - 10, 11);
INSERT INTO Adopters (name, phone, date_adoption, animal_id) VALUES ('Lujan Amador', '111-1122', CURRENT_DATE - 11, 12);
INSERT INTO Adopters (name, phone, date_adoption, animal_id) VALUES ('Ileana Rogelio', '111-1123', CURRENT_DATE - 12, 13);
INSERT INTO Adopters (name, phone, date_adoption, animal_id) VALUES ('Marisa Encarna', '111-1124', CURRENT_DATE - 13, 14);
INSERT INTO Adopters (name, phone, date_adoption, animal_id) VALUES ('Mara Albino', '111-1125', CURRENT_DATE - 14, 15);
