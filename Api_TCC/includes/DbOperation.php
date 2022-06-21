<?php
 
class DbOperation
{    
    private $con; 
 
    function __construct()
    {  
        require_once dirname(__FILE__).'/DbConnect.php';
      
        $db = new DbConnect();
 
        $this->con = $db->connect();
    }	
	
	function createUser ($nome, $email, $telefone, $estado, $cidade, $numeroOAB, $senha){
		$stmt = $this->con->prepare("INSERT INTO `advogados` (`id`, `nome`, `email`, `telefone`, `estado`, `cidade`, `numeroOAB`, `senha`) VALUES (NULL, ?, ?, ?, ?, ?, ?, ?");
		$stmt->bind_param("sssssss", $nome, $email, $telefone, $estado, $cidade, $numeroOAB, $senha);
		if($stmt->execute())
			return true; 
		return false; 
	}
	
	function getUsers(){
		$stmt = $this->con->prepare("SELECT id, nome, email, telefone, estado, cidade, numeroOAB FROM advogados");
		$stmt->execute();
		$stmt->bind_result($id, $nome, $email, $telefone, $estado, $cidade, $numeroOAB);
		
		$advogados = array(); 
		
		while($stmt->fetch()){
			$user  = array();
			$user['id'] = $id; 
			$user['nome'] = $nome; 
			$user['email'] = $email; 
			$user['telefone'] = $telefone; 
			$user['estado'] = $estado; 
			$user['cidade'] = $cidade; 
			$user['numeroOAB'] = $numeroOAB; 
			
			array_push($advogados, $user); 
		}
		
		return $advogados; 
	}	
	
	function updateUser($id, $nome, $email, $telefone, $estado, $cidade, $numeroOAB){
		$stmt = $this->con->prepare("UPDATE advogados SET nome = ?, email = ?, telefone = ?, estado = ?, cidade = ?, numeroOAB = ? WHERE id = ?");
		$stmt->bind_param("issssss", $id, $nome, $email, $telefone, $estado, $cidade, $numeroOAB);
		if($stmt->execute())
			return true; 
		return false; 
	}	
}