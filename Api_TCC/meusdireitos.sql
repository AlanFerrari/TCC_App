CREATE DATABASE IF NOT EXISTS meusdireitos;
USE meusdireitos;

CREATE TABLE advogados 
(
  idAdvogado INT NOT NULL PRIMARY KEY AUTO_INCREMENT,
  nome VARCHAR(50) NOT NULL,
  email VARCHAR(35) NOT NULL,
  telefone VARCHAR(15) NOT NULL,
  estado VARCHAR(20) NOT NULL,
  cidade VARCHAR(50) NOT NULL,
  numeroOAB VARCHAR(30) NOT NULL,
  senha TEXT NOT NULL
);

CREATE TABLE perfilAdvogados 
(
  idPerfil INT NOT NULL PRIMARY KEY AUTO_INCREMENT,
  idAdvogado INT NOT NULL,
  areaAtuacao VARCHAR(50) NOT NULL,
  fotoPerfil INT NOT NULL,
  bibliografia TEXT NOT NULL
  FOREIGN KEY (idAdvogado) REFERENCES advogados(idAdvogado)
);