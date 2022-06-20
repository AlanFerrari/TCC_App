<?php

    class DbOperations{

        private $con;

        function __construct()
        {
            require_once dirname(__FILE__).'/DbConnect.php';

            $db = new DbConnect();

            $this->con = $db->connect();
        }

        public function createUser($nome, $email, $telefone, $estado, $cidade, $numeroOAB, $password){
            if($this->isUserExixt($email)){
                return 0;
            } else {
                $senha = md5($password);
                $stmt = $this->con->prepare("INSERT INTO `advogados` (`idAdvogado`, `nome`, `email`, `telefone`, `estado`, `cidade`, `numeroOAB`, `senha`) VALUES (NULL, ?, ?, ?, ?, ?, ?, ?);");
                $stmt->bind_param("sssssss",$nome, $email, $telefone, $estado, $cidade, $numeroOAB, $senha);

                if($stmt->execute()){
                    return 1;
                } else {
                    return 2;
                }
            }
        }

        private function isUserExixt(){
            $stmt = $this->con->prepare("SELECT idAdvogado FROM advogados WHERE email = ?");
            $stmt->bind_param("s", $email);
            $stmt->execute();
            $stmt->store_result();
            return $stmt->num_rows > 0;
        }
    }