package br.com.etecia.meus_direitos;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.concurrent.ExecutionException;

import br.com.etecia.meus_direitos.objetos.API_IBGE;
import br.com.etecia.meus_direitos.objetos.Cidades;
import br.com.etecia.meus_direitos.objetos.Estados;
import br.com.etecia.meus_direitos.objetos.PerfilUsuario;

public class CadastroAdvogado extends AppCompatActivity {

    Button btnCadastrarAdvogado;
    ImageView voltar;
    EditText edtNomeAdvogado, edtEmail, edtTelefone, edtRegistroOAB, edtSenha;
    Spinner spinnerEstado, spinnerCidade;

    Cidades[] municipios = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastro_advogado);

        btnCadastrarAdvogado = findViewById(R.id.btncadastrarAdvogado);
        voltar = findViewById(R.id.imgVoltar);
        edtNomeAdvogado = findViewById(R.id.nomeAdvogado);
        edtEmail = findViewById(R.id.email);
        edtTelefone = findViewById(R.id.telefone);
        edtRegistroOAB = findViewById(R.id.registroOAB);
        edtSenha = findViewById(R.id.senha);

        spinnerEstado = findViewById(R.id.estadoSpinner);
        spinnerCidade = findViewById(R.id.cidadeSpinner);
        spinnerCidade.setEnabled(true);

        voltar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(CadastroAdvogado.this, Login.class);
                startActivity(intent);
                CadastroAdvogado.this.finish();
            }
        });

        btnCadastrarAdvogado.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (!edtNomeAdvogado.getText().toString().equals("") || edtEmail.getText().toString().equals("") || edtTelefone.getText().toString().equals("") || spinnerEstado.getSelectedItem().toString().equals(0) || spinnerCidade.getSelectedItem().toString().equals(0) || edtRegistroOAB.getText().toString().equals("") || edtSenha.getText().toString().equals("") ) {

                    Intent intent = new Intent(getApplicationContext(), Chip_filtro_areas.class);
                    Api api = new Api(getApplicationContext());
                    PerfilUsuario perfilUsuario = new PerfilUsuario();
                    perfilUsuario.setNome(edtNomeAdvogado.getText().toString());
                    perfilUsuario.setEmail(edtEmail.getText().toString());
                    perfilUsuario.setTelefone(Integer.valueOf(edtTelefone.getText().toString()));
                    perfilUsuario.setEstado(spinnerEstado.getSelectedItem().toString());
                    perfilUsuario.setCidade(spinnerCidade.getSelectedItem().toString());
                    perfilUsuario.setNumeroOAB(edtRegistroOAB.getText().toString());
                    perfilUsuario.setSenha(edtSenha.getText().toString());
                    startActivity(intent);
                    finish();
                }
            }
        });

        String respostaEstados = executaApiIBGE("estado");

        Gson gsonEstados = new GsonBuilder().setPrettyPrinting().create();
        final Estados[] estados = gsonEstados.fromJson(String.valueOf(respostaEstados), Estados[].class);

        final ArrayList<String> estadosParaSpinner = new ArrayList<>();

        for (Estados estado: estados){
            estadosParaSpinner.add(estado.getNome());
        }

        //Ordena em ordem alfabética
        Collections.sort(estadosParaSpinner);

        estadosParaSpinner.add(0, "Selecione o Estado");

        //Criando um array de estados no spinner
        ArrayAdapter<String> adapterEstados = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, estadosParaSpinner);
        spinnerEstado.setAdapter(adapterEstados);

        spinnerEstado.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                for (Estados estado : estados) {
                    if (estado.getNome().equals(spinnerEstado.getSelectedItem().toString())) {
                        solicitarMunicipios(estado.getSigla());
                    }
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        spinnerCidade.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                for (Cidades cidade : municipios) {
                    if (cidade.getNome().equals(spinnerCidade.getSelectedItem().toString())) {
                        solicitarSubdistritos(String.valueOf(cidade.getId()));
                    }
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

    }

    private void solicitarMunicipios(String siglaEstado) {
        String respostaMunicipios = executaApiIBGE("municipio", siglaEstado);

        Gson gsonMunicipios = new GsonBuilder().setPrettyPrinting().create();
        municipios = gsonMunicipios.fromJson(String.valueOf(respostaMunicipios), Cidades[].class);

        final ArrayList<String> municipiosParaSpinner = new ArrayList<>();
        final ArrayList<String> idMunicipios = new ArrayList<>();

        for (Cidades cidade: municipios){
            municipiosParaSpinner.add(cidade.getNome());
            idMunicipios.add(String.valueOf(cidade.getId()));
        }

        //Ordena em ordem alfabética
        Collections.sort(municipiosParaSpinner);

        municipiosParaSpinner.add(0, "Selecione a Cidade");

        spinnerCidade.setEnabled(true);

        //Criando um array de municipios no spinner
        ArrayAdapter<String> adapterMunicipios = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, municipiosParaSpinner);
        spinnerCidade.setAdapter(adapterMunicipios);

    }

    private void solicitarSubdistritos(String idMunicipio) {
        String respostaSubdistritos = executaApiIBGE("subdistrito", idMunicipio);
    }

    private String executaApiIBGE (String... params) {
        API_IBGE api_ibge = new API_IBGE();

        String respostaIBGE = null;

        try {
            respostaIBGE = api_ibge.execute(params).get();

        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return respostaIBGE;
    }

    private void registerUser() {
        final String usuario = edtNomeAdvogado.getText().toString().trim();
        final String email = edtEmail.getText().toString().trim();
        final String senha = edtSenha.getText().toString().trim();
        final String estado = spinnerEstado.getSelectedItem().toString();
        final String cidade = spinnerCidade.getSelectedItem().toString();
        final String numeroOAB = edtRegistroOAB.getText().toString().trim();
        final String telefone = edtTelefone.getText().toString().trim();


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
            spinnerCidade.setSelected(Boolean.parseBoolean("Selecione a cidade onde mora"));
            spinnerCidade.requestFocus();
            return;
        }

        if (TextUtils.isEmpty(estado)) {
            spinnerEstado.setSelected(Boolean.parseBoolean("Selecione um estado onde mora"));
            spinnerEstado.requestFocus();
            return;
        }

        if (TextUtils.isEmpty(numeroOAB)) {
            edtRegistroOAB.setError("Insira seu registro na OAB");
            edtRegistroOAB.requestFocus();
            return;
        }

        if (TextUtils.isEmpty(telefone)) {
            edtTelefone.setError("Insira o número do seu whatsapp");
            edtTelefone.requestFocus();
            return;
        }

        //se passar em todas as validações

        class RegisterUser extends AsyncTask<Void, Void, String> {

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
                params.put("numeroOAB", numeroOAB);
                params.put("telefone", telefone);

                //Essa parte é nova, baseada no projeto hero

                //retornando a resposta
                return requestHandler.sendPostRequest(URLs.URL_REGISTER, params);
            }

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
                        JSONObject userJson = obj.getJSONObject("usuario");

                        //criando um novo objeto usuário
                        PerfilUsuario user = new PerfilUsuario(
                                userJson.getInt("id"),
                                userJson.getString("usuario"),
                                userJson.getString("email"),
                                userJson.getString("cidade"),
                                userJson.getString("estado"),
                                userJson.getString("numeroOAB"),
                                userJson.getString("telefone")
                        );

                        //armazenando o usuário nas preferências compartilhadas
                        SharedPrefManager.getInstance(getApplicationContext()).userLogin(user);

                        //iniciando a atividade do perfil
                        //startActivity(new Intent(getApplicationContext(), PerfilAdvogado_Adv.class));
                        //finish();
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