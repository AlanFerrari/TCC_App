<?php 

	require_once '../includes/DbOperation.php';

	function isTheseParametersAvailable($params){
	
		$available = true; 
		$missingparams = ""; 
		
		foreach($params as $param){
			if(!isset($_POST[$param]) || strlen($_POST[$param])<=0){
				$available = false; 
				$missingparams = $missingparams . ", " . $param; 
			}
		}		
		
		if(!$available){
			$response = array(); 
			$response['error'] = true; 
			$response['message'] = 'Parâmetros ' . substr($missingparams, 1, strlen($missingparams)) . ' faltando';			
		
			echo json_encode($response, JSON_UNESCAPED_UNICODE);			
		
			die();
		}
	}	
	
	$response = array();	

	if(isset($_GET['apicall'])){
		
		switch($_GET['apicall']){
	
			case 'createUser':
				
				isTheseParametersAvailable(array('nome','email','telefone','estado', 'cidade', 'numeroOAB', 'senha'));
				
				$db = new DbOperation();
				
				$result = $db->createUser(
					$_POST['nome'],
					$_POST['email'],
					$_POST['telefone'],
					$_POST['estado'],
					$_POST['cidade'],
					$_POST['numeroOAB'],
					$_POST['senha']
				);
							
				if($result){
					
					$response['error'] = false;
					
					$response['message'] = 'Usuário adicionado com sucesso';
					
					$response['heroes'] = $db->getUsers();
				}else{
					
					$response['error'] = true; 
				
					$response['message'] = 'Algum erro ocorreu por favor tente novamente';
				}				
			break; 			
		
			case 'getUsers':
				$db = new DbOperation();
				$response['error'] = false; 
				$response['message'] = 'Pedido concluído com sucesso';
				$response['heroes'] = $db->getUsers();
			break;			
		
			case 'updateUser':
				isTheseParametersAvailable(array('id','nome','email','telefone','estado', 'cidade', 'numeroOAB'));
				$db = new DbOperation();
				$result = $db->updateUser(
					$_POST['id'],
					$_POST['nome'],
					$_POST['email'],
					$_POST['telefone'],
					$_POST['estado'],
					$_POST['cidade'],
					$_POST['numeroOAB']
				);
				
				if($result){
					$response['error'] = false; 
					$response['message'] = 'Usuário atualizado com sucesso';
					$response['heroes'] = $db->getUsers();
				}else{
					$response['error'] = true; 
					$response['message'] = 'Algum erro ocorreu por favor tente novamente';
				}
			break; 
			
			
			case 'login':
				//para login precisamos do nome de usuário e senha 
				if(isTheseParametersAvailable(array('email', 'senha'))){
				   //pega valores
				   $email = $_POST['email'];
				   $senha = $_POST['senha']; 
				   
				   //criando a consulta
				   $stmt = $con->prepare("SELECT id, nome, email, telefone, estado, cidade, numeroOAB FROM advogados WHERE email = ? AND senha = ?");
				   $stmt->bind_param("ss",$email, $senha);
				   
				   $stmt->execute();
				   
				   $stmt->store_result();
	
				   //se o usuário existir com as credenciais fornecidas 
				   if($stmt->num_rows > 0){
				   
					  $stmt->bind_result($id, $nome, $email, $telefone, $estado, $cidade, $numeroOAB);
					  $stmt->fetch();
					  
					  $advogados = array(
						 'id'=>$id, 
						 'nome'=>$nome, 
						 'email'=>$email,
						 'telefone'=>$telefone,
						 'estado'=>$estado,
						 'cidade'=>$cidade,
						 'numeroOAB'=>$numeroOAB
					  );
				   
					  $response['error'] = true; 
					  $response['message'] = 'Logado com sucesso'; 
					  $response['advogados'] = $advogados; 
				   } else {
				   //se o usuário não foi encontrado 
				   $response['error'] = false; 
				   $response['message'] = 'Nome ou Senha inválidos';
				   }
				} 				
				break; 
		}
		
	}else{
		 
		$response['error'] = true; 
		$response['message'] = 'Chamada de API inválida';
	}
	
	echo json_encode($response, JSON_UNESCAPED_UNICODE);