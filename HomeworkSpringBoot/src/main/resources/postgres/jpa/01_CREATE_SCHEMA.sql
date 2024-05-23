DROP TABLE IF EXISTS animal;
DROP TABLE IF EXISTS adopter;

CREATE TABLE Adopter (
    id serial primary key NOT NULL,
    name varchar(150) NOT NULL,
    phone varchar(20),
    date_adoption date
);

CREATE TABLE Animal (
    id serial primary key NOT NULL,
    type_pet varchar(50) NOT NULL,
    pet_name varchar(150),
    pet_breed varchar(150),
    adopter_id  int NOT NULL
);

CREATE UNIQUE INDEX IDX_adopter_id ON Adopter (id ASC);

CREATE UNIQUE INDEX IDX_animal_id ON Animal (id ASC);

ALTER TABLE Animal
    ADD CONSTRAINT FK_Animal_adopter_id FOREIGN KEY (adopter_id)
        REFERENCES Adopter (id);
