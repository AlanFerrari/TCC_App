<?php 
   //obtendo a conexão com o banco de dados
   require_once 'DbConnect.php';
   
   //an array to display response
   $response = array();

   //se for uma chamada de API
   //isso significa que um parâmetro get chamado api call está definido na URL
   //e com este parâmetro estamos concluindo que é uma chamada de API 
   if(isset($_GET['apicall'])){

   switch($_GET['apicall']){
   
   case 'cadastro':
   //verificando se os parâmetros requeridos estão disponíveis ou não 
   if(EssesParametrosEstaoDisponiveis(array('usuario','email','cidade','estado','senha','numero_oab','telefone_cel'))){

      //pega os valores
      $usuario = $_POST['usuario']; 
      $email = $_POST['email']; 
      $cidade = $_POST['cidade'];
      $estado = $_POST['estado'];
      $senha = md5($_POST['senha']);
      $numero_oab = $_POST['numero_oab'];
      $telefone_cel = $_POST['telefone_cel']; 
      
      //verificando se o usuário já existe com este email
      //pois o email deve ser único para cada usuário 
      $stmt = $conn->prepare("SELECT id FROM advogados WHERE email = ?");
      $stmt->bind_param("s", $email);
      $stmt->execute();
      $stmt->store_result();
      
      //se o usuário já existe no banco de dados 
      if($stmt->num_rows > 0){
      $response['error'] = true;
      $response['message'] = 'Usuario ja esta cadastrado';
      $stmt->close();
      }else{
      
      //se o usuário for novo criando uma consulta de inserção 
      $stmt = $conn->prepare("INSERT INTO advogados (usuario, email, cidade, estado, senha, numero_oab, telefone_cel) VALUES (?, ?, ?, ?, ?, ?, ?)");
      $stmt->bind_param("sssssss", $usuario, $email, $cidade, $estado, $senha, $numero_oab, $telefone_cel);
      
      //se o usuário for adicionado com sucesso ao banco de dados
      if($stmt->execute()){
      
      //buscando o usuário de volta
      $stmt = $conn->prepare("SELECT id, id, usuario, email, cidade, estado, numero_oab, telefone_cel FROM advogados WHERE email = ?"); 
      $stmt->bind_param("s",$email);
      $stmt->execute();
      $stmt->bind_result($userid, $id, $usuario, $email, $cidade, $estado, $numero_oab, $telefone_cel);
      $stmt->fetch();
      
      $advogados = array(
      'id'=>$id, 
      'usuario'=>$usuario, 
      'email'=>$email,
      'cidade'=>$cidade,
      'estado'=>$estado,
      'numero_oab'=>$numero_oab,
      'telefone_cel'=>$telefone_cel
      );
      
      $stmt->close();
      
      //adicionando os dados do usuário em resposta
      $response['error'] = false; 
      $response['message'] = 'Usuario cadastrado com sucesso'; 
      $response['advogados'] = $advogados; 
      }
      }
      
      }else{
      $response['error'] = true; 
      $response['message'] = 'Os parametros necessarios nao estao disponiveis'; 
      }

   break; 

   case 'login':
   //para login precisamos do nome de usuário e senha 
   if(EssesParametrosEstaoDisponiveis(array('email', 'senha'))){
      //pega valores
      $email = $_POST['email'];
      $senha = md5($_POST['senha']); 
      
      //criando a consulta
      $stmt = $conn->prepare("SELECT id, usuario, email, cidade, estado, numero_oab, telefone_cel FROM advogados WHERE email = ? AND senha = ?");
      $stmt->bind_param("ss",$email, $senha);
      
      $stmt->execute();
      
      $stmt->store_result();

      //se o usuário existir com as credenciais fornecidas 
      if($stmt->num_rows > 0){
      
      $stmt->bind_result($id, $usuario, $email, $cidade, $estado, $numero_oab, $telefone_cel);
      $stmt->fetch();
      
      $advogados = array(
      'id'=>$id, 
      'usuario'=>$usuario, 
      'email'=>$email,
      'cidade'=>$cidade,
      'estado'=>$estado,
      'numero_oab'=>$numero_oab,
      'telefone_cel'=>$telefone_cel
      );
      
      $response['error'] = false; 
      $response['message'] = 'Logado com sucesso'; 
      $response['advogados'] = $advogados; 
      }else{
      //se o usuário não foi encontrado 
      $response['error'] = false; 
      $response['message'] = 'Nome ou Senha invalidos';
      }
      } 
   
   break; 
   
   default: 
   $response['error'] = true; 
   $response['message'] = 'Invalida a operacao de chamada';
   }
   
   }else{
   //se não chamar a api
   //enviando valores apropriados para array de resposta 
   $response['error'] = true; 
   $response['message'] = 'Chamada de API invalida';
   }
   
   //exibindo a resposta na estrutura json
   echo json_encode($response);

   //função validando todos os parâmetros que estão disponíveis 
   //passaremos os parâmetros necessários para esta função
   function EssesParametrosEstaoDisponiveis($params){

      //percorrendo todos os parâmetros
      foreach($params as $param){
      //se o parâmetro não estiver disponível
      if(!isset($_POST[$param])){
      //retorno é false 
      return false; 
      }
      }
      //retorna true se todos os parâmetros estiverem disponíveis 
      return true; 
      }