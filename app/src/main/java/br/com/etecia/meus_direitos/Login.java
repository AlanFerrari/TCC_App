package br.com.etecia.meus_direitos;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;

import br.com.etecia.meus_direitos.objetos.User;

public class Login extends AppCompatActivity {

    EditText edtUsuario;
    EditText edtSenha;
    ImageView voltar;
    TextView esqueciSenha;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        edtUsuario = (EditText) findViewById(R.id.edtEmail);
        edtSenha = (EditText) findViewById(R.id.edtSenha);
        voltar = findViewById(R.id.imgVoltar);
        esqueciSenha = findViewById(R.id.txtesqueciSenha);

        if (SharedPrefManager.getInstance(this).isLoggedIn()) {
            finish();
            startActivity(new Intent(this, PerfilAdvogado_Adv.class));
            return;
        }

        //se o usuário pressionar no login
        //chamando o método login
        findViewById(R.id.btnLogar).setOnClickListener(view -> userLogin());

        //se o usuário pressionar não registrado
        findViewById(R.id.txtIrCadastrar).setOnClickListener(view -> {
            //abrir tela de cadastro
            startActivity(new Intent(getApplicationContext(), CadastroAdvogado.class));
            finish();
        });

        voltar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), Entrar_Como.class);
                startActivity(intent);
                finish();
            }
        });
        esqueciSenha.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), SolicitarNovaSenha.class);
                startActivity(intent);
                finish();
            }
        });

    }

    private void userLogin() {
        //primeiro pegando os valores
        final String email = edtUsuario.getText().toString();
        final String senha = edtSenha.getText().toString();

        //validando entradas
        if (TextUtils.isEmpty(email)) {
            edtUsuario.setError("Por favor insira um email");
            edtUsuario.requestFocus();
            return;
        }

        if (TextUtils.isEmpty(senha)) {
            edtSenha.setError("Por favor insira uma senha");
            edtSenha.requestFocus();
            return;
        }

        //se estiver tudo bem

        class UserLogin extends AsyncTask<Void, Void, String> {

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);


                try {
                    //convertendo a resposta para o objeto json
                    JSONObject obj = new JSONObject(s);

                    //se não houver erro na resposta
                    if (!obj.getBoolean("error")) {
                        Toast.makeText(getApplicationContext(), obj.getString("message"), Toast.LENGTH_SHORT).show();

                        //pega o usuário da resposta
                        JSONObject userJson = obj.getJSONObject("user");

                        //criando um novo objeto de usuário
                        User user = new User(
                                userJson.getInt("id"),
                                userJson.getString("usuario"),
                                userJson.getString("email"),
                                userJson.getString("cidade"),
                                userJson.getString("estado"),
                                userJson.getString("numero_oab"),
                                userJson.getString("telefone_cel")
                        );

                        //armazenando o usuário nas preferências compartilhadas
                        SharedPrefManager.getInstance(getApplicationContext()).userLogin(user);

                        //iniciando a atividade do perfil
                        startActivity(new Intent(getApplicationContext(), PerfilAdvogado_Adv.class));
                        finish();
                    } else {
                        Toast.makeText(getApplicationContext(), "E-mail ou senha inválidos", Toast.LENGTH_SHORT).show();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            protected String doInBackground(Void... voids) {
                //criando objeto manipulador de requisição
                RequestHandler requestHandler = new RequestHandler();

                //criando parâmetros de requisição
                HashMap<String, String> params = new HashMap<>();
                params.put("email", email);
                params.put("senha", senha);

                //retornando a resposta
                return requestHandler.sendPostRequest(URLs.URL_LOGIN, params);
            }
        }

        UserLogin ul = new UserLogin();
        ul.execute();
    }
}