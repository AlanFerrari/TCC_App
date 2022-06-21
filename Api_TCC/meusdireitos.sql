CREATE DATABASE IF NOT EXISTS meusdireitos;
USE meusdireitos;

CREATE TABLE advogados (
  id INT NOT NULL PRIMARY KEY AUTO_INCREMENT,
  nome VARCHAR(50) NOT NULL,
  email VARCHAR(35) NOT NULL,
  cidade VARCHAR(50) NOT NULL,
  estado VARCHAR(20) NOT NULL,
  senha VARCHAR(32) NOT NULL,
  numero_oab VARCHAR(30) NOT NULL,
  telefone_cel VARCHAR(15) NOT NULL,
  area_atuacao VARCHAR(50) NULL,
  foto_perfil INT NULL,
  bibliografia VARCHAR(500) NULL
);