package br.com.etecia.meus_direitos;

public class Api {

    private static final String ROOT_URL = "http://localhost/Api_TCC/v1/Api.php?apicall=";

    public static final String URL_CREATE_USER = ROOT_URL + "createUser";
    public static final String URL_READ_USERS = ROOT_URL + "getUsers";
    public static final String URL_UPDATE_USER = ROOT_URL + "updateUser";
    public static final String URL_LOGIN = ROOT_URL + "login";
}
