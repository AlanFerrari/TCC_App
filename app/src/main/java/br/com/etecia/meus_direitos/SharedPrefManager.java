package br.com.etecia.meus_direitos;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;

import br.com.etecia.meus_direitos.objetos.PerfilUsuario;

//aqui para esta classe estamos usando um padrão singleton

public class SharedPrefManager {

    //as constantes
    private static final String SHARED_PREF_NAME = "simplifiedcodingsharedpref";
    private static final String KEY_USUARIO = "keyusuario";
    private static final String KEY_EMAIL = "keyemail";
    private static final String KEY_CIDADE = "keycidade";
    private static final String KEY_ESTADO = "keyestado";
    private static final String KEY_NUMERO_OAB = "keynumero_oab";
    private static final String KEY_TELEFONE = "keytelefone_cel";
    private static final String KEY_ID = "keyid";

    private static SharedPrefManager mInstance;
    private static Context mCtx;

    private SharedPrefManager(Context context) {
        mCtx = context;
    }

    public static synchronized SharedPrefManager getInstance(Context context) {
        if (mInstance == null) {
            mInstance = new SharedPrefManager(context);
        }
        return mInstance;
    }

    //método para permitir o login do usuário
    //este método irá armazenar os dados do usuário em preferências compartilhadas
    public void userLogin(PerfilUsuario perfilUsuario) {
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        //editor.putInt(KEY_ID, perfilUsuario.getId());
        editor.putString(KEY_USUARIO, perfilUsuario.getNome());
        editor.putString(KEY_EMAIL, perfilUsuario.getEmail());
        editor.putString(KEY_CIDADE, perfilUsuario.getCidade());
        editor.putString(KEY_ESTADO, perfilUsuario.getEstado());
        editor.putString(KEY_NUMERO_OAB, perfilUsuario.getNumeroOAB());
        editor.putString(KEY_TELEFONE, String.valueOf(perfilUsuario.getTelefone()));
        editor.apply();
    }

    //este método irá verificar se o usuário já está logado ou não
    public boolean isLoggedIn() {
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        return sharedPreferences.getString(KEY_USUARIO, null) != null;
    }

    //este método dará ao usuário logado
   /* public PerfilUsuario getUser() {
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        return new PerfilUsuario(
                sharedPreferences.getInt(KEY_ID, -1),
                sharedPreferences.getString(KEY_USUARIO, null),
                sharedPreferences.getString(KEY_EMAIL, null),
                sharedPreferences.getString(KEY_CIDADE, null),
                sharedPreferences.getString(KEY_ESTADO, null),
                sharedPreferences.getString(KEY_NUMERO_OAB, null),
                sharedPreferences.getString(KEY_TELEFONE, null)
        );
    }

    //este método fará o logout do usuário
    public void logout() {
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.clear();
        editor.apply();
        mCtx.startActivity(new Intent(mCtx, Login.class));
    }*/
}