CREATE DATABASE IF NOT EXISTS meusdireitos;
USE meusdireitos;

CREATE TABLE advogados (
  id INT NOT NULL PRIMARY KEY AUTO_INCREMENT,
  usuario VARCHAR(50) NOT NULL,
  email VARCHAR(35) NOT NULL,
  cidade VARCHAR(50) NOT NULL,
  estado VARCHAR(20) NOT NULL,
  senha TEXT NOT NULL,
  numeroOAB VARCHAR(30) NOT NULL,
  telefone VARCHAR(15) NOT NULL,
  areaAtuacao VARCHAR(50) NULL,
  fotoPerfil INT NULL,
  bibliografia VARCHAR(500) NULL
);