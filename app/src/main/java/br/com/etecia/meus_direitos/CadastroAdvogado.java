package br.com.etecia.meus_direitos;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;

import br.com.etecia.meus_direitos.objetos.User;

public class CadastroAdvogado extends AppCompatActivity {

    Button btnCadastrarAdvogado;
    ImageView voltar;
    EditText edtNomeAdvogado;
    EditText edtEmail;
    EditText edtTelefone;
    EditText edtCidade;
    EditText edtEstado;
    EditText edtRegistroOAB;
    EditText edtSenha;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastro_advogado);

        btnCadastrarAdvogado = findViewById(R.id.btncadastrarAdvogado);
        voltar = findViewById(R.id.imgVoltar);
        edtNomeAdvogado = findViewById(R.id.nomeAdvogado);
        edtEmail = findViewById(R.id.email);
        edtTelefone = findViewById(R.id.telefone);
        edtCidade = findViewById(R.id.cidade);
        edtEstado = findViewById(R.id.estado);
        edtRegistroOAB = findViewById(R.id.registroOAB);
        edtSenha = findViewById(R.id.senha);

        voltar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(CadastroAdvogado.this, Login.class);
                startActivity(intent);
                CadastroAdvogado.this.finish();
            }
        });

        findViewById(R.id.btncadastrarAdvogado).setOnClickListener(view -> {
            //se o usuário pressionou o botão registrar
            //aqui vamos registrar o usuário no servidor
            registerUser();
            Intent intent = new Intent(getApplicationContext(), Chip_filtro_areas.class);
            startActivity(intent);
            finish();
        });
    }

    private void registerUser() {
        final String usuario = edtNomeAdvogado.getText().toString().trim();
        final String email = edtEmail.getText().toString().trim();
        final String senha = edtSenha.getText().toString().trim();
        final String cidade = edtCidade.getText().toString().trim();
        final String estado = edtEstado.getText().toString().trim();
        final String numero_oab = edtRegistroOAB.getText().toString().trim();
        final String telefone_cel = edtTelefone.getText().toString().trim();


        //primeiro faremos as validações

        if (TextUtils.isEmpty(usuario)) {
            edtNomeAdvogado.setError("Por favor insira seu nome completo");
            edtNomeAdvogado.requestFocus();
            return;
        }

        if (TextUtils.isEmpty(email)) {
            edtEmail.setError("Por favor insira seu email");
            edtEmail.requestFocus();
            return;
        }

        if (!android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            edtEmail.setError("Email inválido");
            edtEmail.requestFocus();
            return;
        }

        if (TextUtils.isEmpty(senha)) {
            edtSenha.setError("Insira uma senha");
            edtSenha.requestFocus();
            return;
        }

        if (TextUtils.isEmpty(cidade)) {
            edtCidade.setError("Digite a cidade onde mora");
            edtCidade.requestFocus();
            return;
        }

        if (TextUtils.isEmpty(estado)) {
            edtEstado.setError("Insira um estado onde mora");
            edtEstado.requestFocus();
            return;
        }

        if (TextUtils.isEmpty(numero_oab)) {
            edtRegistroOAB.setError("Insira seu registro na OAB");
            edtRegistroOAB.requestFocus();
            return;
        }

        if (TextUtils.isEmpty(telefone_cel)) {
            edtTelefone.setError("Insira o número do seu whatsapp");
            edtTelefone.requestFocus();
            return;
        }

        //se passar em todas as validações

        class RegisterUser extends AsyncTask<Void, Void, String> {

            private ProgressBar progressBar;

            @Override
            protected String doInBackground(Void... voids) {
                //criando objeto manipulador de requisição
                RequestHandler requestHandler = new RequestHandler();

                //criando parâmetros de requisição
                HashMap<String, String> params = new HashMap<>();
                params.put("usuario", usuario);
                params.put("email", email);
                params.put("senha", senha);
                params.put("cidade", cidade);
                params.put("estado", estado);
                params.put("numero_oab", numero_oab);
                params.put("telefone_cel", telefone_cel);

                //retornando a resposta
                return requestHandler.sendPostRequest(URLs.URL_REGISTER, params);
            }
/*
                      @Override
                       protected void onPreExecute() {
                          super.onPreExecute();
                          //exibindo a barra de progresso enquanto o usuário se registra no servidor
                          progressBar = (ProgressBar) findViewById(R.id.barraDeProgresso);
                          progressBar.setVisibility(View.VISIBLE);
                      }
*/
            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                //escondendo a barra de progresso após a conclusão
                //progressBar.setVisibility(View.GONE);

                try {
                    //convertendo a resposta para o objeto json
                    JSONObject obj = new JSONObject(s);

                    //se não houver erro na resposta
                    if (!obj.getBoolean("error")) {
                        Toast.makeText(getApplicationContext(), obj.getString("message"), Toast.LENGTH_SHORT).show();

                        //pega o usuário da resposta
                        JSONObject userJson = obj.getJSONObject("usuario");

                        //criando um novo objeto usuário
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
                        Toast.makeText(getApplicationContext(), "Ocorreu algum erro", Toast.LENGTH_SHORT).show();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }

        //executando a tarefa assíncrona
        RegisterUser ru = new RegisterUser();
        ru.execute();
    }
}