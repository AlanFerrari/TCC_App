<?php

require_once '../includes/DbOperations.php';

$response = array();

    if($_SERVER['REQUEST_METHOD'] == 'POST'){

        if(
            isset($_POST['nome']) and
                isset($_POST['email']) and
                    isset($_POST['telefone']) and
                        isset($_POST['estado']) and
                            isset($_POST['cidade']) and
                                isset($_POST['numeroOAB']) and
                                    isset($_POST['senha'])
        ){

                $db = new DbOperations();

                $result = $db->createUser(
                    $_POST['nome'],
                    $_POST['email'],
                    $_POST['telefone'],
                    $_POST['estado'],
                    $_POST['cidade'],
                    $_POST['numeroOAB'],
                    $_POST['senha']
                );

                if($result == 1){
                    $response['error'] = false;
                    $response['message'] = "Usuário registrado com sucesso";
                } elseif($result == 2) {
                    $response['error'] = true;
                    $response['message'] = "Algun erro aconteceu, tente novamente";
                } elseif($result == 0){
                    $response['error'] = true;
                    $response['message'] = "Já existe um usuário cadastrado com este email";
                }
                
        } else {
            $response['error'] = true;
            $response['message'] = "Campos obrigatórios estão ausentes";
        }
    } else {
        $response['error'] = true;
        $response['message'] = "Request inválida";
    }

    echo json_encode($response, JSON_UNESCAPED_UNICODE);