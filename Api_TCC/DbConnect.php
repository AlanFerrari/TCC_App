<?

    $servername = "localhost";
    $username = "TCC_meusDireitos";
    $password = "123456";
    $database = "meusdireitos";
    
    //criando um novo objeto de conexão usando mysqli 
    $conn = new mysqli($servername, $username, $password, $database);
    
    //se houver algum erro na conexão com o banco de dados
    //com die vamos parar a execução posterior exibindo uma mensagem causando o erro
    if ($conn->connect_error) {
        die("A conexão falhou: " . $conn->connect_error);
    }
 
?>